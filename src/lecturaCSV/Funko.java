
package lecturaCSV;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

public class Funko implements Serializable {
    private String cod, nombre, modelo;
    private float precio;
    private LocalDate fechaLanzamiento;

    public Funko() {

    }

    public Funko(String cod, String nombre, String modelo, float precio, LocalDate fechaLanzamiento) {
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public int getAnyoLanzamiento() {
        return fechaLanzamiento.getYear();
    }

    public void mostrarInfo() {
        System.out.print("Cod: " + cod + ", ");
        System.out.print("Nombre: " + nombre + ", ");
        System.out.print("Modelo: " + modelo + ", ");
        System.out.print("Precio: " + precio + ", ");
        System.out.println("Fecha de lanzamiento: " + fechaLanzamiento);
    }


}
