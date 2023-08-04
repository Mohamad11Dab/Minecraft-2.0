package researchsim.logging;
import researchsim.entities.Fauna;
import researchsim.entities.Size;
import researchsim.entities.User;
import researchsim.logging.Event;
import researchsim.map.Coordinate;
import researchsim.map.TileType;
import java.util.StringJoiner;


import java.util.*;

/**
 * A detailed log that contains a record of {@link Event}s and contains some event statistics.
 *
 * @ass2
 */
public class Logger {
    private int entityCollected;
    private int tilesTraversed;
    private int pointsEarned;
    private List<Event> list;

    public Logger() {
        this.entityCollected = 0;
        this.tilesTraversed = 0;
        this.pointsEarned = 0;
        this.list = new ArrayList<>();


    }

    public int getTilesTraversed() {
        return  this.tilesTraversed;
    }

    public int getEntitiesCollected() {
        return this.entityCollected;
    }

    public int getPointsEarned() {
        return this.pointsEarned;
    }

    public List<Event> getEvents() {
        return list;

    }


    public void add(Event event) {
        this.list.add(event);
        if (event instanceof MoveEvent) {
            Coordinate coordinate = event.getInitialCoordinate().distance(event.getCoordinate());
            int tiles = Math.abs(coordinate.getX()) + Math.abs(coordinate.getY());
            this.tilesTraversed = this.tilesTraversed + tiles;
        } else if (event instanceof CollectEvent) {
            this.entityCollected = this.entityCollected + 1;
            this.pointsEarned = event.getEntity().getSize().points;

        }
    }
    public String toString() {
       /* StringBuilder sb = new StringBuilder();
        for(int i=0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            sb.append(System.getProperty("line.separator"));*/
        StringJoiner result = new StringJoiner(System.lineSeparator());
        for (int i=0; i < list.size(); i++) {
            result.add(list.get(i).toString());
        }

        return result.toString();



    }
    public static void main (String ars[]) {
        Logger logger = new Logger();
        Coordinate coordinate = new Coordinate(3,2);
        Coordinate destination = new Coordinate(3,4);
        Fauna fauna = new Fauna(Size.MEDIUM,coordinate, TileType.LAND);
        Fauna hayawen = new Fauna(Size.SMALL,destination,TileType.LAND);
        User dave = new User(coordinate,"Dave");
        logger.add(new MoveEvent(fauna,destination));
        logger.add(new MoveEvent(hayawen,coordinate));
        logger.add(new MoveEvent(hayawen,destination));
        logger.add(new CollectEvent(dave,hayawen));
        System.out.println(logger.toString());
    }


}
