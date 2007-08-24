package search.tictactoe;

import java.util.ArrayList;

import search.Action;
import search.ActionStatePair;

public class TTTState3x3WithMove extends TTTState3x3 {

    public static Action[][][][] MOVE_X=new Action[3][3][3][3]; // all actions for us
    public static Action[][][][] MOVE_O=new Action[3][3][3][3]; // all actions for opponent
    
    public TTTState3x3WithMove() {
        // from position
        for (int r1=0; r1<3; r1++) {
            for (int c1=0; c1<3; c1++) {
                // to position
                for (int r2=0; r2<3; r2++) {
                    for (int c2=0; c2<3; c2++) {
                        MOVE_X[r1][c1][r2][c2]=new Action("X("+r1+","+c1+"->"+r2+","+c2+")");
                        MOVE_O[r1][c1][r2][c2]=new Action("O("+r1+","+c1+"->"+r2+","+c2+")");
                    }
                }
            }
        }
    }
    
    public TTTState3x3WithMove(boolean weGoFirst) {
        this();
        if (weGoFirst) {
            turn=MARK_X;
        } // else the opponent starts
    }

    public TTTState3x3WithMove(TTTState3x3WithMove state) {
        super(state);
    }

    public TTTState3x3WithMove(TTTState3x3WithMove origin, Action action) {
        this(origin); // first make a copy
        boolean moveMade=false;
        // from position
        for (int r1=0; r1<3; r1++) {
            for (int c1=0; c1<3; c1++) {
                // to position
                for (int r2=0; r2<3; r2++) {
                    for (int c2=0; c2<3; c2++) {
                        if ((action.equals(TTTState3x3WithMove.MOVE_X[r1][c1][r2][c2]) && turn==MARK_X) ||
                                (action.equals(TTTState3x3WithMove.MOVE_O[r1][c1][r2][c2]) && turn==MARK_O)) {
                            // check if move is valid
                            if (board[r2][c2]==MARK_EMPTY && board[r1][c1]!=(turn==MARK_X?MARK_O:MARK_X)) {
                                moveMade=true;
                                board[r1][c1]=MARK_EMPTY;
                                board[r2][c2]=turn;
                            } else {
                                throw new RuntimeException("Invalid move: "+action);
                            }
                        }
                    }
                } 
            }
        }
        if (moveMade) {
            turn=(turn==MARK_X?MARK_O:MARK_X);
        } else {
            throw new RuntimeException("Invalid move: "+action);
        }
    }

    private boolean validMove(int rFrom, int cFrom, int rTo, int cTo) {
        if (board[rTo][cTo]==MARK_EMPTY) {
            if (board[rFrom][cFrom]==turn) {
                return true;
            } else {
                if (board[rFrom][cFrom]==MARK_EMPTY) {
                    if (count(turn)<3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected ActionStatePair[] successor(int mark) {
        //ArrayList<ActionStatePair> list=new ArrayList<ActionStatePair>();
        ArrayList list=new ArrayList();
        // from position
        for (int r1=0; r1<3; r1++) {
            for (int c1=0; c1<3; c1++) {
                // to position
                for (int r2=0; r2<3; r2++) {
                    for (int c2=0; c2<3; c2++) {
                        // check if move is valid
                        if (validMove(r1,c1,r2,c2)) {
                            if (mark==TTTState3x3WithMove.MARK_X)
                                list.add(new ActionStatePair(MOVE_X[r1][c1][r2][c2], new TTTState3x3WithMove(this, MOVE_X[r1][c1][r2][c2])));
                            if (mark==TTTState3x3WithMove.MARK_O)
                                list.add(new ActionStatePair(MOVE_O[r1][c1][r2][c2], new TTTState3x3WithMove(this, MOVE_O[r1][c1][r2][c2])));
                        }
                    }
                }
            }
        }
        ActionStatePair[] pairs=new ActionStatePair[list.size()];
        for (int i=0; i<list.size(); i++) {
            pairs[i]=(ActionStatePair)list.get(i);
        }
        return pairs;
    }
    
}
