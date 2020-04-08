package app.arxivorg.commandeLine;

import app.arxivorg.model.Article;
import app.arxivorg.model.ManagerArticle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class Lister
 */
public class Lister implements Command {

    private ManagerArticle managerArticle;
    private Set<Article> filterArticles;

    public Lister(){
        this.managerArticle = new ManagerArticle();
        this.filterArticles = new HashSet<>();
        filterArticles .addAll(managerArticle.getArticles());
    }

    @Override
    public void execute(Object[] args){
        if(args.length==0){
            printArticles(filterArticles);
        }else{
            try {
                for(int i=0; i<args.length;i=i+2){
                   /* if(args[i].toString().equals("-p")){
                        filterArticles.addAll(managerArticle.getArticlesByPeriod(args[i+1].toString()));
                    }*/
                     if(args[i].toString().equals("-c")){
                        filterArticles.addAll(managerArticle.getArticlesByCategory(args[i+1].toString()));
                    }
                    else if(args[i].toString().equals("-a")){
                        filterArticles.addAll(managerArticle.getArticlesByAuthor(args[i+1].toString()));
                    }
                    else if(args[i].toString().equals("-w")){
                        filterArticles.addAll(managerArticle.getArticleByKeyWord(args[i+1].toString()));
                    }else {
                        System.out.println("Invalid option. Try again or use command help.");
                        return;
                    }
                }
                printArticles(filterArticles);
            }catch (Exception e){
                System.out.println("Invalid value option. Try again or use command help.");
            }
        }
    }

    /**
     * Print articles
     * @param articles
     */
    private void printArticles(Set<Article> articles){
        if(articles.isEmpty()){
            System.out.println("No item corresponds to your request.");
        }else {
            for(Article article: articles){
                System.out.println("Titre: "+article.getTitle()+
                        "\nAuteurs: "+article.getAuthors().toString()+
                        "\nCatégories: "+article.getCategories().toString()+
                        "\nID: "+article.getId());
            }
        }
    }

}
