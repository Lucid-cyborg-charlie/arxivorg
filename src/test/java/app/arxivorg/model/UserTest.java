package app.arxivorg.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Test
    void saveArticle() {
        User.removeAllArticles();
        assertTrue(User.saveArticle("2004.03543v1"));
        assertTrue(User.saveArticle("2004.03540v1"));
        assertTrue(User.saveArticle("2004.03522v1"));
    }

    @Test
    void removeArticle() {
        assertTrue(User.removeArticle("2004.03543v1"));
        assertTrue(User.removeArticle("2004.03540v1"));
        assertTrue(User.removeArticle("2004.03522v1"));
    }

    @Test
    void getLastValue(){
        assertEquals("8080", User.getLastValue("http://arxiv/abs/8080"));
    }

    @Test
    void  getIdList(){
        List<String> list=List.of(new String("1234"),
                new String("2200"), new String("4040"));
        String idList=User.getIdList(list);
        assertEquals("1234,2200,4040", idList);
    }

    @Test
    void getArticlesByID(){
        int count=User.getArticlesByID().size();
        assertEquals(0, count);
    }
}