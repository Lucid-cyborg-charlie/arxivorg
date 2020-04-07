package app.arxivorg.controller;

import app.arxivorg.model.Article;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;


/**
 * Abstract class Controller
 */
public abstract class Controller {


    /**
     * Displays the download progress bar
     * @param articles
     */
    public void displayDownloadProgressBar(List<Article> articles){
        FXMLLoader loader = makeWindows("/app/arxivorg/view/progress-bar.fxml", "Téléchargement");
        ProgressBarController progressBarController = loader.getController();
        progressBarController.startProgress(articles);
    }


    /**
     * Make windows
     * @param resources
     * @param title
     * @return FXMLLoader
     */
    public FXMLLoader makeWindows(String resources, String title){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resources));
        AnchorPane panel = null;
        try {
            //build stage of favorites
            panel = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(panel);
            scene.getStylesheets().add(getClass().getResource("/app/arxivorg/css/favoris.css").toString());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}
