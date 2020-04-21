package app.arxivorg.model;
import app.arxivorg.ArxivOrg;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@ExtendWith((ApplicationExtension.class))

public class GraphicsTest { /* chargement du fichier fxml*/
    @Start
    public void start(@NotNull Stage stage)throws Exception{
        Parent mainNode= FXMLLoader.load(ArxivOrg.class.getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene((new Scene(mainNode)));
        stage.show();
    }
    @Test
    void testButtonText(FxRobot robot){
        Button button= robot.lookup("#oneDownloadButton").queryButton();
        Assertions.assertThat(button).hasText("download");
    }
    @Test
    void TestMenuButtonChanges(FxRobot robot){
        MenuButton menuButton=robot.lookup(" #periodDatePicker").queryAs(MenuButton.class);
        Assertions.assertThat(menuButton).hasText("choisir une période");
        robot.clickOn( "#periodDatePicker");
        Assertions.assertThat(menuButton).hasText("avant hier");



    }
     @Test
     void testinitialNumberOfArticles(FxRobot robot){
         ListView listView=robot.lookup("#listView").queryListView();
         assertNotNull(listView);
         assertEquals(0,listView.getItems().size());
     }
     @Test
     void testNumberOfArticlesFiltered(FxRobot robot){
        ListView listView = robot.lookup("#listview").queryListView();
        assertNotNull(listView);
        robot.clickOn("#categoryComboBox");
        robot.clickOn("MATH_ID");
        assertEquals(10,listView.getItems().size());
     }



    }
