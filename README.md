# Personal Project - Strategic Chess Bot

## About This Project

In this application, built in 2023, I have designed and implemented a chess 
game where the user plays against a strategic chess bot. I 
am an enthusiastic chess player and was interested in 
this project for both improving/showcasing my programming,
and satisfying my curiosity about designing a 
competent chess bot.

The game is played using a graphical interface from Java
Swing, and concludes either when the game ends or the 
user exits the application. To play, select the piece you 
want to move by clicking on it, then choose the square you 
wish to move it to. If the move violates the game rules, 
it won't be executed, and you must try again. The computer
will respond automatically, continuing to play until the 
game finishes.

The bot employs a depth-first search of game moves, using strategic 
pruning of search branches based on simple heuristics to enable
a greater depth of analysis on likely favorable paths. It evaluates each available valid
move on the board using a point system, selecting the
move with the most favorable point rating (depths considered). In cases where
multiple moves tie for the highest point rating, one of 
these moves is selected randomly. Different moves are assigned
point ratings under the assumption that the user will always 
respond with the best move available to them. The bot searches
select sequences of valid moves on the board up to 5 moves in 
advance, adjusting each move's point evaluation for factors
like capturing/losing pieces, putting the opponent in check, 
promoting pawns, developing pieces from the back rank, and 
ultimately finding checkmate.

The bot currently performs quite competently, playing at 
around the level of an intermediate/advanced chess player 
(elo ~ 1600-1800). However, the bot exhibits noticeable 
strengths and weaknesses compared to a human player of 
approximately the same overall ability. Several small 
adjustments to the algorithm have significantly improved 
its decision-making, and its steep learning curve indicates 
substantial room for further improvement.
