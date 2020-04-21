package app.arxivorg.model;

import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPersonImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


/**
 * Class Manager Article
 */
public class ManagerArticle {

    private List<Article> articles;
    public static Map<String, Integer> map;
    public final static Set<String> categories = loadCategories();
    public final static List<Article> finalArticles = loadDataFromAPI("http://export.arxiv.org/api/query?search_query=all:all&start=0&"+
            "max_results=500&sortBy=submittedDate&sortOrder=descending");

    /**
     * Constructor
     */
    public ManagerArticle(){
        map = new HashMap<>();
        this.articles = new LinkedList<Article>();
        this.articles.addAll(finalArticles);
    }

    /**
     * load atom file from arxiv api
     * @param
     */
    public static List<Article> loadDataFromAPI(String req){
        List<Article> list = new LinkedList<Article>();
        try {
            URL url = new URL(req);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

                // Article constructor
                Article article=new Article();
                article.setId(entry.getUri());
                article.setTitle(entry.getTitle());
                article.setSummary(entry.getDescription().getValue());
                article.setUpdated(entry.getUpdatedDate());
                article.setPublished(entry.getPublishedDate());

                // Get authors
                for(Object author: entry.getAuthors()){
                    SyndPersonImpl au = ((SyndPersonImpl)(author));
                    article.getAuthors().add(au.getName());
                }

                // Get Categories
                for(Object category: entry.getCategories()){
                    SyndCategoryImpl cat = ((SyndCategoryImpl)(category));
                    article.getCategories().add(cat.getName());
                }

                list.add(article);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } return list;
    }




