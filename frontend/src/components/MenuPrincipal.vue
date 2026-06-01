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
                    <BDropdownItem href="#">Mes cours inscrits</BDropdownItem>
                </BNavItemDropdown>

            </BNavbarNav>

            <BNavbarNav class="align-items-center gap-3 ms-auto mt-3 mt-lg-0">
                
                <BNavItem href="#">
                    <i class="bi bi-gear custom-icon"></i>
                </BNavItem>

                <BNavItemDropdown right no-caret>

                    <template #button-content>
                        <div class="avatar-menu bg-white text-dark rounded-circle d-flex justify-content-center align-items-center">
                            <i class="profil-icon bi bi-person text-secondary"></i>
                        </div>
                    </template>

                    <div class="text-center"> Nom Prénom </div>

                    <BDropdownDivider />

                    <BDropdownItem href="#">Mon Profil</BDropdownItem>
                    <BDropdownItem href="#" @click.prevent="seDeconnecter" class="text-danger">Déconnexion</BDropdownItem>
                </BNavItemDropdown>

            </BNavbarNav>

        </BCollapse>
        
    </BNavbar>
</template>

<script setup>
    import { BCollapse, BDropdown, BDropdownItem, BNavbar, BNavbarBrand, BNavbarToggle, BNavItemDropdown } from 'bootstrap-vue-next';
    import { useRouter } from 'vue-router'

    const router = useRouter()

    const seDeconnecter = () => {
        console.log("Déconnexion...")
        localStorage.removeItem('token');
        router.push('/connexion');
    }
</script>
