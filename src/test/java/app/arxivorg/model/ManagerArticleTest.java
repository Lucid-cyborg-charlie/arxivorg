package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerArticleTest {

    private ManagerArticle managerArticle = new ManagerArticle();

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
    void getArticlesByCategory(){
        Category category = new Category("cs.AI");
        List<Article> articles = managerArticle.getArticlesByCategory(category);
        boolean isOk=true;
        int count=articles.size();
        int current=0;
        for(Article article: articles){
            ArticleCategory articleCategory = new ArticleCategory(article, category);
            if(!article.getArticleCategories().contains(articleCategory)){
                isOk=false;
                current++;
            }
        }
        System.out.println("count="+count+" current="+current);
        assertTrue(true==isOk);
        assertEquals(true, isOk);
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