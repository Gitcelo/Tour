package application;
import java.time.LocalDate;


public class Parameters {
	private final int MAXGROUP = 20;
	// airports codes to be used, more departLocation codes should be added if needed
	public final int RVK = 1;
	public final int Ak = 2;
	public final int ISAF = 3;
	public final int EGIL = 4;
	// Difficulty level

	public final int HANDICAP = 10;
	public final int EASY = 11;
	public final int MODERATE = 12;
	public final int HARD = 13;

	private int difficulty;
	private int [] price; // price range [minPrice, maxPrice]
	private int groupSize;	// "capacity" in the model, number of travellers
	private int departLocation;	// Should find a better name, take off departLocation
	private int destination;// the destination is where the trip will be

	private LocalDate checkIn;
	private LocalDate checkOut;

	/**
	 * Constructor for an object type Parameter
	 * @param dif the difficulty level, integer 11-13, and 10 is handicap accessible
	 * @param priceRange array size 2 of min to max price the customer willing to pay
	 * @param groupS amount of members in the group
	 * @param dateRange array size 2 of starting date and end date
	 * @param from airport of departure
	 * @param to of the trip
	 */
	public Parameters (int dif, int [] priceRange,
				int groupS, LocalDate [] dateRange, int from, int to)
	{
		setDifficulty(dif);
		setPrice(priceRange);
		setGroupSize(groupS);	// "capacity" in the model
		if (dateRange.length != 2)
			printError("date array error");
		else {
			setCheckIn(dateRange[0]);
			setCheckOut(dateRange[1]);
		}
		destination = to;
		departLocation = from;
	}

	/**
	 * empty constructor for Parameter object
	 */
	public Parameters () {
		price = new int [2];
	}

	private void setPrice(int [] p) {
		if (p[0]>0 && p[1]>p[0])
			price = p;
		else printError("Price error");
	}

	private void setDifficulty(int dif) {
		if (dif < 10 || dif > 13)
			printError("setDifficulty number code out of range");
		else difficulty = dif;

	}

	private void setGroupSize(int gs) {
		if (gs < 1 || gs > MAXGROUP)
			printError ("group size not valid");
		else
			groupSize = gs;
	}

	private void setCheckIn(LocalDate in) {
		LocalDate today = LocalDate.now();
		if (today.isBefore(in))
			checkIn = in;
		else
			printError ("bokking date must be in the future");
	}

	private void setCheckOut(LocalDate out) {
		if (checkIn!=null && out.isAfter(checkIn))
			checkOut = out;
		else printError ("check in must be before checkout");
	}
	/**
	 * A temporary implementation of error handler, should later on pop an error message
	 * @param e the error string, indicates where the error occured
	 */
	private void printError (String e) {
		System.out.println("an error message popup should appear here");
		System.out.println("Error: "+ e);

	}

	// get methods
	public int getdifficulty () {
		return difficulty;
	}
	public int getLowerPrice () {
		return price[0];
	}
	public int getMaxPrice () {
		return price[1];
	}
	public int getgroupSize () {
		return groupSize;

	}
	public LocalDate getcheckIn () {
		return checkIn;
	}
	public LocalDate getcheckOut () {
		return checkOut;
	}

	/**
	 * @return the departLocation
	 */
	public int getdepartLocation() {
		return departLocation;
	}
	/**
	 * @return the destination
	 */
	public int getDestination() {
		return destination;
	}

}