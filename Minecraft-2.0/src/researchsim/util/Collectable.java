package researchsim.util;

import researchsim.entities.User;

/**
 * Denotes an entity that can be collected by the {@link researchsim.entities.User} in the
 * simulation.
 *
 * @ass2
 */
public interface Collectable {
    int collect(User user);

}
