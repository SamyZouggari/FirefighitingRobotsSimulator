# 🚒 Firefighting Robots Simulator 🔥

Welcome to our **Java simulation project**, where autonomous firefighting robots collaborate to extinguish fires in a dynamic and realistic environment. This project was developed as part of an **Object-Oriented Programming** course at ENSIMAG. We were 3 students doing the project.

---

## 🌟 Key Features
- 🌍 **Interactive Map**: Simulates a natural environment with various terrains (forest, rocks, water, open terrain).
- 🤖 **Intelligent Robots**: Robots with unique abilities (wheels, tracks, drones, legs) to plan paths and extinguish fires efficiently.
- 🔄 **Astar Algorithm**: Calculates optimized paths for robots to navigate the map.
- 🎯 **Event Management**: Coordinates robot actions with a discrete event simulator.
- 🚒 **Firefighting Strategies**: Implements multiple methods for assigning robots to fires.

---

## 🗺️ Simulation Overview
### Terrain Types
- 💧 **Water**: Robots refill their tanks here.
- 🌲 **Forest**: Flammable areas requiring firefighting efforts.
- 🪨 **Rocks**: Impassable terrain.
- 🟩 **Open Terrain**: Freely navigable areas.

### Robots
Each robot has:
- 🚀 **Unique Mobility**: Different speeds and movement capabilities (e.g., drones can fly, wheeled robots are faster on flat ground).
- 💧 **Tank Capacity**: Determines the amount of water a robot can carry.
- 🔥 **Extinguishing Speed**: Defines how quickly fires can be extinguished.

---

## 🚀 How It Works
1. 🔥 **Dynamic Environment**: Fires break out randomly on the map.
2. 📋 **Robot Assignment**: Robots are dispatched based on strategies:
   - **Basic Strategy**: Randomly assigns fires to available robots.
   - **Advanced Strategy**: Assigns fires to the fastest robot available.
   - **Optimized Strategy**: Allocates fires to minimize travel time for slower robots.
3. 🗺️ **Pathfinding**: Robots use the A* algorithm to calculate the shortest routes.
4. 🤝 **Collaboration**: Robots refill water tanks and return to extinguish fires.

---

## 🛠️ Technical Details
- **Programming Language**: Java
- **Data Structures**:
  - 📜 **Linked Lists**: Efficiently manage robots and fires.
  - 📚 **Priority Queue**: Optimize event and pathfinding tasks.
  - ⚡ **HashSet**: Speeds up lookups in the A* algorithm.
- **Simulation Engine**: Synchronizes robot actions and environmental changes.

---

## 📝 How to Run the Simulation
1. Clone this repository:
   ```bash
   git clone https://github.com/YourUsername/Firefighting-Robots-Simulator.git
   cd Firefighting-Robots-Simulator
   ```
2. Compile the Java project:
   ```bash
   javac -d bin src/*.java
   ```
3. Run the simulation:
   ```bash
   java -cp bin Main
   ```

---

## 🔬 Tests and Execution

Complete tests ensure all fires are extinguished using one of three fire assignment strategies.

1. Clean up compiled files:
   ```bash
   make clean
   ```
2. Compile necessary files:
   ```bash
   make testChefPompier
   ```
3. Run the simulation:
   ```bash
   make exeChefPompier FILE={path/to/file.map} ATTRIBUTION={assignment_type}
   ```
   - **FILE**: Path to the map file to simulate (e.g., `cartes/mushroomOfHell-20x20.map`).
   - **ATTRIBUTION**: Fire assignment strategy:
     - `simple`
     - `avancee`
     - `reflechie`

**Example**:
```bash
make exeChefPompier FILE=cartes/mushroomOfHell-20x20.map ATTRIBUTION=reflechie
```

---

### 3. Simulation Parameters
- ⏱️ **Time Between Updates**: Reduce this to speed up the simulation.
- 🕒 **Steps Between Updates**: Increase this to simulate more steps at once.

---

### 4. Generate Java Documentation
To generate Java documentation for the application:
```bash
make doc
```
The documentation will be generated in the `docs` folder.

---

