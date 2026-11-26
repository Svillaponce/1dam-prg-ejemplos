package tema03.problemas.almazara;

import java.util.Scanner;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        int[][] capacidadInicial = new int[4][7];
        int[][] capacidadDisponible = new int[4][7];
        String[][] diasUsados = new String[4][7];
        Scanner sc = new Scanner(System.in);
        boolean inicializada = false;
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Elige una opción:");
            if (opcion == 1) {
                crearCalendario(capacidadInicial, capacidadDisponible, diasUsados);
                inicializada = true;
                System.out.println("Campaña inicializada.");
            } else if (opcion >= 2 && opcion <= 5 && !inicializada) {
                System.out.println("Primero debes inicializar la campaña.");
            } else {
                switch (opcion) {
                    case 2 -> registrarAgricultor(sc, capacidadDisponible, diasUsados);
                    case 3 -> consultarAgricultor(leerNombre(sc), diasUsados);
                    case 4 -> mostrarPlanificacion(capacidadDisponible, diasUsados);
                    case 5 -> mostrarCapacidadDisponible(capacidadDisponible);
                    case 6 -> System.out.println("Fin del programa.");
                    default -> System.out.println("Opción no válida.");
                }
            }
        } while (opcion != 6);
    }

    public static void mostrarMenu() {
        System.out.println("\n1. Inicializar campaña (borra la información anterior)");
        System.out.println("2. Registrar agricultor");
        System.out.println("3. Consultar agricultor");
        System.out.println("4. Mostrar planificación");
        System.out.println("5. Mostrar capacidad disponible");
        System.out.println("6. Salir");
    }

    public static void crearCalendario(int[][] capacidadInicial, int[][] capacidadDisponible,
                                       String[][] diasUsados) {
        Random random = new Random();
        for (int semana = 0; semana < 4; semana++) {
            int cierre = random.nextInt(7);
            for (int d = 0; d < 7; d++) {
                int capacidad = d == cierre ? -1 : 500 + random.nextInt(1001);
                capacidadInicial[semana][d] = capacidad;
                capacidadDisponible[semana][d] = capacidad;
                diasUsados[semana][d] = "*";
            }
        }
    }

    public static String dia(int numeroDia) {
        String[] nombres = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        return nombres[numeroDia];
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    public static String leerNombre(Scanner sc) {
        String nombre;
        do {
            System.out.println("Nombre del agricultor:");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            }
        } while (nombre.isEmpty());
        return nombre;
    }

    public static boolean calidadValida(String calidad) {
        return calidad.equalsIgnoreCase("suprema") || calidad.equalsIgnoreCase("buena")
                || calidad.equalsIgnoreCase("regular") || calidad.equalsIgnoreCase("mala");
    }

    public static double calcularLitrosAceite(String calidad, int kilos) {
        int rendimiento;
        if (calidad.equalsIgnoreCase("suprema")) {
            rendimiento = 28;
        } else if (calidad.equalsIgnoreCase("buena")) {
            rendimiento = 22;
        } else if (calidad.equalsIgnoreCase("regular")) {
            rendimiento = 15;
        } else if (calidad.equalsIgnoreCase("mala")) {
            rendimiento = 8;
        } else {
            throw new IllegalArgumentException("Calidad no válida.");
        }
        return kilos * (rendimiento / 100.0);
    }

    public static boolean sePuedeProcesar(int kilos, int[][] capacidadDisponible) {
        int total = 0;
        for (int[] semana : capacidadDisponible) {
            for (int capacidad : semana) {
                if (capacidad > 0) {
                    total += capacidad;
                }
            }
        }
        return kilos > 0 && kilos <= total;
    }

    public static void registrarAgricultor(Scanner sc, int[][] capacidadDisponible, String[][] diasUsados) {
        String nombre = leerNombre(sc);
        int kilos;
        do {
            kilos = leerEntero(sc, "Kilos de aceitunas (mayores que 0):");
        } while (kilos <= 0);

        String calidad;
        do {
            System.out.println("Calidad: suprema, buena, regular o mala:");
            calidad = sc.nextLine().trim();
        } while (!calidadValida(calidad));

        if (!planificarProcesado(nombre, kilos, capacidadDisponible, diasUsados)) {
            System.out.println("Entrega rechazada: no hay capacidad suficiente.");
            return;
        }
        System.out.printf("Entrega registrada. Aceite estimado: %.2f litros.%n",
                calcularLitrosAceite(calidad, kilos));
    }

    public static boolean planificarProcesado(String nombre, int kilos, int[][] capacidadDisponible,
                                              String[][] diasUsados) {
        if (!sePuedeProcesar(kilos, capacidadDisponible)) {
            return false;
        }
        int pendientes = kilos;
        for (int semana = 0; semana < 4 && pendientes > 0; semana++) {
            for (int d = 0; d < 7 && pendientes > 0; d++) {
                if (capacidadDisponible[semana][d] > 0) {
                    int asignados = Math.min(pendientes, capacidadDisponible[semana][d]);
                    String reserva = nombre + " - " + asignados + " kg";
                    if (diasUsados[semana][d].equals("*")) {
                        diasUsados[semana][d] = reserva;
                    } else {
                        diasUsados[semana][d] += " | " + reserva;
                    }
                    capacidadDisponible[semana][d] -= asignados;
                    pendientes -= asignados;
                }
            }
        }
        return true;
    }

    public static void consultarAgricultor(String nombre, String[][] diasUsados) {
        Pattern patron = Pattern.compile("(?:^| \\| )" + Pattern.quote(nombre)
                + " - ([0-9]+) kg(?= \\| |$)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        int total = 0;
        for (int semana = 0; semana < 4; semana++) {
            for (int d = 0; d < 7; d++) {
                Matcher coincidencia = patron.matcher(diasUsados[semana][d]);
                int kilosDia = 0;
                while (coincidencia.find()) {
                    kilosDia += Integer.parseInt(coincidencia.group(1));
                }
                if (kilosDia > 0) {
                    System.out.println("Semana " + (semana + 1) + " - " + dia(d) + ": " + kilosDia + " kg");
                    total += kilosDia;
                }
            }
        }
        if (total == 0) {
            System.out.println("No se han encontrado reservas para ese agricultor.");
        }
        System.out.println("Total procesado: " + total + " kg");
    }

    public static void mostrarPlanificacion(int[][] capacidadDisponible, String[][] diasUsados) {
        for (int semana = 0; semana < 4; semana++) {
            System.out.println("SEMANA " + (semana + 1));
            for (int d = 0; d < 7; d++) {
                String uso = capacidadDisponible[semana][d] == -1 ? "CERRADO" : diasUsados[semana][d];
                System.out.println(dia(d) + ": " + uso);
            }
        }
    }

    public static void mostrarCapacidadDisponible(int[][] capacidadDisponible) {
        int total = 0;
        for (int semana = 0; semana < 4; semana++) {
            System.out.println("SEMANA " + (semana + 1));
            for (int d = 0; d < 7; d++) {
                int capacidad = capacidadDisponible[semana][d];
                System.out.println(dia(d) + ": " + (capacidad == -1 ? "CERRADO" : capacidad + " kg"));
                if (capacidad > 0) {
                    total += capacidad;
                }
            }
        }
        System.out.println("Capacidad disponible total: " + total + " kg");
    }
}
