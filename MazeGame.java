import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import javalib.impworld.*;
import javalib.worldimages.*;
import tester.Tester;

//represents a vertex/cell in the maze
class Vertex {
  public static final int WIDTH_PX = 20; // width of the cell
  Posn position; // position of the cell
  ArrayList<Edge> edges = new ArrayList<Edge>(); // the list of edges connected to this cell

  boolean left = false; // is there vertex to the left?
  boolean right = false; // is there vertex to the right?
  boolean top = false; // is there vertex to the top?
  boolean bottom = false; // is there vertex to the bottom?
  boolean seen = false; // has this cell been seen?
  boolean current = false; // is the user currently on this cell?
  Color color = new Color(190, 190, 190);

  Vertex(Posn position) {
    this.position = position;
    this.color = new Color(220, 220, 220);
  }

  // link method
  // EFFECT: adds the given edge to this vertex's list of edges
  void addEdge(Edge connect) {
    this.edges.add(connect);
  }

  // draw method; changes color of the vertex depending on certain conditions
  WorldImage draw(ArrayList<Edge> edgesInTree, Posn endPosition) {
    WorldImage square = new RectangleImage(WIDTH_PX, WIDTH_PX, OutlineMode.SOLID, this.color);

    if (!this.left) {
      WorldImage lineLeft = new LineImage(new Posn(0, WIDTH_PX), Color.BLACK);
      square = new BesideImage(lineLeft, square);
    }

    if (!this.right) {
      WorldImage lineRight = new LineImage(new Posn(0, WIDTH_PX), Color.BLACK);
      square = new BesideImage(square, lineRight);
    }

    if (!this.top) {
      WorldImage lineTop = new LineImage(new Posn(WIDTH_PX, 0), Color.BLACK);
      square = new AboveImage(lineTop, square);
    }

    if (!this.bottom) {
      WorldImage lineBottom = new LineImage(new Posn(WIDTH_PX, 0), Color.BLACK);
      square = new AboveImage(square, lineBottom);
    }
    return square;
  }

  // returns the vertex on the other end of the given edge; assumes the given edge
  // is attached to
  // this vertex
  Vertex getConnectedVertex(Edge e) {
    return e.getOther(this);
  }

  // returns the vertex that connects this vertex to the given vertex
  Edge getEdgeTo(Vertex v) {
    for (Edge e : this.edges) {
      if (this.getConnectedVertex(e).equals(v)) {
        return e;
      }
    }
    return this.edges.get(0);
  }

  // determines whether this vertex has vertices adjacent to it depending on the
  // given list of edges
  // in trees
  // EFFECT: changes this.left, this.right, this.top, this.bottom, to true if
  // there is a vertex
  // connecting to it
  void changeBools(ArrayList<Edge> edgesInTree) {
    for (Edge e : this.edges) {
      if (edgesInTree.contains(e)) {
        Vertex other = this.getConnectedVertex(e);

        if (other.position.x - this.position.x < 0) {
          this.left = true;
        }
        else if (other.position.x - this.position.x > 0) {
          this.right = true;
        }
        else if (other.position.y - this.position.y > 0) {
          this.bottom = true;
        }
        else if (other.position.y - this.position.y < 0) {
          this.top = true;
        }
      }
    }
  }

  // EFFECT: changes this.seen to true
  void makeSeen() {
    this.seen = true;
    if (!this.position.equals(new Posn(0, 0))) {
      this.color = new Color(108, 190, 229);
    }
  }

  // EFFECT: changes this.current to true
  void makeCurrent() {
    this.current = true;
    if (!this.position.equals(new Posn(0, 0))) {
      this.color = new Color(64, 106, 201);
    }
  }

  // EFFECT: changes this.current to false
  void makeNotCurrent() {
    this.current = false;
    if (!this.position.equals(new Posn(0, 0))) {
      this.color = new Color(220, 220, 220);
    }
  }

  // EFFECT: changes this.color to the right color
  void solve() {
    this.color = new Color(64, 106, 201);
  }
}

