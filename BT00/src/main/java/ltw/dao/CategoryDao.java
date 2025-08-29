package ltw.dao;

import java.util.List;
import ltw.models.Category;

public interface CategoryDao {

	void insert(Category c);
    void edit(Category c);              // chỉnh name/icons của category thuộc user
    void delete(int cateId, int userId);// xóa nhưng phải thuộc đúng user

    Category get(int cateId, int userId);
    List<Category> getAllByUser(int userId);
    List<Category> searchByName(int userId, String keyword);
}
