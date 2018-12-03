package simulAr;

public class BloqueIndustrial extends Bloque {

    private Integer nivelDeProduccion;

    public BloqueIndustrial(Integer nivelDeProduccion, Ciudad ciudadPerteneciente) {
        super(ciudadPerteneciente);
        this.nivelDeProduccion = nivelDeProduccion;
    }


    public Integer getNivelDeProduccion() { // Un nivel de produccion 10, equivale a $10.000
        return nivelDeProduccion;
    }

    public void setNivelDeProduccion(Integer nivelDeProduccion) {
        this.nivelDeProduccion = nivelDeProduccion;
    }

    @Override
    protected Integer getPoblacion() {
        return 0;
    }

    @Override
    public void aumentaPoblacion(Double porcentajeCrecimientoPoblacional) {

    }

    @Override
    public void decrecePoblacion(Double porcentajeDecrecimientoPoblacional) {

    }

    @Override
    public Integer getCantidadPlazas() {
        return 0;
    }




    @Override
    public Integer aporteEconomicoLocal() {
        return nivelDeProduccion * 1000;
    }

    @Override
    public boolean tienePlaza() {
        return false;
    }

    @Override
    protected void parquizar() {
    }



    @Override
    public boolean superoLimitePoblacional() {
        return false;
    }

    @Override
    public Bloque mudarPoblacion() {
        return null;
    }




}