//represents an edge
class Edge {
  Vertex from; // the vertex on one end of this edge
  Vertex to; // the vertex on the other end of the edge
  int weight; // the weight of this cell
  public static final int WIDTH_PX = 30;

  Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
    this.to.addEdge(this);
    this.from.addEdge(this);
  }

  // gets the vertex on the other end of this edge
  Vertex getOther(Vertex v) {
    if (v.equals(this.from)) {
      return this.to;
    }
    else {
      return this.from;
    }
  }
}

//a way to compare the weights of the edges
class CompareEdges implements Comparator<Edge> {

  public int compare(Edge e1, Edge e2) {
    return e1.weight - e2.weight;
  }
}

//represents a graph
class Graph {
  ArrayList<Vertex> vertices;

  Graph() {
    this.vertices = new ArrayList<Vertex>();
  }

  // adds a vertex to this graph
  // EFFECT: adds a vertex to this.vertices
  void addVertex(Vertex v) {
    this.vertices.add(v);
  }
}

//represents a maze
class Maze extends World {

  public static final int WIDTH_PX = 20; // width of each cell
  Graph graph; // the graph
  ArrayList<Edge> edgesInTree; // edges in the minimal spanning tree
  int width;
  int height;
  Random rand;
  HashMap<Posn, Posn> representatives; // represents each vertex and the tree it's a part of
  ArrayList<Edge> worklist; // all the edges in the maze

  int searchMode; // 1 breadth-first, 2 depth-first, 3 user search
  int current; // index of current Vertex for user search
  boolean isSearching; // are we currently searching for the right path?
  boolean isSolved; // is the maze solved?

  LinkedList<Vertex> alreadySeen; // contains the cells that have already been seen by the search
  // methods
  LinkedList<Vertex> worklistSeen; // the worklist for the search methods
  HashMap<Posn, Edge> cameFromEdge; // a hashmap containing vertices and the edges they came from
  LinkedList<Vertex> correctPath; // contains the vertices in the correct path

  // for testing
  Maze(int width, int height, Random rand) {
    this.graph = new Graph();
    this.edgesInTree = new ArrayList<Edge>();
    this.width = width;
    this.height = height;
    this.rand = rand;
    this.representatives = new HashMap<Posn, Posn>();
    this.worklist = new ArrayList<Edge>();
    this.searchMode = 3;
    this.current = 0;
    this.isSearching = false;
    this.isSolved = false;

    this.alreadySeen = new LinkedList<Vertex>();
    this.worklistSeen = new LinkedList<Vertex>();
    this.cameFromEdge = new HashMap<Posn, Edge>();
    this.correctPath = new LinkedList<Vertex>();

    this.createNodes(width, height, rand);
    this.spanningTree();

    this.worklistSeen.add(this.graph.vertices.get(0));
  }

  Maze(int width, int height) {
    this(width, height, new Random());
  }

