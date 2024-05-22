package com.example.repository.impl;

import com.example.proto.Category;
import com.example.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InMemoryCategoryRepository implements CategoryRepository {
  private List<Category> categories = new ArrayList<>(List.of(
      Category.newBuilder().setId(1).setName("Laptops").build(),
      Category.newBuilder().setId(2).setName("Phones").build(),
      Category.newBuilder().setId(3).setName("Food").build(),
      Category.newBuilder().setId(4).setName("Household goods").build()
  ));

  @Override
  public List<Category> getCategories() {
    return categories;
  }

  @Override
  public void addCategory(Category category) {
    categories.add(category);
  }

  @Override
  public void removeCategory(Category category) {
    categories.remove(category);
  }

  @Override
  public Optional<Category> findCategoryById(long id) {
    return categories.stream()
        .filter(category -> category.getId() == id)
        .findFirst();
  }
}

