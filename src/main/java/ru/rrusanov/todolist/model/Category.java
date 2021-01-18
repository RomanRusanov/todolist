package ru.rrusanov.todolist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 15.01.2021
 * email roman9628@gmail.com
 * The class describe category of item.
 */
@Entity
public class Category {
    /**
     * The field contain surrogate key id in db.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The field contain name this category.
     */
    private String name;

    /**
     * The method create category instance.
     * @param name Category name.
     * @return Category.
     */
    public static Category of(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }

    /**
     * The getter.
     * @return Id.
     */
    public Long getId() {
        return id;
    }

    /**
     * The setter.
     * @param id id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The getter.
     * @return Name user.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter.
     * @param name Name user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method override equals.
     * @param o Object to compare.
     * @return if id match then return true, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    /**
     * The method override hashcode.
     * @return int hash value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * The method override toString.
     * @return Category id and name.
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}