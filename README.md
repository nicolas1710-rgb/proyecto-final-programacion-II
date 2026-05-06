# proyecto-final-programacion-II
PENSAMIENTO COMPUTACIONAL (RF-043)
¿Qué se solicita finalmente?
Una plataforma de gestión de eventos y venta de entradas desarrollada en Java con interfaz JavaFX. El sistema debe permitir a usuarios explorar eventos, seleccionar asientos, comprar entradas, agregar servicios adicionales y gestionar sus compras. Los administradores deben poder gestionar el catálogo completo (eventos, recintos, zonas, asientos, usuarios) y visualizar métricas de ventas. La plataforma exporta reportes en CSV/PDF y registra incidencias operativas.

¿Qué información es relevante?
Datos del dominio:

Usuario: idUsuario, nombre, correo, teléfono, métodos de pago simulados
Evento: idEvento, nombre, categoría, ciudad, fecha/hora, estado (Borrador, Publicado, Pausado, Cancelado, Finalizado), políticas
Recinto: idRecinto, nombre, dirección, ciudad, zonas asociadas
Zona: idZona, nombre, capacidad, precio base, configuración de asientos
Asiento: idAsiento, fila, número, estado (Disponible, Reservado, Vendido, Bloqueado)
Compra: idCompra, usuario, evento, fecha, total, estado (Creada, Pagada, Confirmada, Cancelada, Reembolsada, Incidencia), ítems y servicios adicionales
Entrada: idEntrada, zona, asiento, precio final, estado (Activa, Usada, Anulada)
Pago: método, monto, fecha, referencia
Tarifa: tipo, precio, condiciones aplicables
Incidencia: idIncidencia, tipo, descripción, fecha, entidad afectada

Reglas de negocio clave:

Una compra puede modificarse solo antes del pago
Una entrada se genera solo después del pago confirmado
La disponibilidad de asientos debe actualizarse en tiempo real para evitar doble venta
Las cancelaciones siguen políticas configurables por evento
Los reportes filtran por periodo, zona, tipo de servicio, estado de compra


¿Cómo se agrupa la información relevante? (clases y estructura)
El sistema se organiza en cuatro capas lógicas:
Capa de dominio (entidades del negocio): Usuario, Evento, Recinto, Zona, Asiento, Compra, ItemCompra, Entrada, Pago, Tarifa, ServicioAdicional, Incidencia
Capa de servicio (lógica de negocio): GestorEventos, GestorCompras, GestorUsuarios, GestorRecintos, GestorReportes, GestorIncidencias
Capa de soporte / patrones: Interfaces de estrategia (EstrategiaPago, EstrategiaCancelacion), decoradores (ServicioDecorador), factories (EventoFactory, CompraFactory), observadores (NotificadorEstado)
Capa de presentación (JavaFX): Pantallas de usuario (explorador de eventos, selector de asientos, carrito, historial) y pantallas de administrador (gestión CRUD, métricas con Charts, reportes)

¿Qué funcionalidades se solicitan?
Funcionalidades del usuario (RF-001 a RF-011):

Registro, login, gestión de perfil
Exploración de eventos con filtros (fecha, ciudad, categoría, precio)
Selección de zona/asiento con mapa visual
Creación, modificación y cancelación de compras antes del pago
Proceso de pago y visualización de comprobantes
Seguimiento del estado de la compra
Agrégar servicios adicionales (VIP, seguro, merchandising, parqueadero, acceso preferencial)
Historial de compras con filtros, descarga en CSV/PDF

Funcionalidades del administrador (RF-012 a RF-019):

CRUD completo de usuarios, eventos, recintos, zonas y asientos
Gestión del ciclo de vida del evento (publicar, pausar, cancelar)
Control de disponibilidad de asientos (habilitar, bloquear, liberar)
Gestión de compras (consultar, reasignar, cancelar, reembolsar)
Registro y consulta de incidencias
Panel de métricas con JavaFX Charts (ventas por periodo, ocupación por zona, ingresos por servicios, tasa de cancelación, top eventos)
Generador de reportes CSV/PDF con Apache POI / PDFBox


