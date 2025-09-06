package jpa.services;

import java.util.List;
import jpa.entity.Category;

public interface CategoryService {
    void insert(Category category);
    void update(Category category);          // thêm update
    void delete(Integer id);                 // thêm delete
    Category findById(Integer id);           // thêm findById
    List<Category> findAll();
    List<Category> findByUserId(Integer id);
}
