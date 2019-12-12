package simulator;

import java.io.*;
import simulator.vehicles.*;
//import weather.WeatherProvider;

public class Simulator {
    public static PrintWriter writer;
    public static int cycles;

    public static void main(String[] args)
    {
        if (args.length < 1)
            return;

        String filename = args[0];

        File simulationFile = new File("simulation.txt");
        try {
            writer = new PrintWriter(simulationFile);
        } catch (FileNotFoundException e) {
            System.out.println("Simulation file exception error: " + e.getMessage());
            return;
        }
        if (simulationFile.exists() && !simulationFile.isDirectory())
            writer.print("");

        AircraftFactory aircraftFactory = new AircraftFactory();
        WeatherTower weatherTower = new WeatherTower();
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            int line = 1;
            String[] splitted;

            while ((strLine = br.readLine()) != null)
            {
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
                    splitted = strLine.split(" ");
                    if (splitted.length == 1 && splitted[0].isEmpty())
                        continue;

                    if (splitted.length != 5)
                        throw new Exception("Error: line " + line + ": there must be 5 parameters.");

                    try {
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

        while (cycles > 0)
        {
            weatherTower.changeWeather();
            cycles--;
        }

        writer.close();
    }

}