¿Cómo se distribuyen las funcionalidades?
CapaResponsabilidadModelo (entidades)Representar el estado del dominio; sin lógica de presentaciónServicioAplicar reglas de negocio; coordinar entre entidadesRepositorio / DAOAcceso a datos (colecciones en memoria o archivo)Controlador JavaFXManejar eventos de UI y delegar a serviciosVista (FXML)Mostrar información al usuarioSoporte (patrones)Estrategias de pago/cancelación, decoradores de servicios, notificaciones
Se aplica el principio SRP: cada clase tiene una única razón de cambio. Los servicios no conocen la UI; las vistas no conocen las reglas de negocio.

¿Qué debo hacer para probar las funcionalidades?
Datos de prueba inicializados (RF-045): Al iniciar la aplicación se cargan en memoria: 3–5 usuarios con métodos de pago, 3 recintos con zonas y asientos numerados, 5–8 eventos en distintos estados, compras en todos los estados posibles, pagos asociados, incidencias de ejemplo.
Casos de prueba por funcionalidad:

Registrar un usuario → verificar que se guarda con ID único
Seleccionar asiento disponible → verificar que su estado cambia a Reservado
Intentar comprar un asiento ya vendido → verificar que se genera Incidencia y no se permite la compra
Pagar una compra → verificar que se generan las Entradas y el estado cambia a Pagada
Cancelar una compra pagada con seguro → verificar que aplica política de reembolso
Exportar reporte → verificar que el archivo CSV/PDF se genera con los datos correctos
Admin cancela evento con compras activas → verificar notificación de cambio de estado


¿Qué puedo reutilizar?

Patrón Strategy para métodos de pago y políticas de cancelación: todas las variantes implementan la misma interfaz
Patrón Decorator para servicios adicionales: cada servicio envuelve la entrada base y añade precio/comportamiento
Patrón Observer para notificaciones: cualquier cambio de estado en Evento o Compra notifica automáticamente a los suscriptores (usuario, admin, log)
Patrón Factory para crear objetos complejos (Compra con sus ítems, Evento con sus zonas) sin exponer la construcción
Patrón Singleton para gestores únicos de sistema (catálogo de eventos, sesión de usuario actual)
Clase base EntidadBase con id, fechaCreacion, toString() reutilizable en todas las entidades
Interfaz Exportable implementada por Compra y Reporte para estandarizar la exportación CSV/PDF


¿Cómo pruebo/escribo la solución en Java?
java// Estructura de paquetes sugerida
com.eventos.plataforma
├── modelo/          // Entidades: Usuario, Evento, Compra, Entrada...
├── servicio/        // GestorCompras, GestorEventos, GestorReportes...
├── repositorio/     // Colecciones en memoria (HashMap, ArrayList)
├── patron/
│   ├── creacional/  // Singleton, Factory, Builder
│   ├── estructural/ // Decorator, Adapter, Facade
│   └── comportamiento/ // Strategy, Observer, Command
├── ui/              // Controladores JavaFX y archivos FXML
└── util/            // Exportadores CSV/PDF, validadores, helpers

// Ejemplo de prueba de integración básica
GestorCompras gestor = GestorCompras.getInstance();
Compra c = gestor.crearCompra(usuario, evento, List.of(asiento1, asiento2));
gestor.agregarServicio(c, new ServicioVIP(new EntradaBase(asiento1)));
Pago pago = gestor.procesarPago(c, new TarjetaStrategy("4111..."));
assert c.getEstado() == EstadoCompra.PAGADA;
assert c.getEntradas().size() == 2;
assert asiento1.getEstado() == EstadoAsiento.VENDIDO;

3. DOCUMENTO DE PATRONES ELEGIDOS (RF-049, RF-050, RF-051)
PATRONES CREACIONALES (Mínimo 3)

