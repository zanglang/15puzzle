package search.tictactoe;

import search.MiniMaxNode;

public class TTT3x3App {

    /**
     * Test program for minimax search procedures
     * @param args none interpreted as yet
     */
    public static void main(String[] args) {
        MiniMaxNode node;
        TTTState3x3 state=new TTTState3x3();
        state=new TTTState3x3(state, TTTState3x3.MOVE_O[1][1]);
        System.out.println(state);
        state=new TTTState3x3(state, TTTState3x3.MOVE_X[2][1]);
        System.out.println(state);
        while (!state.goal() && state.successor().length>0) {
            node=MiniMaxNode.minimaxSearch(state);
            System.out.println(node.getAction());
            state=new TTTState3x3(state, node.getAction());
            System.out.println(state);
        }
        
    }

}
