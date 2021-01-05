package ru.rrusanov.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rrusanov.todolist.model.Item;

import java.util.List;

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
     * The methods add item to DB.
     *
     * @param item instance of item
     * @return new item
     */
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            return item;
        }
    }

    /**
     * The Method return all items.
     * @return items collection.
     */
    public List<Item> findAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("from ru.rrusanov.todolist.model.Item").list();
            session.getTransaction().commit();
            return result;
        }
    }

    /**
     * The method takes an item to update the id field it finds in the items collection.
     * In that schema implementations passed id param must exist in DB.
     * ID use auto generated integer value in DB.
     * @param id   id item.
     * @param item Item to need update.
     * @return if item replace return true, otherwise false.
     */
    public boolean replace(String id, Item item) {
        Item itemToUpdate = findById(id);
        itemToUpdate.setId(Integer.parseInt(id));
        itemToUpdate.setDescription(item.getDescription());
        itemToUpdate.setCreated(item.getCreated());
        itemToUpdate.setDone(item.isDone());
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(itemToUpdate);
            session.getTransaction().commit();
            return true;
        }
    }

    /**
     * The method takes a string and looks for it in the items array by field id,
     * returns the item which has this string.
     * @param id String id item to search
     * @return math item with id
     */
    public Item findById(String id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item result = session.get(Item.class, Integer.parseInt(id));
            session.getTransaction().commit();
            return result;
        }
    }

    /**
     * The method takes an item to delete, the id field it finds in the items collection,
     * founded item are remove from collection.
     * @param id id item.
     * @return if item with passed id delete and not find in DB return true,
     * otherwise false.
     */
    public boolean delete(String id) {
        Integer itemId = Integer.parseInt(id);
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item item = new Item();
            item.setId(itemId);
            session.delete(item);
            session.getTransaction().commit();
            return true;
        }
    }
}