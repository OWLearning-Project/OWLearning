import { ref } from 'vue';
import { coursService } from "@/api/coursService.js";
import { useFiltresCours } from "@/composables/useFiltresCours.js";

export function useCoursPublies(){
  const lesCours = ref([]);
  const chargement = ref(true);
  const erreur = ref('');

  const filtres = useFiltresCours(lesCours);

  async function initialiserPage(){
    chargement.value = true;
    try{
      lesCours.value = await coursService.getCoursPublies();
    } catch (e) {
      console.error("Erreur de chargement des cours publiés :", e);
      erreur.value = 'Chargement des cours publiés impossible';
    } finally {
      chargement.value = false;
    }
  }

  function nombreEleves(cours) {
    return cours.eleves?.length || 0;
  }

  return {
    chargement,
    erreur,
    initialiserPage,
    nombreEleves,
    ...filtres
  }
}
