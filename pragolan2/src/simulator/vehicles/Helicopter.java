package simulator.vehicles;

import simulator.Simulator;
import simulator.WeatherTower;
import weather.Coordinates;
import weather.WeatherProvider;
import java.util.HashMap;

public class Helicopter extends Aircraft implements Flyable{

    private WeatherTower weatherTower;

    Helicopter(String name, Coordinates coordinates)
    {
        super(name, coordinates);
        this.name = name;
        this.coordinates = coordinates;

    }

    public void updateConditions() {

        String weather = WeatherProvider.getProvider().getCurrentWeather(this.coordinates);

        HashMap<String, String> messages = new HashMap<String, String>() {{
            put("SUN", "This weather is too hot even the open doors are not helping.");
            put("RAIN", "Why rain now though?!!");
            put("FOG", "It is Stevie Wonder in this biz.");
            put("SNOW", "My hands are freezing where are my gloves.");
        }};
        if (weather.equals("SUN"))
        {
            this.coordinates = new Coordinates(coordinates.getLongitude() + 10, coordinates.getLatitude(), coordinates.getHeight() + 2);
        } else if (weather.equals("RAIN"))
        {
            this.coordinates = new Coordinates(coordinates.getLongitude() + 5, coordinates.getLatitude(), coordinates.getHeight());
        } else if (weather.equals("FOG"))
        {
            this.coordinates = new Coordinates(coordinates.getLongitude() + 1, coordinates.getLatitude(), coordinates.getHeight());
        } else if (weather.equals("SNOW"))
        {
            this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude(), coordinates.getHeight() - 12);
        }

        Simulator.writer.println("Helicopter#" + this.name + "(" + this.id + "): " + messages.get(weather));
        if (this.coordinates.getHeight() == 0)
        {
            Simulator.writer.println("Helicopter#" + this.name + "(" + this.id + "): landing");
            this.weatherTower.unregister(this);
            Simulator.writer.println("Tower sends that: Helicopter#" + this.name + "(" + this.id + ")" + "unregistered from tower");
        }

    }

    public void registerTower(WeatherTower weatherTower) {

        this.weatherTower = weatherTower;
        weatherTower.register(this);
        Simulator.writer.println("Tower sends that: Helicopter#" + this.name + "(" + this.id + ")" + "registered to tower.");

    }
}
