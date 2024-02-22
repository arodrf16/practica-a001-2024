package ule.edi.travel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ule.edi.model.Person;
import ule.edi.model.Seat;
import ule.edi.model.Travel;

public class TravelArrayImpl implements Travel {

	private static final Double DEFAULT_PRICE = 100.0;
	private static final Byte DEFAULT_DISCOUNT = 25;
	private static final Byte CHILDREN_EXMAX_AGE = 18;
	private Date travelDate;
	private int nSeats;

	private Double price; // precio de entradas
	private Byte discountAdvanceSale; // descuento en venta anticipada (0..100)

	private Seat[] seats;

	public TravelArrayImpl(Date date, int nSeats) {
		// TODO
		// utiliza los precios por defecto: DEFAULT_PRICE y DEFAULT_DISCOUNT definidos
		// en esta clase
		// debe crear el array de asientos
		this.travelDate = date;
		this.nSeats = nSeats;
		seats = new Seat[nSeats];
		this.price = TravelArrayImpl.DEFAULT_PRICE;
		this.discountAdvanceSale = TravelArrayImpl.DEFAULT_DISCOUNT;

	}

	public TravelArrayImpl(Date date, int nSeats, Double price, Byte discount) {
		// TODO
		// Debe crear el array de asientos
		this.travelDate = date;
		this.nSeats = nSeats;
		seats = new Seat[nSeats];
		this.price = price;
		this.discountAdvanceSale = discount;
	}

	@Override
	public Byte getDiscountAdvanceSale() {
		// TODO Auto-generated method stub
		return this.discountAdvanceSale;
	}

