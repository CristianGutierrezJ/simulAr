package v2;

public abstract class Evento {



    protected abstract void produceCambios(Ciudad ciudad);


    protected abstract Boolean esDesastreNatural();

    protected abstract Boolean seProdujoConExito();


}
