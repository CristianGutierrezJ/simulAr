package v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Bloque {

    private Collection<Bloque> bloquesVecinos = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();


    public Bloque() {
    }

    public Bloque(Collection<Bloque> bloquesVecinos) {
        addBloqueVecino(bloquesVecinos);
    }

    public Collection<Bloque> getBloquesVecinos() {
        return bloquesVecinos;
    }

    public void addBloqueVecino(Bloque bloque) {
        bloquesVecinos.add(bloque);
    }

    public void addBloqueVecino(Collection<Bloque> bloques) {
        bloquesVecinos.addAll(bloques);
    }

    // 1) -- Saber si un bloque está celoso,
    // que sucede cuando todos los vecinos están mejor en alguna de las materias importantes,
    // que son economía y cantidad de plazas.
    public boolean estaCeloso() {
        return getBloquesVecinos()
                .stream()
                .allMatch(bloqueVecino -> bloqueVecino.getCantidadPlazas() > getCantidadPlazas()
                        || bloqueVecino.aporteEconomico() > aporteEconomico());
    }

    public abstract Integer getCantidadPlazas();

    protected abstract Double aporteEconomico();

    protected abstract boolean tienePlaza();

    protected abstract void parquizar();

    // 4) -- Hacer que los bloques respondan si son felices.
    public boolean esFeliz() {
        //todo:....................................
        if (sufrioDesastreNatural()) return false;
        else return tienePlaza() && poblacionTotalVecinos() > getPoblacion() && !estaCeloso();
    }

    private Boolean sufrioDesastreNatural() {
        if (eventos.isEmpty()) return false;
        else return ultimoEvento().esDesastreNatural();
    }

    protected Evento ultimoEvento() {
        return eventos.get(eventos.size() - 1);
    }


    protected Integer poblacionTotalVecinos() {
        return bloquesVecinos.stream().mapToInt(bloque -> bloque.getPoblacion()).sum();
    }

    protected abstract Integer getPoblacion();

    protected abstract void crecePoblacion(Double porcentajeCrecimientoPoblacional);

    protected abstract void decrecePoblacion(Double porcentajeDecrecimientoPoblacional);

    protected abstract Boolean superoLimitePoblacion(Integer limiteHabitantes);

    protected abstract void mudarPoblacion(Ciudad ciudad);

    protected abstract void incrementarAporteEconomico(Double porcentajeDeCrecimiento);

    protected abstract Double getNivelProduccion();

    private void addEvento(Evento evento) {
        eventos.add(evento);
    }

    protected void evaluarEvento(Evento evento) {
        if (evento.seProdujoConExito()) addEvento(evento);
    }
}
