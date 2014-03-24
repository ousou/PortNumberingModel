package main;

import examples.BMMExampleGraph;
import examples.Lecture1803;
import java.util.ArrayList;
import java.util.List;
import network.INode;
import network.Node;
import runner.AlgorithmRunner;
import statemachine.BMM.BMMStateMachine;
import statemachine.IStateMachine;
import statemachine.Input;

/**
 *
 * @author Sebastian Björkqvist
 */
public class Main {

    public static void main(String[] args) {
        //        IStateMachine stateMachine = Lecture1803.getStateMachine();
        //        List<INode> path = Lecture1803.getFourNodePath();
        //        List<Input> inputs = Lecture1803.getInputs(4);
        //
        //        AlgorithmRunner runner = new AlgorithmRunner(stateMachine, path);
        //
        //        runner.runAlgorithm(inputs);
        //
        //        System.out.println(runner.getNodes());
        //        System.out.println("Algorithm ran for " + runner.getRoundsRun() + " rounds");
        List<INode> bipartiteGraph = BMMExampleGraph.getGraph();
        List<Input> inputs = new ArrayList<Input>();
        Input white = new Input("white");
        Input black = new Input("black");
        for (int i = 0; i < bipartiteGraph.size(); i++) {
            if (bipartiteGraph.get(i).getName().contains("white")) {
                inputs.add(white);
            } else {
                inputs.add(black);
            }
        }
        IStateMachine BMMStateMachine = new BMMStateMachine();
        AlgorithmRunner runner = new AlgorithmRunner(BMMStateMachine, bipartiteGraph);
        runner.runAlgorithm(inputs);
        
        List<INode> finishedNodes = runner.getNodes();
        for (INode node : finishedNodes) {
            System.out.println(node);
        }
        System.out.println("Algorithm ran for " + runner.getRoundsRun() + " rounds");        
    }

}
