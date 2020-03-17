package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.*;


public class ArxivOrgController implements Initializable {

    @FXML
    private ListView<String> listView;
    @FXML
    private TextArea infosTextArea;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private ComboBox<String> periodComboBox;
    @FXML
    private TextField authorField;
    @FXML
    private TextField keyWordField;
    @FXML
    private CheckBox favorisCheckBox;

    private  ManagerArticle managerArticle = new ManagerArticle();

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
        for(Categorie var : managerArticle.getCategories()){
            categories.add(var.getName());
        }
        categorieComboBox.getItems().addAll(categories);
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
        int index=listView.getSelectionModel().getSelectedIndex();
        Article article= managerArticle.getArticles().get(index);
        infosTextArea.setText("Title: "+article.getTitle()+"\nAuteurs: "+article.getArticleAuthors()
        +"\nDescription: \n"+article.getSummary()+"\nLien: "+article.getId());
    }

    /**
     * display article by category selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByCategory(ActionEvent actionEvent) {
        int index = categorieComboBox.getSelectionModel().getSelectedIndex();
        List<Categorie> tmp = new ArrayList<>(managerArticle.getCategories());
        Categorie categorie = tmp.get(index);

        List<String> values = new ArrayList<>();
        String str = "";
        for(Article article : managerArticle.getArticlesByCategory(categorie)){
            str = str + "Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId();
            values.add(str);
        }listView.getItems().setAll(values);
    }


    /**
     * display article by period selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByPeriod(ActionEvent actionEvent) {
        int index = periodComboBox.getSelectionModel().getSelectedIndex();
        List<String> tmp = new ArrayList<>(managerArticle.getPeriods());
        String date = tmp.get(index);
        String str = "";
        List<String> values = new ArrayList<>();

        for(Article article : managerArticle.getArticlesByPeriod(date)){
            str = str + "Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId();
            values.add(str);
        }listView.getItems().setAll(values);
    }


    /**
     * display article by author selected
     * @param actionEvent
     */
    @FXML
    public void displaySelectedByAuthors(ActionEvent actionEvent) {
        List<Article> articleList = managerArticle.getArticlesByAuthor(new Author(authorField.getCharacters().toString()));
        String str = "";
        List<String> values = new ArrayList<>();

        for(Article article : articleList){
            str = str + "Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId();
            values.add(str);
        }listView.getItems().setAll(values);
    }


    /**
     * @return keywords suggestion
     */
    public Set<String> keyWord(){
        Set<String> words = new HashSet<>();
        for(Article article : managerArticle.getArticles()){
            words.addAll(Arrays.asList(article.getTitle().split(" ")));
            //words.addAll(Arrays.asList(article.getSummary().split(" ")));
        }
        return words;
    }


    /**
     * display articles by keyWord
     * @param actionEvent
     */
    @FXML
    public void findKeyWord(ActionEvent actionEvent) {
        List<Article> articleList = managerArticle.getArticleByKeyWord(keyWordField.getCharacters().toString());
        String str = "";
        List<String> values = new ArrayList<>();

        for(Article article : articleList){
            str = str + "Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId();
            values.add(str);
        }listView.getItems().setAll(values);
    }

    /**
     * add an article in user favorite list
     * @param actionEvent
     */
    @FXML
    public void AddFavoriteArticle(ActionEvent actionEvent) {
        if(favorisCheckBox.isSelected()){
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
    public void downloadArticle(ActionEvent actionEvent) {

    }
}
