package search;

/**
 * The State interface.
 * Implement this to search for goal states.
 */
public interface MiniMaxState extends State {
    /**
     * Determine if this state is a state in which MAX acts (we, as opposed to the opponent that we have no control over).
     * @return true if MAX acts, false if opponent acts
     */
    public boolean isMaxState();
    
    /**
     * Determine the "utility" of the state, is usually called when a terminating state is visited.
     * Should be a positive value (not exceeding +9999999) if we're doing well, a negative value (not below -9999999) if
     * we're doing badly.
     * @return the utility of the state
     */
    public double utility();
    
}
