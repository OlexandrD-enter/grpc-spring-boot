package com.example.service;

import com.example.proto.Category;
import com.example.proto.CategoryServiceGrpc;
import com.example.utils.TempDB;
import io.grpc.stub.StreamObserver;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CategoryService extends CategoryServiceGrpc.CategoryServiceImplBase {

  @Override
  public void createCategory(Category request, StreamObserver<Category> responseObserver) {

    List<Category> categoriesFromTempDb = TempDB.getCategoriesFromTempDb();

    categoriesFromTempDb.add(Category.newBuilder().setId(request.getId()).setName(request.getName()).build());

    System.out.println(categoriesFromTempDb);
    responseObserver.onNext(request);
    responseObserver.onCompleted();
  }
}
