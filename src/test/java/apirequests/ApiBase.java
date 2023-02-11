package apirequests;

import io.restassured.RestAssured;
import org.junit.Before;

public class ApiBase {
    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

}
