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

    private int turnoActual;


    Juego(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = 0;
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
        asignarCartasEspeciales();

    }

    private void asignarCartasEspeciales() {
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            String tipo = (i % 3 == 0) ? "Personaje" : (i % 3 == 1) ? "Arma" : "Habitación";

            Jugador jugadorObjetivo;
            do {
                jugadorObjetivo = jugadores.get((i + 1) % jugadores.size());
            } while (jugadorObjetivo == jugador);

            CartaEspecial cartaEspecial = new CartaEspecial(tipo, jugadorObjetivo);
            jugador.getCartasEspeciales().add(cartaEspecial);
        }
    }


    void usarCartaEspecial(Jugador jugadorActual) {
        Scanner scanner = new Scanner(System.in);

        if (jugadorActual.getCartasEspeciales().isEmpty()) {
            System.out.println(jugadorActual.getNombre() + " no tiene cartas especiales disponibles.");
            return;
        }

        Jugador jugadorObjetivo = null;
        boolean jugadorValido = false;

        while (!jugadorValido) {
            System.out.println(jugadorActual.getNombre() + ", elige a qué jugador deseas ver una carta:");
            for (Jugador jugador : jugadores) {
                if (!jugador.equals(jugadorActual)) {
                    System.out.println("- " + jugador.getNombre());
                }
            }

            String nombreJugadorObjetivo = scanner.nextLine();

            for (Jugador jugador : jugadores) {
                if (jugador.getNombre().equalsIgnoreCase(nombreJugadorObjetivo)) {
                    jugadorObjetivo = jugador;
                    jugadorValido = true;
                    break;
                }
            }

            if (!jugadorValido) {
                System.out.println("Jugador no encontrado. Por favor, intenta de nuevo.");
            }
        }

        int tipoOpcion = 0;
        boolean opcionValida = false;

        while (!opcionValida) {
            System.out.println("¿Qué tipo de carta deseas ver de " + jugadorObjetivo.getNombre() + "?");
            System.out.println("1. Personaje");
            System.out.println("2. Arma");
            System.out.println("3. Habitación");

            String entrada = scanner.nextLine();
            try {
                tipoOpcion = Integer.parseInt(entrada);
                opcionValida = true; // Si llega aquí, la entrada es válida
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debes ingresar un número entero.");
                opcionValida = false; // Mantener el bucle
            }

            switch (tipoOpcion) {
                case 1:
                    System.out.println("Revelando personaje de " + jugadorObjetivo.getNombre() + ": " + jugadorObjetivo.getCartasPersonaje());
                    break;
                case 2:
                    System.out.println("Revelando arma de " + jugadorObjetivo.getNombre() + ": " + jugadorObjetivo.getCartasArma());
                    break;
                case 3:
                    System.out.println("Revelando habitación de " + jugadorObjetivo.getNombre() + ": " + jugadorObjetivo.getCartasHabitacion());
                    break;
                default:
                    System.out.println("Por favor, elige entre 1 y 3.");
                    opcionValida = false; // Volver a pedir la opción
                    break;
            }
        }

        CartaEspecial cartaEspecial = jugadorActual.getCartasEspeciales().get(0);
        if (jugadorActual.usarCartaEspecial(cartaEspecial)) {
            System.out.println("Has usado una carta especial.");
        } else {
            System.out.println("No se pudo usar la carta especial.");
        }
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
    private String obtenerMensajePorPersonaje(Personaje personaje) {
        switch (personaje.toString()) {
            case "Hechicera":
                return "La Hechicera había sido rechazada por el rey, quien ignoró su oferta de poder mágico. "
                        + "Con ganas de venganza, decidió asesinarlo para reclamar el reino " +
                        "y gobernar sin oposición.";

            case "Licantropa":
                return "La Licantropa había sido acusada de un crimen que no cometió. "
                        + "El rey, en lugar de ayudarla, la dejó sola. "
                        + "En la noche del baile, su rabia la llevó a actuar, " +
                        "buscando recuperar su lugar en la nobleza.";

            case "Vampiro":
                return "El Vampiro, hambriento de poder, había estado observando al rey. "
                        + "Creyendo que el rey había arruinado a su familia, " +
                        "decidió acabar con él para vengar a los suyos y tomar el trono.";

            case "Fantasma":
                return "El Fantasma es el espíritu de un rey antiguo traicionado por su propio consejo. "
                        + "Su ira se desata sobre el actual rey, que representa un linaje que él considera corrupto. "
                        + "Busca justicia y quiere advertir a los poderosos del reino.";

            case "Princesa":
                return "La Princesa, atrapada en un matrimonio sin amor, deseaba una vida diferente. "
                        + "Al ver que el rey ignoraba sus sueños, decidió que la única forma de escapar " +
                        "era eliminarlo y gobernar ella misma.";

            default:
                return "Motivo desconocido.";
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
            System.out.println(obtenerMensajePorPersonaje(asesino));
            System.out.println("¿Quieres volver a jugar o salir?");
            System.out.println("1. Volver a jugar");
            System.out.println("2. Salir");

            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
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
            if (!acertoPersonaje && acertoArma) {
                System.out.println("Acierto en el arma: " + sugeridoArma);
            }
            if (!acertoPersonaje && !acertoArma && acertoHabitacion) {
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

    public static void limpiarPantalla() {
        // Número de líneas en blanco que se imprimen
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    void realizarTurno() {
        Scanner scanner = new Scanner(System.in);
        Jugador jugadorActual = jugadores.get(turnoActual);

        // Mostrar las cartas del jugador actual
        mostrarDetallesJugador(jugadorActual);

        // Preguntar si el jugador quiere usar una carta especial
        System.out.println("¿Quieres usar una carta especial? (escribe s para usarla)");
        String usarCartaEspecial = scanner.nextLine().trim().toLowerCase();

        if (usarCartaEspecial.equals("s")) {
            usarCartaEspecial(jugadorActual);
        }


        // Mostrar las opciones disponibles para el jugador
        System.out.println("Opciones disponibles para sugerir:");
        System.out.println("Personajes: ");
        for (Personaje personaje : personajes) {
            if (!jugadorActual.getCartasPersonaje().contains(personaje)) {
                System.out.println("  - " + personaje);
            }
        }

        System.out.println("Armas: ");
        for (Arma arma : armas) {
            if (!jugadorActual.getCartasArma().contains(arma)) {
                System.out.println("  - " + arma);
            }
        }

        System.out.println("Habitaciones: ");
        for (Habitacion habitacion : habitaciones) {
            if (!jugadorActual.getCartasHabitacion().contains(habitacion)) {
                System.out.println("  - " + habitacion);
            }
        }

        System.out.println("Es el turno de " + jugadorActual.getNombre());

        // Solicitar sugerencias por separado
        Personaje sugeridoAsesino = obtenerPersonajeValido(scanner);
        Arma sugeridoArma = obtenerArmaValida(scanner);
        Habitacion sugeridaHabitacion = obtenerHabitacionValida(scanner);

        hacerSugerencia(jugadorActual, sugeridoAsesino, sugeridoArma, sugeridaHabitacion);

        // Esperar a que el jugador presione Enter antes de borrar la pantalla
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine(); // Espera la entrada del usuario

        // Avanzar al siguiente turno
        limpiarPantalla();
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

}


