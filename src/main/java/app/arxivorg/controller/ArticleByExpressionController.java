package app.arxivorg.controller;

import app.arxivorg.model.ManagerArticle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticleByExpressionController extends Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private javafx.scene.control.TextField TextField;
    private ManagerArticle managerArticle ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerArticle = new ManagerArticle();
    }



    @FXML
    public void getExpression(ActionEvent actionEvent) {
        Map<String, Integer> map = managerArticle.statArticleByExpression(TextField.getText());
        if(map.size() > 0) displayStat(map);
        else TextField.setText("AUCUN RESULTAT");
    }


    public void displayStat(Map<String, Integer> map){
        FXMLLoader fxmlLoader = makeWindows("/app/arxivorg/view/articleByExpressionResult.fxml", "statistique");
        ArticleByExpressionResult controller = fxmlLoader.getController();
        controller.displayStat(map);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void CloseWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
