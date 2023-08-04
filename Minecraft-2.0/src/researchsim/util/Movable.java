package researchsim.util;

import researchsim.entities.Fauna;
import researchsim.entities.Size;
import researchsim.logging.MoveEvent;
import researchsim.map.Coordinate;
import researchsim.map.TileType;

import java.util.ArrayList;
import java.util.List;

/**
 * Denotes a specific type of entity that can move around in the simulation.
 * <p>
 * <b>NOTE:</b> <br> Read the documentation for these methods well.
 * This is one of the harder parts of A2.
 * <br> It is recommended that you create some private helper methods to assist with these
 * functions. <br> Some example methods might be: <br> checkTile - see if a Tile can be moved to
 * <br> checkTraversal - see if all tiles along a path can be moved to
 *
 * @ass2
 */
public interface Movable {


    default List<Coordinate> checkRange(int radius, Coordinate initialCoordinate) {
        List<Coordinate> list = new ArrayList<>();


        for (int i = initialCoordinate.getX() - radius; i < initialCoordinate.getX()
                + radius + 1; i++) {

            for (int j = initialCoordinate.getY() - radius; j < initialCoordinate.getY()
                    + radius + 1; j++) {
                if (Math.abs(initialCoordinate.getX() - i)
                       + Math.abs(initialCoordinate.getY() - j)
                        <= radius) {
                    list.add(new Coordinate(i, j));
                } else {
                    continue;
                }


            }
        }
        return list;


    }
    boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException;

    void move(Coordinate coordinate) throws CoordinateOutOfBoundsException;

    List<Coordinate> getPossibleMoves();

    /*public static void main(String[] args) {
        Fauna fauna = new Fauna(Size.SMALL,new Coordinate(3,2), TileType.LAND);
        System.out.println(fauna.checkRange(2,new Coordinate(2,2)));

    }*/

}
