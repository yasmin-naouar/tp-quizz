# Compte rendu du TP Android : un quiz avec des images

| | |
|---|---|
| **Nom** | Yasmine Naouar |
| **Sujet du quiz** | Les drapeaux des pays |
| **Langages** | Kotlin et XML |
| **Écran principal** | `MainActivity` |

---

## Le but de l'application

J'ai fait un quiz simple avec 5 questions sur les drapeaux.
Pour chaque question, on voit un drapeau et on choisit le bon pays parmi 3 boutons.
L'application dit si la réponse est bonne ou pas et compte les points.
À la fin, on voit son score et on peut rejouer.

## Comment l'écran est construit

Tout l'écran est dans le fichier `activity_main.xml`. Il y a une seule activité et je n'ai pas utilisé Jetpack Compose.

Voici les éléments que j'ai mis :

- **TextView** : pour le titre, la question, le numéro de la question et le score.
- **ImageView** : pour montrer le drapeau. J'ai mis `fitCenter` pour que l'image ne soit pas déformée.
- **ProgressBar** (horizontale) : pour voir où on en est dans le quiz.
- **3 Button** : un bouton pour chaque réponse.
- **Bouton « Suivant »** et **bouton « Rejouer »**.
- **ScrollView** : pour pouvoir descendre si l'écran du téléphone est petit.

## Comment les questions sont rangées

J'ai créé une `data class Question`. Elle garde :

1. le texte de la question ;
2. l'image (un `Int` qui pointe vers le drawable) ;
3. les 3 réponses possibles (`Array<String>`) ;
4. le numéro de la bonne réponse ;
5. une petite description de l'image pour l'accessibilité.

Les 5 questions sont dans un tableau en Kotlin.
Les images sont dans `res/drawable` : France, Japon, Allemagne, Suisse et Italie. Ce sont des dessins vectoriels.
L'application n'utilise ni Internet ni base de données.

## Ce qui se passe quand on clique

- Je parcours les 3 boutons avec `forEachIndexed` pour leur donner une action.
- Quand on clique, la fonction `checkAnswer(index)` regarde si la réponse est la bonne :
  - bonne réponse → on gagne 1 point ;
  - mauvaise réponse → le score ne change pas.
- Après un clic, les boutons sont bloqués. On ne peut donc pas gagner des points en cliquant plusieurs fois.
- La barre de progression avance après chaque réponse, bonne ou mauvaise.
- Le bouton « Suivant » ne marche pas tant qu'on n'a pas répondu.
- Après la 5e question, les boutons de réponse sont cachés et le score final s'affiche.
- Le bouton « Rejouer » remet tout à zéro.

## Les tests

| Ce que je fais | Ce qui doit se passer |
|---|---|
| Je lance le quiz | Question 1, score 0/5, 3 réponses visibles |
| Je donne une bonne réponse | +1 point et la barre avance |
| Je donne une mauvaise réponse | Le score reste pareil et la barre avance |
| Je clique plusieurs fois | Les boutons sont bloqués, pas de points en plus |
| Je passe à la question suivante | Nouveau drapeau, le score est gardé |
| J'arrive à la fin | Le score final s'affiche (par exemple 5/5 ou 0/5) |
| Je clique sur « Rejouer » | Retour à la question 1 avec 0 point |
| J'utilise un petit écran | On peut faire défiler l'écran sans problème |

**Résultats obtenus :** (à compléter)

## Captures d'écran

**Une question avec son image**

![Une question avec son image](captures/Une%20question%20avec%20son%20image,.png)

**Une bonne réponse**

![Une bonne réponse](captures/Une%20bonne%20r%C3%A9ponse.png)

**Une mauvaise réponse**

![Une mauvaise réponse](captures/Une%20mauvaise%20r%C3%A9ponse.png)

**L'écran de fin avec le score**

![Écran final avec le score](captures/L%E2%80%99%C3%A9cran%20final%20avec%20le%20score%20obtenu..png)
