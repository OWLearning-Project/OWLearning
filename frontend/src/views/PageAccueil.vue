<template>
  <BContainer fluid class="page-accueil py-5 min-vh-100">
    <BandeauTitre titre="Accueil" sous-titre="Vos cours recents et votre derniere discussion" />

    <BAlert
      v-if="erreur !== ''"
      variant="danger"
      show
      class="alerte-catalogue-vide border-0 shadow-sm"
    >
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
    </BAlert>

    <Chargement v-if="chargement" />

    <BRow v-else class="g-4 align-items-stretch">
      <BCol xl="8">
        <section class="panneau-accueil">
          <div class="entete-panneau d-flex align-items-center justify-content-between gap-3">
            <div>
              <h2 class="titre-panneau mb-1">Cours consultes récemment</h2>
              <p class="texte-secondaire mb-0">Reprenez rapidement la ou vous vous étiez arrêté.</p>
            </div>

            <BButton
              variant="link"
              class="bouton-lien p-0 text-decoration-none fw-bold"
              @click="ouvrirCatalogue"
            >
              Catalogue
              <i class="bi bi-arrow-right ms-1"></i>
            </BButton>
          </div>

          <div v-if="coursRecents.length === 0" class="etat-vide text-center py-5">
            <i class="bi bi-journal-bookmark fs-1"></i>
            <p class="fw-bold mb-1 mt-3">Aucun cours consulté</p>
            <p class="text-muted small mb-3">Les cours ouverts apparaitront ici.</p>
            <BButton class="bouton-cours rounded-pill fw-bold px-4" @click="ouvrirCatalogue">
              Explorer le catalogue
            </BButton>
          </div>

          <div v-else class="grille-cours-recents">
            <article
              v-for="cours in coursRecents"
              :key="cours.id"
              class="carte-cours-recent"
            >
              <div class="bandeau-cours-recent">
                <BBadge
                  v-if="cours.estPrive"
                  pill
                  class="badge-prive"
                >
                  <i class="bi bi-lock-fill me-1"></i> Prive
                </BBadge>
              </div>

              <div class="contenu-cours-recent">
                <div class="mb-3">
                  <h3 class="titre-cours-recent mb-2">{{ cours.titre }}</h3>
                  <p class="texte-secondaire small mb-0">
                    <i class="bi bi-person-circle me-1"></i>
                    {{ nomCreateur(cours) }}
                  </p>
                </div>

                <p class="description-cours-recent mb-3">
                  {{ cours.description || 'Aucune description disponible' }}
                </p>

                <div class="badges-cours mb-3">
                  <BBadge
                    pill
                    :class="couleurDifficulte(cours.difficulte)"
                    class="badge-cours fw-bold px-3 py-2"
                  >
                    <i class="bi bi-bar-chart-fill me-1"></i> {{ cours.difficulte || 'NIVEAU' }}
                  </BBadge>

                  <BBadge
                    v-for="categorie in categoriesAffichees(cours)"
                    :key="categorie"
                    pill
                    class="badge-categorie px-3 py-2"
                  >
                    {{ categorie }}
                  </BBadge>
                </div>

                <div class="mt-auto d-flex align-items-center justify-content-between gap-3">
                  <span class="texte-secondaire small">
                    {{ formaterDateConsultation(cours.consulteLe) }}
                  </span>

                  <BButton
                    size="sm"
                    class="bouton-cours rounded-pill fw-bold px-3"
                    @click="ouvrirCoursRecent(cours)"
                  >
                    Ouvrir
                  </BButton>
                </div>
              </div>
            </article>
          </div>
        </section>
      </BCol>

      <BCol xl="4">
        <section class="panneau-accueil panneau-discussion">
          <div class="entete-panneau">
            <h2 class="titre-panneau mb-1">Dernière discussion</h2>
            <p class="texte-secondaire mb-0">Votre conversation la plus récente.</p>
          </div>

          <div v-if="!derniereDiscussion" class="etat-vide text-center py-5">
            <i class="bi bi-chat-square-text fs-1"></i>
            <p class="fw-bold mb-1 mt-3">Aucune discussion</p>
            <p class="text-muted small mb-3">Contactez un créateur pour démarrer un échange.</p>
            <BButton class="bouton-cours rounded-pill fw-bold px-4" @click="ouvrirMessages">
              Messages
            </BButton>
          </div>

          <div v-else class="resume-discussion">
            <div class="d-flex align-items-center gap-3 mb-4">
              <span class="avatar-discussion">
                {{ initialesUtilisateur(interlocuteurDiscussion(derniereDiscussion)) }}
              </span>

              <div class="min-w-0">
                <h3 class="nom-discussion mb-1">
                  {{ nomUtilisateur(interlocuteurDiscussion(derniereDiscussion)) }}
                </h3>
                <p class="texte-secondaire small mb-0">
                  {{ texteDateMessage(dernierMessage(derniereDiscussion)) }}
                </p>
              </div>
            </div>

            <div class="dernier-message">
              <p class="mb-0">
                {{ texteDernierMessage(derniereDiscussion) }}
              </p>
            </div>

            <BButton class="bouton-cours rounded-pill fw-bold w-100 mt-4 py-2" @click="ouvrirDiscussion">
              <i class="bi bi-chat-left-text me-2"></i>
              Ouvrir la discussion
            </BButton>
          </div>
        </section>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import BandeauTitre from '@/components/BandeauTitre.vue'
import Chargement from '@/components/Chargement.vue'
import { useAccueil } from '@/composables/useAccueil.js'
import { couleurDifficulte } from '@/utils/formatage.js'

const router = useRouter()

