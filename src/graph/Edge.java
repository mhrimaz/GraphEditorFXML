/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Objects;
import javafx.scene.shape.Shape;

/**
 *
 * @author mhrimaz
 */
public class Edge {
    private final Node startNode;
    private final Node endNode;
    private final Shape shape;
    private double weight;
    public final static double DEFAULT_WEIGHT = 1;

    public Edge(Node startNode, Node endNode, Shape representation, double weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
        this.shape = representation;
    }

    /**
     * @return the startNode
     */
    public Node getStartNode() {
        return startNode;
    }

    /**
     * @return the endNode
     */
    public Node getEndNode() {
        return endNode;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    /**
     * 
     * @return shape of this edge that may be Line or CubicCurve
     */
    public Shape getShape() {
        return shape;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.startNode);
        hash = 17 * hash + Objects.hashCode(this.endNode);
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) {
            return false;
        }
        if (!Objects.equals(this.startNode, other.startNode)) {
            return false;
        }
        if (!Objects.equals(this.endNode, other.endNode)) {
            return false;
        }
        return true;
    }

}
