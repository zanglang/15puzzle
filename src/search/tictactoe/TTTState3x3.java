package search.tictactoe;

import java.util.ArrayList;

import search.Action;
import search.ActionStatePair;
import search.MiniMaxState;

public class TTTState3x3 implements MiniMaxState {

    public static int MARK_X=1;
    public static int MARK_O=2;
    public static int MARK_EMPTY=0;
    
    public static Action[][] MOVE_X=new Action[3][3]; // all actions for us
    public static Action[][] MOVE_O=new Action[3][3]; // all actions for opponent
    
    protected int turn=MARK_O; // default opponent starts
    protected int[][] board=new int[3][3];  
    
    public TTTState3x3() {
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                MOVE_X[r][c]=new Action("X("+r+","+c+")");
                MOVE_O[r][c]=new Action("O("+r+","+c+")");
            }
        }
    }
    
    public TTTState3x3(boolean weGoFirst) {
        this();
        if (weGoFirst) {
            turn=MARK_X;
        } // else the opponent starts
    }

    public TTTState3x3(TTTState3x3 state) {
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                board[r][c]=state.board[r][c];
            }
        }
        turn=state.turn;
    }

    public TTTState3x3(TTTState3x3 origin, Action action) {
        this(origin); // first make a copy
        boolean moveMade=false;
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                if ((action.equals(TTTState3x3.MOVE_X[r][c]) && turn==MARK_X) ||
                        (action.equals(TTTState3x3.MOVE_O[r][c]) && turn==MARK_O)) {
                    if (board[r][c]==MARK_EMPTY) {
                        moveMade=true;
                        board[r][c]=turn;
                    } else {
                        throw new RuntimeException("Invalid move: "+action);
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
    
    protected boolean goal(int mark) {
        // check rows
        if ((mark==board[0][0] && mark==board[0][1] && mark==board[0][2]) ||
            (mark==board[1][0] && mark==board[1][1] && mark==board[1][2]) ||
            (mark==board[2][0] && mark==board[2][1] && mark==board[2][2])) return true;
        // check columns
        if ((mark==board[0][0] && mark==board[1][0] && mark==board[2][0]) ||
            (mark==board[0][1] && mark==board[1][1] && mark==board[2][1]) ||
            (mark==board[0][2] && mark==board[1][2] && mark==board[2][2])) return true;
        // check diagonals
        if ((mark==board[0][0] && mark==board[1][1] && mark==board[2][2]) ||
            (mark==board[0][2] && mark==board[1][1] && mark==board[2][0])) return true;
        return false; // not a winner
    }
    
    /**
     * Check if goal is achieved. 
     * Note that in the case of TTT this is an evaluation that is based on a check if the previous player was
     * successful in winning.
     */
    public boolean goal() {
        return goal(turn==MARK_X?MARK_O:MARK_X);
    }

    protected ActionStatePair[] successor(int mark) {
        //ArrayList<ActionStatePair> list=new ArrayList<ActionStatePair>();
        ArrayList list=new ArrayList();
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                if (board[r][c]==MARK_EMPTY) {
                    if (mark==TTTState3x3.MARK_X)
                        list.add(new ActionStatePair(MOVE_X[r][c], new TTTState3x3(this, MOVE_X[r][c])));
                    if (mark==TTTState3x3.MARK_O)
                        list.add(new ActionStatePair(MOVE_O[r][c], new TTTState3x3(this, MOVE_O[r][c])));
                }
            }
        }
        ActionStatePair[] pairs=new ActionStatePair[list.size()];
        for (int i=0; i<list.size(); i++) {
            pairs[i]=(ActionStatePair)list.get(i);
        }
        return pairs;
    }
    
    public ActionStatePair[] successor() {
        return successor(turn);
    }

    public double pathcost(Action action) {
        return 1;
    }

    protected int count(int mark) {
        int cnt=0;
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                if (board[r][c]==mark) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    /** 
     * @return current number of empty positions on board
     */
    protected int empty() {
        return count(MARK_EMPTY);
    }

    /** 
     * Evaluates a terminal state
     * @return a positive number if MARK_X (our team) is the winner, and
     *  a negative number if MARK_O is the winner.
     *  A bonus is added if there are empty positions after the win.
     */
    public double utility() {
        if (goal(MARK_X))
            return +(1+empty());
        else if (goal(MARK_O))
            return -(1+empty());
        else
            return 0;
    }

    /**
     * Determine if this state is a state in which MAX acts (we, as opposed to the opponent that we have no control over).
     * @return true if MAX acts, false if opponent acts
     */
    public boolean isMaxState() {
        return (turn==MARK_X);
    }
    
    /**
     * A printable string of the state - displays the tile configuration using newlines.
     * @return a string displaying the tile configuration of the state
     */
    public String toString() {
        StringBuffer sb=new StringBuffer();
        for (int r=0; r<3; r++) {
            sb.append("<");
            for (int c=0; c<3; c++) {
                sb.append((board[r][c]==MARK_X?'X':board[r][c]==MARK_O?'O':' '));
            }
            sb.append(">\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        TTTState3x3 state=new TTTState3x3();
        System.out.println(state);
        state=new TTTState3x3(state, TTTState3x3.MOVE_O[1][1]);
        System.out.println(state);
        state=new TTTState3x3(state, TTTState3x3.MOVE_X[1][2]);
        System.out.println(state);
        state=new TTTState3x3(state, TTTState3x3.MOVE_O[0][1]);
        System.out.println(state);
        ActionStatePair[] succ=state.successor();
        for (int i=0; i<succ.length; i++) {
            System.out.println(succ[i].getAction());
        }
    }
}
