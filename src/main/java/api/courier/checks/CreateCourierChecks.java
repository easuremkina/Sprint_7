package api.courier.checks;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierChecks {

    @Step("Проверка успешного создания нового курьера - тело содержит поле ok: true")
    public void createdSuccessfullyBodyOkTrue(ValidatableResponse response) {
        response
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Step("Проверка успешного создания нового курьера - код ответа")
    public void createdSuccessfullyResponseCode(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);
    }
    // пример удалить List<Courier> list = response.extract().jsonPath().getList("list", Courier.class)

    //проверки на код и тело объединила в данном случае, но можно/лучше выделить отдельно
    @Step("Проверка ошибки создания курьера без заполнения обязательного поля Login")
    public void createCourierWithFieldLoginIsNull(ValidatableResponse response){
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Проверка ошибки создания курьера без поля Password")
    public  void createCourierWithoutFieldPassword(ValidatableResponse response){
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Проверка ошибки создания курьера с существующим в системе логином")
    public void createCourierWithExistentLogin(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        //текст ошибки в документации и коде имеет различия! взяла за актуальный вариант из реализации
        //на реальном проекте нужно сначала инициировать акутализацию кода/документации
    }
}