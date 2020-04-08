package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *  ArxivOrg Controller
 */
public class ArxivOrgController extends Controller implements Initializable {

    @FXML
    public TextFlow infosText;
    @FXML
    private MenuButton preferenceButton;
    @FXML
    public Button oneDownloadButton;
    @FXML
    private ListView<TextFlow> listView;
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
            TextFlow flow = new TextFlow();
            flow.getChildren().addAll(getStyleText(article));
            listView.getItems().add(flow);
        }
    }


    /**
     * style the text
     * @param article
     * @return
     */
    public static List<Text> getStyleText(Article article){
        List<Text> texts = new ArrayList<Text>();
        Text titre=new Text("Titre: "+article.getTitle()+"\n");
        titre.setStyle("-fx-font-weight: bold");
        Text labelAuthors=new Text("Auteurs: ");
        labelAuthors.setStyle("-fx-fill: #138925; ");
        Text authors=new Text(reduceNumberOfAuthors(article.getAuthors()));
        authors.setStyle("-fx-fill: #1665c1;");
        Text id=new Text("\nID: "+article.getId());
        id.setStyle("-fx-fill: #1665c1;");
        Text date=new Text("\nPublié le "+formatOfDate(article.getPublished()));
        texts.add(titre); texts.add(labelAuthors); texts.add(authors); texts.add(id);
        texts.add(date);
        return texts;

    }


    /**
     * return the correct date format
     * @param date
     * @return
     */
    private static String formatOfDate(Date date){
        String format = "dd/MM/yyyy H:mm:ss";
        SimpleDateFormat formatting = new SimpleDateFormat(format);
        try {
            String res = formatting.format(date);
            return res;
        }catch (Exception e){
            return "";
        }
    }


    /**
     * reduce the number of authors
     * @param authors
     * @return
     */
    private  static String reduceNumberOfAuthors(List<String> authors){
        List<String> reduction = new ArrayList<>();
        int count=0;
        for(String author: authors){
            count++;
            if(count <= 6){
                reduction.add(author);
            }else {
                break;
            }
        }
        return reduction.toString();
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
        Article article = getSelectedArticle();
        if(article != null){
            activateButtons();
            resetCkeckBox();
            List<Text> texts = getStyleText(article);
            Text description=new Text("\n\n"+article.getSummary());
            Text categories=new Text("\n"+article.getCategories().toString());
            infosText.getChildren().clear();
            infosText.getChildren().addAll(texts.get(0), texts.get(1), texts.get(2),
                    description, categories, texts.get(3), texts.get(4));
        }
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
            return null;
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
       FXMLLoader loader = makeWindows("/app/arxivorg/view/favorites.fxml", "Mes Favories");
       FavoritesController favoritesController = loader.getController();
       favoritesController.setFavorites(favorites);
       favoritesController.displayArticles();
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

    @FXML
    public void statArticleByCategory(ActionEvent actionEvent) {
        makeWindows("/app/arxivorg/view/articleByCategory.fxml", "statistique");
    }

    @FXML
    public void statArticleByDay(ActionEvent actionEvent) {
        makeWindows("/app/arxivorg/view/articleByDay.fxml", "statistique");
    }

    @FXML
    public void statArticleByAuthor(ActionEvent actionEvent) {
        makeWindows("/app/arxivorg/view/articleByAuthor.fxml", "statistique");
    }

    @FXML
    public void displayStatistics(ActionEvent actionEvent) {
    }
}