  // creates all the nodes in the graph/maze; produces the edges too
  // EFFECT: adds the created vertices to this.graph, and the created edges to
  // their respective
  // vertices' list of edges
  void createNodes(int width, int height, Random rand) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Vertex vert = new Vertex(new Posn(j, i));
        this.graph.addVertex(vert);
        this.representatives.put(vert.position, vert.position);
      }
    }
    this.graph.vertices.get(0).color = new Color(95, 177, 94);
    this.graph.vertices.get(this.width * this.height - 1).color = new Color(177, 126, 202);

    for (int i = 0; i < this.graph.vertices.size(); i++) {
      if (i < this.width * (this.height - 1)) {
        Edge e = new Edge(this.graph.vertices.get(i), this.graph.vertices.get(i + this.width),
            rand.nextInt(30));
        this.worklist.add(e);
      }
      if (i % this.width < (this.width - 1)) {
        Edge e = new Edge(this.graph.vertices.get(i), this.graph.vertices.get(i + 1),
            rand.nextInt(30));
        this.worklist.add(e);
      }
    }
  }

  // produces the minimal spanning tree; adds the results to this.edgesInTree
  // EFFECT: sets up the initial representatives hashmap (pairing each vertices'
  // position with its
  // own) and adds edges to the worklist; adds appropriate edges to
  // this.edgesInTree and updates
  // representatives
  void spanningTree() {
    HashMapUtils utils = new HashMapUtils();

    worklist.sort(new CompareEdges());
    int edgeCount = 0;

    while (this.edgesInTree.size() < this.graph.vertices.size() - 1
        && edgeCount < worklist.size()) {
      Edge e = worklist.get(edgeCount);
      if (!utils.find(this.representatives, e.from.position)
          .equals(utils.find(this.representatives, e.to.position))) {

        this.edgesInTree.add(e);
        utils.union(this.representatives, utils.find(this.representatives, e.from.position),
            utils.find(this.representatives, e.to.position));
      }
      edgeCount = edgeCount + 1;
    }

    for (Vertex v : this.graph.vertices) {
      v.changeBools(this.edgesInTree);
    }
  }

  // backtracks throught the maze from the end to the beginning to find the
  // correct path
  // EFFECT: using the while loop, it continuously updates this.correctpath until
  // it reaches the
  // starting vertex
  public void reconstruct() {
    Vertex connect = this.graph.vertices.get(this.width * this.height - 1);
    this.correctPath.addFirst(connect);
    Edge e = this.cameFromEdge.get(connect.position);
    while (!connect.getConnectedVertex(e).equals(this.graph.vertices.get(0))) {
      connect = connect.getConnectedVertex(e);
      e = this.cameFromEdge.get(connect.position);
      this.correctPath.addFirst(connect);
    }
    this.correctPath.addFirst(this.graph.vertices.get(0));
    return;
  }

  // makes the scene
  public WorldScene makeScene() {
    WorldScene background = this.getEmptyScene();
    WorldImage boardImage = this.drawMaze();
    WorldImage settings = this.drawSettings().movePinhole(-(this.width * WIDTH_PX) / 2, -135 / 2);
    background.placeImageXY(boardImage, 0, 0);
    background.placeImageXY(settings, 0, this.height * WIDTH_PX);
    return background;
  }

  // draws the maze
  WorldImage drawMaze() {
    WorldImage board = new RectangleImage(WIDTH_PX * this.width, WIDTH_PX * this.height,
        OutlineMode.SOLID, Color.BLACK);
    // add each Cell to the drawing
    for (Vertex v : this.graph.vertices) {
      board = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
          v.draw(this.edgesInTree, new Posn(this.width - 1, this.height - 1)),
          -v.position.x * WIDTH_PX, -v.position.y * WIDTH_PX, board)
          .movePinhole(-(this.width * WIDTH_PX) / 2, -(this.height * WIDTH_PX) / 2);
    }
    return board;
  }

  // draws settings for game
  WorldImage drawSettings() {
    Color breadthColor = Color.WHITE;
    Color depthColor = Color.WHITE;
    Color userColor = Color.WHITE;
    Color foundColor = Color.WHITE;
    if (this.searchMode == 1) {
      breadthColor = Color.PINK;
    }
    else if (this.searchMode == 2) {
      depthColor = Color.PINK;
    }
    else {
      userColor = Color.PINK;
    }

    if (this.isSolved) {
      foundColor = Color.GREEN;
    }

    WorldImage background = new RectangleImage(this.width * WIDTH_PX, 135, OutlineMode.SOLID,
        Color.BLACK);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Press 1 for breadth search", 15, FontStyle.REGULAR, breadthColor), -10, -10,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Press 2 for depth search", 15, FontStyle.REGULAR, depthColor), -10, -35,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Arrow keys for user control", 15, FontStyle.REGULAR, userColor), -10, -60,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Press r for a new maze", 15, FontStyle.REGULAR, Color.WHITE), -10, -85,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Maze solved?: " + this.isSolved, 15, FontStyle.REGULAR, foundColor), -10,
        -110, background);

    return background;
  }

  // 'r' - resets the game;
  // EFFECT: reinitializes all the fields of the maze
  // '1' - to use breadth-first search
  // EFFECT: sets this.searchmode to 1, and sets this.isSearching to true
  // '2' - to use depth-first search
  // EFFECT: sets this.searchmode to 2, and sets this.isSearching to true
  // '3' - to use arrowkeys
  // EFFECT: sets this.searchmode to 3, allows user to use arrow keys
  // also sets up cameFromEdges
  public void onKeyEvent(String key) {
    // only accept 'r' for restart
    if (key.equals("r") || key.equals("R")) {
      this.graph = new Graph();
      this.edgesInTree = new ArrayList<Edge>();
      this.representatives = new HashMap<Posn, Posn>();
      this.worklist = new ArrayList<Edge>();
      this.searchMode = 3;
      this.current = 0;
      this.isSearching = false;
      this.isSolved = false;

      this.alreadySeen = new LinkedList<Vertex>();
      this.worklistSeen = new LinkedList<Vertex>();
      this.cameFromEdge = new HashMap<Posn, Edge>();
      this.correctPath = new LinkedList<Vertex>();

      this.createNodes(width, height, rand);
      this.spanningTree();

      this.worklistSeen.add(this.graph.vertices.get(0));
    }
    else if (key.equals("1") && !this.isSolved) {
      this.searchMode = 1;
      this.isSearching = true;
    }
    else if (key.equals("2") && !this.isSolved) {
      this.searchMode = 2;
      this.isSearching = true;
    }
    else if (key.equals("3") && !this.isSolved) {
      this.searchMode = 3;
    }

    if (this.searchMode == 3) {
      Vertex currentVertex = this.graph.vertices.get(current);
      if (key.equals("left") && currentVertex.left) {
        this.current = this.current - 1;
      }
      else if (key.equals("right") && currentVertex.right) {
        this.current = this.current + 1;
      }
      else if (key.equals("up") && currentVertex.top) {
        this.current = this.current - this.width;
      }
      else if (key.equals("down") && currentVertex.bottom) {
        this.current = this.current + this.width;
      }
      Vertex nextVertex = this.graph.vertices.get(current);
      if (!nextVertex.seen) {
        this.cameFromEdge.put(nextVertex.position, nextVertex.getEdgeTo(currentVertex));
      }
      currentVertex.makeSeen();
      nextVertex.makeSeen();
      nextVertex.makeCurrent();
      if (current == this.width * this.height - 1) { // if the maze is solved, it changes
        // this.isSolved to true
        this.isSolved = true;
      }
    }
  }

  // on tick method for the maze; carries out the search method
  // EFFECT: sets up this.cameFromEdges; adds to workListSeen and alreadySeen
  // changes this.isSearching to false and this.isSolved to true when the maze is
  // solved,
  // then reconstructs the right path
  public void onTick() {

    if (this.isSearching && !worklistSeen.isEmpty()) {

      Vertex next = worklistSeen.remove();

      if (next.equals(this.graph.vertices.get(this.width * this.height - 1))) {
        this.isSearching = false;
        this.isSolved = true;
      }

      else if (!alreadySeen.contains(next)) {
        for (Edge e : next.edges) {
          if (this.edgesInTree.contains(e)) {
            Vertex adjacent = next.getConnectedVertex(e);

            if (this.searchMode == 1) { // breadth first search
              worklistSeen.addLast(adjacent);
            }
            else if (this.searchMode == 2) { // depth first search
              worklistSeen.addFirst(adjacent);
            }
            if (!this.alreadySeen.contains(adjacent)) { // set up cameFromEdges
              this.cameFromEdge.put(adjacent.position, e);
            }
          }
        }
        alreadySeen.add(next);
        next.makeSeen();
      }
    }
    else if (this.isSolved && this.correctPath.size() == 0) { // if the maze is solved,
      // reconstruct the path back to the beginning
      this.graph.vertices.get(this.width * this.height - 1).makeSeen();
      this.correctPath.add(this.graph.vertices.get(0));
      this.reconstruct();
      for (Vertex v : this.correctPath) {
        v.solve();
      }
    }
  }
}

