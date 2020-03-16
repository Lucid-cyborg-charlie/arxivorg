package app.arxivorg.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.File;


public class ManagerArticle {

    private List<Article> articles;
    private Set<Author> authors;
    private Set<Categorie> categories;

    public ManagerArticle(){
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
                article.setUpdated(entry.getUpdatedDate().toString());
                article.setPublished(entry.getPublishedDate().toString());

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
    public List<Article> getArticles() { return articles; }

    /**
     *
     * @param categorie
     * @return  article by category list
     */
    public List<Article> getArticlesByCategory(Categorie categorie){ return null; }

    /**
     *
     * @param date
     * @return articles by periode
     */
    public List<Article> getArticlesByPeriod(String date){ return null; }

    /**
     *
     * @param keyWord
     * @return article by keyword list
     */
    public List<Article> getArticlesByKeyWord(String keyWord){ return null; }

    /**
     *
     * @param author
     * @return articles by author's name
     */
    public List<Article> getArticlesByAuthor(Author author){ return null; }

}
