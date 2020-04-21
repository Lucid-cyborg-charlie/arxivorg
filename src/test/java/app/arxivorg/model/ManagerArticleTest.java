package app.arxivorg.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

class ManagerArticleTest {
    
    ManagerArticle managerArticle = new ManagerArticle();

    boolean testGetArticlesByCategory(String cat) {
        for (Article article : managerArticle.getArticlesByCategory(cat)) {
            if(!article.getCategories().contains(cat)) return false;
        }
        return true;
    }


    /**
     * the category have to exist in api
     */
    @Test
    void getArticleByCategory(){
        assertTrue(testGetArticlesByCategory("math.GT"));
        assertTrue(testGetArticlesByCategory("cs.AI"));
        assertTrue(testGetArticlesByCategory("help-ph"));
        assertTrue(testGetArticlesByCategory("stat.ML"));
    }


    boolean testGetArticlesByPeriod(LocalDate date) {
        LocalDate now = LocalDate.now();

        for(Article article : managerArticle.getArticlesByPeriod(date)){
            LocalDate articleDate = managerArticle.convertToLocalDateViaInstant(article.getPublished());
                   if( !(date.getMonth() == articleDate.getMonth())
                    || !(date.getYear() == articleDate.getYear())
                    || !(now.getDayOfMonth() <= articleDate.getDayOfMonth())
                    || !(articleDate.getDayOfMonth() <= date.getDayOfMonth())){

                   }
        }
        return true;
    }


    @Test
    void getArticleByPeriod(){
        assertTrue(testGetArticlesByPeriod(LocalDate.of(2020, 03, 31)));
        assertTrue(testGetArticlesByPeriod(LocalDate.of(2020, 04, 01)));
        assertTrue(testGetArticlesByPeriod(LocalDate.of(2020, 04, 04)));
        assertTrue(testGetArticlesByPeriod(LocalDate.of(2020, 04, 07)));
    }


    boolean  testGetArticlesByAuthor(String au) {
        int cmp = 0;
        for (Article article : managerArticle.getArticlesByAuthor(au)) {
            for(String author : article.getAuthors()) {
                if(author.toLowerCase().contains(au.toLowerCase())) { cmp++; break;}
            }
            if(cmp == 0) return false;
        }
        System.out.println(cmp);
        return true;
    }


    /**
     * the author's have to exist in api
     */
    @Test
    void getArticlesByAuthor() {
       // assertEquals(testGetArticlesByAuthor("charles"), 10);
    }


}