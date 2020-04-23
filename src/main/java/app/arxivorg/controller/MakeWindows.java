package app.arxivorg.controller;

import app.arxivorg.model.Article;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;


public abstract class MakeWindows {


    /**
     * Displays the download progress bar
     * @param articles
     */
    public void displayDownloadProgressBar(List<Article> articles){
        FXMLLoader loader = makeWindows("/app/arxivorg/view/progress-bar.fxml", "Téléchargement", null);
        ProgressBarController progressBarController = loader.getController();
        progressBarController.startProgress(articles);
    }


    /**
     * Make windows
     * @param fxml
     * @param title
     * @return FXMLLoader
     */
    public FXMLLoader makeWindows(String fxml, String title, String css){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        AnchorPane panel = null;
        try {
            //build stage of favorites
            panel = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(panel);
            if(css!=null){
                scene.getStylesheets().add(css);
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}
