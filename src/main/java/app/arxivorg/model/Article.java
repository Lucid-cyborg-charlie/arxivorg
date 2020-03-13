package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String id;
    private String title;
    private String summary;
    private String updated;
    private String published;
    private List<String> authors;
    private List<String> categories;


    public Article(){ }

    public Article(String id, String title, String summary, String updated, String published){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.updated = updated;
        this.published = published;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void addAuthor(String author) {
        this.authors.add(author);
    }

    public List<String> getCategory() {
        return categories;
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }
}
