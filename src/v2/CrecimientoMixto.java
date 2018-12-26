package v2;

public class CrecimientoMixto extends Evento {

    private CambioPoblacion cambioPoblacion;
    private CrecimientoEconomico crecimientoEconomico;


    public CrecimientoMixto(Double porcentajeDeCrecimientoEconomico) {
        this.cambioPoblacion = new CambioPoblacion();
        this.crecimientoEconomico = new CrecimientoEconomico(porcentajeDeCrecimientoEconomico);
    }

    @Override
    protected void produceCambios(Ciudad ciudad) {
        cambioPoblacion.produceCambios(ciudad);
        crecimientoEconomico.produceCambios(ciudad);
    }

    @Override
    protected Boolean esDesastreNatural() {
        return false;
    }

    @Override
    protected Boolean seProdujoConExito() {
        return cambioPoblacion.seProdujoConExito() || crecimientoEconomico.getFueExitoso();
    }


}
