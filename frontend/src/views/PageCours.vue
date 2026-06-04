<template>
  <BContainer class="py-5 min-vh-100">
    <div v-if="cours">
      <div class="mb-5">
        <BButton
          variant="link"
          class="text-muted text-decoration-none p-0 mb-3"
          @click="retourCatalogue"
        >
          <i class="bi bi-arrow-left me-2"></i> Retour aux cours
        </BButton>
        <h1 class="fw-bold fs-2 m-0 titre-catalogue">{{ cours.titre }}</h1>
        <p class="text-muted fs-6 mt-2 mb-0">{{ cours.description }}</p>
      </div>

      <BRow>
        <BCol lg="4" class="mb-4">
          <div class="sticky-top" style="top: 20px; z-index: 1">
            <BCard class="border-0 shadow-sm carte-filtres">
              <h5 class="fw-bold mb-4 titre-filtres"><i class="bi bi-list-ul me-2"></i>Sommaire</h5>

              <BListGroup flush class="gap-2">
                <BListGroupItem
                  v-for="(chapitre, index) in cours.chapitres"
                  :key="chapitre.id"
                  @click="changerChapitre(index)"
                  class="chapitre-item border-0 d-flex align-items-center"
                  :class="{
                    'chapitre-actif': index === indexChapitreActif,
                    'opacity-50 text-decoration-line-through': index > nbChapitresFinis,
                  }"
                >
                  <div
                    class="numero-chapitre me-3 fw-bold d-flex justify-content-center align-items-center"
                    :class="{
                      'numero-actif': index === indexChapitreActif,
                      'bg-success text-white shadow-sm':
                        index < nbChapitresFinis && index !== indexChapitreActif,
                    }"
                  >
                    <i
                      v-if="index < nbChapitresFinis && index !== indexChapitreActif"
                      class="bi bi-check-lg"
                    ></i>
                    <i v-else-if="index > nbChapitresFinis" class="bi bi-lock-fill small"></i>
                    <span v-else>{{ index + 1 }}</span>
                  </div>

                  <span class="fw-bold text-truncate" style="font-size: 0.95rem">
                    {{ chapitre.titre }}
                  </span>
                </BListGroupItem>
              </BListGroup>
            </BCard>

            <BCard class="border-0 shadow-sm mt-4 carte-filtres">
              <BButton
                variant="link"
                class="text-decoration-none text-dark w-100 d-flex justify-content-between align-items-center p-0"
                @click="deroulerListeEleves"
              >
                <h5 class="fw-bold m-0" style="color: #4a2c59">
                  <i class="bi bi-people-fill me-2"></i>Étudiants inscrits
                </h5>
                <i
                  class="bi fw-bold fs-5"
                  :class="elevesOuverts ? 'bi-chevron-up' : 'bi-chevron-down'"
                  style="color: #4a2c59"
                ></i>
              </BButton>
              <BCollapse id="collapse-eleves" v-model="elevesOuverts" class="mt-3">
                <hr class="text-muted opacity-25 mt-0 mb-3" />

                <BListGroup v-if="elevesInscrits.length > 0" flush class="gap-2">
                  <BListGroupItem
                    v-for="eleve in elevesInscrits"
                    :key="eleve.id"
                    class="d-flex align-items-center p-2 rounded-3 border-0"
                    style="background-color: #f8f9fa"
                  >
                    <BAvatar
                      class="me-3 shadow-sm"
                      size="2.5rem"
                      style="background-color: #b8a854 !important; color: white; font-weight: bold"
                      :text="eleve.prenom.charAt(0).toUpperCase() + eleve.nom.charAt(0).toUpperCase()"
                    />

                    <div class="text-truncate flex-grow-1">
                      <h6 class="mb-0 fw-bold text-truncate" style="color: #4a2c59">
                        {{ eleve.prenom }} {{ eleve.nom }}
                      </h6>
                      <small class="text-muted d-block text-truncate">
                        {{ eleve.email }}
                      </small>
                    </div>
                  </BListGroupItem>
                </BListGroup>

                <div v-else class="text-center py-3 text-muted small">
                  Aucun étudiant n'est encore inscrit à ce cours
                </div>
              </BCollapse>
            </BCard>
          </div>
        </BCol>

        <BCol lg="8">
          <BCard class="border-0 shadow-sm" style="border-radius: 12px" v-if="chapitreActif">
            <BCardBody class="p-4 p-md-5">
              <div class="d-flex align-content mb-5 gap-3">
                <span class="small fw-bold text-muted text-nowrap">Progression</span>
                <BProgress
                  :value="progression"
                  class="flex-grow-1 shadow-sm barre-violette"
                  height="10px"
                />
                <span class="small fw-bold" style="color: #4a2c59"> {{ progression }}% </span>
              </div>

              <h2 class="fw-bold mb-3 titre-catalogue">{{ chapitreActif.titre }}</h2>
              <p class="text-muted fs-5 mb-5 lh-lg border-bottom pb-4">
                {{ chapitreActif.description }}
              </p>

              <div v-if="chapitreActif.ressources && chapitreActif.ressources.length > 0">
                <div v-for="ressource in chapitreActif.ressources" :key="ressource.id" class="mb-4">
                  <div v-if="ressource.type === 'VIDEO'">
                    <h6 class="fw-bold mb-3">
                      <i class="bi bi-play-btn-fill me-2" style="color: #4a2c59"></i
                      >{{ ressource.nom }}
                    </h6>
                    <div class="ratio ratio-16x9 bg-dark rounded-4 shadow-sm overflow-hidden">
                      <iframe :src="ressource.url" allowfullscreen class="border-0"></iframe>
                    </div>
                  </div>

                  <div v-else-if="ressource.type === 'IMAGE'">
                    <h6 class="fw-bold mb-3" style="color: #4a2c59">
                      <i class="bi bi-image-fill me-2"></i>{{ ressource.nom }}
                    </h6>
                    <div
                      class="text-center bg-white p-2 rounded-4 shadow-sm border border-light-subtle"
                    >
                      <img
                        :src="ressource.url"
                        :alt="ressource.nom"
                        class="img-fluid rounded-3"
                        style="max-height: 400px; object-fit: contain"
                      />
                    </div>
                  </div>

                  <div
                    v-else-if="ressource.type === 'FICHIER_PDF' || ressource.type === 'DOCUMENT'"
                    class="p-3 bg-white rounded-4 border border-light-subtle d-flex align-items-center shadow-sm"
                  >
                    <i
                      class="bi bi-file-earmark-pdf-fill me-3"
                      style="color: #a17c5b; font-size: 2.5rem"
                    ></i>
                    <div class="flex-grow-1">
                      <h6 class="mb-1 fw-bold" style="color: #4a2c59">{{ ressource.nom }}</h6>
                      <a
                        :href="ressource.url"
                        target="_blank"
                        rel="noopener noreferrer"
                        class="btn btn-outline-dark btn-sm rounded-pill mt-1"
                      >
                        <i class="bi bi-book me-1"></i> Consulter
                      </a>
                    </div>
                  </div>

                  <div
                    v-else-if="ressource.type === 'FICHIER_ZIP'"
                    class="p-3 bg-white rounded-4 border border-light-subtle d-flex align-items-center shadow-sm"
                  >
                    <i
                      class="bi bi-file-earmark-zip-fill me-3 text-secondary"
                      style="font-size: 2.5rem"
                    ></i>
                    <div class="flex-grow-1">
                      <h6 class="mb-1 fw-bold" style="color: #4a2c59">{{ ressource.nom }}</h6>
                      <a
                        :href="ressource.url"
                        download
                        class="btn btn-dark btn-sm rounded-pill mt-1"
                      >
                        <i class="bi bi-download me-1"></i> Télécharger
                      </a>
                    </div>
                  </div>
                </div>
              </div>

              <hr class="my-5 separateur-catalogue opacity-25" />

              <div class="d-flex justify-content-between align-items-center">
                <BButton
                  variant="outline-secondary"
                  class="rounded-pill px-4 py-2 fw-bold"
                  :disabled="indexChapitreActif === 0"
                  @click="chapitrePrecedent"
                >
                  <i class="bi bi-arrow-left me-2"></i> Précédent
                </BButton>

                <span class="text-muted small fw-bold d-none d-md-block">
                  Chapitre {{ indexChapitreActif + 1 }} sur {{ cours.chapitres.length }}
                </span>

                <BButton
                  class="rounded-pill px-4 py-2 fw-bold bouton-suivant"
                  @click="chapitreSuivant"
                >
                  <span v-if="indexChapitreActif < cours.chapitres.length - 1">
                    Terminer le chapitre <i class="bi bi-arrow-right ms-2"></i>
                  </span>
                  <span v-else>
                    Terminer le cours <i class="bi bi-check-circle-fill ms-2"></i>
                  </span>
                </BButton>
              </div>
            </BCardBody>
          </BCard>
        </BCol>
      </BRow>
    </div>
  </BContainer>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const route = useRoute()

