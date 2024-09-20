package org.example;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    String nombre;
    List<Personaje> cartasPersonaje;
    List<Arma> cartasArma;
    List<Habitacion> cartasHabitacion;
    List<CartaEspecial> cartasEspeciales;



    Jugador(String nombre) {
        this.nombre = nombre;
        this.cartasPersonaje = new ArrayList<>();
        this.cartasArma = new ArrayList<>();
        this.cartasHabitacion = new ArrayList<>();
        this.cartasEspeciales = new ArrayList<>();
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

    public List<CartaEspecial> getCartasEspeciales() {
        return cartasEspeciales;
    }

    public boolean usarCartaEspecial(CartaEspecial carta) {
        if (cartasEspeciales.contains(carta)) {
            cartasEspeciales.remove(carta);
            return true; // Carta usada exitosamente
        }
        return false; // Carta no se pudo usar
    }
}