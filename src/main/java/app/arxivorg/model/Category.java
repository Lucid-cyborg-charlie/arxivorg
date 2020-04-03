package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {

    private String name;
    private List<ArticleCategory> categoryArticles;

    /**
     * construction
     * @param name
     */
    public Category(String name){
        this.name = name;
        this.categoryArticles = new ArrayList<>();
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
     * @param articleCategory
     */
    public void addCategoryArticle(ArticleCategory articleCategory){
        this.categoryArticles.add(articleCategory);
    }

    /**
     *
     * @return categorieArticle list
     */
    public List<ArticleCategory> getCategoryArticles() {
        return categoryArticles;
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
        Category category = (Category) o;
        return name.equals(category.name);
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
