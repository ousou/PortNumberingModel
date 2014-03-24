package statemachine.BMM;

import java.util.HashSet;
import java.util.Set;
import statemachine.IStateMachine;
import statemachine.Input;
import statemachine.Message;
import statemachine.State;

/**
 *
 * @author Sebastian Björkqvist
 */
public class BMMStateMachine implements IStateMachine {

    private Message empty;
    private Message accept;
    private Message matched;
    private Message proposal;
    
    public BMMStateMachine() {
        empty = new Message("empty");
        accept = new Message("accept");
        matched = new Message("matched");
        proposal = new Message("proposal");
    }

    @Override
    public Message[] getOutgoingMessage(int degree, State state) {
        BMMState currentState = (BMMState) state;
        
        if (currentState.white) {
            return processWhiteOutgoing(degree, currentState);
        } else {
            return processBlackOutgoing(degree, currentState);
        }        
    }

    @Override
    public State processIncomingMessage(State state, Message[] messages) {
        BMMState currentState = (BMMState) state;

        if (currentState.isOutputState()) {
            return currentState;
        }

        if (currentState.white) {
            return processWhiteIncoming(currentState, messages);
        } else {
            return processBlackIncoming(currentState, messages);
        }

    }

    @Override
    public State getInitialState(int degree, Input input) {
        BMMState state;
        if (input.getName().equalsIgnoreCase("white")) {
            state = new BMMState(false, true, true, 1, -1, -1, 0, "URWO(1)", false);
        } else if (input.getName().equalsIgnoreCase("black")) {
            state = new BMMState(false, false, true, 1, -1, -1, 0, "URBO(1)", false);
        } else {
            throw new IllegalArgumentException("Illegal input!");
        }
        return state;
    }

    @Override
    public Set<Input> getValidInputs() {
        Set<Input> validInputs = new HashSet<Input>();

        Input white = new Input("white");
        validInputs.add(white);
        Input black = new Input("black");
        validInputs.add(black);

        return validInputs;
    }

    private State processWhiteIncoming(BMMState currentState, Message[] messages) {
        if (currentState.oddRound) {
            if (!currentState.matched && currentState.roundNumber > messages.length) {
                // Node can't be matched anymore, so we stop it.
                return new BMMState(false, true, false, currentState.roundNumber,
                        -1, -1, 0, "USW", true);
            }
            if (currentState.matched) {
                // Node has been matched
                return new BMMState(true, true, false, currentState.roundNumber,
                        currentState.matchedWithPort, -1, 0,
                        "MSW(" + currentState.matchedWithPort + ")", true);
            }
            // We go on to the even round
            return new BMMState(false, true, false, currentState.roundNumber,
                    -1, -1, 0, "URWE(" + currentState.roundNumber + ")", false);
        } else {
            Message relevant = messages[currentState.roundNumber - 1];
            // Checking if we got accept
            if (relevant == accept) {
                return new BMMState(true, true, true, currentState.roundNumber + 1,
                        currentState.roundNumber - 1, -1, 0, "MRW(" + (currentState.roundNumber - 1) + ")", false);
            }
            // Moving back to odd round.
            return new BMMState(false, true, true, currentState.roundNumber + 1,
                    -1, -1, 0, "URWO(" + (currentState.roundNumber + 1) + ")", false);
        }
    }

    private State processBlackIncoming(BMMState currentState, Message[] messages) {
        if (currentState.oddRound) {
            int lowestProposal = -1;
            // Finding lowest proposal
            for (int i = 0; i < messages.length; i++) {
                if (messages[i] == proposal) {
                    lowestProposal = i;
                    break;
                }
            }
            int amountOfMatchedNeighbors = currentState.amountOfMatchedNeighbors;
            // Counting amount of matched neighbors
            for (int i = 0; i < messages.length; i++) {
                if (messages[i] == matched) {
                    amountOfMatchedNeighbors++;
                }
            }
            // Move to even round
            return new BMMState(false, false, false, currentState.roundNumber, -1,
                    lowestProposal, amountOfMatchedNeighbors, "URBE(" + currentState.roundNumber + ")", false);
        } else {
            // Checking if node got accepted
            if (currentState.minimumProposalPort > -1) {
                return new BMMState(true, false, true, currentState.roundNumber + 1,
                        currentState.minimumProposalPort, currentState.minimumProposalPort,
                        currentState.amountOfMatchedNeighbors, "MSB(" + currentState.minimumProposalPort + ")", true);
            }
            // Checking if we can get matched anymore
            if (currentState.amountOfMatchedNeighbors >= messages.length) {
                return new BMMState(false, false, true, currentState.roundNumber + 1,
                        -1, currentState.minimumProposalPort,
                        currentState.amountOfMatchedNeighbors, "USB", true);
            }
            // Otherwise, move back to odd round
            return new BMMState(false, false, true, currentState.roundNumber + 1, -1,
                    currentState.minimumProposalPort, currentState.amountOfMatchedNeighbors,
                    "URBO(" + (currentState.roundNumber + 1) + ")", false);
        }
    }

    private Message[] processWhiteOutgoing(int degree, BMMState currentState) {
        Message[] messages = new Message[degree];
        
        if (currentState.matched) {
            for (int i = 0; i < messages.length; i++) {
                messages[i] = matched;
            }
            return messages;
        }
        for (int i = 0; i < messages.length; i++) {
            messages[i] = empty;
        }
        // If we aren't matched, it only matters what we send on odd rounds
        if (currentState.oddRound && currentState.roundNumber <= degree) {
            messages[currentState.roundNumber - 1] = proposal;
        }       
        
        return messages;
    }

    private Message[] processBlackOutgoing(int degree, BMMState currentState) {
        Message[] messages = new Message[degree];
        
        for (int i = 0; i < messages.length; i++) {
            messages[i] = empty;
        }      
        
        // Here it only matters what we send on even rounds
        if (!currentState.oddRound && currentState.minimumProposalPort > -1) {
            messages[currentState.minimumProposalPort] = accept;
        }
        
        return messages;
    }
}
