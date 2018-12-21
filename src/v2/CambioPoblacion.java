package v2;

public class CambioPoblacion extends Evento {

    // Cambio de población:
    // La población de cada bloque crece en un 5%,
    // pero si la economía está mal (es decir, cuando no está bien), en cambio, cae un 1%.
    // Si un bloque supera los 100.000 habitantes, luego se crea un nuevo bloque residencial,sin comercios ni plazas,
    // que tiene como vecinos a los últimos tres bloques de la ciudad,
    // y la población del original (ya aumentada) se divide por igual, entre el mismo y el nuevo.

    @Override
    public void produceCambios(Ciudad ciudad) {
        if (ciudad.economiaEstaBien()) crecimiento(ciudad);
        else decrecimiento(ciudad);
    }

    @Override
    public Boolean esDesastreNatural() {
        return false;
    }

    protected void crecimiento(Ciudad ciudad) {
        ciudad.crecePoblacion(5.0);

    }

    protected void decrecimiento(Ciudad ciudad) {
        ciudad.decrecePoblacion(1.0);
    }


}