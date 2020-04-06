package app.arxivorg.model;

import javafx.scene.control.DatePicker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerArticleTest {
    ManagerArticle managerarticle = new ManagerArticle();

    @Test
    void readFileAtom() {
    }

    @Test
    void readDataFromArchive() {
    }

    @Test
    void getArticlesById() {
    }

    @Test
    void getArticlesByCategory() throws IOException {
        List<Article> articles = managerarticle.getArticlesByCategory("cs.AI");
        boolean is0k= true;
        int count=articles.size();
        for (Article article : articles ) {
        if (!article.getCategories().contains("cs.AI")) {
            is0k = false;
        }
    }
    assertEquals(true,is0k);
}





    @Test
    void getArticlesByPeriod() {
        DatePicker datepiker = new DatePicker();
        datepiker.setValue(LocalDate.of(2020,04,02));
     /*   LocalDate localdate = datepiker.getValue();
        Date date = Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Article> articles = managerarticle.getArticlesByPeriod(datepiker);
        boolean is0k= true;
        for (Article article : articles ) {
            if (!article.getPublished().equals(date)) {
                is0k = false;
            }
        }
        assertEquals(true,is0k);
        */
      System.out.println(datepiker.getValue());
    }



    @Test
    void getArticlesByKeyWord() {
        List<Article> articles = managerarticle.getArticleByKeyWord("artificial");
        boolean is0k= true;
        for (Article article : articles ) {
            if (!article.getSummary().contains("artificial")) {
                is0k = false;
                return ;
            }
        }
        assertEquals(true,is0k);
    }



    @Test
    void getArticlesByAuthor() throws IOException {
        List<Article> articles = managerarticle.getArticlesByCategory("Jose Font");
        boolean is0k= true;
        int count=articles.size();
        for (Article article : articles ) {
        if (!article.getAuthors().contains("Jose Font")) {
        is0k = false;
        }
        }
        assertEquals(true,is0k);
        }

        }

