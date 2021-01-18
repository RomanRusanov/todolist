package ru.rrusanov.todolist.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 29.12.2020
 * email roman9628@gmail.com
 * The class describe model for task.
 */
@Entity
@Table(name = "item")
public class Item {
    /**
     * The id field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * The description field.
     */
    private String description;
    /**
     * The created field.
     */
    private Timestamp created;
    /**
     * The item is complete.
     */
    private boolean done;
    /**
     * The author this item.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * The category(s) this item.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> category = new ArrayList<>();
    /**
     * Default constructor.
     */
    public Item() {
    }

    /**
     * The constructor with params.
     * @param user Instance user(author).
     * @param description Description.
     * @param created When create.
     * @param done isComplete.
     */
    public Item(User user, String description, Timestamp created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
        this.user = user;
    }

    /**
     * The method add category to item.
     * @param category Category instance.
     */
    public void addCategory(Category category) {
        this.category.add(category);
    }

    /**
     * The getter method.
     * @return List all categories this item.
     */
    public List<Category> getCategory() {
        return category;
    }

    /**
     * The setter method.
     * @param category List category.
     */
    public void setCategory(List<Category> category) {
        this.category = category;
    }

    /**
     * The getter.
     * @return Id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * The setter.
     * @param item_id id.
     */
    public void setId(Integer item_id) {
        this.id = item_id;
    }

    /**
     * The getter.
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter.
     * @param description Description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The getter.
     * @return Time when create.
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * The setter.
     * @param created Time when create.
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * The getter.
     * @return is complete.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * The setter.
     * @param done is complete.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * The getter.
     * @return User instance.
     */
    public User getUser() {
        return user;
    }

    /**
     * The setter.
     * @param user User instance.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Override equals method.
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return done == item.done
                && id.equals(item.id)
                && Objects.equals(user, item.user)
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created);
    }

    /**
     * Override hashCode.
     * @return hash int.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done);
    }

    /**
     * Override toString.
     * @return String.
     */
    @Override
    public String toString() {
        return "Item{"
                + "item_id=" + id
                + ", author(user)=" + user
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}