	@Override
	public int getNumberOfSoldSeats() {
		// TODO Auto-generated method stub
		int cont = 0;

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] != null) {
				cont++;
			}
		}

		return cont;
	}

	@Override
	public int getNumberOfNormalSaleSeats() {
		// TODO Auto-generated method stub
		int cont = 0;

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] != null) {
				if (!seats[i].getAdvanceSale()) {
					cont++;
				}
			}
		}

		return cont;
	}

	@Override
	public int getNumberOfAdvanceSaleSeats() {
		// TODO Auto-generated method stub
		int cont = 0;

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] != null) {
				if (seats[i].getAdvanceSale()) {
					cont++;
				}
			}
		}

		return cont;
	}

	@Override
	public int getNumberOfSeats() {
		// TODO Auto-generated method stub
		return this.nSeats;
	}

	@Override
	public int getNumberOfAvailableSeats() {
		// TODO Auto-generated method stub
		int cont = 0;

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] == null) {
				cont++;
			}
		}

		return cont;
	}

	@Override
	public Seat getSeat(int pos) {
		// TODO Auto-generated method stub
		if (pos > 0 && pos <= this.nSeats) {
			return seats[pos - 1];
		} else {
			return null;
		}
	}

	@Override
	public Person refundSeat(int pos) {
		// TODO Auto-generated method stub
		if (pos > 0 && pos <= this.nSeats) {
			if (seats[pos - 1] == null) {
				return null;
			} else {
				Person persona = seats[pos - 1].getHolder();
				seats[pos - 1] = null;
				return persona;
			}
		} else {
			return null;
		}
	}

	private boolean isChildren(int age) {
		// TODO Auto-generated method stub
		if (age < 18) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isAdult(int age) {
		// TODO Auto-generated method stub
		if (age >= 18) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Integer> getAvailableSeatsList() {
		// TODO Auto-generated method stub
		List<Integer> lista = new ArrayList<Integer>(nSeats);

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] == null) {
				lista.add(i + 1);
			}
		}

		return lista;
	}

	@Override
	public List<Integer> getAdvanceSaleSeatsList() {
		// TODO Auto-generated method stub
		List<Integer> lista = new ArrayList<Integer>(nSeats);

		for (int i = 0; i < seats.length; i++) {
			if (seats[i].getAdvanceSale()) {
				lista.add(i + 1);
			}
		}

		return lista;
	}

	@Override
	public int getMaxNumberConsecutiveSeats() {
		// TODO Auto-generated method stub
		int cont = 0, aux = 0;

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] == null) {
				aux++;
			} else {
				if (aux > cont) {
					cont = aux;
				}
				aux = 0;
			}

			if (i == this.nSeats - 1) {
				if (aux > cont) {
					cont = aux;
				}
			}
		}

		return cont;
	}

	@Override
	public boolean isAdvanceSale(Person p) {
		// TODO Auto-generated method stub
		for (int i = 0; i < seats.length; i++) {
			if (this.seats[i] != null) {
				if (seats[i].getHolder().equals(p)) {
					if (seats[i].getAdvanceSale()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Date getTravelDate() {
		// TODO Auto-generated method stub
		return this.travelDate;
	}

	@Override
	public boolean sellSeatPos(int pos, String nif, String name, int edad, boolean isAdvanceSale) {
		// TODO Auto-generated method stub
		boolean salida = false;
		Person persona = new Person(nif, name, edad);

		if (pos > 0 && pos <= this.nSeats) {
			for (int i = 0; i < this.nSeats; i++) {
				if (this.seats[i] != null) {
					if (seats[i].getHolder().equals(persona)) {
						return false;
					}
				}
			}

			if (this.seats[pos - 1] == null) {
				Seat asiento = new Seat(isAdvanceSale, persona);
				this.seats[pos - 1] = asiento;
				salida = true;
			}
		}

		return salida;
	}

	@Override
	public int getNumberOfChildren() {
		// TODO Auto-generated method stub
		int cont = 0;

		for (int i = 0; i < seats.length; i++) {
			if (this.seats[i] != null) {
				if (isChildren(seats[i].getHolder().getAge())) {
					cont++;
				}
			}
		}

		return cont;
	}

	@Override
	public int getNumberOfAdults() {
		// TODO Auto-generated method stub
		int cont = 0;

		for (int i = 0; i < seats.length; i++) {
			if (this.seats[i] != null) {
				if (isAdult(seats[i].getHolder().getAge())) {
					cont++;
				}
			}
		}

		return cont;
	}

	@Override
	public Double getCollectionTravel() {
		// TODO Auto-generated method stub
		double total = 0.0, precioDescuento = 0.0, descuento = 0.0;

		descuento = (this.price * this.discountAdvanceSale) / 100;
		precioDescuento = this.price - descuento;

		for (int i = 0; i < seats.length; i++) {
			if (seats[i] != null) {
				if (seats[i].getAdvanceSale()) {
					total += precioDescuento;
				} else {
					total += this.price;
				}
			}
		}

		return total;
	}

	@Override
	public int getPosPerson(String nif) {
		// TODO Auto-generated method stub
		int posAsiento = -1;

		for (int i = 0; i < this.nSeats; i++) {
			if (this.seats[i] != null) {
				if (this.seats[i].getHolder().getNif().equals(nif)) {
					posAsiento = i + 1;
				}
			}
		}

		return posAsiento;
	}

	@Override
	public int sellSeatFrontPos(String nif, String name, int edad, boolean isAdvanceSale) {
		// TODO Auto-generated method stub
		int posAsiento = -1;
		Person persona = new Person(nif, name, edad);

		for (int i = 0; i < this.nSeats; i++) {
			if (this.seats[i] == null) {
				posAsiento = i + 1;
				Seat asiento = new Seat(isAdvanceSale, persona);
				this.seats[i] = asiento;
				break;
			} else {
				if (seats[i].getHolder().equals(persona)) {
					break;
				}
			}
		}
		return posAsiento;
	}

	@Override
	public int sellSeatRearPos(String nif, String name, int edad, boolean isAdvanceSale) {
		// TODO Auto-generated method stub
		int posAsiento = -1;
		Person persona = new Person(nif, name, edad);

		for (int i = this.nSeats - 1; i >= 0; i--) {
			if (this.seats[i] == null) {
				posAsiento = i + 1;
				Seat asiento = new Seat(isAdvanceSale, persona);
				this.seats[i] = asiento;
				break;
			} else {
				if (seats[i].getHolder().equals(persona)) {
					break;
				}
			}
		}
		return posAsiento;
	}

	@Override
	public Double getSeatPrice(Seat seat) {
		// TODO Auto-generated method stub
		double precio = 0.0, descuento = 0.0;

		if (seat.getAdvanceSale()) {
			descuento = (this.price * this.discountAdvanceSale) / 100;
			precio = this.price - descuento;
		} else {
			precio = this.price;
		}

		return precio;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

}