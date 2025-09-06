package jpa.services;

import java.util.List;

import jpa.entity.User;

public interface UserService {
	List<User> findAll();
    void insert(User user);
    void update(User user);
    User findById(Integer id);
    User findByUsername(String username);
    User findByEmail(String email);
    User checkLogin(String username, String password);
    List<User> findUsersByRoleOrSelf(int roleId, int selfId);
}