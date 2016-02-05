# Chaine de build

Nous partirons des fichiers sources de notre programme Java puis utiliserons Spoon pour appliquer les mutations. (Cette étape sera répetée un grand nombre de fois(paramétrable) pour récupérer de nombreuses sources avec des mutations différentes)

	  src    ---------->  srcWithMutation
	(.java)     Spoon        (.java)

Ensuite nous compilerons ces sources java avec mutations à l'aide de javac. (Cette étape sera répetée pour chaque source avec des mutations)

	srcWithMutation  ---------->  SoftWithMutation
	   (.java)          javac         (.class)

Nous appliquerons la batterie de tests à ces fichiers compilés, à l'aide de JUnit, pour obtenir un rapport de test en xml.

	                   TestFile
	                   (.java)
	                      |
	                      |
	SoftWithMutation  ---------->  ResultFile
	    (.class)         JUnit       (.xml)

L'ensemble des rapports de tests seront analysés par un programme Java, qui produira un fichier HTML présentant les resultats des tests de mutations.

	ResultFile  ---------->  Rapport
	  (.xml)       Java      (.html)

Tout ce processus sera automatisé avec maven.
Nous utiliserons les plugins de maven déjà existants pour une partie de la chaine de build. Le "compil" pour compiler nos sources avec mutations. Le "test" pour appliquer la batterie de test à nos fichiers compilés avec mutations.
Nous créerons aussi deux plugins maven pour automatiser d'une part l'ajout des mutations aux sources java, et pour automatiser la phase d'assemblage et d'analyse des résultats afin de produire le rapport final.


# Mutations

Notre framework permettra d'appliquer les mutations suivantes:

- Modification des conditions booléenes de tout le code (boucles, conditions, conditions ternaires...)
  - Les modifier par leur inverse, par exemple modifier "==" en "!=", "<=" en ">"...
  - Changer l'égalité pour les superieurs/inferieurs	">=" en ">",  "<" en "<="...

- Modification des operateurs binaires
  - Intervertir "+", "-", "*", "/", "%"
  - Intervertir "^", "&", "|"
  - Intervertir "&&", "||"

- Modification des opérateurs unaires
  - Intervertir "++", "--"
  - Ajouter ou supprimer un "-" préfixé, un "~" préfixé

- Modification des opérateurs d'assignations
  - Intervertir "*=", "/=", "+=", "-=", "%=", "|=", "&=", "^="

- Modification de l'accessibilité des méthodes
  - Changer "private" en "protected", "default" ou "public"
  - Changer "protected" en "default" ou "public"
  - Changer "default" en "public"

- Supprimer des break (dans des switch ou dans des boucles)

- Remplacer des conditions par true ou false (dans un if ou un while par exemple)

- Modification des valeurs écrites "en dur" dans le code (que ça soient des initialisations "String s="Toto"", opérations "i>=3", utilisations "toto('x')"...)
  - Changement de la valeur des nombres (int, unsigned, short, long, float, double...)
  - Changement de la valeur d'un char, d'une partie d'une String ("Toto" > "Txto")

Notre framework permettra de rajouter simplement de nouvelles mutations si le besoin s'en fait sentir. (interface à implementer pour rendre une nouvelle mutation fonctionnelle)


Les mutations seront appliqués comme ceci:
 - Récupération d'une liste de toutes les mutations possibles dans le code, triés par type (Opérateur binaire, unaire, assignation, break, true/false...)
 - Un type de mutation est choisi (dans l'ordre, le premier d'abord)
 - Une mutation parmi celles du type choisi est appliquée (choisie aléatoirement)
 - Le code muté est compilé et testé; le XML de résultats généré.

 - Le type de mutations suivant est choisi (dans l'ordre des types de mutations, retour au premier si on a passé touts les types)
 - Une mutation parmi celles du type choisi est appliquée (choisie aléatoirement)
 - Le code muté est compilé et testé; le XML de résultats généré.

 - ... Jusqu'à que le temps d'execution paramétré par l'utilisateur soit dépassé (on interrompt pas une mutation en cours de traîtement)
