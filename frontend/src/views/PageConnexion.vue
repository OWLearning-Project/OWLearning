<template>
    <BContainer fluid class="vh-100 d-flex flex-column p-0 bg-light">
        <header class="entete-auth sticky-top p-2 text-white d-flex align-items-center shadow-sm">
    <BContainer fluid class="min-vh-100 d-flex flex-column p-0 bg-light">
        <header class="sticky-top p-2 text-white d-flex align-items-center shadow-sm" style="background-color: #a17c5b;">
            <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="fs-4 me-2" />
            <h1 class="m-0 fs-4 fw-bold">OWLearning</h1>
        </header>

        <BContainer class="flex-grow-1 flex-column d-flex align-items-center mt-4 mb-5">

            <h1 class="my-4 text-center fw-bold" style="color:#4a2c59">Bienvenue sur OWLearning !</h1>
            <h1 class="titre-auth m-3 p-5 text-center fw-bold">Bienvenue sur OWLearning !</h1>

            <BCard class="carte-auth shadow w-100 border-0">

                <BAlert v-if="messageErreur !== ''" variant="danger" show class="alerte-auth m-3 text-center shadow border-0">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ messageErreur }}
                </BAlert>
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

                            <BInputGroupText @click="voirMotDePasse = !voirMotDePasse" class="icone-mdp-toggle bg-white border-start-0">
                                <i :class="voirMotDePasse ? 'icone-mdp bi bi-eye-slash' : 'icone-mdp bi bi-eye'"></i>
                            </BInputGroupText>
                        </BInputGroup>
                    </BFormGroup>


                    <div class="d-grid gap-3 mt-4">

                        <BButton type="submit" size="lg" class="bouton-auth-principal fw-bold">
                            <template v-if="chargement">
                                <BSpinner small class="me-2"></BSpinner>
                                Connexion en cours...
                            </template>

                            <template v-else >
                                Connexion
                            </template>
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
    import axios from 'axios';

    const router = useRouter();

    const email = ref('');
    const motDePasse = ref('');
    const voirMotDePasse = ref(false);
    const chargement = ref(false);

    const messageErreur = ref('');

    async function seConnecter () {
        chargement.value = true;
        messageErreur.value = '';
        console.log("Tentative de connexion avec : ", email.value);
        try {
            const reponse = await axios.post('http://localhost:8080/api/authentification/connexion', {
                email: email.value,
                motDePasse: motDePasse.value
            });

            const tokenJwt = reponse.data;
            localStorage.setItem('token', tokenJwt);

            console.log("Connexion réussie");
            router.push('/');
        }
        catch (erreur) {
            if (erreur.response && erreur.response.status === 401) { messageErreur.value = "Email ou mot de passe incorrect"; }
            else { messageErreur.value = "Une erreur est survenue durant la connexion"; }
            console.error("Erreur de connexion :", erreur);
        }
        finally {
            chargement.value = false;
        }
    }

    function inscription () {
        console.log("Redirection vers la page d'inscription")
        router.push('/inscription');
    }
</script>
