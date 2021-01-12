package climatechange;

import java.util.*;

import java.io.*;

public class WeatherIO implements IWeatherIO {

	/**
	 * Reads all data from the weather data file
	 * @param filename the the directory/name of the file from which the data is read
	 * @return ArrayList<ITemperature> of all the data with each line stored as an ITemperature object
	 */
	public ArrayList<ITemperature> readDataFromFile(String fileName) throws FileNotFoundException{
		ArrayList<ITemperature> data = new ArrayList<ITemperature>();
		
		File inputFile = new File(fileName);
		Scanner scan = new Scanner(inputFile);
		
		String line = scan.nextLine(); //skips over the topic line (the first line) of the input file
		
		//As long there is another line of data, reads it and stores it as an ITemperature object
		while(scan.hasNextLine()) {
			line = scan.nextLine();
			
			//calls readDataFromLine() to store a line of data as a ITemperature object
			ITemperature value = readDataFromLine(line); 
			if(value != null) { //adds the ITemperature object to a data as long as it is not null
				data.add(value);
			}
		}
		
		scan.close(); //closes scanner
		
		return data; //returns an ArrayList<ITemperature> of all the data
		
	}
	
	/**
	 * (Helper Method for readDataFromFile)
	 * Stores a line of data as an ITemperature object
	 * @param line the line of data to be stored
	 * @return an ITemperature object of the given line
	 */
	public ITemperature readDataFromLine(String line) {
		//checks if line is null
		if(line == null) {
			return null;
		}
		//if line is not null, splits line into the parameters for the Temperature object
		//and returns it as an ITemperature object
		String[] temp = line.split(",");
		double temperature = Double.parseDouble(temp[0].trim());
		int year = Integer.parseInt(temp[1].trim());
		String month = temp[2].trim();
		String country = temp[3].trim();
		String countryCode = temp[4].trim();
		ITemperature value = new Temperature(temperature, year, month, country, countryCode);
		//System.out.print(value);
		return value;
	}
	 
	/**
	 * Writes the subject header for each ClimateAnalyzer method call before dumping data returned from each ClimateAnalyzer method
	 */
	 public void writeSubjectHeaderInFile(String filename, String subject)  {
		 //creates a File and PrintWriter
		 File outputFile = new File(filename);
		 PrintWriter pw;
		
		 try {
			 //if File already exists, adds to the existing file using a FileWriter
			if(outputFile.exists()) {
				FileWriter fr = new FileWriter(outputFile, true);
				pw = new PrintWriter(fr);
				pw.println(subject);
				fr.close();
			}
			//if File does not exist, writes to a new File
			else {
				pw = new PrintWriter(outputFile);
				pw.println(subject);
				
			}
			pw.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	 }

	 /**
	  * Writes the given ArrayList<ITemperature> returned from each Climate Analyzer task in a csv format output file with the given name
	  * Each ITemperature object is printed in one row in the expected output format
	  * Temperature values are formatted to use a maximum of 2 decimal places, and include both Fahrenheit and Celsius
	  * @param filename the given filename in runClimateAnalyzer() method
	  * @param topic the header for each value as given in runClimateAnalyzer() method
	  * @param theWeatherList the returned ArrayList<ITemperature> from each ClimateAnalyzer
	  */
	 public void writeDataToFile(String filename, String topic, ArrayList<ITemperature> theWeatherList) throws FileNotFoundException, IOException {
		 File outputFile = new File(filename);
		 FileWriter fr = new FileWriter(outputFile, true);
		 PrintWriter pw = new PrintWriter(fr);
		 pw.println(topic);
		 
		 //iterates through all the ITemperature objects in theWeatherList and prints it in the proper csv format
		 for(ITemperature c: theWeatherList) {
			 pw.println((Math.round(c.getTemperature(false) * 100.0)/100.0) + "(C) " + (Math.round(c.getTemperature(true) * 100.0)/100.0) + "(F)" + "," + c.getYear() + "," + c.getMonth()+ "," + c.getCountry() + "," + c.getCountry3LetterCode());
		 }
		 //closes PrintWriter and FileWriter
		 pw.close();
		 fr.close();
	 }

}
	 

