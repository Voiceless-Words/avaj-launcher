package simulator.vehicles;

import simulator.Simulator;
import simulator.WeatherTower;
import weather.Coordinates;
import weather.WeatherProvider;
import java.util.HashMap;
public class JetPlane extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    JetPlane(String name, Coordinates coordinates)
    {
        super(name, coordinates);
        this.name = name;
        this.coordinates = coordinates;

    }

    public void updateConditions() {

            String weather = WeatherProvider.getProvider().getCurrentWeather(this.coordinates);

        HashMap<String, String> messages = new HashMap<String, String>() {{
            put("SUN", "Please turn on the air conditioner");
            put("RAIN", "It is raining this is not good I am scared of lightnings");
            put("FOG", "Now we have a problem I can hardly see where I am going.");
            put("SNOW", "This is some baby making weather mile-high club");
        }};

            if (weather.equals("SUN"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude() + 10, coordinates.getHeight() + 2);
            } else if (weather.equals("RAIN"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude() + 5, coordinates.getHeight());
            } else if (weather.equals("FOG"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude() + 1, coordinates.getHeight());
            } else if (weather.equals("SNOW"))
            {
                this.coordinates = new Coordinates(coordinates.getLongitude(), coordinates.getLatitude(), coordinates.getHeight() - 7);
            }

           Simulator.writer.println("JetPlane#" + this.name + "(" + this.id + "): " + messages.get(weather));
            if (this.coordinates.getHeight() == 0)
            {
                Simulator.writer.println("JetPlane#" + this.name + "(" + this.id + "): landing.");
                this.weatherTower.unregister(this);
                Simulator.writer.println("Tower sends that: JetPlane#" + this.name + "(" + this.id + ")" + "unregistered from tower.");
            }
        }

        public void registerTower(WeatherTower weatherTower) {

            this.weatherTower = weatherTower;
            weatherTower.register(this);
            Simulator.writer.println("Tower sends that: JetPlane#" + this.name + "(" + this.id + ")" + "registered to tower.");

        }
}
