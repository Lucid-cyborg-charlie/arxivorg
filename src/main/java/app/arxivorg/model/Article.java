package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Article
 */
public class Article {

    private String id;
    private String title;
    private String summary;
    private String updated;
    private String published;
    private List<ArticleCategorie> articleCategories;
    private List<ArticleAuthor> articleAuthors;


    /**
     * Default Constructor
     */
    public Article(){
        this.articleAuthors = new ArrayList<>();
        this.articleCategories = new ArrayList<>();
    }

    /**
     * Constructor with parameters
     * @param id
     * @param title
     * @param summary
     * @param updated
     * @param published
     */
    public Article(String id, String title, String summary, String updated, String published){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.updated = updated;
        this.published = published;
        this.articleCategories = new ArrayList<>();
        this.articleAuthors = new ArrayList<>();
    }

    /**
     *
     * @return article id
     */
    public String getId() {
        return id;
    }


    /**
     * set article id
     * @param id
     */
    public void setId(String id){this.id = id; }



    /**
     *
     * @return article title
     */
    public String getTitle() {
        return title;
    }


    /**
     * set article Title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return article summary
     */
    public String getSummary() {
        return summary;
    }


    /**
     * set article summary
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return article updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * set article update
     * @param updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     *
     * @return article date of publication
     */
    public String getPublished() {
        return published;
    }
    /**
     * set article publication
     * @param published
     */
    public void setPublished(String published) {
        this.published = published;
    }

    /**
     * add an ArticleCategorie in articleCategories list
     * @param articleCategorie
     */
    public void addArticleCategorie(ArticleCategorie articleCategorie){
        this.articleCategories.add(articleCategorie);
    }


    /**
     *
     * @return articleCategories list
     */
    public List<ArticleCategorie> getArticleCategories(){ return this.articleCategories ;}

    public void addArticleAuthor(ArticleAuthor articleAuthor){
        this.articleAuthors.add(articleAuthor);
    }


    /**
     *
     * @return articleAuthors list
     */
    public List<ArticleAuthor> getArticleAuthors() {
        return articleAuthors;
    }

    /**
     * verify equality between two article
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     *
     * @return description of article
     */
    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", updated='" + updated + '\'' +
                ", published='" + published + '\'' +
                '}';
    }
}
