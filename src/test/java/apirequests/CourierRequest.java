package apirequests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.Courier;

import static io.restassured.RestAssured.given;

public class CourierRequest {
    private Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создать курьера, проверить код ответа")
    public Response createCourier(){
        Response response =
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post ("/api/v1/courier");
        return response;
    }

    @Step("Авторизоваться курьером, получить его id и проверить код ответа")
    public Response loginCourier(){
        Response response =
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post ("/api/v1/courier/login");
        return response;
    }

@Step("Удалить курьера")
    public void deleteCourier(){
        Integer id =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post ("/api/v1/courier/login")
                        .then().extract().body().path("id");
        if (id != null) {
        given()
                .delete ("/api/v1/courier/"+id);}
    }

}
