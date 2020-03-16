package app.arxivorg.model;

import java.util.Objects;

public class ArticleAuthor {

    private Article article;
    private Author author;

    public ArticleAuthor(Article article, Author author) {
        this.article = article;
        this.author = author;
    }

    /**
     *
     * @return article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * set article
     * @param article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     *
     * @return article author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * set article author
     * @param author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * verify equality between two articleAuthor
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleAuthor that = (ArticleAuthor) o;
        return article.equals(that.article) &&
                author.equals(that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, author);
    }

    /**
     *
     * @return articleAuthor description
     */
    @Override
    public String toString() {
        return author.toString();
    }
}
