package scootertests;

import apirequests.ApiBase;
import apirequests.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.Courier;

import static org.hamcrest.Matchers.*;

public class TestLoginCourier extends ApiBase {
    CourierRequest courierRequest = new CourierRequest();
    Courier courier = new Courier ("SuperCourier","123");
    @Test
    @DisplayName("Авторизаци курьера в системе(успех)")
    @Description("Проверка авторизации с корректными логином и паролем")
    public void checkLoginCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация без логина(негатив)")
    @Description("Проверка невозможности авторизоваться без указания логина")
    public void checkLoginWithoutLogin(){
        courierRequest.setCourier(new Courier("","123"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация без пароля(негатив)")
    @Description("Проверка невозможности авторизоваться без указания пароля")
    public void checkLoginWithoutPassword(){
        courierRequest.setCourier(new Courier("SuperCourier",""));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация несуществующим курьером(негатив)")
    @Description("Проверка авторизации ранее не созданным курьром")
    public void checkLoginNonExisting(){
        courierRequest.setCourier(courier);
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного логина (негатив)")
    @Description("Проверка авторизации с вводом неверного логина")
    public void checkLoginIncorrectLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("SuperCourierr","123"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courierRequest.setCourier(courier);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного пароля (негатив)")
    @Description("Проверка авторизации с вводом неверного пароля")
    public void checkLoginIncorrectPassword() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("SuperCourier","1234"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courierRequest.setCourier(courier);
    }
    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }

}
