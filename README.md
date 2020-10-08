# MachineLearningProject
Code to generate MachineLearning datafiles in regards to Poker.


How to Run:

First compile, with: javac HandStrengthEvaulator.java

3 

To run with a set amount of boards, run:

java HandStrengthEvaulator x, where x is the number of boards it will run. ~600/minute approximately. The program will output to the terminal, to run it to a file use:

java HandStrengthEvaulator x > fileName

If x is not given, it will default to 100 boards.

If multiple arguments or if x is not a number, the code will bug.

-----

To run with a specific board, run:

java HandStrengthEvaulator RS RS RS RS RS, where R = rank, 2-9/T/J/Q/K/A, and S = Suit, c,s,d,h

Anything but that specific format will bug.