package com.example.utils;

import com.example.proto.Category;
import java.util.ArrayList;
import java.util.List;

public class TempDB {

  public static List<Category> getCategoriesFromTempDb() {
    return new ArrayList<>() {
      {
        add(Category.newBuilder().setId(1).setName("Laptops").build());
        add(Category.newBuilder().setId(2).setName("Phones").build());
        add(Category.newBuilder().setId(3).setName("Food").build());
        add(Category.newBuilder().setId(4).setName("Household goods").build());
      }
    };
  }
}
