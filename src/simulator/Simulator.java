package simulator;

import java.io.*;

public class Simulator {

    public static void main(String[] args) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(args[0]));

            String line = reader.readLine();
            int numberOfSimulation = Integer.parseInt(line);

            if (line != null) {
                System.out.println(numberOfSimulation);
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);

                }
            }

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));

            String nL = "Hello world";
            out.write(nL);
            out.close();

        } catch (FileNotFoundException e) {

            System.out.println("File was not found are you sure you have the right file?");

        } catch (IOException e) {

            System.out.println("There is an error trying to read the file");

        } catch (ArrayIndexOutOfBoundsException e){

            System.out.println("Are you sure there is something in the argument list");

        } catch (Exception e) {

            System.out.println("You are getting this exception "+ e);

        }

    }

}