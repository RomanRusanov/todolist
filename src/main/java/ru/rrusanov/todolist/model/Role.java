package ru.rrusanov.todolist.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
     * The field contain all users with this role.
     * transient use for correct parse object to JSON to
     * avoid circular reference(user.role -> role.users -> user.role ...).
     */
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private transient List<User> users = new ArrayList<>();

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
     * The method add user to this role.
     * @param user User instance.
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * The getter method.
     * @return List user.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * The setter method.
     * @param users List users.
     */
    public void setUsers(List<User> users) {
        this.users = users;
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