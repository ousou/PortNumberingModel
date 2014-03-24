package statemachine.BMM;

import statemachine.State;

/**
 * State for the BMM state machine.
 * 
 * @author Sebastian Björkqvist
 */
public class BMMState extends State {

    public final boolean matched;    
    public final boolean white;
    public final boolean oddRound;
    public final int roundNumber;
    public final int matchedWithPort;
    public final int minimumProposalPort;    
    public final int amountOfMatchedNeighbors;

    public BMMState(boolean matched, boolean white, boolean 
            oddRound, int roundNumber, int matchedWithPort, int minimumProposalPort, 
            int amountOfMatchedNeighbors, String name, boolean outputState) {
        super(name, outputState);
        this.matched = matched;
        this.white = white;
        this.oddRound = oddRound;
        this.roundNumber = roundNumber;
        this.matchedWithPort = matchedWithPort;
        this.minimumProposalPort = minimumProposalPort;
        this.amountOfMatchedNeighbors = amountOfMatchedNeighbors;
    }
}
