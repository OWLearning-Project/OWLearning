import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {authClient} from "@/api/authClient.js";

export function useConnexion(){
  const router = useRouter();

  const email = ref('');
  const motDePasse = ref('');
  const chargement = ref(false);
  const messageErreur = ref('');

  async function seConnecter() {
    chargement.value = true;
    messageErreur.value = '';

    try {
      const tokenJwt = await authClient.connexion(email.value, motDePasse.value);

      localStorage.setItem('token', tokenJwt);
      router.push('/');

    } catch (erreur) {
      if (erreur.response && erreur.response.status === 401) {
        messageErreur.value = "Email ou mot de passe incorrect";
      } else {
        messageErreur.value = "Une erreur est survenue durant la connexion";
      }
    } finally {
      chargement.value = false;
    }
  }

  function allerAInscription() {
    router.push('/inscription');
  }

  return {
    email,
    motDePasse,
    chargement,
    messageErreur,
    seConnecter,
    allerAInscription
  }
}
