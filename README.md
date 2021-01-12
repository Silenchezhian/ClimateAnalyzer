# Climate Change Analyzer
Climate Change Analyzer is an object-oriented Java program that performs various climate change analyzing tasks on a given CSV with the lowest and highest temperature in each month between 2000 and 2016 in every country in the world.

# Description
To run the program, run '''ClimateAnalyzer.java''', and respond to the prompted questions in the console, appropriately. If invalid responses are given, the program will reprompt the user to answer accordingly. 
Below is a list of tasks performed by the following program:

**TASK A: Specific Country Searches**
1. For a specific country, the program will collect data for the lowest and highest temperature reading in a given month
2. For a specific country, the program will collect data for the lowest and highest temperature reading in a given year
3. For a specific country, the program will collect all data that falls within a given temperature range

**TASK B: An Inclusive All Country Search**
1. The program will collect data for the top 10 countries with lowest and highest temperature readings for a given month from 2000-2016
2. The program will collect data for the top 10 countries with lowest and highest temperature readings from 2000-2016
3. The program will collect data for the list of all countries that fall within a given temperature range

**TASK C: Climate Change Detection**
1. The program will collect data for the top 10 countries with the largest change in temperature in the same month between two different given years

The results of each task/subtask will be generated in a separate CSV file with a file name of ```task<task number>_climate_info.csv``` in the data folder. By the time the termination message is printed on the console, 7 CSV files with appropriate names would have been generated with the results of each task performed. 
