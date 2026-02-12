# Étape 1: Build avec Maven JDK 11
FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /app

# Copier pom.xml pour profiter du cache Docker
COPY pom.xml ./

# Copier le code source
COPY src ./src

# Compiler et créer le WAR (tests ignorés pour accélérer)
RUN mvn clean package -DskipTests

# Étape 2: Runtime avec Tomcat 9 JDK 11
FROM tomcat:9.0-jdk11-temurin

# Supprimer les webapps par défaut
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le WAR généré depuis l'étape build
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war


# Exposer le port Tomcat
EXPOSE 8080

# Démarrer Tomcat
CMD ["catalina.sh", "run"]
