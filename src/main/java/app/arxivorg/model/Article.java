package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article {

    private String id;
    private String title;
    private String summary;
    private String updated;
    private String published;
    private List<ArticleCategorie> articleCategories;


    public Article(){ }

    public Article(String id, String title, String summary, String updated, String published){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.updated = updated;
        this.published = published;
        this.articleCategories = new ArrayList<>();
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

    private void addArtcleCategorie(ArticleCategorie articleCategorie){
        this.articleCategories.add(articleCategorie);
    }

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
