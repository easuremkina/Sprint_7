package api.orders.checks;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChecks {
    @Step("Проверка успешного создания заказа - тело содержит поле track")
    public int createdSuccessfullyOrderTrack(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("track", notNullValue())
                .extract()
                .path("track");
    }

    @Step("Проверка успешного создания заказа - тело содержит поле track")
    public int getOrderId(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("order.id", notNullValue())
                .extract()
                .path("order.id");
    }

    @Step("Проверка списка заказов курьера")
    public void courierOrderChecks(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders.id", notNullValue());
    }
}
