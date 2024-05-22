package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.proto.Category;
import com.example.proto.CategoryByIdRequest;
import com.example.proto.CategoryServiceGrpc;
import com.example.proto.CategoryUpdateRequest;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest(properties = {
    "grpc.server.inProcessName=test", // Enable inProcess server
    "grpc.server.port=-1", // Disable external server
    "grpc.client.categoryService.address=in-process:test"
    // Configure the client to connect to the inProcess server
})
@ImportAutoConfiguration({
    GrpcServerAutoConfiguration.class, // Create required server beans
    GrpcServerFactoryAutoConfiguration.class, // Select server implementation
    GrpcClientAutoConfiguration.class}) // Support @GrpcClient annotation
@SpringJUnitConfig(classes = CategoryService.class)
@DirtiesContext // Ensures that the grpc-server is properly shutdown after each test
// Avoids "port already in use" during tests
public class CategoryServiceTest {

  @MockBean
  private CategoryRepository categoryRepository;

  @GrpcClient("categoryService")
  private CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub;

  private List<Category> mockCategories;

  @BeforeEach
  public void setUp() {
    mockCategories = Arrays.asList(
        Category.newBuilder().setId(1).setName("Laptops").build(),
        Category.newBuilder().setId(2).setName("Phones").build(),
        Category.newBuilder().setId(3).setName("Food").build(),
        Category.newBuilder().setId(4).setName("Household goods").build()
    );

    when(categoryRepository.getCategories()).thenReturn(mockCategories);
    when(categoryRepository.findCategoryById(1)).thenReturn(
        Optional.ofNullable(mockCategories.get(0)));
    when(categoryRepository.findCategoryById(5)).thenReturn(Optional.empty());
  }

  @Test
  public void createCategory_WhenCategoryUniqueByIdAndName_Success() {
    long uniqueId = 5;
    String uniqueName = "Test Category";
    Category request = Category.newBuilder()
        .setId(uniqueId)
        .setName(uniqueName)
        .build();

    Category response = categoryServiceBlockingStub.createCategory(request);

    assertEquals(response, request);
  }

  @Test
  public void createCategory_WhenCategoryExistById_ThrowsStatusRuntimeException() {
    long notUniqueId = 1;
    String uniqueName = "Test Category";
    Category request = Category.newBuilder()
        .setId(notUniqueId)
        .setName(uniqueName)
        .build();

    assertThrows(StatusRuntimeException.class,
        () -> categoryServiceBlockingStub.createCategory(request));
  }

  @Test
  public void createCategory_WhenCategoryExistByName_ThrowsStatusRuntimeException() {
    long uniqueId = 1;
    String notUniqueName = "Laptops";
    Category request = Category.newBuilder()
        .setId(uniqueId)
        .setName(notUniqueName)
        .build();

    assertThrows(StatusRuntimeException.class,
        () -> categoryServiceBlockingStub.createCategory(request));
  }

  @Test
  public void getCategory_WhenCategoryExist_Success() {
    long existId = 1;
    CategoryByIdRequest request = CategoryByIdRequest.newBuilder()
        .setId(existId)
        .build();

    Category response = categoryServiceBlockingStub.getCategory(request);

    assertNotNull(response);
  }

  @Test
  public void getCategory_WhenCategoryNotExist_ThrowsStatusRuntimeException() {
    long notExistId = 5;
    CategoryByIdRequest request = CategoryByIdRequest.newBuilder()
        .setId(notExistId)
        .build();

    assertThrows(StatusRuntimeException.class,
        () -> categoryServiceBlockingStub.getCategory(request));
  }

  @Test
  public void updateCategory_WhenCategoryExist_Success() {
    long existId = 1;
    CategoryUpdateRequest request = CategoryUpdateRequest.newBuilder()
        .setId(existId)
        .setName("Updated Category")
        .build();

    Category response = categoryServiceBlockingStub.updateCategory(request);

    assertNotNull(response);
    assertEquals(response.getName(), "Updated Category");
  }

  @Test
  public void updateCategory_WhenCategoryNotExist_ThrowsStatusRuntimeException() {
    long notExistId = 5;
    CategoryUpdateRequest request = CategoryUpdateRequest.newBuilder()
        .setId(notExistId)
        .setName("Updated Category")
        .build();

    assertThrows(StatusRuntimeException.class,
        () -> categoryServiceBlockingStub.updateCategory(request));
  }

  @Test
  public void deleteCategory_WhenCategoryExist_Success() {
    long existId = 1;
    CategoryByIdRequest request = CategoryByIdRequest.newBuilder()
        .setId(existId)
        .build();

    Empty response = categoryServiceBlockingStub.deleteCategory(request);

    assertNotNull(response);
  }

  @Test
  public void deleteCategory_WhenCategoryExist_ThrowsStatusRuntimeException() {
    long notExistId = 5;
    CategoryByIdRequest request = CategoryByIdRequest.newBuilder()
        .setId(notExistId)
        .build();

    assertThrows(StatusRuntimeException.class,
        () -> categoryServiceBlockingStub.deleteCategory(request));
  }

  @Test
  public void getCategoriesTest() {
    Empty request = Empty.newBuilder().build();

    List<Category> categories = new ArrayList<>();

    categoryServiceBlockingStub.getCategories(request).forEachRemaining(categories::add);

    assertNotNull(categories);
    assertEquals(4, categories.size());
  }
}