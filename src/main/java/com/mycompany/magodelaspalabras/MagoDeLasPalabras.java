/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.magodelaspalabras;
import javax.swing.*;
import java.util.*;

public class MagoDeLasPalabras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JOptionPane.showMessageDialog(null, "Bienvenido al Mago de las Palabras", "-", JOptionPane.INFORMATION_MESSAGE);

        Object[] botones = {2, 3, 4};
        int numJugadores = 2 + JOptionPane.showOptionDialog(null, "Seleccione la cantidad de jugadores.", "Mago de las Palabras", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);

        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= numJugadores; i++) {
            String nombre = JOptionPane.showInputDialog("Nombre del jugador " + i + ": ");
            jugadores.add(new Jugador(nombre));
        }

        Object[] modalidades = {"Regular", "Experto"};
        int modo = 1 + JOptionPane.showOptionDialog(null, "Seleccione la modalidad de juego.", "Mago de las Palabras", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, modalidades, modalidades[0]);

        ModoJuego modalidad = (modo == 1) ? ModoJuego.REGULAR : ModoJuego.EXPERTO;
        
        Diccionario diccionario = new Diccionario();
        if (modo == 1) {
            new Juego(jugadores);
        }
        else {
            new JuegoExperto(jugadores);
        }
        scanner.close();
    }
}
