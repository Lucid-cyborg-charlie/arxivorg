package app.arxivorg.model;

import java.util.*;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.File;


public class ManagerArticle {

    private List<Article> articles;
    private Set<Author> authors;
    private Set<Categorie> categories;
    private List<String> periods;


    /**
     * Constructor
     */
    public ManagerArticle(){
        this.periods = new ArrayList<>();
        this.articles = new ArrayList<>();
        this.authors = new HashSet<>();
        this.categories = new HashSet<>();
        loadDataFromAtom("src/main/resources/dataFile.atom");
    }

    /**
     * load atom file
     * @param pathFile
     */
    private void loadDataFromAtom(String pathFile){
        try {
            File file = new File(pathFile);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(file));

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
                //construction d'un article avec des infos élémentaires
                Article article=new Article();
                article.setId(entry.getUri());
                article.setTitle(entry.getTitle());
                article.setSummary(entry.getDescription().getValue());

                Date date1 = entry.getUpdatedDate();
                String[] d1 = date1.toString().split(" ");
                article.setUpdated(d1[d1.length-1]);

                Date date2 = entry.getPublishedDate();
                String[] d2 = date2.toString().split(" ");
                article.setPublished(d2[d2.length-1]);
                this.periods.add(d2[d2.length - 1]);

                // Get the authors
                for(SyndPersonImpl author: (List<SyndPersonImpl>) entry.getAuthors()){
                    article.addArticleAuthor(new ArticleAuthor(article, new Author(author.getName())));
                }

                // Get the Categories
                for (SyndCategoryImpl categ : (List<SyndCategoryImpl>) entry.getCategories()) {
                    article.addArticleCategorie(new ArticleCategorie(article, new Categorie(categ.getName())));
                }
                this.articles.add(article);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     *
     * @return articles
     */
    public List<Article> getArticles() { return articles;
    }

    /**
     *
     * @return authors
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    /**
     *
     * @return categories
     */
    public Set <Categorie> getCategories() {
        return categories;
    }

    /**
     *
     * @param categorie
     * @return  articles by category
     */
    public List<Article> getArticlesByCategory(Categorie categorie){
        List<Article> list = new ArrayList<>();
        for(Article article : articles){
            ArticleCategorie articlecategory = new ArticleCategorie(article,categorie);
            if(article.getArticleCategories().contains(articlecategory)){
                list.add(article);
            }
        }
        return list;
    }

    /**
     *
     * @param date
     * @return articles by periode
     */
    public List<Article> getArticlesByPeriod(String date){
        List<Article> list=new ArrayList<>();
        for(Article article: articles){
            if(article.getPublished().equals(date)){
                list.add(article);
            }
        }
        return list;
    }

    /**
     *
     * @param author
     * @return articles by author's name
     */
    public List<Article> getArticlesByAuthor(Author author){
        List<Article> authors = new ArrayList<>();
        for(Article article : articles){
            ArticleAuthor articleauthor = new ArticleAuthor(article,author);
            if(article.getArticleCategories().contains(articleauthor)){
                authors.add(article);
            }
        }
        return authors;
    }

}
