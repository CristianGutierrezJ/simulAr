package v2;

public class Comercio {

    private Double aporteEconomico; // suma que aporta a la economia


    public Comercio(Double aporteEconomico) {
        this.aporteEconomico = aporteEconomico;
    }

    public void setAporteEconomico(Double aporteEconomico) {
        this.aporteEconomico = aporteEconomico;
    }

    public Double getAporteEconomico() {
        return aporteEconomico;
    }

    public void incrementarAporteEconomico(Double porcentajeCrecimiento) {
        aporteEconomico += calculoValorDelPorcentaje(porcentajeCrecimiento);
    }

    private Double calculoValorDelPorcentaje(Double porcentajeCrecimiento) {
        return aporteEconomico * porcentajeCrecimiento / 100;

    }

}
