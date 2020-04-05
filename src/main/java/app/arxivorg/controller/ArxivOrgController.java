package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 *  ArxivOrg Controller
 */
public class ArxivOrgController implements Initializable {

    @FXML
    private MenuButton preferenceButton;
    @FXML
    public Button oneDownloadButton;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextArea infosTextArea;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private DatePicker periodDatePicker;
    @FXML
    private TextField authorField;
    @FXML
    private TextField keyWordField;
    @FXML
    private CheckBox favoriteCheckBox;


    private  ManagerArticle managerArticle = new ManagerArticle();
    private List<Article> articles = new ArrayList<>(managerArticle.getArticles());
    private List<Article> favorites=User.getArticlesByID();
    private int currentIndexSelectInListView = -1;


    // @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayArticles(managerArticle.getArticles());
        displayCategories();
    }


    /**
     * show all articles
     * @param articles
     */
    private void displayArticles(List<Article> articles){
        for(Article article: articles){
            listView.getItems().add("Titre : "+article.getTitle()
                    +"\nAuteurs : "+article.getAuthors().toString()
                    +"\nID: "+article.getId()
                    +"\nCategory(ies) : "+article.getCategories().toString());
        }
    }

    /**
     * show all categories
     */
    private void displayCategories(){
        categoryComboBox.getItems().addAll(managerArticle.getCategories());
    }


    /**
     * display selected article on mouseEvent
     * @param mouseEvent
     */
    @FXML
    public void displaySelectedArticle(MouseEvent mouseEvent) {
        activateButtons();
        resetCkeckBox();
        try {
            currentIndexSelectInListView = listView.getSelectionModel().getSelectedIndex();
        }catch (Exception e){
            e.printStackTrace();
        }
        Article article = managerArticle.getArticles().get(currentIndexSelectInListView);
        infosTextArea.setText("Title : "+article.getTitle()
                + "\nAuteurs : "+article.getAuthors().toString()
                + "\nCategory(ies) : "+article.getCategories().toString()
                + "\nDate : "+article.getPublished().toString()
                + "\nDescription :\n"+article.getSummary()
                + "\nLien: "+article.getId());
    }

    private void resetCkeckBox(){
        if(favoriteCheckBox.isSelected()){
            favoriteCheckBox.setSelected(false);
        }
    }

    private void activateButtons(){
        if(oneDownloadButton.isDisable() && favoriteCheckBox.isDisable()){
            oneDownloadButton.setDisable(false);
            favoriteCheckBox.setDisable(false);
        }
    }

    /**
     * display articles by category selected
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByCategory(ActionEvent actionEvent) {
       managerArticle.setArticles(managerArticle.getArticlesByCategory(categoryComboBox.getSelectionModel().getSelectedItem()));
       listView.getItems().clear();
       displayArticles(managerArticle.getArticles());
    }



    /**
     * display articles by author
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByAuthor(ActionEvent actionEvent) {
        managerArticle.setArticles(managerArticle.getArticlesByAuthor(authorField.getText()));
        listView.getItems().clear();
        displayArticles(managerArticle.getArticles());
    }


    /**
     * display articles by period selected
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByPeriod(ActionEvent actionEvent) {
        managerArticle.setArticles(managerArticle.getArticlesByPeriod(periodDatePicker));
        listView.getItems().clear();
        displayArticles(managerArticle.getArticles());
    }


    /**
     * display articles by keyWord
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByKeyWord(ActionEvent actionEvent) {
        managerArticle.setArticles(managerArticle.getArticleByKeyWord(keyWordField.getCharacters().toString()));
        listView.getItems().clear();
        displayArticles(managerArticle.getArticles());
    }


    /**
     * add an article in user favorite list
     * @param actionEvent
     */
    @FXML
    public void AddFavoriteArticle(ActionEvent actionEvent) {
        if(favoriteCheckBox.isSelected()){
            User.saveArticle(getSelectedArticle().getId());
        }else{
            User.removeArticle(getSelectedArticle().getId());
        }
    }

    /**
     *
     * @return article selected in List View
     */
    private Article getSelectedArticle() {
        int index = listView.getSelectionModel().getSelectedIndex();
        Article article = managerArticle.getArticles().get(index);
        return article;
    }

    /**
     * download an article
     * @param actionEvent
     */
    @FXML
    public void downloadOneArticle(ActionEvent actionEvent){
        Article article = managerArticle.getArticles().get(currentIndexSelectInListView);
        List<Article> articles = new ArrayList<Article>();
        articles.add(article);
        displayDownloadProgressBar(articles);
    }

    /**
     * Download several articles
     * @param actionEvent
     */
    @FXML
    public void downloadSeveralArticles(ActionEvent actionEvent) {
        displayDownloadProgressBar(articles);
    }


    /**
     * Displays the download progress bar
     * @param articles
     */
    private void displayDownloadProgressBar(List<Article> articles){
        FXMLLoader loader = makeWindows("/app/arxivorg/view/progress-bar.fxml", "Téléchargement");
        ProgressBarController progressBarController = loader.getController();
        progressBarController.startProgress(articles);
    }

    /**
     * Displays favorites interface
     * @param actionEvent
     */
    @FXML
    public void displayFavorites(ActionEvent actionEvent){
       FXMLLoader loader = makeWindows("/app/arxivorg/view/favorites.fxml", "Mes Favoris");
       FavoritesController favoritesController = loader.getController();
       favoritesController.displayArticles(favorites);
    }

    /**
     * Make windows
     * @param resources
     * @param title
     * @return FXMLLoader
     */
    private FXMLLoader makeWindows(String resources, String title){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resources));
        AnchorPane panel = null;
        try {
            //build stage of favorites
            panel = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(panel));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    /**
     * Displays statistics
     * @param actionEvent
     */
    @FXML
    public void displayStatistics(ActionEvent actionEvent){

    }

    /**
     * Close windows
     * @param actionEvent
     */
    @FXML
    public void closeWindows(ActionEvent actionEvent){
        Stage stage = (Stage) preferenceButton.getScene().getWindow();
        stage.close();
    }

}
