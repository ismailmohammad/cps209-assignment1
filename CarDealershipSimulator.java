/**
 * Mohammad Ismail
 */
import java.util.ArrayList;
import java.util.Scanner;

public class CarDealershipSimulator 
{
  public static void main(String[] args)
  {
	  Vehicle v1 = new Vehicle("Honda", "blue", 2, 4);
	  Vehicle v2 = new Vehicle("Honda", "blus", 2, 4);
	  Vehicle v3 = new Vehicle("Toyota", "blue", 2, 4);
	  System.out.println(v1.equals(v2));
	  System.out.println(v1.equals(v3));
	  // Test Display
	  System.out.println(v1.display());
	  System.out.println(v2.display());
	  System.out.println(v3.display());
	  // Test Cars
	  Car c1 = new Car("Honda", "blue", 2, 4, "model", 2, 5.6, true, 995556.22);
	  System.out.println(c1.display());

	  // Create a CarDealership object
	  	  
	  // Then create an (initially empty) array list of type Car
      // Then create some new car objects of different types
	  // See the cars file for car object details
	  // Add the car objects to the array list
      // The ADD command should hand this array list to CarDealership object via the addCars() method	  
	  
	  // Create a scanner object
	  
	  // while the scanner has another line
	  //    read the input line
	  //    create another scanner object (call it "commandLine" or something) using the input line instead of System.in
	  //    read the next word from the commandLine scanner 
      //	check if the word (i.e. string) is equal to one of the commands and if so, call the appropriate method via the CarDealership object  
	 
  }
}