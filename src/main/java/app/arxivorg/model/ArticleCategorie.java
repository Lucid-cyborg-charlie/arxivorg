package app.arxivorg.model;

import java.util.Objects;

public class ArticleCategorie {

    private Article article;
    private Categorie categorie;

    public ArticleCategorie(Article article, Categorie categorie) {
        this.article = article;
        this.categorie = categorie;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleCategorie that = (ArticleCategorie) o;
        return article.equals(that.article) &&
                categorie.equals(that.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, categorie);
    }


}
