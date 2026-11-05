# ETAPA 1: BUILD (compilação da aplicação)

# Utiliza uma imagem que já possui Maven e Java 17 instalados. O nome "build" identifica essa etapa
FROM maven:3.9-eclipse-temurin-17 AS build

# Define /app como diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml do projeto para dentro da imagem
COPY pom.xml .

# Baixa as dependências do Maven antecipadamente. Isso permite aproveitar o cache do Docker nos próximos builds
RUN mvn -B dependency:go-offline

# Copia o código-fonte da aplicação para dentro da imagem
COPY src ./src

# Compila e empacota a aplicação, gerando o arquivo .jar. O -DskipTests pula a execução dos testes
RUN mvn -B clean package -DskipTests

# ETAPA 2: EXECUÇÃO DA APLICAÇÃO

# Utiliza uma imagem mais leve, contendo apenas o Java 17. Não precisamos do Maven para executar a aplicação
FROM eclipse-temurin:17-jre

# Define /app como diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado na etapa de build para a imagem final, renomeando-o para app.jar
COPY --from=build /app/target/*.jar app.jar

# Documenta que a aplicação utiliza a porta 8080. Não publica a porta automaticamente no computador
EXPOSE 8080

# Comando executado quando o container é iniciado. Inicia a aplicação Spring Boot usando o Java
ENTRYPOINT ["java", "-jar", "app.jar"]
