package scootertests;

import apirequests.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.Courier;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.*;

public class TestCreateCourier{

        Courier courier = new Courier("SuperCourier", "777", "Gena");
        CourierRequest courierRequest = new CourierRequest();
    @Before
    public void setUp(){
        courierRequest.setUp();
    }

    @Test
    @DisplayName("Создание курьера(позитив)")
    @Description("Проверка успешного создания курьера")
    public void checkCreateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
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
                .statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без указания логина (негатив)")
    @Description("Проверка невозможности создать курьера без указания логин")
    public void checkCreateCourierWithoutLogin(){
        courierRequest.setCourier(new Courier("","777","Gena"));
        courierRequest.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без указания пароля (негатив)")
    @Description("Проверка невозможности создать курьера без указания пароля")
    public void checkCreateCourierWithoutPassword(){
        courierRequest.setCourier(new Courier("SuperCourier","","Gena"));
        courierRequest.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
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
                .statusCode(SC_CREATED);
    }

    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }
}
