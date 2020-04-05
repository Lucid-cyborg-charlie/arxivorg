package app.arxivorg.model;

import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPersonImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import javafx.scene.control.DatePicker;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class ManagerArticle {

    private List<Article> articles;
    private Set<String> categories;
    private List<Article> articlesPeriods;

    public ManagerArticle(){
        this.articlesPeriods = loadDataFromAPI("http://export.arxiv.org/api/query?search_query=all:all&start=0&max_results=150&sortBy=submittedDate&sortOrder=descending");
        this.articles = new LinkedList<>();
        this.categories = new HashSet<>();
        this.articles = loadDataFromAPI("http://export.arxiv.org/api/query?search_query=all:all&start=0&max_results=150&sortBy=submittedDate&sortOrder=descending");
        loadCategories();
    }

    /**
     * load atom file from arxiv api
     * @param
     */
    private List<Article> loadDataFromAPI(String req){
        List<Article> list = new LinkedList<>();
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
            return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=all:all&start=0&max_results=150&sortBy=submittedDate&sortOrder=descending");
        }
       return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=cat:" +
               category+"&start=0&max_results=150&sortBy=submittedDate&sortOrder=descending");
    }


    /**
     * @param period
     * @return articles by periode
     */
    public List<Article> getArticlesByPeriod(DatePicker period){
        List<Article> articleList = new LinkedList<>();
        for(Article article : articlesPeriods){
            if(period.getValue().equals(convertToLocalDateViaInstant(article.getPublished()))) articleList.add(article);
        }
        return articleList;
    }


    /**
     * @param author
     * @return articles by author's name
     */
    public List<Article> getArticlesByAuthor(String author){
        return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=au:" +
                author+"&start=0&max_results=150&sortBy=submittedDate&sortOrder=descending");
    }


    /**
     * @param word
     * @return article by keyWord
     */
    public List<Article> getArticleByKeyWord(String word){
        return loadDataFromAPI("http://export.arxiv.org/api/query?search_query=all:" +
                word+"&start=0&max_results=150&sortBy=submittedDate&sortOrder=descending");
    }



    /**
     * load all categories from a file in resources
     * @throws IOException if file didn't find
     */
    private void loadCategories(){
        File file = new File("src/main/resources/categories.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String category = "";
            while ((category = bufferedReader.readLine()) != null){
                this.categories.add(category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Download an article in pdf
     * @param article
     */
    public static void downloadArticleToPDF(Article article){
        URL url = null;
        try {
            String link1=article.getId().replace("abs","pdf");
            String link2=link1.replace("http", "https");
            url = new URL(link2);
            try (InputStream in = url.openStream()) {
                Path path1 = FileSystems.getDefault().getPath(System.getProperty("user.home"), "/Documents/", "arxivorg");
                Files.createDirectories(path1);
                String[] tab=article.getId().split("/");
                String fineName=tab[tab.length-1];
                Path path2= Paths.get(path1.toString().concat("/"+fineName+".pdf"));
                Files.copy(in, path2, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // handle exception
            }
        } catch (MalformedURLException e) {
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
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    public List<Article> getArticles(){
        return articles;
    }


    public Set <String> getCategories() {
        return categories;
    }


    public void setArticles(List<Article> filterArticles) {
        this.articles=filterArticles;
    }
}
