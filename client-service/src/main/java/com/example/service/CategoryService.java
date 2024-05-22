package com.example.service;

import com.example.domain.dto.CategoryCreateRequest;
import com.example.domain.dto.CategoryEditRequest;
import com.example.proto.Category;
import com.example.proto.CategoryByIdRequest;
import com.example.proto.CategoryServiceGrpc;
import com.example.proto.CategoryUpdateRequest;
import com.example.service.exception.GrpcServerException;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @GrpcClient("grpc-service")
  CategoryServiceGrpc.CategoryServiceBlockingStub synchronousClient;

  public Map<FieldDescriptor, Object> saveCategory(CategoryCreateRequest categoryCreateRequest) {
    Category category = Category.newBuilder()
        .setId(categoryCreateRequest.getId())
        .setName(categoryCreateRequest.getName())
        .build();
    try {
      Category categoryResponse = synchronousClient.createCategory(category);

      return categoryResponse.getAllFields();
    } catch (StatusRuntimeException ex) {
      throw new GrpcServerException(ex.getStatus().getCode(), ex.getMessage());
    }
  }

  public Map<FieldDescriptor, Object> getCategory(long id) {
    CategoryByIdRequest categoryByIdRequest = CategoryByIdRequest.newBuilder()
        .setId(id)
        .build();
    try {
      Category categoryResponse = synchronousClient.getCategory(categoryByIdRequest);

      return categoryResponse.getAllFields();
    } catch (StatusRuntimeException ex) {
      throw new GrpcServerException(ex.getStatus().getCode(), ex.getMessage());
    }
  }

  public Map<FieldDescriptor, Object> updateCategory(long id,
      CategoryEditRequest categoryEditRequest) {
    CategoryUpdateRequest categoryUpdateRequest = CategoryUpdateRequest.newBuilder()
        .setId(id)
        .setName(categoryEditRequest.getName())
        .build();

    try {
      Category categoryResponse = synchronousClient.updateCategory(categoryUpdateRequest);

      return categoryResponse.getAllFields();
    } catch (StatusRuntimeException ex) {
      throw new GrpcServerException(ex.getStatus().getCode(), ex.getMessage());
    }
  }

  public void deleteCategory(long id) {
    CategoryByIdRequest categoryByIdRequest = CategoryByIdRequest.newBuilder()
        .setId(id)
        .build();

    try {
      synchronousClient.deleteCategory(categoryByIdRequest);
    } catch (StatusRuntimeException ex) {
      throw new GrpcServerException(ex.getStatus().getCode(), ex.getMessage());
    }
  }

  public List<Map<FieldDescriptor, Object>> getCategories() {
    List<Map<FieldDescriptor, Object>> categoriesList = new ArrayList<>();
    try {
      Iterator<Category> categories = synchronousClient.getCategories(Empty.newBuilder().build());

      while (categories.hasNext()) {
        categoriesList.add(categories.next().getAllFields());
      }

      return categoriesList;
    } catch (StatusRuntimeException ex) {
      throw new GrpcServerException(ex.getStatus().getCode(), ex.getMessage());
    }
  }
}
