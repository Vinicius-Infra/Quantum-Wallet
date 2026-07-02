# Estágio 1: Build da aplicação usando a imagem mais recente do Gradle 8 com Java 17
FROM gradle:8-jdk17-jammy AS build
WORKDIR /app

# Copia as configurações do projeto
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Executa o build do JAR
RUN gradle bootJar -x test

# Estágio 2: Imagem final leve usando Eclipse Temurin oficial (Java 17 JRE)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]