package api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.Test;

public class
CreateCourierTest extends AbstractCourierTest {
    //
    @DisplayName("Успешное создание курьера - в ответе возвращается ok: true")
    @Description("Пункты требований:\n" +
            "     - курьера можно создать;\n" +
            "     - успешный запрос возвращает ok: true.")
    @Test
    public void createCourierSuccessfullyCheckBodyOkTrue() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        createCourierCheck.createdSuccessfullyBodyOkTrue(response);
    }

    @DisplayName("Успешное создание курьера - код ответа - 201")
    @Description("Пункт требований: - запрос возвращает правильный код ответа")
    @Test
    public void createCourierCheckResponseCode() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        createCourierCheck.createdSuccessfullyResponseCode(response);
    }

    @DisplayName("Создание курьера - Нельзя создать двух одинаковых курьеров")
    @Description("Пункты требований:\n" +
            "    - нельзя создать двух одинаковых курьеров;\n" +
            "    - если создать пользователя с логином, который уже есть, возвращается ошибка.")
    @Test
    public void createCourierWithLoginAlreadyExist() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        createCourierCheck.createdSuccessfullyBodyOkTrue(response);

        ValidatableResponse createSecondResponse = client.createCourier(courier);
        createCourierCheck.createCourierWithExistentLogin(createSecondResponse);
    }

    //точно не поняла, что имеется ввиду, возможно имелось ввиду тоже самое что в пункте ниже
    //сделала разные тесты - когда поле есть, значение null и когда вообще поля нет в запросе
    @DisplayName("Создание курьера - Не заполнено обязательное поле login")
    @Description("Пункт требований: - чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @Test
    public void createCourierWithFieldLoginIsNull() {
        Courier courier = Courier.createCourierWithFieldLoginIsNull();
        ValidatableResponse response = client.createCourier(courier);
        createCourierCheck.createCourierWithFieldLoginIsNull(response);
    }

    //решение с @JsonInclude для поля password в классе Courier кажется костыльным
    @DisplayName("Создание курьера - В запросе отсуствует поле password")
    @Description("Пункт требований: - если одного из полей нет, запрос возвращает ошибку")
    @Test
    public void createCourierWhitoutFieldPassword() {
        Courier courier = Courier.createCourierWithoutFieldPassword();
        ValidatableResponse response = client.createCourier(courier);
        createCourierCheck.createCourierWithoutFieldPassword(response);
    }

    @AfterClass
    public static void deleteCourier() {
        for (Courier courier : courierListForDisposal) {
            System.out.println("Удаление курьера Login " + courier.getLogin());
            Credentials creds = Credentials.fromCourier(courier);
            ValidatableResponse loginResponse = client.login(creds);

            int courierId = loginCourierCheck.loggedInSuccessfully(loginResponse);
                    ValidatableResponse deleteCourier = client.deleteCourierById(courierId);
            deleteCourierCheck.deletedSuccesfully(deleteCourier);
        }
    }
}