import lecturaCSV.Funko;
import lecturaCSV.MainClass;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestClass {

    private ArrayList<Funko> listaFunkos = new ArrayList<>();
    private Funko f1 = new Funko("3b6c6f58-79b9-434b-82ab-01a2d6e4434a", "Spiderman Delight", "MARVEL", 10.00f, LocalDate.of(2020, 5, 1));
    private Funko f2 = new Funko("f8f7ae42-5b01-4d3b-82ab-2d1a2d6e4434a", "Stitch Hula", "DISNEY", 30.00f, LocalDate.of(2023, 1, 1));
    private Funko f3 = new Funko("6b6c6f58-7c6b-434b-82ab-01b2d6e4434a", "Naruto Shippuden", "ANIME", 20.00f, LocalDate.of(2022, 8, 1));
    private Funko f4 = new Funko("a8f7ae42-5b01-4d3b-82ab-2d1a2d6e4434a", "Iron Man Classic", "MARVEL", 18.99f, LocalDate.of(2022, 6, 1));

    MainClass mainClass = new MainClass();


    @Test
    public void funkoMasCaroTest() {

        listaFunkos.add(f1);
        listaFunkos.add(f2);
        listaFunkos.add(f3);

        Assert.assertEquals(mainClass.funkoMasCaro(listaFunkos), f2);
    }

    @Test
    public void precioMedioTest() {

        listaFunkos.add(f1);
        listaFunkos.add(f2);
        listaFunkos.add(f3);

        float f = mainClass.precioMedio(listaFunkos);
        Assert.assertTrue(mainClass.precioMedio(listaFunkos) == 20f);
    }

    @Test
    public void agrupadosPorModeloTest() {

        listaFunkos.add(f1);
        listaFunkos.add(f2);
        listaFunkos.add(f3);
        listaFunkos.add(f4);

        Map<String, List<Funko>> mappedList = mainClass.agrupadosPorModelo(listaFunkos);

        mappedList.forEach((modelo, lista) -> {
            System.out.println("Modelo: " + modelo);
            for (Funko funko : lista) {
                funko.mostrarInfo();
            }
        });

        Assert.assertTrue(mappedList.get("MARVEL").stream().count() == 2);
        Assert.assertTrue(mappedList.get("DISNEY").stream().count() == 1);
        Assert.assertTrue(mappedList.get("ANIME").stream().count() == 1);
    }

    @Test
    public void cantidadPorModeloTest() {

        listaFunkos.add(f1);
        listaFunkos.add(f2);
        listaFunkos.add(f3);
        listaFunkos.add(f4);

        Assert.assertEquals(2, mainClass.cantidadPorModelo(listaFunkos).get("MARVEL").longValue());
    }

    @Test
    public void lanzadosEn2023Test() {

        listaFunkos.add(f1);
        listaFunkos.add(f2);
        listaFunkos.add(f3);
        listaFunkos.add(f4);

        ArrayList<Funko> lanzados2023 = new ArrayList<>();
        lanzados2023.add(f2);
        ArrayList<Funko> lanzados2022 = new ArrayList<>();
        lanzados2022.add(f3);
        lanzados2022.add(f4);

        Assert.assertEquals(lanzados2023, mainClass.lanzadosEn2023(listaFunkos));
    }

    @Test
    public void listaFunkosCSVTest() {

        listaFunkos.add(f1);
        listaFunkos.add(f2);
        listaFunkos.add(f3);
        listaFunkos.add(f4);

        Assert.assertEquals(listaFunkos.get(0).getCod(), mainClass.listaFunkosCSV("funkos2.csv").get(0).getCod());
        Assert.assertEquals(listaFunkos.get(1).getCod(), mainClass.listaFunkosCSV("funkos2.csv").get(1).getCod());
        Assert.assertEquals(listaFunkos.get(2).getCod(), mainClass.listaFunkosCSV("funkos2.csv").get(2).getCod());
        Assert.assertEquals(listaFunkos.get(3).getCod(), mainClass.listaFunkosCSV("funkos2.csv").get(3).getCod());
    }

}