//utils for HashMap
class HashMapUtils {

  HashMapUtils() {
  }

  // gets the representative for the given key
  <T> T find(HashMap<T, T> representatives, T key) {
    if (representatives.get(key).equals(key)) {
      return key;
    }
    else {
      return this.find(representatives, representatives.get(key));
    }
  }

  // makes the value for y the value for x
  <T> void union(HashMap<T, T> representatives, T x, T y) {
    representatives.put(x, representatives.get(y));
  }
}

class ExamplesMaze {

  ExamplesMaze() {

  }

  Random rand;

  Maze test;
  Maze drawTest;

  Graph g1;

  Vertex v1;
  Vertex v2;
  Vertex v3;
  Vertex v4;

  Edge e1;
  Edge e2;
  Edge e3;
  Edge e4;
  
  CompareEdges comp;

  HashMapUtils utils;
  HashMap<Posn, Posn> hm1;
  HashMap<Posn, Posn> hm2;

  void initData() {
    this.rand = new Random(15);
    
    this.test = new Maze(15, 10, this.rand); 
    this.drawTest = new Maze(2,2, this.rand);

    this.g1 = new Graph();

    this.v1 = new Vertex(new Posn(0,0));
    this.v2 = new Vertex(new Posn(1,0));
    this.v3 = new Vertex(new Posn(0, 1));
    this.v4 = new Vertex(new Posn(1,1));

    this.e1 = new Edge(this.v1, this.v2, 1);
    this.e2 = new Edge(this.v2, this.v4, 1);
    this.e3 = new Edge(this.v4, this.v3, 1);
    this.e4 = new Edge(this.v3, this.v1, 1);
    
    this.comp = new CompareEdges();

    this.utils = new HashMapUtils();
    this.hm1 = new HashMap<Posn, Posn>();
    this.hm2 = new HashMap<Posn, Posn>();
    this.hm1.put(new Posn(0, 0), new Posn(0, 0));
    this.hm1.put(new Posn(1, 0), new Posn(0, 0));
    this.hm2.put(new Posn(0, 0), new Posn(1, 0));
    this.hm2.put(new Posn(1, 0), new Posn(1, 1));
    this.hm2.put(new Posn(1, 1), new Posn(1, 1));
    this.hm2.put(new Posn(0, 1), new Posn(0, 1));
  }

