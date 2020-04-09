package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticleByCategoryController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData(getSeries(ManagerArticle.statArticlesByCategories()));
    }

    /**
     * add the data for graphic
     * @param series
     */
    private void setData(XYChart.Series<String, Integer> series){
        series.setName("Categories");
        barChart.getData().addAll(series);
    }

    /**
     * filled in the data for the graphic
     * @param map
     * @return series
     */
    private XYChart.Series<String, Integer> getSeries(Map<String, Integer> map){
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        return series;
    }

}
