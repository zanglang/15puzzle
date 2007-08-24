package search;

/**
 * Node for handling minimax trees. Max and min are alternating going down the search tree.
 */
public class MiniMaxNode extends Node {

    public static int MAXDEPTH=999; // the maximum depth that minimax will search, set it for a specific search using the minimaxSearch method
    
    public MiniMaxNode(MiniMaxState state) {
        super(state);
    }
    
    public MiniMaxNode(MiniMaxState state, MiniMaxNode parent, Action action, double cost) {
        super(state, parent, action, cost);
    }

    /**
     * Expand the node into sub-nodes.
     * It uses the successor-function of the state to determine all Action/State pairs, and generates
     * a node for each.
     * @return all nodes that can be reached from this node
     */
    public Node[] expand() {
        ActionStatePair[] successors=getState().successor();
        Node[] descendants=new MiniMaxNode[successors.length];
        for (int a=0; a<successors.length; a++) {
            Action action=successors[a].getAction();
            MiniMaxState child=(MiniMaxState)successors[a].getState();
            descendants[a]=new MiniMaxNode(child, this, action, this.getCost()+getState().pathcost(action));
        }
        return descendants;
    }

    /** 
     * The function which calculates the utility for this node.
     *  If the state is non-terminal it uses the minimax strategy to create new nodes
     *  (with expanded states).
     *  Alpha-beta pruning is used to reduce the search.
     *  See Russell and Norvig, Artificial Intelligence: A modern approach, 2nd ed., pp. 162-170. Prentice-Hall, 2003.
     *  @param alpha the alpha param should be set to -9999999 in the first call
     *  @param beta the beta param should be set to +9999999 in the first call
     */
    protected double value(double alpha, double beta) {
        // extract state from this node
        MiniMaxState state=(MiniMaxState)getState();    
        // test if this is a goal state for the player that acts
        if (state.goal()) {                             
            // calculates the value of the goal state (should be positive for the winning player, negative otherwise)
            return state.utility();                     
        } else if (getDepth()>MAXDEPTH) { // or if this is a state beyond expansion
            return state.utility();
        } else { // not a goal state
            // ensure that the derived utilities will at least be an improvement from these values...
            double maxUtility=-9999999;     
            double minUtility=+9999999;
            // expand the current node into descending nodes (alternating the active player)
            Node[] succ=expand();
            // test if this is a terminating, non-goal state, if so return what the state is "worth"
            if (succ.length==0) {
                return state.utility();
            }
            if (state.isMaxState()) { // MAX acts... we want to find the highest utility
                // Each of the newly expanded nodes is explored 
                for (int i=0; i<succ.length; i++) {
                    maxUtility=Math.max(maxUtility, ((MiniMaxNode)succ[i]).value(alpha, beta));
                    // However, as soon as we have found a node that renders all remaining nodes irrelevant
                    // we can terminate further exploration
                    // Note that this presents an opportunity to optimise the order of expanded states so as to
                    // minimise the nodes that are explored before termination.
                    if (maxUtility>=beta)
                        return maxUtility;
                    alpha=Math.max(alpha, maxUtility);
                }
                return maxUtility;
            } else { // MIN acts ... we need to face the worst case (lowest utility) 
                for (int a=0; a<succ.length; a++) {
                    minUtility=Math.min(minUtility, ((MiniMaxNode)succ[a]).value(alpha, beta));
                    // when the opponent acts the pruning works similar to the above
                    if (minUtility<=alpha)
                        return minUtility;
                    beta=Math.min(beta, minUtility);
                }
                return minUtility;
            }
        }
    }

    /** 
     * The minimax function which calculates the utility for this node.
     *  If the state is non-terminal it uses the minimax strategy to create new nodes (with the expanded states).
     *  See Russell and Norvig, Artificial Intelligence: A modern approach, 2nd ed., pp. 162-167. Prentice-Hall, 2003.
     */
    protected double value() {
        // extract state from this node
        MiniMaxState state=(MiniMaxState)getState();    
        // test if this is a goal state for the player that acts
        if (state.goal()) {                             
            // calculates the value of the goal state (should be positive for the winning player, negative otherwise)
            return state.utility();                     
        } else if (getDepth()>MAXDEPTH) { // or if this is a state beyond expansion
            return state.utility();
        } else { // not a goal state
            // ensure that the derived utilities will at least be an improvement from these values...
            double maxUtility=-9999999;     
            double minUtility=+9999999;
            // expand the current node into descending nodes (alternating the active player)
            Node[] succ=expand();
            // test if this is a terminating, non-goal state, if so return what the state is "worth"
            if (succ.length==0) {
                return state.utility();
            }
            if (state.isMaxState()) { // MAX acts... we want to find the highest utility
                // Each of the newly expanded nodes is explored 
                for (int i=0; i<succ.length; i++) {
                    maxUtility=Math.max(maxUtility, ((MiniMaxNode)succ[i]).value());
                }
                return maxUtility;
            } else { // MIN acts ... we need to face the worst case (lowest utility) 
                for (int a=0; a<succ.length; a++) {
                    minUtility=Math.min(minUtility, ((MiniMaxNode)succ[a]).value());
                }
                return minUtility;
            }
        }
    }

    /**
     * Perform a Mini-Max search from the specified initial state. 
     * Amongst the descending "nodes", the one with the best utility is returned.
     * What is "best" is determined by looking at whose turn it is (and that information
     * is found in the initial Mini-Max state).
     * Always just an "action" from the specified state. 
     * @param initial The initial state
     * @return the node that presents the best option for the current player (whoever it is)
     */
    public static MiniMaxNode minimaxSearch(MiniMaxState initial) {
        MiniMaxNode root=new MiniMaxNode(initial);
        Node[] choice=root.expand();
        double[] util=new double[choice.length];
        int best=0;
        for (int i=0; i<choice.length; i++) {
            util[i]=((MiniMaxNode)choice[i]).value(-9999999,+9999999); // with alpha-beta pruning
//            util[i]=((MiniMaxNode)choice[i]).value();  // without alpha-beta pruning
//            System.out.println(choice[i].getState()+":"+util[i]);  // debug by printing out all options with their corresponding utility-values
            System.out.print(util[i]+" ");  // debug by printing out all utility-values
            // we select the action that gives the best future utility
            // note that in some cases several actions may result in the same utility and that this 
            // could present an opportunity to introduce arbitrary heuristics
            if (initial.isMaxState()) { // we "MAX" is deciding what to do
                if (util[i]>util[best]) 
                    best=i;
            } else { // opponent "MIN" is deciding
                if (util[i]<util[best]) 
                    best=i;
            }
        }
        return (MiniMaxNode)choice[best];
    }
    
    public static MiniMaxNode minimaxSearch(MiniMaxState initial, int maxDepth) {
        MAXDEPTH=maxDepth;
        return minimaxSearch(initial);
    }
}
