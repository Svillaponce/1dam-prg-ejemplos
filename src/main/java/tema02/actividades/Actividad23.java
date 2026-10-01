package tema02.actividades;

import java.util.Scanner;

public class Actividad23 {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un número natural para el lado del cuadrado más grande: ");
        int ladoMax = sc.nextInt();
        int ladoAct = 2;
        for (int r = ladoAct; r <= ladoMax; r++) {
            for (int i = 0; i < ladoAct; i++) {
                for (int j = 0; j < ladoAct; j++) {
                    if (j == ladoAct - 1)
                        System.out.println(ladoAct);
                    else
                        System.out.printf(ladoAct + " ");
                }
            }
            ladoAct++;
            System.out.println();
        }
    }
}
