/**
 * Mohammad Ismail
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import Car.Model;

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
	  Car c1 = new Car("Honda", "blue", 2, 4, Car.Model.SEDAN, 2, 5.6, true, 995556.22);
	  System.out.println(c1.display());
	  // Test Car eqality
	  Car c2 = new Car("Honda", "blue", 2, 4, Car.Model.SEDAN, 2, 5.6, true, 995556.22);
	  System.out.println("Cars 1 and 2 are equal? Expect true: " + Boolean.toString(c1.equals(c2)));
	  Car c3 = new Car("Honda", "blue", 2, 6, Car.Model.SEDAN, 2, 5.6, true, 995556.22);
	  System.out.println("Cars 1 and 3 are equal? expect false: " + Boolean.toString(c1.equals(c3)));
	  // Test Car compareTo
	  ArrayList<Car> priceCompare = new ArrayList<Car>();
	  priceCompare.add(new Car("Honda", "blue", Car.ELECTRIC_MOTOR, 4, Car.Model.MINIVAN, 2, 5.6, true, 999.55));
	  priceCompare.add(new Car("Honda", "blue", Car.GAS_ENGINE, 4, Car.Model.SEDAN, 2, 5.6, true, 23.33));
	  priceCompare.add(new Car("Toyota", "blue", Car.ELECTRIC_MOTOR, 4, Car.Model.SEDAN, 2, 5.6, true, 544.55));
	  priceCompare.add(new Car("Honda", "blue", Car.GAS_ENGINE, 4, Car.Model.SEDAN, 2, 5.6, true, 9964.4));
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
	  ElectricCar e1 = new ElectricCar("Honda", "blue", 2, 4, Car.Model.MINIVAN, 2, 5.6, true, 9964.4, 45, "Lithium");
	  ElectricCar e2 = new ElectricCar("Honda", "blue", 2, 4, Car.Model.SPORTS, 2, 5.6, true, 964.4, 45, "Lithium");
	  System.out.println(e1.display());
	  System.out.println(e1.compareTo(e2));
	  System.out.println();
	  // Create CarDealership Object
	  CarDealership cardealer = new CarDealership();
	  priceCompare.add(e1);
	  cardealer.addCars(priceCompare);
	  cardealer.displayInventory();
	  System.out.println("buying car");
	  Car saveCar = cardealer.buyCar(2);
	  System.out.println("RETURNED CAR: " + saveCar.display());
	  cardealer.displayInventory();
	  System.out.println("Returning Car: ");
	  cardealer.returnCar(saveCar);
	  cardealer.displayInventory();
	  System.out.println("Sort by Price: ");
	  cardealer.sortByPrice();
	  cardealer.displayInventory();
	  System.out.println("Sort by Safety Rating: ");
	  cardealer.sortBySafetyRating();
	  cardealer.displayInventory();


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