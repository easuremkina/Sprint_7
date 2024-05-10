package api.courier.checks;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;

public class LoginCourierChecks {
    @Step("Проверка успешной авторизации курьера")
    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        //по документации id - строка, но в реализации - числовой тип
        //на реальном проекте нужно уточнить, где именно ошибка
        //и указать на необходимость исправления документации/кода
        //пока оставила числом
        //кажется что не очень хорошо, что метод проверки возвращает id, как будто в другои хелпере это должно быть
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("id", not(equalTo(0)))
                .and()
                .body("id", is(notNullValue()))
                .extract()
                .path("id");
        //не уверена что возвращать id в check это хорошо
    }

    @Step("Проверка авторизации курьера - не заполнено поле password")
    public void loginWithFieldLoginIsNull(ValidatableResponse loginResponse) {
        //по спецификации api написала Ожидаемый результат как для зарпоса "Запрос без логина или пароля:"
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Проверка авторизации курьера - передано неверное значение поля password")
    public void loginWithIncorrectPassword(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Проверка авторизации курьера - не передано поле password")
    public void loginWithoutFieldPassword(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Проверка авторизации курьера - если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void loginWithNonexistentUser(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
