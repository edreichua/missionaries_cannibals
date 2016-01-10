README for Missionaries and Cannibals

Author: Edrei Chua
Created on: 01/10/2016

There are a few important files in this directory:

Report (director for report)
    cannibals.pdf (detailed documentation of the code)
    cannibals.tex (tex file)
src (directory for source code)
    > CannibalDriver.java
    > CannibalProblem.java
    > UUSearchProblem.java
    > LossyCannibalProblem.java
    > LossyUUSearchProblem.java

HOW TO START THE DEFAULT PROGRAM

To start the program, compile and run CannibalDriver.java. The default setting is BOAT_SIZE = 2, MAX_DEPTH = 5000,
start state of original implementation is (8,5,1) i.e. 8 missionaries, 5 cannibals and 1 boat at the start bank
and none at the finishing bank, and start state of lossy implementation is (8,5,1,0,0,0) i.e. 8 missionaries, 5 cannibals
and 1 boat at the start bank and none at the finishing bank and degree of loseness E = 0. Therefore, for this defined
start state, both implementation are identical and should return the same result.

VARYING PARAMETERS

> CHANGE START STATE
To change the start state for the original implementation, vary the parameters on line 12 of CannibalDriver.java.
Recall again the notation for the CannibalProblem mcProblem = new CannibalProblem(sm, sc, sb, gm, gc, gb), where
sm = number of missionaries at start bank, sc = number of cannibals at start bank, sb = number of boat at start bank,
gm = goal number of missionaries at start bank, gc = goal number of cannibals at start bank and gb = goal number
of boats at start bank.

To change the start state for the lossy implementation, vary the parameter on line 49 of CannibalDriver.java.
Recall again the notation for LossyCannibalProblem lossymcProblem = new LossyCannibalProblem(sm, sc, sb, E, gm, gc, gb).
The additional parameter E denotes the degree of lossness (or the maximum number missionaries that can be sacrificed).

> Change BOAT_SIZE
To change the BOAT_SIZE for the orginal implementation, vary the parameter on line 39 of CannibalProblem.java.
To change the BOAT_SIZE for the lossy implementation, vary the parameter on line 39 of LossyCannibalProblem.java.
Note that the default BOAT_SIZE is 2.