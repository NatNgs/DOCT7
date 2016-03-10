Architechture :

	MutationGenerator :
	

	MutationApply :
		Projet maven, génère un jar qui permet d'ajouter les dépendances nécessaires dans le pom.xml des projets à tester
		pour applquer les mutations.

	XmlsCompiler:
		Génère un rapport à partir des rapports de test contenus dans les dossier présents dans TempResult.
		

	exec.sh :
		Supprime les fichier temporaire et créer les dossier requis au framework, pour chaque dossier, créer un dossier temporaire dans MutatedSrc. Un dossier par projet et par liste de processeurs à appliquer. Appel MutationApply pour modifier le pom.xml des projets afin d'appliquer les mutations. Le script appel alors le script execproject.sh en fond pour appliquer les mutations sur les projet et lancer les tests. Une fois tous les tests exécutés le script fait appel à XmlsCompiler pour générer le rapport.
		
		


Forces du projet :
Facilité pour rajouter des processeurs.
Selection simple des processeurs à exécuter.
Possibilité d'appliquer autant de jeux de processeur que souhaité.
Exécution de différents projets en parallèle pour accélérer le processeur.


Faiblesses :
Nombreux modules différents => architechture complexe/peu élégante.
Rapport généré ?
Méthode pour rajouter les processeurs sur les projet à améliorer.
Manque de clareté dans l'architechture du framework.
Manque de controle : pas de vérification les projets sont des projets maven par ex.
Architecture rigide.