const indexChapitreActif = ref(0)
const progression = ref(0)

const cours = ref()
const idCours = route.params.id

const elevesInscrits = ref([])
const elevesOuverts = ref(false)
const elevesDejaCharges = ref(false)

const chapitreActif = computed(() => {
  if (!cours.value || !cours.value.chapitres) {
    return null
  }
  return cours.value.chapitres[indexChapitreActif.value]
})

const nbChapitresFinis = computed(() => {
  if (!cours.value || !cours.value.chapitres) {
    return 0
  }
  return Math.round((progression.value / 100) * cours.value.chapitres.length)
})

onMounted(async () => {
  try {
    const token = localStorage.getItem('token')

    const reponseProgression = await axios.get(`/api/progression/${idCours}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    progression.value = reponseProgression.data.tauxProgression

    const reponseCours = await axios.get(`/api/cours/${idCours}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })

    cours.value = reponseCours.data
    indexChapitreActif.value = nbChapitresFinis.value - 1
  } catch (erreur) {
    console.error('Erreur lors du chargement de la progression', erreur)
    progression.value = 0
    cours.value = null
  }
})

async function deroulerListeEleves() {
  elevesOuverts.value = !elevesOuverts.value
  elevesInscrits.value = cours.value.eleves || []
  elevesDejaCharges.value = true
}

