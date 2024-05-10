package api.courier;

import api.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class CourierClientImpl extends Client implements CourierClient {
    private static final String CREATE_USER_ENDPOINT = "/courier";
    private static final String LOGIN_ENDPOINT = CREATE_USER_ENDPOINT + "/login";
    private static final String DELETE_USER_ENDPOINT  = CREATE_USER_ENDPOINT + "/";

    @Step("Создание курьера")
    @Override
    public ValidatableResponse createCourier(Courier courier) {
        return requestSpecification()
                .body(courier)
                .when()
                .post(CREATE_USER_ENDPOINT)
                .then().log().all();
    }

    @Step("Авторизация курьером")
    @Override
    public ValidatableResponse login(Credentials credentials) {
        return requestSpecification()
                .body(credentials)
                .when()
                .post(LOGIN_ENDPOINT)
                .then().log().all();
    }

    @Step("Удаление курьера")
    @Override
    public ValidatableResponse deleteCourierById(Integer id) {
        return requestSpecification()
                .body(Map.of("id", id))
                .when()
                .delete(DELETE_USER_ENDPOINT + id)
                .then().log().all();
    }
}