import {ref} from 'vue';
import {coursClient} from "@/api/coursClient.js";
import {useFiltresCours} from "@/composables/useFiltresCours.js";

export function useCoursInscrits() {
  const lesCours = ref([]);
  const tauxProgression = ref({});
  const chargement = ref(true);
  const erreur = ref('');

  const filtres = useFiltresCours(lesCours);

  async function initialiserPage() {
    chargement.value = true;
    try {
      lesCours.value = await coursClient.getCoursInscrits();

      for (const cours of lesCours.value) {
        try {
          tauxProgression.value[cours.id] = await coursClient.getProgressionCours(cours.id);
        } catch (e) {
          tauxProgression.value[cours.id] = 0;
        }
      }
    } catch (e) {
      console.error("Erreur chargement des cours :", e);
      erreur.value = 'Chargement des cours impossible';
    } finally {
      chargement.value = false;
    }
  }

  return {
    chargement,
    erreur,
    tauxProgression,
    initialiserPage,
    ...filtres
  }
}
