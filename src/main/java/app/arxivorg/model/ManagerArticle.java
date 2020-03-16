package app.arxivorg.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerArticle {

    private List<Article> articles;
    private Set<Author> authors;
    private Set<Categorie> categories;

    public ManagerArticle(){
        this.articles = new ArrayList<>();
    }

    private void loadDataFromAtom(String fileName){ }

    public List<Article> getArticlesByCategory(Categorie categorie){ return null; }

    public List<Article> getArticlesByPeriod(String date){ return null; }

    public List<Article> getArticlesByKeyWord(String keyWord){ return null; }

    public List<Article> getArticlesByAuthor(Author author){ return null; }

}
