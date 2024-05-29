// Bot player of the game

public class Bot {

    private int column; // column of the table layout
    private long botTime; // the particular time given to bot to make its move
    private TreeNode root; // root node of the tree can be regarded as start state

    /**
     * Constructor of Bot class
     * @param layout table layout of the game. It changes after every turn.
     * @param botTime the particular time given to bot to make its move
     */
    public Bot(Layout layout, long botTime) {
        this.column = layout.column;
        this.botTime = botTime;
        root = new TreeNode(null, layout.getCurrentLayout());
    }

    /**
     * gets number of the columns of the table layout
     * @return number of the columns of the table layout
     */
    public int getColumn() {
        return column;
    }

    /**
     * gets the root node of the tree
     * @return the root node of the tree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * simulates the game and returns a result based on the winner of the game
     * @param childOfBestNode the resulting node coming from childrenOfBestNode method
     * @return the result of simulation. The result is 1 if player wins, the result is 0 if bot wins and the result is 2 if the game ends in a tie.
     */
    public int simulateTheGame(TreeNode childOfBestNode) {
        Layout layout = childOfBestNode.layout.getCurrentLayout();
        while (layout.GameState() == 1) { // while the game in progress
            layout.place((int) (Math.random() * column));
        }
        if (layout.GameState() == 3) { // bot wins
            return 0;
        } else if (layout.GameState() == 2) { // player wins
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * tracks the winner of the simulated game through all the ancestors of the best node.
     * @param childOfBestNode the resulting node coming from childrenOfBestNode method
     * @param result result of the simulated game
     */
    public void backTracking(TreeNode childOfBestNode, int result) {
        while (childOfBestNode != null) {
            childOfBestNode.incrementNumberOfSimulations();
            childOfBestNode.incrementWins(result);
            childOfBestNode = childOfBestNode.getParent();
        }
    }

    /**
     * adds all potential actions as children of the best node after the best node has been chosen.
     * @param bestNode the best node which is selected at selectBestNode method
     * @return potential children of the best node
     */
    public TreeNode childrenOfBestNode(TreeNode bestNode) {
        LinkedList l = new LinkedList();
        for (int index = 0; index < column; index++) {
            if (bestNode.layout.isPlaceable(index) && bestNode.getChildren()[index] == null ) {
                l.insertLast(new Node(index));
            }
        }
        int selectedIndex = l.getNodeI((int) (Math.random() * l.numberOfElements())).getData();
        bestNode.getChildren()[selectedIndex] = new TreeNode(bestNode, bestNode.layout.getNextState(selectedIndex));
        return bestNode.getChildren()[selectedIndex];
    }

    /**
     * adapts root node to new table state after a move
     * @param location the location where the move will happen
     */
    public void updateRoot(int location) {
        if (root.getChildren()[location] == null) {
            root = new TreeNode(null, root.getLayout().getNextState(location));
        } else {
            root = root.getChildren()[location];
        }
    }

    /**
     * a recursive method that selects the best node for bot player via using Upper Confidence Bound formula/algorithm
     * @param parent parent node of selected child node
     * @return selected child node after the best move is made
     */
    public TreeNode selectBestNode(TreeNode parent) {
        for (int index = 0; index < 7; index++) {
            if (parent.getLayout().isPlaceable(index) && parent.getChildren()[index] == null) {
                return parent;
            }
        }
        int max = -1;
        double maxUpperConfidenceBound = -1;
        for (int index = 0; index < 7; index++) {
            if (!parent.getLayout().isPlaceable(index)) {
                continue; // skip the current iteration of the loop if the value of the index is -1 and continue with the next iteration.
            }
            TreeNode currentChild = parent.getChildren()[index];
            double wins;
            if (parent.getLayout().getTurn() == Layout.isPlayerTurn()) {
                wins = currentChild.getWins();
            } else {
                wins = currentChild.getNumberOfSimulation() - currentChild.getWins();
            }
            double upperConfidenceBound = (wins / currentChild.getNumberOfSimulation()) + (Math.sqrt(2) * Math.sqrt(Math.log(parent.getNumberOfSimulation()) / currentChild.getNumberOfSimulation()));
            if (upperConfidenceBound > maxUpperConfidenceBound) {
                maxUpperConfidenceBound = upperConfidenceBound;
                max = index;
            }
        }
        return selectBestNode(parent.getChildren()[max]);
    }

    /**
     * implements the best move and finds the best index for bot player
     * @return returns the best index for bot player to make its move
     */
    public int BestIndex() {
        for (long l = System.nanoTime() + botTime; l > System.nanoTime();) {
            TreeNode bestNode = selectBestNode(root);
            TreeNode childOfBestNode = childrenOfBestNode(bestNode);
            int simulationResult = simulateTheGame(childOfBestNode);
            backTracking(childOfBestNode, simulationResult);
        }
        int best = -1;
        for (int index = 0; index < column; index++) {
            if (root.getChildren()[index] != null) {
                if (best == -1 || root.getChildren()[best].getNumberOfSimulation() < root.getChildren()[index].getNumberOfSimulation()) {
                    best = index;
                }
            }
        }
        return best;
    }
}