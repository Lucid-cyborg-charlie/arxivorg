package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Categorie {

    private String name;
    private List<ArticleCategorie> articleCategories;

    public Categorie( String name){
        this.name = name;
        this.articleCategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArticleCategorie(ArticleCategorie articleCategorie){
        this.articleCategories.add(articleCategorie);
    }

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

    @Override
    public String toString() {
        return "Categorie{" +
                "name='" + name + '\'' +
                '}';
    }
}
