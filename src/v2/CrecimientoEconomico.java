package v2;

public class CrecimientoEconomico extends Evento {

    private Integer fronteraPbiDeCrecimiento;
    private Double porcentajeDeCrecimiento;
    private Boolean fueExitoso;


    public CrecimientoEconomico(Double porcentajeDeCrecimiento) {
        this.fronteraPbiDeCrecimiento = 600;
        this.porcentajeDeCrecimiento = porcentajeDeCrecimiento;
        this.fueExitoso = false;
    }

    public Boolean getFueExitoso() {
        return fueExitoso;
    }

    public void setFueExitoso(Boolean fueExitoso) {
        this.fueExitoso = fueExitoso;
    }

    public Integer getFronteraPbiDeCrecimiento() {
        return fronteraPbiDeCrecimiento;
    }

    public void setFronteraPbiDeCrecimiento(Integer fronteraPbiDeCrecimiento) {
        this.fronteraPbiDeCrecimiento = fronteraPbiDeCrecimiento;
    }

    // Crecimiento de la economía:cumpleRequerimiento
    //      Sólo puede ocurrir cuando el PBI per cápita es mayor a
    //      un valor configurado en el sistema (que hoy se conoce en 600) y si la economía está bien.
    //      La economía, ya sea producción o comercio, crece en un porcentaje indicado para cada evento.
    //      Además si el PBI per cápita supera los 1000,
    //   se crea un nuevo bloque industrial que tiene como vecinos a los últimos tres bloques de la ciudad,
    //   siempre y cuando alguno de esos bloques tenga al menos una plaza.
    // El nuevo bloque tendrá una producción igual al equivalente promedio de producción de los demás
    // (recordar que la producción está en miles y el comercio en unidades).

    @Override
    protected void produceCambios(Ciudad ciudad) {
        if (superaFronteraDeCrecimiento(ciudad)) {
            ciudad.creceEconomia(porcentajeDeCrecimiento);
            setFueExitoso(true);
        }
        else setFueExitoso(false);
    }

    @Override
    protected Boolean esDesastreNatural() {
        return false;
    }

    @Override
    protected Boolean seProdujoConExito() {
        return getFueExitoso();
    }

    private Boolean superaFronteraDeCrecimiento(Ciudad ciudad) {
        return ciudad.economiaEstaBien() && ciudad.pbiPerCapita() > getFronteraPbiDeCrecimiento();
    }

}
