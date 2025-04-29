/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.magodelaspalabras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Diccionario {
    private Map<String, Integer> palabrasValidas;

    public Diccionario() {
        palabrasValidas = new HashMap<>();
        cargarDesdeArchivo("C:\\Users\\enriq\\Documents\\NetBeansProjects\\MagoDeLasPalabras\\src\\main\\java\\com\\mycompany\\magodelaspalabras\\Diccionario.txt"); 
    }

    private void cargarDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                
                String[] palabras = linea.split(",\\s*");
                for (String palabra : palabras) {
                    palabra = palabra.toLowerCase();
                    int puntos = calcularPuntos(palabra);
                    palabrasValidas.put(palabra, puntos);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo del diccionario: " + e.getMessage());
        }
    }

    private int calcularPuntos(String palabra) {
        int puntos = 0;
        for (char c : palabra.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) {
                puntos += 5;
            } else if (Character.isLetter(c)) {
                puntos += 3;
            }
        }
        return puntos;
    }

    public boolean esPalabraValida(String palabra) {
        return palabrasValidas.containsKey(palabra.toLowerCase());
    }

    public int obtenerPuntaje(String palabra) {
        return palabrasValidas.getOrDefault(palabra.toLowerCase(), 0);
    }

    public Map<String, Integer> obtenerDiccionario() {
        return palabrasValidas;
    }
    
    
     public boolean agregarPalabraManual(String nuevaPalabra) {
        nuevaPalabra = nuevaPalabra.toLowerCase().trim();

        if (nuevaPalabra.isEmpty() || palabrasValidas.containsKey(nuevaPalabra)) {
            return false; // ya existe o está vacía
        }

        int puntos = calcularPuntos(nuevaPalabra);
        palabrasValidas.put(nuevaPalabra, puntos);

        // Agregar la palabra al archivo, separada por coma y espacio
        try (FileWriter fw = new FileWriter("C:\\Users\\enriq\\Documents\\NetBeansProjects\\MagoDeLasPalabras\\src\\main\\java\\com\\mycompany\\magodelaspalabras\\Diccionario.txt", true)) {
            fw.write(", " + nuevaPalabra);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo del diccionario: " + e.getMessage());
            return false;
        }

        return true;
    }
    
    
}