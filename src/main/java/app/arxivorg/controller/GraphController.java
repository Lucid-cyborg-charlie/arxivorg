package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.ResourceBundle;


public class GraphController implements Initializable {

    @FXML
    public AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void buildGarph(Graph graph){
        for(Edge edge: graph.getEdges()){
            Point start=edge.getS1().getPoint();
            Label labelStart=new Label(edge.getS1().getName());
            labelStart.setLayoutX(start.getX());
            labelStart.setLayoutY(start.getY());

            Point end=edge.getS2().getPoint();
            Label labelEnd=new Label(edge.getS2().getName());
            labelEnd.setLayoutX(end.getX());
            labelEnd.setLayoutY(end.getY());

            Line line=new Line(start.getX(), start.getY(), end.getX(), end.getY());
            line.setStroke(Color.GREEN);
            root.getChildren().addAll(line, labelStart, labelEnd);
            root.setStyle("-fx-background-color: #fff;");
        }
    }
}
