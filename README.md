PortNumberingModel
==================

A simulator for distributed algorithms in the port-numbering model.

To run the algorithms implemented as a reference, run the program from the command line with the following command:

java -jar Port-numbering_model.jar

You may run algorithms on pre-entered graphs, or enter your own graph.



To implement your own algorithm, you first need to define how the state machine works. This is done by implementing the interface IStateMachine. A crude general implementation is given in the class StateMachine. There you have to specify the transition functions completely, which is very tedious. The class StateMachine has maps sendFunctions, initFunctions and receiveFunctions. These correspond to the functions send, init and receive in the model. The maps define the behavior of nodes in different states. You may also define some default behavior for the functions using the maps sendFunctionDefaults, initFunctionDefaults and receiveFunctionDefaults. These maps will be used if no entry in the main maps are found in some situation. See the class Lecture1803 in the examples-package for an example of how to use the class StateMachine.

You may also implement the IStateMachine-interface yourself. A reference implementation is provided for the bipartite maximal matching-algorithm in the package statemachine.BMM.

To create a port-numbered network, you first must create the nodes in the network, i.e. implement the interface INode. A reference implementation is done in the class Node. If you use that implementation, you have to specify the connections between the nodes using the class ConnectionFunction, and finally you must give that connection function to each of the nodes.

When you have done this, you can create an instance of the class AlgorithmRunner and run the method runAlgorithm, to which you need to give the inputs to the nodes. Note that the inputs are in a list, and the order of elements in the input list correspond to the order of elements in the node list you gave to AlgorithmRunner.

The method runAlgorithm runs until all nodes have stopped. After that, you may examine the states of the nodes by retrieving them using the method getNodes.

