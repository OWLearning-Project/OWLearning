<template>
  <BContainer fluid class="page-messages py-5 min-vh-100">
    <BandeauTitre titre="Messages" sous-titre="Discussions avec les createurs" />

    <BAlert
      v-if="erreur !== ''"
      variant="danger"
      show
      class="alerte-catalogue-vide border-0 shadow-sm"
    >
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
    </BAlert>

    <Chargement v-if="chargement" />

    <BRow v-else class="g-4">
      <BCol xl="3" lg="4">
        <section class="panneau-messagerie">
          <div class="entete-panneau d-flex align-items-center justify-content-between gap-3">
            <h2 class="titre-panneau mb-0">Discussions</h2>
            <BBadge pill class="badge-messagerie">{{ discussionsTriees.length }}</BBadge>
          </div>

          <div v-if="discussionsTriees.length === 0" class="etat-vide text-center py-5">
            <i class="bi bi-chat-square-text fs-1"></i>
            <p class="fw-bold mb-1 mt-3">Aucune discussion</p>
            <p class="text-muted small mb-0">Contactez un createur depuis l'annuaire.</p>
          </div>

          <div v-else class="liste-discussions">
            <button
              v-for="discussion in discussionsTriees"
              :key="discussion.id"
              type="button"
              class="element-discussion"
              :class="{ active: discussionSelectionnee?.id === discussion.id }"
              @click="selectionnerDiscussion(discussion)"
            >
              <span class="avatar-message">
                {{ initialesUtilisateur(interlocuteurDiscussion(discussion)) }}
              </span>

              <span class="contenu-discussion">
                <span class="nom-discussion">
                  {{ nomUtilisateur(interlocuteurDiscussion(discussion)) }}
                </span>
                <span class="dernier-message">
                  {{ dernierMessage(discussion)?.contenu || 'Nouvelle discussion' }}
                </span>
              </span>
            </button>
          </div>
        </section>
      </BCol>

      <BCol xl="6" lg="8">
        <section class="panneau-messagerie panneau-conversation">
          <template v-if="discussionSelectionnee">
            <div class="entete-conversation">
              <div class="avatar-message avatar-conversation">
                {{ initialesUtilisateur(interlocuteurDiscussion(discussionSelectionnee)) }}
              </div>

              <div>
                <h2 class="titre-panneau mb-1">
                  {{ nomUtilisateur(interlocuteurDiscussion(discussionSelectionnee)) }}
                </h2>
                <p class="text-muted small mb-0">Discussion active</p>
              </div>
            </div>

            <div ref="filMessages" class="fil-messages">
              <Chargement v-if="chargementMessages" texte="Chargement des messages..." />

              <div v-else-if="messages.length === 0" class="etat-vide text-center py-5">
                <i class="bi bi-send fs-1"></i>
                <p class="fw-bold mb-1 mt-3">Aucun message</p>
                <p class="text-muted small mb-0">Envoyez le premier message.</p>
              </div>

              <div v-else class="d-flex flex-column gap-3">
                <div
                  v-for="message in messages"
                  :key="message.id"
                  class="ligne-message"
                  :class="{ 'message-connecte': estMessageAuteurConnecte(message) }"
                >
                  <div class="bulle-message">
                    <p class="mb-2">{{ message.contenu }}</p>
                    <span class="date-message">{{ formaterDateMessage(message.dateCreation) }}</span>
                  </div>
                </div>
              </div>
            </div>

            <BForm class="zone-envoi" @submit.prevent="envoyerMessageAvecPieceJointe">
              <input
                ref="inputFichier"
                type="file"
                class="d-none"
                accept="image/*,.pdf,.doc,.docx,.txt"
                @change="selectionnerFichier"
              />

              <BButton
                type="button"
                class="bouton-piece-jointe"
                :disabled="envoiEnCours"
                @click="ouvrirSelecteurFichier"
              >
                <i class="bi bi-plus-lg"></i>
              </BButton>

              <BFormTextarea
                v-model="nouveauMessage"
                rows="2"
                max-rows="4"
                class="champ-message"
                placeholder="Votre message"
                :disabled="envoiEnCours"
                @keydown.enter.exact.prevent="envoyerMessageAvecPieceJointe"
              ></BFormTextarea>

              <BButton
                type="submit"
                class="bouton-cours bouton-envoyer"
                :disabled="envoiEnCours || (nouveauMessage.trim() === '' && !fichierSelectionne)"
              >
                <BSpinner v-if="envoiEnCours" small class="me-2"></BSpinner>
                <i v-else class="bi bi-send-fill me-2"></i>
                Envoyer
              </BButton>

              <div v-if="fichierSelectionne" class="fichier-selectionne">
                <i class="bi bi-paperclip me-2"></i>
                <span>{{ fichierSelectionne.name }}</span>
                <button type="button" class="bouton-retirer-fichier" @click="retirerFichier">
                  <i class="bi bi-x-lg"></i>
                </button>
              </div>
            </BForm>
          </template>

          <div v-else class="conversation-vide text-center">
            <i class="bi bi-chat-dots fs-1"></i>
            <h2 class="titre-panneau mt-3 mb-2">Selectionnez une discussion</h2>
            <p class="text-muted mb-0">Ou contactez un createur depuis l'annuaire.</p>
          </div>
        </section>
      </BCol>

      <BCol xl="3" lg="12">
        <section class="panneau-messagerie">
          <div class="entete-panneau">
            <h2 class="titre-panneau mb-0">Annuaire</h2>
          </div>

          <BInputGroup class="mb-3">
            <BInputGroupText>
              <i class="bi bi-search"></i>
            </BInputGroupText>
            <BFormInput
              v-model="rechercheCreateur"
              placeholder="Rechercher"
              aria-label="Rechercher un createur"
            ></BFormInput>
          </BInputGroup>

          <div v-if="createursFiltres.length === 0" class="etat-vide text-center py-5">
            <i class="bi bi-person-lines-fill fs-1"></i>
            <p class="fw-bold mb-1 mt-3">Aucun utilisateur</p>
            <p class="text-muted small mb-0">Essayez une autre recherche.</p>
          </div>

          <div v-else class="liste-createurs">
            <article
              v-for="utilisateur in createursFiltres" :key="utilisateur.id"
              class="element-createur"
            >
              <div class="infos-createur">
                <span class="avatar-message">
                  {{ initialesUtilisateur(utilisateur) }}
                </span>
                <div class="min-w-0">
                  <span class="nom-createur">{{ nomUtilisateur(utilisateur) }}</span>
                  <span class="email-createur">{{ utilisateur.email }}</span>
                </div>
              </div>

              <BButton
                variant="outline-primary"
                class="bouton-contacter rounded-pill px-3 py-1"
                size="sm"
                @click="contacterUtilisateur(utilisateur)"
              >
                Contacter
              </BButton>
            </article>
          </div>
        </section>
      </BCol>
    </BRow>
  </BContainer>
