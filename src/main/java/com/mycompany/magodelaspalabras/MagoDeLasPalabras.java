/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.magodelaspalabras;
import java.util.*;

public class MagoDeLasPalabras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Bienvenido al Mago de las Palabras!");

        int numJugadores;
        do {
            System.out.print("Ingrese el número de jugadores (2-4): ");
            numJugadores = scanner.nextInt();
            scanner.nextLine(); // limpiar el buffer
        } while (numJugadores < 2 || numJugadores > 4);

        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= numJugadores; i++) {
            System.out.print("Nombre del jugador " + i + ": ");
            String nombre = scanner.nextLine();
            jugadores.add(new Jugador(nombre));
        }

        int modo;
        do {
            System.out.println("Seleccione modalidad de juego:");
            System.out.println("1. Regular");
            System.out.println("2. Experto");
            System.out.print("Opción: ");
            modo = scanner.nextInt();
            scanner.nextLine();
        } while (modo != 1 && modo != 2);

        ModoJuego modalidad = (modo == 1) ? ModoJuego.REGULAR : ModoJuego.EXPERTO;

        Juego juego = new Juego(jugadores, modalidad);
        juego.jugar();
        juego.mostrarResultadosFinales();

        scanner.close();
    }
}
