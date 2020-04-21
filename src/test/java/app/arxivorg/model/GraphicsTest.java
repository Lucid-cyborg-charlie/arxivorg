package app.arxivorg.model;

import app.arxivorg.ArxivOrg;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javax.swing.text.TableView;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)

public class GraphicsTest {
    @Start
    public void start(@NotNull Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(ArxivOrg.class.getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene((new Scene(mainNode)));
        stage.show();
    }

    @Test
    void testDeleteButton(FxRobot robot) {
        ListView<TextFlow> listView = robot.lookup("#oneDownloadButton").queryListView();
        robot.clickOn("telecharger");
        assertEquals(6, listView.getItems().size());
        robot.clickOn("#listvieuw");
        robot.moveTo(robot.point(50, 100));
        assertEquals(2, listView.getItems().size());

    }

    @Test
    void testNumber0fArticlesFilter(FxRobot robot) {
        ListView<TextFlow> listView = robot.lookup("#oneDownloadButton").queryListView();
        robot.clickOn("Categories");
        robot.clickOn("cs.AI");
        robot.write("2");
        assertEquals(2, listView.getItems().size());
    }


}
