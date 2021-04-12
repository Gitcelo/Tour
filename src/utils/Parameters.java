package utils;
import java.time.LocalDate;


public class Parameters {
	private final int MAXGROUP = 20;
	// airports codes to be used, more location codes should be added if needed	
	final int RVK = 1;
	final int Ak = 2;
	final int ISAF = 3;
	final int EGIL = 4;
	// Difficulty level
	final int HANDICAP = 10;
	final int EASY = 11;
	final int MODERATE = 12;
	final int HARD = 13;

	private int difficulty;
	private int [] price; // price range [minPrice, maxPrice]
	private int groupSize;	// "capacity" in the model, number of travellers
	private int location;	// Should find a better name, take off location
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
	 * @param to destination of the trip
	 */
	Parameters (int dif, int [] priceRange,
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
		location = from;
	}
	
	/**
	 * empty constructor for Parameter object
	 */
	Parameters () {
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
		if (today.isBefore(checkIn))
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
	int getdifficulty () {
		return difficulty;
	}
	int getLowerPrice () {
		return price[0];
	}
	int getHigherPrice () {
		return price[1];
	}
	int getgroupSize () {
		return groupSize;
		
	}
	LocalDate getcheckIn () {
		return checkIn;
	}
	LocalDate getcheckOut () {
		return checkOut;
	}

	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * @return the destination
	 */
	public int getDestination() {
		return destination;
	}

}
