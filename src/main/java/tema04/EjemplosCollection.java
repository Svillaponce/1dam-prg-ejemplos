package tema04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EjemplosCollection {
    static void main() {
        ArrayList<String> lista = new ArrayList<>();
        List<String> lista2 = new ArrayList<>();
        List<Integer> lista3 = new ArrayList<>();
        List<Integer> lista4 = new LinkedList<>();

        //Elementos de la lista 1
        lista.add("1");
        lista.add("2");
        lista.add("3");
        lista.add("4");

        //Uso de metodos
        System.out.println(lista.contains("3"));
        //Reemplazo y muestro el elemento anterior o viejo
        System.out.println("Viejo: " + lista.set(0, "0"));

        //Elementos de la lista 2
        lista2.add("Sebas");

        //Lista 1 original
        System.out.println(lista);
        //lista 1 + 2
        lista.addAll(lista2);
        System.out.println(lista);


        // Ordenar descendentemente
        Collections.reverse(lista);
        // Desordenar lista
        Collections.shuffle(lista);
        // Ordenar ascendentemente
        Collections.sort(lista);

        // Lista de Objetos Integer que recibe un int
        lista3.add(2);
        int num = lista3.getFirst();

        // Creacion sublista por indices
        List<String> subLista = lista.subList(1, lista.size() - 1);
        System.out.println(subLista);
    }
}
