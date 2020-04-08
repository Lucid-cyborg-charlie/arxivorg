package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticleByExpressionResult extends StatisticController implements Initializable {
    public BarChart barChart;
    ArticleByExpressionController controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ArticleByExpressionController();
    }


    public void displayStat(Map<String, Integer> map){
        XYChart.Series<String, Integer> series = getSeries(map);
        series.setName("Expression");
        barChart.getData().addAll(series);
    }
}
