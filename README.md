# Projet_DevOps à l'UFR IM2AG 2024

## Badges

- [![codecov](https://codecov.io/gh/tom7438/projetdevops/graph/badge.svg?token=5INFKCFXE6)](https://codecov.io/gh/tom7438/projetdevops)
- ![GitHub Issues or Pull Requests](https://img.shields.io/github/issues-pr/tom7438/projetdevops) ![GitHub commit activity](https://img.shields.io/github/commit-activity/w/tom7438/projetdevops)

- ![GitHub top language](https://img.shields.io/github/languages/top/tom7438/projetdevops)
- ![GitHub License](https://img.shields.io/github/license/tom7438/projetdevops)
- ![GitHub Repo stars](https://img.shields.io/github/stars/tom7438/projetdevops)

[Consulter la couverture de code](./target/site/jacoco/index.html)


## Sommaire

- [Projet_DevOps à l'UFR IM2AG 2024](#projet_devops-à-lufr-im2ag-2024)
  - [Description](#description)
  - [Installation](#installation)
  - [Intégration continue](#intégration-continue)
  - [Travail collaboratif](#travail-collaboratif)
  - [Livraison continue](#livraison-continue)
  - [Utilisation](#utilisation)
  - [Badges](#badges)

## Description
Nous avons réalisé un projet de développement logiciel en utilisant les outils de DevOps. Notre projet est une bibliothèque d'analyse de données Java similaire à Pandas en Python. Nous avons utilisé les outils suivants pour réaliser notre projet :
- Maven
- Docker
- Google Cloud SDK
- JaCoCo / CodeCov
- JUnit 5
- GitHub / GitHub Actions / GitHub Pages
- IntelliJ IDEA
- Java 17 / Javadoc
- DockerHub
- GitHub Container Registry

## Installation
Pour installer notre projet, il suffit de cloner le dépôt GitHub sur votre machine locale. Pour cela, vous pouvez exécuter la commande suivante :
```bash
git clone https://github.com/tom7438/projetdevops.git
```


## Intégration continue
Nous avons utilisé GitHub Actions pour notre intégration continue. Nous avons créé un workflow qui permet de tester notre projet à chaque pull request sur la branche main (voir [Travail collaboratif](#travail-collaboratif)).
Donc à chaque pull request, le workflow est déclenché et exécute les tests unitaires de notre projet ainsi que la couverture de code de notre class DataFrame. Si les tests passent et que la couverture de code est > 90 alors un des membres du groupe, peut faire sa review et décider ou non d'accepter le pull request afin de merger sur la branche main.
Pour le code coverage, nous avons pris JaCoCo, c'était l'outil qui nous était présenté de base comme exemple pour le projet et comme nous n'en connaissions aucun autre, il convenait parfaitement.
Nous avons choisi d'héberger notre projet sur GitHub, car c'est l'outil avec lequel nous sommes le plus familier, nous l'utilisons depuis un moment.

En ce qui concerne les tests unitaires, ils sont réalisés avec JUnit 5, il exploite les fonctionnalités de Java 8 ou version ultérieure, rendant les tests plus puissants et plus faciles à maintenir.

## Travail collaboratif
Nous avons utilisé le Feature Branch Workflow pour travailler sur notre projet. Le Feature Branch Workflow est un processus de développement logiciel dans lequel chaque fonctionnalité est développée dans une branche dédiée.
Cette encapsulation permet à plusieurs développeurs de travailler facilement sur une fonctionnalité particulière sans perturber la base de code principale. Cela signifie également que la main branche ne doit jamais contenir de code défectueux, ce qui constitue un énorme avantage pour les environnements d'intégration continue.

Nous avons donc travaillé en développant chaque feature dans une branche différente, lorsque la feature était terminée et testée, un Pull Request est envoyé sur la branche main. Alors au moins une review du code doit être effectuée par l'un des autres collaborateurs du projet, s'il accepte, la feature peut ainsi être mergée sur la branche main.
De plus, on ne peut pas merge une feature sur la branche main s'il n'y a pas au moins 90% de code coverage sur la feature. Cela permet de garder un pourcentage élevé de couverture de code et ainsi de s'assurer que notre projet est le plus fonctionnel possible.

## Livraison continue
Notre projet est également agrémenté d'une livraison continue. Pour cela, nous avons utilisé les outils suivants :
- Maven
- GitHub Pages
- Docker (GitHub Packages & GitHub Container Registry)
- Google Cloud SDK

URL du projet : https://tom7438.github.io/projetdevops/

## Utilisation
Pour utiliser notre bibliothèque d'analyse de données Java, vous pouvez importer notre projet dans votre IDE préféré. Ensuite, vous pouvez utiliser les fonctionnalités suivantes :

- Création d'une DataFrame à partir d'une liste de données (liste de liste ou tableau de tableau) et d'une HashMap (key = le nom de la colonne, data = type de donnée) :

```java
import java.util.LinkedHashMap;

List<List<Object>> data = new ArrayList<>();
data.add(Arrays.asList(1, "Alice",25));
data.add(Arrays.asList(2, "Bob",30));
data.add(Arrays.asList(3, "Charlie",35));
LinkedHashMap<String, Class<?>> schema = new LinkedHashMap<>();
schema.put("id", Integer.class);
schema.put("name", String.class);
schema.put("age", Integer.class);

DataFrame df = new DataFrame(data, schema);

// Version tableau
Object[][] data2 = {
    {1, "Alice", 25},
    {2, "Bob", 30},
    {3, "Charlie", 35}
};
DataFrame df2 = new DataFrame(data2, schema);
```

- Création d'une DataFrame à partir d'un fichier .CSV :

```java
DataFrame df = DataFrame.readCSV("data.csv");
```

- Afficher la DataFrame ou les n premières/dernières lignes de la DataFrame :

```java
df.display();
df.displayFirstLines(2);
df.displayLastLines(2);
```

- Sélection dans une DataFrame (lignes, colonnes, valeurs) :

```java
// Sélectionner des lignes à partir de leur index
int[] rows = {0, 2};
DataFrame df2 = df.select_line(rows);

// Sélectionner des colonnes à partir de leur nom
String[] columns = {"name", "age"};
DataFrame df3 = df.select_column(columns);

// Obtenir les lignes dont la colonne "age" est supérieure à 25
String cond = "age > 25";
DataFrame df4 = df.select(cond);
```

- Ajouter/Supprimer une ligne dans la DataFrame :

```java
// Ajouter une ligne
List<Object> ligne = Arrays.asList(4, "David", 40);
df.ajouter_ligne(ligne);

// Supprimer une ligne
df.supprimer_ligne(2);
```

- Statistique sur une colonne (min, max, moyenne, somme) :

```java
// Afficher le minimum d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
System.out.println(df.min("age"));
System.out.println(df.min(2));

// Afficher le maximum d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
System.out.println(df.max("age"));
System.out.println(df.max(2));

// Afficher la moyenne d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
System.out.println(df.mean("age"));
System.out.println(df.mean(2));

// Afficher la somme d'une colonne selon son nom ou selon son index de colonne si c'est des valeurs numériques
System.out.println(df.sum("id"));
System.out.println(df.sum(0));
```

- Récupérer un index de colonne à partir de son nom :
```java
int index = df.getIndexesColone("age");
```

- Obtenir une valeur dans le DataFrame à partir d'une ligne et d'une colonne :
```java
Object value = def.obtenirValeur(1, "name");
```

- Pour déployer l'application sur Google Cloud, vous pouvez exécuter la commande suivante :
```bash
cd terraform
terraform apply --auto-approve
# Se connecter à la VM en ssh
gcloud compute ssh --zone "us-central1-c" "terraform-instance-0" --project "devops2024rtl2"
# lancer l'image docker
sudo docker run ghcr.io/tom7438/projetdevops/rtl2_datalibrary:latest
```

## Auteurs

- ALVES Romain
- BOSSY Romain
- CLEMENT Tom
- FOUSSADIER Lancelin

M1 INFO 2023-2024