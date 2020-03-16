package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {

    private String name;
    private List<ArticleAuthor> authorArticles;

    public Author(String name) {
        this.name = name;
        this.authorArticles = new ArrayList<>();
    }

    /**
     *
     * @return author name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return authorArticles list
     */
    public List<ArticleAuthor> getAuthorArticles() {
        return authorArticles;
    }

    /**
     * add articleAuthor in authorArticles list
     * @param articleAuthor
     */
    public void addAuthorArticles(ArticleAuthor articleAuthor){
        this.authorArticles.add(articleAuthor);
    }

    /**
     * verify equality between two authors
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     *
     * @return author description
     */
    @Override
    public String toString() {
        return name;
    }
}
