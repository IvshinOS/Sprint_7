package scootertests;

import apirequests.ApiBase;
import apirequests.OrderRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class TestOrderList extends ApiBase {
    OrderRequest orderRequest = new OrderRequest();
    @Test
    @DisplayName("Получение списка заказов(позитив)")
    @Description("Проверка получения списка заказов")
    public void checkCreateOrder(){
        orderRequest.getOrdersRequest()
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
