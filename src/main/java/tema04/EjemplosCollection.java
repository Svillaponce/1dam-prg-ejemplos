package tema04;

import java.util.*;

public class EjemplosCollection {
    static void main() {
        operacionesComunes();
        listaDeEspera();
        participantesUnicos();
        incidencias();
        inventario();
        recorrerColecciones();
        corregirNotas();
        analizarNotas();
        compararImplementaciones();
    }

    private static void operacionesComunes() {
        System.out.println("\n1. Tamaño dinámico y operaciones de Collection");
        Integer[] array = {25, 30};
        // Collection define las operaciones disponibles. ArrayList almacena los datos.
        Collection<Integer> numeros = new ArrayList<>();
        numeros.add(25); // Autoboxing de int a Integer.
        numeros.add(30);
        numeros.addAll(List.of(35, 40, 40));
        int primero = numeros.iterator().next(); // Unboxing de Integer a int.
        System.out.println("Longitud del array: " + array.length + ", tamaño de la colección: " + numeros.size());
        System.out.println("Primer número: " + primero + ", contiene 35: " + numeros.contains(35));

        numeros.remove(30); // Collection solo ofrece remove(Object).
        numeros.removeAll(List.of(40)); // Elimina las dos apariciones de 40.
        numeros.retainAll(List.of(25, 99)); // Conserva 25 y deja fuera 35; 99 no se añade.
        Integer[] copia = numeros.toArray(Integer[]::new);
        numeros.clear();
        System.out.println("Copia en array: " + Arrays.toString(copia) + ", colección vacía: " + numeros.isEmpty());
    }

    private static void listaDeEspera() {
        System.out.println("\n2. ArrayList y operaciones por posición");
        // List.of crea una lista no modificable. El constructor copia sus elementos.
        List<String> espera = new ArrayList<>(List.of("Ana", "Pablo", "Marta", "Pablo"));
        espera.add("Luis");
        espera.add(1, "Eva");
        System.out.println("Primer elemento: " + espera.getFirst() + ", índice 1: " + espera.get(1));
        System.out.println("Primer y último Pablo: " + espera.indexOf("Pablo") + ", " + espera.lastIndexOf("Pablo"));
        System.out.println("Índice de Sara: " + espera.indexOf("Sara"));
        System.out.println("Nombre sustituido: " + espera.set(0, "Nora"));

        // subList incluye el índice inicial y excluye el final. set se refleja en la lista original.
        List<String> tramo = espera.subList(1, 3);
        System.out.println("Vista antes de un cambio [1, 3): " + tramo + ", lista: " + espera);
        tramo.set(0, "Elena");
        System.out.println("Vista [1, 3): " + tramo + ", lista: " + espera);

        System.out.println("Eliminado por índice: " + espera.remove(0));
        System.out.println("Eliminado el primer Pablo: " + espera.remove("Pablo"));
        System.out.println("Lista de espera: " + espera);

        List<Integer> turnos = new ArrayList<>(List.of(1, 2, 3, 2));
        System.out.println("remove(2) quita el elemento del índice 2: " + turnos.remove(2));
        System.out.println("remove(Integer.valueOf(2)) quita el primer valor 2: "
                + turnos.remove(Integer.valueOf(2)) + ", quedan " + turnos);
    }

    private static void participantesUnicos() {
        System.out.println("\n3. HashSet y control de participantes");
        Set<String> inscritos = new HashSet<>();
        for (String id : List.of("D03", "D01", "D02", "D01")) {
            // Dos cadenas con el mismo contenido representan el mismo identificador.
            System.out.println("Alta de " + id + ": " + inscritos.add(id));
        }
        System.out.println("Inscritos únicos: " + inscritos.size());
        System.out.println("¿Está inscrito D02? " + inscritos.contains("D02"));
        System.out.println("Baja de D02: " + inscritos.remove("D02"));
        System.out.println("Inscritos (orden sin garantía): " + inscritos);
    }

