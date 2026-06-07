<template>
  <BContainer class="py-5">
    <BNavbar class="bg-white rounded-4 shadow-sm py-2 mb-5 justify-content-center">
      <BNavbarBrand tag="div" class="mb-0 p-0 w-100 text-center">
        <h1 class="titre-catalogue fw-bold fs-2 m-0 mt-2">Mes cours publiés</h1>
        <p class="text-muted fs-6 mt-1 mb-2">Retrouvez les cours que vous avez publiés</p>
        <hr class="separateur-catalogue w-25 mx-auto mb-2" />
      </BNavbarBrand>
    </BNavbar>

    <div class="d-flex justify-content-start mb-4">
      <BButton variant="link" class="bouton-retour-navigation p-2 text-decoration-none d-flex align-items-center gap-2"
               @click="retourArriere"
               title="Retour à la page précédente">
        <i class="bi bi-arrow-left fs-4"></i>
        <span class="fw-medium fs-5">Retour</span>
      </BButton>
      </div>

    <BRow>
      <BCol lg="3" md="4" class="mb-4">
        <BCard class="carte-filtres border-0 shadow-sm">
          <h5 class="titre-filtres fw-bold mb-4">
            <i class="bi bi-funnel-fill me-2"></i>Filtres
          </h5>

          <div class="mb-3">
            <label class="form-label fw-bold small text-muted">Rechercher un cours</label>
            <BFormInput
              v-model="recherche"
              placeholder="Ex: Java..."
              class="rounded-pill px-3"
            ></BFormInput>
          </div>

          <hr class="text-muted mb-4" />

          <div class="mb-4 d-flex justify-content-between align-items-center">
            <label class="form-label fw-bold small text-muted mb-0">Cours privés</label>
            <BFormCheckbox v-model="afficherCoursPrives" switch size="lg" class="switch-custom"></BFormCheckbox>
          </div>

          <hr class="text-muted mb-4" />

          <div class="mb-4">
            <label class="form-label fw-bold small text-muted mb-3">Difficulté</label>
            <BFormCheckboxGroup
              v-model="difficultes"
              :options="difficultesPossibles"
              stacked
              class="checkboxes-custom"
            ></BFormCheckboxGroup>
          </div>

          <div class="mb-2" v-if="categoriesPossibles.length > 0">
            <label class="form-label fw-bold small text-muted mb-3">Catégories</label>
            <BFormCheckboxGroup
              v-model="categories"
              :options="categoriesPossibles"
              stacked
              class="checkboxes-custom"
            ></BFormCheckboxGroup>
          </div>
        </BCard>
      </BCol>

      <BCol lg="9" md="8">
        <div v-if="chargement" class="text-center my-5">
          <BSpinner class="spinner-catalogue" label="Chargement..."></BSpinner>
        </div>

        <BAlert v-else-if="erreur !== ''" variant="danger" show>
          <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
        </BAlert>

        <BAlert v-else-if="coursFiltres.length === 0" variant="info" show class="alerte-catalogue-vide border-0 shadow-sm">
          <i class="bi bi-search me-2"></i> Aucun cours publié ne correspond à votre recherche
        </BAlert>

        <div v-else>
          <BRow class="g-4">
            <BCol cols="12" md="6" lg="4" v-for="cours in coursAffiches" :key="cours.id">
              <BCard class="h-100 shadow-sm carte-cours position-relative border-0" no-body>
                <div
                  v-if="cours.estPrive"
                  class="badge-cours-prive position-absolute top-0 end-0 m-2 bg-white rounded-circle d-flex align-items-center justify-content-center shadow-sm"
                  title="Cours privé"
                >
                  <i class="icone-cours-prive bi bi-lock-fill"></i>
                </div>

                <div class="bandeau-deco"></div>

                <BCardBody class="d-flex flex-column">
                  <BCardTitle class="titre-carte-cours fw-bold fs-5 mt-2">
                    {{ cours.titre }}
                  </BCardTitle>

                  <BCardText class="text-muted small mb-3">
                    {{ cours.description || 'Aucune description disponible.' }}
                  </BCardText>

                  <div class="mb-3">
                    <BBadge pill :class="couleurDifficulte(cours.difficulte)" class="me-2 mb-1 shadow-sm border-0 fw-bold px-3 py-2">
                      <i class="bi bi-bar-chart-fill me-1"></i> {{ cours.difficulte }}
                    </BBadge>
                    <BBadge pill class="badge-categorie border-0 me-1 mb-1 px-3 py-2" v-for="categorie in cours.categories" :key="categorie">
                      {{ categorie }}
                    </BBadge>
                  </div>

                  <div class="stat-cours mt-auto text-muted small">
                    <i class="bi bi-people-fill me-1"></i>
                    {{ nombreEleves(cours) }} élève{{ nombreEleves(cours) > 1 ? 's' : '' }} inscrit{{ nombreEleves(cours) > 1 ? 's' : '' }}
                  </div>
                </BCardBody>

                <BCardFooter class="bg-white border-top-0 text-end pb-3">
                  <small class="date-cours text-muted">
                    <i class="bi bi-calendar3 me-1"></i> {{ formaterDate(cours.dateCreation) }}
                  </small>

                  <BButton
                    v-if="estCreateur"
                    class="bouton-cours d-flex align-items-center gap-2 px-3 py-2 shadow-sm fw-medium"
                    size="sm"
                    @click="allerVersModification(cours.id)"
                    title="Modifier les informations de ce cours"
                  >
                    <i class="bi bi-pencil-square fs-5"></i>
                    <span>Modifier</span>
                  </BButton>

                </BCardFooter>
              </BCard>
            </BCol>
          </BRow>

          <div class="d-flex justify-content-center mt-5" v-if="coursFiltres.length > coursParPage">
            <BPagination v-model="pageCourante" :total-rows="coursFiltres.length" :per-page="coursParPage" pills class="pagination-personnalisee"></BPagination>
          </div>
        </div>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
  import axios from 'axios'
  import { computed, onMounted, ref } from 'vue'
  import { useRouter } from 'vue-router';

  const routeur = useRouter();
  const lesCours = ref([])
  const chargement = ref(true)
  const erreur = ref('')

  const pageCourante = ref(1)
  const coursParPage = ref(6)

  const recherche = ref('')

  const difficultes = ref([])
  const categories = ref([])
  const afficherCoursPrives = ref(false)

  const difficultesPossibles =
  [
    { text: 'Débutant', value: 'DEBUTANT' },
    { text: 'Intermédiaire', value: 'INTERMEDIAIRE' },
    { text: 'Avancé', value: 'AVANCE' },
  ]

  const estCreateur = computed(() => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const role = JSON.parse(atob(token.split('.')[1])).role;
        return role === 'CREATEUR' || role === 'createur';
      } catch (e) {
        return false;
      }
    }
    return false;
  });

  function allerVersModification(id) {
    routeur.push(`/cours/${id}/modifier`);
  }

  const categoriesPossibles = computed(() =>
  {
    const lesCategories = new Set()
    lesCours.value.forEach((cours) =>
    {
      if (cours.categories)
      {
        cours.categories.forEach((categorie) => lesCategories.add(categorie))
      }
    })
    return Array.from(lesCategories).map((categorie) => ({ text: categorie, value: categorie }))
  })

  const coursAffiches = computed(() =>
  {
    const debut = (pageCourante.value - 1) * coursParPage.value
    const fin = debut + coursParPage.value
    return coursFiltres.value.slice(debut, fin)
  })

  const coursFiltres = computed(() =>
  {
    pageCourante.value = 1

    return lesCours.value.filter((cours) =>
    {
      const texte = recherche.value.toLowerCase()
      const texteValide =
        !recherche.value ||
        cours.titre.toLowerCase().includes(texte) ||
        (cours.description || '').toLowerCase().includes(texte)

      const difficulte = cours.difficulte.toUpperCase()
      const difficulteValide =
        difficultes.value.length === 0 || difficultes.value.includes(difficulte)

      const categorieValide =
        categories.value.length === 0 ||
        (cours.categories || []).some((categorie) => categories.value.includes(categorie))

      const priveValide = !afficherCoursPrives.value || cours.estPrive === true

      return texteValide && difficulteValide && categorieValide && priveValide
    })
  })

  onMounted(() =>
  {
    chargerCours()
  })

  async function chargerCours()
  {
    try
    {
      const token = localStorage.getItem('token')

      const reponse = await axios.get('http://localhost:8080/api/cours/utilisateurs/publications',
        {
        headers:
          {
          Authorization: `Bearer ${token}`,
        },
      })
      lesCours.value = reponse.data.filter((cours) => cours.estPublie)
    }
    catch (error)
    {
      console.error('Erreur de chargement :', error)
      erreur.value = 'Chargement des cours publiés impossible'
    }
    finally
    {
      chargement.value = false
    }
  }

  function nombreEleves(cours)
  {
    return cours.eleves?.length || 0
  }

  function formaterDate(dateBrute)
  {
    if (!dateBrute) return ''
    const date = new Date(dateBrute)
    return date.toLocaleDateString('fr-FR')
  }

  function couleurDifficulte(difficulte)
  {
    if (!difficulte)
    {
      return 'badge-defaut'
    }
    switch (difficulte.toUpperCase())
    {
      case 'DEBUTANT':
        return 'badge-debutant'
      case 'INTERMEDIAIRE':
        return 'badge-intermediaire'
      case 'AVANCE':
        return 'badge-avance'
      default:
        return 'badge-defaut'
    }
  }

  function retourArriere()
  {
    routeur.back()
  }

</script>
