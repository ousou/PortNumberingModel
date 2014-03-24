package statemachine.BMM;

import statemachine.State;

/**
 * State for the BMM state machine.
 * 
 * The state machine will name the states in the following way:
 * 
 * URWO(i) - Unmatched running white odd, round i
 * URWE(i) - Unmatched running white even, round i
 * URBO(i) - Unmatched running black odd, round i
 * URBE(i) - Unmatched running black even, round i
 * MRW(j)  - Matched running white, matched to port j
 * 
 * Stopping states:
 * 
 * USW     - Unmatched stopped white
 * USB     - Unmatched stopped black
 * MSW(j)  - Matched stopped white, matched to port j
 * MSB(j)  - Matched stopped black, matched to port j
 * 
 * @author Sebastian Björkqvist
 */
public class BMMState extends State {

    public final boolean matched; // Is node matched or not
    public final boolean white; // Is node white or not
    public final boolean oddRound; // Are we currently on an odd round
    public final int roundNumber; // The current round number (One round has an odd and an even part)
    public final int matchedWithPort; // Matched with this port, -1 if not matched
    public final int minimumProposalPort; // Port from which smallest proposal came, -1 if none or if white node
    public final int amountOfMatchedNeighbors; // Amount of matched neighbors (only used with black nodes)

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
