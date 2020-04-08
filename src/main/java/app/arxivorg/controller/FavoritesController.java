package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Favorites Controller
 */
public class FavoritesController extends Controller implements Initializable {

    @FXML
    public ListView listView;
    @FXML
    public Button deleteButton;
    @FXML
    public Button downloadButton;

    private int currentIndex = 1;
    private List<Article> favorites=new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * show all articles of favorite
     */
    public void displayArticles(){
        for(Article article: favorites){
            TextFlow flow = new TextFlow();
            flow.getChildren().addAll(ArxivOrgController.getStyleText(article));
            listView.getItems().add(flow);
        }
    }


    /**
     * Delete an article
     * @param actionEvent
     */
    @FXML
    public void deleteArticle(ActionEvent actionEvent) {
        Article article=getSelectedArticle();
        favorites.remove(article);
        User.removeArticle(article.getId());
        listView.getItems().clear();
        displayArticles();
        disableButton();
    }


    /**
     * Download an article
     * @param actionEvent
     */
    @FXML
    public void downloadArticle(ActionEvent actionEvent) {
        Article article = getSelectedArticle();
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        displayDownloadProgressBar(articles);
    }


    /**
     * changes the value of the current index
     * @param mouseEvent
     */
    @FXML
    public void changeCurrentIndex(MouseEvent mouseEvent) {
        currentIndex = listView.getSelectionModel().getSelectedIndex();
        activateButton();
    }


    /**
     *
     * @return article selected in List View
     */
    private Article getSelectedArticle() {
        Article article = favorites.get(currentIndex);
        return article;
    }


    /**
     * activates the buttons
     */
    private void activateButton(){
        if(deleteButton.isDisable() && downloadButton.isDisable()){
            deleteButton.setDisable(false);
            downloadButton.setDisable(false);
        }
    }


    /**
     * disables the buttons
     */
    private void disableButton(){
        if(favorites.isEmpty()){
            deleteButton.setDisable(true);
            downloadButton.setDisable(true);
        }
    }

    public void setFavorites(List<Article> favorites) {
        this.favorites = favorites;
    }

}
