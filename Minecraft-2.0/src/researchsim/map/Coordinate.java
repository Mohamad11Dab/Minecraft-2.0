package researchsim.map;

import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.BadSaveException;
import researchsim.util.Encodable;

/**
 * A coordinate is a representation of the  X and Y positions on a graphical map.<br>
 * This X, Y position can be used to calculate the index of a Tile in the scenario tile map
 * depending on the currently active scenario. <br>
 * The X and Y positions will not change but the index will depending on the current scenario.
 * <p>
 * A coordinate is similar to a point on the cartesian plane.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 * @ass1_test_partial
 */
public class Coordinate implements Encodable {

    /**
     * The position in the Horizontal plane (Left-Right).
     */
    private final int xcoord;

    /**
     * The position in the Vertical plane (Up-Down).
     */
    private final int ycoord;

    /**
     * Creates a new coordinate at the top left position (0,0), index 0 (zero).
     *
     * @ass1
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Creates a new coordinate at the specified (x,y) position.
     *
     * @param xcoord horizontal position
     * @param ycoord vertical position
     * @ass1
     */
    public Coordinate(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * Creates a new coordinate at the specified index.
     *
     * @param index index in the tile grid
     * @ass1
     */
    public Coordinate(int index) {
        int width = ScenarioManager.getInstance().getScenario().getWidth();
        this.xcoord = index % width;
        this.ycoord = index / width;
    }

    /**
     * The position in the Horizontal plane (Left-Right)
     *
     * @return the horizontal position
     * @ass1
     */
    public int getX() {
        return xcoord;
    }

    /**
     * The position in the Vertical plane (Up-Down)
     *
     * @return the vertical position
     * @ass1
     */
    public int getY() {
        return ycoord;
    }

    /**
     * The index in the tile grid of this coordinate.
     *
     * @return the grid index
     * @ass1
     */
    public int getIndex() {
        return Coordinate.convert(xcoord, ycoord);
    }

    /**
     * Determines if the coordinate in the bounds of the current scenario map
     *
     * @return true, if 0 &le; coordinate's x position &lt; current scenarios' width AND 0 &le;
     * coordinate's y position &lt; current scenarios' height
     * else, false
     * @ass1
     */
    public boolean isInBounds() {
        Scenario scenario = ScenarioManager.getInstance().getScenario();
        return xcoord < scenario.getWidth() && xcoord >= 0
            && ycoord < scenario.getHeight() && ycoord >= 0;
    }

    /**
     * Utility method to convert an (x,y) integer pair to an array index location.
     *
     * @param xcoord the x portion of a coordinate
     * @param ycoord the y portion of a coordinate
     * @return the converted index
     * @ass1
     */
    public static int convert(int xcoord, int ycoord) {
        return xcoord + ycoord * ScenarioManager.getInstance().getScenario().getWidth();
    }

    /**
     * Returns the human-readable string representation of this Coordinate.
     * <p>
     * The format of the string to return is:
     * <pre>(x,y)</pre>
     * Where:
     * <ul>
     *   <li>{@code x} is the position in the Horizontal plane (Left-Right)</li>
     *   <li>{@code y} is the position in the Vertical plane (Up-Down)</li>
     * </ul>
     * For example:
     *
     * <pre>(1,3)</pre>
     *
     * @return human-readable string representation of this Coordinate.
     * @ass1
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)",
            this.xcoord, this.ycoord);
    }
    public Coordinate distance(Coordinate other) {
        return new Coordinate(other.getX() - this.getX(),  other.getY() - this.getY());

    }
    public Coordinate translate(int x, int y) {
        return new Coordinate(this.getX() + x, this.getY() + y);

    }
    public String encode() {
        return this.getX() + "," + this.getY();
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + xcoord;
        result = prime * result + ycoord;
        return result;

    }
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null
                || this.getClass() != other.getClass()) {
            return false;
        }
        Coordinate a = (Coordinate) other;
        return this.xcoord == a.xcoord
                && this.ycoord == a.ycoord;
    }
    public static Coordinate decode(String encoded) throws BadSaveException {


        String[] toks = encoded.split(",");
        int commas = 0;
        for (int j = 0; j < encoded.length(); j++) {
            if (encoded.charAt(j) == ',') {
                commas++;
            }
        }
        if (commas != 1) {
            throw new BadSaveException();
        }
        try {
            int i = Integer.parseInt(toks[0]);
            int k = Integer.parseInt(toks[1]);
            return new Coordinate(i, k);
        } catch (NumberFormatException e) {
            throw new BadSaveException();
        }
    }
    /*public static void main (String args[]) throws BadSaveException {
        Coordinate coordinate = new Coordinate(3, 4);
        Coordinate test = coordinate.decode("(l,3)");
        System.out.print(test.getX());
    }*/


}
