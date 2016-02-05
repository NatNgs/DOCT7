************************
*** Chaine de build: ***
************************

Nous partirons des fichiers sources de notre programme java et appliquerons Spoon pour appliquer les mutations et génerer les fichiers sources avec mutations. (Cette étape sera répeté un grand nombre de fois pour récupérer de nombreuses sources avec des mutations différentes)

	  src    ---------->  srcWithMutation
	(.java)     Spoon        (.java)

Ensuite nous compilerons ces sources java avec mutations à l'aide de javac pour obtenir des fichiers compilés. (Cette étape sera répeté pour chaque source avec des mutations)

	srcWithMutation  ---------->  SoftWithMutation
	   (.java)          javac         (.class)

Nous appliquerons la batterie de test à ces fichiers compilé à l'aide de JUnit pour obtenir un rapport de test en xml.

	                   TestFile
	                   (.java)
	                      |
	                      |
	SoftWithMutation  ---------->  ResultFile
	    (.class)         JUnit       (.xml)

L'ensemble des rapports de test seront analysé par un programme java qui produira un fichier HTML compilant les resultats des tests de mutations.

	ResultFile  ---------->  Rapport
	  (.xml)       Java      (.html)

Tout ce processus sera automatisé avec maven.
Nous utiliserons les plugins de maven déjà existant pour une partie de la chaine de build. Le "compil" pour compiler nos sources avec mutations. Le "test" pour appliquer la batterie de test à nos fichiers compilé avec les mutations.
Nous créerons aussi deux plugins maven pour automatiser d'une part l'application des mutations aux sources java et un autre pour automatiser la phase d'assemblage et d'analyse des résultats pour produire le rapport final.



*****************
*** Mutations ***
*****************

Notre framework permettra d'appliquer les mutations suivantes:

- Modification de condition booleene (modifier les conditions booleene par leur opposé, par exemple modifier un == en != ou un <= en >)
	Cette mutation sera effectué dans les conditions des boucles, dans les conditions de l'operateur ternaire 

- Modification des operateurs binaire et unaire (par exemple changer un ++ en --, un ^ en & ou encore changer un / en *)

- Modification de l'accessibilité des méthodes (par exemple modifier un public en private ou un protected en private)

- Supprimer des break (dans un switch ou une boucle)

- Remplacer des conditions par true ou false (dans un if ou un while par exemple)

- Modification des valeurs affecté en dur dans le code (Par exemple l'initialisation de variable à un int fixe ou les valeurs fixe dans les conditions des boucles)

Notre framework permettra de rajouter simplement de nouvelle mutation si le besoin s'en fait sentir (interface à implementer pour rendre une nouvelle mutation fonctionnelle).


