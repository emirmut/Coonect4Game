// root or child node of tree

public class TreeNode {
    public int numberOfSimulation; // number of times the game is simulated. This variable is here to calculate Upper Confidence Bound formula/algorithm.
    public int wins; // number of games that player or bot has won. This variable is here to calculate Upper Confidence Bound formula/algorithm.
    public Layout layout; // table layout of the game. It changes after every turn.
    public int column = 7; // a fixed number of columns of table layout
    public TreeNode parent; // parent node of a child node
    public TreeNode[] children; // the next game state, in which the player or bot puts the coin at the ith index, is represented as an array with node as its members.

    /**
     * Constructor of Node class
     * @param parent parent of the current node
     * @param layout table layout of the game. It changes after every turn.
     */
    public TreeNode(TreeNode parent, Layout layout) {
        this.numberOfSimulation = 0;
        this.wins = 0;
        this.parent = parent;
        this.layout = layout;
        children = new TreeNode[column];
    }

    /**
     * gets children of a node
     * @return children of a node
     */
    public TreeNode[] getChildren() {
        return children;
    }

    /**
     * gets how many times the game is simulated
     * @return the time the game simulated
     */
    public int getNumberOfSimulation() {
        return numberOfSimulation;
    }

    /**
     * gets how many wins the player or bot has gotten
     * @return number of wins the player or bot has gotten
     */
    public int getWins() {
        return wins;
    }

    /**
     * gets parent of any node except root node
     * @return parent node of any node except root node
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * gets the table layout of the game
     * @return table layout of the game
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * increments the number of wins of player or bot
     * we have this method to implement backtracking
     * @param count increment amount of wins
     */
    public void incrementWins(int count) {
        wins = wins + count;
    }

    /**
     * increments the number of simulations of the game
     * we have this method to implement backtracking
     */
    public void incrementNumberOfSimulations() {
        numberOfSimulation++;
    }
}
