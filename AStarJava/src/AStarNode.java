
import java.util.LinkedList;

/**
  The AStarNode class, along with the AStarSearch class,
  implements a generic A* search algorithm. The AStarNode
  class should be subclassed to provide searching capability.
*/
public class AStarNode implements Comparable<Object>{

  AStarNode pathParentMADE; // null in the beginning
  int costFromStart;
  int[] packetsToBeMoved; //S-in-T, L-in-T, M-in-A, M-in-B
  int[] packetsAlreadyMoved; // S-in-A, L-inB, M-in-T
  String robotPlace;
  int estimatedCostToGoal;
  
  public AStarNode(int[] toMove,int[] inPlace, String startIn){
	  
	  if(startIn.equals("t") || startIn.equals("a") ||startIn.equals("b"))
		  robotPlace = startIn;
	  else
		  System.err.println("INPUT t, a or b");
	  
	  if(toMove.length == 4)
		  packetsToBeMoved = toMove;
	  else
		  System.err.println("INPUT AN ARRAY OF SIZE 4 FOR toMOve");
	  
	  if(inPlace.length == 3)
		  packetsAlreadyMoved = inPlace;
	  else
		  System.err.println("INPUT AN ARRAY OF SIZE 3 FOR inPlace");

  }


  public int getCost() {
	  if(pathParentMADE == null){
		  return estimatedCostToGoal;
	  }
    return costFromStart + estimatedCostToGoal;
  }

  public int compareTo(Object other) {
	  int thisValue = this.getCost();
	  int otherValue = ((AStarNode)other).getCost();

	  int v = thisValue - otherValue;
	   return (v>0)?1:(v<0)?-1:0; // sign function
	 }


  /**
    Gets the cost between this node and the specified
    adjacent (AKA "neighbor" or "child") node.
  */
  public int getCost(AStarNode node){
    return 1 ;
  }


  /**
    Gets the estimated cost between this node and the
    specified node. The estimated cost should never exceed
    the true cost. The better the estimate, the more
    effecient the search.
  */
  public int heuristicOne(AStarNode node){
	  
//---------------------HEURISTIC-----------------------------
	  
	  int estimated = 0;
	  
	  for(int i = 0; i < packetsToBeMoved.length; i++){
		  estimated += packetsToBeMoved[i];		  
	  }
	  
	  if(pathParentMADE == null){
		  return estimated;
	  }
		  
	  if(pathParentMADE.robotPlace.equals("t")){
		  if(robotPlace.equals("a"))
			  estimated = estimated - packetsToBeMoved[2];
		  if(robotPlace.equals("b"))
			  estimated = estimated - packetsToBeMoved[3];
	  }
	  
	  if(pathParentMADE.robotPlace.equals("a")){
		  if(robotPlace.equals("t")){
			  if(packetsToBeMoved[0] > packetsToBeMoved[1])
				 estimated = estimated - packetsToBeMoved[0];
			  else
				 estimated = estimated - packetsToBeMoved[1]; 			  
		  }
		  if(robotPlace.equals("b"))
			  estimated = estimated - packetsToBeMoved[3];
	  }
	  
	  if(pathParentMADE.robotPlace.equals("b")){
		  if(robotPlace.equals("t")){
			  if(packetsToBeMoved[0] > packetsToBeMoved[1])
				 estimated = estimated - packetsToBeMoved[0];
			  else
				 estimated = estimated - packetsToBeMoved[1]; 			  
		  }
		  if(robotPlace.equals("a"))
			  estimated = estimated - packetsToBeMoved[2];
	  }
	  

	  return estimated;
	  
	//-----------------------------------------------------------
  }
  
  
  public int heuristicTwo(AStarNode node){
	  
	//---------------------HEURISTIC-----------------------------
		  
		  int estimated = 0;
		  
		  for(int i = 0; i < packetsToBeMoved.length; i++){
			  estimated += packetsToBeMoved[i];		  
		  }
		  
		  return estimated;
		  
		//-----------------------------------------------------------
	  }

