export function formaterDate(dateBrute) {
  if (!dateBrute) { return '' };
  const date = new Date(dateBrute);
  return date.toLocaleDateString('fr-FR');
}

export function couleurDifficulte(difficulte) {
  if (!difficulte) return 'badge-defaut';

  switch (difficulte.toUpperCase()) {
    case 'DEBUTANT':
      return 'badge-debutant';
    case 'INTERMEDIAIRE':
      return 'badge-intermediaire';
    case 'AVANCE':
      return 'badge-avance';
    default:
      return 'badge-defaut';
  }
}
