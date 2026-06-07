<template>
    <BNavbar sticky="top" toggleable="lg" type="dark" class="menu-principal px-4 shadow-sm">

        <BNavbarToggle target="nav-collapse" class="bouton-menu-principal"></BNavbarToggle>

        <BNavbarBrand href="/" class="brand-logo d-flex align-items-center gap-2 fw-bold m-0 text-white">
            <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="d-inline-block align-top" />
            <span class="fs-4">OWLearning</span>
        </BNavbarBrand>

        <BCollapse id="nav-collapse" is-nav>

            <BNavbarNav class="fs-5 fw-bold custom-links gap-3 align-items-center me-auto mt-4 mt-lg-0">

                <BNavItem to="/" exact-active-class="lien-actif">Accueil</BNavItem>
                <BNavItem to="/messages" exact-active-class="lien-actif">Messages</BNavItem>

                <BNavItemDropdown text="Mes cours" no-caret>
                    <BDropdownItem href="/catalogue">Liste des cours</BDropdownItem>
                    <BDropdownItem v-if="roleUtilisateur === 'eleve'" href="/mes-cours-inscrits">Mes cours inscrits</BDropdownItem>
                    <BDropdownItem v-if="roleUtilisateur === 'createur'" href="/cours/creation">Créer un cours</BDropdownItem>
                    <BDropdownItem v-if="roleUtilisateur === 'createur'" href="/mes-cours-publies">Gérer mes cours</BDropdownItem>
                </BNavItemDropdown>

            </BNavbarNav>

            <BNavbarNav class="align-items-center gap-3 ms-auto mt-3 mt-lg-0">

                <BNavItemDropdown right no-caret>

                    <template #button-content>
                        <div class="avatar-menu bg-white text-dark rounded-circle d-flex justify-content-center align-items-center fw-bold">
                            {{ initialesUtilisateur }}
                        </div>
                    </template>

                    <div class="text-center px-3 fw-bold"> {{ pseudoUtilisateur || 'Mon compte' }} </div>

                    <BDropdownDivider />

                    <BDropdownItem href="/profil" @click.prevent="allerProfil">Mon Profil</BDropdownItem>
                    <BDropdownItem href="#" @click.prevent="seDeconnecter" class="text-danger">Déconnexion</BDropdownItem>
                </BNavItemDropdown>

            </BNavbarNav>

        </BCollapse>

    </BNavbar>
</template>

<script setup>
    import { BCollapse, BDropdownItem, BNavbar, BNavbarBrand, BNavbarToggle, BNavItemDropdown } from 'bootstrap-vue-next';
    import { useRouter } from 'vue-router'
    import {ref, onMounted, computed } from 'vue';

    const router = useRouter()
    const roleUtilisateur = ref('');
    const pseudoUtilisateur = ref('');

    const initialesUtilisateur = computed(() => {
      if (!pseudoUtilisateur.value) {
        return '?';
      }

      return pseudoUtilisateur.value
        .split(' ')
        .map(mot => mot.charAt(0).toUpperCase())
        .slice(0, 2)
        .join('');
    });

    onMounted(() => {
      const utilisateur = getUtilisateurConnecte();
      roleUtilisateur.value = utilisateur.role;
      pseudoUtilisateur.value = utilisateur.pseudo;
    })

    function allerProfil() {
        router.push('/profil');
    }

    function seDeconnecter () {
        console.log("Déconnexion...")
        localStorage.removeItem('token');
        router.push('/connexion');
    }

    function getUtilisateurConnecte() {
      const token = localStorage.getItem('token');
      if(!token) { return { role: null, pseudo: '' }; }
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return {
          role: payload.role,
          pseudo: payload.pseudo || '',
        };
      }
      catch(e){
        console.error("Token invalide", e);
        return { role: null, pseudo: '' };
      }
    }
</script>
