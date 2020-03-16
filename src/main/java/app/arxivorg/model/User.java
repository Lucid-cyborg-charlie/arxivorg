package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Article> favoris;

    public User(){
        this.favoris = new ArrayList<>();
    }

    /**
     * save favorite articles
     */
    public void saveArticle(){ }

    /**
     * load Article
     */
    public void loadArticle(){ }
}
