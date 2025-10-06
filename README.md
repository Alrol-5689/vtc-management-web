# VTC Management Web

Aplicación web para la autogestión de conductores VTC y el seguimiento de sus relaciones laborales. Permite registrar conductores, adherirlos a convenios colectivos, definir condiciones contractuales (anejos, pluses y comisiones) y consultar la información necesaria para preparar las nóminas mensuales.

## Características principales
- Registro y autenticación de conductores con validación de DNI/NIE y contraseñas cifradas con BCrypt.
- Selección de empresa y generación de contratos personalizados, incluyendo anejos con calendarios, salarios y políticas de incentivos.
- Gestión administrativa de convenios colectivos, anejos, pluses y empresas desde un panel de administración protegido.
- Registro diario de actividad (horas de conexión, presencia, tareas auxiliares y facturación) enlazado a cada anejo contractual.
- Consulta de nóminas guardadas por conductor y empresa, con estructura preparada para cálculos automáticos y desgloses de pluses.
- Endpoint de verificación `/db-ping` para comprobar conectividad con la base de datos MariaDB.

## Stack tecnológico
- Java 21 y Servlets Jakarta EE 10 (Servlet 6.0, JSP y JSTL 3.0).
- Hibernate ORM 6 + Jakarta Persistence 3.1 para acceso a datos.
- MariaDB como base de datos relacional.
- Maven para la gestión de dependencias y empaquetado (`war`).
- Bean Validation (Jakarta Validation + validadores propios) para reglas de dominio.

## Requisitos previos
- JDK 21 o superior disponible en el entorno de ejecución.
- Maven 3.9+.
- Servidor de aplicaciones compatible con Jakarta EE 10 (Tomcat 10.1+, Jetty 12, Payara, etc.).
- Servidor MariaDB 10.5+ con una base de datos accesible para la aplicación.

## Configuración de base de datos
1. Crear la base de datos y usuario (ajusta credenciales según tus necesidades):
   ```sql
   CREATE DATABASE vtcweb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'vtc'@'%' IDENTIFIED BY 'vtc_password';
   GRANT ALL PRIVILEGES ON vtcweb.* TO 'vtc'@'%';
   FLUSH PRIVILEGES;
   ```
2. Actualiza las propiedades de conexión en `src/main/resources/META-INF/persistence.xml` (`jakarta.persistence.jdbc.*`).
3. Por defecto la utilidad `JpaUtil` inicializa la unidad de persistencia `vtcPU` (modo `update`). Si quieres un arranque limpio que regenere el esquema usa `vtcPU-dev` (acción `drop-and-create`).

## Puesta en marcha local
1. Clona el repositorio y sitúate en la raíz del proyecto.
2. Compila y empaqueta la aplicación:
   ```bash
   mvn clean package
   ```
   El artefacto `target/vtcweb.war` se generará listo para desplegar.
3. Copia el `war` a tu contenedor de servlets (por ejemplo, `${TOMCAT_HOME}/webapps/`).
4. Arranca el servidor y accede a `http://localhost:8080/vtcweb/`.
5. En el primer acceso crea una cuenta de administrador en `/admins/create` (el formulario se habilita en modo autoservicio mientras no exista ninguno).
6. Crea las entidades necesarias desde el panel de administración (convenios, anejos, empresas) y después regístrate como conductor para empezar a operar.

## Estructura de carpetas destacada
```
src/main/java/com/vtc/
├── bootstrap/StartupListener.java      // Inicialización temprana de JPA/Hibernate
├── model/                              // Entidades de dominio (usuarios, contratos, convenios, nóminas, etc.)
├── persistence/                        // DAOs genéricos + implementaciones JPA
├── service/                            // Capa de servicios y orquestación de negocio
├── servlets/                           // Controladores web (Servlets Jakarta)
└── validation/                         // Bean Validation y validadores personalizados
src/main/webapp/
├── index.jsp                           // Página de aterrizaje y acceso a login/registro
├── auth/                               // Formularios de autenticación y alta
├── driver/                             // Vistas del área del conductor
├── admin/                              // Panel de administración por módulos
└── dailylog/, contract/, css/, js/     // Formularios específicos y recursos estáticos
```

## Roles y flujos principales
- **Conductores**: alta y login, selección de empresa activa, creación de contratos propios, registro de jornadas diarias y consulta de nóminas asociadas a cada contrato.
- **Administradores**: gestión de convenios colectivos y sus anejos, definición de pluses y políticas, creación de empresas, supervisión de conductores y asignaciones.
- **Soporte operativo**: comprobación de salud con `/db-ping`, bootstrap controlado del primer administrador y cierre continuo de convenios mediante reglas de negocio en `CollectiveAgreementService`.

## Seguridad y validaciones
- Contraseñas cifradas con BCrypt tanto para conductores (`DriverCreateServlet`) como para administradores (`AdminCreateServlet`).
- Validaciones de Bean Validation más validadores personalizados (`DniNie`, `Pin`, `UniqueUsername`) para asegurar coherencia en los datos clave.
- Gestión de sesión segmentada por rol (`driverId` y `adminId`) para proteger las vistas y acciones sensibles.

## Testing
La dependencia de JUnit 5 está configurada, pero actualmente no se incluyen pruebas automatizadas. Se recomienda añadir suites de integración para servicios y DAOs cuando se amplíe la lógica (calculadoras de nómina, conciliaciones de convenios, etc.).

## Próximos pasos sugeridos
1. Implementar la lógica en `PayslipCalculator` para automatizar los cálculos de nóminas a partir de registros diarios y políticas de incentivos.
2. Añadir pruebas de integración con una base de datos en memoria (MariaDB4j/Testcontainers) que cubran los flujos críticos de convenios y contratos.
3. Incorporar internacionalización y mensajes en castellano en las vistas JSP.
4. Automatizar la provisión de datos iniciales (seed) para entornos de desarrollo y demo.
