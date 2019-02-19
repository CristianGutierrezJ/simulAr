package v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinisterioEconomia {

    private List<Integer> pbis = new ArrayList<>();


    protected void addPBI(Integer pbi) {
        pbis.add(pbi);
    }


// CALCULAR PBI.....

    protected Integer calcularPbiPerCapita(List<Bloque> bloques) {
        if (puedeCalcularPbiPerCapita(bloques)) {
            return calcularPbiTotal(bloques) / calcularPoblacionTotal(bloques);
        } else return calcularPoblacionTotal(bloques);
    }

    // Resolver ERROR, si la cantidadDePoblacion es cero
    private Boolean puedeCalcularPbiPerCapita(List<Bloque> bloques) {
        return calcularPoblacionTotal(bloques) != 0;
    }

    private Integer calcularPbiTotal(List<Bloque> bloques) {
        return bloques.stream().mapToInt(bloque -> bloque.aporteEconomico().intValue()).sum();
    }

    private Integer calcularPoblacionTotal(List<Bloque> bloques) {
        return bloques.stream().mapToInt(bloque -> bloque.getPoblacion()).sum();
    }


// OTRO....................

    private Integer ultimoPbi() {
        return pbis.get(pbis.size() - 1);
    }


// LA ECONOMIA ESTA BIEN ?? ....
// Esta bien cuando crecio los ultimos dos trmestres

    public Boolean laEconomiaEstaBien() {
        if (ultimosNPbis(3).size() < 3) return true;
            // Al principio la economia esta bien, asi puede crecer la CIUDAD
            // Luego empiezo a comparar
        else return estaEnOrdenDecreciente(ultimosNPbis(3));
    }

    private List<Integer> ultimosNPbis(Integer n) {
        if (pbis.size() < n) {
            return pbis;
        } else {
            List<Integer> pbisCopy = new ArrayList<Integer>(pbis);
            Collections.reverse(pbisCopy);
            // Ahora el ultimo (pbi ingresado) es el primero de la lista;
            return pbisCopy.subList(0, n);
        }
    }

    //  Recibe la sublita de PBIs (3 elementos), con orden inverso respecto a la original
    private Boolean estaEnOrdenDecreciente(List<Integer> pbis) {
        for (int i = 0; i < pbis.size() - 1; i++) {
            if (pbis.get(i) <= pbis.get(i + 1)) return false;
        }
        return true;
    }


    public static  void main(String... args) {
        System.out.println("Hi");
    }


}
