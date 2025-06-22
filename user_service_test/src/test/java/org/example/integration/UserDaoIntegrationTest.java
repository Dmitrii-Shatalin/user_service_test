package org.example.integration;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class UserDaoIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeAll
    static void setUpClass() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    static void tearDownClass() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(sessionFactory);
        cleanUpDatabase();
    }

    private void cleanUpDatabase() {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users RESTART IDENTITY").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    void testSaveAndFindUser() {
        User user = new User("Charlie", "charlie@example.com", 28);
        userDao.save(user);
        User found = userDao.findById(user.getId());
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("charlie@example.com");
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User("Alice", "alice@example.com", 30);
        User user2 = new User("Bob", "bob@example.com", 25);
        userDao.save(user1);
        userDao.save(user2);
        List<User> users = userDao.findAll();
        assertThat(users).hasSize(2);
    }
}