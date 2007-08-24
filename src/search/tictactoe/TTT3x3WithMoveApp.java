package search.tictactoe;

import search.MiniMaxNode;

public class TTT3x3WithMoveApp {

    /**
     * Test program for minimax search procedures
     * @param args none interpreted as yet
     */
    public static void main(String[] args) {
        MiniMaxNode node;
        TTTState3x3WithMove state=new TTTState3x3WithMove();
        state=new TTTState3x3WithMove(state, TTTState3x3WithMove.MOVE_O[0][0][0][0]);
        System.out.println(state);
        state=new TTTState3x3WithMove(state, TTTState3x3WithMove.MOVE_X[1][1][1][1]);
        System.out.println(state);
        state=new TTTState3x3WithMove(state, TTTState3x3WithMove.MOVE_O[1][0][1][0]);
        System.out.println(state);
        state=new TTTState3x3WithMove(state, TTTState3x3WithMove.MOVE_X[2][0][2][0]);
        System.out.println(state);
        state=new TTTState3x3WithMove(state, TTTState3x3WithMove.MOVE_O[0][2][0][2]);
        System.out.println(state);
        while (!state.goal() && state.successor().length>0) {
            if (state.turn==TTTState3x3WithMove.MARK_X)
                node=MiniMaxNode.minimaxSearch(state,3);
            else
                node=MiniMaxNode.minimaxSearch(state,0);
            System.out.println(node.getAction());
            state=new TTTState3x3WithMove(state, node.getAction());
            System.out.println(state);
        }
        
    }

}
