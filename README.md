PortNumberingModel
==================

A simulator for distributed algorithms in the port-numbering model.

To use the simulator, you first need to define how the state machine works. This is implemented in the class StateMachine.
The class StateMachine has maps sendFunctions, initFunctions and receiveFunctions. These correspond to the functions send, init and receive in the model. The maps define the behavior of nodes in different states. You may also define some default behavior for the functions using the maps sendFunctionDefaults, initFunctionDefaults and receiveFunctionDefaults. These maps will be used if no entry in the main maps are found in some situation.

To create a port-numbered network, you first must create the nodes in the network (class INode/Node). After that you have to specify the connections between the nodes using the class ConnectionFunction, and finally you must give that connection function to each of the nodes.

When you have done this, you can create an instance of the class AlgorithmRunner and run the method runAlgorithm, to which you need to give the inputs to the nodes. Note that the inputs are in a list, and the order of elements in the input list correspond to the order of elements in the node list you gave to AlgorithmRunner.

The method runAlgorithm runs until all nodes have stopped. After that, you may examine the states of the nodes by retrieving them using the method getNodes.

See the main method for an example of how to use the class AlgorithmRunner, and the package examples for examples on how to create the state machine and the port-numbered network.
