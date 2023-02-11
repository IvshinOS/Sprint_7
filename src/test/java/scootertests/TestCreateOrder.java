package scootertests;

import apirequests.ApiBase;
import apirequests.OrderRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.Order;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class TestCreateOrder extends ApiBase {
      Order order;

    public TestCreateOrder(Order order) {
        this.order = order;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {new Order("Том","Круз","Кутузовский пр-кт","Кутузовская","+79099995566",2,"2022-09-12","Позвонить за час",new String[]{"BLACK"})},
                {new Order("Джейсон","Стэйтем","Мира пр-кт","Проспект Мира","89199895767",3,"2022-10-30","Позвонить за час",new String[]{"GREY"})},
                {new Order("Daniel","Craig","Лубянка 38","Лубянка","+70070070070",4,"2022-08-01","Позвонить за час",new String[]{"BLACK","GREY"})},
                {new Order("Guy","Ritchie","Новый арбат","Арбатская","89262626123",5,"2022-07-10","Позвонить за час",new String[]{})}
        };
    }

    OrderRequest orderRequest = new OrderRequest();

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void checkCreateOrder(){
        orderRequest.setOrder(order);
        orderRequest.createOrderRequest()
                .then().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
