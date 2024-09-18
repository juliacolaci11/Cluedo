package org.example;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    String nombre;
    List<Personaje> cartasPersonaje;
    List<Arma> cartasArma;
    List<Habitacion> cartasHabitacion;

    Jugador(String nombre) {
        this.nombre = nombre;
        this.cartasPersonaje = new ArrayList<>();
        this.cartasArma = new ArrayList<>();
        this.cartasHabitacion = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Personaje> getCartasPersonaje() {
        return cartasPersonaje;
    }

    public List<Arma> getCartasArma() {
        return cartasArma;
    }

    public List<Habitacion> getCartasHabitacion() {
        return cartasHabitacion;
    }

}