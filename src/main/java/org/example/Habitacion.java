package org.example;
public class Habitacion {
    String nombre;

    Habitacion(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion habitacion = (Habitacion) o;
        return nombre.equals(habitacion.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}