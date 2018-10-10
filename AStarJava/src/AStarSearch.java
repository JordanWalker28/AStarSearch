import java.util.*;

/**
  The AStarSearch class, along with the AStarNode class,
  implements a generic A* search algorithm. The AStarNode
  class should be subclassed to provide searching capability.
*/
public class AStarSearch {


  /**
    A simple priority list, also called a priority queue.
    Objects in the list are ordered by their priority,
    determined by the object's Comparable interface.
    The highest priority item is first in the list.
  */
  public static class PriorityList extends LinkedList {

    public void add(Comparable object) {
      for (int i=0; i<size(); i++) {
        if (object.compareTo(get(i)) <= 0) {
          add(i, object);
          return;
        }
      }
      addLast(object);
    }
  }


  /**
    Construct the path, not including the start node.
  */
  protected List<AStarNode> constructPath(AStarNode node) {
    LinkedList<AStarNode> path = new LinkedList<>();
    while (node.pathParentMADE != null) {
      path.addFirst(node);
//      System.out.println(node.robotPlace + " " + "ST" +node.packetsToBeMoved[0]+
//        		" LT" +node.packetsToBeMoved[1]+ " Ma" +node.packetsToBeMoved[2]+ " Mb" +node.packetsToBeMoved[3]);
//        System.out.println("  " + "Sa" +node.packetsAlreadyMoved[0]+
//          		" Lb" +node.packetsAlreadyMoved[1]+ " MT" +node.packetsAlreadyMoved[2]);
      node = node.pathParentMADE;
    }
    return path;
  }


  /**
    Find the path from the start node to the end node. A list
    of AStarNodes is returned, or null if the path is not
    found. 
  */
  public List<AStarNode> findPath(AStarNode startNode, AStarNode goalNode, int heuris) {

    PriorityList openList = new PriorityList();
    LinkedList<AStarNode> closedList = new LinkedList<>();

    startNode.pathParentMADE = null;
    startNode.costFromStart = 0;
    if(heuris == 1)
    	startNode.estimatedCostToGoal = startNode.heuristicOne(goalNode);
    else
    	startNode.estimatedCostToGoal = startNode.heuristicTwo(goalNode);
    openList.add(startNode);

    while (!openList.isEmpty()) {
      AStarNode node = (AStarNode)openList.removeFirst();
      if (areEqual(node, goalNode)) {
        // construct the path from start to goal
        return constructPath(node);
      }

      List<AStarNode> neighbors = node.getNeighbors();
      for (int i=0; i<neighbors.size(); i++) {
        AStarNode neighborNode = (AStarNode)neighbors.get(i);
        boolean isOpen = openList.contains(neighborNode);
        boolean isClosed = closedList.contains(neighborNode);
        int costFromStart = node.costFromStart +
          node.getCost(neighborNode);

        // check if the neighbor node has not been
        // traversed or if a shorter path to this
        // neighbor node is found.
        if ((!isOpen && !isClosed) || costFromStart < neighborNode.costFromStart)
        {
          neighborNode.pathParentMADE = node;
          neighborNode.costFromStart = costFromStart;
          if(heuris == 1)
        	  neighborNode.estimatedCostToGoal = neighborNode.heuristicOne(goalNode);
          else
        	  neighborNode.estimatedCostToGoal = neighborNode.heuristicTwo(goalNode);
          if (isClosed) {
            closedList.remove(neighborNode);
          }
          if (!isOpen) {
            openList.add(neighborNode);
            //System.out.println("added to OPEN " + neighborNode.robotPlace + " "+ neighborNode.costFromStart + " " + neighborNode.estimatedCostToGoal);
          }
        }
      }
      closedList.add(node);
      //System.out.println("added to CLOSE " + node.robotPlace + " "+ node.costFromStart + " " + node.estimatedCostToGoal);
    }

    // no path found
    return null;
  }
  
