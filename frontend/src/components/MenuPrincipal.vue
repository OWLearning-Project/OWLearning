<template>
    <BNavbar sticky="top" toggleable="lg" type="dark" style="background-color: #a17c5b;" class="px-4 shadow-sm">
        
        <BNavbarToggle target="nav-collapse" style="color: white;"></BNavbarToggle>

        <BNavbarBrand href="/" class="brand-logo d-flex align-items-center gap-2 fw-bold m-0 text-white" style="z-index: 10;">
            <img src="../assets/OwleEcharpe.svg" alt="Logo" width="70" height="70" class="d-inline-block align-top" />
            <span class="fs-4">OWLearning</span>
        </BNavbarBrand>

        <BCollapse id="nav-collapse" is-nav>

            <BNavbarNav class="fs-5 fw-bold custom-links gap-3 align-items-center me-auto mt-4 mt-lg-0">

                <BNavItem to="/" exact-active-class="lien-actif">Accueil</BNavItem>
                <BNavItem to="/messages" exact-active-class="lien-actif">Messages</BNavItem>

                <BNavItemDropdown text="Mes cours" no-caret>
                    <BDropdownItem href="#">Liste des cours</BDropdownItem>
                    <BDropdownItem href="#">Mes cours inscrits</BDropdownItem>
                </BNavItemDropdown>

            </BNavbarNav>

            <BNavbarNav class="align-items-center gap-3 ms-auto mt-3 mt-lg-0">
                
                <BNavItem href="#">
                    <i class="bi bi-gear custom-icon"></i>
                </BNavItem>

                <BNavItemDropdown right no-caret>

                    <template #button-content>
                        <div class="bg-white text-dark rounded-circle d-flex justify-content-center align-items-center" style="width: 45px; height: 45px;">
                            <i class="bi bi-person text-secondary" style="font-size: 1.5rem;"></i>
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

<style scoped>
    :deep(.custom-links .nav-link) {
        color: rgba(255, 255, 255, 0.8) !important;
        transition: color 0.2s ease-in-out;
        position: relative;
        padding-bottom: 5px;
    }

    :deep(.custom-links .nav-link::after) {
        content: '';
        position: absolute;
        width: 0;
        height: 2px;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        background-color: rgba(255, 255, 255, 0.8);
        transition: width 0.3s ease-out;
    }

    :deep(.custom-links .lien-actif) {
        font-weight: 800;
    }

    :deep(.custom-links .lien-actif::after) {
        width: 100%;
    }

    :deep(.custom-links .nav-link:hover) {
        opacity: 0.8; 
    }

    .custom-icon {
        font-size: 1.5rem;
        color: rgba(255, 255, 255, 0.8);
        transition: transform 0.3s ease;
    }

    .custom-icon:hover {
        transform: rotate(90deg);
    }

    @media(min-width: 992px) {
        .brand-logo {
            position: absolute !important;
            left: 50% !important;
            transform: translateX(-50%) !important;
        }
    }
</style>