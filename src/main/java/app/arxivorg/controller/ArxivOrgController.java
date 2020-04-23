package app.arxivorg.controller;

import app.arxivorg.model.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.net.URL;
import java.util.*;


public class ArxivOrgController extends MakeWindows implements Initializable {

    @FXML
    public TextFlow infosText;
    @FXML
    public Button severalDownloadButton;
    @FXML
    private MenuButton preferenceButton;
    @FXML
    public Button oneDownloadButton;
    @FXML
    private ListView<Article> listView;
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
    @FXML
    private StackPane stackPane;

    final ProgressIndicator loadingIndicator = new ProgressIndicator();
    private  ManagerArticle managerArticle = new ManagerArticle();
    private List<Article> favorites;
    private List<Article> articlesSelected = new ArrayList<>();
    private int currentIndex = -1;
    private int count=0;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayArticles(managerArticle.getArticles());
        displayCategories();
        favorites = User.getArticlesByID();
        stackPane.getChildren().add(loadingIndicator);
        loadingIndicator.setVisible(false);
    }


    /**
     * show all articles
     * @param articles
     */
    private void displayArticles(List<Article> articles){
        for(Article article: articles){
            listView.getItems().add(article);
        }
        listView.setCellFactory(CheckBoxListCell.forListView(new Callback<Article, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Article item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) ->{
                    if(isNowSelected) {
                        articlesSelected.add(item);
                        count++;
                        updateSeveralDownloadsButton();
                    }
                    if(!isNowSelected){
                        articlesSelected.remove(item);
                        count--;
                        updateSeveralDownloadsButton();
                    }
                });
                return observable;
            }
        }));
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
        Text authors=new Text(article.getAuthors().toString());
        authors.setStyle("-fx-fill: #1665c1;");
        Text id=new Text("\nID: "+article.getId());
        id.setStyle("-fx-fill: #1665c1;");
        Text date=new Text("\nPublié le "+Article.formatOfDate(article.getPublished()));
        texts.add(titre); texts.add(labelAuthors); texts.add(authors); texts.add(id);
        texts.add(date);
        return texts;

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
     * Update the button of download several articles
     */
    private void updateSeveralDownloadsButton(){
        if(count==0) severalDownloadButton.setDisable(true);
        else severalDownloadButton.setDisable(false);
    }


    /**
     * display articles by category selected
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByCategory(ActionEvent actionEvent) {
        listView.getItems().clear();
        loadingIndicator.setVisible(true);
        // loads the items at another thread, asynchronously
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // just emulates some loading time
                    managerArticle.setArticles(managerArticle.getArticlesByCategory
                            (categoryComboBox.getSelectionModel().getSelectedItem()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // just updates the list view items at the
                    // Application Thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayArticles(managerArticle.getArticles());
                            loadingIndicator.setVisible(false); // stop displaying the loading indicator
                        }
                    });
                }
            }
        }).start();
    }


    /**
     * display articles by author
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByAuthor(ActionEvent actionEvent) {
        listView.getItems().clear();
        loadingIndicator.setVisible(true);
        // loads the items at another thread, asynchronously
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // just emulates some loading time
                    managerArticle.setArticles(managerArticle.getArticlesByAuthor(authorField.getText()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // just updates the list view items at the
                    // Application Thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayArticles(managerArticle.getArticles());
                            loadingIndicator.setVisible(false); // stop displaying the loading indicator
                        }
                    });
                }
            }
        }).start();
    }


    /**
     * display articles by period selected
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByPeriod(ActionEvent actionEvent) {
        listView.getItems().clear();
        loadingIndicator.setVisible(true);
        // loads the items at another thread, asynchronously
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // just emulates some loading time
                    managerArticle.setArticles(managerArticle.getArticlesByPeriod(
                            periodDatePicker.getValue()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // just updates the list view items at the
                    // Application Thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayArticles(managerArticle.getArticles());
                            loadingIndicator.setVisible(false); // stop displaying the loading indicator
                        }
                    });
                }
            }
        }).start();
    }


    /**
     * display articles by keyWord
     * @param actionEvent
     */
    @FXML
    public void displayArticlesByKeyWord(ActionEvent actionEvent) {
        listView.getItems().clear();
        loadingIndicator.setVisible(true);
        // loads the items at another thread, asynchronously
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // just emulates some loading time
                    managerArticle.setArticles(managerArticle.getArticleByKeyWord(
                            keyWordField.getCharacters().toString()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // just updates the list view items at the
                    // Application Thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayArticles(managerArticle.getArticles());
                            loadingIndicator.setVisible(false); // stop displaying the loading indicator
                        }
                    });
                }
            }
        }).start();
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
        displayDownloadProgressBar(articlesSelected);
    }


    /**
     * Displays favorites interface
     * @param actionEvent
     */
    @FXML
    public void displayFavorites(ActionEvent actionEvent){
       FXMLLoader loader = makeWindows("/app/arxivorg/view/favorites.fxml", "Mes Favories",
               getClass().getResource("/app/arxivorg/css/favoris.css").toString());
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
        makeWindows("/app/arxivorg/view/articleByCategory.fxml", "statistique",null);
    }

    @FXML
    public void statArticleByDay(ActionEvent actionEvent) {
        makeWindows("/app/arxivorg/view/articleByDay.fxml", "statistique", null);
    }

    @FXML
    public void statArticleByAuthor(ActionEvent actionEvent) {
        makeWindows("/app/arxivorg/view/articleByAuthor.fxml", "statistique",null);
    }


    public void statArticleByExpression(ActionEvent actionEvent) {
        makeWindows("/app/arxivorg/view/articleByExpression.fxml", "statistique", null);
    }
}
