package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticleByCategoryController extends StatisticController implements Initializable {

    private ManagerArticle managerArticle = new ManagerArticle();
    @FXML
    private BarChart<String, Integer> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> series = getSeries(managerArticle.statArticlesByCategories());
        series.setName("Categories");
        barChart.getData().addAll(series);
    }
}
