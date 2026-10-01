package tema02.actividades;

import java.util.Random;
import java.util.Scanner;

public class Actividad24 {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el número de columnas de la matriz: ");
        int columnas = sc.nextInt();
        int[][] matriz = new int[5][columnas];
        Random rand = new Random();
        // 1. Crear y mostrar la matriz
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = rand.nextInt(5, 14);
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        // 2. Calcular la suma de sus elementos
        int suma = 0;
        int pares = 0, impares = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                suma += matriz[i][j];
                if (matriz[i][j] % 2 == 0) {
                    pares++;
                } else {
                    impares++;
                }
            }
        }
        System.out.println("La suma de los elementos de la matriz es: " + suma);
        System.out.println("El número de elementos pares es: " + pares);
        System.out.println("El número de elementos impares es: " + impares);
    }
}
