/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Shape;
import util.AnimationMaker;
import util.UtillColor;

/**
 *
 * @author mhrimaz
 */
public class Graph {

    private HashMap<Shape, Node> nodes;
    private HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    public void addVertex(Circle location) {
        if (!nodes.containsKey(location)) {
            Node newNode = new Node(location);
            nodes.put(location, newNode);
        }
    }

    public void addEdge(Circle startLocation, Circle endLocation, CubicCurve shapeEdge) {
        Node startNode = nodes.get(startLocation);
        Node endNode = nodes.get(endLocation);
        if (startNode != null && endNode != null) {
            addEdge(startNode, endNode, shapeEdge, Edge.DEFAULT_WEIGHT);
        }
    }

    public void addEdge(Circle startLocation, Circle endLocation, Shape shapeEdge, double weight) {
        Node startNode = nodes.get(startLocation);
        Node endNode = nodes.get(endLocation);
        if (startNode != null && endNode != null) {
            addEdge(startNode, endNode, shapeEdge, weight);
        }
    }

    public void deleteNode(Circle selectedNode) {
        Node get = nodes.get(selectedNode);
        if (get != null) {
            deleteNode(get);
            nodes.remove(selectedNode);
        }
    }

    private void deleteNode(Node node) {
        edges.parallelStream()
                .filter(edge -> edge.getEndNode().equals(node) || edge.getStartNode().equals(node))
                .forEach(edge -> edges.remove(edge));
    }

    private void addEdge(Node startNode, Node endNode, Shape shapeEdge, double weight) {
        Edge edge = new Edge(startNode, endNode, shapeEdge, weight);
        edges.add(edge);
        startNode.addEdge(edge);
    }

    private List<Node> bfs(Node startNode, Node endNode) {
        HashMap<Node, Node> parent = new HashMap<>();
        Queue<Node> toExplore = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();
        toExplore.add(startNode);
        visited.add(startNode);
        Node next = null;
        AnimationMaker animation = new AnimationMaker();
        while (!toExplore.isEmpty()) {
            next = toExplore.remove();

            animation.addNode(next);

            if (next.equals(endNode)) {
                break;
            }
            Set<Node> neighbors = next.getNeighbors();
            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, next);
                    toExplore.add(neighbor);
                }
            }
        }

        if (!next.equals(endNode)) {
            return null;
        }

        animation.play();
        // Reconstruct the parent path
        List<Node> path = reconstructPath(parent, startNode, endNode);
        return path;
    }

    public List<Node> bfs(Circle source, Circle target) {
        Node startNode = nodes.get(source);
        Node endNode = nodes.get(target);
        if (startNode == null || endNode == null) {
            return null;
        }
        return bfs(startNode, endNode);
    }

    public List<Node> dijkstra(Circle source, Circle target) {
        Node startNode = nodes.get(source);
        Node endNode = nodes.get(target);
        if (startNode == null || endNode == null) {
            return null;
        }
        return dijkstra(startNode, endNode);
    }

    public boolean isHaveCycle(Circle source, Circle target) {
        Node startNode = nodes.get(source);
        Node endNode = nodes.get(target);
        if (startNode == null || endNode == null) {
            return false;
        }
        return isHaveCycle(startNode, endNode);
    }

    public boolean colorize(int colorSize) {
        nodes.values().forEach(node -> {
            node.setColor(Color.BLACK);
        });
        ArrayList<Node> allNodes = new ArrayList<>(nodes.values());
        UtillColor.getInstance().shuffle();
        mColoring(allNodes, colorSize, 0);
        boolean isSolved = true;
        for (Node node : allNodes) {
            if (node.getColor().equals(Color.BLACK)) {
                isSolved = false;
                break;
            }
        }
        if (!isSolved) {
            nodes.values().forEach(node -> {
                node.setColor(Color.BLACK);
            });
        }
        return isSolved;
    }

    private void mColoring(ArrayList<Node> allNodes, int colorSize, int index) {
        Node node = allNodes.get(index);
        for (int color = 0; color < colorSize; color++) {
            if (isPromising(node, color)) {
                node.setColor(UtillColor.getInstance().getColor(color));
                if (index + 1 < allNodes.size()) {
                    mColoring(allNodes, colorSize, index + 1);
                } else {
                    return;
                }
            }
        }
//        node.setColor(Color.BLACK);
    }

    /**
     * check whether a node color is promising or not.
     * <br>
     * it will check node color to it's neighbors
     *
     * @param node node to check if it's color is promising
     * @return true if it's promising and false if it's not
     */
    private boolean isPromising(Node node, int colorIndex) {
        Set<Node> neighbors = node.getNeighbors();
        Color color = UtillColor.getInstance().getColor(colorIndex);
        return neighbors.stream().noneMatch((neighbor)
                -> (color.equals(neighbor.getColor())));
    }

    private boolean isHaveCycle(Node startNode, Node endNode) {

        return false;
    }

    private List<Node> reconstructPath(HashMap<Node, Node> parentMap, Node start, Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (!current.equals(start)) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        // add start
        path.addFirst(start);
        return path;
    }

    private List<Node> dijkstra(Node startNode, Node endNode) {
        HashMap<Node, Node> parentMap = new HashMap<>();
        Queue<Node> toExplore = new PriorityQueue<>();

        HashSet<Node> visited = new HashSet<>();
        nodes.values().forEach(node -> {
            node.setActualDistance(Double.MAX_VALUE);
            node.setDistance(0.0);
        });
        startNode.setActualDistance(0.0);
        startNode.setDistance(0.0);
        toExplore.add(startNode);
        Node next = null;
        AnimationMaker animation = new AnimationMaker();
        while (!toExplore.isEmpty()) {
            next = toExplore.remove();
            animation.addNode(next);
            if (next.equals(endNode)) {
                break;
            }
            if (!visited.contains(next)) {
                visited.add(next);
                Set<Edge> nodeEdges = next.getEdges();
                for (Edge edge : nodeEdges) {
                    if (!visited.contains(edge.getEndNode())) {
                        if (edge.getWeight() + next.getActualDistance() < edge.getEndNode().getActualDistance()) {
                            edge.getEndNode().setActualDistance(edge.getWeight() + next.getActualDistance());
                            parentMap.put(edge.getEndNode(), next);
                            toExplore.add(edge.getEndNode());
                        }
                    }
                }
            }

        }
        if (!next.equals(endNode)) {
            return null;
        }
        animation.play();
        // Reconstruct the parent path
        List<Node> path = reconstructPath(parentMap, startNode, endNode);

        return path;
    }

}
