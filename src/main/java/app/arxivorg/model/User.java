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
     */// ajout des artciles dans les favories
    public void saveArticle(Article article){
        this.favoris.add(article);
    }

    /**
     * //supprimer la liste des articles dans les favories.
     * remove Article
     */
    public void removeArticle(Article article){
        if(favoris.contains(article)){
            this.favoris.remove(article);
        }
    }
}
