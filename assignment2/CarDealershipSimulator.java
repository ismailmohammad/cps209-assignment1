/**
 * CPS 209 Assignment 2
 * CarDealershipSimulator.java
 * @author Mohammad Ismail
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CarDealershipSimulator 
{
	/** Constant of number of columns expected for an electric car */
	public static final int ELECTRIC_CAR_COLUMNS = 9;
	/** Constant of number of columns expected for a gas car */
	public static final int GAS_CAR_COLUMNS = 8;
	/** Expected location of the type of power the Car uses within data */
	public static final int POWER_TYPE_LOCATION = 3;
	/** 
	 * Number of String tokens for Commands without any arguments. Add 1, 2, etc. to indicate
	 * number of arguments.
	 */
	public static final int COMMAND_WITHOUT_ARGS = 1;

	/**
	 * A list of default cars identical to the provided cars.txt plus
	 * an invalid car object to demonstrate validity checking.
	 * Used should cars.text not be found and throw an exception and only as a 
	 * backup should file loading fail. 
	 * Not intended to circumvent any expectectations of File I/O.
	 */
	public static final String DEMO_CARS = "Toyota     blue   SEDAN   GAS_ENGINE      9.5  500 2WD 25000\n"
	+ "Honda           red      SPORTS  GAS_ENGINE      9.2  450 2WD 30000\n" 
	+ "Kia        white  MINIVAN GAS_ENGINE      9.7  550 2WD 20000\n"
	+ "BMW        black  SEDAN   GAS_ENGINE      9.6  600 AWD 55000\n"
	+ "Tesla      red    SEDAN   ELECTRIC_MOTOR  9.1  425 AWD 85000  30\n"
	+ "Chevy      red    MINIVAN GAS_ENGINE      9.25 475 2WD 40000\n"
	+ "Chevy      red    MINIVAN GAS_ENGINE      9.25 475 2WD 40000\n"
	+ "MfrExpectedToFail red MINIVAN SOUL_POWER 9.25 475 2WD 40000\n" // This line is expected to be invalid
	+ "ChevyVolt  green  SEDAN   ELECTRIC_MOTOR  8.9  375 AWD 37000  45\n"
	+ "Bentley    black  SEDAN   GAS_ENGINE      9.8  575 2WD 150000\n"
	+ "NissanLeaf green  SEDAN   ELECTRIC_MOTOR  8.8  325 AWD 32000  55";
		
	public static void main(String[] args)
	{
		/** Reference variable to the last Car bought from the dealership. Initially null */
		Car lastCarBought = null;
		boolean loaded = false;
		// Create a CarDealership object
		CarDealership dealership = new CarDealership();
		// Then create an (initially empty) array list of type Car
		ArrayList<Car> cars = new ArrayList<Car>();
		// Then create some new car objects of different types
		// Attempts to load files, otherwise sets program to demo inventory
		attemptFileLoad("cars.txt", cars);
		// Create a scanner object, taking in keyboard input.
		Scanner in = new Scanner(System.in);
		String inputLine; // To contain user Input
		StringTokenizer inputTokens;
		int tokenNum; // To contain number of user entered tokens per line
		Scanner commandLine;
		String command;
		// Greet User.
		System.out.println("Welcome to Car Dealership Simulator 2.0.0 (Goat Rentals coming soon in v11.11.0)");
		System.out.print("Please enter a command or Q to quit: ");
		// while the scanner has another line
		while (in.hasNextLine()) {
			// read the input line
			inputLine = in.nextLine();
			// Tokenize the input line
			inputTokens = new StringTokenizer(inputLine);
			// Count how many tokens are provided.
			tokenNum = inputTokens.countTokens();
			// create another scanner object (call it "commandLine" or something) using the input line instead of System.in
			commandLine = new Scanner(inputLine);
			if (commandLine.hasNext()) {
				// read the next word from the commandLine scanner 
				command = commandLine.next();
				// Check for null String and return invalid.
				if (command == null) {
					promptValid();
				}
				// Check if user wants to list inventory
				else if (command.equalsIgnoreCase("L")) {
					// If "L", it cannot be followed by another token otherwise invalid.
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.displayInventory(); }
				// Check if Q or the quit command is invoked and if so, bid adieu and break out of while.
				} else if (command.equalsIgnoreCase("Q")) {
					System.out.println("Goodbye.");
					break;
				// First check if the command is to BUY a car and then ensure that it has one argument
				} else if (command.equalsIgnoreCase("BUY")) {
					// Check if number of arguments exceeds single argument
					if (tokenNum > (COMMAND_WITHOUT_ARGS + 1)) {
						System.out.println("BUY takes exactly 1 argument, the Car's index. More than 1 was provided.");
					// Check if no arguments are provided.
					} else if (tokenNum == COMMAND_WITHOUT_ARGS) {
						System.out.println("Please enter which Car number you'd like to purchase. ie. BUY 0"); 
					} else {
						int carIndex;
						// Check whether the next argument is a valid integer.
						try {
							carIndex = Integer.parseInt(commandLine.next());
						} catch (IllegalArgumentException parseExcept) {
							System.out.println("Invalid BUY argument. Please enter a positive whole number after BUY\n");
							continue; // Continue to the next command.
						}
						// Attempt to purchase the indicated Car
						try {
							lastCarBought = dealership.buyCar(carIndex);
							System.out.printf("Purchased Car: %s\n", lastCarBought.display());
						} catch (IndexOutOfBoundsException indexExcept) {
							System.out.println(indexExcept.getMessage());
						}
					}
				// Check if the command is RET and isn't followed by any other tokens
				} else if (command.equalsIgnoreCase("RET")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					// Attempt to return the last Car bought (if applicable)
					else { lastCarBought = attemptReturn(dealership, lastCarBought); }
				// Add loaded car list to inventory if not already loaded.
				} else if (command.equalsIgnoreCase("ADD")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { loaded = attemptAddInventory(loaded, dealership, cars); }
				// Sort by Price of Car in Ascending order
				} else if (command.equalsIgnoreCase("SPR")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.sortByPrice(); System.out.println("Sorted by Price in Ascending Order.\n");}
				// Sort by Safety Rating in Descending order.
				} else if (command.equalsIgnoreCase("SSR")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.sortBySafetyRating(); System.out.println("Sorted by Safety Rating in Descending Order.\n");}
				// Sort by Maximum Range of car in Descending order.
				} else if (command.equalsIgnoreCase("SMR")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.sortByMaxRange(); System.out.println("Sorted by Max Range in Descending Order.\n"); }
				// Filter by price; via minimum and maximum price.
				} else if (command.equalsIgnoreCase("FPR")) {
					// Check if more than 2 arguments provided
					if (tokenNum > (COMMAND_WITHOUT_ARGS + 2)) {
						System.out.println("FPR takes exactly 2 arguments, the minimum and maximum price. More than 2 were provided.");
					// Check if less than 2 arguments provided
					} else if (tokenNum < (COMMAND_WITHOUT_ARGS + 2)) {
						System.out.println("FPR takes exactly 2 arguments, the minimum and maximum price. Less than 2 were provided.");
					} else {
						double minPrice;
						double maxPrice;
						// Check whether the next argument is a valid integer.
						try {
							minPrice = Double.parseDouble(commandLine.next());
							maxPrice = Double.parseDouble(commandLine.next());
						} catch (IllegalArgumentException parseExcept) {
							System.out.println("Invalid FPR arguments. Please enter a minimum and maximum price, where both are positive and minimum is less than maximum");
							// Continue loop to the propt for next command. ie. ski attempt to set to filter
							continue;
						}
						// Try Setting Filter with parsed arguments
						try {
							dealership.filterByPrice(minPrice, maxPrice);
							System.out.printf("Price Filter from %.1f$ to %.1f$ set.\n", minPrice, maxPrice);
						} catch (IllegalArgumentException minMaxExcept) {
							System.out.println(minMaxExcept.getMessage());
						}
					}
				// Filter by Electric Cars (only show electric vehicles)
				} else if (command.equalsIgnoreCase("FEL")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.filterByElectric();System.out.println("Electric Car filter set."); }
				// Filter by All Wheel Drive capable Vehicles (only show AwD)
				} else if (command.equalsIgnoreCase("FAW")) {
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.filterByAWD(); System.out.println("All Wheel Drive filter set."); }
				// Clear all set filters (AWD, Electric, Price)
				} else if (command.equalsIgnoreCase("FCL")) {
					// Ensure command isn't followed by another token/argument.
					if (tokenNum > COMMAND_WITHOUT_ARGS) { promptValid();}
					else { dealership.filtersClear();System.out.println("All Filters Cleared."); }
				} else {
					promptValid();
				} 
			} else {
				promptValid();
			}
			System.out.print("Please enter a command or Q to quit: ");
		}

	}

	/**
	 * Prompts user that the entered command is invalid.
	 * @param command command entered.
	 */
	private static void promptValid() {
		System.out.println("The command entered is not valid. Please enter a valid command or enter Q to quit.\n");
	}

	/**
	 * Adds Cars in populated ArrayList to the dealership's inventory
	 * @param loaded whether the cars have been already loaded
	 * @param dealership reference to deadlership object
	 * @param cars reference to ArrayList of Car objects
	 * @return
	 */
	private static boolean attemptAddInventory(boolean loaded, CarDealership dealership, ArrayList<Car> cars) {
		if (!loaded) {
			dealership.addCars(cars);
			loaded = true;
			System.out.println("Cars added to main dealership inventory. Enter `L` or `l` to display inventory\n");
		} else {
			System.out.println("Cars have already been added to inventory.\n");
		}
		return loaded;
		}
	
	/**
	 * Attempt to return the last purchased car (if exists) to the dealership.
	 * @param dealership Reference to CarDealership object
	 * @param returnCar Reference to lastCarBought object. can either be null or
	 * contain a valid Car object. Handles exception accordingly.
	 */
	private static Car attemptReturn(CarDealership dealership, Car returnCar) {
		try {
			dealership.returnCar(returnCar);
			System.out.println("Car returned back to dealership.\n");
			returnCar = null;

		} catch (IllegalArgumentException returnExcept) { 
			System.out.println(returnExcept.getMessage()); 
		}
		return returnCar;
  }

  /**
	 * Attempts to load existing file based on filename. If not found, load the demo cars from 
	 * the constant declared above. Will let user know whether file was loaded or program is running
	 * demo mode.
	 * @param filename string of name of the file to attempt loading of ie. "cars.text".
	 * @param cars Reference to ArrayList to be handed through.
	 */
	private static void attemptFileLoad(String filename, ArrayList<Car> cars){
		try {
			File file = new File(filename);
			parseAndPopulateCars(new Scanner(file), cars);
			System.out.println("File Successfully Loaded.\n");
		} catch (IOException loadException) {
			System.out.println("File not found, attempting to create cars from demo list instead.\n");
			parseAndPopulateCars(new Scanner(DEMO_CARS), cars);
		}
	}

  /**
   * Parses each line belonging to the Scanner object for valid Car/Electric Car data
   * and proceeds to create 
   * @param scanner Reference to Scanner object expected to contain Car data
   * @param cars Reference to ArrayList of Type Car to which the parsed Car(s) should
   * be added to. Handles exceptions and lets user know if an input line is invalid and skips
   * adding it
   */
  private static void parseAndPopulateCars(Scanner scanner, ArrayList<Car> cars) {
	  	String errorMessage;
	    while (scanner.hasNextLine()) {
			// Read next line of the data expected to contain Car fields
			String line = scanner.nextLine();
			// Tokenize String to extract parameters as needed;
			StringTokenizer tokenizer = new StringTokenizer(line);
			// Store the number of fields within the line.
			int numColumns = tokenizer.countTokens();
			// Reset error message to default indicating invalid Car data.
			errorMessage = "\n`" + line + "` contains invalid Car data, skipping it."
			+ " Please ensure data values are of correct type and formatted as follows:\n" 
			+ "MFR COLOR MODEL POWER_TYPE SAFETY_RATING MAX_RANGE AWD/2WD PRICE RCH_TIME(only if Electric)\n"
			+ "Sample: Chevy red MINIVAN GAS_ENGINE 9.25 475 2WD 40000\n";
			// If the data has more than 9 or less than 8 columns, it is invalid. Communicates error and moves on to next car.
			if (numColumns > ELECTRIC_CAR_COLUMNS || numColumns < GAS_CAR_COLUMNS) {
				System.out.println(errorMessage); continue;
			}
			// Otherwise attempt to extract relevant fields and catching any IllegalArgumentExceptions (if any of the parameters are invalid) thus indicating invalid car data
			try {
				String mfr = tokenizer.nextToken();
				String color = tokenizer.nextToken();
				// Potenitally throws Illegal Argument Exception if not within enum
				Car.Model model = Car.Model.valueOf(tokenizer.nextToken());
				String powerString = tokenizer.nextToken();
				int power;
				// Potentially Throws Illgeal Argument
				double safetyRating = Double.parseDouble(tokenizer.nextToken());
				int maxRange = Integer.parseInt(tokenizer.nextToken());
				boolean AWD = tokenizer.nextToken().equals("AWD");
				double price = Double.parseDouble(tokenizer.nextToken());
				// Check if power is GAS or ELECTRIC, otherwise throw IA Exception as it's not within expected parameters.
				if (powerString.equals("GAS_ENGINE")) {
					power = Vehicle.GAS_ENGINE;
					// Attempt to create and add the Car object to the ArrayList
					cars.add(new Car(mfr, color, power, model, maxRange, safetyRating, AWD, price));
				} else if (powerString.equals("ELECTRIC_MOTOR")) {
					power = Vehicle.ELECTRIC_MOTOR;
					int rechargeTime = Integer.parseInt(tokenizer.nextToken());
					// Attempt to create and add the ElectricCar object to the ArrayList
					cars.add(new ElectricCar(mfr, color, power, model, maxRange, safetyRating, AWD, price, rechargeTime, ""));
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException exception) {
				System.out.println(errorMessage);
				continue;
			} catch (InstantiationException exception) {
				System.out.println(exception.getMessage());
				continue;
			}
		}
		scanner.close();
	}
}