package tema03.problemas.parking;

import java.util.Arrays;
import java.util.Scanner;

public class ParkingInteligente {
    static String[][] tipoPlaza = {
            {"N", "N", "M", "N", "E", "N", "M", "N", "N"},
            {"N", "M", "N", "E", "E", "E", "N", "M", "N"},
            {"N", "N", "N", "N", "E", "N", "N", "N", "N"},
            {"M", "N", "N", "E", "E", "E", "N", "N", "M"},
            {"N", "M", "N", "N", "E", "N", "N", "M", "N"},
            {"N", "N", "M", "N", "N", "N", "M", "N", "N"}
    };
    static String[][] matriculas = new String[6][9];
    static int[][] tiemposEstimados = new int[6][9];
    static int[][] usos = new int[6][9];

    static void main() {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        String matricula = "";
        String tipoVehiculo = "";
        int tiempoEstimado = 0;
        rellenarMatrices();
        do {
            mostrarMenu();
            System.out.println("Elige una opcion 1-5: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1 -> {
                    do {
                        System.out.println("Ingresa la matricula 4 + 3: ");
                        matricula = sc.nextLine().trim().toUpperCase();
                    } while (!matricula.matches("\\d{4}[A-Z]{3}"));
                    System.out.println("Ingresa el tipo de plaza N/M/E: ");
                    tipoVehiculo = sc.nextLine().trim().toUpperCase();
                    tiempoEstimado = 0;
                    do {
                        System.out.println("Ingresa el tiempo estimado: ");
                        tiempoEstimado = sc.nextInt();
                    } while (tiempoEstimado < 30 || tiempoEstimado > 1440);
                    sc.nextLine();
                    //mostrarMatrices();
                    entradaVehiculo(matricula, tipoVehiculo, tiempoEstimado);
                    mostrarMatrices();
                }
                case 2 -> {
                    System.out.println("Ingresa la matricula a RETIRAR 4 + 3: ");
                    matricula = sc.nextLine().trim().toUpperCase();
                    retirarVehiculo(matricula, tipoVehiculo, tiempoEstimado);
                }
                case 3 -> {
                    mostrarMatrices();
                }
                case 4 -> {
                    calcularMostrarMasUtilizado();
                }
                case 5 -> {
                    System.out.println("Finalizando");
                    return;
                }
                default -> System.out.println("Error en la elección");
            }
        } while (opcion != 5);
    }

    public static int[] buscarPlazaMasCercana(String tipoVehiculo) {
        int mejorFila = -1;
        int mejorColumna = -1;
        int menorActual = Integer.MAX_VALUE;
        int menorDistancia = Integer.MAX_VALUE;
        int[] posicionesMasCortas = {-1, -1};
        for (int i = 0; i < matriculas.length; i++) {
            for (int j = 0; j < matriculas[0].length; j++) {
                if (estaLibre(i, j)) {
                    if (esCompatible(tipoVehiculo, i, j)) {
                        System.out.println("Distancia actual calculada: " + calcularDistancia(i, j));
                        menorActual = calcularDistancia(i, j);
                        if (menorActual < menorDistancia) {
                            menorDistancia = menorActual;
                            mejorFila = i;
                            mejorColumna = j;
                        }
                    }
                }
            }
        }
        posicionesMasCortas[0] = mejorFila;
        posicionesMasCortas[1] = mejorColumna;
        return posicionesMasCortas;

    }

    public static boolean estaLibre(int fila, int columna) {
        return matriculas[fila][columna].equals("*");
    }

    public static boolean esCompatible(String tipoVehiculo, int fila, int columna) {
        return tipoPlaza[fila][columna].equals(tipoVehiculo);
    }

    public static void mostrarMenu() {
        System.out.println("""
                    1. Entrada de vehículo
                    2. Retirar vehiculo
                    3. Mostrar parking
                    4. Mostrar plaza más utilizada
                    5. Salir
                """);
    }

    public static int calcularDistancia(int fila, int columna) {
        return Math.abs(-fila) + Math.abs(-columna);
    }

    public static void entradaVehiculo(String matricula, String tipoVehiculo, int tiempoEstimado) {
        int[] plazaMasCorta = buscarPlazaMasCercana(tipoVehiculo);
        System.out.println("Plaza más corta: " + Arrays.toString(plazaMasCorta));
        if (plazaMasCorta[0] != -1 && plazaMasCorta[1] != -1) {
            matriculas[plazaMasCorta[0]][plazaMasCorta[1]] = matricula;
            tiemposEstimados[plazaMasCorta[0]][plazaMasCorta[1]] = tiempoEstimado;
            usos[plazaMasCorta[0]][plazaMasCorta[1]]++;
        } else {
            System.out.println("No se ha podido rellenar la plaza");
        }
    }

    public static void retirarVehiculo(String matricula, String tipoVehiculo, int tiempoEstimado) {
        int[] posiciones = buscarMatricula(matricula);
        if (posiciones[0] != -1 && posiciones[1] != -1) {
            System.out.println("Matricula no existe");
        } else {
            matriculas[posiciones[0]][posiciones[1]] = "*";
            tiemposEstimados[posiciones[0]][posiciones[1]] = 0;
            System.out.println("Vehículo retirado");
            System.out.println("Precio a pagar: " + calcularTarifaTotal(tipoVehiculo, tiempoEstimado));
        }
    }

    public static int[] buscarMatricula(String matricula) {
        int[] valores = {-1, -1};
        for (int i = 0; i < matriculas.length; i++) {
            for (int j = 0; j < matriculas[0].length; j++) {
                if (matriculas[i][j].equals(matricula)) {
                    valores[0] = i;
                    valores[1] = j;
                }
            }
        }
        return valores;
    }

    public static void rellenarMatrices() {
        for (int i = 0; i < matriculas.length; i++) {
            for (int j = 0; j < matriculas[0].length; j++) {
                matriculas[i][j] = "*";
                tiemposEstimados[i][j] = 0;
                usos[i][j] = 0;
            }
        }
    }

    public static void mostrarMatrices() {
        System.out.println("MATRICES");
        for (int i = 0; i < matriculas.length; i++) {
            System.out.println(Arrays.toString(matriculas[i]));
        }
        System.out.println("TIPOS");
        for (int i = 0; i < matriculas.length; i++) {
            System.out.println(Arrays.toString(tipoPlaza[i]));
        }
        System.out.println("USOS");
        for (int i = 0; i < matriculas.length; i++) {
            System.out.println(Arrays.toString(usos[i]));
        }
    }

    public static void mostrarPlazaMasUtilizada() {
        int[] plazaMasCorta = {-1, -1};
        int masUso = -1;
        int menorActual = -1;
        for (int i = 0; i < usos.length; i++) {
            for (int j = 0; j < usos[0].length; j++) {
                if (usos[i][j] > menorActual) {
                    plazaMasCorta[0] = i;
                    plazaMasCorta[j] = j;
                }

            }
        }
        System.out.println("La plaza con más usos es: " + Arrays.toString(plazaMasCorta));
    }

    public static double calcularTarifaTotal(String tipoVehiculo, int minutos) {
        double[] tarifas1 = {0.04, 0.02, 0.03};
        double[] tarifas2 = {0.03, 0.015, 0.02};
        double[] tarifas3 = {0.02, 0.01, 0.015};
        double tarifa1 = 0;
        double tarifa2 = 0;
        double tarifa3 = 0;
        switch (tipoVehiculo) {
            case "N" -> {
                tarifa1 = tarifas1[0];
                tarifa2 = tarifas2[0];
                tarifa3 = tarifas3[0];
            }
            case "M" -> {
                tarifa1 = tarifas1[1];
                tarifa2 = tarifas2[1];
                tarifa3 = tarifas3[1];
            }
            case "E" -> {
                tarifa1 = tarifas1[2];
                tarifa2 = tarifas2[2];
                tarifa3 = tarifas3[2];
            }
            default -> {
                System.out.println("Tipo de vehiculo no permitido");
            }
        }
        double tarifaTotal = 0;

        if (minutos < 60)
            tarifaTotal = minutos * tarifa1;
        else if (minutos < 180)
            tarifaTotal = minutos * 60 + (minutos - 60) * tarifa2;
        else
            tarifaTotal = minutos * 60 + (minutos - 60) * tarifa2 + (minutos - 180) * tarifa3;

        return tarifaTotal;
    }

    public static void calcularMostrarMasUtilizado() {
        int mayorActual = Integer.MIN_VALUE;
        int mayorUso = Integer.MIN_VALUE;
        int fila = -1;
        int columna = -1;
        for (int i = 0; i < usos.length; i++) {
            for (int j = 0; j < usos[0].length; j++) {
                mayorActual = usos[i][j];
                if (mayorActual > mayorUso) {
                    mayorUso = mayorActual;
                    fila = i;
                    columna = j;
                }
            }
        }
        System.out.println("La plaza más usada es: [" + fila + "] [" + columna + "] con " + mayorUso + " uso");
    }
}