const {
  coursRecents,
  derniereDiscussion,
  chargement,
  erreur,
  chargerAccueil,
  memoriserDiscussionAccueil,
  dernierMessage,
  interlocuteurDiscussion,
  nomUtilisateur,
  initialesUtilisateur,
} = useAccueil()

onMounted(() => {
  chargerAccueil()
})

function ouvrirCatalogue()
{
  router.push({ name: 'catalogue' })
}

function ouvrirMessages()
{
  router.push({ name: 'messages' })
}

function ouvrirDiscussion()
{
  if (derniereDiscussion.value?.id)
  {
    memoriserDiscussionAccueil(derniereDiscussion.value.id)
  }

  ouvrirMessages()
}

function ouvrirCoursRecent(cours)
{
  if (cours.routeName === 'cours')
  {
    router.push({ name: 'cours', params: { id: cours.id } })
    return
  }

  router.push({ name: 'apercuCours', params: { idCours: cours.id } })
}

function nomCreateur(cours)
{
  const createur = cours.createur

  if (!createur)
  {
    return 'Createur'
  }

  return `${createur.prenom || ''} ${createur.nom || ''}`.trim() || createur.pseudo || createur.email || 'Createur'
}

function categoriesAffichees(cours)
{
  return (cours.categories || []).slice(0, 2)
}

function texteDernierMessage(discussion)
{
  return dernierMessage(discussion)?.contenu || 'Nouvelle discussion'
}

function texteDateMessage(message)
{
  if (!message?.dateCreation)
  {
    return 'Aucun message envoye'
  }

  return formaterDateConsultation(message.dateCreation)
}

function formaterDateConsultation(dateBrute)
{
  if (!dateBrute)
  {
    return ''
  }

  return new Date(dateBrute).toLocaleString('fr-FR', {
    day: '2-digit',
    month: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  })
}
</script>

<style scoped>
.page-accueil {
  max-width: 1680px;
  margin: 0 auto;
}

.panneau-accueil {
  height: 100%;
  min-height: 520px;
  border: 1px solid rgba(74, 44, 89, 0.12);
  border-radius: 12px;
  background: #fff;
  box-shadow: 0 0.5rem 1.5rem rgba(33, 37, 41, 0.08);
  padding: 1.25rem;
}

.entete-panneau {
  padding-bottom: 1rem;
  border-bottom: 1px solid rgba(33, 37, 41, 0.08);
  margin-bottom: 1rem;
}

.titre-panneau {
  color: #2f2536;
  font-size: 1.1rem;
  font-weight: 800;
}

.texte-secondaire {
  color: #6c757d;
}

.bouton-lien {
  color: #4a2c59;
  white-space: nowrap;
}

.bouton-lien:hover {
  color: #a17c5b;
}

.grille-cours-recents {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 1rem;
}

.carte-cours-recent {
  display: flex;
  flex-direction: column;
  min-height: 320px;
  overflow: hidden;
  border: 1px solid rgba(33, 37, 41, 0.08);
  border-radius: 10px;
  background: #fff;
}

.bandeau-cours-recent {
  min-height: 70px;
  background: linear-gradient(135deg, #a17c5b 0%, #4a2c59 100%);
  padding: 0.75rem;
}

.contenu-cours-recent {
  display: flex;
  flex: 1;
  flex-direction: column;
  padding: 1rem;
}

.titre-cours-recent {
  color: #2f2536;
  font-size: 1.05rem;
  font-weight: 800;
  line-height: 1.25;
  overflow-wrap: anywhere;
}

.description-cours-recent {
  display: -webkit-box;
  min-height: 3rem;
  overflow: hidden;
  color: #495057;
  font-size: 0.92rem;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
}

.badges-cours {
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
}

.badge-cours,
.badge-categorie,
.badge-prive {
  border: 0;
}

.badge-debutant { background-color: #e2f0e5 !important; color: #2d6a4f !important; }
.badge-intermediaire { background-color: #fff3cd !important; color: #856404 !important; }
.badge-avance { background-color: #fad2e1 !important; color: #842047 !important; }
.badge-defaut { background-color: #f8f9fa !important; color: #6c757d !important; }

.badge-categorie {
  background-color: rgba(74, 44, 89, 0.1) !important;
  color: #4a2c59 !important;
  font-weight: 700;
}

.badge-prive {
  background: #fff;
  color: #4a2c59;
}

.bouton-cours {
  background-color: #4a2c59 !important;
  border: 2px solid #4a2c59 !important;
  color: #fff !important;
}

.bouton-cours:hover {
  background-color: #b8a854 !important;
  border-color: #b8a854 !important;
  color: #4a2c59 !important;
}

.panneau-discussion {
  display: flex;
  flex-direction: column;
}

.resume-discussion {
  display: flex;
  flex: 1;
  flex-direction: column;
  justify-content: center;
}

.avatar-discussion {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex: 0 0 auto;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: #4a2c59;
  color: #fff;
  font-weight: 800;
}

.nom-discussion {
  color: #2f2536;
  font-size: 1.15rem;
  font-weight: 800;
  overflow-wrap: anywhere;
}

.dernier-message {
  border: 1px solid rgba(74, 44, 89, 0.12);
  border-radius: 10px;
  background: rgba(74, 44, 89, 0.04);
  color: #495057;
  padding: 1rem;
  overflow-wrap: anywhere;
}

.etat-vide {
  color: #6c757d;
}

.min-w-0 {
  min-width: 0;
}

@media (max-width: 1199.98px) {
  .grille-cours-recents {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 767.98px) {
  .panneau-accueil {
    min-height: auto;
  }

  .entete-panneau {
    align-items: flex-start !important;
    flex-direction: column;
  }

  .grille-cours-recents {
    grid-template-columns: 1fr;
  }
}
</style>
