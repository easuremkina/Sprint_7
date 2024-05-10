package api.order;

import api.courier.Courier;
import api.orders.ScooterColor;
import api.courier.Credentials;
import api.orders.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class CourierOrdersTest extends AbstractOrderTest {
    //Проверь, что в тело ответа возвращается список заказов.
    //Смоук тест, что список заказов возвращается не пустой
    //По ответу видно что есть ошибка, возвращаются лишние заказы,
    // на полноту и корректность всего списка заказов проверки не писала
    @DisplayName("Получение списка заказов курьера - смоук тест - список содержит хотя бы один заказ")
    @Description("Проверка пункта требований: -  в тело ответа возвращается список заказов")
    @Test
    public void courierOrdersTest() {
        //TODO вынести/разбить все предусловия до вызова ручки получения списка заказов на отдельные @Step
        //проверки на корректность выполнения предшагов не стала добавлять в текущей реализации
        //так же по хорошему на каждый шаг нужно добавить assume, т е  если создание предусловий провалилось
        //то пометить тест как игнорируемый

        //Предварительные шаги
        //создать курьера
        Courier courier = Courier.createRandom();
        ValidatableResponse responseCreateCourier = client.createCourier(courier);
        courierListForDisposal.add(courier);

        //Получить id курьера
        Credentials creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.login(creds);
        int courierId = loginCourierCheck.loggedInSuccessfully(loginResponse);

        //создать заказ
        ValidatableResponse responseCreateOrder = orderClient.createOrder(new Order(new String[] {ScooterColor.BLACK.getMode()}));
        int trackId = orderCheck.createdSuccessfullyOrderTrack(responseCreateOrder);

        //Получить  данные по заказу по его треку и сохранить id заказа
        ValidatableResponse orderTrackResponse = orderClient.getOrderTrack(trackId);
        int orderId = orderCheck.getOrderId(orderTrackResponse);

        //Привязать заказ к курьеру
        ValidatableResponse asseptOrderResponse = orderClient.acceptOrder(orderId, courierId);

        //Сама суть теста
        //Получить список заказов курьера
        ValidatableResponse responseCourierOrders = orderClient.courierOrders(courierId);
        orderCheck.courierOrderChecks(responseCourierOrders);
    }
}
