/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author mhrimaz
 */
public class AnimationMaker {

    private SequentialTransition sequentialTransition;
    

    public AnimationMaker() {
        sequentialTransition = new SequentialTransition();
        sequentialTransition.setAutoReverse(true);
        sequentialTransition.setCycleCount(2);
    }

    public void addNode(graph.Node node) {
        FillTransition fillColor
                = new FillTransition(Duration.millis(800), node.getShape(), node.getColor(), Color.YELLOW);
        sequentialTransition.getChildren().add(fillColor);
    }

    public void addEdge(graph.Edge edge) {
        FillTransition fillColor
                = new FillTransition(Duration.millis(500), edge.getShape(), Color.DARKSLATEGREY, Color.LIGHTSLATEGREY);
        sequentialTransition.getChildren().add(fillColor);
    }
    
    public void play(){
        sequentialTransition.play();
    }
}
