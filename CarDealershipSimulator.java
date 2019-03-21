/**
 * CPS 209 Assignment 1
 * CarDealershipSimulator.java
 * @author Mohammad Ismail
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
	/** Enumerated Available Commands */
	public static enum Commands {
		L, Q, BUY, RET, ADD, SPR, SSR, SMR, FPR, FEL, FAW, FCL
	}

	/**
	 * A list of default cars identical to the provided cars.txt
	 * should cars.text not be found and throw an exception and only as a 
	 * backup should file loading fail. 
	 * Not intended to circumvent any expectectations of File I/O.
	 */
	public static final String demoCars = "Toyota     blue   SEDAN   GAS_ENGINE      9.5  500 2WD 25000\n"
	+ "Honda           red      SPORTS  GAS_ENGINE      9.2  450 2WD 30000\n" 
	+ "Kia        white  MINIVAN GAS_ENGINE      9.7  550 2WD 20000\n"
	+ "BMW        black  SEDAN   GAS_ENGINE      9.6  600 AWD 55000\n"
	+ "Tesla      red    SEDAN   ELECTRIC_MOTOR  9.1  425 AWD 85000  30\n"
	+ "Chevy      red    MINIVAN GAS_ENGINE      9.25 475 2WD 40000\n"
	+ "Chevy      red    MINIVAN GAS_ENGINE      9.25 475 2WD 40000\n"
	+ "ChevyExpectInvalid red MINIVAN SOUL_POWER 9.25 475 2WD 40000\n" // This line is expected to be invalid
	+ "ChevyVolt  green  SEDAN   ELECTRIC_MOTOR  8.9  375 AWD 37000  45\n"
	+ "Bentley    black  SEDAN   GAS_ENGINE      9.8  575 2WD 150000\n"
	+ "NissanLeaf green  SEDAN   ELECTRIC_MOTOR  8.8  325 AWD 32000  55";
	
  public static void main(String[] args)
  {
	  /** Reference variable to the last Car bought from the dealership. Initially null */
	  Car lastCarBought = null;
	  boolean loaded;
	  // Create a CarDealership object
	  CarDealership dealership = new CarDealership();
	  // Then create an (initially empty) array list of type Car
	  ArrayList<Car> cars = new ArrayList<Car>();
	  // Then create some new car objects of different types
	  // Attempts to load files, otherwise sets program to demo inventory
	  attemptFileLoad("cars.txt", cars);
      // The ADD command should hand this array list to CarDealership object via the addCars() method	  
	  
	  // Create a scanner object
	  Scanner in = new Scanner(System.in);
	  // while the scanner has another line
	  while (in.hasNextLine()) {
		  // read the input line
		  String inputLine = in.nextLine();
		  // create another scanner object (call it "commandLine" or something) using the input line instead of System.in
		  Scanner commandLine = new Scanner(inputLine);
		  if (!commandLine.hasNext()) {
			  continue;
		  }
		  // read the next word from the commandLine scanner 
		  String command = commandLine.next();
		  // check if the word (i.e. string) is equal to one of the commands and if so, call the appropriate method via the CarDealership object
		  switch(command) {
			  case "L": dealership.displayInventory(); break;
			  default: System.out.printf("The command `%s` is not valid. Please enter a valid command or enter HELP to see commands\n", command);
		  }
	  }
	 
  }

  /**
	 * Attempts to load existing file based on filename. If not found, load the demo cars from 
	 * the constant declared above. Will let user know whether file was loaded or program is running
	 * demo mode.
	 * @param filename string of name of the file to attempt loading of ie. "cars.text".
	 * @param cars Reference to ArrayList to be handed through.
	 */
	public static void attemptFileLoad(String filename, ArrayList<Car> cars){
		try {
			File file = new File(filename);
			parseAndPopulateCars(new Scanner(file), cars);
			System.out.println("File Loaded.");
		} catch (IOException loadException) {
			System.out.println("File not found, attempting to create cars from demo list instead.");
			parseAndPopulateCars(new Scanner(demoCars), cars);
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
  public static void parseAndPopulateCars(Scanner scanner, ArrayList<Car> cars) {
	  	String errorMessage;
	    while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
			int numColumns = tokenizer.countTokens();
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
				Car.Model model = Car.Model.valueOf(tokenizer.nextToken()); // Potenitally throws Illegal Argument Exception if not within enum
				String powerString = tokenizer.nextToken();
				int power;
				double safetyRating = Double.parseDouble(tokenizer.nextToken()); // Potentially Throws Illgeal Argument
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
			}
		}
		scanner.close();
	}

	/**
	 * Vehicle v1 = new Vehicle("Honda", "blue", 2, 4);
	  Vehicle v2 = new Vehicle("Honda", "blus", 2, 4);
	  Vehicle v3 = new Vehicle("Toyota", "blue", 2, 4);
	  System.out.println(v1.equals(v2));
	  System.out.println(v1.equals(v3));
	  // Test Display
	  System.out.println(v1.display());
	  System.out.println(v2.display());
	  System.out.println(v3.display());
	  // Test Cars
	  Car c1 = new Car("Honda", "blue", 2, Car.Model.SEDAN, 2, 5.6, true, 995556.22);
	  System.out.println(c1.display());
	  // Test Car eqality
	  Car c2 = new Car("Honda", "blue", 2, Car.Model.SEDAN, 2, 5.6, true, 995556.22);
	  System.out.println("Cars 1 and 2 are equal? Expect true: " + Boolean.toString(c1.equals(c2)));
	  Car c3 = new Car("Honda", "blue", 2, Car.Model.SEDAN, 2, 5.6, true, 995556.22);
	  System.out.println("Cars 1 and 3 are equal? expect false: " + Boolean.toString(c1.equals(c3)));
	  // Test Car compareTo
	  ArrayList<Car> priceCompare = new ArrayList<Car>();
	  priceCompare.add(new Car("Honda", "blue", Car.GAS_ENGINE, Car.Model.MINIVAN, 300, 10.9, true, 999.55));
	  priceCompare.add(new Car("Honda", "blue", Car.GAS_ENGINE, Car.Model.SEDAN, 400, 5.6, false, 23.33));
	  priceCompare.add(new Car("Toyota", "blue", Car.GAS_ENGINE, Car.Model.SEDAN, 345, 4.2, true, 544.55));
	  priceCompare.add(new Car("Honda", "blue", Car.GAS_ENGINE, Car.Model.SEDAN, 100, 9.25, false, 9964.4));
	  System.out.println("Before sort: ");
	  for (Car car : priceCompare) {
		  System.out.println(car.display());
	  }
	//   System.out.println("After Sort: ");
	//   Collections.sort(priceCompare);
	//   for (Car car : priceCompare) {
	// 	  System.out.println(car.display());
	//   }
	  // Electric Car Testing
	  System.out.println("Testing Electric Cars: ");
	  ElectricCar e1 = new ElectricCar("Honda", "blue", Car.ELECTRIC_MOTOR, Car.Model.MINIVAN, 2, 2.2, true, 9964.4, 45, "Lithium");
	  ElectricCar e2 = new ElectricCar("Honda", "blue", Car.ELECTRIC_MOTOR, Car.Model.SPORTS, 2, 5.6, false, 964.4, 45, "Lithium");
	  System.out.println(e1.display());
	  System.out.println(e1.compareTo(e2));
	  System.out.println();
	  // Create CarDealership Object
	  CarDealership cardealer = new CarDealership();
	  priceCompare.add(e1);
	  priceCompare.add(e2);
	  cardealer.addCars(priceCompare);
	  cardealer.displayInventory();
	  System.out.println("buying car");
	  Car saveCar = cardealer.buyCar(2);
	  try {
		  cardealer.buyCar(23);
	  } catch (IndexOutOfBoundsException e) {
		  System.out.println(e.getMessage());
	  }
	  System.out.println("RETURNED CAR: " + saveCar.display());
	  cardealer.displayInventory();
	  try {
		System.out.println("Returning Car: ");
		cardealer.returnCar(saveCar);
		System.out.println("Attempt to return null");
		Car nullCar = null;
		cardealer.returnCar(nullCar);
	  } catch (IllegalArgumentException exception) {
		  System.out.println(exception.getMessage());
	  }
	  cardealer.displayInventory();
	  System.out.println("Sort by Price: ");
	  cardealer.sortByPrice();
	  cardealer.displayInventory();
	  System.out.println("Sort by Safety Rating: ");
	  cardealer.sortBySafetyRating();
	  cardealer.displayInventory();
	  System.out.println("Sort by Maximum Range");
	  cardealer.sortByMaxRange();
	  cardealer.displayInventory();
	  System.out.println("Filter by Electric: ");
	  cardealer.filterByElectric();
	  cardealer.displayInventory();
	  cardealer.filtersClear();
	  System.out.println("Filter by AWD: ");
	  cardealer.filterByAWD();
	  cardealer.displayInventory();
	  System.out.println("Filter by price 500 to 1000");
	  cardealer.filtersClear();
	  cardealer.filterByPrice(500, 1000);
	  cardealer.sortByPrice();
	  cardealer.displayInventory();
	  cardealer.filtersClear();
	  System.out.println("Filter by AWD and Electric and Price: ");
	  cardealer.filterByAWD();
	  cardealer.filterByElectric();
	  cardealer.displayInventory();
	  System.out.println(demoCars);

	 */
}