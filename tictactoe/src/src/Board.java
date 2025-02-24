import enums.SymbolType;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Board {
    private int size;
    private Player[][] board;
    private Deque<Player> players;

    public Board(int size) {
        this.size = size;
        this.board = new Player[size][size];
        this.players = new LinkedList<>();

        // Initialize players
        Player playerOne = new Player("Sam", new PlayingSymbol(SymbolType.X));
        Player playerTwo = new Player("Alex", new PlayingSymbol(SymbolType.O));

        players.add(playerOne);
        players.add(playerTwo);
    }

    private Player getNextPlayer() {
        Player current = players.poll();
        players.addLast(current); // Rotate turns
        return current;
    }

    private boolean checkEmptySpace() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    return false; // Empty space found
                }
            }
        }
        return true; // Board is full
    }

    private boolean checkValidPos(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size && board[i][j] == null;
    }

    private boolean checkWinner() {
        for (int i = 0; i < size; i++) {
            if (checkRow(i) || checkColumn(i)) return true;
        }
        return checkDiagonals();
    }

    private boolean checkRow(int row) {
        Player first = board[row][0];
        if (first == null) return false;
        for (int j = 1; j < size; j++) {
            if (board[row][j] == null || !board[row][j].equals(first)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int col) {
        Player first = board[0][col];
        if (first == null) return false;
        for (int i = 1; i < size; i++) {
            if (board[i][col] == null || !board[i][col].equals(first)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonals() {
        Player firstMain = board[0][0];
        boolean mainDiagonal = firstMain != null;
        Player firstAnti = board[0][size - 1];
        boolean antiDiagonal = firstAnti != null;

        for (int i = 1; i < size; i++) {
            if (mainDiagonal && (board[i][i] == null || !board[i][i].equals(firstMain))) {
                mainDiagonal = false;
            }
            if (antiDiagonal && (board[i][size - 1 - i] == null || !board[i][size - 1 - i].equals(firstAnti))) {
                antiDiagonal = false;
            }
        }
        return mainDiagonal || antiDiagonal;
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        while (!checkEmptySpace()) {
            Player currentPlayer = getNextPlayer();
            System.out.println(currentPlayer.name + "'s turn, please enter row,column");
            int i = sc.nextInt();
            int j = sc.nextInt();
            if (!checkValidPos(i, j)) {
                players.addFirst(currentPlayer);
                System.out.println("Selected position is invalid/occupied. Try Again!");
                continue;
            }
            board[i][j] = currentPlayer;
            if (checkWinner()) {
                System.out.println("Winner is " + currentPlayer.name);
                return;
            }
        }
        System.out.println("TIE");
    }
}
