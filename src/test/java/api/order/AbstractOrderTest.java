package api.order;

import api.orders.checks.OrderChecks;
import api.courier.AbstractCourierTest;
import api.orders.OrderClientImpl;


public class AbstractOrderTest extends AbstractCourierTest {
    protected static final OrderClientImpl orderClient = new OrderClientImpl();
    protected static final OrderChecks orderCheck = new OrderChecks();
}
