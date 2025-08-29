package ltw.service;
import java.util.List;
import ltw.models.Category;

public interface CategoryService {

	void insert(Category c);
    void edit(Category c);
    void delete(int cateId, int userId);

    Category get(int cateId, int userId);
    List<Category> getAllByUser(int userId);
    List<Category> searchByName(int userId, String keyword);
}
