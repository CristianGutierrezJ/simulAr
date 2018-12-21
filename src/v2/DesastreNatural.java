package v2;


public class DesastreNatural extends Evento {


    @Override
    public void produceCambios(Ciudad ciudad) {
        ciudad.decrecePoblacion(10.0);
        ciudad.destruirBloqueViejo(2);
        ciudad.encenderAlarma();
        // ciudad.activarAlarma()
    }

    @Override
    public Boolean esDesastreNatural() {
        return true;
    }


}
