package app.arxivorg.model;

import java.util.*;

public class User {

    private Set<Article> favorites;

    /**
     * constructor
     */
    public User(){
        this.favorites = new HashSet<>();
    }

    /**
     * save favorite articles
     */
    public void saveArticle(Article article){
        this.favorites.add(article);
    }

    /**
     * remove an article from favorite
     */
    public void removeArticle(Article article){
        if(favorites.contains(article)){
            this.favorites.remove(article);
        }
    }
}