</template>

<script setup>
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import BandeauTitre from '@/components/BandeauTitre.vue'
import Chargement from '@/components/Chargement.vue'
import { useMessagerie } from '@/composables/useMessagerie.js'
import { messagerieClient } from '@/api/messagerieClient.js'
const {
  createursFiltres,
  discussionsTriees,
  discussionSelectionnee,
  messages,
  rechercheUtilisateur,
  nouveauMessage,
  chargement,
  chargementMessages,
  envoiEnCours,
  actionCreateurId,
  erreur,
  chargerMessagerie,
  contacterCreateur,
  contacterUtilisateur,
  selectionnerDiscussion,
  envoyerMessage,
  fermerMessagerie,
  interlocuteurDiscussion,
  nomUtilisateur,
  initialesUtilisateur,
  dernierMessage,
  estMessageAuteurConnecte,
} = useMessagerie()

const inputFichier = ref(null)
const fichierSelectionne = ref(null)
const filMessages = ref(null)

async function scrollerEnBas()
{
  await nextTick()

  if (filMessages.value)
  {
    filMessages.value.scrollTop = filMessages.value.scrollHeight
  }
}

watch(messages, () => {
  scrollerEnBas()
}, { deep: true })

watch(discussionSelectionnee, () => {
  scrollerEnBas()
})

function ouvrirSelecteurFichier()
{
  inputFichier.value?.click()
}

function selectionnerFichier(event)
{
  fichierSelectionne.value = event.target.files[0] || null
}

function retirerFichier()
{
  fichierSelectionne.value = null

  if (inputFichier.value)
  {
    inputFichier.value.value = ''
  }
}

async function envoyerMessageAvecPieceJointe()
{
  try
  {
    let ressourceId = null

    if (fichierSelectionne.value)
    {
      const ressource = await messagerieClient.uploaderRessource(fichierSelectionne.value)
      ressourceId = ressource.id
    }

    await envoyerMessage(ressourceId)
    await scrollerEnBas()
    retirerFichier()
  }
  catch (e)
  {
    console.error("Erreur lors de l'envoi de la pièce jointe :", e)
    alert("La pièce jointe n'a pas pu être envoyée. Vérifie les droits ou la connexion.")
  }
}

onMounted(async () => {
  await chargerMessagerie()
  scrollerEnBas()
})

onBeforeUnmount(() => {
  fermerMessagerie()
})

function formaterDateMessage(dateBrute)
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
.page-messages {
  max-width: 1680px;
  margin: 0 auto;
}

.panneau-messagerie {
  height: 100%;
  min-height: 360px;
  border: 1px solid rgba(74, 44, 89, 0.12);
  border-radius: 12px;
  background: #fff;
  box-shadow: 0 0.5rem 1.5rem rgba(33, 37, 41, 0.08);
  padding: 1.25rem;
}

.panneau-conversation {
  display: flex;
  flex-direction: column;
  min-height: 640px;
}

