package simulator;

import weather.Coordinates;
import weather.WeatherProvider;

import java.io.PrintWriter;

public class WeatherTower extends Tower {

    public static  PrintWriter writer;

    public String getWeather(Coordinates coordinates)
    {
        return WeatherProvider.getProvider().getCurrentWeather(coordinates);
    }

    void changeWeather()
    {
        this.conditionChanged();
    }
}
