package app.arxivorg.model;

import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPersonImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class ManagerArticle {

    private List<Article> articles;
    private Set<Author> authors;
    private Set<Categorie> categories;
    private List<String> periods;

    public ManagerArticle() throws IOException {
        this.periods = new ArrayList<>();
        this.articles = new ArrayList<>();
        this.authors = new HashSet<>();
        this.categories = new HashSet<>();
        loadDataFromAtom();
        initializePeriod();
        loadCategories();
    }

    /**
     * load atom file from arxiv api
     * @param
     */
    private void loadDataFromAtom(){
        try {

            URL url = new URL("http://export.arxiv.org/api/query?search_query=cat:cs.CL&start=0&max_results=30&sortBy=submittedDate&sortOrder=descending");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

                // Article constructor
                Article article=new Article();
                article.setId(entry.getUri());
                article.setTitle(entry.getTitle());
                article.setSummary(entry.getDescription().getValue());
                article.setUpdated(entry.getUpdatedDate().toString());

                // Get date of publication
                article.setPublished(entry.getPublishedDate());


                // Get the authors
                for(SyndPersonImpl author: (List<SyndPersonImpl>) entry.getAuthors()){
                    article.addArticleAuthor(new ArticleAuthor(article, new Author(author.getName())));
                    this.authors.add(new Author(author.getName()));
                }

                this.articles.add(article);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    /**
     *
     * @param middle category or author name
     * @return article by category or author from arxiv api
     */
    private List<Article> loadDataFromAtom(String middle){
        List<Article> articleList = new ArrayList<>();
        try{
            String head = "http://export.arxiv.org/api/query?search_query=";
            String feet = "&start=0&max_results=30&sortBy=submittedDate&sortOrder=descending";
            URL url = new URL(head+middle+feet);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            for(SyndEntry entry : (List<SyndEntry>) feed.getEntries()){
                Article article = new Article();
                article.setId(entry.getUri());
                article.setPublished(entry.getPublishedDate());
                article.setTitle(entry.getTitle());
                article.setSummary(entry.getDescription().getValue());
                article.setUpdated(entry.getUpdatedDate().toString());

                for(SyndPersonImpl author: (List<SyndPersonImpl>) entry.getAuthors()){
                    article.addArticleAuthor(new ArticleAuthor(article, new Author(author.getName())));
                    this.authors.add(new Author(author.getName()));
                }
                for (SyndCategoryImpl categ : (List<SyndCategoryImpl>) entry.getCategories()) {
                    article.addArticleCategorie(new ArticleCategorie(article, new Categorie(categ.getName())));

                }
                articleList.add(article);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return articleList;
    }



    /**
     *
     * @param categorie
     * @return  article by category list
     */
    public List<Article> getArticlesByCategory(Categorie categorie){
        if(categorie.getName().equals("Toutes")) return articles;
        return loadDataFromAtom("cat:"+categorie.getName());
    }

    /**
     *
     * @param period
     * @return articles by periode
     */
    public List<Article> getArticlesByPeriod(String period) throws ParseException {
        List<Article> list = new ArrayList<>();
        if (period.equals("Tout")) return articles;
        for(Article article : articles){
            if(period.equals("Aujourd'hui") && findPeriod(article.getPublished(), 0)) list.add(article);
            else if(period.equals("Hier") && findPeriod(article.getPublished(), 1)) list.add(article);
            else if(period.equals("Avant hier") && findPeriod(article.getPublished(), 2)) list.add(article);
        }
        return list;
    }

    /**
     *
     * @param author
     * @return articles by author's name
     */
    public List<Article> getArticlesByAuthor(Author author){
        if(authors.contains(author)) return findAuthors(author);
        return loadDataFromAtom("au:"+author.getName());
    }

    /**
     *
     * @param word
     * @return article by keyWord
     */
    public List<Article> getArticleByKeyWord(String word){
        List<Article> articleList = new ArrayList<>();
        for(Article article : getArticles()){
            if (article.getTitle().contains(word) || article.getSummary().contains(word)){
                articleList.add(article);
            }
        }
        return articleList;
    }


    /**
     *
     * @param author author selected
     * @return article by author
     */
    private List<Article> findAuthors(Author author){
        List<Article> articleList = new ArrayList<>();
        for (Article article : articles){
            for(int i = 0; i < article.getArticleAuthors().size(); i++){
                if(article.getArticleAuthors().get(i).getAuthor().equals(author)) articleList.add(article);
            }
        }
        return articleList;
    }


    /**
     * load all categories from a file in resources
     * @throws IOException if file didn't find
     */
    private void loadCategories() throws IOException {
        File file = new File("src/main/resources/categories.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String category = "";
        while ((category = bufferedReader.readLine()) != null){
            this.categories.add(new Categorie(category));
        }
    }


    /**
     *
     * @param date article published date
     * @param period an period maybe today = 0 or yesterday = 1
     * @return boolean if article math with an periods
     * @throws ParseException
     */
    private boolean findPeriod(Date date, int period) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar today = Calendar.getInstance();
        today.setTime(Calendar.getInstance().getTime());

        Integer month = calendar.get(Calendar.MONTH + 1);
        Integer day  = calendar.get(Calendar.DAY_OF_MONTH);
        Integer year = calendar.get(Calendar.YEAR);

        Integer recentMonth = today.get(Calendar.MONTH + 1);
        Integer recentDay  = today.get(Calendar.DAY_OF_MONTH);
        Integer recentYear = today.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.FRENCH);
        Date firstDate = sdf.parse(recentMonth+"/"+recentDay+"/"+recentYear);
        Date secondDate = sdf.parse(month.toString()+"/"+day.toString()+"/"+year.toString());

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return period == diff;
    }


    /**
     *  initialize all periods
     */
    public void initializePeriod(){
        periods.add("Tout");
        periods.add("Aujourd'hui");
        periods.add("Hier");
        periods.add("Avant hier");
    }




    public List<Article> getArticles(){
        return articles;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Set <Categorie> getCategories() {
        return categories;
    }

    public List<String> getPeriods(){
        return this.periods;
    }

    public void setArticles(List<Article> filterArticles) {
        this.articles=filterArticles;
    }
}