    /**
     * @param category
     * @return  article by category list
     */
    public List<Article> getArticlesByCategory(String category){
        if(category.equals("Toutes")){
            return finalArticles;
        }
       return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=cat:" +
               category+"&start=0&max_results=500&sortBy=submittedDate&sortOrder=descending");
    }


    /**
     * @param period
     * @return articles by periode
     */
    public List<Article> getArticlesByPeriod(LocalDate period){
        List<Article> articleList = new LinkedList<>();
        for(Article article : finalArticles){
            if(period(period, convertToLocalDateViaInstant(article.getPublished()))) articleList.add(article);
        }
        return articleList;
    }


    /**
     * @param author
     * @return articles by author's name
     */
    public List<Article> getArticlesByAuthor(String author){
        return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=au:" +
                author+"&start=0&max_results=500&sortBy=submittedDate&sortOrder=descending");
    }


    /**
     * @param word
     * @return article by keyWord
     */
    public List<Article> getArticleByKeyWord(String word){
        return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=all:" +
                word+"&start=0&max_results=500&sortBy=submittedDate&sortOrder=descending");
    }



    /**
     * load all categories from a file in resources
     */
    private static Set<String> loadCategories(){
        Set<String> result= new HashSet<>();
        File file = new File("src/main/resources/categories.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String category = "";
            while ((category = bufferedReader.readLine()) != null){
                result.add(category);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }


    /**
     * Download an article in pdf
     * @param article
     */
    private static void downloadArticleToPDF(Article article){
        try {
            String link1=article.getId().replace("abs","pdf");
            String link2=link1.replace("http", "https");
            URL url = new URL(link2);

            InputStream in = url.openStream();
            Path path1 = FileSystems.getDefault().getPath(System.getProperty("user.home"), "/Documents/", "arxivorg");
            Files.createDirectories(path1);
            String[] tab=article.getId().split("/");
            String fineName=tab[tab.length-1];
            Path path2= Paths.get(path1.toString().concat("/"+fineName+".pdf"));
            Files.copy(in, path2, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Download several articles in pdf
     * @param articles
     */
    public static void downloadSeveralArticlesToPDF(List<Article> articles){
        for(Article article: articles){
            downloadArticleToPDF(article);
        }
    }


    /**
     * @param dateToConvert
     * @return
     */
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    /**
     * @return
     */
    public List<Article> getArticles(){
        return articles;
    }


    /**
     * @return  api categories
     */
    public Set <String> getCategories() {
        return categories;
    }

    /**
     * @param filterArticles
     */
    public void setArticles(List<Article> filterArticles) {
        this.articles=filterArticles;
    }


    /**
     *
     * @param date
     * @param param
     * @return
     */
    private boolean period(LocalDate date, LocalDate param){
        LocalDate today = LocalDate.now();
        return param.getMonth() == date.getMonth() && param.getYear() == date.getYear()
                && today.getDayOfMonth() >= param.getDayOfMonth() && date.getDayOfMonth() <= param.getDayOfMonth();
    }



    /**
     * @return articles dates
     */
    private static Set<String> extractDate(){
        Set<String> list = new HashSet<>();
        for(Article article : finalArticles){
            LocalDate localDate = convertToLocalDateViaInstant(article.getPublished());
            list.add(localDate.getDayOfMonth()+"/"+localDate.getMonth()+"/"+localDate.getYear());
        } return list;
    }

    /**
     * @return articles authors
     */
    private static Set<String> extractAuthor(){
        Set<String> list = new HashSet<>();
        for(Article article : finalArticles){
            list.addAll(article.getAuthors());
        } return list;
    }


    /**
     * @return the number of article by category
     */
    public static Map<String, Integer> statArticlesByCategories(){
        Map<String, Integer> map = new HashMap<>();
        for(String category : categories){
            int cmp = 0;
            for(Article article : finalArticles){
                if(article.getCategories().contains(category)) cmp++;
            } if(cmp > 0) map.put(category, cmp);
        }
        return map;
    }


    /**
     * @return the number of article by day
     */
    public static Map<String, Integer> statArticleByDay(){
        Map<String, Integer> unSortedMap = new HashMap<>();
        for(String date : extractDate()){
            int cmp = 0;
            for(Article article: finalArticles){
               LocalDate localDate = convertToLocalDateViaInstant(article.getPublished());
               if(date.equals(localDate.getDayOfMonth()+"/"+localDate.getMonth()+"/"+localDate.getYear())) cmp++;
            }
           unSortedMap.put(date, cmp);
        }
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(unSortedMap);
        return sortedMap;
    }


    /**
     * @return the number of article by author
     */
    public static Map<String, Integer> statArticleByAuthor(){
        Map<String, Integer> map = new HashMap<>();
        for(String author : extractAuthor()){
            int cmp = 0;
            for(Article article: finalArticles){
                if(article.getAuthors().contains(author)) cmp++;
            }
            if(cmp >= 2)map.put(author, cmp);  // only authors with more than one article are selected
        }
        return map;
    }


    /**
     *
     * @param expressions
     * @return
     */
    public void statArticleByExpression(String expressions){
        String[] list = expressions.split(",");
        Map<String, Integer> map = new HashMap<>();
        for(String expression : list){
            int cmp = 0;
            for(Article article : finalArticles){
                if(article.getTitle().contains(expression) || article.getSummary().contains(expression)) cmp++;
            }
            if(cmp > 0) map.put(expression, cmp);
        }
        ManagerArticle.map = map;
    }



    /**
     * load data from atom file
     * @param
     */
    public static List<Article> loadDataFromAtom(){
        List<Article> list = new LinkedList<Article>();
        try {
            URL url = new URL("src/main/resources/atomFile.atom");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

                // Article constructor
                Article article=new Article();
                article.setId(entry.getUri());
                article.setTitle(entry.getTitle());
                article.setSummary(entry.getDescription().getValue());
                article.setUpdated(entry.getUpdatedDate());
                article.setPublished(entry.getPublishedDate());

                // Get authors
                for(Object author: entry.getAuthors()){
                    SyndPersonImpl au = ((SyndPersonImpl)(author));
                    article.getAuthors().add(au.getName());
                }

                // Get Categories
                for(Object category: entry.getCategories()){
                    SyndCategoryImpl cat = ((SyndCategoryImpl)(category));
                    article.getCategories().add(cat.getName());
                }

                list.add(article);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } return list;
    }



}
