package app.arxivorg.model;

import java.text.SimpleDateFormat;
import java.util.*;


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
     * @return article date of publication
     */
    public Date getPublished() {
        return published;
    }

    /**
     * @param published
     */
    public void setPublished(Date published) {
        this.published = published;
    }

    /**
     * @param categories
     */
    public void setCategories(List<String> categories){
        this.categories = categories;
    }

    /**
     * @return
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * @return
     */
    public List<String> getAuthors(){
        return this.authors;
    }

    /**
     * @param authors
     */
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

    /**
     * return the correct date format
     * @param date
     * @return
     */
    public static String formatOfDate(Date date){
        String format = "dd/MM/yyyy à H:mm:ss";
        SimpleDateFormat formatting = new SimpleDateFormat(format);
        try {
            String res = formatting.format(date);
            return res;
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return description of article
     */
    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("Titre: "+title);
        stringBuffer.append("\nAuteurs: "+authors.toString());
        stringBuffer.append("\nID: "+id);
        stringBuffer.append("\nPublié le "+formatOfDate(published));
        return stringBuffer.toString();
    }

}
