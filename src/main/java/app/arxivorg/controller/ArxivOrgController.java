package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.ManagerArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML
    private ListView<String> listView;
    @FXML
    private TextArea infosTextArea;

    private  ManagerArticle ma=new ManagerArticle();

    // @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        showAllArticle(ma.getArticles());
    }

    /**
     * show articles list
     * @param articles
     */
    private void showAllArticle(List<Article> articles){
        for(Article article: articles){
            listView.getItems().add("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }
    }

    /**
     * display selected on mouseEvent
     * @param mouseEvent
     */
    @FXML
    public void displaySelected(MouseEvent mouseEvent) {
        int index=listView.getSelectionModel().getSelectedIndex();
        Article article=ma.getArticles().get(index);
        infosTextArea.setText("Title: "+article.getTitle()+"\nAuteurs: "+article.getArticleAuthors()
        +"\nDescription: \n"+article.getSummary()+"\nLien: "+article.getId());
    }
}
