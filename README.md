# My Personal Project - A Chess Bot

## About This Project

In this application, I have designed and implemented a chess 
game where the user plays against a strategic chess bot. I 
have always enjoyed the game of chess and was interested in 
this project for both improving/showcasing my object-oriented
programming and satisfying my curiosity about designing a 
competent chess bot.

The game is played using a graphical interface from the Java
Swing library, concluding either when the game ends or the 
user exits the application. To play, select the piece you 
want to move by clicking on it, then choose the square you 
wish to move it to. If the move violates the game rules, 
it won't be executed, and you must try again. The computer
will respond automatically, continuing to play until the 
game concludes.

The bot decides its move by evaluating each available valid
move on the board using a point system. It then selects the
move with the most favorable point rating. In cases where
multiple moves tie for the highest point rating, one of 
these moves is selected randomly. Different moves are assigned
point ratings under the assumption that the user will always 
respond with the best move available to them. The bot searches
all sequences of valid moves on the board up to 4 moves in 
advance, adjusting each move's point evaluation for factors
like capturing/losing pieces, putting the opponent in check, 
promoting pawns, developing pieces from the back rank, and 
ultimately finding checkmate.

The bot currently performs quite competently, playing at 
around the level of an intermediate/advanced chess player 
(elo 1400-1800)*. However, the bot exhibits noticeable 
strengths and weaknesses compared to a human player of 
approximately the same overall ability. Several small 
adjustments to the algorithm have significantly improved 
its competency, and its steep learning curve indicates 
substantial room for further improvement.

*Note: This is an approximate elo rating assuming blitz style
and is based on a comparison to other officially rated chess 
bots online. (En passant and castling have not yet been 
implemented).