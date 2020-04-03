package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerArticleTest {
  ManagerArticle managerArticle = new ManagerArticle();

    ManagerArticleTest() throws IOException {
    }

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
        Categorie category = new Categorie("cs.AI");
        List<Article> articles = managerArticle.getArticlesByCategory(category);
        boolean is0k= true;
        int count=articles.size();
        for (Article article : articles ) {
            ArticleCategorie articleCategorie = new ArticleCategorie(article, category);
            if (!article.getArticleCategories().contains(articleCategorie)) {
                is0k = false;
            }

        }
        assertEquals(true,is0k);
        }


    @Test
    void getArticlesByPeriod() throws ParseException {
        String period = "hier";
        List<Article> articles = managerArticle.getArticlesByPeriod(period);
        boolean is0k = true;
        for (Article article : articles) {
            if (!article.getPublished().equals(period)) {
                is0k = false;
            }
        }

        assertEquals(true,is0k);
    }


    @Test
    void getArticlesByKeyWord() {
        List<Article> articles = managerArticle.getArticleByKeyWord("artificial");
        boolean is0k = true;
        int count=articles.size();
        int current=0;

        for (Article article : articles) {
            if (!article.getTitle().equals("artificial")) {
                is0k = false;
            }
        }
       // System.out.println("count=" +count+ " current="+current);
        assertEquals(true,is0k);
    }

    @Test
    void getArticlesByAuthor() {
        Author author = new Author("Tara Safavi");
        List<Article> articles = managerArticle.getArticlesByAuthor(author);
        boolean is0k= true;
        for (Article article : articles ) {
            ArticleAuthor articleAuthor = new ArticleAuthor(article, author);
            if (!article.getArticleCategories().contains(articleAuthor)) {
                is0k = false;
            }

        }
        assertEquals(true,is0k);
    }
    }
