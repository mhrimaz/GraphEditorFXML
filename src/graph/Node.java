/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author mhrimaz
 */
public class Node implements Comparable<Node> {

    private final Shape shape;
    private final Set<Edge> edges;
    private Color color;

    /**
     * for A* algorithm
     */
    private double distance;

    /**
     * for dijkestra Algorithm
     */
    private double actualDistance;

    public Node(Circle shape) {
        this.shape = shape;
        color = Color.BLACK;
        shape.setFill(color);
        edges = new HashSet<>();
        distance = 0.0;
        actualDistance = 0.0;
    }

    /**
     * 
     * @return return a copy of neighbors of this node
     */
    public Set<Node> getNeighbors() {
        Set<Node> neighbors = new HashSet<>();
        edges.stream().forEach((edge) -> {
            neighbors.add(edge.getEndNode());
        });
        return neighbors;
    }

    /**
     * 
     * @param edge an edge that go out from this node
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * @return the representation
     */
    public Shape getShape() {
        return shape;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.shape);
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.shape, other.shape)) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return all the edges that goes out from this node
     */
    protected Set<Edge> getEdges() {
        return edges;
    }

    @Override
    public int compareTo(Node other) {
        Double thisEstimation = this.getActualDistance() + this.getDistance();
        Double otherEstimation = other.getActualDistance() + other.getDistance();
        return thisEstimation.compareTo(otherEstimation);
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the actualDistance
     */
    public double getActualDistance() {
        return actualDistance;
    }

    /**
     * @param actualDistance the actualDistance to set
     */
    public void setActualDistance(double actualDistance) {
        this.actualDistance = actualDistance;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
        shape.setFill(color);
    }

}
