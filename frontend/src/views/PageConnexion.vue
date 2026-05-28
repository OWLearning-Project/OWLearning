<template>
    <BContainer fluid class="vh-100 d-flex flex-column p-0 bg-light">
        <header class="p-2 text-white d-flex align-items-center shadow-sm" style="background-color: #a17c5b;">
            <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="fs-4 me-2" />
            <h1 class="m-0 fs-4 fw-bold">OWLearning</h1>
        </header>

        <BContainer class="flex-grow-1 flex-column d-flex align-items-center mt-5">

            <h1 class="m-3 p-5 text-center fw-bold" style="color:#4a2c59">Bienvenue sur OWLearning !</h1>

            <BCard class="shadow w-100 border-0" style="max-width: 500px; border-radius: 16px;">

                <BForm @submit.prevent="seConnecter" class="p-3">

                    <BFormGroup label="Identifiant étudiant :" label-for="identifiant" class="mb-4 fw-bold">
                        <BFormInput id="identifiant" v-model="identifiant" size="lg"></BFormInput>
                    </BFormGroup>

                    <BFormGroup label="Mot de passe :" label-for="motdepasse" class="mb-4 fw-bold">
                        <BFormInput id="motdepasse" v-model="motDePasse" size="lg"></BFormInput>
                    </BFormGroup>

                    <div class="d-grid gap-3 mt-4">

                        <BButton type="submit" size="lg" style="background-color: #f6c05d; color: black; border: none;" class="fw-bold">
                            Connexion
                        </BButton>

                        <BButton @click="inscription" variant="outline-dark" size="lg" class="fw-bold">
                            Créer un compte
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

    const identifiant = ref('');
    const motDePasse = ref('');

    const seConnecter = () => {
        console.log("Connexion avec : ", identifiant.value, motDePasse.value);
        //appel api
    }

    const inscription = () => {
        console.log("Redirection vers la page d'inscription")
        //router.push('/inscription');
    }
</script>

<style lang="css" scoped>
</style>
