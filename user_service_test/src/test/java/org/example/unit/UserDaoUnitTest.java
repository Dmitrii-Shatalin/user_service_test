package org.example.unit;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserDaoUnitTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private UserDao userDao = new UserDaoImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void testSaveUser_ShouldCallHibernateMethods() {
        // Arrange
        User user = new User("Alice", "alice@example.com", 30);

        // Act
        userDao.save(user);

        // Assert
        verify(session, times(1)).save(user);
        verify(transaction, times(1)).commit();
    }

    @Test
    void testFindAll_ReturnsListOfUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                new User("Alice", "alice@example.com", 30),
                new User("Bob", "bob@example.com", 25)
        );

        var query = mock(org.hibernate.query.Query.class);
        when(session.createQuery("from User", User.class)).thenReturn(query);
        when(query.list()).thenReturn(users);

        // Act
        List<User> result = userDao.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Alice");
    }
}