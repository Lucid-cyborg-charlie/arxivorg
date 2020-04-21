package app.arxivorg;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class ArxivOrgTest {

    @Start
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }

    @Test
    void testRobot (FxRobot robot) {
        robot.clickOn("#listView");
    }

    @Test
    void testButtonText(FxRobot robot){
        Button button = robot.lookup("#oneDownloadButton").queryButton();
        Assertions.assertThat(button).hasText("Télécharger");
    }
}