# Arca-Sphynx
Squelette d'application pour Arca

Le projet se découpe en 2 parties.
En entrée, il y a un fichier de données disponible ici (~500 Mo) :
https://www.dropbox.com/s/r666vgx8msg9lg9/data.txt.zip?dl=0

Il contient 3 données par ligne :

- Un timestamp
- Une valeur (entre 0 et 100)
- Une origine (nom de pays)

Il faut partir du principe que les données ne tiennent potentiellement pas en RAM.

## Features à implémenter (en théorie) :
### Partie batch :

- Intégrer le fichier de données dans une base de données.
- Le batch si interrompu doit être capable de reprendre là où il s'était arrêté.

### Partie web :

- Etre capable de lancer le batch
- Etre capable d'afficher un état d'avancement pendant l'exécution du batch (ça peut nécessiter des modifications dans le batch pour exposer des choses)
- Afficher un tableau qui affiche la somme des valeurs par source de données.
- Représenter les données de la base dans un chart qui trace une courbe en fonction de la somme des valeurs d'une journée (toute source confondue) et du temps sur une période d'un an (granularité au jour).
- Etre capable de naviguer d'une année à l'autre pour le chart.
- Filtrer le contenu du tableau sur la période active dans le chart
- Pouvoir choisir une période au choix.
- Pouvoir choisir la granularité sur le chart (jour, semaine, mois, année)
- Pouvoir choisir une source qui sera représentée par une 2eme courbe sur le chart, reprenant les mêmes fonctionnalités mais qui représente uniquement les valeurs de cette source.
