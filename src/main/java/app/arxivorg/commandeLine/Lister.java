package app.arxivorg.commandeLine;

import app.arxivorg.model.Article;
import app.arxivorg.model.Author;
import app.arxivorg.model.Categorie;
import app.arxivorg.model.ManagerArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Lister
 */
public class Lister implements Command {

    private ManagerArticle managerArticle;
    private List<Article> filterArticles;

    public Lister() {
        this.managerArticle = new ManagerArticle();
        this.filterArticles = managerArticle.getArticles();
    }

    @Override
    public void execute(Object[] args) {
        if(args.length==0){
            printArticles(filterArticles);
        }else{
            for(int i=0; i<args.length;i=i+2){
                if(args[i].toString().equals("-p")){
                    filterArticles=managerArticle.getArticlesByPeriod(args[i+1].toString());
                    managerArticle.setArticles(filterArticles);
                }
                if(args[i].toString().equals("-c")){
                    filterArticles=managerArticle.getArticlesByCategory(new Categorie(args[i+1].toString()));
                    managerArticle.setArticles(filterArticles);
                }
                if(args[i].toString().equals("-a")){
                    filterArticles=managerArticle.getArticlesByAuthor(new Author((args[i+1].toString())));
                    managerArticle.setArticles(filterArticles);
                }
                if(args[i].toString().equals("-w")){
                    filterArticles=managerArticle.getArticleByKeyWord(args[i+1].toString());
                    managerArticle.setArticles(filterArticles);
                }
            }
            printArticles(filterArticles);
        }
    }

    private void printArticles(List<Article> articles){
        for(Article article: articles){
            System.out.println("Titre: "+article.getTitle()+
                    "\nAuteurs: "+article.getArticleAuthors().toString()+"\nID: "+article.getId());
        }
    }
}
