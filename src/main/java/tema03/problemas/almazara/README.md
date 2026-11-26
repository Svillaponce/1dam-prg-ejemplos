# La almazara — versión básica

El calendario se conserva en las tres matrices de 4 × 7 del enunciado.
Las reservas se asignan en orden cronológico y solo si cabe toda la entrega.
Inicializar la campaña de nuevo elimina los datos anteriores.

## Diseño mediante métodos

| Método | Responsabilidad | Parámetros | Retorno |
| --- | --- | --- | --- |
| main | Coordinar el menú y conservar las matrices | Argumentos | void |
| mostrarMenu | Mostrar las seis opciones | Ninguno | void |
| crearCalendario | Generar capacidades, un cierre semanal e inicializar usos | Las tres matrices | void |
| dia | Obtener el nombre del día | Índice de 0 a 6 | String |
| leerEntero | Leer un entero y repetir si el formato es incorrecto | Scanner, mensaje | int |
| leerNombre | Solicitar un nombre no vacío | Scanner | String |
| calidadValida | Validar la calidad sin distinguir mayúsculas | Calidad | boolean |
| calcularLitrosAceite | Estimar el aceite según el rendimiento | Calidad, kilos | double |
| sePuedeProcesar | Comprobar la capacidad total de los días abiertos | Kilos, capacidad disponible | boolean |
| registrarAgricultor | Leer y validar la entrega, reservar y mostrar litros | Scanner, capacidad disponible, usos | void |
| planificarProcesado | Comprobar capacidad y asignar kilos desde el primer día | Nombre, kilos, capacidad disponible, usos | boolean, reserva aceptada |
| consultarAgricultor | Mostrar los días reservados y sumar sus kilos | Nombre, usos | void |
| mostrarPlanificacion | Mostrar agricultores por día y cierres | Capacidad disponible, usos | void |
| mostrarCapacidadDisponible | Mostrar capacidad diaria y total | Capacidad disponible | void |

No se incluyen las ampliaciones opcionales del PDF.