function changerChapitre(index) {
  if (index > nbChapitresFinis.value) {
    return
  }
  indexChapitreActif.value = index
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function chapitrePrecedent() {
  if (indexChapitreActif.value > 0) {
    changerChapitre(indexChapitreActif.value - 1)
  }
}

async function chapitreSuivant() {
  if (indexChapitreActif.value >= nbChapitresFinis.value) {
    const idChapitre = chapitreActif.value.id

    try {
      const token = localStorage.getItem('token')

      await axios.post(
        `/api/chapitres/${idChapitre}/terminer`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      )
      const reponseProgression = await axios.get(`/api/progression/${idCours}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      progression.value = reponseProgression.data.tauxProgression
    } catch (erreur) {
      console.error('Erreur API : ', erreur)
    }
  }

  if (indexChapitreActif.value < cours.value.chapitres.length - 1) {
    changerChapitre(indexChapitreActif.value + 1)
  } else {
    retourCatalogue()
  }
}

function retourCatalogue() {
  router.push('/mes-cours-inscrits')
}
</script>

<style scoped>
.chapitre-item {
  border-radius: 8px !important;
  cursor: pointer;
  transition: all 0.2s ease;
  background-color: transparent;
  color: #6c757d;
}

.chapitre-item:hover {
  background-color: #f8f9fa;
  transform: translateX(5px);
}

.chapitre-actif {
  background-color: rgba(74, 44, 89, 0.08) !important;
  color: #4a2c59 !important;
}

.numero-chapitre {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: #e9ecef;
  color: #6c757d;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.numero-actif {
  background-color: #4a2c59;
  color: white;
  box-shadow: 0 4px 8px rgba(74, 44, 89, 0.2);
}

.bouton-suivant {
  background-color: #4a2c59 !important;
  border-color: #4a2c59 !important;
  color: white !important;
  transition: all 0.3s ease;
}

.bouton-suivant:hover {
  background-color: #b8a854 !important;
  border-color: #b8a854 !important;
  color: #4a2c59 !important;
  transform: translateX(3px);
}

.barre-violette :deep(.progress-bar) {
  background-color: #4a2c59 !important;
}
</style>
