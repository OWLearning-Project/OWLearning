<template>
    <BContainer class="py-5">
        <BNavbar class="bg-white rounded-4 shadow-sm py-2 mb-5 justify-content-center">
            <BNavbarBrand tag="div" class="mb-0 p-0 w-100 text-center">
                <h1 class="fw-bold fs-2 m-0 mt-2" style="color:#4a2c59;">Catalogue</h1>
                <p class="text-muted fs-6 mt-1 mb-2">Découvrez les différents cours disponibles</p>
                <hr class="w-25 mx-auto mb-2" style="border-color: #a17c5b; border-width: 3px; opacity: 1;">
            </BNavbarBrand>
        </BNavbar>


        <BRow>
        <BCol lg="3" md="4" class="mb-4">
                <BCard class="border-0 shadow-sm" style="border-radius: 12px; position: sticky; top: 20px;">
                    <h5 class="fw-bold mb-4" style="color: #4a2c59;">
                        <i class="bi bi-funnel-fill me-2"></i>Filtres
                    </h5>

                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Rechercher un cours</label>
                        <BFormInput v-model="recherche" placeholder="Ex: Java..." class="rounded-pill px-3"></BFormInput>
                    </div>

                    <hr class="text-muted mb-4">

                    <div class="mb-4">
                        <label class="form-label fw-bold small text-muted mb-3">Difficulté</label>
                        <BFormCheckboxGroup v-model="difficultes" :options="difficultesPossibles" stacked class="checkboxes-custom"></BFormCheckboxGroup>
                    </div>

                    <div class="mb-2" v-if="categoriesPossibles.length > 0">
                        <label class="form-label fw-bold small text-muted mb-3">Catégories</label>
                        <BFormCheckboxGroup v-model="categories" :options="categoriesPossibles" stacked class="checkboxes-custom"></BFormCheckboxGroup>
                    </div>

                </BCard>
        </BCol>

        <BCol lg="9" md="8">

                <div v-if="chargement" class="text-center my-5">
                    <BSpinner style="width: 3rem; height: 3rem; color: #a17c5b;" label="Chargement..."></BSpinner>
                </div>

                <BAlert v-else-if="erreur !== ''" variant="danger" show>
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ erreur }}
                </BAlert>

                <BAlert v-else-if="coursFiltres.length === 0" variant="info" show class="border-0 shadow-sm" style="border-radius: 12px;">
                    <i class="bi bi-search me-2"></i> Aucun cours ne correspond à votre recherche
                </BAlert>

                <div v-else>
                    <BRow class="g-4">
                        <BCol cols="12" md="6" lg="4" v-for="cours in coursAffiches" :key="cours.id">

                            <BCard class="h-100 shadow-sm carte-cours border-0" no-body>
                                <div class="bandeau-deco"></div>
                                    <BCardBody class="d-flex flex-column">

                                        <BCardTitle class="fw-bold fs-5 mt-2" style="color: #4a2c59;">
                                            {{ cours.titre }}
                                        </BCardTitle>

                                        <BCardText class="text-muted small mb-4">
                                            <i class="bi bi-person-circle me-1"></i> {{ cours.createur.prenom }} {{ cours.createur.nom }}
                                        </BCardText>

                                        <div class="mb-3">
                                            <BBadge pill :class="couleurDifficulte(cours.difficulte)" class="me-2 mb-1 shadow-sm border-0 fw-bold px-3 py-2">
                                                <i class="bi bi-bar-chart-fill me-1"></i> {{ cours.difficulte }}
                                            </BBadge>
                                            <BBadge pill class="badge-categorie border-0 me-1 mb-1 px-3 py-2" v-for="categorie in cours.categories" :key="categorie">
                                                {{ categorie }}
                                            </BBadge>
                                        </div>

                                    <BButton class="mt-auto fw-bold bouton-cours w-100 rounded-pill py-2">
                                        Consulter
                                    </BButton>
                                </BCardBody>

                                <BCardFooter class="bg-white border-top-0 text-end pb-3">
                                    <small class="text-muted" style="font-size: 0.75rem;">
                                        <i class="bi bi-calendar3 me-1"></i> {{ formaterDate(cours.dateCreation) }}
                                    </small>
                                </BCardFooter>
                            </BCard>
                        </BCol>
                    </BRow>

                    <div class="d-flex justify-content-center mt-5" v-if="coursFiltres.length > coursParPage">
                        <BPagination
                            v-model="pageCourante"
                            :total-rows="coursFiltres.length"
                            :per-page="coursParPage"
                            pills
                            class="pagination-personnalisee"
                        ></BPagination>
                    </div>
                </div>

            </BCol>
        </BRow>
    </BContainer>
