package ule.edi.travel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ule.edi.model.Person;

public class TravelArrayImplTests {

	private DateFormat dformat = null;
	private TravelArrayImpl e, ep;

	private Date parseLocalDate(String spec) throws ParseException {
		return dformat.parse(spec);
	}

	public TravelArrayImplTests() {

		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	@Before
	public void testBefore() throws Exception {
		e = new TravelArrayImpl(parseLocalDate("24/02/2020 17:00:00"), 110);
		ep = new TravelArrayImpl(parseLocalDate("24/02/2020 17:00:00"), 4);

	}

	@Test
	public void testEventoVacio() throws Exception {

		Assert.assertTrue(e.getNumberOfAvailableSeats() == 110);
		Assert.assertEquals(110, e.getNumberOfAvailableSeats());
		Assert.assertEquals(0, e.getNumberOfAdults());
		Assert.assertEquals(0, e.getNumberOfChildren());
		Assert.assertEquals(100.0, 0.0, e.getPrice());

	}

	// test 2 constructor
	@Test
	public void test2Constructor() throws Exception {
		TravelArrayImpl e2 = new TravelArrayImpl(parseLocalDate("24/02/2020 17:00:00"), 110, 200.0, (byte) 20);
		Assert.assertEquals(parseLocalDate("24/02/2020 17:00:00"), e2.getTravelDate());

		Assert.assertEquals(200.0, 0.0, e2.getPrice());
		Assert.assertEquals((byte) 20, (byte) e2.getDiscountAdvanceSale());
	}

	@Test
	public void test2ConstructorCollect() throws Exception {
		TravelArrayImpl e2 = new TravelArrayImpl(parseLocalDate("24/02/2018 17:00:00"), 110, 200.0, (byte) 20);
		Assert.assertTrue(e2.sellSeatPos(1, "10203040A", "Alice", 34, false)); // venta normal
		Assert.assertTrue(e2.sellSeatPos(2, "10203040B", "Alice", 34, true)); // venta anticipada
		Assert.assertEquals(2, e2.getNumberOfSoldSeats());

		Assert.assertEquals(360.0, 0.0, e2.getCollectionTravel());
	}

	// test getDiscountAdvanceSale

	@Test
	public void testGetDiscountAdvanceSale() throws Exception {

		Assert.assertTrue(e.getDiscountAdvanceSale() == 25);
	}

	// test getDate

	@Test
	public void testGetDate() throws Exception {

		Assert.assertEquals(parseLocalDate("24/02/2020 17:00:00"), e.getTravelDate());
		Assert.assertEquals(110, e.getNumberOfAvailableSeats());
		Assert.assertEquals(0, e.getNumberOfAdults());
		Assert.assertEquals(0, e.getNumberOfSoldSeats());

	}

	// test getNumber....
	@Test
	public void testsellSeatPos1Adult() throws Exception {
		Assert.assertEquals(0, e.getNumberOfAdults());
		Assert.assertTrue(e.sellSeatPos(4, "10203040A", "Alice", 18, false)); // venta normal
		Assert.assertTrue(e.sellSeatPos(3, "10203040B", "Alice", 17, true)); // venta previa
		Assert.assertEquals(1, e.getNumberOfAdults());
		Assert.assertEquals(1, e.getNumberOfChildren());
		Assert.assertEquals(1, e.getNumberOfAdvanceSaleSeats());
		Assert.assertEquals(1, e.getNumberOfNormalSaleSeats());
		Assert.assertEquals(2, e.getNumberOfSoldSeats());
		Assert.assertEquals(110, e.getNumberOfSeats());
		Assert.assertEquals(108, e.getNumberOfAvailableSeats());

	}

	// TEST OF sellSeatPos
	@Test
	public void testsellSeatPosPosCero() throws Exception {
		Assert.assertEquals(false, e.sellSeatPos(0, "10203040A", "Alice", 34, false)); // venta normal
	}

	@Test
	public void testsellSeatPosPosMayorMax() throws Exception {
		Assert.assertEquals(false, e.sellSeatPos(e.getNumberOfAvailableSeats() + 1, "10203040A", "Alice", 34, false)); // venta
																														// normal
	}

	@Test
	public void testsellSeatPosPosOcupada() throws Exception {
		Assert.assertEquals(true, e.sellSeatPos(5, "10203040A", "Alice", 34, false)); // venta normal
		Assert.assertEquals(false, e.sellSeatPos(5, "10203040A", "Alice", 34, false)); // venta normal
		Assert.assertEquals(false, e.sellSeatPos(5, "10203040B", "Alice", 34, false)); // venta normal
	}

	// TEST OF GET COLLECTION

	@Test
	public void testgetCollectionAnticipadaYnormal() throws Exception {
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(true, e.sellSeatPos(4, "10101", "AA", 10, false));

		Assert.assertTrue(e.getCollectionTravel() == 175.0);
	}

	// TEST List
	@Test
	public void testGetListEventoCompleto() throws Exception {
		Assert.assertEquals(true, ep.sellSeatPos(1, "10203040A", "Alice", 34, true)); // venta normal
		Assert.assertEquals(true, ep.sellSeatPos(2, "10203040B", "Alice", 34, true)); // venta normal
		Assert.assertEquals(true, ep.sellSeatPos(3, "10203040C", "Alice", 34, false)); // venta normal
		Assert.assertEquals(true, ep.sellSeatPos(4, "10203040D", "Alice", 34, false)); // venta normal
		Assert.assertEquals("[]", ep.getAvailableSeatsList().toString());
		Assert.assertEquals("[1, 2]", ep.getAdvanceSaleSeatsList().toString());
	}

	@Test
	public void testGetList() throws Exception {
		Assert.assertEquals(true, ep.sellSeatPos(1, "10203040A", "Alice", 34, true)); // venta normal
		Assert.assertEquals(true, ep.sellSeatPos(2, "10203040B", "Alice", 34, true)); // venta normal
		Assert.assertEquals(true, ep.sellSeatPos(3, "10203040C", "Alice", 34, false)); // venta normal
		Assert.assertEquals("[4]", ep.getAvailableSeatsList().toString());
	}

	// TEST DE GETPRICE

	@Test
	public void testgetPrice() throws Exception {
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(true, e.sellSeatPos(4, "10101", "AA", 10, false));
		Assert.assertEquals(100.0, 0.0, e.getSeatPrice(e.getSeat(4)));
		Assert.assertEquals(75.0, 0.0, e.getSeatPrice(e.getSeat(1)));
	}

	// tests REFUND

	@Test
	public void testREFUNDCero() throws Exception {
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(null, e.refundSeat(0));
	}

	@Test
	public void testrefundOk() throws Exception {
		Person p = new Person("1010", "AA", 10);
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(p, e.refundSeat(1));
	}

	@Test
	public void testREFUNDFailure() throws Exception {
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(null, e.refundSeat(3));
	}

	@Test
	public void testREFUNDOutOfBounds() throws Exception {
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(null, e.refundSeat(120));
	}

	// TEST GetPosPerson
	@Test
	public void testGetPosPersonLleno() throws Exception {
		Assert.assertEquals(true, ep.sellSeatPos(1, "10203040", "Alic", 34, true)); // venta anticipada
		Assert.assertEquals(true, ep.sellSeatPos(3, "10203040A", "Alice", 34, false)); // venta normal
		Assert.assertEquals(true, ep.sellSeatPos(4, "10203040B", "Alice", 34, false)); // venta normal
		Assert.assertEquals(-1, ep.getPosPerson("10205040"));
		Assert.assertEquals(false, ep.isAdvanceSale(new Person("10203040A", "Alice", 34)));
		Assert.assertEquals(true, ep.isAdvanceSale(new Person("10203040", "Alic", 34)));
		Assert.assertEquals(false, ep.isAdvanceSale(new Person("10202531", "Ana", 31)));
		Assert.assertEquals(3, ep.getPosPerson("10203040A"));

	}

	// TEST getSeat
	@Test
	public void testGetSeatFailure() throws Exception {
		Assert.assertEquals(null, e.getSeat(120));
		Assert.assertEquals(null, e.getSeat(0));
	}

	// TEST getMaxNumberConsecutiveSeats
	@Test
	public void testConsecutiveSeats() throws Exception {
		Assert.assertEquals(true, ep.sellSeatPos(3, "1010", "AA", 10, true));
		Assert.assertEquals(true, e.sellSeatPos(1, "1010", "AA", 10, true));
		Assert.assertEquals(2, ep.getMaxNumberConsecutiveSeats());
		Assert.assertEquals(109, e.getMaxNumberConsecutiveSeats());
	}

	// TEST sellSeatFrontPos
	@Test
	public void testSellFrontPos() throws Exception {
		Assert.assertEquals(1, ep.sellSeatFrontPos("1010", "AA", 10, true));
		Assert.assertEquals(2, ep.sellSeatFrontPos("1011", "AA", 10, true));
	}

	@Test
	public void testSellFrontPosFailure() throws Exception {
		Assert.assertEquals(1, ep.sellSeatFrontPos("1010", "AA", 10, true));
		Assert.assertEquals(-1, ep.sellSeatFrontPos("1010", "AA", 10, true));
	}

	@Test
	public void testSellFrontPosFull() throws Exception {
		Assert.assertEquals(1, ep.sellSeatFrontPos("1010", "AA", 10, true));
		Assert.assertEquals(2, ep.sellSeatFrontPos("1011", "AA", 10, true));
		Assert.assertEquals(3, ep.sellSeatFrontPos("1000", "AA", 10, true));
		Assert.assertEquals(4, ep.sellSeatFrontPos("1111", "AA", 10, true));
		Assert.assertEquals(-1, ep.sellSeatFrontPos("0011", "AA", 10, true));
	}

	// TEST sellSeatRearPos
	@Test
	public void testSellRearPos() throws Exception {
		Assert.assertEquals(4, ep.sellSeatRearPos("1010", "AA", 10, true));
		Assert.assertEquals(3, ep.sellSeatRearPos("1011", "AA", 10, true));
	}

	@Test
	public void testSellRearPosFailure() throws Exception {
		Assert.assertEquals(4, ep.sellSeatRearPos("1010", "AA", 10, true));
		Assert.assertEquals(-1, ep.sellSeatRearPos("1010", "AA", 10, true));
	}

	@Test
	public void testSellRearPosFull() throws Exception {
		Assert.assertEquals(4, ep.sellSeatRearPos("1010", "AA", 10, true));
		Assert.assertEquals(3, ep.sellSeatRearPos("1011", "AA", 10, true));
		Assert.assertEquals(2, ep.sellSeatRearPos("1000", "AA", 10, true));
		Assert.assertEquals(1, ep.sellSeatRearPos("1111", "AA", 10, true));
		Assert.assertEquals(-1, ep.sellSeatRearPos("0011", "AA", 10, true));
	}

}
