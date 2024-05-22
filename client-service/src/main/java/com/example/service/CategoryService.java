package com.example.service;

import com.example.domain.dto.CategoryCreateRequest;
import com.example.proto.Category;
import com.example.proto.CategoryServiceGrpc;
import com.google.protobuf.Descriptors.FieldDescriptor;
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

    Category categoryResponse = synchronousClient.createCategory(category);

    return categoryResponse.getAllFields();
  }
}
