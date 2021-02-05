# Risque
Game project for SOEN 6441 Winter 2021 at Concordia University

By: Kyle Taylor Lange et. al.

## Model-View-Controller Pattern:
* Model: the core will likely be the GameEngine class that will manage most phases of the game, from gameplay to map editing.
 * Key classes mentioned in the requirements include:
  * GameEngine
  * Player
  * Order (issued by players)
* View: another thread to print (and possibly retrieve) text from the GameEngine when events occur. (Possible need for an observer?)
* Controller: a class to parse (and validate) console commands and send them to the GameEngine (or other peripheral classes) for execution.

That's all for now!
