package simulator;

import java.io.*;
import simulator.vehicles.*;
//import weather.WeatherProvider;

public class Simulator {
    public static PrintWriter writer;
    public static int cycles;

    public static void main(String[] args)
    {
        //if we do not have any arguments passed to the program just return and exit
        if (args.length < 1)
            return;

        //the input argument file
        String filename = args[0];

        //creating the file to print out our output from the program itself
        File simulationFile = new File("simulation.txt");
        try {
            writer = new PrintWriter(simulationFile);
        } catch (FileNotFoundException e) {
            System.out.println("Simulation file exception error: " + e.getMessage());
            return;
        }
        if (simulationFile.exists() && !simulationFile.isDirectory())
            writer.print("");

        //creating instances of our classes AircraftFactory and WeatherTower
        AircraftFactory aircraftFactory = new AircraftFactory();
        WeatherTower weatherTower = new WeatherTower();
        try {
            //Reading from the argument file
            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            int line = 1;
            String[] splitted;

            while ((strLine = br.readLine()) != null)
            {
                // Checking the first line is an integer.
                if (line == 1)
                    try {
                        cycles = Integer.parseInt(strLine);
                        if (cycles < 0)
                        {
                            System.out.println("Error: first line of scenario file must be a POSITIVE integer.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: first line of scenario file must be an integer.");
                        return;
                    }
                else
                {
                    //splitting the string
                    splitted = strLine.split(" ");
                    //skipping the empty lines
                    if (splitted.length == 1 && splitted[0].isEmpty())
                        continue;
                    //check for correct number of parameters which is 5
                    if (splitted.length != 5)
                        throw new Exception("Error: line " + line + ": there must be 5 parameters.");

                    try {
                        //creating the new aircraft from the splitted string and registering it to the tower
                        aircraftFactory.newAircraft(
                                splitted[0],
                                splitted[1],
                                Integer.parseInt(splitted[2]),
                                Integer.parseInt(splitted[3]),
                                Integer.parseInt(splitted[4])
                        ).registerTower(weatherTower);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: line " + line + ": parameter 3 to 5 must be integers.");
                        return;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return;
                    }
                }
                line++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

       // WeatherProvider weatherProvider = WeatherProvider.getProvider();
        //running the number of circles and changing the weather
        while (cycles > 0)
        {
            weatherTower.changeWeather();
            cycles--;
        }

        //closing the writer
        writer.close();
    }

}
