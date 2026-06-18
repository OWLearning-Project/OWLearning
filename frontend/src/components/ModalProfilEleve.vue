<template>
  <BModal
    v-model="estOuvert"
    title="Profil de l'étudiant"
    centered
    no-footer
    header-bg-variant="light"
  >
    <div v-if="eleve" class="py-3">
      <div class="d-flex align-items-center mb-4 pb-3 border-bottom border-light-subtle">
        <BAvatar
          class="me-3 shadow-sm"
          size="4rem"
          style="background-color: #b8a854 !important; color: white; font-weight: bold"
          :text="initiales"
        />
        <div>
          <h4 class="mb-1 fw-bold" style="color: #4a2c59">{{ eleve.prenom }} {{ eleve.nom }}</h4>
          <BBadge
            v-if="eleve.pseudo !== null || eleve.pseudo !== ''"
            variant="light"
            class="text-muted border border-light-subtle px-2 py-1"
          >
            @{{ eleve.pseudo }}
          </BBadge>
        </div>
      </div>
      <div class="d-flex flex-column gap-3">
        <div>
          <small class="text-muted d-block fw-bold" style="font-size: 0.75rem">Adresse Email</small>
          <span>{{ eleve.email }}</span>
        </div>
      </div>

      <div class="d-flex align-items-center" v-if="eleve.age">
        <div>
          <small class="text-muted d-block fw-bold" style="font-size: 0.75rem">Âge</small>
          <span>{{ eleve.age }} ans</span>
        </div>
      </div>

      <div class="d-flex align-items-center" v-if="eleve.niveauEtude">
        <div>
          <small class="text-muted d-block fw-bold" style="font-size: 0.75rem"
            >Niveau d'étude</small
          >
          <span>{{ eleve.niveauEtude }}</span>
        </div>
      </div>

      <div class="d-flex align-items-center" v-if="eleve.dateInscription">
        <div>
          <small class="text-muted d-block fw-bold" style="font-size: 0.75rem"
            >Membre depuis le</small
          >
          <span>{{ formaterDate(eleve.dateInscription) }}</span>
        </div>
      </div>

      <div v-if="eleve.id !== utilisateurConnecteId" class="mt-4 pt-3 border-top border-light-subtle d-flex justify-content-end">
        <BButton
          style="background-color: #4a2c59; border-color: #4a2c59; color: white;"
          class="rounded-pill px-4 shadow-sm btn-contact"
          @click="contacterEleve(eleve.id)"
        >
          <i class="bi bi-chat-dots-fill me-2"></i> Contacter l'étudiant
        </BButton>
      </div>
    </div>
  </BModal>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { messagerieClient } from '@/api/messagerieClient.js'

const props = defineProps({
  eleve: {
    type: Object,
    default: null,
  },
})

const estOuvert = defineModel()
const router = useRouter()

const initiales = computed(() => {
  if (!props.eleve) {
    return '??'
  }
  return props.eleve.prenom.charAt(0).toUpperCase() + props.eleve.nom.charAt(0).toUpperCase()
})

const utilisateurConnecteId = computed(() => {
  const utilisateur = recupererUtilisateurConnecte()
  return utilisateur.id
})

function formaterDate(date) {
  if (!date) {
    return 'Date inconnue'
  }
  const dateFormatee = new Date(date)
  return dateFormatee.toLocaleDateString('fr-FR', {
    day: 'numeric',
    month: 'long',
    year: 'numeric',
  })
}

async function contacterEleve(idEleve) {
  try {
    await messagerieClient.demarrerDiscussionAvecUtilisateur(idEleve);

    estOuvert.value = false;

    router.push({
      name: 'messages',
      query: { utilisateurId: idEleve }
    });
  } catch (error) {
    console.error("Erreur lors de la création de la discussion :", error);
  }
}

function recupererUtilisateurConnecte()
{
  const token = localStorage.getItem('token')

  if (!token)
  {
    return { id: null, role: null }
  }

  try
  {
    const payload = JSON.parse(atob(token.split('.')[1]))

    return {
      id: Number(payload.id),
      role: payload.role,
    }
  } catch (e)
  {
    console.error('Token invalide', e)
    return { id: null, role: null }
  }
}
</script>

<style scoped></style>
