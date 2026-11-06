# 🏳️ Quiz des drapeaux

Une petite application Android pour apprendre les drapeaux du monde.
Elle est faite en **Kotlin** avec une interface en **XML**.

**Auteur :** Yasmine Naouar

## Ce que fait l'application

Le joueur répond à 5 questions. Pour chaque question :

1. un drapeau s'affiche ;
2. le joueur choisit le bon pays parmi 3 réponses ;
3. l'application dit tout de suite si c'est juste ;
4. le score et la barre de progression se mettent à jour.

À la fin, le score final s'affiche et on peut cliquer sur **Rejouer**.

## Détails techniques

- Une seule activité : `MainActivity`
- Écran fait dans `activity_main.xml` (sans Jetpack Compose)
- Questions écrites directement dans le code Kotlin
- 5 images enregistrées dans le projet (pas besoin d'Internet)
- Les boutons sont bloqués après une réponse
- L'écran défile sur les petits téléphones

## Lancer le projet

1. Ouvrir le dossier dans Android Studio.
2. Attendre la fin de la synchronisation Gradle.
3. Cliquer sur **Run** avec un émulateur ou un téléphone branché.

Le compte rendu du TP se trouve dans [`compte-rendu/compte-rendu.md`](compte-rendu/compte-rendu.md).
