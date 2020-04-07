package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticleByDay implements Initializable {
    private ManagerArticle managerArticle = new ManagerArticle();
    @FXML
    private BarChart<?, ?> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series = new XYChart.Series<>();
        Map<String, Integer> map = managerArticle.statArticleByDay();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        series.setName("Dates");
        barChart.getData().addAll(series);
    }
}
