These two folders contain a project that I worked on all quarter for my Project Based Object Oriented Programming and Design 
class. The original project is a simulated visual world where miner move about the landscape to collect ore and give to
blacksmiths. While this happens the rocks randomly generate ore for the miners to collect. An entity known as a quake can 
appear and they destroy the rocks that create the ore. Once the quakes destroy all of the rocks the simualtion is over.
Over the quarter I refactored the entire project to use an object oriented design. The functions class originally was home to 
every single method that was used by the project. I created a new design that split entities into respective classes. They
each inherited certain traits that were shared amongst some entitites but not all such as animation, movement, or having an
action. Lastly I implemented the A* pathing strategy to make the miners and quake entities move in a more logical way. The final
project runs the exact same as the original except the code is much neater and utilizes better object oriented design techniques. 
