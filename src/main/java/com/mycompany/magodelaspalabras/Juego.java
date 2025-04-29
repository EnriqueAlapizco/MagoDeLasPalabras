/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.magodelaspalabras;


import java.util.*;
import java.util.stream.Collectors;

public class Juego {
    private final List<Jugador> jugadores;
    private final Diccionario diccionario;
    private final Random random = new Random();

    public Juego(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.diccionario = new Diccionario();
        jugar();
        mostrarResultadosFinales();
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);
        for (int ronda = 1; ronda <= 3; ronda++) {
            System.out.println("\n --- RONDA " + ronda + " ---");
            HashSet<String> palabrasUsadasEnRonda = new HashSet<>();
            Set<Character> letrasDeRonda = generarLetrasRonda();
            //System.out.println("Letras disponibles: " + letrasDeRonda);
            boolean seguir = true;
            while (seguir) {
                for (Jugador jugador : jugadores) {
                    System.out.println("Letras disponibles: " + letrasDeRonda);
                    System.out.println("\nTurno de " + jugador.getNombre());
                    System.out.print("Escribe una palabra (ENTER para pasar): ");
                    String palabra = scanner.nextLine().toLowerCase().trim();
                    if (palabra.isEmpty()) continue;

                    if (palabrasUsadasEnRonda.contains(palabra)) {
                        System.out.println(" Esa palabra ya se usó. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                        continue;
                    }

                    if (!diccionario.esPalabraValida(palabra)) {
                        System.out.print(" Palabra inválida. ¿Deseas registrarla? (s/n): ");
                        String respuesta = scanner.nextLine().trim().toLowerCase();
                        if (respuesta.equals("s")) {
                            diccionario.agregarPalabraManual(palabra);
                            System.out.println(" Palabra registrada exitosamente.");
                        } else {
                            System.out.println(" Palabra rechazada. Pierdes 5 puntos.");
                            jugador.agregarPuntos(-5);
                            continue;
                        }
                    }

                    if (!letrasValidas(palabra, letrasDeRonda)) {
                        System.out.println(" No se puede formar con las letras dadas. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                    } else {
                        int puntos = diccionario.obtenerPuntaje(palabra);
                        jugador.agregarPuntos(puntos);
                        jugador.usarPalabra(palabra);
                        palabrasUsadasEnRonda.add(palabra);
                        System.out.println(" Palabra válida. + " + puntos + " puntos.");
                    }

                    System.out.println("Puntaje actual: " + jugador.getPuntaje());
                }

                System.out.print("\n¿Desean continuar esta ronda? (s/n): ");
                String respuesta = scanner.nextLine();
                seguir = respuesta.equalsIgnoreCase("s");
            }

            jugadores.forEach(Jugador::reiniciarPalabras);
        }
    }

    public void mostrarResultadosFinales() {
        System.out.println("\nRESULTADOS FINALES:");
        jugadores.forEach(j ->
                System.out.println(j.getNombre() + " -> " + j.getPuntaje() + " puntos")
        );
        Jugador ganador = jugadores.stream()
                .max(Comparator.comparingInt(Jugador::getPuntaje))
                .orElse(null);
        if (ganador != null) {
            System.out.println("\n¡Ganador: " + ganador.getNombre() + " con " + ganador.getPuntaje() + " puntos!");
        }
    }

    private Set<Character> generarLetrasRonda() {
        Set<Character> letras = new HashSet<>();
        String vocales = "aeiou";
        String consonantes = "bcdfghjklmnpqrstvy";
        boolean modoExperto = false;
        int cantidadLetras = modoExperto ? 12 : 10;

        while (letras.size() < 2) {
            char vocal = vocales.charAt(random.nextInt(vocales.length()));
            letras.add(vocal);
        }

        while (letras.size() < cantidadLetras) {
            char letra = random.nextBoolean()
                    ? vocales.charAt(random.nextInt(vocales.length()))
                    : consonantes.charAt(random.nextInt(consonantes.length()));
            letras.add(letra);
        }

        return letras;
    }

    private boolean letrasValidas(String palabra, Set<Character> letrasDisponibles) {
        Set<Character> caracteresPalabra = palabra.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
        for (Character letra : caracteresPalabra) {
            if (!letrasDisponibles.contains(letra)) {
                return false;
            }
        }
        return true;
    }
}