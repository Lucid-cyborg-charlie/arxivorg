package app.arxivorg.model;

import java.util.Objects;

public class ArticleAuthor {

    private Article article;
    private Author author;

    public ArticleAuthor(Article article, Author author) {
        this.article = article;
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

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

    @Override
    public String toString() {
        return "ArticleAuthor{" +
                "article=" + article +
                ", author=" + author +
                '}';
    }
}
