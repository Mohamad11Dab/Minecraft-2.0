package researchsim.entities;

import researchsim.logging.MoveEvent;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.ScenarioManager;
import researchsim.util.Collectable;
import researchsim.util.CoordinateOutOfBoundsException;
import researchsim.util.Movable;

import java.util.ArrayList;
import java.util.List;


/**
 * Fauna is all the animal life present in a particular region or time.
 * Fauna can move around the scenario and be collected by the {@link User}.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 * @ass1_test
 */
public class Fauna extends Entity implements Movable, Collectable {

    /**
     * The habitat associated with the animal.
     * That is, what tiles an animal can exist in.
     */
    private final TileType habitat;

    /**
     * Creates a fauna (Animal) with a given size, coordinate and habitat.
     *
     * @param size       size associated with the animal
     * @param coordinate coordinate associated with the animal
     * @param habitat    habitat tiles associated with the animal
     * @throws IllegalArgumentException if habitat is not {@link TileType#LAND} or
     *                                  {@link TileType#OCEAN}
     * @ass1
     */
    public Fauna(Size size, Coordinate coordinate, TileType habitat)
        throws IllegalArgumentException {
        super(size, coordinate);
        if (habitat != TileType.LAND && habitat != TileType.OCEAN) {
            throw new IllegalArgumentException("Animal was created with a bad habitat: " + habitat);
        }
        this.habitat = habitat;
    }

    /**
     * Returns the animal's habitat.
     *
     * @return animal's habitat
     * @ass1
     */
    public TileType getHabitat() {
        return habitat;
    }

    /**
     * Returns the human-readable name of this animal.
     * The name is determined by the following table.
     * <p>
     * <table border="1">
     *     <caption>Human-readable names</caption>
     *     <tr>
     *         <td rowspan="2" colspan="2" style="background-color:#808080">&nbsp;</td>
     *         <td colspan="3">Habitat</td>
     *     </tr>
     *     <tr>
     *         <td>LAND</td>
     *         <td>OCEAN</td>
     *     </tr>
     *     <tr>
     *         <td rowspan="4">Size</td>
     *         <td>SMALL</td>
     *         <td>Mouse</td>
     *         <td>Crab</td>
     *     </tr>
     *     <tr>
     *         <td>MEDIUM</td>
     *         <td>Dog</td>
     *         <td>Fish</td>
     *     </tr>
     *     <tr>
     *         <td>LARGE</td>
     *         <td>Horse</td>
     *         <td>Shark</td>
     *     </tr>
     *     <tr>
     *         <td>GIANT</td>
     *         <td>Elephant</td>
     *         <td>Whale</td>
     *     </tr>
     * </table>
     * <p>
     * e.g. if this animal is {@code MEDIUM} in size and has a habitat of {@code LAND} then its
     * name would be {@code "Dog"}
     *
     * @return human-readable name
     * @ass1
     */
    @Override
    public String getName() {
        String name;
        switch (getSize()) {
            case SMALL:
                name = habitat == TileType.LAND ? "Mouse" : "Crab";
                break;
            case MEDIUM:
                name = habitat == TileType.LAND ? "Dog" : "Fish";
                break;
            case LARGE:
                name = habitat == TileType.LAND ? "Horse" : "Shark";
                break;
            case GIANT:
            default:
                name = habitat == TileType.LAND ? "Elephant" : "Whale";
        }
        return name;
    }

    /**
     * Returns the human-readable string representation of this animal.
     * <p>
     * The format of the string to return is:
     * <pre>name [Fauna] at coordinate [habitat]</pre>
     * Where:
     * <ul>
     *   <li>{@code name} is the animal's human-readable name according to {@link #getName()}</li>
     *   <li>{@code coordinate} is the animal's associated coordinate in human-readable form</li>
     *   <li>{@code habitat} is the animal's associated habitat</li>
     *
     * </ul>
     * For example:
     *
     * <pre>Dog [Fauna] at (2,5) [LAND]</pre>
     *
     * @return human-readable string representation of this animal
     * @ass1
     */
    @Override
    public String toString() {
        return String.format("%s [%s]",
            super.toString(),
            this.habitat);
    }

