package api.courier;

import api.courier.checks.CreateCourierChecks;
import api.courier.checks.LoginCourierChecks;
import api.courier.checks.DeleteCourierChecks;

import java.util.ArrayList;

public class AbstractCourierTest {
    protected static final CourierClientImpl client = new CourierClientImpl();
    protected static final CreateCourierChecks createCourierCheck = new CreateCourierChecks();
    protected static final LoginCourierChecks loginCourierCheck = new LoginCourierChecks();
    protected static final DeleteCourierChecks deleteCourierCheck = new DeleteCourierChecks();
    protected static final ArrayList<Courier> courierListForDisposal = new ArrayList<>();
}
