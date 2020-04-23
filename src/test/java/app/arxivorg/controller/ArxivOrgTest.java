package app.arxivorg.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(ApplicationExtension.class)
class ArxivOrgTest {

    @Start
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setTitle("arxivorg");
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/app/arxivorg/css/app.css").toString());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Test
    void testDisabledAndEnabledOfDownloadButton(FxRobot robot) {
        Button button = robot.lookup("#oneDownloadButton").queryButton();
        Assertions.assertThat(button).hasText("Télécharger");
        Assertions.assertThat(button).isDisabled();

        robot.clickOn((CheckBoxListCell)robot.lookup(".list-cell").nth(1).query()).sleep(2000);
        Assertions.assertThat(button).isEnabled();
    }

    @Test
    void testFavoriteCheckBox(FxRobot robot){
        CheckBox checkBox = robot.lookup("#favoriteCheckBox").query();
        Assertions.assertThat(checkBox).hasText("Favoris");
        Assertions.assertThat(checkBox).isDisabled();

        robot.clickOn((CheckBoxListCell)robot.lookup(".list-cell").nth(1).query()).sleep(2000);
        Assertions.assertThat(checkBox).isEnabled();
        robot.clickOn("#favoriteCheckBox");
        assertEquals(true, checkBox.isSelected());
    }

    @Test
    void testStartDownload(FxRobot robot){
        robot.clickOn((CheckBoxListCell)robot.lookup(".list-cell").nth(1).query()).sleep(2000);
        robot.clickOn("#oneDownloadButton").sleep(20000);
    }

    @Test
    void testDisplayAnArticleInTextArea(FxRobot robot){
        robot.clickOn((CheckBoxListCell)robot.lookup(".list-cell").nth(1).query()).sleep(2000);
        TextFlow textFlow =  robot.lookup("#infosText").queryTextFlow();
        ObservableList<Node> list = textFlow.getChildren();
        assertNotNull(list);
    }


    @Test
    void testSelectedCategory(FxRobot robot){
        StackPane stackPane = robot.lookup("#stackPane").query();
        Node progressIndicator = stackPane.getChildren().get(1);
        Assertions.assertThat(progressIndicator).isInvisible();

        robot.clickOn("#categoryComboBox").type(KeyCode.DOWN).push(KeyCode.ENTER);
        Assertions.assertThat(progressIndicator).isVisible();
    }

    @Test
    void testInputFieldPeriodDatePicker(FxRobot robot){
        StackPane stackPane = robot.lookup("#stackPane").query();
        Node progressIndicator = stackPane.getChildren().get(1);
        Assertions.assertThat(progressIndicator).isInvisible();

        robot.clickOn("#periodDatePicker").write("13/04/2020").push(KeyCode.ENTER);
        Assertions.assertThat(progressIndicator).isVisible();
    }

    @Test
    void testInputFieldAuthor(FxRobot robot){
        StackPane stackPane = robot.lookup("#stackPane").query();
        Node progressIndicator = stackPane.getChildren().get(1);
        Assertions.assertThat(progressIndicator).isInvisible();

        robot.clickOn("#authorField").write("Eduardo").push(KeyCode.ENTER);
        Assertions.assertThat(progressIndicator).isVisible();
    }

    @Test
    void testInputFieldKeyWord(FxRobot robot){
        StackPane stackPane = robot.lookup("#stackPane").query();
        Node progressIndicator = stackPane.getChildren().get(1);
        Assertions.assertThat(progressIndicator).isInvisible();

        robot.clickOn("#keyWordField").write("machine").push(KeyCode.ENTER);
        Assertions.assertThat(progressIndicator).isVisible();
    }

    @Test
    void testPreferencesButton(FxRobot robot){
        robot.clickOn("#preferenceButton").clickOn("#item1").sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#item2").sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#item3").sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#item4").write("machine").
                push(KeyCode.ENTER).sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#exitMenu");
    }

    @Test
    void testAll(FxRobot robot){
        robot.clickOn((CheckBoxListCell)robot.lookup(".list-cell").nth(1).query()).sleep(2000);

        robot.clickOn("#favoriteCheckBox").sleep(2000);
        robot.clickOn("#oneDownloadButton").sleep(14000);
        robot.clickOn("#categoryComboBox").type(KeyCode.DOWN).push(KeyCode.ENTER).sleep(5000);
        robot.clickOn("#periodDatePicker").write("13/04/2020").push(KeyCode.ENTER).sleep(5000);
        robot.clickOn("#authorField").write("Edward Lockhart").push(KeyCode.ENTER).sleep(8000);
        robot.clickOn("#keyWordField").write("Quantum Monte").push(KeyCode.ENTER).sleep(8000);

        robot.clickOn("#preferenceButton").clickOn("#item1").sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#item2").sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#item3").sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#item4").write("machine, physic, mathematic, Quantum Monte").
                push(KeyCode.ENTER).sleep(2000);
        robot.clickOn("#preferenceButton").clickOn("#exitMenu");
    }


}