package researchsim.logging;

import researchsim.entities.Entity;
import researchsim.entities.User;

import java.util.StringJoiner;

/**
 * The collection of an entity that implemented {@link researchsim.util.Collectable} by a
 * {@link User}.
 *
 * @ass2
 */
public class CollectEvent extends Event {

    private User user;
    private Entity target;


    public CollectEvent(User user, Entity target) {
        super(target, user.getCoordinate());
        this.user = user;


    }
    public Entity getTarget() {
        return this.target;
    }

    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add(user.getName() + " [User] at " + user.getCoordinate());
        result.add("COLLECTED");
        result.add(getEntity().toString());
        result.add("-".repeat(5));
        return result.toString();
    }

}
