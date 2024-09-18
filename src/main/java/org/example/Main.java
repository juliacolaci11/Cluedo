package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenidos Al Clue!");

        // Solicitar el número de jugadores
        int numJugadores = obtenerNumeroDeJugadores(scanner);

        List<Jugador> jugadores = new ArrayList<>();

        // Solicitar nombres de los jugadores
        for (int i = 1; i <= numJugadores; i++) {
            System.out.print("Ingresa el nombre del jugador " + i + ": ");
            String nombre = scanner.nextLine();
            jugadores.add(new Jugador(nombre));
        }

        Juego juego = new Juego(jugadores);

        // Iniciar el bucle del juego
        while (true) {
            juego.realizarTurno();
        }
    }

    // Función para obtener el número de jugadores con validación
    private static int obtenerNumeroDeJugadores(Scanner scanner) {
        boolean entradaValida = false;
        int numJugadores = 0;

        while (!entradaValida) {
            System.out.print("¿Cuántos jugadores están participando? ");
            String entrada = scanner.nextLine();

            if (esNumeroValido(entrada)) {
                numJugadores = Integer.parseInt(entrada);
                if (numJugadores >= 2 && numJugadores <= 4) {
                    entradaValida = true;
                } else {
                    System.out.println("Error: El número de jugadores debe estar entre 2 y 4. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Error: Entrada no válida. Debe ingresar un número entero.");
            }
        }

        return numJugadores;
    }

    // Función para verificar si la entrada es un número válido
    private static boolean esNumeroValido(String entrada) {
        try {
            Integer.parseInt(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}