<template>
    <BContainer fluid class="flax-grow-1 d-flex flex-column p-0 bg-light">
        <header class="sticky-top p-2 text-white d-flex align-items-center shadow-sm" style="background-color: #a17c5b;">
            <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="fs-4 me-2" />
            <h1 class="m-0 fs-4 fw-bold">OWLearning</h1>
        </header>

        <BContainer class="flex-grow-1 flex-column d-flex align-items-center mt-5">

            <h1 class="m-3 p-5 text-center fw-bold" style="color:#4a2c59">Bienvenue sur OWLearning !</h1>

            <BCard class="shadow w-100 border-0 mb-5" style="max-width: 500px; border-radius: 16px;">

                <BForm @submit.prevent="sInscrire" class="p-3">

                    <BFormGroup class="mb-4 fw-bold">
                        <template #label>Nom <span class="text-danger">*</span> :</template>
                        <BFormInput id="nom" v-model="nom" size="lg" required></BFormInput>
                    </BFormGroup>

                    <BFormGroup class="mb-4 fw-bold">
                        <template #label>Prénom <span class="text-danger">*</span> :</template>
                        <BFormInput id="prenom" v-model="prenom" size="lg" required></BFormInput>
                    </BFormGroup>

                    <BFormGroup class="mb-4 fw-bold">
                        <template #label>Email <span class="text-danger">*</span> :</template>
                        <BFormInput id="email" type="email" v-model="email" size="lg" required></BFormInput>
                    </BFormGroup>

                    <BFormGroup class="mb-4 fw-bold">
                        <template #label>Mot de passe <span class="text-danger">*</span> :</template>
                        <BInputGroup size="lg">
                            <BFormInput id="motdepasse" :type="voirMotDePasse ? 'text' : 'password'" v-model="motDePasse" class="border-end-0" required></BFormInput>
                            
                            <BInputGroupText @click="voirMotDePasse = !voirMotDePasse" class="bg-white border-start-0" style="cursor: pointer;">
                                <i :class="voirMotDePasse ? 'bi bi-eye-slash' : 'bi bi-eye'" style="color: #4a2c59; font-size: 1.2rem;"></i>
                            </BInputGroupText>
                        </BInputGroup>
                    </BFormGroup>

                    <BFormGroup class="mb-4 fw-bold">
                        <template #label>Confirmer mot de passe <span class="text-danger">*</span> :</template>
                        <BInputGroup size="lg">
                            <BFormInput id="confirmation" :type="voirConfirmation ? 'text' : 'password'" v-model="confirmationMdp" class="border-end-0" required></BFormInput>
                            
                            <BInputGroupText @click="voirConfirmation = !voirConfirmation" class="bg-white border-start-0" style="cursor: pointer;">
                                <i :class="voirConfirmation ? 'bi bi-eye-slash' : 'bi bi-eye'" style="color: #4a2c59; font-size: 1.2rem;"></i>
                            </BInputGroupText>
                        </BInputGroup>
                    </BFormGroup>

                    <BFormGroup label="Je suis un(e) :" label-class="fw-bold" class="mb-4">
                        <BFormRadioGroup v-model="role" name="role-choix" required>
                            <BFormRadio value="CREATEUR" class="fw-normal">Créateur / Créatrice</BFormRadio>
                            <BFormRadio value="ELEVE" class="fw-normal">Élève</BFormRadio>
                        </BFormRadioGroup>
                    </BFormGroup>

                    <div class="d-grid gap-3 mt-4">
                        <BButton type="submit" size="lg" style="background-color: #f6c05d; color: black; border: none;" class="fw-bold">
                            S'inscrire
                        </BButton>

                        <hr>

                        <div class="text-center">
                            <span class="text-muted small fw-normal">Déjà un compte ?</span>
                        </div>
                        <BButton @click="connexion" variant="outline-dark" size="lg" class="fw-bold">
                            Se connecter
                        </BButton>
                    </div>

                </BForm>

            </BCard>

        </BContainer>

    </BContainer>

</template>

<script setup>
    import {ref} from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const email = ref('');
    const motDePasse = ref('');
    const confirmationMdp = ref('');
    const prenom = ref('');
    const nom = ref('');
    const role = ref('ELEVE');
    const voirMotDePasse = ref(false);
    const voirConfirmation = ref(false);

    function connexion () {
        console.log("Redirection vers la page d'inscription");
        router.push('/connexion');
    }

    function inscription () {
        console.log("Inscription en cours...");
    }
</script>{}

<style>

    .form-check-input:checked {
        background-color: #4a2c59 !important;
        border-color: #4a2c59 !important;
    }

    .form-check-input:focus {
        border-color: #4a2c59 !important;
        box-shadow: 0 0 0 0.25rem rgba(74, 44, 89, 0.25) !important;
    }
</style>
