// table itself and table's functionalities of the Connect4 game

public class Layout {

    public static boolean playerTurn; // player's turn
    private int[][] state; // current state of the table layout
    public int row; // row of the table layout
    public int column; // column of the table layout
    private boolean turn; // decides whose turn it is (bot's turn or player's turn)

    /**
     * Constructor of the layout class
     * @param column column of the table layout
     * @param row row of the table layout
     */
    public Layout(int column, int row) {
        this.row = 6;
        this.column = 7;
        this.turn = playerTurn;
        state = new int[row][column];
    }

    /**
     * fills empty slots of the table
     * @param slotsState state of the table's slots
     */
    public void fillSlots(int[][] slotsState) {
        for (int width = 0; width < row; width++) {
            for (int height = 0; height < column; height++) {
                state[width][height] = slotsState[width][height];
            }
        }
    }

    /**
     * transforms the table layout to string
     * @return string version of the table layout
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("|");
        string = new StringBuilder(string.substring(0, string.length() - 1) + "\n");
        for (int rowCounter = 0; rowCounter < row; rowCounter++) {
            string.append("| ");
            for (int columnCounter = 0; columnCounter < column; columnCounter++) {
                if (state[rowCounter][columnCounter] == 0) {
                    string.append(" ").append(" | ");
                } else if (state[rowCounter][columnCounter] == 1) {
                    string.append("R").append(" | ");
                } else {
                    string.append("Y").append(" | ");
                }
            }
            string = new StringBuilder(string.substring(0, string.length()));
            string = new StringBuilder(string.substring(0, string.length() - 1));
            string.append("\n");
        }
        string.append("  0   1   2   3   4   5   6  ");
        return string.substring(0, string.length() - 1);
    }

    /**
     * gets next turn of the player
     * @return next turn of the player
     */
    public static boolean isPlayerTurn() {
        return playerTurn;
    }

    /**
     * gets next turn of the player or bot
     * @return next turn of the player or bot
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * returns current layout of the table
     * @return current layout of the table
     */
    public Layout getCurrentLayout() {
        return new Layout(state, turn);
    }

    /**
     * gets next state of the table layout
     * @param column column of the table layout
     * @return the table layout with the next state
     */
    public Layout getNextState(int column) {
        Layout layout = getCurrentLayout();
        layout.place(column);
        return layout;
    }

    /**
     * decides the state of the game.
     * @return returns 1 if the game is in progress, 2 if the game is won by the player, 3 if the game is won by bot, 0 if the game ends in draw.
     */
    public int GameState() {
        if (isWinner(2)) {
            return 3; // bot wins
        } else if (tableIsFull()) {
            return 0; // ends in a tie
        } else if (isWinner(1)) {
            return 2; // player wins
        } else {
            return 1; // game is not over yet
        }
    }

    /**
     * checks whether the table layout is full
     * @return true if the table layout is full; false otherwise
     */
    public boolean tableIsFull() {
        for (int[] ints : state) {
            for (int anInt : ints) {
                if (anInt == 0) { // if the state of the table slot is empty
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * checks whether a coin is placeable to the table or not
     * @param column column of the table layout
     * @return true if the coin is placeable; false otherwise.
     */
    public boolean isPlaceable(int column) {
        return column < 7 && column >= 0 && state[0][column] == 0;
    }

    /**
     * enables player and bot to place a coin
     * @param slot slots in rows of the table
     */
    public void place(int slot) {
        int coin;
        if (turn != playerTurn) {
            coin = 2; // bot's turn
        } else {
            coin = 1; // player's turn
        }
        if (!isPlaceable(slot)) {
            return; // do nothing
        }
        int heightOfCoin = row - 1;
        while (state[heightOfCoin][slot] != 0) { // if the state of the slot is not empty
            heightOfCoin--;
        }
        state[heightOfCoin][slot] = coin;
        turn = !turn; // it is there because the player or bot cannot play consecutive turns.
    }

    /**
     * checks the winner of the game at the end of every turn.
     * Also, checks the all winning conditions which are diagonal downward case, diagonal upward case, horizontal case and vertical case
     * @param coin it can have the value of 1 or 2. 1 is player's coin and 2 is bot's coin
     * @return true if there is any winner; false otherwise
     */
    public boolean isWinner(int coin) {
        int column = state[0].length;
        int row = state.length;
        for (int width = 0; width < row - 3; width++) {
            for (int height = 0; height < column - 3; height++) {
                for (int count = 0; count < 4 && state[width + count][height + count] == coin; count++) {
                    if (count == 3) {
                        return true;
                    }
                }
            }
        }
        for (int width = 0; width < row - 3; width++) {
            for (int height = 3; height < column; height++) {
                for (int count = 0; count < 4 && state[width + count][height - count] == coin; count++) {
                    if (count == 3) {
                        return true;
                    }
                }
            }
        }
        for (int[] ints : state) {
            for (int height = 0; height < column - 3; height++) {
                for (int count = height; count < height + 4 && ints[count] == coin; count++) {
                    if (count == height + 3) {
                        return true;
                    }
                }
            }
        }
        for (int width = 0; width < row - 3; width++) {
            for (int height = 0; height < column; height++) {
                for (int count = width; count < width + 4 && state[count][height] == coin; count++) {
                    if (count == width + 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * second Constructor of the layout class
     * @param slotsState state of the table's slots
     * @param nextTurn nextTurn of player or bot
     */
    public Layout(int[][] slotsState, boolean nextTurn) {
        this(slotsState[0].length, slotsState.length);
        fillSlots(slotsState);
        this.turn = nextTurn;
    }
}
