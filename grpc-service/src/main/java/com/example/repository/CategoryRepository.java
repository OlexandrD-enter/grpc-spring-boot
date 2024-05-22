package com.example.repository;

import com.example.proto.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
  List<Category> getCategories();
  void addCategory(Category category);
  void removeCategory(Category category);
  Optional<Category> findCategoryById(long id);
}

