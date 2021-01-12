package ru.rrusanov.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rrusanov.todolist.model.Item;
import ru.rrusanov.todolist.model.Role;
import ru.rrusanov.todolist.model.User;

import java.util.List;
import java.util.function.Function;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 30.12.2020
 * email roman9628@gmail.com
 * The class .
 */
public class Hibernate implements AutoCloseable {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Hibernate.class.getName());
    /**
     * Registry for hibernate configuration.
     */
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    /**
     * Session factory for hibernate interaction.
     */
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    /**
     * The inner class guarantees that only one instance is initialized.
     */
    private static final class Lazy {
        private static final Hibernate INST = new Hibernate();
    }

    /**
     * Default private constructor.
     */
    private Hibernate() {
    }
    /**
     * The method create and get instance PsqlStore.
     * @return PsqlStore.
     */
    public static Hibernate instOf() {
        return Lazy.INST;
    }

    /**
     * The method implements in necessary AutoCloseable interface.
     * @throws Exception Exception
     */
    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * The method execute query to DB.
     * @param command lambda with custom query.
     * @param <T> Expected type.
     * @return Instance created by hibernate.
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * The methods add model to DB.
     * @param model Instance model.
     * @param <T> Class model.
     * @return instance of model.
     */
    public <T> T createModel(T model) {
        return this.tx(session -> {
            session.save(model);
            return model;
        });
    }

    /**
     * The Method return all items.
     * @return items collection.
     */
    public List<Item> findAllItems() {
        return this.tx(
                session -> session.createQuery("from ru.rrusanov.todolist.model.Item").list()
        );
    }

    /**
     * The method takes an item to update the id field it finds in the items collection.
     * In that schema implementations passed id param must exist in DB.
     * ID use auto generated integer value in DB.
     * @param id   id item.
     * @param item Item to need update.
     * @return if item replace return true, otherwise false.
     */
    public boolean replaceItem(String id, Item item) {
        Item itemToUpdate = this.findItemById(id);
        itemToUpdate.setId(Integer.parseInt(id));
        itemToUpdate.setDescription(item.getDescription());
        itemToUpdate.setCreated(item.getCreated());
        itemToUpdate.setDone(item.isDone());
        itemToUpdate.setUser(item.getUser());
        this.tx(session -> {
            session.update(itemToUpdate);
            return itemToUpdate;
        });
        return true;
    }

    /**
     * The method takes a string and looks for it in DB by field id,
     * returns the item which has this string.
     * @param id String id item to search
     * @return math item with id
     */
    public Item findItemById(String id) {
        return this.tx(
                session -> session.get(Item.class, Integer.parseInt(id))
        );
    }

    /**
     * The method find in DB user with passed login.
     * @param login String to find.
     * @return If user exist return instance user,
     * otherwise return instance userName = -1 roleName = -1.
     */
    public User findUserByLogin(String login) {
        return this.tx(
                session -> {
                    User result = User.of("-1", Role.of("-1"));
                    final Query query = session.createQuery(
                            "from User as user where user.name=:login");
                    query.setString("login", login);
                    User user = (User) query.uniqueResult();
                    if (user != null) {
                        result = user;
                    }
                    return result;
                }
        );

    }

    /**
     * The method takes an item to delete, the id field it finds in the items collection,
     * founded item are remove from collection.
     * @param id id item.
     * @return if item with passed id delete and not find in DB return true,
     * otherwise false.
     */
    public boolean deleteItem(String id) {
        Integer itemId = Integer.parseInt(id);
        Item item = new Item();
        item.setId(itemId);
        this.tx(session -> {
            session.delete(item);
            return item;
        });
        return true;
    }
}