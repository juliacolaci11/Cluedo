package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private List<Personaje> personajes;
    private List<Arma> armas;
    private List<Habitacion> habitaciones;
    private Personaje asesino;
    private Arma armaMortal;
    private Habitacion habitacionDelCrimen;
    private List<Jugador> jugadores;

    Juego(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        personajes = new ArrayList<>();
        armas = new ArrayList<>();
        habitaciones = new ArrayList<>();
        inicializarJuego();
    }

    private void inicializarJuego() {
        // Inicializar personajes
        personajes.add(new Personaje("Hechicera"));
        personajes.add(new Personaje("Licantropa"));
        personajes.add(new Personaje("Vampiro"));
        personajes.add(new Personaje("Fantasma"));
        personajes.add(new Personaje("Princesa"));

        // Inicializar armas
        armas.add(new Arma("Candelabro"));
        armas.add(new Arma("Cuchillo"));
        armas.add(new Arma("Cable"));
        armas.add(new Arma("Tubos"));
        armas.add(new Arma("Pistola"));

        // Inicializar habitaciones
        habitaciones.add(new Habitacion("Biblioteca"));
        habitaciones.add(new Habitacion("Comedor"));
        habitaciones.add(new Habitacion("Cocina"));
        habitaciones.add(new Habitacion("Salon"));
        habitaciones.add(new Habitacion("Jardin"));

        // Selección aleatoria del asesino, arma y habitación
        Random random = new Random();
        asesino = personajes.get(random.nextInt(personajes.size()));
        armaMortal = armas.get(random.nextInt(armas.size()));
        habitacionDelCrimen = habitaciones.get(random.nextInt(habitaciones.size()));

        // Repartir cartas a jugadores
        repartirCartas();
    }

    private void repartirCartas() {
        List<Personaje> personajesRestantes = new ArrayList<>(personajes);
        List<Arma> armasRestantes = new ArrayList<>(armas);
        List<Habitacion> habitacionesRestantes = new ArrayList<>(habitaciones);

        // Eliminar cartas de la solución de las listas
        personajesRestantes.remove(asesino);
        armasRestantes.remove(armaMortal);
        habitacionesRestantes.remove(habitacionDelCrimen);

        Random random = new Random();
        for (Jugador jugador : jugadores) {
            // Repartir cartas de personajes (excluyendo el asesino)
            if (!personajesRestantes.isEmpty()) {
                jugador.getCartasPersonaje().add(personajesRestantes.remove(random.nextInt(personajesRestantes.size())));
            }
            // Repartir cartas de armas (excluyendo el arma mortal)
            if (!armasRestantes.isEmpty()) {
                jugador.getCartasArma().add(armasRestantes.remove(random.nextInt(armasRestantes.size())));
            }
            // Repartir cartas de habitaciones (excluyendo la habitación del crimen)
            if (!habitacionesRestantes.isEmpty()) {
                jugador.getCartasHabitacion().add(habitacionesRestantes.remove(random.nextInt(habitacionesRestantes.size())));
            }
        }
    }
