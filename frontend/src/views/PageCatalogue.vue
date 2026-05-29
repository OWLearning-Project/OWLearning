<template>
    <div class="p-2 justify-content-center">
        <h2>Catalogue de cours</h2>

        <BContainer class="mb-4">
            <h3>Retrouvez la liste des différents cours disponibles</h3>

            <div v-if="chargement" class="text-center my-5">
                <BSpinner style="width: 3rem; height: 3rem; color: #a17c5b;" label="Chargement..."></BSpinner>
                <p class="mt-3 text-muted fw-bold">Chargement du catalogue...</p>
            </div>
            
            <BAlert v-else-if="erreur !== ''" variant="danger" show>
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                {{ erreur }}
            </BAlert>

            <BRow v-else class="g-4"><BCol cols="12" md="6" lg="4" v-for="cours in lesCours" :key="cours.id">
                
                <BCard class="h-100 shadow-sm carte-cours border-0" no-body>
                    <div class="bandeau-deco"></div>

                    <BCardBody class="d-flex flex-column">
                        <div class="mb-2">
                            <BBadge :variant="couleurDifficulte(cours.difficulte)" class="me-2 mb-1 shadow-sm">
                                <i class="bi bi-bar-chart-fill me-1"></i>
                                {{ cours.difficulte }}
                            </BBadge>
                            <BBadge :variant="light" text-variant="dark" class="border me-1 mb-1" v-for="categorie in cours.categories" :key="categorie">
                                {{ categorie }}
                            </BBadge>
                        </div>

                        <BCardTitle class="fw-bold fs-5 mt-2" style="color: #4a2c59;">
                            {{ cours.titre }}
                        </BCardTitle>

                        <BCardText class="text-muted small mb-4">
                            <i class="bi bi-person-circle me-1"></i>
                            {{ cours.createur.prenom }} {{ cours.createur.nom }}
                        </BCardText>

                        <BButton variant="outline-dark" class="mt-auto fw-bold bouton-cours w-100">
                            Consulter
                        </BButton>
                    </BCardBody>

                    <BCardFooter class="bg-white border-top-0 text-end pb-3">
                        <small class="text-muted" style="font-size: 0.75rem;">
                            <i class="bi bi-calendar3 me-1"></i>
                            {{ formaterDate(cours.dateCreation) }}
                        </small>
                    </BCardFooter>
                    
                </BCard>
            </BCol></BRow>
        </BContainer>
            
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const lesCours = ref([]);
    const chargement = ref(true);
    const erreur = ref('');

    const champsTableau = [
        { key: 'titre', label: 'Titre du cours' },
        { key: 'createur', label: 'Créateur' },
        { key: 'dateCreation', label: 'Date de création' },
        { key: 'difficulte', label: 'Difficulté' },
        { key: 'categories', label: 'Catégorie' } 
    ];

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
        if(!difficulte) { return 'secondary' }
        switch(difficulte.toUpperCase()) {
            case 'DEBUTANT': return 'success';
            case 'INTERMEDIAIRE': return 'warning';
            case 'AVANCE': return 'danger';
            default: return 'primary';
        }
    }

</script>

<style>
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
        height: 120px;
        background: linear-gradient(135deg, #a17c5b 0%, #4a2c59 100%);
    }

    .bouton-cours:hover {
        background-color: #f6c05d;
        color: black !important;
        border-color: #f6c05d;
    }
</style>