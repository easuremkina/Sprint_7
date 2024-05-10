package api.orders;

import io.restassured.response.ValidatableResponse;

public interface OrderClient {
    ValidatableResponse createOrder(Order order);
    ValidatableResponse acceptOrder(int orderId, int courierId);
    ValidatableResponse getOrderTrack(int trackId);
    ValidatableResponse courierOrders(int courierId);
}
