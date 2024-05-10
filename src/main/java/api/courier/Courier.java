package api.courier;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    private String login;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String firstName;

    public Courier() {
    }

    private Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    private Courier(String login) {
        this.login = login;
    }

    public static Courier createRandom() {
        return new Courier(RandomStringUtils.randomAlphabetic(5, 15), RandomStringUtils.randomAlphabetic(10, 15), "Testuser");
    }

    public static Courier createCourierWithFieldLoginIsNull() {
        return new Courier(null, RandomStringUtils.randomAlphabetic(10, 15), "Testuser");
    }

    //как сделать вообще без передачи поля
    public static Courier createCourierWithoutFieldPassword() {
        return new Courier(RandomStringUtils.randomAlphabetic(5, 15));
    }

    public String getLogin() {
        return login;
    }

    /*public void setLogin(String login) {
        this.login = login;
    }*/

    public String getPassword() {
        return password;
    }

    /*public void setPassword(String password) {
        this.password = Optional.of(password);
    }*/

    public String getFirstName() {
        return firstName;
    }

    /*public void setFirstName(String firstName) {
        this.firstName = Optional.of(firstName);
    }*/
}