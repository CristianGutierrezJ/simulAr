package v2;

import java.util.List;

public class BloqueIndustrial extends Bloque {

    private Double nivelDeProduccion;


    public BloqueIndustrial(Double nivelDeProduccion) {
        this.nivelDeProduccion = nivelDeProduccion;
    }

    public BloqueIndustrial(Double nivelDeProduccion, List<Bloque> bloques) {
        super(bloques);
        this.nivelDeProduccion = nivelDeProduccion;
    }


    @Override
    public Double getNivelProduccion() { // Un nivel de produccion 10, equivale a $10.000
        return nivelDeProduccion;
    }

    public void setNivelDeProduccion(Double nivelDeProduccion) {
        this.nivelDeProduccion = nivelDeProduccion;
    }


    @Override
    protected Integer getPoblacion() {
        return 0;
    }

    @Override
    protected void crecePoblacion(Double porcentajeCrecimientoPoblacional) {
    }

    @Override
    protected void decrecePoblacion(Double porcentajeDecrecimientoPoblacional) {
    }

    @Override
    public Integer getCantidadPlazas() {
        return 0;
    }


    @Override
    protected Double aporteEconomico() {
        return nivelDeProduccion * 1000;
    }


    @Override
    protected boolean tienePlaza() {
        return false;
    }

    @Override
    protected void parquizar() {
    }

    @Override
    protected Boolean superoLimitePoblacion(Integer limiteHabitantes) {
        return false;
    }

    @Override
    protected void mudarPoblacion(Ciudad ciudad) {
    }

    @Override
    public void incrementarAporteEconomico(Double porcentajeDeCrecimiento) {
        nivelDeProduccion += calcularValorDePorcentaje(porcentajeDeCrecimiento);
    }

    private Double calcularValorDePorcentaje(Double porcentajeDeCrecimiento) {
        return nivelDeProduccion * porcentajeDeCrecimiento / 100;
    }


}
