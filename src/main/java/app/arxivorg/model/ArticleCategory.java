package app.arxivorg.model;

import java.util.Objects;

public class ArticleCategory {

    private Article article;
    private Category category;

    public ArticleCategory(Article article, Category category) {
        this.article = article;
        this.category = category;
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
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * set category
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }


    /**
     * verify equality between two articleCategorie
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleCategory that = (ArticleCategory) o;
        return article.equals(that.article) &&
                category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, category);
    }

}
