/**
 * This package contains all classes involved in the generation 
 * and internal representation of a maze.
 * It implements a factory pattern to encapsulate how a maze is instantiated
 * and computed. The factory produces a MazeConfiguration as a product.
 * As the production works asynchronously with a background thread, 
 * there is support for more communication (order, delivery, cancel)
 * Getting progress information is supported with an observer pattern
 * as the factory accepts a listener that notified when progress is made.
 * It uses the facade pattern to hide several data structures
 * behind the MazeConfiguration interface, respectively its implementation
 * with a MazeContainer class.
 * @author pk
 *
 */
package edu.wm.cs.cs301.amazebyeyosyas.generation;

/*
Bugs and potentially extra features:
- include "revisit" feature
- fix wall problem
- fix turning animation problem
- add a minecraft hand pic to maze screen
- make "jump" take use up energy
- properly exit maze
- change walls to look like stone blocks
- change fonts
- make playing screen larger and use something other than toggle buttons for map and stuff
- Fix horizontal walls of Eller's alg
 */
