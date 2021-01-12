package climatechange;
import java.util.*;

public class Temperature implements ITemperature{
	private double temperature;
	private int year;
	private String month;
	private String country;
	private String countryCode;
	
	//Creates a Temperature Object with a given temperature, year, month, country, and countryCode
	public Temperature(double temperature, int year, String month, String country, String countryCode){
		this.temperature = temperature;
		this.year = year;
		this.month = month;
		this.country = country;
		this.countryCode = countryCode;
	}
	
	// get the name of the country
	public String getCountry() {
		return country;
	}
	
	// get the 3-letter code of the country
	public String getCountry3LetterCode() {
		return countryCode;
	}
	
	// get the month
	public String getMonth() {
		return month;
	}
	
	//Helper Method
	//given the first 3 letters of a month as a String, gets its corresponding integer value
	//(i.e, if given "Jan", returns the value 1)
	public int getMonthAsInt(String month) {
		
		//Employs a HashMap to associate the first 3 letters of each month with the corresponding number
		HashMap<String, Integer> months = new HashMap<>();
		String[] listOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		int number = 1;
		for(String value: listOfMonths) {
			months.put(value, number);
			number++;
		}
		
		return months.get(month); //returns the value of a month as an integer
	}
	
	// get the year
	public int getYear() {
		return year;
	}
	
	// get temperature; input parameter of false = return Celsius value)
	public double getTemperature(boolean getFahrenheit) {
		if(getFahrenheit) {
			double fahrenheit = (temperature * 9/5) + 32;
			return fahrenheit;
		}
		return temperature;
	}
	
	//implements compareTo() as a part of the Comparable<ITemperature> interface
	//Checks first by temperature,then country,then year, and then month
	//returns 1, -1, and 0, depending on whether this object is greater, lesser than or equal to the other object
	public int compareTo(ITemperature otherObject) {
		Temperature other = (Temperature) otherObject;
		int temp = Double.compare(this.temperature, other.temperature);
		if(temp != 0){
			return temp;
		}
		else {
			int countries = country.compareTo(other.country);
			if(countries != 0) {
				return countries;
			}
			else {
				int years = Integer.compare(this.year, other.year);
				if(years != 0) {
					return years;
				}
				else {
					return Integer.compare(getMonthAsInt(month), getMonthAsInt(other.month));
				}
			}
		}
	}
	
	//implements equals() as a part of the Comparable<ITemperature> interface
	public boolean equals(Object otherObject) {
		Temperature other = (Temperature) otherObject;
		return this.compareTo(other) == 0;
	}
	
	//implements hashCode() as a part of the Comparable<ITemperature> interface
	public int hashCode() {
		int temp = (int)this.getTemperature(false);
		return temp + this.getMonthAsInt(this.getMonth()) + this.getYear();
	}
	
	//returns a String when Temperature values are printed
	public String toString() {
		return "Temperature: " + temperature + " Month/Year: " + month +"/" + year + " Country: " + country;
	}
	
}
