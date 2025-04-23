/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.magodelaspalabras;

import java.util.*;
import java.util.stream.Collectors;

public class Juego {
    private final List<Jugador> jugadores;
    private final ModoJuego modoJuego;
    private final Diccionario diccionario;
    private final Random random = new Random();

    public Juego(List<Jugador> jugadores, ModoJuego modoJuego) {
        this.jugadores = jugadores;
        this.modoJuego = modoJuego;
        this.diccionario = new Diccionario();
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);

        for (int ronda = 1; ronda <= 3; ronda++) {
            System.out.println("\n --- RONDA " + ronda + " ---");

            HashSet<String> palabrasUsadasEnRonda = new HashSet<>();
            //HashSet<Character> letrasDeRonda = generarLetrasDeRonda();
            Set<Character> letrasDeRonda = generarLetrasRonda();

            System.out.println("Letras disponibles: " + letrasDeRonda);

            boolean seguir = true;
            while (seguir) {
                for (Jugador jugador : jugadores) {
                    System.out.println("\nTurno de " + jugador.getNombre());
                    System.out.print("Escribe una palabra (ENTER para pasar): ");
                    String palabra = scanner.nextLine().toLowerCase().trim();

                    if (palabra.isEmpty()) continue;

                    if (palabrasUsadasEnRonda.contains(palabra)) {
                        System.out.println(" Esa palabra ya se uso. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                        continue;
                    }

                    if (!diccionario.esPalabraValida(palabra)) {
                        System.out.println(" Palabra invalida. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                    } else if (!letrasValidas(palabra, letrasDeRonda)) {
                        System.out.println(" No se pueden formar con las letras dadas. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                    } else {
                        int puntos = diccionario.obtenerPuntaje(palabra);
                        jugador.agregarPuntos(puntos);
                        jugador.usarPalabra(palabra);
                        palabrasUsadasEnRonda.add(palabra);

                        System.out.println(" Palabra valida. + " + puntos + " puntos.");
                    }

                    System.out.println("Puntaje actual: " + jugador.getPuntaje());
                }

                System.out.print("\n¿Desean continuar esta ronda? (s/n): ");
                String respuesta = scanner.nextLine();
                seguir = respuesta.equalsIgnoreCase("s");
            }

            // Limpiar palabras usadas por jugador
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

//    private HashSet<Character> generarLetrasDeRonda() {
//        int cantidadLetras = (modoJuego == ModoJuego.REGULAR) ? 10 : 12;
//        String abecedario = "abcdefghijklmnopqrstuvwxyz";
//
//        HashSet<Character> letras = new HashSet<>();
//        while (letras.size() < cantidadLetras) {
//            char letra = abecedario.charAt(random.nextInt(abecedario.length()));
//            letras.add(letra);
//        }
//
//        return letras;
//    }

    
    
    private Set<Character> generarLetrasRonda() {
    Set<Character> letras = new HashSet<>();
    Random random = new Random();

    String vocales = "aeiou";
    String consonantes = "bcdfghjklmnpqrstvwxyz";
    boolean modoExperto = false;

    int cantidadLetras = modoExperto ? 12 : 10;

    // 1. Agregar mínimo 2 vocales obligatorias
    while (letras.size() < 2) {
        char vocal = vocales.charAt(random.nextInt(vocales.length()));
        letras.add(vocal);
    }

    // 2. Agregar el resto de letras (pueden ser vocales o consonantes)
    while (letras.size() < cantidadLetras) {
        char letra;
        if (random.nextBoolean()) {
            letra = vocales.charAt(random.nextInt(vocales.length()));
        } else {
            letra = consonantes.charAt(random.nextInt(consonantes.length()));
        }
        letras.add(letra);
    }

    return letras;
}
    
    private boolean letrasValidas(String palabra, Set<Character> letrasDisponibles) {
        Map<Character, Long> letrasPalabra = palabra.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        for (Map.Entry<Character, Long> entry : letrasPalabra.entrySet()) {
            long enRonda = letrasDisponibles.stream()
                    .filter(c -> c == entry.getKey())
                    .count();
            if (entry.getValue() > enRonda) return false;
        }
        return true;
    }
}
