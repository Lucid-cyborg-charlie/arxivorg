package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void saveArticle() {
        boolean isSave=User.saveArticle("8000");
        assertTrue(isSave==true);
    }

    @Test
    void removeArticle() {
        boolean isRemove=User.removeArticle("8000");
        assertTrue(isRemove==true);
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
        assertEquals(4, count);
    }
}