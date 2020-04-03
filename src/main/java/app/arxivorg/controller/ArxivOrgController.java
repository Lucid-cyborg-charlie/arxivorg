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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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
    private ComboBox<String> periodComboBox;
    @FXML
    private TextField authorField;
    @FXML
    private TextField keyWordField;
    @FXML
    private CheckBox favoriteCheckBox;


    private  ManagerArticle managerArticle = new ManagerArticle();
    private List<Article> articles = new ArrayList<>(managerArticle.getArticles());
    private int currentIndexSelectInListView=-1;

    public ArxivOrgController() throws IOException {
    }

    // @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        TextFields.bindAutoCompletion(keyWordField, keyWord());
        TextFields.bindAutoCompletion(authorField, managerArticle.getAuthors());
        showAllArticle(managerArticle.getArticles());
        showAllCategories();
        showAllPeriod();
    }

    /**
     * show all articles
     * @param articles
     */
    private void showAllArticle(List<Article> articles){
        for(Article article: articles){
            listView.getItems().add("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }
    }

    /**
     * show all categories
     */
    private void showAllCategories(){
        Set<String> categories = new HashSet<>();
        for(Category var : managerArticle.getCategories()){
            categories.add(var.getName());
        }
        categoryComboBox.getItems().addAll(categories);
    }


    /**
     * show all period
     */
    public void showAllPeriod(){
     this.periodComboBox.getItems().addAll(managerArticle.getPeriods());
    }

    /**
     * display article selected on mouseEvent
     * @param mouseEvent
     */
    @FXML
    public void displaySelected(MouseEvent mouseEvent) {
        oneDownloadButton.setDisable(false);
        currentIndexSelectInListView = listView.getSelectionModel().getSelectedIndex();
        Article article = getArticles().get(currentIndexSelectInListView);
        infosTextArea.setText("Title: "+article.getTitle()+"\nAuteurs: "+article.getArticleAuthors()
        +"\nDescription: \n"+article.getSummary()+"\nLien: "+article.getId());
    }


    /**
     * display article by category selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByCategory(ActionEvent actionEvent) {
        int index = categoryComboBox.getSelectionModel().getSelectedIndex();
        List<Category> tmp = new ArrayList<>(managerArticle.getCategories());
        Category category = tmp.get(index);
        this.setArticles(managerArticle.getArticlesByCategory(category));

        listView.getItems().clear();
        for(Article article: getArticles()){
            listView.getItems().add("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }
    }


    /**
     * display article by period selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByPeriod(ActionEvent actionEvent) throws ParseException {
        int index = periodComboBox.getSelectionModel().getSelectedIndex();
        this.setArticles(managerArticle.getArticlesByPeriod(managerArticle.getPeriods().get(index)));

        listView.getItems().clear();
        for(Article article: getArticles()){
            listView.getItems().add("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }
    }


    /**
     * display article by author selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByAuthors(ActionEvent actionEvent) {
        setArticles(managerArticle.getArticlesByAuthor(new Author(authorField.getCharacters().toString())));

        listView.getItems().clear();
        for(Article article: getArticles()){
            listView.getItems().add("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }
    }


    /**
     * @return keywords suggestion
     */
    public Set<String> keyWord(){
        Set<String> words = new HashSet<>();
        for(Article article : managerArticle.getArticles()){
            words.addAll(Arrays.asList(article.getTitle().split(" ")));
        }
        return words;
    }


    /**
     * display articles by keyWord
     * @param actionEvent
     */
    @FXML
    public void findKeyWord(ActionEvent actionEvent) {
        setArticles(managerArticle.getArticleByKeyWord(keyWordField.getCharacters().toString()));
        listView.getItems().clear();
        for(Article article: getArticles()){
            listView.getItems().add("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }

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
        Article article = getArticles().get(currentIndexSelectInListView);
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

    /** set articles list
     * @param articleList
     */
    public void setArticles(List<Article> articleList){
        this.articles = articleList;
    }


    public List<Article> getArticles(){
        return this.articles;
    }

}