    private static void incidencias() {
        System.out.println("\n4. FIFO y PriorityQueue");
        List<Integer> llegadas = List.of(3, 1, 4, 2, 1);
        Queue<Integer> fifo = new ArrayDeque<>();
        Queue<Integer> porPrioridad = new PriorityQueue<>();
        for (int prioridad : llegadas) {
            fifo.offer(prioridad);
            porPrioridad.offer(prioridad);
        }

        // El orden natural de Integer sitúa el número menor en la cabeza.
        System.out.println("Cabeza FIFO: " + fifo.peek() + ", cabeza por prioridad: " + porPrioridad.peek());
        System.out.println("Recorrido de PriorityQueue (sin orden garantizado): " + porPrioridad);
        System.out.println("Extracción FIFO: " + extraerEnOrden(fifo));
        System.out.println("Extracción por prioridad: " + extraerEnOrden(porPrioridad));
        System.out.println("peek y poll en vacío: " + porPrioridad.peek() + ", " + porPrioridad.poll());

        // add, element y remove() son las variantes que pueden lanzar excepciones.
        porPrioridad.add(5);
        System.out.println("element consulta: " + porPrioridad.element() + ", remove extrae: " + porPrioridad.remove());
        try {
            porPrioridad.remove();
        } catch (NoSuchElementException excepcion) {
            System.out.println("remove() en vacío lanza NoSuchElementException");
        }

        Queue<Integer> mayorPrimero = new PriorityQueue<>(Collections.reverseOrder());
        mayorPrimero.addAll(llegadas);
        System.out.println("Extracción con el número mayor primero: " + extraerEnOrden(mayorPrimero));
    }

    private static List<Integer> extraerEnOrden(Queue<Integer> cola) {
        List<Integer> extraidos = new ArrayList<>();
        while (!cola.isEmpty()) {
            extraidos.add(cola.poll());
        }
        return extraidos;
    }

