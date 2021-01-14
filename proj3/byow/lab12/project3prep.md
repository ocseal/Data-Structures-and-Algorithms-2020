# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer:
I was trying to draw the hexagon in one method in HexWorld, utilizing math to identify the pattern of tiles and then figuring out a loop to change tiles accordingly. This ended up being a poor approach as I didn't delegate any work to other classes. 
-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer:
The hexagons represent rooms while the empty spaces represent hallways. 
-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I would probably start by writing a position class that would help me organize my world into coordinates. 

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: Nothing except baseline width. They both are enclosed by walls, but a hallway must connect to at least one room.  
