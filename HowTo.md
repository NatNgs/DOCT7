Instruction pour utiliser le framework:

Placer les projets maven dans le dossier OriginalSrc (les dossier des projets doivent ce trouver dans ce dossier).

Les processeurs à exécuter doivent être renseignés dans le fichier processors.
Chaque ligne correspond à un jeu de test.
Les processeurs doivent être spécifiés séparés par des virgules seulement.

Pour utiliser un processeur suffie de mettre le nom de la class java sans l'extension.

Processeurs disponibles :
ArithmeticRandMutator
BooleanRandMutator
EmptyMutator
PrivacityRandMutator

Une fois les projets placés dans le dossier et les processeurs sélectionnés il suffit d'exécuter le script exec.sh pour lancer le framework.
Un rapport est alors généré au format html dans le dossier racine du framework sous le nom de Result.html


Pour ajouter des nouveau processeurs, il suffit de les ajouter dans le dossier MutationGenerator/src/main/java/fr/unice/polytech/devops/group7/processors
Il faut ensuite relancer le script install.sh pour mettre à jour les processeurs.


