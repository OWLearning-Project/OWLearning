<template>
    <BContainer class="py-5">
        <div v-if="chargement" class="text-center my-5">
            <BSpinner class="spinner-catalogue" label="Chargement..."></BSpinner>
        </div>

        <BAlert v-else-if="erreur !== ''" variant="danger" show class="alerte-catalogue-vide border-0 shadow-sm">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
        </BAlert>

        <template v-else-if="cours">
            <BNavbar class="bg-white rounded-4 shadow-sm py-2 mb-5 justify-content-center">
                <BNavbarBrand tag="div" class="mb-0 p-0 w-100 text-center">
                    <h1 class="titre-catalogue fw-bold fs-2 m-0 mt-2">Aperçu du cours</h1>
                    <p class="text-muted fs-6 mt-1 mb-2">{{ cours.titre }}</p>
                    <hr class="separateur-catalogue w-25 mx-auto mb-2">
                </BNavbarBrand>
            </BNavbar>

          <div class="d-flex justify-content-start mb-4">
            <BButton variant="link" class="bouton-retour-navigation p-2 text-decoration-none d-flex align-items-center gap-2"
                     @click="retourArriere"
                     title="Retour à la page précédente">
              <i class="bi bi-arrow-left fs-4"></i>
              <span class="fw-medium fs-5">Retour</span>
            </BButton>
          </div>
            <BRow class="g-4">
                <BCol lg="8">
                    <BCard class="carte-apercu-cours border-0 shadow-sm overflow-hidden" no-body>
                        <div class="bandeau-deco"></div>

                        <BCardBody>
                            <div class="d-flex flex-wrap gap-2 mb-3">
                                <BBadge pill :class="couleurDifficulte(cours.difficulte)" class="shadow-sm border-0 fw-bold px-3 py-2">
                                    <i class="bi bi-bar-chart-fill me-1"></i> {{ cours.difficulte }}
                                </BBadge>
                                <BBadge v-if="cours.estPrive" pill class="badge-categorie border-0 fw-bold px-3 py-2">
                                    <i class="bi bi-lock-fill me-1"></i> Privé
                                </BBadge>
                                <BBadge v-if="estInscrit" pill class="badge-inscrit border-0 fw-bold px-3 py-2">
                                    <i class="bi bi-check-circle-fill me-1"></i> Inscrit
                                </BBadge>
                            </div>

                            <h2 class="titre-carte-cours fw-bold mb-3">{{ cours.titre }}</h2>

                            <p class="text-muted mb-4">
                                <i class="bi bi-person-circle me-1"></i>
                                {{ cours.createur?.prenom }} {{ cours.createur?.nom }}
                            </p>

                            <p class="description-apercu-cours mb-4">
                                {{ cours.description || 'Aucune description disponible pour ce cours.' }}
                            </p>

                            <div class="mb-4" v-if="cours.categories && cours.categories.length > 0">
                                <h3 class="titre-section-apercu fs-5 fw-bold mb-3">Catégories</h3>
                                <BBadge pill class="badge-categorie border-0 me-2 mb-2 px-3 py-2" v-for="categorie in cours.categories" :key="categorie">
                                    {{ categorie }}
                                </BBadge>
                            </div>

                            <div>
                                <h3 class="titre-section-apercu fs-5 fw-bold mb-3">Programme</h3>

                                <div v-if="chapitres.length === 0" class="text-muted">
                                    Aucun chapitre n'est encore disponible.
                                </div>

                                <div v-else class="liste-chapitres-apercu">
                                    <div class="chapitre-apercu d-flex gap-3 align-items-start" v-for="(chapitre, index) in chapitres" :key="chapitre.id">
                                        <span class="numero-chapitre d-flex align-items-center justify-content-center fw-bold">
                                            {{ index + 1 }}
                                        </span>
                                        <div>
                                            <h4 class="fs-6 fw-bold mb-1">{{ chapitre.titre }}</h4>
                                            <p class="text-muted small mb-0">{{ chapitre.description }}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </BCardBody>
                    </BCard>
                </BCol>

                <BCol lg="4">
                    <BCard class="carte-action-cours border-0 shadow-sm">
                        <h2 class="titre-carte-cours fs-4 fw-bold mb-3">Votre accès</h2>

                        <div v-if="estInscrit" class="mb-4">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <span class="fw-bold text-muted small">Progression</span>
                                <span class="fw-bold">{{ progressionPourcent }}%</span>
                            </div>
                            <BProgress :value="progressionPourcent" :max="100" height="1rem" class="progression-cours"></BProgress>
                        </div>

                        <p v-else class="text-muted mb-4">
                            Inscrivez-vous pour suivre votre progression sur ce cours.
                        </p>

                        <BAlert v-if="messageAction !== ''" :variant="typeMessageAction" show class="alerte-catalogue-vide border-0">
                            {{ messageAction }}
                        </BAlert>

                        <BButton class="bouton-cours w-100 rounded-pill fw-bold py-2" :disabled="actionEnCours" @click="actionPrincipale">
                            <template v-if="actionEnCours">
                                <BSpinner small class="me-2"></BSpinner>
                                Traitement...
                            </template>
                            <template v-else>
                                {{ libelleAction }}
                            </template>
                        </BButton>
                    </BCard>
                </BCol>
            </BRow>
        </template>
    </BContainer>
