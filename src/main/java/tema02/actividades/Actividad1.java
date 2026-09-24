package tema02.actividades;

import java.util.Scanner;

public class Actividad1 {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un número: ");
        int numero = sc.nextInt();
        if(numero == 0){
            System.out.println("El número es cero.");
        } else if(numero > 0){
            System.out.println("El número es positivo.");
        } else {
            System.out.println("El número es negativo.");
        }
    }
}
