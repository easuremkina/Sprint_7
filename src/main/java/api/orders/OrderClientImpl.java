package api.orders;

import api.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClientImpl extends Client implements OrderClient{
    private static final String CREATE_ORDER_ENDPOINT = "/orders";
    private static final String TRACK_ORDER_ENDPOINT = CREATE_ORDER_ENDPOINT + "/track/";
    private static final String ACCEPT_ORDER_ENDPOINT = CREATE_ORDER_ENDPOINT + "/accept/";

    @Step("Создание заказа")
    @Override
    public ValidatableResponse createOrder(Order order) {
        return requestSpecification()
                .body(order)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then().log().all();
    }

    @Step("Получить номер заказа по его треку")
    @Override
    public  ValidatableResponse getOrderTrack(int trackId) {
        return requestSpecification()
                .param("t", trackId)
                .when()
                .get(TRACK_ORDER_ENDPOINT)
                .then().log().all();
    }

    @Step("Привязать заказ к курьеру")
    @Override
    public ValidatableResponse acceptOrder(int orderId, int courierId) {
        return requestSpecification()
                .param("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER_ENDPOINT+orderId)
                .then().log().all();
    }

    @Step("Получить список заказов курьера")
    @Override
    public ValidatableResponse courierOrders(int courierId) {
        return requestSpecification()
                .param("courierId", courierId)
                .when()
                .get(CREATE_ORDER_ENDPOINT)
                .then().log().all();
    }
}
