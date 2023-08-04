package researchsim.entities;

import researchsim.logging.MoveEvent;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.ScenarioManager;
import researchsim.util.Collectable;
import researchsim.util.CoordinateOutOfBoundsException;
import researchsim.util.Movable;
import researchsim.util.NoSuchEntityException;


import java.util.ArrayList;
import java.util.List;

/**
 * User is the player controlled character in the simulation.
 * A user can {@code collect} any class that implements the {@link researchsim.util.Collectable}
 * interface.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass2
 * @ass2_test
 */
public class User extends Entity implements Movable {


    private final String name;

    public User(Coordinate coordinate, String name) {
        super(Size.MEDIUM, coordinate);
        this.name = name;

    }



    public String getName() {
        return this.name;
    }

    public String encode() {
        return String.format("%s-%s-%s",
                this.getClass().getSimpleName(),
                this.getCoordinate(),
                this.name);

    }
    public int hashCode() {
        final int prime = 31;
        int result = prime * super.hashCode() + ((name == null) ? 0 : name.hashCode());
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
        User user = (User) other;
        return super.equals(user) && this.name.equals(user.name);


    }
    private boolean checkDistance(Coordinate coordinate) {
        Coordinate testCoordinate = this.getCoordinate().distance(coordinate);
        int distance = Math.abs(testCoordinate.getX()) + Math.abs(testCoordinate.getY());
        if (distance > 4) {
            return false;
        } else {
            return true;
        }
    }
    private boolean satisfyCondition(int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);
        Tile tile = ScenarioManager.getInstance().getScenario().getMapGrid()[coordinate.getIndex()];
        return !(this.getCoordinate().equals(coordinate)) && coordinate.isInBounds()
                && this.checkDistance(coordinate) && tile.getType() != TileType.OCEAN
                && tile.getType() != TileType.MOUNTAIN;

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


    @Override
    public boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException {
        if (!(coordinate.isInBounds())) {
            throw new CoordinateOutOfBoundsException();
        }
        return  (this.checkPath1(coordinate)
                || this.checkPath2(coordinate));

    }


    @Override
    public void move(Coordinate coordinate) {
        try {
            if (this.canMove(coordinate)) {
                MoveEvent newEvent = new MoveEvent(this, coordinate);
                ScenarioManager.getInstance().getScenario().getLog().add(newEvent);
                Tile destinationTile = ScenarioManager.getInstance().getScenario()
                        .getMapGrid()[coordinate.getIndex()];
                Tile initialTile = ScenarioManager.getInstance().getScenario()
                        .getMapGrid()[this.getCoordinate().getIndex()];
                if (destinationTile.hasContents() && destinationTile.getContents() instanceof Collectable) {
                    collect(coordinate);
                }
                destinationTile.setContents(this);
                initialTile.setContents(null);

            }
        } catch (Exception e) {
            //
        }


    }

    @Override
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

    public void collect(Coordinate coordinate) throws NoSuchEntityException,
            CoordinateOutOfBoundsException {
        Tile tile = ScenarioManager.getInstance().getScenario().getMapGrid()[coordinate.getIndex()];
        if (tile.getContents() == null) {
            throw new NoSuchEntityException();
        } else if (!coordinate.isInBounds()) {
            throw new CoordinateOutOfBoundsException();
        } else if (tile.getContents() instanceof Collectable
                && this.getPossibleMoves().contains(coordinate)) {
            tile.setContents(null);
        }

    }
    public List<Coordinate> getPossibleCollection() {
        List<Coordinate> list = new ArrayList<>();
        try {
            for (int i = 0; i < checkRange(1,
                    this.getCoordinate()).size(); i++) {
                Coordinate coordinate = checkRange(1, this.getCoordinate()).get(i);
                if (coordinate.isInBounds()
                        && ScenarioManager.getInstance().getScenario().getMapGrid()[coordinate
                        .getIndex()].hasContents() && ScenarioManager.getInstance().getScenario().getMapGrid()[coordinate
                        .getIndex()].getContents() instanceof Collectable) {
                    list.add(coordinate);
                }
            }
        } catch (NoSuchEntityException e) {
            //
        }
        return list;



    }
}
