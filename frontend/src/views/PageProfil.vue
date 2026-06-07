<template>
  <BContainer class="py-5 min-vh-100">
    <Chargement v-if="chargement" />

    <BAlert
      v-else-if="erreur !== '' && !utilisateur"
      variant="danger"
      show
      class="alerte-catalogue-vide border-0 shadow-sm"
    >
      <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
    </BAlert>

    <template v-else-if="utilisateur">
      <BandeauTitre titre="Mon profil" :sous-titre="utilisateur.email" />

      <BRow class="g-4">
        <BCol lg="4">
          <BCard class="carte-profil border-0 shadow-sm h-100">
            <div class="text-center">
              <div class="avatar-profil mx-auto mb-3 d-flex align-items-center justify-content-center">
                <span class="fw-bold">{{ initialesUtilisateur }}</span>
              </div>

              <h2 class="titre-carte-cours fs-4 fw-bold mb-1">
                {{ utilisateur.prenom }} {{ utilisateur.nom }}
              </h2>
              <BBadge pill class="badge-categorie border-0 px-3 py-2 mt-2">
                {{ libelleRole }}
              </BBadge>
            </div>

            <hr class="my-4">

            <dl class="mb-0">
              <div class="mb-3">
                <dt class="text-muted small">Pseudo</dt>
                <dd class="fw-bold mb-0">{{ utilisateur.pseudo || 'Non renseigné' }}</dd>
              </div>

              <div class="mb-3">
                <dt class="text-muted small">Inscription</dt>
                <dd class="fw-bold mb-0">{{ formaterDate(utilisateur.dateInscription) }}</dd>
              </div>

              <div>
                <dt class="text-muted small">Dernière activité</dt>
                <dd class="fw-bold mb-0">{{ formaterDate(utilisateur.derniereActivite) || 'Non disponible' }}</dd>
              </div>
            </dl>
          </BCard>
        </BCol>

        <BCol lg="8">
          <BCard class="carte-profil border-0 shadow-sm">
            <BForm @submit.prevent="modifierProfil">
              <BAlert
                v-if="erreur !== ''"
                variant="danger"
                show
                class="alerte-catalogue-vide border-0"
              >
                {{ erreur }}
              </BAlert>

              <BAlert
                v-if="messageSucces !== ''"
                variant="success"
                show
                class="alerte-catalogue-vide border-0"
              >
                {{ messageSucces }}
              </BAlert>

              <BRow>
                <BCol md="6">
                  <BFormGroup label="Nom" label-class="fw-bold" class="mb-4">
                    <BFormInput :model-value="utilisateur.nom" disabled></BFormInput>
                  </BFormGroup>
                </BCol>

                <BCol md="6">
                  <BFormGroup label="Prénom" label-class="fw-bold" class="mb-4">
                    <BFormInput :model-value="utilisateur.prenom" disabled></BFormInput>
                  </BFormGroup>
                </BCol>
              </BRow>

              <BFormGroup label="Pseudo" label-class="fw-bold" class="mb-4">
                <BFormInput v-model="pseudo"></BFormInput>
              </BFormGroup>

              <BFormGroup label="Email" label-class="fw-bold" class="mb-4">
                <BFormInput v-model="email" type="email" required></BFormInput>
              </BFormGroup>

              <template v-if="estEleve">
                <BRow>
                  <BCol md="4">
                    <BFormGroup label="Âge" label-class="fw-bold" class="mb-4">
                      <BFormInput v-model.number="age" type="number" min="0" required></BFormInput>
                    </BFormGroup>
                  </BCol>

                  <BCol md="8">
                    <BFormGroup label="Niveau d'étude" label-class="fw-bold" class="mb-4">
                      <BFormInput v-model="niveauEtude" required></BFormInput>
                    </BFormGroup>
                  </BCol>
                </BRow>
              </template>

              <div class="d-flex justify-content-end gap-3">
                <BButton
                  type="button"
                  variant="outline-secondary"
                  class="rounded-pill fw-bold px-4"
                  @click="retourArriere"
                >
                  Annuler
                </BButton>

                <BButton
                  type="submit"
                  class="bouton-cours rounded-pill fw-bold px-4"
                  :disabled="sauvegardeEnCours"
                >
                  <template v-if="sauvegardeEnCours">
                    <BSpinner small class="me-2"></BSpinner>
                    Enregistrement...
                  </template>
                  <template v-else>
                    Enregistrer
                  </template>
                </BButton>
              </div>
            </BForm>
          </BCard>
        </BCol>
      </BRow>
    </template>
  </BContainer>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import BandeauTitre from '@/components/BandeauTitre.vue'
import Chargement from '@/components/Chargement.vue'
import { useProfil } from '@/composables/useProfil.js'
import { formaterDate } from '@/utils/formatage.js'

const router = useRouter()

const {
  utilisateur,
  pseudo,
  email,
  age,
  niveauEtude,
  chargement,
  sauvegardeEnCours,
  erreur,
  messageSucces,
  estEleve,
  chargerProfil,
  modifierProfil,
} = useProfil()

const initialesUtilisateur = computed(() =>
{
  const prenom = utilisateur.value?.prenom?.charAt(0) || ''
  const nom = utilisateur.value?.nom?.charAt(0) || ''

  return `${prenom}${nom}`.toUpperCase() || '?'
})

const libelleRole = computed(() => estEleve.value ? 'Élève' : 'Créateur')

onMounted(() => {
  chargerProfil()
})

function retourArriere()
{
  router.back()
}
</script>

<style scoped>
.carte-profil {
  border-radius: 12px;
}

.avatar-profil {
  width: 88px;
  height: 88px;
  border-radius: 50%;
  background-color: rgba(74, 44, 89, 0.12);
  color: #4a2c59;
  font-size: 1.9rem;
}
</style>
