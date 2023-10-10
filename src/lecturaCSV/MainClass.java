package lecturaCSV;


import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class MainClass {


    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.00");
        MainClass mc = new MainClass();

        //Lee un archivo csv de Funkos y devuelve un ArrayList<Funko>
        ArrayList<Funko> funkos = mc.listaFunkosCSV("funkos.csv");

        //Funko más caro
        System.out.println("El Funko más caro es: ");
        mc.funkoMasCaro(funkos).mostrarInfo();

        //Media de precio de Funkos
        System.out.println("El precio medio de los Funkos es: " + df.format(mc.precioMedio(funkos)));

        //Funkos agrupados por modelos
        System.out.println("Funkos agrupados por modelo:");
        mc.agrupadosPorModelo(funkos).forEach((modelo, lista) -> {
            System.out.println("Modelo: " + modelo);
            for (Funko funko : lista) {
                funko.mostrarInfo();
            }
        });

        //Cantidad de Funkos por modelo
        System.out.println("Cantidad de Funkos por modelo:");
        mc.cantidadPorModelo(funkos).forEach((modelo, cantidad) ->
                System.out.println("Modelo: " + modelo + ", Cantidad: " + cantidad));

        //Funkos lanzados en 2023
        System.out.println("Funkos lanzados en 2023:");
        mc.lanzadosEn2023(funkos).stream().forEach(Funko::mostrarInfo);



        //Serializa los objetos del ArrayList al archivo "funkos.dat"
        mc.backup(funkos);

        //Deserializa el archivo "funkos.dat" y muestra la lista por pantalla
        mc.restore("funkos.dat");

    }

    /**
     * Método que lee un archivo csv de Funkos y devuelve un ArrayList<Funko>
     *
     * @param archivoCSV
     */
    public ArrayList<Funko> listaFunkosCSV(String archivoCSV) {
        ArrayList<Funko> funkos = new ArrayList<>();

        try (FileReader fr = new FileReader(archivoCSV); BufferedReader br = new BufferedReader(fr)) {
            String linea;
            //Leemos la primera línea para omitirla en la lista
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] detalles = linea.split(",");
                String cod = detalles[0];
                String nombre = detalles[1];
                String modelo = detalles[2];
                float precio = Float.parseFloat(detalles[3]);
                LocalDate fechaLanzamiento = LocalDate.parse(detalles[4]);
                funkos.add(new Funko(cod, nombre, modelo, precio, fechaLanzamiento));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return funkos;
    }

    /**
     * Método que devuelve el Funko más caro de un ArrayList<Funko>
     *
     * @param listaFunkos
     */
    public Funko funkoMasCaro(ArrayList<Funko> listaFunkos) {
        Funko f = new Funko();
        Optional<Funko> funkoMasCaro = listaFunkos.stream()
                .max((o1, o2) -> {
                    return (int) (o1.getPrecio() - o2.getPrecio());
                });
        if (funkoMasCaro.isPresent()) {
            f = funkoMasCaro.get();
        }
        return f;
    }

    /**
     * Método que muestra la media de precio de Funkos de un ArrayList<Funko>
     *
     * @param listaFunkos
     */
    public float precioMedio(ArrayList<Funko> listaFunkos) {
        return (float) listaFunkos.stream()
                .mapToDouble(v -> v.getPrecio())
                .average()
                .getAsDouble();
    }

    /**
     * Método que muestra Funkos agrupados por modelos de una ArrayList<Funko>
     *
     * @param listaFunkos
     */
    public Map<String, List<Funko>> agrupadosPorModelo(ArrayList<Funko> listaFunkos) {

        return listaFunkos.stream()
                .collect(Collectors.groupingBy(Funko::getModelo));
    }

    /**
     * Método que muestra el número de funkos por modelo de una ArrayList<Funko>
     *
     * @param listaFunkos
     */
    public Map<String, Long> cantidadPorModelo(ArrayList<Funko> listaFunkos) {

        return listaFunkos.stream()
                .collect(Collectors.groupingBy(Funko::getModelo, Collectors.counting()));
    }

    /**
     * Método que muestra los Funkos lanzados en 2023 de una ArrayList<Funko>
     *
     * @param listaFunkos
     */
    public List<Funko> lanzadosEn2023(ArrayList<Funko> listaFunkos) {

        return listaFunkos.stream()
                .filter(funko -> funko.getAnyoLanzamiento() == 2023)
                .toList();
    }

    /**
     * Método backup que serializa los objetos Funko de una ArrayList<Funko> a un fichero .dat.
     *
     * @param listaFunkos
     */
    public void backup(ArrayList<Funko> listaFunkos) {
        try ( FileOutputStream archivo = new FileOutputStream("funkos.dat");
              ObjectOutputStream salida = new ObjectOutputStream(archivo);){

            salida.writeObject(listaFunkos);

            salida.close();
            archivo.close();

            System.out.println("Copia de seguridad completa.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al realizar la copia de seguridad.");
        }
    }

    /**
     * Método restore que deserializa los objetos Funko desde un fichero y los muestra.
     *
     * @param archivoOrigen
     */
    public void restore(String archivoOrigen) {
        try {
            FileInputStream archivo = new FileInputStream(archivoOrigen);
            ObjectInputStream entrada = new ObjectInputStream(archivo);

            ArrayList<Funko> listaFunkos = (ArrayList<Funko>) entrada.readObject();

            System.out.println("Mostrando Funkos deserializados:");
            for (Funko f : listaFunkos
            ) {
                f.mostrarInfo();
            }

            entrada.close();
            archivo.close();

        } catch (FileNotFoundException e) {
            System.err.println("* ERROR: No se ha encontrado el archivo de origen. *");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("* ERROR: No se ha podido realizar la lectura desde el archivo. *");
        }
    }


}
