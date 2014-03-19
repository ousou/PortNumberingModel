package main;

import examples.Lecture1803;
import java.util.List;
import network.INode;
import network.Node;
import runner.AlgorithmRunner;
import statemachine.IStateMachine;
import statemachine.Input;

/**
 *
 * @author Sebastian Björkqvist
 */
public class Main {

    public static void main(String[] args) {
        IStateMachine stateMachine = Lecture1803.getStateMachine();
        List<INode> path = Lecture1803.getFiveNodePath();
        List<Input> inputs = Lecture1803.getInputs(5);
        
//        stateMachine.setNodes(path);        
//        stateMachine.initializeNodes(inputs);

        AlgorithmRunner runner = new AlgorithmRunner(stateMachine, path);
        
        runner.runAlgorithm(inputs);
        

//        while (!stateMachine.hasStopped()) {
//            stateMachine.executeRound();
//        }        
        System.out.println(runner.getNodes());
        System.out.println("Algorithm ran for " + runner.getRoundsRun() + " rounds");
    }

}
