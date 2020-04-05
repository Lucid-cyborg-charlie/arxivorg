package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FavoritesController implements Initializable {

    @FXML
    public ListView listView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * show all articles of favorite
     */
    public void displayArticles(List<Article> articles){
        for(Article article: articles){
            listView.getItems().add("Titre : "+article.getTitle()
                    +"\nAuteurs : "+article.getAuthors().toString()
                    +"\nID: "+article.getId()
                    +"\nCategory(ies) : "+article.getCategories().toString());
        }
    }
}
