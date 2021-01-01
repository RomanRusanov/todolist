package ru.rrusanov.todolist.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
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
    private Integer item_id;
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
     * Default constructor.
     */
    public Item() {
    }

    /**
     * The constructor with params.
     * @param description Description.
     * @param created When create.
     * @param done isComplete.
     */
    public Item(String description, Timestamp created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    /**
     * The getter.
     * @return Id.
     */
    public Integer getItem_id() {
        return item_id;
    }

    /**
     * The setter.
     * @param item_id id.
     */
    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
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
                && item_id.equals(item.item_id)
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created);
    }

    /**
     * Override hashCode.
     * @return hash int.
     */
    @Override
    public int hashCode() {
        return Objects.hash(item_id, description, created, done);
    }

    /**
     * Override toString.
     * @return String.
     */
    @Override
    public String toString() {
        return "Item{"
                + "item_id=" + item_id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}