PATRÓN 1: SINGLETON – GestorEventos
Requisitos que resuelve:

RF-003 (Explorar eventos disponibles con filtros)
RF-013 (Gestionar eventos: crear/actualizar/eliminar/listar)
RF-024 (Publicar/pausar/cancelar eventos)
RF-045 (Datos inicializados en la aplicación)

Problema en el contexto:
La plataforma necesita un catálogo centralizado de eventos que sea único en toda la aplicación. Sin Singleton, múltiples instancias causan inconsistencias: usuarios verían eventos diferentes, los cambios de estado no serían visibles globalmente, y la sincronización sería imposible.
Por qué Singleton y no otro:

No es Factory (no crea variantes, necesita una única instancia)
No es Builder (no construye paso a paso, ya existe el objeto)
Singleton garantiza acceso único al catálogo, evitando duplicación de datos en memoria

Beneficio en el dominio:
Todo cliente accede al mismo catálogo de eventos. Un administrador publica un evento → automáticamente visible a todos los usuarios. Un asiento se vende → el estado es consistente globalmente.

PATRÓN 2: FACTORY METHOD – EventoFactory
Requisitos que resuelve:

RF-013 (Gestionar eventos: crear eventos de distintos tipos)
RF-023 (Crear, actualizar, eliminar y consultar eventos)
RF-045 (Estructura del proyecto con datos inicializados)

Problema en el contexto:
Los eventos tienen tipos diferentes (Concierto, Teatro, Conferencia) con reglas de inicialización distintas:

Concierto: múltiples zonas (VIP, Preferencial, General), escenario, políticas complejas
Teatro: distribución acústica especial, menos zonas
Conferencia: layout sencillo, solo asientos numerados

Si el cliente creara manualmente cada evento y configurara sus zonas y asientos, tendría código duplicado y frágil.
Por qué Factory y no Singleton:

Singleton maneja la unicidad del catálogo
Factory encapsula la creación variada según el tipo de evento
Factory es extensible: agregar "Evento Deportivo" solo requiere nueva subclase

Beneficio en el dominio:
Cada tipo de evento se inicializa correctamente con sus zonas y asientos preconfigurados. El administrador solo especifica "crear Concierto" y la Factory genera automáticamente 3 zonas con asientos numerados y políticas apropiadas.

PATRÓN 3: BUILDER – CompraBuilder
Requisitos que resuelve:

RF-005 (Seleccionar entradas por zona/asientos)
RF-006 (Crear, modificar y cancelar una compra antes de pagar)
RF-009 (Agregar servicios adicionales)
RF-034 (Crear compras nuevas)
RF-035 (Modificar una compra antes de pagar)

Problema en el contexto:
Una Compra es compleja: usuario (requerido) + evento (requerido) + 1 a N entradas + servicios opcionales (VIP, seguro, merchandising) + pago (null hasta confirmación).
Un constructor con todos los parámetros sería ilegible: new Compra(usuario, evento, items, servicios, tarifa, metodo, ...). Además, el usuario construye la compra paso a paso en la UI: primero selecciona asientos, luego agrega servicios, finalmente paga.
Por qué Builder y no Factory:

Factory crea variantes del mismo tipo
Builder permite construir un objeto único y personalizado paso a paso
Builder valida cada paso y permite modificaciones antes del "build" final
Builder refleja el flujo real del usuario

Beneficio en el dominio:
Interfaz fluida y legible. El usuario selecciona asientos → agrega VIP → agrega seguro → valida total. Si cambia de opinión (RF-035), borra un servicio sin crear una nueva compra. Solo cuando hace "Confirmar", se crea la Compra final con estado PAGADA.

PATRONES ESTRUCTURALES (Mínimo 3)

PATRÓN 1: DECORATOR – ServicioDecorador (Servicios Adicionales)
Requisitos que resuelve:

