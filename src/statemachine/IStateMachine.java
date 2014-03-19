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
     * Retrieves all nodes connected to the state machine.
     * 
     * @return List of nodes
     */
    List<Node> getNodes();
    
    /**
     * Executes a round of the algorithm.
     */
    
    void executeRound();
    
    /**
     * Initializes the nodes connected to the state machine.
     * 
     * @param inputs List of nodes.
     */
    
    void initializeNodes(List<Input> inputs);
    
    /**
     * Returns number of rounds run.
     * 
     * @return 
     */
    int getNumberOfRoundsRun();
    
    /**
     * Gets all valid inputs.
     * 
     * @return 
     */
    
    Set<Input> getValidInputs();
    
    /**
     * Tells if all nodes have stopped.
     * 
     * @return 
     */
    boolean hasStopped();
    
    /**
     * Sets the nodes to the machine.
     * 
     * @param nodes 
     */
    void setNodes(List<Node> nodes);
}
