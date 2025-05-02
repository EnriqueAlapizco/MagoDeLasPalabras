/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.magodelaspalabras;


import javax.swing.*;
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
            int seguir = 0;
            while (seguir == 0) {
                for (Jugador jugador : jugadores) {
                    JOptionPane.showMessageDialog(null, "Ingrese una palabra", "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                    String palabra = JOptionPane.showInputDialog("Letras disponibles: " + letrasDeRonda);
                    if (palabra.isEmpty()) continue;

                    if (palabrasUsadasEnRonda.contains(palabra)) {
                        JOptionPane.showMessageDialog(null, "Esa palabra ya se usó. Pierdes 5 puntos.", "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println(" Esa palabra ya se usó. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                        continue;
                    }

                    if (!diccionario.esPalabraValida(palabra)) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "Palabra inválida. ¿Deseas registrarla?", "-", JOptionPane.YES_NO_OPTION);
                        //String respuesta = scanner.nextLine().trim().toLowerCase();
                        if (respuesta==0) {
                            diccionario.agregarPalabraManual(palabra);
                            JOptionPane.showMessageDialog(null, "Palabra registrada exitosamente.", "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                            //System.out.println(" Palabra registrada exitosamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Palabra rechazada. Pierdes 5 puntos.", "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                            //System.out.println(" Palabra rechazada. Pierdes 5 puntos.");
                            jugador.agregarPuntos(-5);
                            continue;
                        }
                    }

                    if (!letrasValidas(palabra, letrasDeRonda)) {
                        JOptionPane.showMessageDialog(null, "No se puede formar con las letras dadas. Pierdes 5 puntos.", "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println(" No se puede formar con las letras dadas. Pierdes 5 puntos.");
                        jugador.agregarPuntos(-5);
                    } else {
                        int puntos = diccionario.obtenerPuntaje(palabra);
                        jugador.agregarPuntos(puntos);
                        jugador.usarPalabra(palabra);
                        palabrasUsadasEnRonda.add(palabra);
                        JOptionPane.showMessageDialog(null, "Palabra válida. + " + puntos + " puntos.", "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println(" Palabra válida. + " + puntos + " puntos.");
                    }
                    JOptionPane.showMessageDialog(null, "Puntaje actual: " + jugador.getPuntaje() , "Jugador: " + jugador.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                    //System.out.println("Puntaje actual: " + jugador.getPuntaje());
                }

                seguir = JOptionPane.showConfirmDialog(null, "¿Desean continuar esta ronda?", "-", JOptionPane.YES_NO_OPTION);
            }

            jugadores.forEach(Jugador::reiniciarPalabras);
        }
    }

    public void mostrarResultadosFinales() {
        //System.out.println("\nRESULTADOS FINALES:");
        jugadores.forEach(j ->
                JOptionPane.showMessageDialog(null, "Puntaje de " + j.getNombre() + " : " + j.getPuntaje() , "RESULTADOS FINALES:", JOptionPane.INFORMATION_MESSAGE)
        );
        Jugador ganador = jugadores.stream()
                .max(Comparator.comparingInt(Jugador::getPuntaje))
                .orElse(null);
        if (ganador != null) {
            JOptionPane.showMessageDialog(null, "\n¡Ganador: " + ganador.getNombre() + " con " + ganador.getPuntaje() + " puntos!", "RESULTADOS FINALES:", JOptionPane.INFORMATION_MESSAGE);
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