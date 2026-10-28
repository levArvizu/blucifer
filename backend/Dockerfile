# Define la imagen base con Java 21 (Temurin es la distribución de OpenJDK mantenida por Eclipse).
FROM eclipse-temurin:21-jdk

# Establece el directorio de trabajo dentro del contenedor. Todo lo que se ejecute o copie será relativo a /app.
WORKDIR /app

# 	Copia el archivo compilado .jar desde tu proyecto local (carpeta target) al contenedor con el nombre app.jar.
COPY target/backend-*.jar app.jar

# Indica que el contenedor escuchará en el puerto 8080 (el mismo que usa tu aplicación Spring Boot).
EXPOSE 8081

# Define el comando que se ejecutará al iniciar el contenedor: lanzar la aplicación Java.
ENTRYPOINT ["java","-jar","app.jar"]


# Empaqueta tu aplicación Java en una imagen lista para ejecutar.

# Puntos clave:

# Usa Java 21 (Temurin).

# Copia el .jar compilado al contenedor.

# Expone el puerto 8080.

# Ejecuta la app con java -jar app.jar.

# Relación:  
# La imagen generada se usa en el Backend Deployment de Kubernetes.