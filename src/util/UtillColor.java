/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author mhrimaz
 */
public class UtillColor {

    private final ArrayList<Color> colors;
    private Random rand;
    private static UtillColor utillColor = new UtillColor();

    private UtillColor() {
        colors = new ArrayList<>();
        rand = new Random();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("/res/Color.txt")));) {
            bf.lines().forEach(line -> {
                colors.add(Color.web(line));
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ex = " + ex);
        }
        Collections.shuffle(colors);
    }

    public static UtillColor getInstance() {
        return utillColor;
    }

    public Color getColor(int index) {
        return colors.get(index);
    }

    public Color getRandomColor() {
        int nextInt = rand.nextInt(colors.size());
        return colors.get(nextInt);
    }
    
    public void shuffle(){
        Collections.shuffle(colors);
    }
}
