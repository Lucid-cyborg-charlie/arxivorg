package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;
import org.kordamp.ikonli.javafx.FontIcon;


/**
 *  ArxivOrg Controller
 */
public class ArxivOrgController extends Controller implements Initializable {

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
    private List<Article> favorites;
    private int currentIndex = -1;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayArticles(managerArticle.getArticles());
        displayCategories();
        favorites=User.getArticlesByID();
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
        Article article = getSelectedArticle();
        infosTextArea.setText("Title : "+article.getTitle()
                + "\nAuteurs : "+article.getAuthors().toString()
                + "\nCategorie(s) : "+article.getCategories().toString()
                + "\nDate : "+article.getPublished().toString()
                + "\nDescription :\n"+article.getSummary()
                + "\nLien: "+article.getId());
    }


    /**
     *
     * @return article selected in List View
     */
    private Article getSelectedArticle() {
        currentIndex = listView.getSelectionModel().getSelectedIndex();
        try{
            Article article = managerArticle.getArticles().get(currentIndex);
            return article;
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return new Article();//empty article
        }
    }


    /**
     * Reset checkBox
     */
    private void resetCkeckBox(){
        if(favoriteCheckBox.isSelected()){
            favoriteCheckBox.setSelected(false);
        }
    }


    /**
     * Activates the buttons
     */
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
            Article article=getSelectedArticle();
            User.saveArticle(article.getId());
            favorites.add(article);
        }else{
            Article article=getSelectedArticle();
            User.removeArticle(article.getId());
            favorites.remove(article);
        }
    }


    /**
     * download an article
     * @param actionEvent
     */
    @FXML
    public void downloadOneArticle(ActionEvent actionEvent){
        Article article = getSelectedArticle();
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        displayDownloadProgressBar(articles);
    }


    /**
     * Download several articles
     * @param actionEvent
     */
    @FXML
    public void downloadSeveralArticles(ActionEvent actionEvent) {
        displayDownloadProgressBar(managerArticle.getArticles());
    }


    /**
     * Displays favorites interface
     * @param actionEvent
     */
    @FXML
    public void displayFavorites(ActionEvent actionEvent){
       FXMLLoader loader = makeWindows("/app/arxivorg/view/favorites.fxml", "Mes Favoris");
       FavoritesController favoritesController = loader.getController();
       favoritesController.setFavorites(favorites);
       favoritesController.displayArticles();
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
