package com.example.controller;

import com.example.domain.dto.CategoryCreateRequest;
import com.example.service.CategoryService;
import com.google.protobuf.Descriptors.FieldDescriptor;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping()
  public Map<FieldDescriptor, Object> createCategory(@RequestBody CategoryCreateRequest category) {
    return categoryService.saveCategory(category);
  }
}