  void testCompareEdges(Tester t) {
    this.initData();
    Edge e = new Edge(this.v1, this.v4, 4);
    t.checkExpect(new CompareEdges().compare(this.e1, this.e2), 0);
    t.checkExpect(new CompareEdges().compare(e, this.e1), 3);
  }


  //VERTEX
  //addEdge
  void testAddEdge(Tester t) {
    initData();
    this.v1.edges = new ArrayList<Edge>();
    this.v2.edges = new ArrayList<Edge>();
    this.v3.edges = new ArrayList<Edge>();
    t.checkExpect(this.v1.edges.size(), 0);
    t.checkExpect(this.v2.edges.size(), 0);
    t.checkExpect(this.v3.edges.size(), 0);

    this.v1.addEdge(e1);
    this.v2.addEdge(e1);
    this.v3.addEdge(e2);
    t.checkExpect(this.v1.edges.get(0), this.e1);
    t.checkExpect(this.v2.edges.get(0), this.e1);
    t.checkExpect(this.v3.edges.get(0), this.e2);
  }

  //getConnectedVertex
  void testGetConnectedVertex(Tester t) {
    initData();
    t.checkExpect(this.v1.getConnectedVertex(this.e1), this.v2);
    t.checkExpect(this.v2.getConnectedVertex(this.e1), this.v1);
    t.checkExpect(this.v2.getConnectedVertex(this.e2), this.v4);
    t.checkExpect(this.v4.getConnectedVertex(this.e2), this.v2);
    t.checkExpect(this.v4.getConnectedVertex(this.e3), this.v3);
    t.checkExpect(this.v3.getConnectedVertex(this.e3), this.v4);
  }