    public String encode() {
        return String.format("%s-%s",
                super.encode(),
                this.habitat);
    }
    public int hashCode() {
        final int prime = 31;
        int result = prime * super.hashCode() + ((habitat == null) ? 0 : habitat.hashCode());
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
        Fauna fauna = (Fauna) other;
        return super.equals(fauna) && this.habitat.equals(fauna.habitat);
    }
    //Helper Method
    private boolean checkDistance(Coordinate coordinate) {
        Coordinate testCoordinate = this.getCoordinate().distance(coordinate);
        int distance = Math.abs(testCoordinate.getX()) + Math.abs(testCoordinate.getY());
        if (distance > this.getSize().moveDistance) {
            return false;
        } else {
            return true;
        }

    }
    public List<Coordinate> getPossibleMoves() {
        List<Coordinate> list = new ArrayList<>();
        try {
            for (int i = 0; i < checkRange(this.getSize().moveDistance,
                    this.getCoordinate()).size(); i++) {
                if (this.canMove(checkRange(this.getSize().moveDistance,
                        this.getCoordinate()).get(i))) {
                    list.add(checkRange(this.getSize().moveDistance, this.getCoordinate()).get(i));
                }

            }
        } catch (CoordinateOutOfBoundsException e) {
            //
        }
        return list;

    }
    public void move(Coordinate coordinate) {
        try {
            if (this.canMove(coordinate)) {
                MoveEvent newEvent = new MoveEvent(this, coordinate);
                ScenarioManager.getInstance().getScenario().getLog().add(newEvent);
                Tile destinationTile = ScenarioManager.getInstance().getScenario()
                        .getMapGrid()[coordinate.getIndex()];
                Tile initialTile = ScenarioManager.getInstance().getScenario()
                        .getMapGrid()[this.getCoordinate().getIndex()];
                destinationTile.setContents(this);
                initialTile.setContents(null);

            }
        } catch (CoordinateOutOfBoundsException e) {
            //
        }



    }
    private boolean satisfyCondition(int x, int y)  {
        Coordinate coordinate = new Coordinate(x, y);
        Tile tile = ScenarioManager.getInstance().getScenario().getMapGrid()[coordinate.getIndex()];
        return !(this.getCoordinate().equals(coordinate)) && coordinate.isInBounds()
                && this.checkDistance(coordinate) && !(tile.hasContents())
                && ((this.getHabitat() == TileType.OCEAN && tile.getType() == TileType.OCEAN)
                || (this.getHabitat() == TileType.LAND && tile.getType()
                != TileType.OCEAN));
    }
    private boolean checkPath1(Coordinate coordinate) {
        boolean checkHorizontalPath = true;
        boolean checkVerticalPath = true;
        Coordinate distance = this.getCoordinate().distance(coordinate);
        int horizontalTiles = Math.abs(distance.getX());
        int verticalTiles = Math.abs(distance.getY());
        if (horizontalTiles != 0) {
            for (int i = 1; i < horizontalTiles + 1; i++) {
                if (!(this.satisfyCondition(this.getCoordinate().getX()
                        + i, this.getCoordinate().getY()))) {
                    checkHorizontalPath = false;
                    break;
                }
            }
        }
        if (verticalTiles != 0) {
            for (int j = 1; j < verticalTiles + 1; j++) {
                if (!(this.satisfyCondition(this.getCoordinate().getX()
                        + horizontalTiles, this.getCoordinate().getY() + j))) {
                    checkVerticalPath = false;
                    break;

                }
            }
        }
        return checkHorizontalPath && checkVerticalPath;

    }
    private boolean checkPath2(Coordinate coordinate) {
        boolean checkHorizontalPath = true;
        boolean checkVerticalPath = true;
        Coordinate distance = this.getCoordinate().distance(coordinate);
        int horizontalTiles = Math.abs(distance.getX());
        int verticalTiles = Math.abs(distance.getY());
        if (verticalTiles != 0) {
            for (int i = 1; i < verticalTiles + 1; i++) {
                if (!(this.satisfyCondition(this.getCoordinate().getX(),
                        this.getCoordinate().getY() + i))) {
                    checkVerticalPath = false;
                    break;
                }
            }
        }
        if (horizontalTiles != 0) {
            for (int j = 1; j < horizontalTiles + 1; j++) {
                if (!(this.satisfyCondition(this.getCoordinate().getX()
                        + j, this.getCoordinate().getY() + verticalTiles))) {
                    checkHorizontalPath = false;
                    break;

                }
            }
        }
        return checkHorizontalPath && checkVerticalPath;

    }
    public boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException {
        if (!(coordinate.isInBounds())) {
            throw new CoordinateOutOfBoundsException();
        }
        return  (this.checkPath1(coordinate)
                || this.checkPath2(coordinate));

    }

    public int collect(User user) {
        return 1;

    }
    




}
