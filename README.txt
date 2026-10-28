Guía Técnica Completa — Proyecto Fullstack Lab

1. Docker Compose

Orquesta los servicios base (Postgres, Redis, Kafka y Backend) en un entorno local. Cada servicio define su imagen, puertos, variables y dependencias.

Propósito: Permite levantar todo el entorno con un solo comando (docker-compose up).

2. Dockerfile

Empaqueta tu aplicación Java en una imagen lista para ejecutar.

Puntos clave:

Usa Java 21 (Temurin).

Copia el .jar compilado al contenedor.

Expone el puerto 8080.

Ejecuta la app con java -jar app.jar.

Relación: La imagen generada se usa en el Backend Deployment de Kubernetes.

3. application.yml (Spring Boot)

Define la configuración de tu aplicación:

Conexiones a Postgres, Redis y Kafka.

Configuración de JPA y Actuator para salud del sistema.

Variables de entorno (DB_URL, DB_USER, DB_PASSWORD, etc.) que se obtienen del ConfigMap y Secret.

Relación: El backend usa este archivo para conectarse a los servicios del clúster.

4. Kubernetes Deployments

Cada servicio (Postgres, Redis, Kafka, Backend) se define como un Deployment.

Propósito:

Crear pods que ejecutan los contenedores.

Controlar réplicas y actualizaciones.

Definir variables de entorno y puertos.

Ejemplo:

Postgres: image: postgres:17, puerto 5432.

Redis: image: redis:8, puerto 6379.

Kafka: image: apache/kafka:latest, puerto 9092.

Backend: image: fullstack-lab:latest, puerto 8080.

5. Kubernetes Services

Exponen los pods dentro del clúster.

Tipos:

ClusterIP: acceso interno (Postgres, Redis, Kafka).

LoadBalancer: acceso externo (Backend).

Relación: El backend se comunica con los otros servicios usando sus nombres (postgres, redis, kafka).

6. ConfigMap y Secret

Proveen configuración y credenciales al backend.

ConfigMap:

DB_URL: jdbc:postgresql://postgres:5432/fullstacklab
REDIS_HOST: redis
KAFKA_HOST: kafka:9092

Secret:

DB_USER: admin
DB_PASSWORD: admin
JWT_SECRET: my-secret-key

Relación: El Backend Deployment obtiene estos valores mediante valueFrom.configMapKeyRef y valueFrom.secretKeyRef.

7. Ingress

Expone el backend al exterior del clúster.

Host: fullstack-lab.localPuerto: 8080Función: Redirige tráfico HTTP al backend.

8. GitHub Actions — CI/CD Workflow

Automatiza la compilación y empaquetado de tu aplicación cada vez que haces push o PR a main.

Pasos principales:

Checkout Repository: descarga el código fuente.

Setup Java 21: instala Java Temurin 21.

Verify Maven: confirma que Maven está disponible.

Build Application: compila el proyecto y genera el .jar.

Upload Jar Artifact: guarda el .jar como artefacto del build.

Propósito: Automatiza la compilación y artefactos del proyecto.

9. Flujo completo del proyecto

Etapa

Herramienta

Propósito

Desarrollo local

Docker Compose

Levantar entorno completo con un comando

Empaquetado

Dockerfile

Crear imagen del backend

Configuración

application.yml

Definir conexiones y propiedades

Despliegue

Kubernetes

Orquestar servicios y pods

Configuración dinámica

ConfigMap / Secret

Proveer variables y credenciales

Exposición

Ingress

Permitir acceso externo

Automatización

GitHub Actions

Compilar y subir artefactos automáticamente

Recomendaciones finales

Usa PersistentVolumes para Postgres y Redis si quieres persistencia real.

Configura JWT_SECRET como variable segura en GitHub Actions para despliegues automatizados.

Agrega un paso de Docker build & push en el workflow para subir la imagen al registro.

Implementa Helm charts si planeas escalar el proyecto.