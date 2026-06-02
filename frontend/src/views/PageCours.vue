<template>
  <BContainer class="py-5 min-vh-100">
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
                :class="{ 'chapitre-actif': index === indexChapitreActif }"
              >
                <div
                  class="numero-chapitre me-3 fw-bold d-flex justify-content-center align-items-center"
                  :class="{ 'numero-actif': index === indexChapitreActif }"
                >
                  {{ index + 1 }}
                </div>

                <span class="fw-bold text-truncate" style="font-size: 0.95rem">
                  {{ chapitre.titre }}
                </span>
              </BListGroupItem>
            </BListGroup>
          </BCard>
        </div>
      </BCol>

      <BCol lg="8">
        <BCard class="border-0 shadow-sm" style="border-radius: 12px" v-if="chapitreActif">
          <BCardBody class="p-4 p-md-5">
            <h2 class="fw-bold mb-3 titre-catalogue">{{ chapitreActif.titre }}</h2>
            <p class="text-muted fs-5 mb-5 lh-lg border-bottom pb-4">
              {{ chapitreActif.description }}
            </p>

            <div
              v-if="chapitreActif.contenu"
              class="contenu-cours mb-5"
              v-html="chapitreActif.contenu"
            ></div>

            <div
              v-if="chapitreActif.ressources && chapitreActif.ressources.length > 0"
              class="ressources-section bg-light p-4 p-md-5 rounded-4 mt-5"
            >
              <h4 class="fw-bold mb-4" style="color: #4a2c59">
                <i class="bi bi-paperclip me-2"></i>Ressources annexes
              </h4>

              <div v-for="ressource in chapitreActif.ressources" :key="ressource.id" class="mb-4">
                <div v-if="ressource.type === 'VIDEO'">
                  <h6 class="fw-bold mb-2">
                    <i class="bi bi-play-btn-fill me-2" style="color: #4a2c59"></i
                    >{{ ressource.nom }}
                  </h6>
                  <div class="ratio ratio-16x9 bg-dark rounded-4 shadow-sm overflow-hidden">
                    <iframe :src="ressource.url" allowfullscreen class="border-0"></iframe>
                  </div>
                </div>

                <div v-else-if="ressource.type === 'IMAGE'">
                  <h6 class="fw-bold mb-2" style="color: #4a2c59">
                    <i class="bi bi-image-fill me-2"></i>{{ ressource.nom }}
                  </h6>
                  <div
                    class="text-center bg-white p-3 rounded-4 shadow-sm border border-light-subtle"
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
                    <a :href="ressource.url" download class="btn btn-dark btn-sm rounded-pill mt-1">
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
                <span v-else> Terminer le cours <i class="bi bi-check-circle-fill ms-2"></i> </span>
              </BButton>
            </div>
          </BCardBody>
        </BCard>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const indexChapitreActif = ref(0)

//données tests, à remplacer par l'appel api
const cours = ref({
  id: 1,
  titre: 'Maîtriser Vue.js 3',
  description: 'Formation complète et interactive sur le framework JavaScript.',
  chapitres: [
    {
      id: 101,
      titre: '1. Introduction à Vue.js',
      description:
        'Découvrez les bases avec cette vidéo de présentation, un schéma de fonctionnement et le code source de départ.',
      contenu: `
        <h4 style="color: #4a2c59; font-weight: bold;">Le contexte de ce cours</h4>
        <p style="font-size: 1.1rem; line-height: 1.8;">Bienvenue dans ce premier chapitre ! Avant de télécharger les ressources ci-dessous, il est important de comprendre les directives de base. <strong>Vue.js</strong> est un framework fantastique.</p>
        <p style="font-size: 1.1rem; line-height: 1.8;">Voici ce que vous devez faire pour valider ce module :</p>
        <ul>
            <li>Regarder la vidéo en annexe</li>
            <li>Télécharger le fichier ZIP pour avoir le code de départ</li>
            <li>Lire attentivement le schéma d'architecture</li>
        </ul>
        <div style="background-color: #fff3cd; padding: 15px; border-radius: 8px; border-left: 5px solid #ffc107;">
           💡 <strong>Astuce :</strong> N'hésitez pas à garder le PDF de la documentation ouvert sur un deuxième écran !
        </div>
      `,
      ressources: [
        {
          id: 1,
          nom: 'Vidéo de présentation',
          url: 'https://www.youtube.com/embed/dQw4w9WgXcQ',
          type: 'VIDEO',
        },
        {
          id: 2,
          nom: "Schéma d'architecture Vue.js",
          url: 'https://upload.wikimedia.org/wikipedia/commons/9/95/Vue.js_Logo_2.svg',
          type: 'IMAGE',
        },
        {
          id: 3,
          nom: 'Code source de départ',
          url: '#',
          type: 'FICHIER_ZIP',
        },
      ],
    },
    {
      id: 102,
      titre: '2. Les Composants et les Props',
      description:
        'Plongez au cœur de la réutilisabilité avec les composants. Lisez le cours complet en format PDF.',
      ressources: [
        {
          id: 4,
          nom: 'Support de cours (Chapitre 2)',
          url: 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf',
          type: 'FICHIER_PDF',
        },
      ],
    },
    {
      id: 103,
      titre: '3. Le Routing et la Navigation',
      description: 'Apprenez à naviguer entre les pages sans recharger le navigateur web.',
      ressources: [
        {
          id: 5,
          nom: 'Tutoriel Vidéo : Vue Router',
          url: 'https://www.youtube.com/embed/vWnN_84nmbQ',
          type: 'VIDEO',
        },
      ],
    },
    {
      id: 104,
      titre: '4. Projet Final et Conclusion',
      description:
        'Mettez en pratique tout ce que vous avez appris en créant une application complète de A à Z.',
      ressources: [
        {
          id: 7,
          nom: 'Ressources graphiques (Images, Icônes)',
          url: '#',
          type: 'FICHIER_ZIP',
        },
      ],
    },
  ],
})

const chapitreActif = computed(() => {
  if (!cours.value || !cours.value.chapitres) {
    return null
  }
  return cours.value.chapitres[indexChapitreActif.value]
})

function changerChapitre(index) {
  indexChapitreActif.value = index
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function chapitrePrecedent() {
  if (indexChapitreActif.value > 0) {
    changerChapitre(indexChapitreActif.value - 1)
  }
}

function chapitreSuivant() {
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
</style>
