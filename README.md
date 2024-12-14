🚒 Firefighting Robots Simulator 🔥

Welcome to our Java simulation project where autonomous firefighting robots work together to extinguish fires in a dynamic and realistic environment. This project was developed as part of an Object-Oriented Programming course at ENSIMAG.

🌟 Key Features

Interactive Map: Models a natural environment (forest, rocks, water, open terrain, etc.).

Intelligent Robots: Robots with unique characteristics (wheels, tracks, drones, legs) capable of planning their paths and efficiently extinguishing fires.

A Algorithm*: Implements the shortest path algorithm for optimized robot movements.

Event Management: A discrete event simulator to coordinate the actions of the robots.

Firefighting Strategies: Multiple strategies to assign robots to fires and test their performance.

🗺️ Simulation Overview

Terrain Types

Water: Used by robots to refill their tanks.

Forest: Flammable areas requiring firefighting efforts.

Rocks: Impassable terrain.

Open Terrain: Areas where robots can move freely.

Robots

Each robot has:

Unique mobility: Different speeds and accessibility (e.g., drones can fly, wheeled robots are faster on flat ground).

Tank capacity: Limits the amount of water they can carry.

Extinguishing speed: Determines how quickly they can put out fires.

🚀 How It Works

Dynamic Environment: Fires break out randomly on the map.

Robot Assignment: Robots are dispatched based on various strategies:

Basic Strategy: Fires are randomly assigned to available robots.

Advanced Strategy: Robots calculate their travel time to each fire, and the fastest one is dispatched.

Optimized Strategy: Slower robots are assigned to the nearest fires to reduce travel time.

Pathfinding: Robots calculate the shortest route to their target using the A* algorithm.

Collaboration: Robots refill water tanks at water sources and continue firefighting.

🛠️ Technical Details

Programming Language: Java

Data Structures:

Linked Lists: Efficient management of robots and fires.

Priority Queue: Optimized handling of events and pathfinding.

HashSet: Used for faster lookups in the A* algorithm.

Simulation Engine: A discrete event simulator synchronizes robot actions and environmental changes.

📝 How to Run the Simulation

Clone this repository:

git clone https://github.com/YourUsername/Firefighting-Robots-Simulator.git
cd Firefighting-Robots-Simulator

Compile the Java project:

javac -d bin src/*.java

Run the simulation:

java -cp bin Main

🔬 Tests and Execution

Complete Tests

Complete tests verify that all fires are extinguished according to one of three fire assignment strategies.

Clean up compiled files:

make clean

Compile necessary files:

make testChefPompier
or make all

Run the simulation:

make exeChefPompier FILE={path/to/file.map} ATTRIBUTION={assignment_type}

FILE: Path to the map file to simulate (e.g., cartes/mushroomOfHell-20x20.map).

ATTRIBUTION: Fire assignment strategy:

simple

avancee

reflechie

Example:
To simulate the mushroomOfHell-20x20.map map with the most efficient strategy:

make exeChefPompier FILE=cartes/mushroomOfHell-20x20.map ATTRIBUTION=reflechie

3. Simulation Parameters

Time Between Updates: Reduce this to speed up the simulation.

Steps Between Updates: Increase this if the simulation takes too long.

4. Generate Java Documentation

To generate Java documentation for the application:

make doc

The documentation will be generated in the docs folder.

📂 Project Structure

src/: Contains all the source code.

bin/: Compiled files.

docs/: Documentation for the project.

💡 Future Improvements

Implement fire propagation across the map.

Add energy consumption and charging stations for robots.

Support larger and more complex maps.