  //draw
  void testDraw(Tester t) {
    this.initData();
    ArrayList<Edge> testEdgesInTree = new ArrayList<Edge>(Arrays.asList(this.e1));
    this.v1.changeBools(testEdgesInTree);
    this.v2.changeBools(testEdgesInTree);

    WorldImage lineLeft = new LineImage(new Posn(0, 20), Color.BLACK);
    WorldImage lineRight = new LineImage(new Posn(0, 20), Color.BLACK);
    WorldImage lineTop = new LineImage(new Posn(20, 0), Color.BLACK);
    WorldImage lineBottom = new LineImage(new Posn(20, 0), Color.BLACK);

    WorldImage square = new RectangleImage(20, 20, OutlineMode.SOLID, new Color(220, 220, 220));
    
    WorldImage squareV1 = new BesideImage(lineLeft, square);
    squareV1 = new AboveImage(lineTop, squareV1);
    squareV1 = new AboveImage(squareV1, lineBottom);

    WorldImage squareV2 = new BesideImage(square, lineRight);
    squareV2 = new AboveImage(lineTop, squareV2);
    squareV2 = new AboveImage(squareV2, lineBottom);

    WorldImage squareV4 = new BesideImage(lineLeft, square);
    squareV4 = new BesideImage(squareV4, lineRight);
    squareV4 = new AboveImage(lineTop, squareV4);
    squareV4 = new AboveImage(squareV4, lineBottom);

    t.checkExpect(this.v1.draw(testEdgesInTree, new Posn(1,1)), squareV1);
    t.checkExpect(this.v2.draw(testEdgesInTree, new Posn(1,1)), squareV2);
    t.checkExpect(this.v4.draw(new ArrayList<Edge>(), new Posn(1,1)), squareV4);
  }

  void testGetEdgeTo(Tester t) {
    this.initData();
    t.checkExpect(this.v1.getEdgeTo(this.v2), this.e1);
    t.checkExpect(this.v1.getEdgeTo(this.v3), this.e4);
    t.checkExpect(this.v2.getEdgeTo(this.v4), this.e2);
    t.checkExpect(this.v4.getEdgeTo(this.v3), this.e3);
  }

  //changeBools
  void testChangeBools(Tester t) {
    this.initData();
    t.checkExpect(this.v1.left, false);
    t.checkExpect(this.v1.right, false);
    t.checkExpect(this.v1.top, false);
    t.checkExpect(this.v1.bottom, false);

    ArrayList<Edge> testEdgesInTree = new ArrayList<Edge>(Arrays.asList(this.e1, this.e4));
    this.v1.changeBools(testEdgesInTree);

    t.checkExpect(this.v1.left, false);
    t.checkExpect(this.v1.right, true);
    t.checkExpect(this.v1.top, false);
    t.checkExpect(this.v1.bottom, true);
  }

  void testMakeSeen(Tester t) {
    this.initData();

    t.checkExpect(this.v1.seen, false);

    this.v1.makeSeen();

    t.checkExpect(this.v1.seen, true);
  }

  void testMakeCurrent(Tester t) {
    this.initData();

    t.checkExpect(this.v1.current, false);

    this.v1.makeCurrent();

    t.checkExpect(this.v1.current, true);
  }
  
  void testMakeNotCurrent(Tester t) {
    initData();
    this.v1.color = new Color(0, 0, 0);
    this.v1.makeNotCurrent();
    this.v2.makeNotCurrent();
    this.v3.makeNotCurrent();
    t.checkExpect(this.v1.color, new Color(0, 0, 0));
    t.checkExpect(this.v1.current, false);
    t.checkExpect(this.v2.color, new Color(220, 220, 220));
    t.checkExpect(this.v2.current, false);
    t.checkExpect(this.v3.color, new Color(220, 220, 220));
    t.checkExpect(this.v3.current, false);
  }
  
  void testSolve(Tester t) {
    initData();
    this.v1.solve();
    this.v2.solve();
    this.v3.solve();
    t.checkExpect(this.v1.color, new Color(64, 106, 201));
    t.checkExpect(this.v2.color, new Color(64, 106, 201));
    t.checkExpect(this.v2.color, new Color(64, 106, 201));    
  }

  
  //EDGE
  //constructor
  void testEdgeConstructor(Tester t) {
    initData();
    t.checkExpect(this.v1.edges.get(0), this.e1);
    t.checkExpect(this.v1.edges.get(1), this.e4);
    t.checkExpect(this.v2.edges.size(), 2);
    t.checkExpect(this.v3.edges.size(), 2);
    t.checkExpect(this.v4.edges.size(), 2);
  }

