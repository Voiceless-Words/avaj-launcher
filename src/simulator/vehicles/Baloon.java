package simulator.vehicles;

import simulator.Simulator;
import simulator.WeatherTower;
import weather.Coordinates;
import weather.WeatherProvider;
import java.util.HashMap;

class Baloon extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    Baloon(String name, Coordinates coordinates)
    {
        super(name, coordinates);
        this.name = name;
        this.coordinates = coordinates;
    }

    public void updateConditions() {
        String weather = WeatherProvider.getProvider().getCurrentWeather(this.coordinates);
        HashMap<String, String> messages = new HashMap<String, String>() {{
            put("SUN", "Let's enjoy this weather and the moment it is very nice.");
            put("RAIN", "Bad day it is, being romantic sucks huh");
            put("FOG", "Where are we going, where are we hope we don't land on a tree");
            put("SNOW", "I hope this don't put holes in my baloon.");
        }};

            if (weather.equals("SUN"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude() + 2, coordinates.getLatitude(), coordinates.getHeight() + 4);
            } else if (weather.equals("RAIN"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude(), coordinates.getHeight() - 5);
            } else if (weather.equals("FOG"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude(), coordinates.getHeight() - 3);
            } else if (weather.equals("SNOW"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude(), coordinates.getHeight() - 13);
            }

        Simulator.writer.println("Baloon#" + this.name + "(" + this.id + "): " + messages.get(weather));

            if (this.coordinates.getHeight() == 0)
            {

                Simulator.writer.println("Baloon#" + this.name + "(" + this.id + "): landing.");
                this.weatherTower.unregister(this);
                Simulator.writer.println("Tower sends that: Baloon#" + this.name + "(" + this.id + ")" + " unregistered from tower.");
            }
        }

        public void registerTower(WeatherTower weatherTower) {

            this.weatherTower = weatherTower;
            weatherTower.register(this);
            Simulator.writer.println("Tower sends that: Baloon#" + this.name + "(" + this.id + ")" + " registered to tower.");
        }

}
