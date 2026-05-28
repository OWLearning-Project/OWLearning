<template>
    <BContainer fluid class="vh-100 d-flex flex-column p-0 bg-light">
        <header class="sticky-top p-2 text-white d-flex align-items-center shadow-sm" style="background-color: #a17c5b;">
            <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="fs-4 me-2" />
            <h1 class="m-0 fs-4 fw-bold">OWLearning</h1>
        </header>

        <BContainer class="flex-grow-1 flex-column d-flex align-items-center mt-5">

            <h1 class="m-3 p-5 text-center fw-bold" style="color:#4a2c59">Bienvenue sur OWLearning !</h1>

            <BCard class="shadow w-100 border-0" style="max-width: 500px; border-radius: 16px;">

                <BForm @submit.prevent="seConnecter" class="p-3">

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


                    <div class="d-grid gap-3 mt-4">

                        <BButton type="submit" size="lg" style="background-color: #f6c05d; color: black; border: none;" class="fw-bold">
                            Connexion
                        </BButton>

                        <hr>

                        <div class="text-center">
                            <span class="text-muted small fw-normal">Pas encore de compte ?</span>
                        </div>
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

    const email = ref('');
    const motDePasse = ref('');
    const voirMotDePasse = ref(false);

    const seConnecter = () => {
        console.log("Connexion avec : ", email.value);
        //appel api
    }

    const inscription = () => {
        console.log("Redirection vers la page d'inscription")
        router.push('/inscription');
    }
</script>

<style lang="css" scoped>
</style>
