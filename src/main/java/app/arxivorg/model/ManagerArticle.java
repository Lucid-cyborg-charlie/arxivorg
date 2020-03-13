package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

public class ManagerArticle {

    private List<Article> articles;

    public ManagerArticle(){
        this.articles = new ArrayList<>();
    }

    public void readFileAtom(String fileName){ }

    public void readDataFromArchive(){ }

    public List<Article> getArticlesById(String id){ return null; }

    public List<Article> getArticlesByCategory(String category){ return null; }

    public List<Article> getArticlesByPeriod(String date){ return null; }

    public List<Article> getArticlesByKeyWord(String keyWord){ return null; }

    public List<Article> getArticlesByAuthor(String author){ return null; }


}
