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
public class Node implements INode {

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

    @Override
    public void setInitialState(State initialState) {
        this.currentState = initialState;
    }

    @Override
    public void setStateMachine(IStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
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

    @Override
    public void receiveMessage(Message message, int port) {
        receivedMessages[port] = message;
    }
    
    @Override
    public void updateState() {
        currentState = stateMachine.processIncomingMessage(currentState, receivedMessages);        
    }

    @Override
    public int getDegree() {
        return degree;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public String toString() {
        return "Node{" + "name=" + name + ", degree=" + degree + ", currentState=" + currentState + '}';
    }

    @Override
    public String getName() {
        return name;
    }
}