    private static void inventario() {
        System.out.println("\n5. HashMap e inventario por código");
        Map<String, Integer> existencias = new HashMap<>();
        existencias.put("P02", 12);
        existencias.put("P01", 7);
        System.out.println("Existencias anteriores de P01: " + existencias.put("P01", 12));
        System.out.println("P01 ahora tiene " + existencias.get("P01") + " unidades");
        System.out.println("¿Hay un producto con 12 unidades? " + existencias.containsValue(12));
        System.out.println("P99 ausente, valor por defecto: " + existencias.getOrDefault("P99", 0));

        // En HashMap, null también puede representar un valor almacenado.
        existencias.put("P03", null);
        System.out.println("P03: " + existencias.get("P03") + ", existe: " + existencias.containsKey("P03"));
        System.out.println("P99: " + existencias.get("P99") + ", existe: " + existencias.containsKey("P99"));
        System.out.println("Claves: " + existencias.keySet() + ", valores: " + existencias.values());
        for (Map.Entry<String, Integer> entrada : existencias.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
        System.out.println("Valor retirado de P02: " + existencias.remove("P02"));
        System.out.println("Asociaciones restantes: " + existencias.size());
        existencias.clear();
        System.out.println("Inventario vacío: " + existencias.isEmpty());
    }

    private static void recorrerColecciones() {
        System.out.println("\n6. for, for-each y forEach");
        List<String> alumnos = List.of("Ana", "Luis", "Marta");
        for (int indice = 0; indice < alumnos.size(); indice++) {
            System.out.println("Índice " + indice + ": " + alumnos.get(indice));
        }
        for (String alumno : alumnos) {
            System.out.println("for-each: " + alumno);
        }
        // La lambda recibe un alumno cada vez y ejecuta la acción a la derecha de ->.
        alumnos.forEach(alumno -> System.out.println("forEach: " + alumno));

        Map<String, Integer> notas = Map.of("Ana", 8, "Luis", 6, "Marta", 9);
        notas.forEach((alumno, nota) -> System.out.println(alumno + " obtuvo un " + nota));
    }

    private static void corregirNotas() {
        System.out.println("\n7. Iterator y ListIterator");
        List<Integer> originales = List.of(8, -1, 6, 12, 4, -3, 9);
        List<Integer> notas = new ArrayList<>(originales);
        Iterator<Integer> iterador = notas.iterator();
        while (iterador.hasNext()) {
            if (iterador.next() < 0) {
                iterador.remove(); // Elimina el último valor que devolvió next().
            }
        }
        List<Integer> conRemoveIf = new ArrayList<>(originales);
        conRemoveIf.removeIf(nota -> nota < 0);
        System.out.println("Filtradas: " + notas + ", mismo resultado con removeIf: " + notas.equals(conRemoveIf));

        ListIterator<Integer> cursor = notas.listIterator();
        while (cursor.hasNext()) {
            if (cursor.next() > 10) {
                cursor.set(10);
            }
        }
        // Al terminar el avance, el cursor está detrás del último elemento.
        System.out.println("Siguiente índice: " + cursor.nextIndex() + ", anterior: " + cursor.previousIndex());
        List<Integer> alReves = new ArrayList<>();
        while (cursor.hasPrevious()) {
            alReves.add(cursor.previous());
        }
        System.out.println("Lectura hacia atrás: " + alReves + ", lista corregida: " + notas);
        cursor.add(7); // Tras retroceder hasta el inicio, inserta antes del primer elemento.
        System.out.println("Lista con una nota insertada mediante el cursor: " + notas);
    }

    private static void analizarNotas() {
        System.out.println("\n8. Utilidades de Collections");
        List<Integer> notas = new ArrayList<>(List.of(7, 4, 9, 6, 9, 3, 8, 5, 10, 7));
        System.out.println("Mínimo: " + Collections.min(notas) + ", máximo: " + Collections.max(notas));
        System.out.println("Número de nueves: " + Collections.frequency(notas, 9));

        Collections.sort(notas);
        System.out.println("Orden natural: " + notas);
        System.out.println("Índice del 8: " + Collections.binarySearch(notas, 8));
        int resultado = Collections.binarySearch(notas, 11);
        // Para una búsqueda sin coincidencia, resultado = -(punto de inserción) - 1.
        System.out.println("Búsqueda del 11: " + resultado + ", punto de inserción: " + (-resultado - 1));

        Collections.reverse(notas);
        System.out.println("Orden descendente tras invertir: " + notas);
        Collections.swap(notas, 0, 1);
        System.out.println("Dos primeras posiciones intercambiadas: " + notas);
        Collections.shuffle(notas);
        System.out.println("Mezcladas: " + notas);

        List<Integer> vista = Collections.unmodifiableList(notas);
        notas.add(6);
        System.out.println("La vista no modificable refleja el añadido: " + vista);
    }

    private static void compararImplementaciones() {
        System.out.println("\n9. Elegir una implementación");
        List<Integer> datos = List.of(30, 10, 20, 10);
        List<Integer> listaArray = new ArrayList<>(datos);
        List<Integer> listaEnlazada = new LinkedList<>(datos);
        System.out.println("Misma secuencia: " + listaArray.equals(listaEnlazada)
                + ", valor en el índice 2: " + listaArray.get(2) + " / " + listaEnlazada.get(2));

        Set<Integer> conjuntoHash = new HashSet<>(datos);
        TreeSet<Integer> conjuntoOrdenado = new TreeSet<>(datos);
        System.out.println("Mismos elementos únicos: " + conjuntoHash.equals(conjuntoOrdenado));
        System.out.println("TreeSet: " + conjuntoOrdenado + ", rango [10, 30): " + conjuntoOrdenado.subSet(10, 30));

        Map<Integer, String> mapaHash = new HashMap<>(Map.of(30, "Ana", 10, "Luis", 20, "Marta"));
        TreeMap<Integer, String> mapaOrdenado = new TreeMap<>(mapaHash);
        System.out.println("Mismas asociaciones: " + mapaHash.equals(mapaOrdenado));
        System.out.println("TreeMap: " + mapaOrdenado + ", claves menores de 30: " + mapaOrdenado.headMap(30));
    }
}
