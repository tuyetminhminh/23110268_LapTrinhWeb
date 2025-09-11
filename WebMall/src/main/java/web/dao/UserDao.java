package web.dao;

import web.entity.User;

public interface UserDao {
    void update(User user);
    User findByUsername(String username);
    void save(User user);
}