# Minecraft-2.0

**Description: Minecraft-Like Simulation**

In this completed simulation project, a simplified version of a game similar to Minecraft has been developed. The game involves entities, The user plus both flora (plants) and fauna (animals), moving around a 2-dimensional grid-like environment represented by tiles. The main class in this simulation is the "Scenario," which maintains a record of all inhabitants of the environment and their movements and interactions.

![image](https://github.com/Mohamad11Dab/Minecraft-2.0/assets/114811082/13c6234d-4fb9-47e0-825f-776c76a42e4f)


**Key Components:**

1. `Scenario:`
- The Scenario class maintains a record of all entities (flora and fauna) living in the environment.
- It keeps track of all movements and interactions between the entities.
- The Scenario class uses a singleton pattern, ensuring there is only one instance of the Scenario in the simulation.

2. `Environment and Tiles:`
- The environment is made up of tiles that form a 2-dimensional grid-like structure.
- The tiles represent different types of terrain, such as grassy, ocean, or sandy.
- Each tile can have an inhabitant (entity) residing within it.

3. `Entities:`
- Entities are divided into three main types: User (You), flora (plants) and fauna (animals).
- An entity's characteristics, such as its size, define what type of tile it can inhabit.
- Some animals can only live in water-based tiles, while others can only live in land-based tiles.

4. `Coordinate System:`
- The tiles are located using a coordinate system, similar to the cartesian plane.
- This coordinate system facilitates the conversion between the 2-dimensional view of the scenario and the tile array index.

5. `Events:`
- The scenario uses the Event classes, such as MoveEvent and CollectEvent, to represent interactions between entities.
- A MoveEvent records an entity leaving its current tile and inhabiting a new (unoccupied) tile.

6. `Statistics:`
- The simulation provides various statistics, such as the number of entities (flora and fauna) collected by the User and the number of tiles entities have moved.

7. `Graphical User Interface (GUI):`
- A graphical user interface has been implemented to visualize the entities moving around the map.
- The GUI displays the scenario, entities, and interactions for user interaction and observation.
