package v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Ciudad {

    private List<Bloque> bloques = new ArrayList<Bloque>();
    private MinisterioEconomia ministerioEconomia;


    public Ciudad() {
        this.ministerioEconomia = new MinisterioEconomia();
    }

    public Ciudad(Collection<Bloque> bloques) {
        this.ministerioEconomia = new MinisterioEconomia();
        this.addBloques(bloques);
    }


    public List<Bloque> getBloques() {
        return bloques;
    }

    public void addBloque(Bloque bloque) {
        bloques.add(bloque);
    }

    public void addBloques(Collection<Bloque> bloques) {
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


    public Integer pbiPerCapita() {
        return ministerioEconomia.calcularPbiPerCapita(bloques);
    }

    public Boolean economiaEstaBien() {
        return ministerioEconomia.laEconomiaEstaBien();
    }


// SE PRODUCE UN EVENTO

    private void avanzaTrimestre() {
        ministerioEconomia.addPBI(pbiPerCapita()); // Captura el PBI actual, y lo almacena en el ministerio
    }


    public void ocurreEvento(Evento evento) {
        avanzaTrimestre();

        evento.produceCambios(this);

        informarEvento(evento);

        evaluarPoblacion(100000);
        evaluarPbiPerCapita(1000);
    }


// Evento: Cambio en Poblacion

    protected void crecePoblacion(Double porcentajePoblacion) {
        bloques.forEach(bloque -> bloque.crecePoblacion(porcentajePoblacion));
    }

    protected void decrecePoblacion(Double porcentajePoblacion) {
        bloques.forEach(bloque -> bloque.decrecePoblacion(porcentajePoblacion));
    }

    private void evaluarPoblacion(Integer limiteHabitantes) {
        if (AlgunBloqueSuperoLimitePoblacion(limiteHabitantes)) resolverSuperpoblacion(limiteHabitantes);
    }

    private boolean AlgunBloqueSuperoLimitePoblacion(Integer limiteHabitantes) {
        return bloques.stream().anyMatch(bloque -> bloque.superoLimitePoblacion(limiteHabitantes));
    }

    private void resolverSuperpoblacion(Integer limiteHabitantes) {
        bloquesSuperpoblados(limiteHabitantes).forEach(bloque -> bloque.mudarPoblacion(this));
    }


    protected void compartirVecinoEnComun(List<Bloque> bloques, Bloque bloqueVecino) {
        bloques.forEach(bloque -> bloque.addBloqueVecino(bloqueVecino));
    }

    private List<Bloque> bloquesSuperpoblados(Integer limiteHabitantes) {
        return bloques
                .stream()
                .filter(bloque -> bloque.superoLimitePoblacion(limiteHabitantes))
                // los Industriales nunca van a superar el limite
                .collect(Collectors.toList());
    }

    protected List<Bloque> ultimosNBloques(Integer n) {
        if (bloques.size() <= n) return bloques;
        else {
            List<Bloque> bloquesCopy = new ArrayList<Bloque>(bloques);
            Collections.reverse(bloquesCopy);
            return bloquesCopy.subList(0, n);
        }
    }


// Evento: Crecimento Economico

    protected void creceEconomia(Double porcentajeDeCrecimiento) {
        bloques.forEach(bloque -> bloque.incrementarAporteEconomico(porcentajeDeCrecimiento));
    }

    private void evaluarPbiPerCapita(Integer valorLimite) {
        if (pbiPerCapita() > valorLimite) crearNuevoBloqueIndustrial();
    }

    private void crearNuevoBloqueIndustrial() {
        BloqueIndustrial nuevoBloqueIndustrial = new BloqueIndustrial(calcularNivelProduccion());

        List<Bloque> bloquesVecinos = ultimosNBloques(3);
        if (vecinosTienenPlaza(bloquesVecinos)) nuevoBloqueIndustrial.addBloqueVecino(bloquesVecinos);

        bloques.add(nuevoBloqueIndustrial);
    }

    private Boolean vecinosTienenPlaza(List<Bloque> bloques) {
        return bloques.stream().anyMatch(Bloque::tienePlaza);
    }

    private Double calcularNivelProduccion() {
        return bloques.stream().mapToDouble(bloque -> bloque.getNivelProduccion()).average().orElse(0);
    }


// Evento: Desastre Natural

    protected void destruirBloqueViejo(Integer cantidadBloques) {
        for (int i = 0; i < cantidadBloques; i++) removerBloque(0);
    }

    private void removerBloque(Integer indice) {
        bloques.remove(indice);
    }

    public void informarEvento(Evento evento) {
        bloques.forEach(bloque -> bloque.evaluarEvento(evento));
    }


}