package api.order;

import api.orders.Order;
import api.orders.ScooterColor;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderWithDifferentScooterColorTest extends AbstractOrderTest{
    private final String[] color;

    public CreateOrderWithDifferentScooterColorTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters()
    //для читаемости названий тестов в allure видимо нужно писать что-то отдельное
    //не получилось быстро разобраться как сделать
    public static Object[][] getColor() {
        return new Object[][]{
                {new String[] {ScooterColor.BLACK.getMode()}},
                {new String[] {ScooterColor.GREY.getMode()}},
                {new String[] {ScooterColor.GREY.getMode(), ScooterColor.BLACK.getMode()}},
                {new String[] {}}, //пустой массив в json выглядит обычно как [], не [null]
        };
    }

    @DisplayName("Создание заказа c разными цветами самоката")
    @Description("Проверка пункта требований: \n" +
            "- можно указать один из цветов — BLACK или GREY;\n" +
            "- можно указать оба цвета;\n" +
            "- можно совсем не указывать цвет;\n" +
            "- тело ответа содержит track.")
    @Test
    public void createOrder() {
        ValidatableResponse response = orderClient.createOrder(new Order(color));
        orderCheck.createdSuccessfullyOrderTrack(response);
    }
}
