package simulAr;

import java.util.ArrayList;
import java.util.Collection;

public class BloqueResidencial extends Bloque {

    private Integer poblacion; // todo: Cuando inicie poblacion en constructor, evaluar que no sobrepase 100.000 sino mudarPoblacion. Probablemente deba conocer CIUDAD
    private Collection<Comercio> comercios;
    private Integer cantidadPlazas;

//  CONSTRUCTOR

    public BloqueResidencial(Integer poblacion, Integer cantidadPlazas, Ciudad ciudadPerteneciente) {
        super(ciudadPerteneciente);
        this.poblacion = poblacion;
        this.cantidadPlazas = cantidadPlazas;
        comercios = new ArrayList<>();
    }


// GETTERS

    public Collection<Comercio> getComercios() {
        return comercios;
    }

    @Override
    public Integer getCantidadPlazas() {
        return cantidadPlazas;
    }

    @Override
    protected Integer getPoblacion() {
        return poblacion;
    }

// SETTERS

    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }

// OTROS

    @Override
    public Integer aporteEconomicoLocal() {
        return comercios.stream().mapToInt(comercio -> comercio.getAporteEconomico()).sum();
    }

    @Override
    public boolean tienePlaza() {
        return cantidadPlazas > 0;
    }


    // Cada 10000 habitantes, aunmenta la cantidad de cantidadPlazas +1
    @Override
    public void parquizar() {
        cantidadPlazas += nivelDePoblacion();
    }

    // Ej: 1 nivel = 10000 habitantes - Sirve para parquizar
    private Integer nivelDePoblacion() {
        return poblacion / 10000;
    }


    @Override
    public void aumentaPoblacion(Double porcentajeCrecimientoPoblacional) {
        poblacion = poblacion + calculoPorcentajePoblacional(porcentajeCrecimientoPoblacional).intValue();
    }

    private Double calculoPorcentajePoblacional(Double porcentajePoblacional) {
        return poblacion * porcentajePoblacional / 100;
    }

    @Override
    public void decrecePoblacion(Double porcentajeDecrecimientoPoblacional) {
        poblacion = poblacion - calculoPorcentajePoblacional(porcentajeDecrecimientoPoblacional).intValue();
    }

    @Override
    public boolean superoLimitePoblacional() {
        return poblacion > 100000;
    }


    public Bloque mudarPoblacion() {
        // divido mi poblacion
        Integer nuevaPoblacion = poblacion / 2;
        // Creo un Bloque residencial y mando la mitad a la nueva residencia

        BloqueResidencial nuevoBloqueResidencial = new BloqueResidencial(nuevaPoblacion, 0, getCiudadPerteneciente());
        // actualizo mi poblacion
        setPoblacion(nuevaPoblacion);

        return nuevoBloqueResidencial;
    }


}
