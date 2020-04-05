package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


public class ArxivOrgController implements Initializable {

    @FXML
    public Button oneDownloadButton;
    @FXML
    public BorderPane rootPane;
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
     * display article selected on mouseEvent
     * @param mouseEvent
     */
    @FXML
    public void displaySelected(MouseEvent mouseEvent) {
        oneDownloadButton.setDisable(false);
        currentIndexSelectInListView = listView.getSelectionModel().getSelectedIndex();
        Article article = managerArticle.getArticles().get(currentIndexSelectInListView);
        infosTextArea.setText("Title : "+article.getTitle()
                + "\nAuteurs : "+article.getAuthors().toString()
                + "\nCategory(ies) : "+article.getCategories().toString()
                + "\nDate : "+article.getPublished().toString()
                + "\nDescription :\n"+article.getSummary()
                + "\nLien: "+article.getId());
    }


    /**
     * display article by category selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByCategory(ActionEvent actionEvent) {
       managerArticle.setArticles(managerArticle.getArticlesByCategory(categoryComboBox.getSelectionModel().getSelectedItem()));
       listView.getItems().clear();
       displayArticles(managerArticle.getArticles());
    }



    /**
     * display article by author
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByAuthors(ActionEvent actionEvent) {
        managerArticle.setArticles(managerArticle.getArticlesByAuthor(authorField.getText()));
        listView.getItems().clear();
        displayArticles(managerArticle.getArticles());
    }


    /**
     * display article by period selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByPeriod(ActionEvent actionEvent) {
        managerArticle.setArticles(managerArticle.getArticlesByPeriod(periodDatePicker));
        listView.getItems().clear();
        displayArticles(managerArticle.getArticles());
    }


    /**
     * display articles by keyWord
     * @param actionEvent
     */
    @FXML
    public void findKeyWord(ActionEvent actionEvent) {
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
            int index = listView.getSelectionModel().getSelectedIndex();
            Article article = managerArticle.getArticles().get(index);
            User user = new User();
            user.saveArticle(article);
        }
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/arxivorg/view/progress-bar.fxml"));
        AnchorPane panel = null;
        try {
            //build stage of progressBar
            panel = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Téléchargement");
            stage.setScene(new Scene(panel));
            stage.setResizable(false);

            //get progressBar controller
            ProgressBarController progressBarController = loader.getController();
            progressBarController.startProgress(articles);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
