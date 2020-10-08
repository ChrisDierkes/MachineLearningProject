
# MachineLearningProject
Code to generate MachineLearning data files in regards to Poker.

How to Compile:
---------------
<code>javac HandStrengthEvaluator.java</code>

How to Run:
-----------
<code>java HandStrengthEvaluator NUMBER_OF_BOARDS</code>
+ Output will direct to terminal.

*For example:*
<code>java HandStrengthEvaluator 300</code>


<code>java HandStrengthEvaluator NUMBER_OF_BOARDS > FILENAME</code>
+ Output will direct to FILENAME.

*For example:*
<code>java HandStrengthEvaluator 300 > data.txt</code>



*Note:*
+ If NUMBER_OF_BOARDS is not given, it will default to 100 boards.
+ If NUMBER_OF_BOARDS is not a number the program will fail.


Run with a specific board:
-----------------------------
<code>java HandStrengthEvaluator RS RS RS RS RS</code>
+ R = rank, 2-9/T/J/Q/K/A
+ S = Suit, c,s,d,h


*For example:*
<code>java HandStrengthEvaluator 2s 4s 5h Ah Jd</code>


*Note:*
+ If R or S are not in that form the program will fail.


Output Format:
-----------------------------
CSV (Comma Separated Values)
<br/>
*Columns are as follows:*
+ Hand Card One Suit
+ Hand Card Two Suit
+ Board Card One Suit
+ Board Card Two Suit
+ Board Card Three Suit
+ Board Card Four Suit
+ Board Card Five Suit
+ Hand Card One Rank
+ Hand Card Two Rank
+ Board Card One Rank
+ Board Card Two Rank
+ Board Card Three Rank
+ Board Card Four Rank
+ Board Card Five Rank
+ Percentile

*Suit Values:*
+ 1 : Clubs (c)
+ 2 : Diamonds (d)
+ 3 : Hearts (h)
+ 4 : Spades (s)

*Rank Values:*
+ 1 : 2
+ 2 : 3
+ 3 : 4
+ 4 : 5
+ 5 : 6
+ 6 : 7
+ 7 : 8
+ 8 : 9
+ 9 : T (Ten)
+ 10 : J (Jack)
+ 11 : Q (Queen)
+ 12 : K (King)
+ 13 : A (Ace)

*Sample:*
<pre>
3,4,4,3,3,2,4,3,5,1,1,12,4,8,2.960222016651249
1,3,4,3,3,2,4,5,2,1,1,12,4,8,2.960222016651249
1,4,4,3,3,2,4,5,3,1,1,12,4,8,2.960222016651249
</pre>





