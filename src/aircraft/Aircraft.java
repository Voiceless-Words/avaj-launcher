package aircraft;

public class Aircraft {

    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private  long idCounter;

    protected Aircraft(String name, Coordinates coordinates){
        System.out.println(("The aircraft was created"));
    }

}

class Hellicopter {
    private WeatherTower weatherTower;

    
}