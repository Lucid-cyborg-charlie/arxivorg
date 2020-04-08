package app.arxivorg.controller;

import javafx.scene.chart.XYChart;

import java.util.Map;

public abstract class StatisticController {

    public XYChart.Series<String, Integer> getSeries(Map<String, Integer> map){
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        return series;
    }
}
