package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.ManagerArticle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.ResourceBundle;


public class ProgressBarController implements Initializable {

    @FXML
    public Label labelFullDownload;
    @FXML
    public Label pathLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;

    private String path;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        path =  FileSystems.getDefault().getPath(System.getProperty("user.home"), "/Documents/", "arxivorg").toString();
        pathLabel.setText("Dossier: "+path);
    }

    /**
     * Starts the download progress bar
     * @param articles
     */
    public void startProgress(List<Article> articles){
        if(articles.size()>1) {
            labelFullDownload.setText("Fichiers téléchargés avec succès");
        }
        Task<Void> task=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ManagerArticle.downloadSeveralArticlesToPDF(articles);
                for(int i=0; i<=100; i++){
                    updateProgress(i, 100);
                    updateMessage(""+i+"%");
                    Thread.sleep(30);
                }
                labelFullDownload.setVisible(true);
                pathLabel.setVisible(true);
                return null;
            }
        };

        task.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                progressLabel.setText(t1);
            }
        });

        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

}
