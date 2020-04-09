package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticleByExpressionResult implements Initializable {
    @FXML
    public BarChart<String, Integer> barChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> series= getSeries(ManagerArticle.map);
        series.setName("Expressions");
        barChart.getData().addAll(series);
    }

    /**
     * filled in the data for the graphic
     * @param map
     * @return series
     */
    public static XYChart.Series<String, Integer> getSeries(Map<String, Integer> map){
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        return series;
    }

}