  //getOther
  void testGetOther(Tester t) {
    initData();
    t.checkExpect(this.e1.getOther(v1), this.v2);
    t.checkExpect(this.e1.getOther(v2), this.v1);
    t.checkExpect(this.e2.getOther(v4), this.v2);
    t.checkExpect(this.e3.getOther(v3), this.v4);
  }

  
  //COMPARE EDGES
  //compare
  void testCompare(Tester t) {
    initData();
    t.checkExpect(this.comp.compare(this.e1, this.e2), 0);
    this.e1.weight = 5;
    t.checkExpect(this.comp.compare(this.e1, this.e2), 4);
    t.checkExpect(this.comp.compare(this.e2, this.e1), -4);
  }
  
  
  //GRAPH
  //addVertex
  void testAddVertex(Tester t) {
    initData();
    this.g1.addVertex(v1);
    this.g1.addVertex(v2);
    this.g1.addVertex(v3);
    t.checkExpect(this.g1.vertices.size(), 3);
    t.checkExpect(this.g1.vertices.get(0), this.v1);
    t.checkExpect(this.g1.vertices.get(1), this.v2);
    t.checkExpect(this.g1.vertices.get(2), this.v3);
  }


  //MAZE
  //createNodes
  void testCreateNodes(Tester t) {
    initData();
    t.checkExpect(this.test.graph.vertices.size(), this.test.width * this.test.height);
    t.checkExpect(this.test.worklist.size(), 
        (this.test.width - 1) * this.test.height + this.test.width * (this.test.height - 1));
  }

  //spanningTree
  void testSpanningTree(Tester t) {
    initData();
    Posn topLeftRepresentative = utils.find(this.test.representatives, 
        this.test.graph.vertices.get(0).position);
    for (Vertex v : this.test.graph.vertices) {
      t.checkExpect(utils.find(this.test.representatives, v.position), topLeftRepresentative);
    }
  }

  //drawMaze
  void testDrawMaze(Tester t) {
    this.initData();
    WorldImage board = new RectangleImage(40, 40, OutlineMode.SOLID, Color.BLACK);

    board = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
        this.drawTest.graph.vertices.get(0).draw(this.drawTest.edgesInTree,  new Posn(1, 1)), 
        0,
        0, board)
        .movePinhole(-20, - 20);