RF-009 (Agregar servicios adicionales: VIP, seguro, merchandising, parqueadero, acceso preferencial)
RF-035 (Modificar una compra antes de pagar)

Problema en el contexto:
Los servicios adicionales son opcionales y combinables. Un usuario puede comprar: entrada base, entrada+VIP, entrada+VIP+Seguro, entrada+Seguro+Merchandising+Parqueadero, etc.
Si modelamos cada combinación como clase (EntradaVIP, EntradaVIPSeguro, ...), tenemos explosión combinatoria. Con N servicios tenemos 2^N clases.
Por qué Decorator y no Herencia:

Herencia crearía O(2^n) subclases para n servicios
Decorator permite composición dinámica: un servicio envuelve otro, que envuelve otro
Es flexible en tiempo de ejecución

Beneficio en el dominio:
Combinaciones ilimitadas sin explosion de clases. Fácil agregar nuevos servicios (ej: "Meet&Greet") sin tocar código existente. Cada decorador es responsable solo de su servicio, respetando SRP.

PATRÓN 2: ADAPTER – ReporteAdapter
Requisitos que resuelve:

RF-011 (Descargar reportes en CSV o PDF)
RF-046 (Generador de Reportes: exportar CSV/PDF con Apache POI / PDFBox)

Problema en el contexto:
La aplicación exporta reportes en dos formatos:

CSV: usa Apache POI (API: XSSFWorkbook, XSSFSheet, XSSFRow)
PDF: usa PDFBox (API: PDDocument, PDPage, PDPageContentStream)

Cada librería tiene interfaz distinta. Si GestorReportes acoplara ambas, sería un caos de if-else.
Por qué Adapter y no Decorator:

Decorator agrega comportamiento a un objeto existente
Adapter transforma la interfaz de un objeto externo para hacerla compatible con el código cliente
Aquí se adaptan dos librerías externas a una interfaz uniforme

Beneficio en el dominio:
GestorReportes ve una interfaz uniforme IExportador, sin conocer los detalles de Apache POI o PDFBox. Si en el futuro se reemplaza una librería, solo cambia el adaptador correspondiente.

PATRÓN 3: FACADE – GestorCompras
Requisitos que resuelve:

RF-006 (Crear, modificar y cancelar una compra)
RF-007 (Pagar la compra)
RF-008 (Visualizar estado de la compra)
RF-009 (Agregar servicios adicionales)
RF-010 (Consultar historial de compras)
RF-016 (Gestionar compras: consultar, cancelar, registrar reembolsos)

Problema en el contexto:
El flujo de compra es complejo y requiere orquestar muchas operaciones:

Crear Compra (Builder)
Agregar ítems y servicios (Decorator)
Validar disponibilidad de asientos
Procesar pago (Strategy)
Generar entradas
Notificar observadores
Registrar incidencias si hay error

Si cada controlador JavaFX llamara directamente a cada componente, habría acoplamiento alto y código duplicado.
Por qué Facade:

Simplifica la interfaz compleja del subsistema de compras
Desacopla la UI del dominio
Centraliza la lógica de orquestación

Beneficio en el dominio:
La UI solo ve un método simple: GestorCompras.procesarCompra(usuario, evento, asientos, servicios, metodoPago) que internamente coordina Builder, Decorator, Pago, Entrada, Notificación e Incidencia.

PATRONES DE COMPORTAMIENTO (Mínimo 3)

PATRÓN 1: STRATEGY – IEstrategiaPago
Requisitos que resuelve:

RF-007 (Pagar la compra y consultar comprobantes)
RF-021 (Gestionar métodos de pago simulados)

Problema en el contexto:
Existen múltiples métodos de pago con lógicas distintas:

Tarjeta de crédito: valida número, fecha, CVV, envía a gateway
Transferencia bancaria: genera referencia, espera confirmación asíncrona
Billetera digital: consulta saldo, autoriza descuento

Si Pago tuviera if-else para cada método, violaría OCP (Open/Closed Principle).
Por qué Strategy y no Polymorfismo simple:

