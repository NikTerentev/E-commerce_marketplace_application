package org.example.lab_1.controller;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.lab_1.daos.*;
import org.example.lab_1.exceptions.NotFoundException;
import org.example.lab_1.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Основной контроллер", description = "Контроллер с эндпоинтами")
public class TestController {

    public String convertToJson(Object data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return json;
    }

    @Operation(
            summary = "Получение всех товаров",
            description = "Позволяет получить информацию о всех товарах в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)))
            }
    )
    @GetMapping("products")
    public String getProducts() {
        List<Product> products = ProductDAO.getAllProducts();
        return convertToJson(products);
    }

    @Operation(
            summary = "Получение всех категорий",
            description = "Позволяет получить информацию о всех категориях в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @GetMapping("categories")
    public String getCategories() {
        List<Category> categories = CategoryDAO.getAllCategories();
        return convertToJson(categories);
    }

    @Operation(
            summary = "Получение всех характеристик",
            description = "Позволяет получить информацию о всех характеристиках в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Characteristics.class)))
            }
    )
    @GetMapping("characteristics")
    public String getCharacteristics() {
        List<Characteristics> characteristics = CharacteristicsDAO.getAllCharacteristics();
        return convertToJson(characteristics);
    }

    @Operation(
            summary = "Получение всех заказов",
            description = "Позволяет получить информацию о всех заказах в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)))
            }
    )
    @GetMapping("orders")
    public String getOrders() {
        List<Order> orders = OrderDAO.getAllOrders();
        return convertToJson(orders);
    }

    @Operation(
            summary = "Получение всех пользователей",
            description = "Позволяет получить информацию о всех пользователях в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Characteristics.class)))
            }
    )
    @GetMapping("users")
    public String getUsers() {
        List<User> users = UserDAO.getAllUsers();
        return convertToJson(users);
    }

    @Operation(
            summary = "Получение одного товара",
            description = "Позволяет получить информацию об одном товаре в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)))
            }
    )
    @GetMapping("products/{id}")
    public String getOneProduct(@PathVariable @Parameter(description = "Идентификатор товара") String id) {
        try {
            Product product = ProductDAO.getSingleProduct(Integer.parseInt(id));
            return convertToJson(product);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Получение одной категории",
            description = "Позволяет получить информацию об одной категории в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @GetMapping("categories/{id}")
    public String getOneCategory(@PathVariable @Parameter(description = "Идентификатор категории") String id) {
        try {
            Category category = CategoryDAO.getSingleCategory(Integer.parseInt(id));
            return convertToJson(category);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Получение одной характеристики",
            description = "Позволяет получить информацию об одной характеристике в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Characteristics.class)))
            }
    )
    @GetMapping("characteristics/{id}")
    public String getOneCharacteristics(@PathVariable @Parameter(description = "Идентификатор характеристики") String id) {
        try {
            Characteristics characteristics = CharacteristicsDAO.getSingleCharacteristics(Integer.parseInt(id));
            return convertToJson(characteristics);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Получение одного заказа",
            description = "Позволяет получить информацию об одном заказе в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)))
            }
    )
    @GetMapping("orders/{id}")
    public String getOneOrder(@PathVariable @Parameter(description = "Идентификатор заказа") String id) {
        try {
            Order order = OrderDAO.getSingleOrder(Integer.parseInt(id));
            return convertToJson(order);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Получение одного пользователя",
            description = "Позволяет получить информацию об одном пользователе в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))
            }
    )
    @GetMapping("users/{id}")
    public String getOneUser(@PathVariable @Parameter(description = "Идентификатор пользователя") String id) {
        try {
            User user = UserDAO.getSingleUser(Integer.parseInt(id));
            return convertToJson(user);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Создание товара",
            description = "Позволяет добавить новый товар, возвращает добавленный товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)))
            }
    )
    @PostMapping("products")
    public String createProduct(@RequestBody Product product) {
        ProductDAO.createProduct(product);
        return convertToJson(product);
    }

    @Operation(
            summary = "Создание категории",
            description = "Позволяет добавить новую категорию, возвращает добавленную категорию в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @PostMapping("categories")
    public String createCategory(@RequestBody Category category) {
        CategoryDAO.createCategory(category);
        return convertToJson(category);
    }


    @Operation(
            summary = "Создание характеристик",
            description = "Позволяет добавить новые характеристики, возвращает добавленные характеристики в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @PostMapping("characteristics")
    public String createCharacteristics(@RequestBody Characteristics characteristics) {
        CharacteristicsDAO.createCharacteristics(characteristics);
        return convertToJson(characteristics);
    }

    @Operation(
            summary = "Создание заказа",
            description = "Позволяет добавить новый заказ, возвращает добавленный заказ в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @PostMapping("orders")
    public String createOrder(@RequestBody Order order) {
        OrderDAO.createOrder(order);
        return convertToJson(order);
    }

    @Operation(
            summary = "Создание пользователя",
            description = "Позволяет добавить нового пользователя, возвращает добавленного пользователя в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @PostMapping("users")
    public String createUser(@RequestBody User user) {
        UserDAO.createUser(user);
        return convertToJson(user);
    }

    @Operation(
            summary = "Удаление товара",
            description = "Позволяет удалить товар"
    )
    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable @Parameter(description = "Идентификатор товара") String id) {
        try {
            Product product = ProductDAO.getSingleProduct(Integer.parseInt(id));
            ProductDAO.deleteProduct(product);
        } catch (NumberFormatException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Удаление категории",
            description = "Позволяет удалить категории"
    )
    @DeleteMapping("categories/{id}")
    public void deleteCategory(@PathVariable @Parameter(description = "Идентификатор категории") String id) {
        try {
            Category category = CategoryDAO.getSingleCategory(Integer.parseInt(id));
            CategoryDAO.deleteCategory(category);
        } catch (NumberFormatException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Удаление характеристики",
            description = "Позволяет удалить характеристики"
    )
    @DeleteMapping("characteristics/{id}")
    public void deleteCharacteristics(@PathVariable @Parameter(description = "Идентификатор характеристики") String id) {
        try {
            Characteristics characteristics = CharacteristicsDAO.getSingleCharacteristics(Integer.parseInt(id));
            CharacteristicsDAO.deleteCharacteristics(characteristics);
        } catch (NumberFormatException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Удаление заказа",
            description = "Позволяет удалить заказ"
    )
    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable @Parameter(description = "Идентификатор заказа") String id) {
        try {
            Order order = OrderDAO.getSingleOrder(Integer.parseInt(id));
            OrderDAO.deleteOrder(order);
        } catch (NumberFormatException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя"
    )
    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable @Parameter(description = "Идентификатор пользователя") String id) {
        try {
            User user = UserDAO.getSingleUser(Integer.parseInt(id));
            UserDAO.deleteUser(user);
        } catch (NumberFormatException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Обновление товара",
            description = "Позволяет обновить данные конкретного товара, возвращает товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)))
            }
    )
    @PutMapping("products/{id}")
    public String updateProduct(@PathVariable @Parameter(description = "Идентификатор продукта") String id, @RequestBody Product product) {
        try {
            Product updatedProduct = ProductDAO.updateProduct(Integer.parseInt(id), product);
            return convertToJson(updatedProduct);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Обновление категории",
            description = "Позволяет обновить данные конкретной категории, возвращает товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @PutMapping("categories/{id}")
    public String updateCategory(@PathVariable @Parameter(description = "Идентификатор продукта") String id, @RequestBody Category category) {
        try {
            Category updatedCategory = CategoryDAO.updateCategory(Integer.parseInt(id), category);
            return convertToJson(updatedCategory);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Обновление характеристик",
            description = "Позволяет обновить данные конкретных характеристик, возвращает товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Characteristics.class)))
            }
    )
    @PutMapping("characteristics/{id}")
    public String updateCharacteristics(@PathVariable @Parameter(description = "Идентификатор характеристик") String id, @RequestBody Characteristics characteristics) {
        try {
            Characteristics updatedCharacteristics = CharacteristicsDAO.updateCharacteristics(Integer.parseInt(id), characteristics);
            return convertToJson(updatedCharacteristics);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Обновление заказа",
            description = "Позволяет обновить данные конкретного заказа, возвращает товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)))
            }
    )
    @PutMapping("orders/{id}")
    public String updateOrder(@PathVariable @Parameter(description = "Идентификатор заказа") String id, @RequestBody Order order) {
        try {
            Order updatedOrder = OrderDAO.updateOrder(Integer.parseInt(id), order);
            return convertToJson(updatedOrder);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Обновление пользователя",
            description = "Позволяет обновить данные конкретного пользователя, возвращает товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))
            }
    )
    @PutMapping("users/{id}")
    public String updateUser(@PathVariable @Parameter(description = "Идентификатор пользователя") String id, @RequestBody User user) {
        try {
            User updatedUser = UserDAO.updateUser(Integer.parseInt(id), user);
            return convertToJson(updatedUser);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Частичное обновление товара",
            description = "Позволяет частично обновить данные конкретного товара, возвращает товар в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)))
            }
    )
    @PatchMapping("products/{id}")
    public String partialUpdateProduct(@PathVariable @Parameter(description = "Идентификатор товара") String id, @RequestBody Product product) {
        try {
            Product updatedProduct = ProductDAO.partialUpdateProduct(Integer.parseInt(id), product);
            return convertToJson(updatedProduct);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Частичное обновление категории",
            description = "Позволяет частично обновить данные конкретной категории, возвращает категорию в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class)))
            }
    )
    @PatchMapping("categories/{id}")
    public String partialUpdateCategory(@PathVariable @Parameter(description = "Идентификатор категории") String id, @RequestBody Category category) {
        try {
            Category updatedCategory = CategoryDAO.partialUpdateCategory(Integer.parseInt(id), category);
            return convertToJson(updatedCategory);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Частичное обновление характеристик",
            description = "Позволяет частично обновить данные конкретных характеристик, возвращает характеристики в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Characteristics.class)))
            }
    )
    @PatchMapping("characteristics/{id}")
    public String partialUpdateCharacteristics(@PathVariable @Parameter(description = "Идентификатор характеристик") String id, @RequestBody Characteristics characteristics) {
        try {
            Characteristics updatedCharacteristics = CharacteristicsDAO.partialUpdateCharacteristics(Integer.parseInt(id), characteristics);
            return convertToJson(updatedCharacteristics);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Частичное обновление заказа",
            description = "Позволяет частично обновить данные конкретного заказа, возвращает заказ в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)))
            }
    )
    @PatchMapping("orders/{id}")
    public String partialUpdateOrder(@PathVariable @Parameter(description = "Идентификатор заказа") String id, @RequestBody Order order) {
        try {
            Order updatedOrder = OrderDAO.partialUpdateOrder(Integer.parseInt(id), order);
            return convertToJson(updatedOrder);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Частичное обновление пользователя",
            description = "Позволяет частично обновить данные конкретного пользователя, возвращает пользователя в виде JSON",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))
            }
    )
    @PatchMapping("users/{id}")
    public String partialUpdateUser(@PathVariable @Parameter(description = "Идентификатор пользователя") String id, @RequestBody User user) {
        try {
            User updatedUser = UserDAO.partialUpdateUser(Integer.parseInt(id), user);
            return convertToJson(updatedUser);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new NotFoundException();
        }
    }

    @Operation(
            summary = "Получение доступных HTTP методов для /products",
            description = "Возвращает список доступных HTTP методов для эндпоинта /products"
    )
    @RequestMapping(method = RequestMethod.OPTIONS, value = "products")
    public ResponseEntity<String> optionsProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение доступных HTTP методов для /categories",
            description = "Возвращает список доступных HTTP методов для эндпоинта /categories"
    )
    @RequestMapping(method = RequestMethod.OPTIONS, value = "categories")
    public ResponseEntity<String> optionsCategory() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение доступных HTTP методов для /characteristics",
            description = "Возвращает список доступных HTTP методов для эндпоинта /characteristics"
    )
    @RequestMapping(method = RequestMethod.OPTIONS, value = "characteristics")
    public ResponseEntity<String> optionsCharacteristics() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение доступных HTTP методов для /orders",
            description = "Возвращает список доступных HTTP методов для эндпоинта /orders"
    )
    @RequestMapping(method = RequestMethod.OPTIONS, value = "orders")
    public ResponseEntity<String> optionsOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение доступных HTTP методов для /users",
            description = "Возвращает список доступных HTTP методов для эндпоинта /users"
    )
    @RequestMapping(method = RequestMethod.OPTIONS, value = "users")
    public ResponseEntity<String> optionsUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение количества товаров",
            description = "Возвращает количество товаров в базе данных"
    )
    @RequestMapping(method = RequestMethod.HEAD, value = "products")
    public ResponseEntity<String> headProduct() {
        List<Product> products = ProductDAO.getAllProducts();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number-Of-Products", String.valueOf(products.size()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение количества категорий",
            description = "Возвращает количество категорий в базе данных"
    )
    @RequestMapping(method = RequestMethod.HEAD, value = "categories")
    public ResponseEntity<String> headCategories() {
        List<Category> categories = CategoryDAO.getAllCategories();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number-Of-Categories", String.valueOf(categories.size()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение количества характеристик",
            description = "Возвращает количество характеристик в базе данных"
    )
    @RequestMapping(method = RequestMethod.HEAD, value = "characteristics")
    public ResponseEntity<String> headCharacteristics() {
        List<Characteristics> characteristics = CharacteristicsDAO.getAllCharacteristics();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number-Of-Characteristics", String.valueOf(characteristics.size()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение количества заказов",
            description = "Возвращает количество заказов в базе данных"
    )
    @RequestMapping(method = RequestMethod.HEAD, value = "orders")
    public ResponseEntity<String> headOrders() {
        List<Order> orders = OrderDAO.getAllOrders();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number-Of-Orders", String.valueOf(orders.size()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение количества пользователей",
            description = "Возвращает количество пользователей в базе данных"
    )
    @RequestMapping(method = RequestMethod.HEAD, value = "users")
    public ResponseEntity<String> headUsers() {
        List<User> users = UserDAO.getAllUsers();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number-Of-Users", String.valueOf(users.size()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}

