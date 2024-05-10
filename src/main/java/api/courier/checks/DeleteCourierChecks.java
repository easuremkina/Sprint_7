package api.courier.checks;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;

public class DeleteCourierChecks {
    @Step("Удаление курьера")
    public void deletedSuccesfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("ok", equalTo(true));
    }
}
