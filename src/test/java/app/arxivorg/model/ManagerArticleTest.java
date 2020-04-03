package app.arxivorg.model;

import javafx.concurrent.Service;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerArticleTest {

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
        ManagerArticle mangerarticletest = new ManagerArticle();
        Categorie cat = new Categorie("cs.AI");
        ArrayList<List> list = new ArrayList<>();
   /*    int count = 0;
        for (Article article : mangerarticletest.getArticlesByCategory(cat)) {
            count++;
            System.out.println("Titre" + count + ": " + article.getTitle());  */

            assertArrayEquals(list, mangerarticletest.getArticlesByCategory(cat));

        }

    }
    @Test
    void getArticlesByPeriod() throws IOException, ParseException {
        ManagerArticle mangerarticletest = new ManagerArticle();
        int count=0;
        for (Article article: mangerarticletest.getArticlesByPeriod("aujourd'hui")) {
            count++;
            System.out.println("Titre"+count+": " +article.getTitle());
        }

    }

    @Test
    void getArticlesByKeyWord() throws IOException, ParseException {
        ManagerArticle mangerarticletest = new ManagerArticle();
        int count=0;
        for (Article article: mangerarticletest.getArticleByKeyWord("Marc")) {
            count++;
            System.out.println("Titre"+count+": " +article.getSummary());
        }
    }

    @Test
    void getArticlesByAuthor() throws IOException {
        ManagerArticle mangerarticletest = new ManagerArticle();
        Author aut = new Author("Ernie chang");
        int count=0;
        for(Article article: mangerarticletest.getArticlesByAuthor(aut)) {
            count++;
            System.out.println("Titre"+count+": " +article.getTitle());
        }

    }
}