  /**
    Gets the children (AKA "neighbors" or "adjacent nodes")
    of this node.
  */
  public LinkedList<AStarNode> getNeighbors(){
	  
	  LinkedList<AStarNode> l = new LinkedList<>();
	  
	  if(robotPlace.equals("t")){
		  
		  if(packetsToBeMoved[0] > 0){ //there are any packets to be moved to a in the truck
			  int[] tobM =new int[] {(packetsToBeMoved[0] -1), packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
			  int[] alrIp =new int[] {(packetsAlreadyMoved[0] + 1), packetsAlreadyMoved[1], packetsAlreadyMoved[2]};
			  l.add(new AStarNode(tobM, alrIp, "a")); // create node move to a
		  }
		  else{
			  int[] tobM =new int[] {packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
		  	  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1], packetsAlreadyMoved[2]};
		  	  l.add(new AStarNode(tobM, alrIp, "a")); // create node move to a
		  }
		  
		  
		  if(packetsToBeMoved[1] > 0){ //there are any packets to be moved to a in the truck
			  int[] tobM =new int[] { packetsToBeMoved[0], (packetsToBeMoved[1] -1), packetsToBeMoved[2], packetsToBeMoved[3] };
			  int[] alrIp =new int[] {packetsAlreadyMoved[0],(packetsAlreadyMoved[1] + 1),  packetsAlreadyMoved[2]};
			  l.add(new AStarNode(tobM, alrIp, "b")); // create node move to b
		  }
		  else{
			  int[] tobM =new int[] {packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
		  	  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1], packetsAlreadyMoved[2]};
		  	  l.add(new AStarNode(tobM, alrIp, "b")); // create node move to b
		  }
		  		  
	  }
	  
	  if(robotPlace.equals("a")){
		  
		  if(packetsToBeMoved[2] > 0){ //there are any packets to be moved to a in the truck
			  int[] tobM =new int[] {packetsToBeMoved[0], packetsToBeMoved[1], (packetsToBeMoved[2]-1), packetsToBeMoved[3] };
			  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1], (packetsAlreadyMoved[2] + 1)};
			  l.add(new AStarNode(tobM, alrIp, "t")); // create node move to a
		  }
		  else{
			  int[] tobM =new int[] {packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
		  	  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1], packetsAlreadyMoved[2]};
		  	  l.add(new AStarNode(tobM, alrIp, "t")); // create node move to a
		  }
		  
			  int[] tobM =new int[] { packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
			  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1],  packetsAlreadyMoved[2]};
			  l.add(new AStarNode(tobM, alrIp, "b")); // create node move to b		  		  
	  }
	  
	  if(robotPlace.equals("b")){
		  
		  if(packetsToBeMoved[3] > 0){ //there are any packets to be moved to a in the truck
			  int[] tobM =new int[] {packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], (packetsToBeMoved[3]-1) };
			  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1], (packetsAlreadyMoved[2] + 1)};
			  l.add(new AStarNode(tobM, alrIp, "t")); // create node move to a
		  }
		  else{
			  int[] tobM =new int[] {packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
		  	  int[] alrIp =new int[] {packetsAlreadyMoved[0], packetsAlreadyMoved[1], packetsAlreadyMoved[2]};
		  	  l.add(new AStarNode(tobM, alrIp, "t")); // create node move to a
		  }
		  
			  int[] tobM =new int[] { packetsToBeMoved[0], packetsToBeMoved[1], packetsToBeMoved[2], packetsToBeMoved[3] };
			  int[] alrIp =new int[] {packetsAlreadyMoved[0],packetsAlreadyMoved[1],  packetsAlreadyMoved[2]};
			  l.add(new AStarNode(tobM, alrIp, "a")); // create node move to b
	  }
	  
	  return l;
	  
  }
  
 
  
}  