package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        int count=0;
        for(Article article: mangerarticletest.getArticlesByCategory(cat)){
            count++;
            System.out.println("Titre"+count+": "+article.getTitle());
        }
    }

    @Test
    void getArticlesByPeriod() {
    }

    @Test
    void getArticlesByKeyWord() {
    }

    @Test
    void getArticlesByAuthor() {
    }
}