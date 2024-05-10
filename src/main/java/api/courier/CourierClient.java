package api.courier;

import io.restassured.response.ValidatableResponse;

public interface CourierClient {
    ValidatableResponse createCourier(Courier courier);
    ValidatableResponse login(Credentials credentials);
    ValidatableResponse deleteCourierById(Integer id);
}
