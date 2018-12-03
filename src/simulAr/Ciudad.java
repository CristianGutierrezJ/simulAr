package simulAr;

import java.util.*;
import java.util.stream.Collectors;

public class Ciudad {

    private String nombre;
    private List<Bloque> bloques = new ArrayList<Bloque>();
    private Integer indiceTrimestral; // Sirive para indicar la informacion, en la posicion de la lista, a obtener
    private List<Integer> pbiPerCapitaTrimestrales = new ArrayList<>();
    private Integer valorEstandarDeCrecimientoEconomico = 600;

    public Ciudad(String nombre) {
        this.nombre = nombre;
        this.indiceTrimestral = 0;
        // inicio en 0 para guardar informacion del 1er Trimestre en dicho lugar de la lista
    }

    // Setter

    public void setValorEstandarDeCrecimientoEconomico(Integer valorEstandarDeCrecimientoEconomico) {
        this.valorEstandarDeCrecimientoEconomico = valorEstandarDeCrecimientoEconomico;
    }


// Getter

    public Integer getValorEstandarDeCrecimientoEconomico() {
        return valorEstandarDeCrecimientoEconomico;
    }

// Otros


    public void addBloque(Bloque bloque) {
        bloques.add(bloque);
    }

    public void addBloque(Collection<Bloque> bloques) {
        this.bloques.addAll(bloques);
    }


    // 2) -- Saber si una ciudad es verde, que es cuando todos sus bloques tienen plazas.
    public boolean esVerde() {
        return bloques.stream().allMatch(bloque -> bloque.tienePlaza());
    }

    // 3) -- Parquizar la ciudad, que incrementa la cantidad de plazas,
    //    de todos los bloques que puedan hacerlo, en una por cada 10000 habitantes.
    public void parquizar() {
        bloques.forEach(bloque -> bloque.parquizar());
    }

    // 5) -- Necesitamos saber el PBI per cápita de la ciudad.
    //   El PBI (total) es el tamaño de la economía,
    // calculable por la suma de los aportes de los comercios de los bloques con habitantes
    // sumado a la producción de los bloques con industrias.
    //   Para calcular el PBI per cápita, el PBI total se divide por la población total de la ciudad.
    public Integer pbiPerCapita() {
        return pbiTotal() / poblacionTotal();
    }

    public Integer pbiTotal() {
        return bloques.stream().mapToInt(bloque -> bloque.aporteEconomicoConsolidado()).sum();
    }

    public Integer poblacionTotal() {
        return bloques.stream().mapToInt(bloque -> bloque.getPoblacion()).sum();
    }

    // o Trimestre Actual
    public Integer enQueTrimestreSeEncuentra() {
        return indiceTrimestral + 1;
    }

    // 6) -- Saber si la economía está bien,
    // lo cual sucede cuando el PBI per cápita creció los últimos 2 trimestres.
    public boolean laEconomiaEstaBien() {
        if (puedeCompararTrimestres()) return crecioDosUltimosTrimestres();
        else return false;
    }

    public boolean puedeCompararTrimestres() {
        return indiceTrimestral >= 3;
    }

    public boolean crecioDosUltimosTrimestres() {
        // Actual PBI                                   // Anterior PBI (-1)
        return pbiPerCapitaTrimestrales.get(indiceTrimestral) > pbiPerCapitaTrimestrales.get(indiceTrimestral - 1)
                && pbiPerCapitaTrimestrales.get(indiceTrimestral) > pbiPerCapitaTrimestrales.get(indiceTrimestral - 2);
    }                                                                                  // Semi anterior PBI (-2)


// 7) -- EVENTOS = Diferentes Cambio debido al ciclo trimestral de la Ciudad

    // Cuando un trimestre avanza
    // 1ero. Guardo el pbiPerCapita (para poder compararlo con anteriores)
    // 2do. Incremento el indiceTrimestral
    private void avanzarTrimestre() {
        pbiPerCapitaTrimestrales.add(pbiPerCapita());
        indiceTrimestral++;
    }

