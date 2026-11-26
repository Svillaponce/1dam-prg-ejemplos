# Unidad 4 · Estructuras dinámicas en Java

Ejecuta el método `main` de [EjemplosCollection.java](EjemplosCollection.java) desde IntelliJ IDEA con el JDK 26 del proyecto. Cada bloque de la consola corresponde a un método de la clase. Para estudiar un bloque de forma aislada, puedes dejar activa solo su llamada en `main`. Antes de ejecutar, intenta predecir el resultado indicado en la última columna.

| Bloque | Apartado del PDF | Qué comprobar en la consola |
| --- | --- | --- |
| 1. `operacionesComunes` | 1 y 2 · Estructuras dinámicas y JCF | El array tiene longitud 2 y la colección llega a tamaño 5. Tras eliminar y filtrar, el array resultante contiene `[25]`, incluso después de vaciar la colección. |
| 2. `listaDeEspera` | 3.1 · `ArrayList` | Pablo aparece primero en el índice 2 y por última vez en el 4. La modificación de la sublista cambia también la lista principal. |
| 3. `participantesUnicos` | 3.2 · `HashSet` | El segundo intento de alta de D01 devuelve `false`. Hay tres identificadores únicos antes de la baja. |
| 4. `incidencias` | 3.3 · `PriorityQueue` | Con las mismas llegadas, FIFO extrae `[3, 1, 4, 2, 1]` y la cola de prioridad extrae `[1, 1, 2, 3, 4]`. |
| 5. `inventario` | 3.4 · `HashMap` | Actualizar P01 devuelve sus 7 unidades anteriores. P01 y P02 pueden tener ambos 12 unidades. P03 existe con valor `null` y P99 está ausente. |
| 6. `recorrerColecciones` | 4.1 · Recorridos | Los tres recorridos de la lista visitan los mismos nombres en el mismo orden. El `forEach` del mapa recibe dos argumentos por asociación. |
| 7. `corregirNotas` | 4.2 · Iteradores | Se eliminan las notas negativas y 12 se sustituye por 10. El recorrido inverso devuelve `[9, 4, 10, 6, 8]`. |
| 8. `analizarNotas` | 4.3 · `Collections` | El mínimo es 3, el máximo es 10 y hay dos nueves. Tras ordenar, el 8 aparece en el índice 6 y el 11 tendría que insertarse en el índice 10. |
| 9. `compararImplementaciones` | 5 · Selección de estructuras | Las listas contienen la misma secuencia. Los conjuntos tienen los mismos elementos y los mapas las mismas asociaciones. Las implementaciones `Tree` permiten consultar rangos ordenados. |

## Interfaces, implementaciones y genéricos

Una interfaz define las operaciones disponibles. En `List<String> espera = new ArrayList<>()`, la referencia ofrece las operaciones de `List` y el objeto utiliza un array redimensionable. Se puede sustituir la implementación por `LinkedList` conservando las operaciones de `List`.

`List<E>`, `Set<E>` y `Queue<E>` forman parte de la jerarquía de `Collection<E>`. Una lista ofrece posiciones, un conjunto garantiza la unicidad y una cola determina qué elemento se procesa a continuación. `Map<K, V>` pertenece al Java Collections Framework y tiene una jerarquía propia. Sus claves son únicas y los valores pueden repetirse. `Collections` es la clase de utilidades estáticas que se usa en el bloque 8.

Los genéricos especifican el tipo de elemento, o los tipos de clave y valor en un mapa. Los primitivos usan clases envoltorio como `Integer` para `int` y `Double` para `double`. Java convierte automáticamente un `int` en `Integer` al añadirlo (*autoboxing*) y lo convierte en `int` al asignarlo a una variable primitiva (*unboxing*). El unboxing de un valor `null` lanza `NullPointerException`.

## Operaciones que conviene distinguir

