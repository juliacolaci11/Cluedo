package org.example;

public class CartaEspecial {
    private String tipo; // "Personaje", "Arma" o "Habitación"
    private Jugador jugadorObjetivo; // Jugador cuyo carta será revelada

    public CartaEspecial(String tipo, Jugador jugadorObjetivo) {
        this.tipo = tipo;
        this.jugadorObjetivo = jugadorObjetivo;
    }

    public String getTipo() {
        return tipo;
    }

    public Jugador getJugadorObjetivo() {
        return jugadorObjetivo;
    }
}