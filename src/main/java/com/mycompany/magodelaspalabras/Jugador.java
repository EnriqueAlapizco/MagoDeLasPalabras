/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.magodelaspalabras;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private final String nombre;
    private int puntaje;
    private final List<String> palabrasUsadas;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.palabrasUsadas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void agregarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    public void usarPalabra(String palabra) {
        palabrasUsadas.add(palabra);
    }

    public List<String> getPalabrasUsadas() {
        return palabrasUsadas;
    }

    public void reiniciarPalabras() {
        palabrasUsadas.clear();
    }
}

