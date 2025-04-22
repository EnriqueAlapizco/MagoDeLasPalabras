/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.magodelaspalabras;

import java.util.HashMap;
import java.util.Map;

public class Diccionario {
    private final Map<String, Integer> palabrasValidas;

    public Diccionario() {
        palabrasValidas = new HashMap<>();

        // Palabras de ejemplo
        String[] lista = {"casa", "sapo", "luz", "raton", "botella", "nube", "flor", "sol", "gato", "pato"};

        for (String palabra : lista) {
            palabrasValidas.put(palabra, calcularPuntaje(palabra));
        }
    }

    public boolean esPalabraValida(String palabra) {
        return palabrasValidas.containsKey(palabra.toLowerCase());
    }

    public int obtenerPuntaje(String palabra) {
        return palabrasValidas.getOrDefault(palabra.toLowerCase(), 0);
    }

    private int calcularPuntaje(String palabra) {
        int puntos = 0;
        for (char c : palabra.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) {
                puntos += 5; // Para vocales
            } else if (Character.isLetter(c)) {
                puntos += 3; // Para consonantes
            }
        }
        return puntos;
    }
}
