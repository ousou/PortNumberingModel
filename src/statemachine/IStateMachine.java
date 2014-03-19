package statemachine;

import java.util.List;
import java.util.Set;
import network.Node;

/**
 *
 * @author Sebastian Björkqvist
 */
public interface IStateMachine {

    /**
     * Gets the outgoing messages for a node.
     *
     * @param degree Degree of node
     * @param state Current state
     * @return List of messages
     */
    Message[] getOutgoingMessage(int degree, State state);

    /**
     * Processes incoming messages for a node.
     *
     * If the state in the argument is a stopping state, the method returns the
     * same state.
     * 
     * @param state Current state
     * @param messages Received messages
     * @return New state
     */
    State processIncomingMessage(State state, Message[] messages);

    /**
     * Gets the initial state for an input.
     * 
     * @param degree Degree of node
     * @param input Input
     * @return Initial state
     */
    State getInitialState(int degree, Input input);
    
  
    /**
     * Gets all valid inputs.
     * 
     * @return 
     */
    
    Set<Input> getValidInputs();
    
}
