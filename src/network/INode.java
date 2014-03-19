package network;

import statemachine.IStateMachine;
import statemachine.Message;
import statemachine.State;

/**
 * Interface for nodes in a port-numbered network.
 * 
 * @author Sebastian Björkqvist
 */
public interface INode {

    /**
     * Sets the initial state of the node.
     * 
     * @param initialState State
     */
    void setInitialState(State initialState);

    /**
     * Gives the node a state machine.
     * 
     * @param stateMachine 
     */       
    void setStateMachine(IStateMachine stateMachine);
    
    /**
     * Sends messages to other nodes. 
     */
    void sendMessages();
    
    /**
     * Receives message to given port.
     * 
     * @param message 
     * @param port 
     */
    void receiveMessage(Message message, int port);
    
    /**
     * Updates the state of the node.
     */
    void updateState();

    int getDegree();

    State getCurrentState();
}
