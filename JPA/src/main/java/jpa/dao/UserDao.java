package jpa.dao;

import jpa.entity.User;

public interface UserDao {
    void create(User user);
    void update(User user);
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    User checkLogin(String username, String password);
}
