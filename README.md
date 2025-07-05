# commande-microservice

Microservice de gestion des commandes, développé en Java avec Spring Boot, Kafka et Avro.

## Fonctionnalités

- Création, consultation et gestion des commandes
- Communication asynchrone via Apache Kafka
- Sérialisation des messages avec Avro
- Architecture modulaire et évolutive

## Prérequis

- Java 17+
- Maven 3.8+
- Docker (pour Kafka et le registre de schémas)
- Kafka et Schema Registry en fonctionnement

## Démarrage rapide

1. **Cloner le dépôt :**
   ```bash
   git clone https://github.com/ton-utilisateur/commande-microservice.git
   cd commande-microservice

2. **Configurer les variables d'environnement** :

.Modifier les fichiers de configuration dans src/main/resources/application.yml selon votre environnement (Kafka, base de données, etc.).

3. **Lancer les dépendances (Kafka, Schema Registry)** :

docker-compose up -d

4. **Construire et lancer l'application** :
mvn clean install
mvn spring-boot:run

## Structure du projet
kafka-service/ : gestion de la production et consommation des messages Kafka
commande-service/ : logique métier liée aux commandes
common/ : classes utilitaires et modèles partagés

## Exemples d'utilisation
Envoyer une commande

POST /api/commandes
{
"clientId": "123",
"produits": [
{"id": "A1", "quantite": 2}
]
}

**Consommer les événements Kafka**
Le microservice consomme et publie des événements sur les topics Kafka configurés.


## Tests
Lancer les tests unitaires et d'intégration :

## Contribution
1. **Forker le projet**
2. **Créer une branche** 
```bash
git checkout -b feature/ma-fonctionnalite
```

3. **Committer vos modifications**
4. **Pousser la branche** 
```bash
git push origin feature/ma-fonctionnalite
```

5. **Ouvrir une Pull Request**

## Licence
Ce projet est sous licence MIT.