- **Tamaño y capacidad.** La longitud de un array es fija. El tamaño de un `ArrayList` es la cantidad de elementos que contiene. La capacidad del array interno crece cuando hace falta espacio y está limitada por la memoria disponible.
- **Operaciones en bloque.** `addAll` incorpora elementos, `removeAll` elimina todas las coincidencias con la colección indicada y `retainAll` conserva las coincidencias. `toArray(Integer[]::new)` devuelve un array del tipo indicado con los elementos actuales.
- **Posición y valor.** Los índices de una lista empiezan en 0. `add(i, valor)` inserta y desplaza los elementos posteriores. `set(i, valor)` sustituye y devuelve el valor anterior. En una `List<Integer>`, `remove(2)` elimina por índice y `remove(Integer.valueOf(2))` elimina la primera aparición del valor 2.
- **Mutabilidad y vistas.** `List.of(...)` crea una lista no modificable. `new ArrayList<>(...)` copia sus elementos a una lista modificable. `subList(inicio, fin)` devuelve una vista que incluye `inicio` y excluye `fin`. Si modificas estructuralmente la lista original por fuera de esa vista, deja de usar la vista. `Collections.unmodifiableList(lista)` impide modificar la lista a través de la vista y refleja cambios realizados en la lista original.
- **Igualdad y hash.** `HashSet` y `HashMap` usan `hashCode()` para localizar dónde buscar y `equals()` para comprobar la igualdad de un elemento o una clave. Dos objetos iguales deben tener el mismo hash. Dos objetos distintos pueden compartirlo, lo que se denomina colisión. El orden de recorrido de estas estructuras carece de garantía.
- **Cabeza de la cola.** `peek()` consulta y `poll()` extrae. En vacío devuelven `null`, mientras que `element()` y `remove()` lanzan `NoSuchElementException`. `PriorityQueue` usa un montículo binario para mantener accesible el elemento prioritario. Para obtener los elementos en orden de prioridad, extráelos sucesivamente con `poll()`. Los empates carecen de un orden de salida garantizado. El ejemplo invierte la prioridad mediante un comparador.
- **Consultas al mapa.** `containsKey` distingue una clave ausente de una clave asociada a `null`. `getOrDefault` usa el valor por defecto cuando la clave está ausente. `keySet()`, `values()` y `entrySet()` son vistas respaldadas por el mapa. Elige `entrySet()` si necesitas la clave y su valor en cada paso.

## Recorrido y análisis de datos

Usa `for` con índice cuando necesites la posición, `for-each` para recorrer elementos directamente y `forEach` para aplicar una lambda a cada elemento o asociación. Para eliminar durante el recorrido, llama a `Iterator.remove()` después de `next()`, una vez como máximo por elemento devuelto. Para un filtro sencillo, `removeIf` elimina los elementos para los que su condición devuelve `true`.

El cursor de `ListIterator` está entre elementos. `next()` y `previous()` lo desplazan en sentidos opuestos. `set` sustituye el último elemento devuelto y `add` inserta en el cursor. En el bloque 7, justo al final del recorrido hacia delante, `nextIndex()` vale 5 y `previousIndex()` vale 4. El recorrido hacia atrás cambia la posición del cursor y conserva el orden de la lista.

`Collections.sort` ordena por el orden natural del tipo, salvo que se indique otro criterio. Ejecuta `binarySearch` sobre una lista ya ordenada con el mismo criterio. Si devuelve un valor negativo `r`, el punto de inserción es `-r - 1`. Si hay duplicados, puede devolver el índice de cualquiera de las coincidencias. `reverse` invierte la secuencia actual, `swap` intercambia dos posiciones y `shuffle` produce una mezcla que puede variar entre ejecuciones.

## Elegir la estructura

La notación Big O expresa cómo crece el coste de una operación al crecer el número de elementos `n`.

| Necesidad | Implementación | Coste o propiedad relevante |
| --- | --- | --- |
| Acceso frecuente por posición | `ArrayList` | Acceso por índice O(1). Añadir al final cuesta O(1) amortizado. Insertar en el centro puede desplazar O(n) elementos. |
| Modificar una lista en una posición ya localizada | `LinkedList` | Con un iterador situado en la posición, insertar o eliminar cambia enlaces en O(1). Localizar un índice puede costar O(n). |
| Comprobar si un elemento pertenece a un conjunto | `HashSet` | Búsqueda O(1) en promedio con una buena distribución hash. |
| Mantener elementos únicos ordenados o consultar rangos | `TreeSet` | Búsqueda, alta y baja O(log n) mediante un árbol de búsqueda equilibrado. |
| Consultar un valor por identificador | `HashMap` | Búsqueda por clave O(1) en promedio con una buena distribución hash. Buscar un valor puede costar O(n). |
| Consultar claves ordenadas o por rango | `TreeMap` | Búsqueda, alta y baja por clave O(log n) mediante un árbol de búsqueda equilibrado. |
| Atender primero los elementos más urgentes | `PriorityQueue` | Consultar la cabeza O(1). Inserción y extracción O(log n) para mantener el montículo, con posibles redimensionamientos al insertar. |

## Pruebas para hacer en clase

1. En `listaDeEspera`, predice qué ocurriría si cambiaras `subList(1, 3)` por `subList(1, 4)`. Comprueba qué elementos aparecen en la vista.
2. En `participantesUnicos`, registra dos veces un identificador nuevo. Compara los valores devueltos por `add` y el tamaño final.
3. En `incidencias`, añade un 0 al final de las llegadas. Comprueba en qué momento se extrae en cada cola.
4. En `inventario`, compara `getOrDefault("P03", 0)` con `getOrDefault("P99", 0)` antes de vaciar el mapa.
5. En `analizarNotas`, busca un 2. Predice el retorno de la búsqueda y el índice en el que lo insertarías.
