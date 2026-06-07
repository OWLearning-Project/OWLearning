import {ref, computed} from 'vue';

export function useFiltresCours(lesCours){
  const pageCourante = ref(1);
  const coursParPage = ref(6);

  const recherche = ref('');
  const difficultes = ref([]);
  const categories = ref([]);
  const afficherCoursPrives = ref(false);

  const difficultesPossibles = [
    { text: 'Débutant', value: 'DEBUTANT' },
    { text: 'Intermédiaire', value: 'INTERMEDIAIRE' },
    { text: 'Avancé', value: 'AVANCE' },
  ];

  const categoriesPossibles = computed(() => {
    const lesCategories = new Set();
    lesCours.value.forEach((cours) => {
      if (cours.categories) {
        cours.categories.forEach((categorie) => lesCategories.add(categorie));
      }
    });
    return Array.from(lesCategories).map((categorie) => ({ text: categorie, value: categorie }));
  });

  const coursFiltres = computed(() => {
    pageCourante.value = 1;

    return lesCours.value.filter((cours) => {
      const texte = recherche.value.toLowerCase();
      const texteValide = !recherche.value ||
        cours.titre.toLowerCase().includes(texte) ||
        cours.createur.nom.toLowerCase().includes(texte) ||
        cours.createur.prenom.toLowerCase().includes(texte);

      const difficulteValide = difficultes.value.length === 0 || difficultes.value.includes(cours.difficulte.toUpperCase());
      const categorieValide = categories.value.length === 0 || cours.categories.some((c) => categories.value.includes(c));
      const priveValide = !afficherCoursPrives.value || cours.estPrive === true;

      return texteValide && difficulteValide && categorieValide && priveValide;
    });
  });

  const coursAffiches = computed(() => {
    const debut = (pageCourante.value - 1) * coursParPage.value;
    return coursFiltres.value.slice(debut, debut + coursParPage.value);
  });

  return {
    pageCourante,
    coursParPage,
    recherche,
    difficultes,
    categories,
    afficherCoursPrives,
    difficultesPossibles,
    categoriesPossibles,
    coursFiltres,
    coursAffiches
  }
}
