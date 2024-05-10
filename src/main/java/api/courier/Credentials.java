package api.courier;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.RandomStringUtils;

public class Credentials {
    private String login;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;

    public Credentials() {
    }

    private Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Credentials fromCourier(Courier courier) {
        return new Credentials(courier.getLogin(), courier.getPassword());
    }

    public static Credentials fromCourierWhitFieldLoginIsNull(Courier courier) {
        return new Credentials(null, courier.getPassword());
    }

    public static Credentials fromCourierWhitIncorrectPassword(Courier courier) {
        return new Credentials(courier.getLogin(), RandomStringUtils.randomAlphabetic(10, 15) );
    }
    public static Credentials fromCourierWhithoutFieldPassword(Courier courier) {
        return new Credentials(courier.getLogin(), null );
    }

    public static Credentials newRandom() {
        return new Credentials(RandomStringUtils.randomAlphabetic(5, 15), RandomStringUtils.randomAlphabetic(10, 15) );
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}