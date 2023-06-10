# RobotsSucks
An assignment for Concurrent programming course was issued to us, the purpose was to learn multithreading programming.

I created a gui (not a requirement - most of the class used console) cuz i thought its cool. - and it was :D !

## The Assignment Description & Requirements:

For this assignment you will simulate a group of robot vacuums cleaning a dirty room. You can assume that
the robot can clean a 0.5 m x 0.5 m section of the room every 2 seconds, and you may assume the room is
divided into a grid of 0.5 m x 0.5 m cells. For simplicity, you may assume the room is square in shape so
that its length and width are identical.
For the simulation, the dimensions of the room, which can be assumed to be in units of 0.5 m x 0.5 m cells,
are given in a text file named room.txt which consists of only one line with one integer M , e.g., "11". The
integer is assumed to be odd, so that both the length and width of the room consist of that odd number of
cells.

You may also assume there is another text file, named robots.txt, the first line of which consists of a single
positive integer n which gives the number of robot vacuums to simulate, followed by n ¡ 1 lines, each of which
consists of an initial position of the robot vacuum (horizontal coordinate followed by a space and then the
vertical coordinate), followed by another space, and then the initial direction of travel of the robot vacuum
encoded as a single uppercase character: U for up, L for left, D for down and R for right. You may assume
there is initially at most one robot vacuum per room cell. If there is not, your program should output the
following message to standard output "INPUT ERROR" and exit before conducting the simulation.
Each robot is assumed to follow a counter-clockwise spiral pattern, starting in the initial direction. To ensure
that the simulation terminates, you may assume there is always a robot vacuum which starts at the centre
grid cell of the room, and also travels in a counterclockwise spiral pattern. You may assume the initial
direction of travel of the centre robot vacuum is upwards.

The simulation will be clock-driven so that the positions of all robot vacuums will be updated at each
iteration, which will consist of 2 seconds of the simulation. If two vacuums occupy the same cell at any
iteration of the algorithm, then the program should terminate and output to standard output the following
message: "COLLISION AT CELL (m; n)", where m is the horizontal cell number (an integer from 0 to M ¡ 1,
increasing from left to right), and n is the vertical cell number (another integer from 0 to M ¡ 1, increasing
from bottom to top). If a robot is at the boundary of the room and about to hit a wall, then it should turn
counterclockwise and continue travelling counterclockwise along the walls of the room in a circular loop, and
no longer a spiral pattern. If the entire room becomes clean before any collision occurs, then the following
message should be output to standard output "ROOM CLEAN", after which the simulation should end.

You are to submit your source code, preferably in Java, as well as a build script to compile your code. You
should also submit some sample inputs to and outputs from your code.
