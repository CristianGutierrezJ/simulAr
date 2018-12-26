package v2;


public class DesastreNatural extends Evento {



    @Override
    protected void produceCambios(Ciudad ciudad) {
        ciudad.decrecePoblacion(10.0);
        ciudad.destruirBloqueViejo(2);
    }

    @Override
    protected Boolean esDesastreNatural() {
        return true;
    }

    @Override
    protected Boolean seProdujoConExito() {
        return true;
    }


}
