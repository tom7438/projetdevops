# Projet_DevOps à l'UFR IM2AG 2024

Feature Branch Workflow est le fait que tout le développement de fonctionnalités doit avoir lieu dans une branche dédiée au lieu de la main branche. Cette encapsulation permet à plusieurs développeurs de travailler facilement sur une fonctionnalité particulière sans perturber la base de code principale. Cela signifie également que la main branche ne doit jamais contenir de code défectueux, ce qui constitue un énorme avantage pour les environnements d'intégration continue.  

Nous avons travaillé en développant chaque feature dans une branche différente, lorsque la feature était terminée et testée, un Pull Request est envoyé sur la branche main. Alors au moins une review du code doit être effectuée par l'un des autres collaborateurs du projet, si il accepte, la feature peut alors être merge sur la branche main. De plus, on ne peut pas merge une feature sur le main si il n'y a pas au moins 90% de code coverage sur la feature.  Cela permet de garder un pourcentage élevé et ainsi de s'assurer que notre projet est le plus fonctionnel possible.

URL : https://tom7438.github.io/projetdevops/

Badges

[![codecov](https://codecov.io/gh/tom7438/projetdevops/graph/badge.svg?token=5INFKCFXE6)](https://codecov.io/gh/tom7438/projetdevops)

[Consulter la couverture de code](./target/site/jacoco/index.html)


![GitHub Issues or Pull Requests](https://img.shields.io/github/issues-pr/tom7438/Projet_DevOps)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/tom7438/Projet_DevOps)

![GitHub top language](https://img.shields.io/github/languages/top/tom7438/Projet_DevOps)
![GitHub License](https://img.shields.io/github/license/tom7438/Projet_DevOps)
![GitHub Repo stars](https://img.shields.io/github/stars/tom7438/Projet_DevOps)

Notre bibliothèque d'analyse de données Java fournis les fonctionnalités suivantes :
  - Création d'une DataFrame à partir d'une liste de données ou d'un tableau
  - Création d'une DataFrame à partir d'un fichier .CSV
  - Récupérer un index de colonne à partir de son nom
  - Ajouter/Supprimer une ligne dans la DataFrame
  - Supprimer une colonne du DataFrame
  - Obtenir une valeur dans le DataFrame à partir d'une ligne et d'une colonne
  - Sélectionner des lignes à partir de leur index
  - Sélectionner des colonnes à partir de leur nom
  - Afficher la DataFrame
  - Afficher les n premières lignes de la DataFrame
  - Afficher les n dernières lignes de la DataFrame
  - Afficher le minimum d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
  - Afficher le maximum d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
  - Afficher la moyenne d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
  - Afficher la somme d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques

Notre projet est également agrémenté d'une livraison continue :  - Maven
                                                                   - Docker
                                                                   - Google Cloud SDK

Pour le code coverage, nous avons pris JaCoCo, c'était l'outil qui nous était présenté de base comme exemple pour le projet et comme nous n'en connaissions aucun autre, il convenait parfaitement.  
Nous avons choisi choisit d'héberger notre projet sur GitHub, car c'est l'outil avec lequel nous sommes le plus familier, parce que nous l'utilisons depuis un moment.

En ce qui concerne les tests unitaires, ils sont réalisés avec JUnit 5, exploite les fonctionnalités de Java 8 ou version ultérieure, rendant les tests plus puissants et plus faciles à maintenir.
Pour les autres outils, nous avons suivis les recommandations du sujet.