Strategy permite intercambiar algoritmos en tiempo de ejecución
El contexto (Pago) no conoce las implementaciones concretas
Fácil agregar nuevo método sin modificar Pago

Beneficio en el dominio:
El usuario elige método de pago en la UI → se inyecta la estrategia correspondiente en Pago → se ejecuta sin que Pago sepa los detalles. Si la empresa agrega "Pago con Criptomonedas" mañana, solo crea nueva clase que implemente IEstrategiaPago.

PATRÓN 2: STRATEGY – IEstrategiaCancelacion
Requisitos que resuelve:

RF-008 (Visualizar estado de la compra)
RF-036 (Cancelar una compra según reglas/políticas)
RF-003 (Explorar eventos disponibles; algunos eventos tienen políticas diferentes)

Problema en el contexto:
Las políticas de reembolso varían por evento:

Concierto: 90% si cancela 24h antes, 50% si 24-48h, 0% después
Conferencia: 100% si cancela 48h antes, 75% si 24-48h, 0% después
Teatro: política personalizada según artista

Si Compra.cancelar() tuviera if-else por tipo de evento, sería frágil.
Por qué Strategy:

Cada política es una estrategia distinta
El evento especifica qué estrategia aplicar
Descacopla la lógica de cancelación de la lógica del dominio

Beneficio en el dominio:
Un administrador configura la política de reembolso al crear un evento. Cuando un usuario cancela, Compra consulta su estrategia y calcula el reembolso correctamente. Nueva política → nueva clase Strategy, sin modificar Compra.

PATRÓN 3: OBSERVER – IObservador
Requisitos que resuelve:

RF-008 (Visualizar estado de la compra: Creada, Pagada, Confirmada, Cancelada, Reembolsada, Incidencia)
RF-017 (Registrar incidencias y cambios de estado)
RF-018 (Panel de métricas)

Problema en el contexto:
Cuando cambia el estado de una Compra o Evento, múltiples partes necesitan saberlo:

El usuario necesita notificación en su panel
El administrador necesita actualizar el historial
El sistema debe registrar la incidencia
Las métricas deben recalcularse

Sin Observer, Compra.confirmarPago() tendría que conocer y llamar manualmente a cada subsistema (Notificador, RegistroIncidencias, ActualizadorMetricas), acoplamiento alto.
Por qué Observer:

Desacopla el sujeto (Compra/Evento) de los observadores
Múltiples observadores pueden reaccionar sin que el sujeto los conozca
Fácil agregar nuevos observadores

Beneficio en el dominio:
Cuando Compra cambia a estado PAGADA:

NotificadorUsuario envía alerta al usuario
RegistradorIncidencias crea entrada de log
ActualizadorMetricas recalcula % de cancelaciones
GráficoVentas actualiza en tiempo real

Todo ocurre automáticamente sin que Compra conozca detalles.

PATRÓN 4 (ADICIONAL): COMMAND – IComando
Requisitos que resuelve:

RF-006 (Crear, modificar y cancelar una compra)
RF-016 (Gestionar compras: consultar, cancelar, registrar reembolsos)
RF-035 (Modificar una compra antes de pagar)

Problema en el contexto:
Las operaciones sobre compras pueden ser complejas y necesitan poder:

Ejecutarse (procesar pago, cancelar)
Deshacerse (undo): si el pago falla, restaurar disponibilidad de asientos
Registrarse en un historial de auditoría
Encadenarse (macro: pagar + enviar confirmación + registrar log)

Si cada operación fuera un método directo en Compra, no habría forma de deshacer o encadenar.
Por qué Command:

Encapsula cada operación como un objeto
Permite undo/redo
Permite cola de comandos para procesar después
Permite auditoría

Beneficio en el dominio:
Un usuario paga una compra. Si la transacción falla, se ejecuta deshacer() automáticamente, liberando asientos y cancelando reservas. El historial queda registrado para auditoría.