    public void cambioPoblacional() {
        avanzarTrimestre();

        if (puedeCompararTrimestres() && laEconomiaEstaBien()) {
            bloques.forEach(bloque -> bloque.setUltimoEvento("Cambio Poblacional"));

            //La población de cada bloque crece en un 5%
            bloques.forEach(bloque -> bloque.aumentaPoblacion(5.0));

            List<Bloque> bloquesSuperPoblados = bloquesSuperPoblados();
            // si hay bloques super poblados
            if (!bloquesSuperPoblados.isEmpty()) {
                // mudarPoblacion ... crear nuevo Bloque(dentro de cada Blque Residencial Super Poblado) y mudar poblacion
                List<Bloque> nuevosBloquesResidensiales = bloquesSuperPoblados.stream().map(bloque -> bloque.mudarPoblacion()).collect(Collectors.toList());
                // compartir vecinos (los ultimos 3 de la ciudad)
                nuevosBloquesResidensiales.forEach(bloque -> bloque.addBloqueVecino(ultimosNBloques(3)));
                bloques.addAll(nuevosBloquesResidensiales);
            }
        } else {
            //La población de cada bloque cae un 1%.
            bloques.forEach(bloque -> bloque.decrecePoblacion(1.0));
        }
    }


    public Collection<Bloque> ultimosNBloques(Integer n) {
        // me oediste n elementos y mi collecton tiene menos de esos n no los busco
        if (bloques.size() < n) {
            return bloques;
        } else {
            List<Bloque> ultimosBloques = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                ultimosBloques.add(bloqueDesdeLaUltimaPosicion(i));
            }
            return ultimosBloques;
        }
    }


    private Bloque bloqueDesdeLaUltimaPosicion(Integer posicion) {
        return bloques.get(bloques.size() - posicion);
    }


    private List<Bloque> bloquesSuperPoblados() {
        return bloques.stream()
                .filter(bloque -> bloque.superoLimitePoblacional())
                .collect(Collectors.toList());
    }


    // 2do. EVENTO
    public void crecimientoEconomico(Double porcentajeCrecimientoEconomico) {
        avanzarTrimestre();
        if (pbiPerCapita() > valorEstandarDeCrecimientoEconomico) {
            if (puedeCompararTrimestres() && laEconomiaEstaBien()) {
                bloques.forEach(bloque -> bloque.setUltimoEvento("Crecimiento Economico"));
                bloques.forEach(bloque -> bloque.creceProduccion(porcentajeCrecimientoEconomico));

                if (pbiPerCapita() > 1000) {
                    BloqueIndustrial nuevoBloqueIndustrial = new BloqueIndustrial(promedioEconomiaLocal(), this);
                    //todo: preguntar por el uso del average
                    if (algunoDeLosUltimosBloquesTienePlaza()) {
                        nuevoBloqueIndustrial.addBloqueVecino(ultimosNBloques(3));
                    }
                    bloques.add(nuevoBloqueIndustrial);
                }
            }
        }
    }

    private Integer promedioEconomiaLocal() {
        return totalEconomiaLocal() / cantidadBloques();
    }

    private Integer totalEconomiaLocal() {
        return bloques.stream().mapToInt(bloque -> bloque.aporteEconomicoLocal()).sum();
    }

    public Integer cantidadBloques() {
        return bloques.size();
    }


    private boolean algunoDeLosUltimosBloquesTienePlaza() {
        return ultimosNBloques(3).stream().anyMatch(bloque -> bloque.tienePlaza());
    }


    public void crecimientoMixto(Double porcentajeCrecimento) {
        bloques.forEach(bloque -> bloque.setUltimoEvento("Crecimiento Mixto"));
        avanzarTrimestre();
        cambioPoblacional();
        crecimientoEconomico(porcentajeCrecimento);
    }

    public void desastreNatural() {
        avanzarTrimestre();
        bloques.forEach(bloque -> bloque.setUltimoEvento("Desastre Natural"));
        bloques.forEach(bloque -> bloque.decrecePoblacion(10.0));
        eliminarBloqueViejo();
        eliminarBloqueViejo();
    }

    private void eliminarBloqueViejo() {
        if (!bloques.isEmpty()) {
            bloques.remove(0);
        }
    }

}