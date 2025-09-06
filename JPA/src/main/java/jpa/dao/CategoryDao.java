package jpa.dao;

import java.util.List;
import jpa.entity.Category;

public interface CategoryDao {
    void create(Category category);
    void update(Category category);
    void delete(int id);
    Category findById(int id);
    List<Category> findAll();
    List<Category> findByUserId(int userId); // để manager xem category của chính mình
}
