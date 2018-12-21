import org.testng.annotations.Test;
import v2.*;

public class CiudadTest {

    @Test
    public void clonarBloqueTest() {

        Ciudad ciudad = new Ciudad();
        ciudad.addBloque(new BloqueIndustrial(2));

        ciudad.addBloque(new BloqueResidencial(100, 5));

        System.out.println("Ciudad" + ciudad.getBloques());


    }

}
