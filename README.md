# Shortest Path Algorithm

## Problem Description: 

<img src="images/Landscape.PNG" width="200"> <img src="images/LandscapeGrid.PNG" width="200"> 

The image on the left depicts the background of a landscape, whereas the image on the right illustrates the landscape underpinning grid. The grid is a two-dimensional array, upon which the algorithm will operate. 

The following assumptions will have to hold: 
1. All white cells, apart from the black ones, are viable;
2. All adjacent cells can be visited by moving diagonally, vertically, or horizontally; 
3. The cost of visiting a cell is dictated by the chosen heuristic as well as the four different shades of grey;
    - White = 1 
    - Lightest shade of grey = 2
    - Middle shade of grey = 3
    - Darkest shade of grey = 4
4. The definitions of the three distance metrics being used as heuristics as shown below;