    board = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
        this.drawTest.graph.vertices.get(1).draw(this.drawTest.edgesInTree,  new Posn(1, 1)), 
        -20,
        0, board)
        .movePinhole(-20, - 20);

    board = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
        this.drawTest.graph.vertices.get(2).draw(this.drawTest.edgesInTree,  new Posn(1, 1)), 
        0,
        -20, board)
        .movePinhole(-20, - 20);

    board = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
        this.drawTest.graph.vertices.get(3).draw(this.drawTest.edgesInTree,  new Posn(1, 1)), 
        -20,
        -20, board)
        .movePinhole(-20, - 20);

    t.checkExpect(this.drawTest.drawMaze(), board);
  }

  void testMakeSettings(Tester t) {
    this.initData();
    WorldImage background = new RectangleImage(40, 135, OutlineMode.SOLID,
        Color.BLACK);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Press 1 for breadth search", 15, FontStyle.REGULAR, Color.WHITE), -10, -10,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Press 2 for depth search", 15, FontStyle.REGULAR, Color.WHITE), -10, -35,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Arrow keys for user control", 15, FontStyle.REGULAR, Color.PINK), -10, -60,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Press r for a new maze", 15, FontStyle.REGULAR, Color.WHITE), -10, -85,
        background);
    background = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP,
        new TextImage("Maze solved?: false", 15, FontStyle.REGULAR, Color.WHITE), -10,
        -110, background);

    t.checkExpect(this.drawTest.drawSettings(), background);
  }

  //makeScene
  void testMakeScene(Tester t) {
    initData();

    WorldScene background = this.drawTest.getEmptyScene();
    WorldImage boardImage = this.drawTest.drawMaze();
    WorldImage settings = this.drawTest.drawSettings();
    background.placeImageXY(boardImage, 0, 0);
    background.placeImageXY(settings, 0, 40);

    t.checkExpect(this.drawTest.makeScene(), background);
  }

  void testOnKey(Tester t) {
    this.initData();
    
    this.drawTest.onKeyEvent("r");
    t.checkExpect(this.drawTest.isSearching, false);
    t.checkExpect(this.drawTest.searchMode, 3);
    t.checkExpect(this.drawTest.worklist.size(), 4);
    t.checkExpect(this.drawTest.graph.vertices.size(), 4);

    this.drawTest.onKeyEvent("1");
    t.checkExpect(this.drawTest.isSearching, true);
    t.checkExpect(this.drawTest.searchMode, 1);
    
    this.drawTest.onKeyEvent("right");
    t.checkExpect(this.drawTest.isSearching, true);
    t.checkExpect(this.drawTest.searchMode, 1);

    this.drawTest.onKeyEvent("2");
    t.checkExpect(this.drawTest.isSearching, true);
    t.checkExpect(this.drawTest.searchMode, 2);

    this.drawTest.onKeyEvent("3");
    t.checkExpect(this.drawTest.current, 0);
    this.drawTest.onKeyEvent("right");
    t.checkExpect(this.drawTest.current, 0);
  }
  
  void testReconstruct(Tester t) {
    initData();
    this.drawTest.onKeyEvent("2");
    while (!this.drawTest.isSolved) {
      this.drawTest.onTick();
    }
    this.drawTest.reconstruct();
    t.checkExpect(this.drawTest.correctPath.size(), 3);
    t.checkExpect(this.drawTest.correctPath.get(0), this.drawTest.graph.vertices.get(0));
    t.checkExpect(this.drawTest.correctPath.get(1), this.drawTest.graph.vertices.get(1));
    t.checkExpect(this.drawTest.correctPath.get(2), this.drawTest.graph.vertices.get(3));
    initData();
    this.drawTest.onKeyEvent("1");
    while (!this.drawTest.isSolved) {
      this.drawTest.onTick();
    }
    this.drawTest.reconstruct();
    t.checkExpect(this.drawTest.correctPath.size(), 3);
    t.checkExpect(this.drawTest.graph.vertices.get(2).seen, true);
    t.checkExpect(this.drawTest.correctPath.get(0), this.drawTest.graph.vertices.get(0));
    t.checkExpect(this.drawTest.correctPath.get(1), this.drawTest.graph.vertices.get(1));
    t.checkExpect(this.drawTest.correctPath.get(2), this.drawTest.graph.vertices.get(3));
  }
  
  void testOnTick(Tester t) {
    initData();
    this.drawTest.onKeyEvent("1");
    for (int i = 0; i < 5; i++) {
      this.drawTest.onTick();
    }
    t.checkExpect(this.drawTest.isSolved, false);
    this.drawTest.onTick();
    t.checkExpect(this.drawTest.isSolved, true);
    initData();
    this.drawTest.onKeyEvent("2");
    this.drawTest.onTick();
    this.drawTest.onTick();
    t.checkExpect(this.drawTest.isSolved, false);
    this.drawTest.onTick();
    t.checkExpect(this.drawTest.isSolved, true);
  }
  
  
  //HMUtils
  //find
  void testFind(Tester t) {
    initData();
    t.checkExpect(utils.find(this.hm1, new Posn(0, 0)), new Posn(0, 0));
    t.checkExpect(utils.find(this.hm1, new Posn(1, 0)), new Posn(0, 0));
    t.checkExpect(utils.find(this.hm2, new Posn(0, 0)), new Posn(1, 1));
    t.checkExpect(utils.find(this.hm2, new Posn(1, 0)), new Posn(1, 1));
    t.checkExpect(utils.find(this.hm2, new Posn(1, 1)), new Posn(1, 1));
  }

  //union
  void testUnion(Tester t) {
    initData();
    utils.union(this.hm2, new Posn(0, 0), new Posn(0, 1));
    t.checkExpect(this.hm2.get(new Posn(0, 0)), new Posn(0, 1));
    initData();
    utils.union(this.hm2, new Posn(1, 1), new Posn(0, 1));
    t.checkExpect(this.hm2.get(new Posn(1, 1)), new Posn(0, 1));
    t.checkExpect(utils.find(this.hm2, new Posn(0, 0)), new Posn(0, 1));
    t.checkExpect(utils.find(this.hm2, new Posn(1, 0)), new Posn(0, 1));
  }

  
  //BIGBANG
  void testBigBang(Tester t) {
    this.initData();
    this.test.bigBang(this.test.width * 20, this.test.height * 20 + 135, .001);
  }
}


