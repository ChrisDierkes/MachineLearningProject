# MachineLearningProject
Code to generate MachineLearning datafiles in regards to Poker.


How to Run:

First compile, with: javac HandStrengthEvaulator.java

To run with a set amount of boards, run:

java HandStrengthEvaulator x, where x is the number of boards it will run. ~600/minute approximately. The program will output to the terminal, to run it to a file use:

java HandStrengthEvaulator x > fileName

If x is not given, it will default to 100 boards.

If multiple arguments or if x is not a number, the code will bug.

-----

To run with a specific board, run:

java HandStrengthEvaulator RS RS RS RS RS, where R = rank, 2-9/T/J/Q/K/A, and S = Suit, c,s,d,h

Anything but that specific format will bug.

----

The output format is:

Hand Card One Suit, Hand Card Two Suit, Board Card One Suit, Board Card Two Suit, Board Card Three Suit, Board Card Four Suit, Board Card Five Suit, Hand Card One Rank, Hand Card Two Rank, Board Card One Rank, Board Card Two Rank, Board Card Three Rank, Board Card Four Rank, Board Card Five Rank, Percentile