.entete-panneau,
.entete-conversation {
  padding-bottom: 1rem;
  border-bottom: 1px solid rgba(33, 37, 41, 0.08);
  margin-bottom: 1rem;
}

.entete-conversation {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.titre-panneau {
  color: #2f2536;
  font-size: 1.1rem;
  font-weight: 800;
}

.badge-messagerie {
  background-color: #4a2c59;
}

.liste-discussions,
.liste-createurs {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.element-discussion {
  display: grid;
  grid-template-columns: auto minmax(0, 1fr);
  gap: 0.75rem;
  align-items: center;
  width: 100%;
  border: 1px solid transparent;
  border-radius: 10px;
  background: #f8f9fa;
  padding: 0.75rem;
  text-align: left;
  transition: border-color 0.2s ease, background-color 0.2s ease;
}

.element-discussion:hover,
.element-discussion.active {
  border-color: rgba(74, 44, 89, 0.32);
  background: rgba(74, 44, 89, 0.06);
}

.avatar-message {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex: 0 0 auto;
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background: #4a2c59;
  color: #fff;
  font-size: 0.9rem;
  font-weight: 800;
}

.avatar-conversation {
  width: 52px;
  height: 52px;
  font-size: 1rem;
}

.contenu-discussion {
  min-width: 0;
}

.nom-discussion,
.dernier-message,
.email-createur {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.nom-discussion,
.nom-createur {
  color: #2f2536;
  font-weight: 800;
}

.nom-createur {
  font-size: 1.05rem;
  line-height: 1.2;
  overflow-wrap: anywhere;
}

.dernier-message,
.email-createur {
  color: #6c757d;
  font-size: 0.88rem;
}

.fil-messages {
  flex: 1;
  min-height: 0;
  max-height: 56vh;
  overflow-y: auto;
  padding: 0.5rem 0.25rem 1rem;
}

.ligne-message {
  display: flex;
  justify-content: flex-start;
}

.ligne-message.message-connecte {
  justify-content: flex-end;
}

.bulle-message {
  max-width: min(78%, 520px);
  border-radius: 14px;
  background: #f1f3f5;
  color: #212529;
  padding: 0.8rem 0.95rem;
  overflow-wrap: anywhere;
}

.message-connecte .bulle-message {
  background: #4a2c59;
  color: #fff;
}

.date-message {
  display: block;
  color: #6c757d;
  font-size: 0.75rem;
  text-align: right;
}

.message-connecte .date-message {
  color: rgba(255, 255, 255, 0.78);
}

.zone-envoi {
  display: grid;
  grid-template-columns: auto minmax(0, 1fr) auto;
  gap: 0.75rem;
  align-items: end;
  border-top: 1px solid rgba(33, 37, 41, 0.08);
  padding-top: 1rem;
}

.bouton-piece-jointe {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  border: none;
  background: #f1f3f5;
  color: #4a2c59;
  font-weight: 800;
}

.bouton-piece-jointe:hover {
  background: rgba(74, 44, 89, 0.12);
  color: #4a2c59;
}

.fichier-selectionne {
  grid-column: 2 / 4;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  max-width: 100%;
  border-radius: 10px;
  background: rgba(74, 44, 89, 0.08);
  color: #4a2c59;
  padding: 0.55rem 0.75rem;
  font-size: 0.9rem;
  font-weight: 700;
}

.fichier-selectionne span {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.bouton-retirer-fichier {
  margin-left: auto;
  border: none;
  background: transparent;
  color: #4a2c59;
  font-weight: 800;
}

.champ-message {
  resize: none;
  border-radius: 12px;
}

.bouton-envoyer {
  min-width: 126px;
  border-radius: 999px;
  font-weight: 800;
}

.element-createur {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  align-items: center;
  gap: 1rem;
  border: 1px solid rgba(33, 37, 41, 0.08);
  border-radius: 10px;
  padding: 0.85rem;
}

.infos-createur {
  display: grid;
  grid-template-columns: auto minmax(0, 1fr);
  align-items: center;
  gap: 0.75rem;
  min-width: 0;
}

.bouton-contacter {
  flex: 0 0 auto;
  border-color: #4a2c59;
  color: #4a2c59;
  white-space: nowrap;
}

.bouton-contacter:hover {
  background: #4a2c59;
  border-color: #4a2c59;
  color: #fff;
}

.conversation-vide {
  margin: auto;
  color: #6c757d;
}

.etat-vide {
  color: #6c757d;
}

.min-w-0 {
  min-width: 0;
}

@media (max-width: 767.98px) {
  .panneau-conversation {
    min-height: 520px;
  }

  .zone-envoi {
    grid-template-columns: auto 1fr;
  }

  .bouton-envoyer {
    grid-column: 1/3;
  }

  .fichier-selectionne {
    grid-column: 1/3;
  }

  .bulle-message {
    max-width: 88%;
  }

  .element-createur {
    grid-template-columns: 1fr;
    align-items: stretch;
  }

  .bouton-contacter {
    width: 100%;
  }
}
</style>
