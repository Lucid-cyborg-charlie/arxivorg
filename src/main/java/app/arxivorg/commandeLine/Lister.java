package app.arxivorg.commandeLine;

import app.arxivorg.model.Article;
import app.arxivorg.model.ManagerArticle;
import java.util.List;

/**
 * Class Lister
 */
public class Lister implements Command {

    private ManagerArticle managerArticle;
    private List<Article> filterArticles;

    public Lister(){
        this.managerArticle = new ManagerArticle();
        this.filterArticles = managerArticle.getArticles();
    }

    @Override
    public void execute(Object[] args){
        if(args.length==0){
            printArticles(filterArticles);
        }else{
            try {
                for(int i=0; i<args.length;i=i+2){
                   /* if(args[i].toString().equals("-p")){
                        filterArticles=managerArticle.getArticlesByPeriod(args[i+1].toString());
                        managerArticle.setArticles(filterArticles);
                    }*/
                     if(args[i].toString().equals("-c")){
                        filterArticles=managerArticle.getArticlesByCategory(args[i+1].toString());
                        managerArticle.setArticles(filterArticles);
                    }
                    else if(args[i].toString().equals("-a")){
                        filterArticles=managerArticle.getArticlesByAuthor(args[i+1].toString());
                        managerArticle.setArticles(filterArticles);
                    }
                    else if(args[i].toString().equals("-w")){
                        filterArticles=managerArticle.getArticleByKeyWord(args[i+1].toString());
                        managerArticle.setArticles(filterArticles);
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

    private void printArticles(List<Article> articles){
        if(articles.isEmpty()){
            System.out.println("No item corresponds to your request.");
        }else {
            for(Article article: articles){
                System.out.println("Titre: "+article.getTitle()+
                        "\nAuteurs: "+article.getAuthors().toString()+"\nID: "+article.getId());
            }
        }
    }

}
