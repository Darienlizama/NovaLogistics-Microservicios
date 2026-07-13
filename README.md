🚀 NovaLogistics: Sistema de Gestión Logística Inteligente
NovaLogistics es una solución integral orientada a microservicios diseñada para la optimización de procesos logísticos. El sistema permite gestionar desde el registro de usuarios y personal hasta la trazabilidad de envíos, utilizando una arquitectura moderna, reactiva y distribuida.

🏗️ Arquitectura del Sistema
El sistema ha evolucionado hacia un modelo de microservicios independientes, garantizando escalabilidad y alta disponibilidad. Los servicios se comunican entre sí utilizando WebClient para operaciones asíncronas y se despliegan mediante Docker.

Microservicios Implementados:
Eureka Server: Servidor de descubrimiento para el registro y monitoreo de servicios.

API Gateway: Punto de entrada único para el enrutamiento y la seguridad.

MS Usuarios: Gestión de clientes, personal y seguridad.

MS Operaciones: Orquestación de logística, envíos y paquetería.

MS Comercial: Gestión de transacciones, presupuestos y tarifas.

MS Infraestructura: Mantenimiento y recursos del sistema.

Módulos Complementarios: Auditoría, Reclamos, Seguimiento, Sucursales y Gestión de Vehículos.

🛠️ Tecnologías Utilizadas
Backend: Java 21, Spring Boot 4.0.6, Spring Cloud.

Persistencia: Oracle Database 19c (Cloud) con Oracle Wallet para seguridad.

Comunicación: Spring WebFlux (WebClient).

Orquestación: Docker & Docker Compose.

Herramientas: Maven, Postman, Git/GitHub.

🚀 Despliegue Local
Para ejecutar el entorno completo de NovaLogistics, asegúrate de tener instalado Docker Desktop.

Clona el repositorio:

Bash
git clone https://github.com/Darienlizama/NovaLogistics-Microservicios.git
Preparación: Asegúrate de tener la carpeta Wallet_BDNOVALOGISTIC configurada dentro de cada microservicio que requiera conexión a base de datos.

Lanzar el ecosistema: Desde la raíz del proyecto, ejecuta:

Bash
docker compose up --build
Acceso a Servicios:

Eureka Dashboard: http://localhost:8761

API Gateway: http://localhost:8080

📦 Estructura del Repositorio
Plaintext
├── api_gateway/          # Enrutamiento y seguridad
├── eureka_server/        # Service Discovery
├── ms_usuarios/          # Gestión de usuarios
├── ms_operaciones/       # Lógica logística y envíos
├── ms_comercial/         # Módulo comercial
├── ms_infraestructura/   # Soporte de recursos
└── docker-compose.yml    # Orquestación global
✒️ Guía de Trabajo Git
Utiliza estos comandos en la terminal de VS Code para mantener el proyecto sincronizado:

Preparar archivos: git add .

Confirmar cambios: git commit -m "Descripción de tu avance"

Traer cambios de compañeros: git pull origin main

Subir cambios a la nube: git push origin main

👥 Autores
Darien Lizama 

Juan Pablo Sagredo

Gerardo Silva

Proyecto Académico - 2026.
