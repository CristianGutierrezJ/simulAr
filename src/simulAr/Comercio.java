package simulAr;

public class Comercio {

    private Double aporteEconomico; // suma que aporta a la economia

    public Comercio(Double aporteEconomico) {
        this.aporteEconomico = aporteEconomico;
    }

    public Integer getAporteEconomico() {
        return aporteEconomico.intValue();
    }


}
