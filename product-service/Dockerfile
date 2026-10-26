#//-----Primera Etapa: Imagen -> .jar ejecutable
FROM eclipse-temurin:25-jdk AS builder
# Usa una imagen con JDK 25 para construir la aplicación.

WORKDIR /app
# Establece /app como directorio de trabajo.

COPY . .
# Copia todos los archivos del proyecto actual al directorio /app.

RUN ./mvnw clean package -DskipTests
# Ejecuta Maven Wrapper para limpiar, compilar y empaquetar la aplicación en un .jar, omitiendo los tests.


#//----Segunda Etapa: Busca el .jar del builder y descarta herramientas innecesarias (maven, repositorios, etc)
FROM eclipse-temurin:25-jre
# Crea una segunda etapa usando únicamente el JRE 25 para ejecutar la aplicación.

WORKDIR /app
# Establece /app como directorio de trabajo dentro del segundo contenedor.

COPY --from=builder /app/target/*.jar app.jar
# Copia el .jar generado en la etapa "builder" hacia la segunda etapa y lo renombra como app.jar.

EXPOSE 8080
# Declara que la aplicación utiliza el puerto 8080 dentro del contenedor.

ENTRYPOINT ["java", "-jar", "app.jar"]
# Define el comando que se ejecutará cuando se inicie el contenedor.