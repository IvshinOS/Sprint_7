package scootertests;

import apirequests.ApiBase;
import apirequests.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.Courier;

import static org.hamcrest.Matchers.*;

public class TestCreateCourier extends ApiBase {

        Courier courier = new Courier("SuperCourier", "777", "Gena");
        CourierRequest courierRequest = new CourierRequest();
    @Test
    @DisplayName("Создание курьера(позитив)")
    @Description("Проверка успешного создания курьера")
    public void checkCreateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Создания уже существующего курьера(негатив)")
    @Description("Проверка ограничения на создание дубликата курьера")
    public void checkCreateDuplicateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Создание курьера без указания логина (негатив)")
    @Description("Проверка невозможности создать курьера без указания логин")
    public void checkCreateCourierWithoutLogin(){
        courierRequest.setCourier(new Courier("","777","Gena"));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без указания пароля (негатив)")
    @Description("Проверка невозможности создать курьера без указания пароля")
    public void checkCreateCourierWithoutPassword(){
        courierRequest.setCourier(new Courier("SuperCourier","","Gena"));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без указания имени (позитив)")
    @Description("Проверка возможности создать курьера без имени")
    public void checkCreateCourierWithoutName() {
        courierRequest.setCourier(new Courier("SuperCourier", "777", ""));
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(201);
    }

    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }
}
