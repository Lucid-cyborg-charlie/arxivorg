package app.arxivorg.model;

import javafx.scene.control.DatePicker;

import java.util.*;

/**
 * Class Article
 */
public class Article {

    private String id;
    private String title;
    private String summary;
    private Date updated;
    private Date published;
    private List<String> categories;
    private List<String> authors;


    /**
     * Default Constructor
     */
    public Article(){
        this.authors = new LinkedList<>();
        this.categories = new LinkedList<>();
    }

    /**
     * Constructor with parameters
     * @param id
     * @param title
     * @param summary
     * @param updated
     * @param published
     */
    public Article(String id, String title, String summary, Date updated, Date published){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.updated = updated;
        this.published = published;
        this.authors = new LinkedList<>();
        this.categories = new LinkedList<>();
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
    public Date getUpdated() {
        return updated;
    }

    /**
     * set article update
     * @param updated
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     *
     * @return article date of publication
     */
    public Date getPublished() {
        return published;
    }
    /**
     * set article publication
     * @param published
     */
    public void setPublished(Date published) {
        this.published = published;
    }


    public void setCategories(List<String> categories){
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getAuthors(){
        return this.authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
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
