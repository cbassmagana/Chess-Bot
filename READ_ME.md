# My Personal Project - A Chess Bot

## About this Project

In this application, I have designed and implemented a chess game
in which the user plays against a strategic chess bot. I have always
enjoyed the game of chess and was interested in this project on both
the bases of improving/showcasing my object-oriented programming, 
and also because of my curiosity for how competent of a chess bot I 
could design.

The game is played using a graphical interface from the Java Swing library, 
ending when either the game concludes or the user exits the application. 
In order to play, select the piece that you would like to move by 
clicking on it, and then proceed to select the square that you wish
to move it to. If the move is invalid with respect to the rules of
the game, the move will not be executed, and you must try again. The
computer will respond automatically, continue playing until the game
concludes.

The bot decides which move to respond with by evaluating each 
available valid move on the board using a point system. It then 
selects the move with the most favorable point rating, or in the
case that multiple moves are tied for the highest point rating, 
one of these moves is selected randomly. Different moves are
assigned point ratings on the assumption that the user will 
always respond with the best move available to them. The bot 
searches all sequences of valid moves on the board up to 4 
moves in advance, adjusting each move's point evaluation for 
things like capturing/losing pieces, putting the opponent in check,
promoting pawns, developing pieces from the back rank and 
ultimately finding checkmate.

The bot currently performs quite competently, playing at around the
level of an intermediate/advanced chess player (elo 1400-1800)*. However,
the bot has noticeable strengths and weaknesses in 
comparison to a human player of approximately the same overall
ability. Several small adjustments to the algorithm have improved 
its competency significantly, and its steep learning curve indicates
substantial room to improve this bot further. 

*Note: this is an approximate elo rating assuming blitz style and 
is based on comparison to other officially rated chess bots online.
(En passant and castling have not yet been implemented).