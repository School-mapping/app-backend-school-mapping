# --- Etapa 1: Builder (Usamos a imagem com Maven para compilar) ---
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o projeto principal e o submódulo
COPY pom.xml .
COPY src src


# Compila o projeto principal. O Shade Plugin cria o JAR executável.
RUN mvn clean package -DskipTests

# --- Etapa 2: Runtime (Container Final - Apenas JRE, mais leve) ---
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# COPIA o JAR COMPLETO (nome do artefato principal) do build
COPY --from=builder /app/target/backend-school-mapping-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

# Define o ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
