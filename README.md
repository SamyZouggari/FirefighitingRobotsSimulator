# 🚒 Firefighting Robots Simulator 🔥

Welcome to our **Java simulation project** where autonomous firefighting robots work together to extinguish fires in a dynamic and realistic environment. This project was developed as part of an **Object-Oriented Programming** course at ENSIMAG.

## 🌟 Key Features
- **Interactive Map**: Models a natural environment (forest, rocks, water, open terrain, etc.).
- **Intelligent Robots**: Robots with unique characteristics (wheels, tracks, drones, legs) capable of planning their paths and efficiently extinguishing fires.
- **A* Algorithm**: Implements the shortest path algorithm for optimized robot movements.
- **Event Management**: A discrete event simulator to coordinate the actions of the robots.
- **Firefighting Strategies**: Multiple strategies to assign robots to fires and test their performance.

---

## 🗺️ Simulation Overview
### Terrain Types
- **Water**: Used by robots to refill their tanks.
- **Forest**: Flammable areas requiring firefighting efforts.
- **Rocks**: Impassable terrain.
- **Open Terrain**: Areas where robots can move freely.

### Robots
Each robot has:
- **Unique mobility**: Different speeds and accessibility (e.g., drones can fly, wheeled robots are faster on flat ground).
- **Tank capacity**: Limits the amount of water they can carry.
- **Extinguishing speed**: Determines how quickly they can put out fires.

---

## 🚀 How It Works
1. **Dynamic Environment**: Fires break out randomly on the map.
2. **Robot Assignment**: Robots are dispatched based on various strategies:
   - **Basic Strategy**: Fires are randomly assigned to available robots.
   - **Advanced Strategy**: Robots calculate their travel time to each fire, and the fastest one is dispatched.
   - **Optimized Strategy**: Slower robots are assigned to the nearest fires to reduce travel time.
3. **Pathfinding**: Robots calculate the shortest route to their target using the A* algorithm.
4. **Collaboration**: Robots refill water tanks at water sources and continue firefighting.

---

## 🛠️ Technical Details
- **Programming Language**: Java
- **Data Structures**:
  - **Linked Lists**: Efficient management of robots and fires.
  - **Priority Queue**: Optimized handling of events and pathfinding.
  - **HashSet**: Used for faster lookups in the A* algorithm.
- **Simulation Engine**: A discrete event simulator synchronizes robot actions and environmental changes.

---

## 🏆 Challenges Faced
- **Event Chaining**: Adjusting the simulator to handle a series of events rather than individual actions.
- **Fire Propagation**: Attempted to implement fire spread but faced issues with new fire addition.
- **Continuous Movement**: Energy management for continuous robot movement remains a potential improvement.

---

## 📊 Results
- **Optimized Coordination**: Robots effectively extinguish fires in various scenarios.
- **Improved Efficiency**: Advanced and optimized strategies significantly reduced response times compared to the basic strategy.
- **Team Collaboration**: The project fostered teamwork and improved our understanding of advanced object-oriented programming concepts.

---

## 📝 How to Run the Simulation
1. Clone this repository:
   ```bash
   git clone https://github.com/YourUsername/Firefighting-Robots-Simulator.git
   cd Firefighting-Robots-Simulator
