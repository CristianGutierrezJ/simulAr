package v2;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Bloque {

    private Collection<Bloque> bloquesVecinos = new ArrayList<>();


    public Collection<Bloque> getBloquesVecinos() {
        return bloquesVecinos;
    }

    public void addBloqueVecino(Bloque bloque) {
        bloquesVecinos.add(bloque);
    }

    public void addBloqueVecino(Collection<Bloque> bloques) {
        bloques.forEach(bloque -> addBloqueVecino(bloque));
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
        if(evento.esDesastreNatural()) return false;
        else return tienePlaza() && poblacionTotalVecinos() > getPoblacion() && !estaCeloso();
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
}
