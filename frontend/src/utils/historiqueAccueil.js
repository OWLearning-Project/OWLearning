const PREFIXE_STOCKAGE = 'owlearning'
const NOMBRE_MAX_COURS_RECENTS = 6

export function enregistrerCoursConsulte(cours, routeName = 'apercuCours')
{
  if (!cours?.id)
  {
    return
  }

  const coursRecent = {
    id: Number(cours.id),
    titre: cours.titre,
    description: cours.description,
    difficulte: cours.difficulte,
    categories: cours.categories || [],
    createur: cours.createur || null,
    dateCreation: cours.dateCreation,
    estPrive: Boolean(cours.estPrive),
    routeName,
    consulteLe: new Date().toISOString(),
  }

  const historique = recupererCoursConsultesRecents()
    .filter((coursHistorique) => Number(coursHistorique.id) !== coursRecent.id)

  localStorage.setItem(
    cleCoursRecents(),
    JSON.stringify([coursRecent, ...historique].slice(0, NOMBRE_MAX_COURS_RECENTS)),
  )
}

export function recupererCoursConsultesRecents()
{
  try
  {
    const historique = JSON.parse(localStorage.getItem(cleCoursRecents()) || '[]')

    return historique
      .filter((cours) => cours?.id)
      .sort((coursA, coursB) => new Date(coursB.consulteLe).getTime() - new Date(coursA.consulteLe).getTime())
  } catch (e)
  {
    console.error('Historique des cours invalide', e)
    localStorage.removeItem(cleCoursRecents())
    return []
  }
}

export function enregistrerDerniereDiscussionUtilisee(idDiscussion)
{
  if (!idDiscussion)
  {
    return
  }

  localStorage.setItem(cleDerniereDiscussion(), String(idDiscussion))
}

export function recupererDerniereDiscussionUtilisee()
{
  const idDiscussion = Number(localStorage.getItem(cleDerniereDiscussion()))
  return Number.isNaN(idDiscussion) || idDiscussion <= 0 ? null : idDiscussion
}

function cleCoursRecents()
{
  return `${PREFIXE_STOCKAGE}:cours-recents:${identifiantUtilisateur()}`
}

function cleDerniereDiscussion()
{
  return `${PREFIXE_STOCKAGE}:derniere-discussion:${identifiantUtilisateur()}`
}

function identifiantUtilisateur()
{
  const token = localStorage.getItem('token')

  if (!token)
  {
    return 'invite'
  }

  try
  {
    const payload = JSON.parse(atob(token.split('.')[1]))
    return payload.id || payload.email || 'invite'
  } catch (e)
  {
    console.error('Token invalide pour l historique accueil', e)
    return 'invite'
  }
}
