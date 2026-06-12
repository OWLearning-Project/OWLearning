export function recupererUtilisateurConnecte()
{
  const token = localStorage.getItem('token')

  if (!token)
  {
    return { id: null, role: null }
  }

  try
  {
    const payload = JSON.parse(atob(token.split('.')[1]))

    return {
      id: Number(payload.id),
      role: payload.role,
    }
  } catch (e)
  {
    console.error('Token invalide', e)
    return { id: null, role: null }
  }
}