</template>

<script setup>
    import axios from 'axios';
    import { computed, onMounted, ref } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();
    const idCours = Number(route.params.idCours);

    const cours = ref(null);
    const estInscrit = ref(false);
    const progression = ref(0);
    const chargement = ref(true);
    const actionEnCours = ref(false);
    const erreur = ref('');
    const messageAction = ref('');
    const typeMessageAction = ref('info');

    const headersAuth = computed(() => ({
        Authorization: `Bearer ${localStorage.getItem('token')}`
    }));

    const chapitres = computed(() => cours.value?.chapitres || []);

    const progressionPourcent = computed(() => {
        const valeur = Number(progression.value) || 0;
        const pourcentage = valeur <= 1 ? valeur * 100 : valeur;
        return Math.min(100, Math.max(0, Math.round(pourcentage)));
    });

    const libelleAction = computed(() => {
        if (estInscrit.value) { return 'Reprendre'; }
        return cours.value?.estPrive ? "Demander l'inscription" : "S'inscrire";
    });

    onMounted(() => {
        chargerApercuCours();
    });

    async function chargerApercuCours() {
        chargement.value = true;
        erreur.value = '';

        try {
            const [reponseCours, reponseInscriptions] = await Promise.all([
                axios.get(`http://localhost:8080/api/cours/${idCours}`, { headers: headersAuth.value }),
                axios.get('http://localhost:8080/api/cours/utilisateurs/inscriptions', { headers: headersAuth.value })
            ]);

            cours.value = reponseCours.data;
            estInscrit.value = reponseInscriptions.data.some(unCours => Number(unCours.id) === idCours);

            if (estInscrit.value) {
                await chargerProgression();
            }
        }
        catch (error) {
            console.error("Erreur de chargement de l'aperçu :", error);
            erreur.value = "Chargement de l'aperçu du cours impossible";
        }
        finally {
            chargement.value = false;
        }
    }

    async function chargerProgression() {
        try {
            const reponse = await axios.get(`http://localhost:8080/api/progression/${idCours}`, { headers: headersAuth.value });
            progression.value = reponse.data?.tauxProgression ?? 0;
        }
        catch (error) {
            console.warn('Progression indisponible :', error);
            progression.value = 0;
        }
    }

    async function actionPrincipale() {
        messageAction.value = '';

        if (estInscrit.value) {
            typeMessageAction.value = 'info';
            messageAction.value = "La page de cours n'est pas encore disponible.";
            return;
        }

        actionEnCours.value = true;

        try {
            await axios.post(`http://localhost:8080/api/inscription/etudiants/cours/${idCours}`, null, { headers: headersAuth.value });
            estInscrit.value = true;
            typeMessageAction.value = 'success';
            messageAction.value = cours.value?.estPrive ? "Demande d'inscription envoyée." : 'Inscription réussie.';
            await chargerProgression();
        }
        catch (error) {
            console.error("Erreur d'inscription :", error);
            typeMessageAction.value = 'danger';
            messageAction.value = "L'inscription au cours est impossible pour le moment.";
        }
        finally {
            actionEnCours.value = false;
        }
    }

    function couleurDifficulte(difficulte) {
        if (!difficulte) { return 'badge-defaut'; }
        switch (difficulte.toUpperCase()) {
            case 'DEBUTANT': return 'badge-debutant';
            case 'INTERMEDIAIRE': return 'badge-intermediaire';
            case 'AVANCE': return 'badge-avance';
            default: return 'badge-defaut';
        }
    }
    function retourArriere()
    {
      router.back();
    }
</script>
