package v2;

public class CrecimientoMixto extends Evento {

    private CambioPoblacion cambioPoblacion;
    private CrecimientoEconomico crecimientoEconomico;


    public CrecimientoMixto(Double porcentajeDeCrecimientoEconomico) {
        this.cambioPoblacion = new CambioPoblacion();
        this.crecimientoEconomico = new CrecimientoEconomico(porcentajeDeCrecimientoEconomico);
    }

    @Override
    public void produceCambios(Ciudad ciudad) {
        cambioPoblacion.produceCambios(ciudad);
        crecimientoEconomico.produceCambios(ciudad);
    }

    @Override
    public Boolean esDesastreNatural() {
        return false;
    }


}
