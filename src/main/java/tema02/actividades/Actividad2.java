package tema02.actividades;

import java.util.Scanner;

public class Actividad2 {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un número: ");
        int num1 = sc.nextInt();
        System.out.print("Introduce otro número: ");
        int num2 = sc.nextInt();
        if (num2 == 0) {
            System.out.println("No se puede dividir entre cero.");
        } else {
            double resultado = (double) num1 / num2;
            System.out.println("El resultado de la división es: " + resultado);

        }
    }
}
