# Strategic Chess Bot

## About This Project

In this application, I have designed and implemented a chess 
environment where the user plays against a strategic chess bot. I 
am an enthusiastic chess player and was interested in 
this project for both improving/showcasing my programming, 
and also satisfying my curiosity about designing a 
competent chess bot.

## How the Engine Works

The bot searches potential move sequences using a depth-first minimax search with 
heuristic-based pruning, allowing it to focus computation on the most promising 
lines rather than exhaustively searching every branch. Each candidate move is 
scored using a weighted evaluation system and searched up to five moves ahead, 
under the assumption that the user always plays their best available response. 
Move scores account for factors such as material gained or lost, checks, pawn promotion, 
piece development from the back rank, and forced checkmates; when multiple moves tie for 
the top score, one is selected at random. Rather than hand-tuning these weights, I 
selected them through self-play using an evolutionary optimization algorithm, 
rather than just using a basic grid-search.

## Performance

The bot currently plays at roughly an intermediate/advanced level (~1600–1800 ELO), though 
its strengths and weaknesses differ noticeably from a human player of similar rating. Small tuning 
adjustments have produced outsized gains in play quality, suggesting there's still real room for improvement with further iteration.
