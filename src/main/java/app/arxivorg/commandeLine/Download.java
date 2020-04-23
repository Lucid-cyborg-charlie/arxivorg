package app.arxivorg.commandeLine;

import app.arxivorg.model.Article;
import app.arxivorg.model.ManagerArticle;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Download implements Command{

    private ManagerArticle managerArticle;

    public Download(){
        this.managerArticle = new ManagerArticle();
    }

    @Override
    public void execute(Object[] args){
        if(args.length==0){
            List<Article> articles=managerArticle.getArticles();
            ManagerArticle.downloadSeveralArticlesToPDF(articles);
            printResult(articles.size());
        }else{
            int count=0;
            try {
                for(int i=0; i<args.length;i=i+2){
                    if(args[i].toString().equals("-p")){
                        String date=args[i+1].toString();
                        try {
                            List<Integer> list=decomposeDate(date);
                            LocalDate localDate=LocalDate.of(list.get(2), list.get(1), list.get(0));
                            List<Article> articles=managerArticle.getArticlesByPeriod(localDate);
                            ManagerArticle.downloadSeveralArticlesToPDF(articles);
                            count+=articles.size();
                        }catch(Exception e){
                            System.out.println("Invalid date format. jj/mm/aaaa or jj-mm-aaaa");
                        }
                    }
                    else if(args[i].toString().equals("-c")){
                        List<Article> articles=managerArticle.getArticlesByCategory(args[i+1].toString());
                        ManagerArticle.downloadSeveralArticlesToPDF(articles);
                        count+=articles.size();
                    }
                    else if(args[i].toString().equals("-a")){
                        List<Article> articles=managerArticle.getArticlesByAuthor(args[i+1].toString());
                        ManagerArticle.downloadSeveralArticlesToPDF(articles);
                        count+=articles.size();
                    }
                    else if(args[i].toString().equals("-w")){
                        List<Article> articles=managerArticle.getArticleByKeyWord(args[i+1].toString());
                        ManagerArticle.downloadSeveralArticlesToPDF(articles);
                        count+=articles.size();
                    }else {
                        System.out.println("Invalid option. Try again or use command help.");
                        return;
                    }
                }
                printResult(count);
            }catch (Exception e){
                System.out.println("Invalid value option. Try again or use command help.");
            }
        }
    }

    /**
     * Print articles
     * @param size
     */
    private void printResult(int size){
        Path path = FileSystems.getDefault().getPath(System.getProperty("user.home"),
                "/Documents/", "arxivorg");
        System.out.println(size+" files downloads in "+path);
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
