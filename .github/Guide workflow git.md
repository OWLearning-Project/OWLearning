# 🚀 Guide de Contribution & Workflow Git

Ce document définit les standards de développement de l'équipe. L'objectif est de garantir un code stable, un historique propre et d'éviter les conflits sur les branches principales.

---

## 1. 🌳 Architecture des Branches (Gitflow)

Nous utilisons une structure stricte pour sécuriser la production.

| Branche | Rôle | 🔒 Règles de Sécurité |
| :--- | :--- | :--- |
| **`main`** | **Production** (Stable) | ⛔️ **PUSH INTERDIT**. Seule une fusion validée depuis `develop` est autorisée. |
| **`develop`** | **Intégration** (Test) | ⛔️ **PUSH INTERDIT**. Passage obligé par une **Pull Request** (PR). |
| **`feature/...`** | **Développement** | ✅ Zone de travail libre. C'est ici que vous codez. |
| **`fix/...`** | **Correction** | Pour corriger un bug non critique sur `develop`. |
| **`hotfix/...`** | **Urgence Prod** | Branche partant de `main` pour corriger un bug critique en prod. |

---

## 2. 🔄 Le Workflow : Comment travailler ?

Suivez ces étapes scrupuleusement pour chaque ticket/tâche.

### Étape 1 : Démarrer proprement
Ne partez jamais d'une vieille version. Mettez-vous à jour avant de créer votre branche.

```bash
# Se placer sur develop et récupérer les mises à jour
git checkout develop
git pull origin develop

# Créer sa branche de travail
git checkout -b feature/ma-nouvelle-tache

```

### Étape 2 : Coder et Commiter (Liberté totale)

Sur votre branche `feature`, vous êtes chez vous.

* Faites des commits réguliers (`WIP`, `sauvegarde`, `test`).
* 💡 **Note :** Ce n'est pas grave si l'historique est "sale" sur cette branche, il sera nettoyé automatiquement à la fin.

### Étape 3 : Ouvrir la Pull Request (PR)

Une fois le code terminé et **testé en local** :

1. Poussez votre branche : `git push origin feature/ma-nouvelle-tache`
2. Allez sur GitHub/GitLab et créez une **Pull Request**.
* **Base :** `develop`
* **Compare :** `feature/ma-nouvelle-tache`


3. Remplissez le modèle de description (Tests, Impact BDD, etc.).

### Étape 4 : Validation & Nettoyage (Le SQUASH)

Une fois la PR validée par l'équipe (Code Review OK) :

1. **NE CLIQUEZ PAS** sur le bouton "Merge" classique.
2. Cliquez sur la flèche ▼ et choisissez **"Squash and merge"**.
3. **Pourquoi ?** Cela fusionne tous vos commits de travail en **1 seul commit propre** sur `develop`.

---

## 3. 📝 Convention des Commits

Lors du "Squash and merge" final, le message doit respecter la convention **Conventional Commits**.

**Format :** `type(portée): verbe à l'impératif et description courte`

### Les Types Autorisés :

* ✨ **`feat`** : Nouvelle fonctionnalité (ex: `feat(chapitre): ajout entité et relation`)
* 🐛 **`fix`** : Correction de bug (ex: `fix(auth): correction du token invalide`)
* ♻️ **`refactor`** : Amélioration du code sans changer le comportement
* 🔧 **`chore`** : Config, dépendances, build (ex: `chore: ajout dépendance postgres`)
* 🧪 **`test`** : Ajout ou modification de tests
* 📚 **`docs`** : Documentation uniquement

---

## 4. ☕️ Standards Java & Spring Boot

### Gestion des Entités JPA

* **Relations Bidirectionnelles** : Toujours utiliser des méthodes "helper" pour garantir la cohérence des deux côtés de la relation.
```java
public void addRessource(Ressource ressource) {
    this.ressources.add(ressource);
    ressource.setChapitre(this);
}

```


* **Listes** : Toujours initialiser les listes pour éviter les `NullPointerException`.
```java
private List<Ressource> ressources = new ArrayList<>();

```



### Qualité du Code

* ❌ Pas de `System.out.println`. Utilisez `@Slf4j` et `log.info()`.
* ❌ Pas de code commenté inutile.
* ✅ Les tests unitaires (`mvn test`) doivent passer avant toute PR.

---

## 5. 🆘 Résolution de problèmes

### "Git refuse mon push !" (Protected Branch)

Si vous voyez l'erreur `remote: error: GH006: Protected branch update failed`, c'est que vous essayez d'écrire directement sur `main` ou `develop`.
👉 **Solution :** Créez une branche `feature`, déplacez vos modifs dessus, et faites une PR.

### "J'ai des conflits lors du merge"

Cela arrive si `develop` a évolué pendant votre travail.

1. `git checkout develop` puis `git pull`
2. `git checkout feature/ma-tache`
3. `git merge develop`
4. Réglez les conflits dans votre IDE.
5. `git push` (Votre PR se mettra à jour automatiquement).
