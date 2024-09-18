package org.example;
public class Arma {
    String nombre;

    Arma(String nombre) {
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
        Arma arma = (Arma) o;
        return nombre.equals(arma.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
