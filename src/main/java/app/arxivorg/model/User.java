package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Article> favoris;

    public User(){
        this.favoris = new ArrayList<>();
    }

    public void saveArticle(){ }

    public void loadArticle(){ }
}
