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
        List<INode> path = Lecture1803.getFourNodePath();
        List<Input> inputs = Lecture1803.getInputs(4);
        
        AlgorithmRunner runner = new AlgorithmRunner(stateMachine, path);
        
        runner.runAlgorithm(inputs);

        System.out.println(runner.getNodes());
        System.out.println("Algorithm ran for " + runner.getRoundsRun() + " rounds");
    }

}
