package ru.rrusanov.todolist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 11.01.2021
 * email roman9628@gmail.com
 * The class describe model for User.
 */
@Entity
@Table(name = "j_user")
public class User {
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
     * The role field.
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * The method create instance of user.
     * @param name User name.
     * @param role User role.
     * @return Instance user.
     */
    public static User of(String name, Role role) {
        User user = new User();
        user.name = name;
        user.role = role;
        return user;
    }

    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
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
     * The getter.
     * @return Name role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * The setter.
     * @param role Name role.
     */
    public void setRole(Role role) {
        this.role = role;
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
        User user = (User) o;
        return id == user.id;
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
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", role=" + role
                + '}';
    }
}