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

    if (categoryExistsById(request.getId())) {
      handleError(responseObserver, Status.ALREADY_EXISTS,
          "Category with ID " + request.getId() + " already exists");
      return;
    }

    if (categoryExistsByName(request.getName())) {
      handleError(responseObserver, Status.ALREADY_EXISTS,
          "Category with name '" + request.getName() + "' already exists");
      return;
    }

    categories.add(Category.newBuilder().setId(request.getId()).setName(request.getName()).build());
    responseObserver.onNext(request);
    responseObserver.onCompleted();
  }

  @Override
  public void getCategory(CategoryByIdRequest request, StreamObserver<Category> responseObserver) {
    findCategoryById(request.getId()).ifPresentOrElse(
        responseObserver::onNext,
        () -> handleError(responseObserver, Status.NOT_FOUND,
            "Category with id = " + request.getId() + " not found"));
  }

  @Override
  public void updateCategory(CategoryUpdateRequest request, StreamObserver<Category> responseObserver) {
    findCategoryById(request.getId()).ifPresentOrElse(
        oldCategory -> {
          Category updatedCategory = Category.newBuilder(oldCategory)
              .setName(request.getName())
              .build();
          replaceCategory(updatedCategory);
          responseObserver.onNext(updatedCategory);
          responseObserver.onCompleted();
        },
        () -> handleError(responseObserver, Status.NOT_FOUND,
            "Category with id = " + request.getId() + " not found"));
  }

  @Override
  public void deleteCategory(CategoryByIdRequest request, StreamObserver<Empty> responseObserver) {
    findCategoryById(request.getId()).ifPresentOrElse(
        category -> {
          TempDB.getCategoriesFromTempDb().remove(category);
          responseObserver.onNext(Empty.newBuilder().build());
          responseObserver.onCompleted();
        },
        () -> handleError(responseObserver, Status.NOT_FOUND,
            "Category with id = " + request.getId() + " not found"));
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

  private boolean categoryExistsById(long id) {
    return TempDB.getCategoriesFromTempDb().stream().anyMatch(category -> category.getId() == id);
  }

  private boolean categoryExistsByName(String name) {
    return TempDB.getCategoriesFromTempDb().stream().anyMatch(category -> category.getName().equals(name));
  }

  private void replaceCategory(Category updatedCategory) {
    List<Category> categories = TempDB.getCategoriesFromTempDb();
    for (int i = 0; i < categories.size(); i++) {
      if (categories.get(i).getId() == updatedCategory.getId()) {
        categories.set(i, updatedCategory);
        break;
      }
    }
  }

  private void handleError(StreamObserver<?> responseObserver, Status status, String description) {
    responseObserver.onError(status.withDescription(description).asRuntimeException());
  }
}

