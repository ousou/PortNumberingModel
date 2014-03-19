package main;

import examples.Lecture1803;
import java.util.List;
import network.Node;
import statemachine.IStateMachine;
import statemachine.Input;

/**
 *
 * @author Sebastian Björkqvist
 */
public class Main {

    public static void main(String[] args) {
        IStateMachine stateMachine = Lecture1803.getStateMachine();
        List<Node> path = Lecture1803.getFiveNodePath();
        List<Input> inputs = Lecture1803.getInputs(5);
        
        stateMachine.setNodes(path);        
        stateMachine.initializeNodes(inputs);
        for (Node n : path) {
            n.setStateMachine(stateMachine);
        }
        while (!stateMachine.hasStopped()) {
            stateMachine.executeRound();
        }        
        System.out.println(stateMachine.getNodes());
        System.out.println("Algorithm ran for " + stateMachine.getNumberOfRoundsRun() + " rounds");
    }

}
