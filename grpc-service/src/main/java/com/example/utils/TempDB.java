package com.example.utils;

import com.example.proto.Category;
import java.util.ArrayList;
import java.util.List;

public class TempDB {

  private static List<Category> categories = new ArrayList<>() {
    {
      add(Category.newBuilder().setId(1).setName("Laptops").build());
      add(Category.newBuilder().setId(2).setName("Phones").build());
      add(Category.newBuilder().setId(3).setName("Food").build());
      add(Category.newBuilder().setId(4).setName("Household goods").build());
    }
  };

  public static List<Category> getCategoriesFromTempDb() {
    return categories;
  }
}
