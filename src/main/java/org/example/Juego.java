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

    void hacerSugerencia(Jugador jugador, Personaje sugeridoAsesino, Arma sugeridoArma, Habitacion sugeridaHabitacion) {
        boolean acertoPersonaje = sugeridoAsesino.equals(asesino);
        boolean acertoArma = sugeridoArma.equals(armaMortal);
        boolean acertoHabitacion = sugeridaHabitacion.equals(habitacionDelCrimen);

        System.out.println(jugador.getNombre() + " hace una sugerencia: " +
                sugeridoAsesino + " en " + sugeridaHabitacion + " con " + sugeridoArma);

        if (acertoPersonaje && acertoArma && acertoHabitacion) {
            System.out.println("¡" + jugador.getNombre() + " ha resuelto el caso!");
            System.out.println("¿Quieres volver a jugar o salir?");
            System.out.println("1. Volver a jugar");
            System.out.println("2. Salir");

            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Aquí puedes llamar al método que reinicie el juego o configurar una nueva ronda
                    System.out.println("¡Preparándote para una nueva ronda!");
                    break;
                case 2:
                    System.out.println("Gracias por jugar. ¡Hasta luego!");
                    System.exit(0);  // Termina la aplicación
                    break;
                default:
                    System.out.println("Opción no válida. El juego terminará.");
                    System.exit(0);  // Termina la aplicación si la opción es inválida
                    break;
            }
        } else {
            if (acertoPersonaje) {
                System.out.println("Acierto en el personaje: " + sugeridoAsesino);
            }
            if (acertoArma) {
                System.out.println("Acierto en el arma: " + sugeridoArma);
            }
            if (acertoHabitacion) {
                System.out.println("Acierto en la habitación: " + sugeridaHabitacion);
            }
            if (!acertoPersonaje && !acertoArma && !acertoHabitacion) {
                System.out.println("Sugerencia incorrecta.");
            }
        }

        // Opciones al final del juego

    }

    void mostrarDetallesJugador(Jugador jugador) {
        System.out.println(jugador.getNombre() + " tiene las siguientes cartas:");
        System.out.println("Personajes: " + jugador.getCartasPersonaje());
        System.out.println("Armas: " + jugador.getCartasArma());
        System.out.println("Habitaciones: " + jugador.getCartasHabitacion());
    }

    private Personaje obtenerPersonajeValido(Scanner scanner) {
        boolean esValido = false;
        Personaje personaje = null;
        while (!esValido) {
            System.out.println("Introduce la sugerencia para el personaje:");
            String entrada = scanner.nextLine();
            personaje = new Personaje(entrada);
            if (personajes.contains(personaje)) {
                esValido = true;
            } else {
                System.out.println("Opción inválida. Por favor, introduce un personaje válido.");
            }
        }
        return personaje;
    }

    private Arma obtenerArmaValida(Scanner scanner) {
        boolean esValido = false;
        Arma arma = null;
        while (!esValido) {
            System.out.println("Introduce la sugerencia para el arma:");
            String entrada = scanner.nextLine();
            arma = new Arma(entrada);
            if (armas.contains(arma)) {
                esValido = true;
            } else {
                System.out.println("Opción inválida. Por favor, introduce un arma válida.");
            }
        }
        return arma;
    }

    private Habitacion obtenerHabitacionValida(Scanner scanner) {
        boolean esValido = false;
        Habitacion habitacion = null;
        while (!esValido) {
            System.out.println("Introduce la sugerencia para la habitación:");
            String entrada = scanner.nextLine();
            habitacion = new Habitacion(entrada);
            if (habitaciones.contains(habitacion)) {
                esValido = true;
            } else {
                System.out.println("Opción inválida. Por favor, introduce una habitación válida.");
            }
        }
        return habitacion;
    }
}


