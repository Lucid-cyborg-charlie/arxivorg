package app.arxivorg.commandeLine;

import app.arxivorg.model.Article;
import app.arxivorg.model.ManagerArticle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Lister implements Command {

    private ManagerArticle managerArticle;
    private Set<Article> filterArticles;

    public Lister(){
        this.managerArticle = new ManagerArticle();
        this.filterArticles = new HashSet<>();
    }

    @Override
    public void execute(Object[] args){
        if(args.length==0){
            filterArticles .addAll(managerArticle.getArticles());
            printArticles(filterArticles);
        }else{
            try {
                for(int i=0; i<args.length;i=i+2){
                   if(args[i].toString().equals("-p")){
                       String date=args[i+1].toString();
                       try {
                           List<Integer> list=decomposeDate(date);
                           LocalDate localDate=LocalDate.of(list.get(2), list.get(1), list.get(0));
                           filterArticles.addAll(managerArticle.getArticlesByPeriod(localDate));
                       }catch(Exception e){
                           System.out.println("Invalid date format. jj/mm/aaaa or jj-mm-aaaa");
                       }
                   }
                   else if(args[i].toString().equals("-c")){
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

    /**
     * Decompose the date into list of Integer
     * @param date
     * @return
     * @throws Exception
     */
    private List<Integer> decomposeDate(String date) throws Exception{
        List<Integer> list=new ArrayList<>();
        String[] res=date.split("-|/");
        list.add(Integer.parseInt(res[0]));
        list.add(Integer.parseInt(res[1]));
        list.add(Integer.parseInt(res[2]));
        return list;
    }

}
