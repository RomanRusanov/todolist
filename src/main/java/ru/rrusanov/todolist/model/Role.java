package ru.rrusanov.todolist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 11.01.2021
 * email roman9628@gmail.com
 * The class describe model for role.
 */
@Entity
@Table(name = "j_role")
public class Role {
    /**
     * The id field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * The name field.
     */
    private String name;

    /**
     * The method create instance of Role.
     * @param name Role name.
     * @return Role instance.
     */
    public static Role of(String name) {
        Role role = new Role();
        role.name = name;
        return role;
    }
    /**
     * The getter.
     * @return Id.
     */
    public int getId() {
        return id;
    }
    /**
     * The setter.
     * @param id id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter.
     * @return Name role.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter.
     * @param name Name role.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Override equals method.
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id;
    }

    /**
     * Override hashCode.
     * @return hash int.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Override toString.
     * @return String.
     */
    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}