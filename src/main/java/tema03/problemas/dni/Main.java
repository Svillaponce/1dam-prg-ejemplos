package tema03.problemas.dni;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el DNI deteriorado:");
        String dni = sc.nextLine();

        if (!esValido(dni)) {
            System.out.println("DNI no válido. Debe contener ocho dígitos con uno o dos ? consecutivos y una letra final.");
            return;
        }

        String[] soluciones = new String[20];
        int cantidad = buscarSoluciones(dni, soluciones);
        mostrarResultados(soluciones, cantidad);
    }

    public static boolean esValido(String dni) {
        if (!dni.matches("[0-9?]{8}[a-zA-Z]")) {
            return false;
        }

        int primero = localizarDanio(dni);
        if (primero == -1) {
            return false;
        }

        int cantidad = 0;
        for (int i = 0; i < 8; i++) {
            if (dni.charAt(i) == '?') {
                cantidad++;
            }
        }
        return cantidad == 1 || (cantidad == 2 && dni.charAt(primero + 1) == '?');
    }

    public static int localizarDanio(String dni) {
        return dni.indexOf('?');
    }

    public static char calcularLetra(int dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letras.charAt(dni % 23);
    }

    public static String generarCandidato(String dni, int posicion, int cantidadDanios, int valor) {
        String sustitucion = Integer.toString(valor);
        if (cantidadDanios == 2 && valor < 10) {
            sustitucion = "0" + sustitucion;
        }
        return dni.substring(0, posicion) + sustitucion
                + dni.substring(posicion + cantidadDanios, 8)
                + Character.toUpperCase(dni.charAt(8));
    }

    public static boolean esCompatible(String candidato) {
        int numero = Integer.parseInt(candidato.substring(0, 8));
        return calcularLetra(numero) == candidato.charAt(8);
    }

    // Recibe un DNI validado y almacena los resultados en el array de soluciones.
    public static int buscarSoluciones(String dni, String[] soluciones) {
        int posicion = localizarDanio(dni);
        int cantidadDanios = dni.lastIndexOf('?') - posicion + 1;
        int limite = cantidadDanios == 1 ? 10 : 100;
        int cantidad = 0;

        for (int valor = 0; valor < limite; valor++) {
            String candidato = generarCandidato(dni, posicion, cantidadDanios, valor);
            if (esCompatible(candidato)) {
                soluciones[cantidad] = candidato;
                cantidad++;
            }
        }
        return cantidad;
    }

    public static void mostrarResultados(String[] soluciones, int cantidad) {
        if (cantidad == 0) {
            System.out.println("No existe ningún DNI compatible con los datos introducidos.");
        } else {
            if (cantidad == 1) {
                System.out.println("Se ha encontrado 1 posible DNI:");
            } else {
                System.out.println("Se han encontrado " + cantidad + " posibles DNI:");
            }
            for (int i = 0; i < cantidad; i++) {
                System.out.println(soluciones[i]);
            }
        }
    }
}
