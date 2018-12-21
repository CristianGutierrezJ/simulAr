package simulAr;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Bloque {

    private String nombre;
    private Ciudad ciudadPerteneciente;
    private Collection<Bloque> bloquesVecinos = new ArrayList<>();
    private Double valorAcumuladoDeCrecimientoEconomico = 0.0;
    private String ultimoEvento;


    public Bloque(Ciudad ciudadPerteneciente) {
        this.ciudadPerteneciente = ciudadPerteneciente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad getCiudadPerteneciente() {
        return ciudadPerteneciente;
    }

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
        return getBloquesVecinos().stream()
                .allMatch(bloqueVecino -> bloqueVecino.getCantidadPlazas() > getCantidadPlazas()
                        || bloqueVecino.aporteEconomicoLocal() > aporteEconomicoLocal());
    }

    public abstract Integer getCantidadPlazas();

    public abstract Integer aporteEconomicoLocal();

    public Integer aporteEconomicoConsolidado() {
        return aporteEconomicoLocal() + valorAcumuladoDeCrecimientoEconomico.intValue();
    }

    public abstract boolean tienePlaza();

    public abstract void parquizar();

    // 4) -- Hacer que los bloques respondan si son felices.
    // Para ser feliz, un bloque tiene que, al mismo tiempo:
    //  . Tener plazas,
    //  . La población total de sus vecinos debe ser mayor que la propia, y
    //  . No estar celoso.
    public boolean esFeliz() {
        if (ultimoEvento == "Desastre Natural") return false;
        else return tienePlaza() && poblacionTotalVecinos() >= getPoblacion() && !estaCeloso();
    }

    public Integer poblacionTotalVecinos() {
        return bloquesVecinos.stream().mapToInt(bloque -> bloque.getPoblacion()).sum();
    }

    protected abstract Integer getPoblacion();

    public abstract void aumentaPoblacion(Double porcentajeCrecimientoPoblacional);

    public abstract void decrecePoblacion(Double porcentajeDecrecimientoPoblacional);

    public abstract boolean superoLimitePoblacional();

    public abstract Bloque mudarPoblacion();


    public void creceProduccion(Double porcentajeCrecimiento) {
        valorAcumuladoDeCrecimientoEconomico += aporteEconomicoLocal() * porcentajeCrecimiento / 100;
    }

    public void setUltimoEvento(String ultimoEvento) {
        this.ultimoEvento = ultimoEvento;
    }
}
