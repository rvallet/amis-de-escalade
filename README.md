# amis-de-escalade LADE [OCP6]
Site communautaire : Les amis de l'escalde

## Conception d'une application de site sur l'esclade

### Objet :
Création d’un site communautaire autour de l’escalade.

### Contexte :
Avec l’objectif de fédérer les licenciés, l’association “Les amis de l’escalade”
souhaite développer sa présence en ligne. À ce titre, plusieurs axes d’amélioration
ont été identifiés dont la création d’un site communautaire.
Ce site aura pour but la mise en relation et le partage d’informations. Il permettra
de donner de la visibilité à l’association afin d’encourager des grimpeurs
indépendants à y adhérer.

#### Travail demandé :

Liste des fonctionnalités attendues :
* Visiteur
    * Un utilisateur doit pouvoir consulter les informations des sites d’escalades (secteurs, voies, longueurs, etc.).
    * Un utilisateur doit pouvoir faire une recherche à l’aide de plusieurs critères pour trouver un site de grimpe et consulter le résultat de cette recherche. Les critères peuvent être le lieu, la cotation, le nombre de secteurs, etc.
    * Un utilisateur doit pouvoir s’inscrire gratuitement sur le site.

* Utilisateur
    * Un utilisateur connecté doit pouvoir partager des informations sur un site d’escalade (secteurs, voies, longueurs, etc.).
    * Un utilisateur connecté doit pouvoir laisser des commentaires sur les pages des sites d’escalade.
    * Un utilisateur connecté doit pouvoir enregistrer dans son espace personnel les topos qu’ils possèdent et préciser si ces derniers sont disponibles pour être prêtés ou non. Un topo est défini par un nom, une description, un lieu et une date de parution.
    * Un utilisateur connecté doit pouvoir consulter la liste des topos disponibles par d’autres utilisateurs et faire une demande de réservation. La réservation n’inclut pas les notions de date de début et date de fin.
    * Un utilisateur connecté doit pouvoir accepter une demande de réservation. Si une réservation est acceptée, automatiquement le topo devient indisponible. L’utilisateur connecté pourra ensuite de nouveau changer le statut du topo en « disponible ». La plateforme se contente de mettre en contact les deux parties pour le prêt d’un topo (par échange de coordonnées).

* Membre de l'association LADE
    * Un membre de l'association doit pouvoir taguer un site d’escalade enregistré sur la plateforme comme « Officiel Les amis de l’escalade ».
    * Un membre de l'association doit pouvoir modifier et supprimer un commentaire.

### Livrables attendus :
* le code source de l’application
* les scripts SQL de création de la base de données et d'un jeu de données de démo
* une documentation succincte (un fichier README.md  suffit) expliquant comment déployer l'application (base de données, configuration sur serveur Tomcat...).

@[MPD](https://github.com/rvallet/amis-de-escalade/blob/master/sql/MPD_OCP6_LADE.JPG) : Modèle Physique de Données

@[SQL Script CREATE](https://github.com/rvallet/amis-de-escalade/blob/master/sql/escalade_bdd_create.sql) : Création de la BDD et des tables

@[SQL Script INSERT](https://github.com/rvallet/amis-de-escalade/blob/master/sql/dump-escalade_bdd.sql) : Jeux de données de la BDD

@[UML](https://github.com/rvallet/amis-de-escalade/blob/master/uml/class-diagram.jpg) : Diagram de classe des entitées

@[JavaDoc](https://github.com/rvallet/amis-de-escalade/tree/master/doc/com/ocesclade/amisdeescalade) : JavaDoc du projet

## Installation
* Importer le projet dans votre repertoire de travail (git clone)
* Créer une base donnée MySQL de nom "escalade_bdd" et lacer votre serveur (ex : WampServer)
* Paramétrer l'accès au serveur de votre base de donnée dans le fichier application.properties (/amis-de-escalade/src/main/resources/application.properties)
    * spring.datasource.url : votre adresse vers votre BDD (URL, Port, Nom de la BDD, paramètres)
    * spring.datasource.username : l'identifiant de vore BDD (utilisteur aavec droits CRUD)
    * spring.datasource.password : le mot de passe de votre BDD
* Lancer un Maven Install ou Update pour télécharger les dépendances du projet
* Lancer l'application (Boot Dashboard Start, serveur Apache Tomcat v8.0 ou ultérieure)
    * Au lancement, l'application vas créer un jeu de donnée dans votre BDD (cf. /amis-de-escalade/src/main/java/com/ocesclade/amisdeescalade/Application.java). Une simple recherche de l'existance de l'utilisateur 'email@user1.fr' en BDD est effectuée (s'il existe, le jeu de donnée ne seras pas lancé). Si vous rencontrer un problème à l'initialisation du jeu de donnée, supprimer toute vos tables et relancer l'application

## Built With

* [Eclipse](https://www.jetbrains.com/idea/) - IDE (JDK8)
* [Maven](https://maven.apache.org/) - Dependency Management
* [MySQL WorkBench](https://www.mysql.com/) - SGB MySQL, pour la conception du Modèle Physique de Donnée
* [DBeaver](https://dbeaver.io/) - SGBD universelle, pour l'écriture des scripts SQL et des tests MySQL
* [WampServer](http://www.wampserver.com/) - Plateforme Apache, PHP, MySQL (+PHP MyAdmin)

## Authors

* **Rémy VALLET** - *Initial work* - [rvallet](https://github.com/rvallet)

See also the list of [contributors](https://github.com/rvallet/amis-de-escalade/contributors) who participated in this project.

## License
This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/rvallet/amis-de-escalade/blob/master/LICENSE) file for details
