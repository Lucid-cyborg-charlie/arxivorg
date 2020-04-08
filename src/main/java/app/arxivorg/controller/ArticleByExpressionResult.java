package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleByExpressionResult extends StatisticController implements Initializable {
    @FXML
    public BarChart<String, Integer> barChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> series= getSeries(ManagerArticle.map);
        series.setName("Expressions");
        barChart.getData().addAll(series);
    }

}
