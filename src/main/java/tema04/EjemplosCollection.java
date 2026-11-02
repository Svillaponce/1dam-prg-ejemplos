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
        lista.add("1");
        lista.add("2");

        lista2.add("Sebas");

        System.out.println(lista);
        lista.addAll(lista2);
        System.out.println(lista);

        Collections.reverse(lista);
        Collections.shuffle(lista);
        Collections.sort(lista);

        lista3.add(2);
        int num = lista3.getFirst();

    }
}