  public boolean areEqual(AStarNode a, AStarNode b){
	  
	  for(int i = 0; i< a.packetsToBeMoved.length; i++){
		  if(a.packetsToBeMoved[i] != b.packetsToBeMoved[i]){
			  return false;
		  }
	  }
	  for(int i = 0; i< a.packetsAlreadyMoved.length; i++){
		  if(a.packetsAlreadyMoved[i] != b.packetsAlreadyMoved[i]){
			  return false;
		  }
	  }
	  return true;
			  
  }
 
  
  public static void main(String[] args){
	  
	  Scanner sc = new Scanner(System.in);
	  
	  String rob = "t";
	  int heur;
	  int[] tobM = new int[4];
	  int[] alrIp = new int[]{0,0,0};
	  
	  int[] tobM1 = new int[]{0,0,0,0};
	  int[] alrIp1 = new int[3];
	  
	  String tmp = "";
	  
	  while(!tmp.equals("1") && !tmp.equals("2")){
		  System.out.println("Enter 1 or 2 for the heuristic you want to use:");
		  tmp = sc.nextLine();		  	  
	  }
	  heur = Integer.parseInt(tmp);
	  
	  tmp = "";
	  while(!tmp.matches("\\d+")){
		  System.out.println("Enter the number of SMALL packages in the truck:");
		  tmp = sc.nextLine();		  	  
	  }
	  tobM[0] = Integer.parseInt(tmp);
	  alrIp1[0] = Integer.parseInt(tmp);
	  
	  tmp = "";
	  while(!tmp.matches("\\d+")){
		  System.out.println("Enter the number of LARGE packages in the truck:");
		  tmp = sc.nextLine();
	  }
	  tobM[1] = Integer.parseInt(tmp);
	  alrIp1[1] = Integer.parseInt(tmp);
	  
	  tmp = "";
	  while(!tmp.matches("\\d+")){
		  System.out.println("Enter the number of MEDIUM packages in the Warehouse A:");
		  tmp = sc.nextLine();
	  }
	  tobM[2] = Integer.parseInt(tmp);
	  alrIp1[2] += Integer.parseInt(tmp);
	  
	  tmp = "";
	  while(!tmp.matches("\\d+")){
		  System.out.println("Enter the number of MEDIUM packages in the Warehouse B:");
		  tmp = sc.nextLine();
	  }
	  tobM[3] = Integer.parseInt(tmp);
	  alrIp1[2] += Integer.parseInt(tmp);
	  
	  System.out.println();
	  
	  AStarNode startState = new AStarNode(tobM, alrIp, rob);

	  AStarNode endState = new AStarNode(tobM1, alrIp1, rob);
	  
	  AStarSearch a = new AStarSearch();
	  
	  LinkedList<AStarNode> l = (LinkedList<AStarNode>) a.findPath(startState, endState, heur);
	  
	  int[] tobMee = tobM;
	  String size = "";
	  Boolean bool;
	  int counter = 0;
	  String path = "";
	  
	  System.out.println("Press enter to make the Robot move one step");
	  
	  for(AStarNode n : l){
		  
		  sc.nextLine();
		  
		  bool = false;
		  for(int i = 0; i< tobMee.length; i++){
			  if(n.packetsToBeMoved[i] < tobMee[i]){
				  if(i==0){
					  size = "LARGE    ";
					  bool =  true;
				  }
				  if(i==1){
					  size = "SMALL    ";
					  bool =  true;
				  }
				  if(i==2 || i==3){
					  size = "MEDIUM   ";
					  bool =  true;
				  }
				  path += rob + " -> ";
				  System.out.println("Moving " + size + ": " + rob + " ---> " + n.robotPlace);
			  }
		  }
		  if(!bool){
			  path += rob + " -> ";
			  System.out.println("Moving NO PACKET: " + rob + " ---> " + n.robotPlace);
		  }
		  counter++;
		  rob = n.robotPlace;
		  tobMee = n.packetsToBeMoved;
	  }
	  path = path.substring(0, path.length() -4);
	  System.out.println("\nRobot moved " + counter + " times");
	  System.out.println("Optimal path: " + path);
	  sc.close();
	  
  }

}