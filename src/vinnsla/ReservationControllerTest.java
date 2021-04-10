package vinnsla;

import gagnagrunnur.ReservationDbMock;
import org.junit.*;
import static org.junit.Assert.*;

public class ReservationControllerTest {
    private ReservationDbMock resDbMock;
    private ReservationController resController;
    private DateMock d1, d2, d3;
    private CustomerMock c1, c21, c22, c23, c24;

    @Before
    public void setUp() {
        resDbMock = new ReservationDbMock();
        resController = new ReservationController(resDbMock);
        d1 = new DateMock(0);
        d2 = new DateMock(4);
        d3 = new DateMock(5);
        c1 = new CustomerMock("Siggi", "siggi@hi.is");
        c21 = new CustomerMock("Lalli", "lalligerðivitlaust");
        c22 = new CustomerMock(null, "lalli@hi.is");
        c23 = new CustomerMock("Lalli", null);
        c24 = new CustomerMock(null, null);
    }

    @After
    public void tearDown() {
        resDbMock = null;
        resController = null;
        d1 = null;
        d2 = null;
        c1 = null;
        c21 = null;
        c22 = null;
        c23 = null;
        c24 = null;
        d3 = null;
    }

    @Test
    public void testNoAvailability() {
        boolean reservation = resController.makeReservation(d1, c1, 2);
        assertFalse(reservation);
    }

    @Test
    public void testConsumer() {
        boolean reservation1 = resController.makeReservation(d2, c21, 3);
        boolean reservation2 = resController.makeReservation(d2, c22, 3);
        boolean reservation3 = resController.makeReservation(d2, c23, 3);
        boolean reservation4 = resController.makeReservation(d2, c24, 3);
        assertFalse(reservation1);
        assertFalse(reservation2);
        assertFalse(reservation3);
        assertFalse(reservation4);
    }

    @Test
    public void testNoOfSeats() {
        boolean reservation = resController.makeReservation(d1, c1, -5);
        assertFalse(reservation);
    }

    @Test
    public void testValidReservation() {
        boolean trueReservation = resController.makeReservation(d3,c1,5);
        assertTrue(trueReservation);
    }
}
