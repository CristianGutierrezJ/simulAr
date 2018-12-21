package v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BloqueResidencial extends Bloque {

    private Integer poblacion;
    private Collection<Comercio> comercios;
    private Integer cantidadPlazas;

//  CONSTRUCTOR

    public BloqueResidencial(Integer poblacion, Integer cantidadPlazas) {
        this.poblacion = poblacion;
        this.cantidadPlazas = cantidadPlazas;
        comercios = new ArrayList<>();
    }

    public BloqueResidencial(Integer poblacion, Integer cantidadPlazas, List<Bloque> bloques) {
        this.poblacion = poblacion;
        this.cantidadPlazas = cantidadPlazas;
        this.comercios = new ArrayList<>();
        addBloqueVecino(bloques);
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
    protected Double aporteEconomico() {
        if(!comercios.isEmpty()) return comercios.stream().mapToDouble(comercio -> comercio.getAporteEconomico()).sum();
        else return 0.0;
    }

    @Override
    protected boolean tienePlaza() {
        return cantidadPlazas > 0;
    }

    // Cada 10000 habitantes, aunmenta la cantidad de cantidadPlazas +1
    @Override
    protected void parquizar() {
        cantidadPlazas += nivelDePoblacion();
    }

    // Ej: 1 nivel = 10000 habitantes - Sirve para parquizar
    private Integer nivelDePoblacion() {
        return poblacion / 10000;
    }


    private Double calculoPorcentajePoblacional(Double porcentajePoblacional) {
        return poblacion * porcentajePoblacional / 100;
    }

    @Override
    protected void crecePoblacion(Double porcentajeCrecimientoPoblacional) {
        poblacion += calculoPorcentajePoblacional(porcentajeCrecimientoPoblacional).intValue();
    }

    @Override
    protected void decrecePoblacion(Double porcentajeDecrecimientoPoblacional) {
        poblacion -= calculoPorcentajePoblacional(porcentajeDecrecimientoPoblacional).intValue();
    }

    @Override
    protected Boolean superoLimitePoblacion(Integer limiteHabitantes) {
        return poblacion > limiteHabitantes;
    }


    @Override
    public void mudarPoblacion(Ciudad ciudad) {
        Integer nuevaPoblacion = poblacion / 2;
        setPoblacion(nuevaPoblacion);
            //List<Bloque> bloquesVecinos = ciudad.ultimosNBloques(3);
        BloqueResidencial nuevoBloqueResidencial = new BloqueResidencial(nuevaPoblacion, 0, ciudad.ultimosNBloques(3));
            //ver to do: ciudad.compartirVecinoEnComun(bloquesVecinos, nuevoBloqueResidencial);
        ciudad.addBloque(nuevoBloqueResidencial);
    }

    @Override
    public void incrementarAporteEconomico(Double porcentajeDeCrecimiento) {
        comercios.forEach(comercio -> comercio.incrementarAporteEconomico(porcentajeDeCrecimiento));
    }

    @Override
    protected Double getNivelProduccion() {
        return aporteEconomico()/1000;
    }


    // todo...Idea de mejora: A cada bloqueVecino podria decirles que me conocen asi hay una bidireccionalidad de conocimiento
}
