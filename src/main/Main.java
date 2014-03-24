package main;

import examples.BMMExampleGraph;
import examples.Lecture1803;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import network.INode;
import network.NetworkParser;
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
        IStateMachine stateMachine = null;
        List<INode> graph = null;
        List<Input> inputs = null;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            boolean goOn = true;
            while (goOn) {
                printPrompt();
                String input = scanner.nextLine();
                if (input.equals(("1"))) {
                    stateMachine = Lecture1803.getStateMachine();
                    graph = Lecture1803.getFiveNodePath();
                    inputs = Lecture1803.getInputs(5);
                    goOn = false;
                } else if (input.equals("2")) {
                    graph = BMMExampleGraph.getGraph();
                    inputs = new ArrayList<Input>();
                    Input white = new Input("white");
                    Input black = new Input("black");
                    for (int i = 0; i < graph.size(); i++) {
                        if (graph.get(i).getName().contains("white")) {
                            inputs.add(white);
                        } else {
                            inputs.add(black);
                        }
                    }
                    stateMachine = new BMMStateMachine();
                    goOn = false;
                } else if (input.equals("3")) {
                    stateMachine = new BMMStateMachine();
                    NetworkParser parser = new NetworkParser();
                    graph = parser.readNetworkFromInput();
                    inputs = parser.readInputsForNodes(graph);
                    goOn = false;
                } else if (input.equalsIgnoreCase("x")) {
                    System.exit(0);
                } else {
                    System.out.println("Invalid input!");
                }
            }


            AlgorithmRunner runner = new AlgorithmRunner(stateMachine, graph);
            runner.runAlgorithm(inputs);

            System.out.println("");

            System.out.println("The nodes and their states when algorithm stopped: ");
            List<INode> finishedNodes = runner.getNodes();
            for (INode node : finishedNodes) {
                System.out.println(node);
            }
            System.out.println("");
            System.out.println("Algorithm ran for " + runner.getRoundsRun() + " rounds.");

        }
    }

    private static void printPrompt() {
        System.out.println("");
        System.out.println("NOTE: All port numberings start with zero in this implementation. ");
        System.out.println("When entering your own port-numbered network, the node numberings also start with zero.");
        System.out.println("");
        System.out.println("Choose one of the following options: ");
        System.out.println("1) Simulate the simple algorithm presented on the lecture 18.03 on a five node path graph");
        System.out.println("2) Simulate the BMM algorithm on the port-numbered network found in Figure 2.5 in the book");
        System.out.println("3) Simulate the BMM algorithm on a network of your choosing");
        System.out.println("x) Exit the program");
        System.out.print("> ");
    }
}
