# Primero, copiamos SOLO el archivo pom.xml
# Esto es un "truco" de Docker. Al copiar solo el pom.xml primero,
# Docker descargará las dependencias de Maven y las guardará en caché.
# Si luego cambias tu código Java, no tendrá que volver a descargar todo el internet.
COPY pom.xml .

# Descargamos las dependencias (esto puede tardar la primera vez)
RUN mvn dependency:go-offline -B

# Ahora sí, copiamos todo el código fuente de nuestra aplicación (la carpeta src)
COPY src ./src

# Si tu microservicio necesita la Oracle Wallet local para compilar/probar, 
# descomenta la siguiente línea y asegúrate de que la carpeta Wallet exista localmente
# COPY Wallet_BDNOVALOGISTIC /app/Wallet_BDNOVALOGISTIC

# Compilamos el proyecto, saltándonos las pruebas para que sea más rápido
# El resultado será un archivo .jar en la carpeta /app/target
RUN mvn clean package -DskipTests

# ==============================================================================
# ETAPA 2: EJECUCIÓN (RUNTIME)
# ==============================================================================
# Usamos una imagen MUCHO más ligera que solo contiene Java 21 (sin Maven)
# para que nuestra imagen final sea pequeña y segura.
FROM eclipse-temurin:21-jre-alpine

# Establecemos el directorio de trabajo para la aplicación final
WORKDIR /app

# Copiamos ÚNICAMENTE el archivo .jar generado en la ETAPA 1 ('builder')
# y lo renombramos a 'app.jar' por conveniencia.
COPY --from=builder /app/target/*.jar app.jar

# (Opcional) Si usas Oracle Wallet, cópiala también a la imagen final
COPY --from=builder /app/Wallet_BDNOVALOGISTIC /app/Wallet_BDNOVALOGISTIC

# Exponemos el puerto en el que corre el microservicio (por defecto 8081 para usuarios)
# Cambia este puerto según corresponda (Gateway=8080, Eureka=8761, etc.)
EXPOSE 8080

# Comando que se ejecutará al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]