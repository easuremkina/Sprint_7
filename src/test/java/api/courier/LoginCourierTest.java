package api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.Test;

public class LoginCourierTest extends AbstractCourierTest {

    //TODO вынести создание курьера в предусловия для каждого теста
    @DisplayName("Успешная авторизация курьера")
    @Description("Пункты требований:\n" +
            "     - курьер может авторизоваться\n" +
            "     -  успешный запрос возвращает id")
    @Test
    public void loginCourier() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        //не уверена что нужна проверка создания курьера т к тест про логин
        createCourierCheck.createdSuccessfullyBodyOkTrue(response);

        Credentials creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.login(creds);

        loginCourierCheck.loggedInSuccessfully(loginResponse);
    }

    @DisplayName("Авторизация курьера - не заполнено поле логина")
    @Description("Пункт требований: - для авторизации нужно передать все обязательные поля")
    @Test
    public void loginCourierWithFieldLoginIsNull() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        //не уверена что нужна проверка создания курьера т к тест про логин
        createCourierCheck.createdSuccessfullyBodyOkTrue(response);

        Credentials creds = Credentials.fromCourierWhitFieldLoginIsNull(courier);
        ValidatableResponse loginResponse = client.login(creds);

        loginCourierCheck.loginWithFieldLoginIsNull(loginResponse);
    }

    @DisplayName("Авторизация курьера - поле password заполнено неверными данными")
    @Description("Пункт требований: - система вернёт ошибку, если неправильно указать логин или пароль")
    @Test
    public void loginCourierWhitIncorrectPassword() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        //не уверена что нужна проверка создания курьера т к тест про логин
        createCourierCheck.createdSuccessfullyBodyOkTrue(response);

        Credentials creds = Credentials.fromCourierWhitIncorrectPassword(courier);
        ValidatableResponse loginResponse = client.login(creds);

        loginCourierCheck.loginWithIncorrectPassword(loginResponse);
    }

    //
    @DisplayName("Авторизация курьера - поле password отсутствует в запросе")
    @Description("Пункт требований: - если какого-то поля нет, запрос возвращает ошибку")
    @Test
    public void loginCourierWhithoutFieldPassword() {
        Courier courier = Courier.createRandom();
        ValidatableResponse response = client.createCourier(courier);
        courierListForDisposal.add(courier);
        //не уверена что нужна проверка создания курьера т к тест про логин
        createCourierCheck.createdSuccessfullyBodyOkTrue(response);

        Credentials creds = Credentials.fromCourierWhithoutFieldPassword(courier);
        ValidatableResponse loginResponse = client.login(creds);

        loginCourierCheck.loginWithoutFieldPassword(loginResponse);
    }

    @DisplayName("Авторизация курьера под несуществующим пользователем")
    @Description("Пункт требований: - если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    @Test
    public void loginCourierWhithNonexistentUser() {
        //по хорошему надо сначала убедится, что пользователь действительно не существует в системе
        Credentials creds = Credentials.newRandom();
        ValidatableResponse loginResponse = client.login(creds);

        loginCourierCheck.loginWithNonexistentUser(loginResponse);
    }

    @AfterClass
    public static void deleteCourier() {
        for (Courier courier : courierListForDisposal) {
            Credentials creds = Credentials.fromCourier(courier);
            ValidatableResponse loginResponse = client.login(creds);
            int courierId = loginCourierCheck.loggedInSuccessfully(loginResponse);

            ValidatableResponse deleteCourier = client.deleteCourierById(courierId);
            deleteCourierCheck.deletedSuccesfully(deleteCourier);
        }
    }
}
