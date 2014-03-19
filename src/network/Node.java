package network;

import statemachine.IStateMachine;
import statemachine.Message;
import statemachine.State;

/**
 * Class representing a node in a port-numbered network.
 * 
 * NOTE! Port numbering starts from 0!
 * 
 * @author Sebastian Björkqvist
 */
public class Node {

    private final int degree;
    private ConnectionFunction connectionFunction;
    private State currentState;
    private IStateMachine stateMachine;
    private final Message[] receivedMessages;
    private final String name;
    
    public Node(String name, int degree) {
        this.name = name;
        this.degree = degree;
        this.receivedMessages = new Message[degree];
    }

    public Node(int degree) {
        this.degree = degree;
        this.receivedMessages = new Message[degree];
        this.name = "";
    }
    
    public void setConnectionFunction(ConnectionFunction connectionFunction) {
        this.connectionFunction = connectionFunction;
    }

    public void setInitialState(State initialState) {
        this.currentState = initialState;
    }

    public void setStateMachine(IStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
    
    /**
     * Sends messages to ports.
     */
    
    public void sendMessages() {
        if (connectionFunction == null) {
            throw new RuntimeException("No connection function set!");
        }
        Message[] msgList = stateMachine.getOutgoingMessage(degree, currentState);
        
        for (int i = 0; i < msgList.length; i++) {            
            Message msg = msgList[i];
            Port connectedPort = connectionFunction.getConnectedPort(new Port(this, i));
            connectedPort.getNode().receiveMessage(msg, connectedPort.getPortNumber());
        }
    }
    
    /**
     * Receives message from a port.
     * 
     * @param message Received message
     * @param port Port
     */
    public void receiveMessage(Message message, int port) {
        receivedMessages[port] = message;
    }
    
    /**
     * Updates state using current received messages.
     */
    public void updateState() {
        currentState = stateMachine.processIncomingMessage(currentState, receivedMessages);        
    }

    public int getDegree() {
        return degree;
    }

    public State getCurrentState() {
        return currentState;
    }

    @Override
    public String toString() {
        return "Node{" + "degree=" + degree + ", currentState=" + currentState + ", name=" + name + '}';
    }
    
    
    
    
}
