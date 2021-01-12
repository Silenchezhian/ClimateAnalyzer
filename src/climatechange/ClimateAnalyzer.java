package climatechange;
import java.util.*;
import java.io.*;

public class ClimateAnalyzer extends TreeSet<ITemperature> implements IClimateAnalyzer{
	
	private ArrayList<ITemperature> data;
	private String inputFileName;
	private WeatherIO weather;
	private boolean getFahrenheit;
	
	/**
	 * (Constructor)
	 * Creates a ClimateAnalyzer that implements the methods from IClimateAnalyzer, when given an input file with data
	 * Initializes private instance variables inputFileName with the given data file, data with ArrayList<ITemperature> of Temperature objects
	 * from the input file and getFahrenheit to a default of false
	 * @param inputFilename the filename of the input file with all the data, i.e world_temp_2000-2016.csv
	 */
	public ClimateAnalyzer(String inputFileName) {
		this.inputFileName = inputFileName;	
		getFahrenheit = false; //initialized to false, as temperature is compared in Celsius by default
		
		try {
			 weather = new WeatherIO();
			 data = weather.readDataFromFile(inputFileName); 	//reads data from given input file and stores it as Temperature objects
			 													//in ArrayList<ITemperature> data
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * (Helper Method)
	 * When provided a month as an integer, returns its corresponding string name
	 * i.e, if month is given as 1, returns "Jan"
	 * @param month the integer value of a month between 1 and 12
	 * @return the corresponding string name of the given integer month 
	 */
	public String getMonthAsString(int month) {
		//Employs a HashMap to associate month names with their corresponding integer values
		HashMap<Integer, String> months = new HashMap<>();
		
		//a String array of the first 3-letters of each month, capitalized according to the manner viewed in the input data file
		String[] listOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		
		//A for loop that iterates through the String array, associates integer values with their corresponding string month name
		int number = 1;
		for(String value: listOfMonths) {
			months.put(number, value); //adds them to the HashMap months
			number++;
		}
		return months.get(month); //gets the corresponding String name for the given integer value of the month
	}

	/**
	 * (Helper Method)
	 *  Compiles a list of the all the countries within the ArrayList<ITemperature> data
	 * @return an ArrayList<String> with the names of all the countries within ArrayList<ITemperature> data
	 */
	public ArrayList<String> listOfAllCountries(){
		ArrayList<String> countries = new ArrayList<>();
		for(ITemperature t: data) {
			if(!countries.contains(t.getCountry())){
				countries.add(t.getCountry());
			}
		}
		return countries;
	}
	
	/**
	 * (Helper Method-runClimateAnalyzer())
	 * Validates a provided user input by checking if it is in acceptable String value for country that is listed in the given data file
	 * (i.e, a country listed in world_temp_2000-2016.csv)
	 * @param input the user provided input
	 * @return true, if user input is valid. Otherwise, returns false
	 */
	public Boolean validCountry(String input) {
		ArrayList<String> countries = listOfAllCountries();
		if(countries.contains(input)) {
			return true;
		}
		return false;
	}
	
	/**
	 * (Helper Method-runClimateAnalyzer())
	 * Validates a provided user input by checking if it is in acceptable Integer value for month between 1 and 12
	 * @param input the user provided input
	 * @return true, if user input is valid. Otherwise, returns false
	 */
	public Boolean validMonth(String input) {
		try { 
	        int month = Integer.parseInt(input); //if input is not an integer, exits try block and enters catch block
	        
	        if(month >= 1 && month <=12) {
				return true;
			}
	        else {
	        	return false;
	        }
	    }
	    catch( Exception e ) { 
	        return false; //if input is not an integer (i.e, Integer.parseInt(input) throws an exception), returns false
	    }
		
	}
	
	/**
	 * (Helper Method-runClimateAnalyzer())
	 * Validates a provided user input by checking if it is in acceptable Integer value for a year between 2000 and 2016
	 * @param input the user provided input
	 * @return true, if user input is valid. Otherwise, returns false
	 */
	public Boolean validYear(String input) {
		try {
			int year = Integer.parseInt(input); //if input is not an integer, exits try block and enters catch block
			
			if(year >= 2000 && year <= 2016) { //checks if the given year is between 2000 and 2016
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false; //if input is not an integer (i.e, Integer.parseInt(input) throws an exception), returns false
		}
		
	}
	
	/**
	 * (Helper Method-runClimateAnalyzer())
	 * Validates a provided user input by checking if it is in acceptable Double value where the user provided input for
	 * the higher end of the range is greater than the input provided for the lower end
	 * @param lowTemp the user provided input for the temperature at the lower-end of the range
	 * @param highTemp the user provided input for the temperature at the higher-end of the range
	 * @return true, if user input is valid. Otherwise, returns false
	 */
	public Boolean validTempRange(String lowTemp, String highTemp) {
		try { 
	        double low = Double.parseDouble(lowTemp); //if lowTemp is not a double, exits try block and enters catch block
	        double high = Double.parseDouble(highTemp); //if highTemp is not a double, exits try block and enters catch block
	        
	        if(high > low) { //checks if the Temperature entered as the higher-end of the range is actually greater than the lower-end
				return true;
			}
	        else {
	        	return false;
	        }
	    }
	    catch( Exception e ) { 
	        return false; //if any input is not an double (i.e, Double.parseDouble(input) throws an exception), returns false
	    }
	}
	

	/**
	 * (TASK A-1)
	 * Gets the lowest temperature reading among all data that matches the specific given month and country
	 * @param country, the given name of the country
	 * @param month, the given month
	 * @return an ITemperature object that is the lowest temperature reading in accordance with the given parameters
	 */
	public ITemperature getLowestTempByMonth(String country, int month) {
		double minValue = Double.MAX_VALUE;
		ITemperature lowest = data.get(0);
		
		//enhanced for loop that iterates through all the data in ArrayList<ITemperature> data and 
		//checks whether the given values for country and month are matched
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) { 
				if(t.getMonth().equals(getMonthAsString(month))) { 
					if(t.getTemperature(getFahrenheit) <= minValue) { //among all data that matches given values, 
						minValue = t.getTemperature(getFahrenheit);   //iterates to find lowest temperature
						lowest = t;
					}
				}
			}
		}
		return lowest; //returns the ITemperature object of the lowest temperature
	}
	
	/**
	 * (TASK A-1)
	 * Gets the highest temperature reading among all data that matches the specific given month and country
	 * @param country, the given name of the country
	 * @param month, the given month
	 * @return an ITemperature object that is the highest temperature reading in accordance with the given parameters
	 */
	 public ITemperature getHighestTempByMonth(String country, int month) {
		double maxValue = Double.MIN_VALUE;
		ITemperature highest = data.get(0);
		
		//enhanced for loop that iterates through all the data in ArrayList<ITemperature> data and 
		//checks whether the given values for country and month are matched
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) {
				if(t.getMonth().equals(getMonthAsString(month))) {
					if(t.getTemperature(getFahrenheit) >= maxValue) { //among all data that matches given values, 
						maxValue = t.getTemperature(getFahrenheit);		 //iterates to find highest temperature
						highest = t;
					}
				}
			}
		}
		return highest;  //returns the ITemperature object of the highest temperature
	 }
	
	
	 /**
	  * (TASK A-2)
	  * Gets the lowest temperature reading among all data that matches the specific given year and country
	  * @param country, the given name of the country
	  * @param year, the given year
	  * @return an ITemperature object that is the lowest temperature reading in accordance with the given parameters
	  */
	  public ITemperature getLowestTempByYear(String country, int year) {
		double minValue = Double.MAX_VALUE;
		Temperature lowest = (Temperature)data.get(0);
		
		//enhanced for loop that iterates through all the data in ArrayList<ITemperature> data and 
		//checks whether the given values for country and year are matched
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) {
				if(t.getYear() == year) {
					if(t.getTemperature(getFahrenheit) <= minValue) { //among all data that matches given values, 
						minValue = t.getTemperature(getFahrenheit);		//iterates to find lowest temperature
						lowest = (Temperature) t;
					}
				}
			}
		}
		return lowest; //returns the ITemperature object of the lowest temperature
	  }
	 
	  /**
	   * (TASK A-2)
	   * Gets the highest temperature reading among all data that matches the specific given year and country
	   * @param country, the given name of the country
	   * @param year, the given year
	   * @return an ITemperature object that is the highest temperature reading in accordance with the given parameters
	   */
	 public ITemperature getHighestTempByYear(String country, int year) {
		double maxValue = Double.MIN_VALUE;
		Temperature highest = (Temperature)data.get(0);
		
		//enhanced for loop that iterates through all the data in ArrayList<ITemperature> data and 
		//checks whether the given values for country and year are matched
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) {
				if(t.getYear() == year) {
					if(t.getTemperature(getFahrenheit) >= maxValue) { //among all data that matches given values, 
						maxValue = t.getTemperature(getFahrenheit);		//iterates to find highest temperature
						highest = (Temperature) t;
					}
					
				}
			}
		}
		return highest; //returns the ITemperature object of the highest temperature
	 }
	 
	 /**
	  * (TASK A-3)
	  * Gets all the temperature data that falls within the given temperature range and matches the given country
	  * Arranges the data from lowest to highest temperature using a TreeSet<ITemperature>
	  * @param country the name of the given country
	  * @param rangeLowTemp, the lower-end of the range of temperatures in Celsius
	  * @param rangeHighTemp, the higher-end of the range of temperatures in Celsius
	  * @return TreeSet<ITemperature> containing the all the gathered temperature data, arranged from low to high
	  */
	 public TreeSet<ITemperature> getTempWithinRange(String country, double rangeLowTemp, double rangeHighTemp){
		ArrayList<ITemperature> range = new ArrayList<>();
		ITemperature inRange = data.get(0);
		
		//iterates through all the data, and checks whether the given values for country and the temperature is between the given range
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) {
				if(t.getTemperature(getFahrenheit) >= rangeLowTemp && t.getTemperature(getFahrenheit) <= rangeHighTemp) {
					inRange = t;		//if the temperature is within the given range,
					range.add(inRange); //that ITemperature object is added to the ArrayList<ITemperature> range 
				}
			}
		}
		
		//ArrayList<ITemperature> range is put into a TreeSet<ITemperature> sortedRange to sort it from low to high, and then returned
		TreeSet<ITemperature> sortedRange = new TreeSet<>(range);
		return sortedRange;
	 }
	 
	 /**
	  * (TASK A-4)
	  * Gets  the lowest temperature reading amongst all data for a given country
	  * @param the name of the given country
	  * @return the ITemperature object of the lowest temperature of the given country
	  */
	 public ITemperature getLowestTempYearByCountry(String country) {
		double minValue = Double.MAX_VALUE;
		ITemperature lowest = data.get(0);
		
		//iterates through all the data, and checks if the given country is matched
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) {
				if(t.getTemperature(getFahrenheit) <= minValue) { //if the given country is matched, 
					minValue = t.getTemperature(getFahrenheit);		//finds the lowest temperature in Celsius
					lowest = t;
				}
			}
		}
		return lowest; //returns the ITemperature object of the lowest temperature in Celsius
	 }
	 
	 /**
	  * (TASK A-4)
	  * Gets  the highest temperature reading amongst all data for a given country
	  * @param the name of the given country
	  * @return the ITemperature object of the highest temperature of the given country
	  */
	 public ITemperature getHighestTempYearByCountry(String country) {
		double maxValue = Double.MIN_VALUE;
		Temperature highest = (Temperature)data.get(0);
		
		//iterates through all the data, and checks if the given country is matched
		for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country)) {
				if(t.getTemperature(getFahrenheit) >= maxValue) { //if the given country is matched, 
					maxValue = t.getTemperature(getFahrenheit);		//finds the highest temperature in Celsius
					highest = (Temperature) t;
				}
			}
		}
		return highest; //returns the ITemperature object of the highest temperature in Celsius
	 }
	 
	 /**
	  * (TASK B-1)
	  * Gets an ArrayList of the Top 10 Lowest Temperature by a given month
	  * Arranges the ArrayList from low to high, using Collections.sort()
	  * @param the given month
	  * @return an ArrayList<ITemperature> arranged from lowest to highest
	  */
	 public ArrayList<ITemperature> allCountriesGetTop10LowestTemp(int month){
		//this ArrayList is used to store the lowest temperatures of each country in that given month
		 ArrayList<ITemperature> narrowedList = new ArrayList<>(); 
		 
		//this ArrayList is used in the for loop to make sure that the lowest temperature of each country
		//is calculated and added to narrowedList only once, allowing the end result to be filled with only unique countries
		 ArrayList<String> existingCountries = new ArrayList<>();

		 //iterates through all the data, and
		 //checks if a country's lowest temperature has been already calculated using existingCountries
		 for(ITemperature t: data) {
			 if(!existingCountries.contains(t.getCountry())) {
				 
				 //uses method from TASK A-1 to find the lowest Temperature of a given month in a given country
				 ITemperature lowest = getLowestTempByMonth(t.getCountry(), month);
				 
				 narrowedList.add(lowest); //adds calculated lowest Temperature to narrowedList
				 existingCountries.add(t.getCountry()); //adds a country to existingCountries once its data has been added to narrowedList
			}
		 }
		 
		 //sorts narrowedList from lowest to highest
		 Collections.sort(narrowedList);

		 //adds the first 10 lowest temperatures to solution and returns solution
		 ArrayList<ITemperature> solution = new ArrayList<>();
		 for(int i = 0; i < 10; i++) {
			 solution.add(narrowedList.get(i));
		 }
		 return solution;
	 }
	 
	 /**
	  * (TASK B-1)
	  * Gets an ArrayList of the Top 10 Highest Temperature by a given month
	  * Arranges the ArrayList from low to high, using Collections.sort()
	  * @param the given month
	  * @return an ArrayList<ITemperature> arranged from lowest to highest
	  */
	 public ArrayList<ITemperature> allCountriesGetTop10HighestTemp(int month){
		//this ArrayList is used in the for loop to store the highest temperatures of each country in that given month
		 ArrayList<ITemperature> narrowedList = new ArrayList<>();
		 
		//this ArrayList is used in the for loop to make sure that the highest temperature of each country
		//is calculated and added to narrowedList only once, allowing the end result to be filled with only unique countries
		 ArrayList<String> existingCountries = new ArrayList<>();
		 
		 //iterates through all the data, and
		 //checks if a country's highest temperature has been already calculated using existingCountries		 
		 for(ITemperature t: data) {
			 if(!existingCountries.contains(t.getCountry())) {
				//uses method from TASK A-1 to find the highest Temperature of a given month in a given country
				 ITemperature highest = getHighestTempByMonth(t.getCountry(), month);
				
				 narrowedList.add(highest);  //adds calculated highest Temperature to narrowedList
				 existingCountries.add(t.getCountry()); //adds a country to existingCountries once its data has been added to narrowedList
			}
		 }
		 
		//sorts narrowedList from lowest to highest
		 Collections.sort(narrowedList);
		 
		 //adds the first 10 highest temperatures (or last 10 lowest since sorted from low to high) to solution and returns solution
		 ArrayList<ITemperature> solution = new ArrayList<>();
		 for(int i = 10; i >= 1; i--) {
			 solution.add(narrowedList.get(narrowedList.size()-i));
		 }
		 return solution;
	 }
	 
	 /**
	  * (TASK B-2)
	  *  Gets an ArrayList of the countries with the Top 10 Lowest Temperatures
	  *  Arranges the ArrayList from lowest to highest, using Collections.sort
	  *  @return ArrayList<ITemperature>, arranged from low to high
	  */
	 public ArrayList<ITemperature> allCountriesGetTop10LowestTemp(){
		 //this ArrayList is used in the for loop to store the lowest temperatures of each country
		 ArrayList<ITemperature> narrowedList = new ArrayList<>();
		
		//this ArrayList is used in the for loop to make sure that the lowest temperature of each country
		//is calculated and added to narrowedList only once, allowing the end result to be filled with only unique countries		 
		 ArrayList<String> existingCountries = new ArrayList<>();
		 
		 //iterates through all the data, and calculates the lowest temperature for each unique country
		 for(ITemperature t: data) {
			 if(!existingCountries.contains(t.getCountry())) {
				 //uses method from TASK A-4 to find the lowest Temperature of a given country
				 ITemperature lowest = getLowestTempYearByCountry(t.getCountry());
				 narrowedList.add(lowest);
				 existingCountries.add(t.getCountry());
			}
		 }
		 
		//sorts narrowedList from lowest to highest
		 Collections.sort(narrowedList);
		 
		//adds the first 10 lowest temperatures to solution and returns solution
		 ArrayList<ITemperature> solution = new ArrayList<>();
		 for(int i = 0; i < 10; i++) {
			 solution.add(narrowedList.get(i));
		 }
		 return solution;
	 }
	 
	 
	 /**
	  * (TASK B-2)
	  *  Gets an ArrayList of the countries with the Top 10 Highest Temperatures
	  *  Arranges the ArrayList from lowest to highest, using Collections.sort
	  *  @return ArrayList<ITemperature>, arranged from low to high
	  */
	 public ArrayList<ITemperature> allCountriesGetTop10HighestTemp(){
		//this ArrayList is used in the for loop to store the highest temperatures of each country
		 ArrayList<ITemperature> narrowedList = new ArrayList<>();
		 
		//this ArrayList is used in the for loop to make sure that the highest temperature of each country
		//is calculated and added to narrowedList only once, allowing the end result to be filled with only unique countries			 
		 ArrayList<String> existingCountries = new ArrayList<>();
		 
		//iterates through all the data, and calculates the highest temperature for each unique country
		 for(ITemperature t: data) {
			 if(!existingCountries.contains(t.getCountry())) {
				 //uses method from TASK A-4 to find the highest Temperature of a given country
				 Temperature highest = (Temperature) getHighestTempYearByCountry(t.getCountry());
				 narrowedList.add(highest);
				 existingCountries.add(t.getCountry());
			}
		 }
		//sorts narrowedList from lowest to highest
		 Collections.sort(narrowedList);
		 
		 //adds the first 10 highest temperatures (or last 10 lowest since sorted from low to high) to solution and returns solution
		 ArrayList<ITemperature> solution = new ArrayList<>();
		 for(int i = 10; i >= 1; i--) {
			 solution.add(narrowedList.get(narrowedList.size()-i));
		 }
		 return solution;
	 }
	
	 /**
	  * (TASK B-3)
	  * Gets all the temperature data that falls within the given temperature range
	  * Arranges the data from lowest to highest temperature using Collections.sort()
	  * @param lowRangeTemp, the lower-end of the range of temperatures in Celsius
	  * @param highRangeTemp, the higher-end of the range of temperatures in Celsius
	  * @return ArrayList<ITemperature> containing the all the gathered temperature data, arranged from low to high
	  */
	 public ArrayList<ITemperature> allCountriesGetAllDataWithinTempRange(double lowRangeTemp, double highRangeTemp){
		//this ArrayList is used in the for loop to store all the temperatures within the range of each country
		 ArrayList<ITemperature> narrowedList = new ArrayList<>();
		 
		//this ArrayList is used in the for loop to make sure that all the temperatures within the range of each country
		//is calculated and added to narrowedList only once, allowing the end result to be filled with only unique countries		 
		 ArrayList<String> existingCountries = new ArrayList<>();
		 for(ITemperature t: data) {
			 if(!existingCountries.contains(t.getCountry())) {
				 
				 //uses method from TASK A-3 to find the all the temperatures within the range of a given country
				 TreeSet<ITemperature> list = getTempWithinRange(t.getCountry(), lowRangeTemp, highRangeTemp);
				 //adds all the ITemperature objects for each country from list to the narrowedList
				 for(ITemperature e: list) {
					 narrowedList.add(e);
				 }
				 existingCountries.add(t.getCountry());
			}
		 }
		//sorts narrowedList from lowest to highest, and returns it
		 Collections.sort(narrowedList);
		 return narrowedList;
	 }
	
	 /**
	  * (Helper Method for TASK C-1)
	  * Gets the ITemperature object that matches the given country, month, and year
	  * @param country the name of the given country
	  * @param the given month
	  * @param year the given year
	  * @return ITemperature object that matches the given parameters
	  */
	 public ITemperature getTemp(String country, int month, int year){
		 ITemperature temp = data.get(0);
		 for(ITemperature t: data) {
			if(t.getCountry().equalsIgnoreCase(country) && t.getMonth().equals(getMonthAsString(month)) && t.getYear() == year) {
				temp = t;
			}
		}
	 	return temp;
	 }
	 
	 /**
	  * (Helper Method for TASK C-1)
	  * Gets the largest temperature delta(absolute value) among the given ArrayList<String> countries for a given month, between two given years
	  * @param countries an ArrayList<String> of countries among which the country with highest temperature difference is calculated
	  * @param month the given month
	  * @param year1 one of the two different years for which the difference is calculated
	  * @param year2 other one of the two different years for which the difference is calculated
	  * @return ITemperature object of the country among ArrayList<String> countries with the largest temperature delta
	  */
	 public ITemperature getLargestTempDeltaByCountry(ArrayList<String> countries, int month, int year1, int year2) {
		 double maxDifference = Double.MIN_VALUE;
		 ITemperature tempDelta = data.get(0);
		 double difference = 0.0;
		 
		 //iterates through the given ArrayList of countries and calculates the country with the largest temperature difference
		 for(String country: countries) {
			 
			//for each country, gets the temperature for that specific month and year, using above helper method
			ITemperature allTempByYear2 = getTemp(country, month, year2);
			ITemperature allTempByYear1 = getTemp(country, month, year1); 
			
			//calculates the difference of temperature between the two years
			difference = Math.abs(allTempByYear1.getTemperature(getFahrenheit)-allTempByYear2.getTemperature(getFahrenheit));
			
			//Stores it if it's greater than the past differences
			if(difference > maxDifference) {
				maxDifference = difference;
				
				//creates a new Temperature object in order to store the maxDifference, and stores the new Temperature object in tempDelta
				tempDelta = new Temperature(maxDifference, year2 - year1, getMonthAsString(month), country, allTempByYear1.getCountry3LetterCode());
			}
		}
		return tempDelta;
	 }
	
	 /**
	  * (TASK C-1)
	  * Gets the Top 10 Countries with the largest temperature differences(absolute value) for a given month, between two given years
	  * and arranges it from lowest to highest temperature delta
	  * @param year1 one of the two different years for which the difference is calculated
	  * @param year2 other one of the two different years for which the difference is calculated
	  * @return ArrayList<ITemperature> the top 10 countries with the largest temperature delta, arranged low to high
	  */
	 public ArrayList<ITemperature> allCountriesTop10TempDelta(int month, int year1, int year2){
		//An arrayList initialized to all the countries in ArrayList<ITemperature> data
		ArrayList<String> existingCountries = listOfAllCountries();
		
		//this ArrayList is used in the for loop to make sure that the ITemperature objects with the largest temperature differences
		//is calculated and added to narrowedList only once, allowing the end result to be filled with only unique countries
		ArrayList<ITemperature> narrowedList = new ArrayList<>();
		
		//Loops 10 times to find the top 10 countries with largest temperature differences
		for(int count = 1; count <= 10; count++) {
			
			//gets the ITemperature object of the country with the largest Temperature Difference among the countries in existing countries
			ITemperature tempDelta = getLargestTempDeltaByCountry(existingCountries, month, year1, year2);
			
			//adds the ITemperature object to narrowedList
			narrowedList.add(tempDelta);
			
			//removes the country with the current largest temperature delta from the arrayList, 
			//to find the country with the next largest temperature delta
			existingCountries.remove(tempDelta.getCountry());
		}
		
		//Since ITemperature objects are added to narrowedList from high to low,
		//ITemperature objects are added to solution in a reversed(low to high) order and returned
		ArrayList<ITemperature> solution = new ArrayList<>();
		for(int i = 1; i <= 10; i++) {
			 solution.add(narrowedList.get(narrowedList.size()-i));
		}
		return solution;
	}
	
	 /**
	  * This method starts the climate-change task activities
	  * The ClimateChange methods are called in order as listed: TASK A, TASK B, and TASK C
	  * For each of the ClimateChange methods that require input parameters, this method asks the user 
	  * to enter the required information for each task.
	  * The returned data results from ClimateAnalyzer methods are written to a data file.
	  */
	 public void runClimateAnalyzer() {
		 try {
			 //creates a scanner
			 Scanner scan = new Scanner(System.in);
			 
			 //boolean used for User Input Validation, set as false by default
			 boolean isValid = false;
			 String errorMessage = ""; //error message printed when input is false, changed within each Task Section
			 
			//Each task follows the general pattern listed below:
			//1. print the task header at the start of every Task and SubTask, and reset the values for fileName, and topic in accordance with subtask
			//2. ask user input, if any required through do-while loops that allow for re-prompting if invalid
			//(NOTE: user input is asked for every Task, such as Task A, B, C and NOT for every Sub Task, A1, A2, A3...)
			//3. through helper methods like validMonth, validYear and so forth declared at the beginning of this class
			//	 the user input is checked, and if invalid, a specific error message is printed and the do-while loop
			//   mechanism allows for re-prompting.
			//4. Once user input passes validity checks, the corresponding climate analyzer method is called with the required 
			//	 user inputs as parameters and the result is stored in an ArrayList<ITemperature> named according to Sub Task
			//5. Set subject in accordance with subtask and call writeSubjectHeaderInFile with the proper subject and fileName parameters
			//6. call writeDataToFile with the proper topic, fileName, and result as a ArrayList<ITemperature>
			//7. Then, starts again with Next Task, and resets all variables that need to be reset
			 
			 
			 
			 //********** TASK-A ***********
			 System.out.println("Starting with Climate Change Task A (specific country searches)");
			 System.out.println();
			 
			 //The name of the fileName, topic, and subject of every task, 
			 //changed as needed within the section of each Task A1, A2, and so on...
			 String fileName = "data/taskA1_climate_info.csv";
			 String topic = "Temperature, Year, Month_Avg, Country, Country_Code";
			 String subject = "";
			 
			 //A1 - DONE
			 String country;
			 int month;
			 ArrayList<ITemperature> A1LowTemp, A1HighTemp;
			 int count = 0;
			 
			 System.out.println("TASK A1: Collecting data for the lowest and highest temperature reading for a given month...");
			 System.out.println();
			 
			 do{ 
				 if(count > 0) {
					 System.out.println();
					 System.out.println("Due to invalid inputs, re-prompting user inputs for TASK A1");
					 System.out.println();
				 }
				 
				 System.out.println("Enter the name of the country (Ex. India):");
				 country = scan.nextLine();
				 isValid = validCountry(country);
				 
				 if(!isValid) {
					errorMessage = "User input for country is invalid. Please enter a valid country with ONLY the first letter capitalized when re-prompted (Ex: India)";
					System.out.println(errorMessage);
					count++;
				 }
				 
				 else {
					 System.out.println("Enter the month between 1-12 (Ex. Enter 1 for January, etc.):");
					 String value = scan.nextLine();
					 isValid = validMonth(value);
					 
					 if(!isValid) {
						 errorMessage = "User input for month is invalid. Please enter a month(1-12) when re-prompted.";
						 System.out.println(errorMessage);
						 count++;
					 }
					 else {
						 month = Integer.parseInt(value);
						 Temperature lowestMonth = (Temperature)getLowestTempByMonth(country, month);
						 A1LowTemp = new ArrayList<ITemperature>();
						 A1LowTemp.add(lowestMonth);
						 
						 Temperature highestMonth = (Temperature)getHighestTempByMonth(country, month);
						 A1HighTemp = new ArrayList<ITemperature>();
						 A1HighTemp.add(highestMonth);
						 
						 subject = "Task A1: lowest temperature for " + country + " in " + getMonthAsString(month);
						 weather.writeSubjectHeaderInFile(fileName, subject);
						 weather.writeDataToFile(fileName, topic, A1LowTemp);
						 
						 
						 subject = "Task A1: highest temperature for " + country + " in " + getMonthAsString(month);
						 weather.writeSubjectHeaderInFile(fileName, subject);
						 weather.writeDataToFile(fileName, topic, A1HighTemp);
					 } 
				}
				 
			} while(!isValid);
			 
			 //A2-DONE
			 ArrayList<ITemperature> A2Low, A2High;
			 fileName = "data/taskA2_climate_info.csv";
			 int year;
			 count = 0;
			 
			 System.out.println("TASK A2: Collecting data for the lowest and highest temperature reading for a given year...");
			 System.out.println();
			 
			 do{ 
				 if(count > 0) {
					 System.out.println();
					 System.out.println("Due to invalid inputs, re-prompting user inputs for TASK A2...");
					 System.out.println();
				 }
				 
				 System.out.println("Enter a year between 2000 and 2016:");
				 String value = scan.nextLine();
				 isValid = validYear(value);
				 
			 
				 if(!isValid) {
					 errorMessage = "User input for year is invalid. Please enter a year between 2000 and 2016 when re-prompted.";
					 System.out.println(errorMessage);
					 count++;
				 }
				 else {
					 year = Integer.parseInt(value);
					 
					//here, country is used from the user input provided in A1 task (user-input asked once per Task, NOT sub Task)
					 Temperature lowestYear = (Temperature)getLowestTempByYear(country, year); 
					 A2Low = new ArrayList<ITemperature>();
					 A2Low.add(lowestYear);
					 
					//here, country is used from the user input provided in A1 task (user-input asked once per Task, NOT sub Task)
					 Temperature highestYear = (Temperature)getHighestTempByYear(country, year);
					 A2High= new ArrayList<ITemperature>();
					 A2High.add(highestYear);
					 
					 subject = "Task A2: lowest temperature for " + country + " in " + year;
					 weather.writeSubjectHeaderInFile(fileName, subject);
					 weather.writeDataToFile(fileName, topic, A2Low);
					 
					 
					 subject = "Task A2: highest temperature for " + country + " in " + year;
					 weather.writeSubjectHeaderInFile(fileName, subject);
					 weather.writeDataToFile(fileName, topic, A2High);
				 } 
				 
			} while(!isValid);

			 
			 //A3-DONE
			 //country is used from the user input provided in A1 task, (user-input asked once per Task, NOT sub Task)
			 ArrayList<ITemperature> A3;
			 fileName = "data/taskA3_climate_info.csv";
			 count = 0;
			 double lowTemp, highTemp;
			 
			 System.out.println("TASK A3: Collecting data that falls within a specific temperature range...");
			 System.out.println();
			 
			 do{ 
				 if(count > 0) {
					 System.out.println();
					 System.out.println("Due to invalid inputs, re-prompting user inputs for TASK A3...");
					 System.out.println();
				 }
				 
				 System.out.println("Enter the lower end of the temperature range (in Celsius):");
				 String value = scan.nextLine();
				 
				 System.out.println("Enter the higher end of the temperature range (in Celsius):");
				 String value2 = scan.nextLine();
				 
				 isValid = validTempRange(value, value2);
			 
				 if(!isValid) {
					 errorMessage = "User input for year is invalid. Please enter a valid temperature range with the lower end entered first when re-prompted.";
					 System.out.println(errorMessage);
					 count++;
				 }
				 else {
					 lowTemp = Double.parseDouble(value);
					 highTemp = Double.parseDouble(value2);
					 
					 //(user-input asked once per Task, NOT sub Task), country is used from the user input provided in A1 task
					 TreeSet<ITemperature> range = getTempWithinRange(country, lowTemp, highTemp);
					 A3 = new ArrayList<>(range);
					 
					 if(A3.size() == 0) {
						 isValid = false;						 
						 errorMessage = "WARNING: There are no temperatures within the specific range of " + Math.round(lowTemp * 100.0)/100.0 + "(C)-" + Math.round(highTemp * 100.0)/100.0 + "(C) for " + country;
						 System.out.println(errorMessage + ". Please enter a different range of temperatures when re-prompted.");
					 }
					 else {
						 subject = "Task A3: Temperatures within the specific range of " + Math.round(lowTemp * 100.0)/100.0 + "(C)-" + Math.round(highTemp * 100.0)/100.0 + "(C) for " + country;
						 weather.writeSubjectHeaderInFile(fileName, subject);
						 weather.writeDataToFile(fileName, topic, A3);
					 }
					 
				 }
				 
			} while(!isValid);
			 
			 //A4-DONE
			 //No additional User Inputs necessary, hence no user input validation employed
			 //country is used from the user input provided in A1 task, (user-input asked once per Task, NOT sub Task)
			 ArrayList<ITemperature> A4Low = new ArrayList<>();
			 ArrayList<ITemperature> A4High = new ArrayList<>();
			 fileName = "data/taskA4_climate_info.csv";
			 
			 System.out.println("TASK A4: Collecting data for the year with the lowest and highest temperature reading for a given country...");
			 System.out.println();
			 
			 Temperature lowest = (Temperature)getLowestTempYearByCountry(country);
			 A4Low.add(lowest);
			 
			 subject = "Task A4: The year with the lowest temperature for " + country;
			 weather.writeSubjectHeaderInFile(fileName, subject);
			 weather.writeDataToFile(fileName, topic, A4Low);
			 
			 Temperature highest = (Temperature)getHighestTempYearByCountry(country);
			 A4High.add(highest);
			 
			 subject = "Task A4: The year with the highest temperature for " + country;
			 weather.writeSubjectHeaderInFile(fileName, subject);
			 weather.writeDataToFile(fileName, topic, A4High);
			 
			 
			 //********** TASK-B ***********
			 System.out.println("Starting with Climate Change Task B (an inclusive all countries search)");
			 System.out.println();
			 
			//B1-DONE
			 ArrayList<ITemperature> B1Low, B1High;
			 count = 0;
			 fileName = "data/taskB1_climate_info.csv";
			 int monthB;
			 
			 System.out.println("TASK B1: Collecting data for the top 10 countries with lowest and highest temperature readings for a given month from 2000-2016");
			 System.out.println();
			 
			 do{ 
				 if(count > 0) {
					 System.out.println();
					 System.out.println("Due to invalid inputs, re-prompting user inputs for TASK A3...");
					 System.out.println();
				 }
				 
				 System.out.println("Enter the month between 1 and 12 (Ex. Enter 1 for January, etc.):");
				 String value = scan.nextLine();
				 isValid = validMonth(value);
			 
				 if(!isValid) {
					 errorMessage = "User input for month is invalid. Please enter a valid month between 1 and 12 when re-prompted.";
					 System.out.println(errorMessage);
					 count++;
				 }
				 else {
					 monthB = Integer.parseInt(value);
					 
					 B1Low = allCountriesGetTop10LowestTemp(monthB);
					 
					 subject = "Task B1: Top 10 countries with the lowest temperature reading for " + getMonthAsString(monthB) +" between 2000 and 2016";
					 weather.writeSubjectHeaderInFile(fileName, subject);
					 weather.writeDataToFile(fileName, topic, B1Low);
					 
					 B1High = allCountriesGetTop10HighestTemp(monthB);
					 
					 subject = "Task B1: Top 10 countries with the highest temperature reading for " + getMonthAsString(monthB) +" between 2000 and 2016";
					 weather.writeSubjectHeaderInFile(fileName, subject);
					 weather.writeDataToFile(fileName, topic, B1High);
				 } 
				 
			} while(!isValid);
			 
			 //B2 - DONE
			 //No additional User Inputs necessary, hence no user input validation employed//
			 ArrayList<ITemperature> B2Low, B2High;
			 fileName = "data/taskB2_climate_info.csv";
			 
			 System.out.println("TASK B2: Collecting data for the top 10 countries with lowest and highest temperature readings from 2000-2016");
			 System.out.println();
			 
			 B2Low = allCountriesGetTop10LowestTemp();
			 
			 subject = "Task B2: Top 10 countries with the lowest temperature from 2000 and 2016";
			 weather.writeSubjectHeaderInFile(fileName, subject);
			 weather.writeDataToFile(fileName, topic, B2Low);
			 
			 B2High = allCountriesGetTop10HighestTemp();
			 
			 subject = "Task B2: Top 10 countries with the highest temperature reading from 2000 and 2016";
			 weather.writeSubjectHeaderInFile(fileName, subject);
			 weather.writeDataToFile(fileName, topic, B2High);
			 
			 System.out.println("so far so good");
			 
			 //B3-DONE
			 ArrayList<ITemperature> B3;
			 count = 0;
			 fileName = "data/taskB3_climate_info.csv";
			 double lowTempB, highTempB;
			 
			 System.out.println("TASK B3: Collecting data for the list of all countries that fall within a specific range");
			 System.out.println();
			 
			 do{ 
				 if(count > 0) {
					 System.out.println();
					 System.out.println("Due to invalid inputs, re-prompting user inputs for TASK A3...");
					 System.out.println();
				 }
				 
				 System.out.println("Enter the lower end of the temperature range (in Celsius):");
				 String value = scan.nextLine();
				 
				 System.out.println("Enter the higher end of the temperature range (in Celsius):");
				 String value2 = scan.nextLine();
				 
				 isValid = validTempRange(value, value2);
			 
				 if(!isValid) {
					 errorMessage = "User input for year is invalid. Please enter a valid temperature range with the lower end entered first when re-prompted.";
					 System.out.println(errorMessage);
					 count++;
				 }
				 else {
					 lowTempB = Double.parseDouble(value);
					 highTempB = Double.parseDouble(value2);
					 
					 B3 = allCountriesGetAllDataWithinTempRange(lowTempB, highTempB);
					 
					 if(B3.size() == 0) {
						 isValid = false;
						 errorMessage = "WARNING: There are no temperatures within the specific range of " + Math.round(lowTempB * 100.0)/100.0 + "(C)-" + Math.round(highTempB * 100.0)/100.0 + "(C) for " + country;
						 System.out.println(errorMessage + ". Please enter a different range of temperatures when re-prompted.");
					 }
					 else {
						 subject = "Task B3: List all of the countries that fall within a specific temperature range of " + + Math.round(lowTempB * 100.0)/100.0 + "(C)-" + Math.round(highTempB * 100.0)/100.0 + "(C)";
						 weather.writeSubjectHeaderInFile(fileName, subject);
						 weather.writeDataToFile(fileName, topic, B3);
					 }
				 } 
			} while(!isValid);
			 
			//********** TASK-C ***********
			 System.out.println("Starting with Climate Change Task C (Climate Change Detection)");
			 System.out.println();
			 
			 //C1-DONE
			 ArrayList<ITemperature> C1;
			 count = 0;
			 int monthC;
			 fileName = "data/taskC1_climate_info.csv";
			 topic = "Temperature Delta, Year Delta, Month_Avg, Country, Country_Code";
			 
			 System.out.println("TASK C1: Collecting data for the top 10 countries with the largest change in temperature in the same month between two different given years");
			 System.out.println();
			 
			 do{ 
				 if(count > 0) {
					 System.out.println();
					 System.out.println("Due to invalid inputs, re-prompting user inputs for TASK A1");
					 System.out.println();
				 }
				 
				 System.out.println("Enter the month between 1-12 (Ex. Enter 1 for January, etc.):");
				 String value = scan.nextLine();
				 isValid = validMonth(value);
				 
				 if(!isValid) {
					 errorMessage = "User input for month is invalid. Please enter a month(1-12) when re-prompted.";
					 System.out.println(errorMessage);
					 count++;
				 }
				 else {
					 monthC = Integer.parseInt(value);
					 System.out.println("Enter the first of the two different years (2000-2016):");
					 String value1 = scan.nextLine();
					 
					 System.out.println("Enter the second of the two different years (2000-2016):");
					 String value2 = scan.nextLine();
					 
					 isValid = validYear(value1) && validYear(value2);
					 
					 if(!isValid) {
						 errorMessage = "User input for either/both year(s) is invalid. Please enter a year between 2000 and 2016, with the lower end entered first when re-prompted.";
						 System.out.println(errorMessage);
						 count++;
					 }
					 else {
						 int year1 = Integer.parseInt(value1);
						 int year2 = Integer.parseInt(value2);
						 
						 C1 = allCountriesTop10TempDelta(monthC, year1, year2);
						 
						 subject = "Task C1: List of the top 10 countries with the largest change in temperature in " + getMonthAsString(monthC) + " between " + year1 + " and " + year2;
						 weather.writeSubjectHeaderInFile(fileName, subject);
						 weather.writeDataToFile(fileName, topic, C1);
					 }
				 }
				 
			}while(!isValid);
			 
			//ALL TASKS COMPLETED... 
			System.out.println("All tasks are completed...YAY!");
		 }
		 catch(Exception e) {
			 System.out.println(e.getMessage()+ e.getClass());
		 }
		 
	 }
	 
	 public static void main(String[] args) {
		 ClimateAnalyzer tester = new ClimateAnalyzer("data/world_temp_2000-2016.csv");
		 tester.runClimateAnalyzer();
 
	 }
}
