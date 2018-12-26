import org.testng.annotations.Test;
import v2.*;

public class CiudadTest {

    @Test
    public void clonarBloqueTest() {

        Ciudad ciudad = new Ciudad();

        Bloque bloque = new BloqueResidencial(2, 2);

        ciudad.addBloque(new BloqueIndustrial(2.0));
        ciudad.addBloque(new BloqueResidencial(100, 5));
        ciudad.addBloque(bloque);

        System.out.println("Ciudad" + ciudad.getBloques());
        System.out.println(bloque);

    }

}