</template>

<script setup>
    import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

    const lesCours = ref([]);
    const chargement = ref(true);
    const erreur = ref('');

    const pageCourante = ref(1);
    const coursParPage = ref(6);

    const recherche = ref('');

    const difficultes = ref([]);
    const categories = ref([]);

    const difficultesPossibles = [
        { text: 'Débutant', value: 'DEBUTANT' },
        { text: 'Intermédiaire', value: 'INTERMEDIAIRE' },
        { text: 'Avancé', value: 'AVANCE' }
    ];

    const categoriesPossibles = computed(() => {
        const lesCategories = new Set();
        lesCours.value.forEach(cours => {
            if (cours.categories) {
                cours.categories.forEach(categorie => lesCategories.add(categorie));
            }
        });
        return Array.from(lesCategories).map(categorie => ( { text: categorie, value: categorie } ))
    })

    const coursAffiches = computed(() => {
        const debut = (pageCourante.value - 1) * coursParPage.value;
        const fin = debut + coursParPage.value;
        return coursFiltres.value.slice(debut, fin);
    });

    const coursFiltres = computed(() => {
        pageCourante.value = 1;

        return lesCours.value.filter(cours => {

            const texte = recherche.value.toLowerCase();
            const texteValide = !recherche.value ||
                                cours.titre.toLowerCase().includes(texte) ||
                                cours.createur.nom.toLowerCase().includes(texte) ||
                                cours.createur.prenom.toLowerCase().includes(texte);

            const difficulte = cours.difficulte.toUpperCase();
            const difficulteValide = difficultes.value.length === 0 || difficultes.value.includes(difficulte);

            const categorieValide = categories.value.length === 0 || cours.categories.some( categorie => categories.value.includes(categorie));

            return texteValide && difficulteValide && categorieValide;
        });
    });

    onMounted(() => {
        chargerCours();
    })

    async function chargerCours() {
        try {
            const token = localStorage.getItem('token');

            const reponse = await axios.get("http://localhost:8080/api/cours", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            lesCours.value = reponse.data
        }
        catch (error) {
            console.error("Erreur de chargement :", error);
            erreur.value = "Chargement du catalogue impossible";
        }
        finally {
            chargement.value = false;
        }
    }

    function formaterDate (dateBrute) {
        if (!dateBrute) return '';
        const date = new Date(dateBrute);
        return date.toLocaleDateString('fr-FR');
    };

    function couleurDifficulte(difficulte) {
        if(!difficulte) { return 'badge-defaut' }
        switch(difficulte.toUpperCase()) {
            case 'DEBUTANT': return 'badge-debutant';
            case 'INTERMEDIAIRE': return 'badge-intermediaire';
            case 'AVANCE': return 'badge-avance';
            default: return 'badge-defaut';
        }
    }
</script>

<style scoped>
    .carte-cours {
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        border-radius: 12px;
        overflow: hidden;
    }

    .carte-cours:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
    }

    .bandeau-deco {
        height: 80px;
        background: linear-gradient(135deg, #a17c5b 0%, #4a2c59 100%);
    }

    .badge-debutant {
        background-color: #e2f0e5 !important;
        color: #2d6a4f !important;
    }

    .badge-intermediaire {
        background-color: #fff3cd !important;
        color: #856404 !important;
    }

    .badge-avance {
        background-color: #fad2e1 !important;
        color: #842047 !important;
    }

    .badge-defaut {
        background-color: #f8f9fa !important;
        color: #6c757d !important;
    }

    .badge-categorie {
        background-color: rgba(74, 44, 89, 0.1) !important;
        color: #4A2C59 !important;
        font-weight: 600;
    }

    .bouton-cours {
        background-color: #4A2C59 !important;
        border: 2px solid #4A2C59 !important;
        color: white !important;
        transition: all 0.3s ease;
    }

    .bouton-cours:hover {
        background-color: #B8A854 !important;
        border-color: #B8A854 !important;
        color: #4A2C59 !important;
        transform: scale(1.02);
    }

    .pagination {
        --bs-pagination-active-bg: #4a2c59;
        --bs-pagination-active-border-color: #4a2c59;
        --bs-pagination-color: #4a2c59;
        --bs-pagination-hover-color: #b8a854;
    }

    :deep(.checkboxes-custom .form-check-input:checked) {
        background-color: #4a2c59 !important;
        border-color: #4a2c59 !important;
    }

    :deep(.checkboxes-custom .form-check-input:focus) {
        box-shadow: 0 0 0 0.25rem rgba(74, 44, 89, 0.25) !important;
        border-color: #4a2c59 !important;
    }
</style>
