package apirequests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.Order;

import static io.restassured.RestAssured.given;

public class OrderRequest {
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ, проверить код ответа и номер заказа(track)")
    public Response createOrderRequest(){
        Response response =
        given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post ("/api/v1/orders");
        return response;
    }

    @Step("Получить список заказов, проверить что он не пустой и код ответа")
    public Response getOrdersRequest(){
        Response response =
        given()
                .get ("/api/v1/orders");
        return  response;
    }
}
