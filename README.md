# Risque
A project for SOEN 6441 Winter 2021 at Concordia University that is similar to the Risk boardgame.

By: Kyle Taylor Lange et. al.

## Our Framework: Model-View-Controller Pattern via Packages
* Game (the model): this package handles the gameplay logic, from map editing to world domination.
  * The GameEngine class is the main thread for this module; it creates and interacts with the game's classes.
  * The Map class will handle both pre-gameplay map editing and tracking the current state of the game's world.
* Console (the view): : this package presents the player with a non-GUI interface for the game.
  * The console is a thread that handles output, but it also makes use of a second thread to allow input at the same time. The latter thread is called InputHandler.
* Controller (the controller): this package contains methods for the player to interact with the game.
  * What is the difference between the console and the controller? Basically, this: if both a GUI and non-GUI interface would both use it, it should be in controller. The console module should be swappable with a GUI version, even if a GUI is never implemented.
* The game is started via the main function in `game.GameEngine`.

That's all for now!
