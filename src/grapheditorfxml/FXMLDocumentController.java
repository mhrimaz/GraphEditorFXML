/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grapheditorfxml;

import graph.Graph;
import graph.Node;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import shapes.Arrow;
import util.UtillColor;

/**
 *
 * @author mhrimaz
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane graphPane;
    @FXML
    private TextField colorLimitTF;
    private Circle source;
    private Circle target;
    private Graph graph = new Graph();

    @FXML
    private void handleBFSButton(ActionEvent event) {
        if (source != null && target != null) {
            List<Node> bfs = graph.bfs(source, target);
            if (bfs == null) {
                showAlertInformation("No path found between Selected Nodes");
            }
            reset();
        } else {
            showAlertInformation("You must Select source (RED) and destionation (BLUE)");
        }
    }

    private void showAlertInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, information, ButtonType.OK);
        alert.setHeaderText("Algorithm Result");
        alert.setTitle("Info");
        alert.showAndWait();
    }

    private void getInputCurveEdge(MouseEvent event) {
        CubicCurve curve
                = new CubicCurve(source.getCenterX(), source.getCenterY(),
                        event.getSceneX(), event.getSceneY(),
                        event.getSceneX(), event.getSceneY(),
                        target.getCenterX(), target.getCenterY());
        Arrow arrow = new Arrow(curve, 0.9f);
        curve.setStroke(Color.DARKSLATEGRAY);
        curve.setStrokeWidth(3);
        curve.setFill(Color.TRANSPARENT);
        TextInputDialog dialog = new TextInputDialog("1.0");
        dialog.setTitle("Weight of Edge");
        dialog.setHeaderText("Enter Weight of this Edge");
        dialog.setContentText("Please Enter a Double Number: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                double weight = Double.parseDouble(input);
                graph.addEdge(source, target, curve, weight);
                graphPane.getChildren().addAll(curve, arrow);
                Tooltip tip = new Tooltip("Weight : " + weight);
                Tooltip.install(curve, tip);
                curve.toBack();
            } catch (NumberFormatException ex) {

            }
        });
    }

    private void getInputDirectEdge(MouseEvent event) {
        Line line = new Line(source.getCenterX(), source.getCenterY(),
                target.getCenterX(), target.getCenterY());
        line.setStroke(Color.DARKSLATEGRAY);
        line.setStrokeWidth(3);
        line.setFill(Color.TRANSPARENT);
        TextInputDialog dialog = new TextInputDialog("1.0");
        dialog.setTitle("Weight of Edge");
        dialog.setHeaderText("Enter Weight of this Edge");
        dialog.setContentText("Please Enter a Double Number: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                double weight = Double.parseDouble(input);
                graph.addEdge(source, target, line, weight);
                graph.addEdge(target, source, line, weight);
                graphPane.getChildren().addAll(line);
                Tooltip tip = new Tooltip("Weight : " + weight);
                Tooltip.install(line, tip);
                line.toBack();
            } catch (NumberFormatException ex) {

            }
        });
    }

    private void reset() {
        if (source != null) {
            source.setFill(Color.BLACK);
            source = null;
        }
        if (target != null) {
            target.setFill(Color.BLACK);
            target = null;
        }
    }

    @FXML
    private void handlePaneClicked(MouseEvent event) {
        if (event.isConsumed()) {
            return;
        }
        if (source != null && target != null) {
            if (!source.equals(target)) {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    getInputDirectEdge(event);
                } else {
                    getInputCurveEdge(event);
                }
            }
            reset();
            return;
        }

        Circle circle = new Circle(event.getSceneX() - graphPane.getLayoutX(),
                event.getSceneY() - graphPane.getLayoutY(), 12);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        graph.addVertex(circle);
        circle.setOnMouseClicked(cricleEvent -> {
            if (source == null) {
                source = (Circle) cricleEvent.getSource();
                source.setFill(Color.RED);
            } else if (target == null) {
                target = (Circle) cricleEvent.getSource();
                target.setFill(Color.DARKBLUE);
            }
            cricleEvent.consume();
        });
        graphPane.getChildren().add(circle);
    }

    @FXML
    private void handleDijkstraButton() {
        if (source != null && target != null) {
            List<Node> dijkstra = graph.dijkstra(source, target);
            if (dijkstra == null) {
                showAlertInformation("No path found between Selected Nodes");
            }
            reset();
        } else {
            showAlertInformation("You must Select source (RED) and destionation (BLUE)");
        }
    }

    @FXML
    private void handleColorizeButton() {
        reset();
        boolean haveColor = graph.colorize(Integer.parseInt(colorLimitTF.getText()));
        if (!haveColor) {
            showAlertInformation("No Solution Found for this graph");
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (source != null) {
            graph.deleteNode(source);
            graphPane.getChildren().remove(source);
            reset();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
