package com.example.api.controller;

import com.example.domain.dto.CategoryCreateRequest;
import com.example.domain.dto.CategoryEditRequest;
import com.example.service.CategoryService;
import com.google.protobuf.Descriptors.FieldDescriptor;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping()
  public ResponseEntity<Map<FieldDescriptor, Object>> createCategory(
      @RequestBody CategoryCreateRequest category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(category));
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<Map<FieldDescriptor, Object>> getCategory(@PathVariable Long categoryId) {
    return ResponseEntity.ok(categoryService.getCategory(categoryId));
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<Map<FieldDescriptor, Object>> updateCategory(@PathVariable Long categoryId,
      @RequestBody CategoryEditRequest category) {
    return ResponseEntity.ok(categoryService.updateCategory(categoryId, category));
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  public ResponseEntity<List<Map<FieldDescriptor, Object>>> getAllCategories() {
    return ResponseEntity.ok(categoryService.getCategories());
  }
}
