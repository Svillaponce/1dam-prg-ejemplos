package tema03.actividades;

import java.util.Scanner;

public class Actividad01 {
    static void main() {
        // 1. Estructuras de datos
        String[] productos = {"Manzana", "Plátano", "Naranja"};
        double[] precios = {0.26, 0.38, 0.25};
        double[] descuentos = {0.1, 0.2, 0};
        double[] IVA = {0.21, 0.3, 0.15};
        String[] productosComprados = new String[productos.length];
        int[] cantidadesCompradas = new int[productos.length];
        double[] subtotales = new double[productos.length];
        int indiceCompra = 0;

        // 2. Recursos externos
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Productos disponibles:");
            for (int i = 0; i < productos.length; i++) {
                System.out.println(productos[i] + " - Precio: " + precios[i] + "€ - IVA: " + IVA[i] * 100 + "%");
            }

            String producto = "";
            int cantidad = 0;
            do {
                System.out.println("Qué producto quieres? Dame el nombre exacto:");
                producto = sc.nextLine();
                System.out.println("Qué cantidad quieres? Dame el número exacto:");
                cantidad = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
            } while (!sonValidos(productos, producto, cantidad));

            System.out.println("Quieres otro producto? S/N");

            productosComprados[indiceCompra] = producto;
            cantidadesCompradas[indiceCompra] = cantidad;
            indiceCompra++;

        } while (sc.nextLine().equalsIgnoreCase("S"));

        // 1. Calcular subtotales
        for (int i = 0; i < productos.length; i++) {
            subtotales[i] = subtotal(precios[i], cantidadesCompradas[i]);
        }
        // 2. Calcular descuentos
        for (int i = 0; i < productos.length; i++) {
            subtotales[i] = aplicarDescuento(subtotales[i], descuentos[i]);
        }
        // 3. Calcular IVA
        for (int i = 0; i < productos.length; i++) {
            subtotales[i] = aplicarIVA(subtotales[i], IVA[i]);
        }
        // 4. Calcular precio final
        double precioFinal = 0;
        for (int i = 0; i < subtotales.length; i++) {
            precioFinal += subtotales[i];
        }
        // 5. Mostrar resumen
        mostrarResumen(precioFinal);


    }

    public static boolean sonValidos(String[] productos, String producto, int cantidad) {
        boolean productoEncontrado = false;
        boolean cantidadValida = false;

        for (int i = 0; i < productos.length; i++) {
            if (productos[i].equals(producto)) {
                productoEncontrado = true;
                break;
            }
        }
        if (cantidad > 0) cantidadValida = true;
        return productoEncontrado && cantidadValida;
    }

    public static double subtotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    public static double aplicarDescuento(double subtotal, double descuento) {
        return subtotal * (1 - descuento);
    }

    public static double aplicarIVA(double subtotal, double iva) {
        return subtotal * (1 + iva);
    }

    public static void mostrarResumen(double precioFinal) {
        System.out.println("Precio final: " + precioFinal);
    }
}
