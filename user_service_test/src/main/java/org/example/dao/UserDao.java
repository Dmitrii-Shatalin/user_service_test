package org.example.dao;
import org.example.model.User;
import java.util.List;

public interface UserDao {
    //Сохраняет нового пользователя в базе данных
    void save(User user);
    //Находит пользователя по его идентификатору
    User findById(Long id);
    //Возвращает список всех пользователей из базы данных
    List<User> findAll();
    //Обновляет данные существующего пользователя
    void update(User user);
    //Удаляет пользователя из базы данных
    void delete(User user);
}