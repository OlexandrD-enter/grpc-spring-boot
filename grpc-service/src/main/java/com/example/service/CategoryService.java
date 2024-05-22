package com.example.service;

import com.example.proto.Category;
import com.example.proto.CategoryByIdRequest;
import com.example.proto.CategoryServiceGrpc;
import com.example.proto.CategoryUpdateRequest;
import com.example.utils.TempDB;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CategoryService extends CategoryServiceGrpc.CategoryServiceImplBase {

  @Override
  public void createCategory(Category request, StreamObserver<Category> responseObserver) {
    List<Category> categories = TempDB.getCategoriesFromTempDb();

    boolean idExists = categories.stream()
        .anyMatch(category -> category.getId() == request.getId());
    if (idExists) {
      responseObserver.onError(
          Status.ALREADY_EXISTS.withDescription(
                  "Category with ID " + request.getId() + " already exists").asRuntimeException());
      return;
    }

    boolean nameExists = categories.stream()
        .anyMatch(category -> category.getName().equals(request.getName()));
    if (nameExists) {
      responseObserver.onError(
          Status.ALREADY_EXISTS.withDescription(
                  "Category with name '" + request.getName() + "' already exists").asRuntimeException());
      return;
    }

    categories.add(Category.newBuilder().setId(request.getId()).setName(request.getName()).build());

    responseObserver.onNext(request);
    responseObserver.onCompleted();
  }


  @Override
  public void getCategory(CategoryByIdRequest request, StreamObserver<Category> responseObserver) {
    Optional<Category> categoryOptional = findCategoryById(request.getId());
    if (categoryOptional.isPresent()) {
      responseObserver.onNext(categoryOptional.get());
      responseObserver.onCompleted();
    } else {
      responseObserver.onError(
          Status.NOT_FOUND.withDescription("Category with id = " + request.getId() + " not found")
              .asRuntimeException());
    }
  }

  @Override
  public void updateCategory(CategoryUpdateRequest request,
      StreamObserver<Category> responseObserver) {
    Optional<Category> categoryOptional = findCategoryById(request.getId());
    if (categoryOptional.isPresent()) {
      Category oldCategory = categoryOptional.get();

      Category updatedCategory = Category.newBuilder(oldCategory)
          .setName(request.getName())
          .build();

      List<Category> categories = TempDB.getCategoriesFromTempDb();
      for (int i = 0; i < categories.size(); i++) {
        if (categories.get(i).getId() == request.getId()) {
          categories.set(i, updatedCategory);
          break;
        }
      }

      responseObserver.onNext(updatedCategory);
      responseObserver.onCompleted();
    } else {
      responseObserver.onError(
          Status.NOT_FOUND.withDescription("Category with id = " + request.getId() + " not found")
              .asRuntimeException());
    }
  }

  @Override
  public void deleteCategory(CategoryByIdRequest request, StreamObserver<Empty> responseObserver) {
    Optional<Category> categoryOptional = findCategoryById(request.getId());

    if (categoryOptional.isPresent()) {
      Category category = categoryOptional.get();
      TempDB.getCategoriesFromTempDb().remove(category);

      responseObserver.onNext(Empty.newBuilder().build());
      responseObserver.onCompleted();
    } else {
      responseObserver.onError(
          Status.NOT_FOUND.withDescription("Category with id = " + request.getId() + " not found")
              .asRuntimeException());
    }
  }

  @Override
  public void getCategories(Empty request, StreamObserver<Category> responseObserver) {
    TempDB.getCategoriesFromTempDb().forEach(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  private Optional<Category> findCategoryById(long id) {
    return TempDB.getCategoriesFromTempDb().stream()
        .filter(category -> id == category.getId())
        .findFirst();
  }
}
