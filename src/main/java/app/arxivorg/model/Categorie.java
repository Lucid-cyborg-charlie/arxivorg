package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Categorie {

    private String name;
    private List<ArticleCategorie> categorieArticles;

    /**
     * construction
     * @param name
     */
    public Categorie( String name){
        this.name = name;
        this.categorieArticles = new ArrayList<>();
    }

    /**
     *
     * @return category name
     */
    public String getName() {
        return name;
    }

    /**
     * set category name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * add articleCategory in categorieArticle list
     * @param articleCategorie
     */
    public void addCategorieArticle(ArticleCategorie articleCategorie){
        this.categorieArticles.add(articleCategorie);
    }

    /**
     *
     * @return categorieArticle list
     */
    public List<ArticleCategorie> getCategorieArticles() {
        return categorieArticles;
    }

    /**
     * verify equality between two categories
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return name.equals(categorie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     *
     * @return category description
     */
    @Override
    public String toString() {
        return name;
    }
}
