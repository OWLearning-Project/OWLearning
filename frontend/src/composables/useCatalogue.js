import {ref, computed} from 'vue';
import {coursService} from "@/api/coursService.js";
import {useFiltresCours} from "@/composables/useFiltresCours.js";

export function useCatalogue() {
  const lesCours = ref([]);
  const chargement = ref(true);
  const erreur = ref('');

  const filtres = useFiltresCours(lesCours);

  async function chargerCours() {
    chargement.value = true;
    erreur.value = '';
    try {
      lesCours.value = await coursService.getTousLesCours()
    } catch (e) {
      console.error('Erreur de chargement :', e);
      erreur.value = 'Chargement du catalogue impossible';
    } finally {
      chargement.value = false;
    }
  }

  return {
    chargement,
    erreur,
    chargerCours,
    ...filtres
  }
}
