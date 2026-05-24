# TicketFlow - Plataforma de Gestión de Eventos y Venta de Entradas

TicketFlow es una aplicación de escritorio desarrollada en **Java 17** y **JavaFX**, siguiendo las mejores prácticas de Arquitectura de Software, Metodologías Orientadas a Objetos (SOLID) y Patrones de Diseño.

## Características Principales

1. **Gestión de Usuarios y Roles**: Separación entre Usuario (comprador) y Administrador.
2. **Gestión de Eventos y Recintos**: Creación de eventos (Conciertos, Teatro, etc.), recintos y mapas de asientos interactivos.
3. **Flujo de Compras Completo**: Selección de zona/asientos, adición de servicios extra (VIP, Seguro) usando el patrón *Decorator*, y procesamiento de pagos con el patrón *Adapter*.
4. **Patrones de Diseño Implementados**:
   - *Creacionales*: Singleton (GestorSesion), Factory Method (EventoCreator), Builder (CompraBuilder).
   - *Estructurales*: Decorator (ServiciosAdicionales), Adapter (Pasarelas de Pago), Facade (PlataformaFacade).
   - *De Comportamiento*: Strategy (Descuentos), Observer (Notificaciones UI/Consola), Command (Deshacer Compras Admin).
5. **Reportes**: Generación de reportes PDF (usando Apache PDFBox) y CSV (usando OpenCSV) con métricas del sistema.
6. **Diseño Visual**: Tema oscuro moderno aplicando CSS para los componentes JavaFX.

## Requisitos Previos

- Java Development Kit (JDK) 17 o superior.
- Apache Maven 3.8 o superior.

## Instrucciones de Ejecución

1. Clona el repositorio o descarga el código fuente.
2. Abre una terminal en la raíz del proyecto (donde se encuentra `pom.xml`).
3. Compila el proyecto ejecutando:
   ```bash
   mvn clean compile
   ```
4. Ejecuta la aplicación usando el plugin de JavaFX de Maven:
   ```bash
   mvn javafx:run
   ```

## Datos de Prueba Iniciales

Para facilitar la evaluación de la aplicación, el sistema ya viene con datos pre-cargados (ver `DataInitializer.java`):

- **Administrador**: 
  - Correo: `admin@gmail.com`
  - Contraseña: `admin123`
- **Usuarios de Prueba**:
  - Correo: `usuario1@gmail.com`
  - Contraseña: `contraseña1` (y así del 1 al 5)
  
¡Puedes iniciar sesión directamente usando estas credenciales y probar la aplicación!
