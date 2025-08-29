package ltw.service;

import java.util.List;
import ltw.dao.CategoryDao;
import ltw.dao.impl.CategoryDaoImpl;
import ltw.models.Category;

public class CategoryServiceImpl implements CategoryService {

	private final CategoryDao dao = new CategoryDaoImpl();

	@Override
	public void insert(Category c) {
		dao.insert(c);
	}

	@Override
	public void edit(Category c) {
		dao.edit(c);
	}

	@Override
	public void delete(int cateId, int userId) {
		dao.delete(cateId, userId);
	}

	@Override
	public Category get(int cateId, int userId) {
		return dao.get(cateId, userId);
	}

	@Override
	public List<Category> getAllByUser(int userId) {
		return dao.getAllByUser(userId);
	}

	@Override
	public List<Category> searchByName(int userId, String keyword) {
		return dao.searchByName(userId, keyword);
	}

}