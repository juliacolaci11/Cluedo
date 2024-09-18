package org.example;
public class Personaje {
    String nombre;

    Personaje(String nombre) {
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
