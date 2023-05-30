package com.kuba.tictactoe.model;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Scanner;

@Component
public class TicTacToe {
    private static final Scanner scanner = new Scanner(System.in);

    public void start() {
        programLoop:
        while (true) {
            switch (printInitialMenu()) {
                case "1":
                    switch (fullGamePlay(true)) {
                        case "2":
                            break;
                        case "3":
                            break programLoop;
                    }
                    break;
                case "2":
                    switch (fullGamePlay(false)) {
                        case "2":
                            break;
                        case "3":
                            break programLoop;
                    }
                    break;
                case "3":
                    break programLoop;
                default:
                    break;
            }
        }
    }


    private String fullGamePlay(boolean isPlayedWithComputer) {
        String player1Name = "", player2Name = "Computer";
        char player1Symbol, player2Symbol;
        int player1WinCount = 0, player2WinCount = 0;

        if (isPlayedWithComputer) {
            System.out.println("Enter Player name: ");
            player1Name = scanner.nextLine();
        } else {
            System.out.println("Enter Player 1 name: ");
            player1Name = scanner.nextLine();
            System.out.println("Enter Player 2 name: ");
            player2Name = scanner.nextLine();
        }

        player1Symbol = choosePlayer1Symbol(player1Name);
        player2Symbol = choosePlayer2Symbol(player1Symbol);

        while (true) {
            playGame(player1Name, player2Name,
                    player1Symbol, player2Symbol,
                    player1WinCount, player2WinCount,
                    isPlayedWithComputer);

            switchLoop:
            while (true) {
                switch (printInternalMenu()) {
                    case "1":
                        break switchLoop;
                    case "2":
                        return "2";
                    case "3":
                        return "3";
                    default:
                        break;
                }
            }
        }
    }

    private String printInitialMenu() {
        System.out.println("""
                Let's play TIC-TAC-TOE!
                                
                1. 1 player
                2. 2 players
                3. Exit""");
        return scanner.nextLine();
    }

    private String printInternalMenu() {
        System.out.println(""" 
                1. Play again
                2. Back to Main Menu
                3. Exit""");
        return scanner.nextLine();
    }

    private Character choosePlayer1Symbol(String player1Name) {
        while (true) {
            System.out.println(player1Name + ", choose your symbol [X/O]");
            switch (scanner.nextLine().toUpperCase()) {
                case "X":
                    return 'X';
                case "O":
                    return 'O';
                default:
                    break;
            }
        }
    }

    private Character choosePlayer2Symbol(char player1Symbol) {
        if (player1Symbol == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }

    private void playGame(String player1Name, String player2Name,
                          char player1Symbol, char player2Symbol,
                          int player1WinCount, int player2WinCount,
                          boolean againstComputer) {
        char[][] startBoard = {{'1', '2', '3'}, {'4', '5', '6',}, {'7', '8', '9'}};
        char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' ',}, {' ', ' ', ' '}};
        printBoard(startBoard);

        while (true) {
            playerTurn(board, player1Name, player1Symbol);
            printBoard(board);
            if (isGameOver(board, player1Name, player2Name,
                    player1Symbol, player2Symbol,
                    player1WinCount, player2WinCount)) {
                break;
            }

            if (againstComputer) {
                computerTurn(board, player2Symbol);
            } else {
                playerTurn(board, player2Name, player2Symbol);
            }
            printBoard(board);
            if (isGameOver(board, player1Name, player2Name,
                    player1Symbol, player2Symbol,
                    player1WinCount, player2WinCount)) {
                break;
            }
        }
    }


    private boolean isGameOver(char[][] board,
                               String player1Name, String player2Name,
                               char player1Symbol, char player2Symbol,
                               int player1WinCount, int player2WinCount) {
        if (hasPlayerWon(board, player1Symbol)) {
            player1WinCount++;
            System.out.println("-".repeat(10));
            System.out.println("Game Over - " + player1Name + " wins!");
            System.out.println(player1Name + " " + player1WinCount + ":" + player2WinCount + " " + player2Name);
            System.out.println("-".repeat(10));
            return true;
        }

        if (hasPlayerWon(board, player2Symbol)) {
            player2WinCount++;
            System.out.println("-".repeat(10));
            System.out.println("Game Over - " + player2Name + " wins!");
            System.out.println(player1Name + " " + player1WinCount + ":" + player2WinCount + " " + player2Name);
            System.out.println("-".repeat(10));
            return true;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        System.out.println("Game Over - Tie");
        System.out.println(player1Name + " " + player1WinCount + ":" + player2WinCount + " " + player2Name);
        return true;
    }

    private boolean hasPlayerWon(char[][] board, char symbol) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ((board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) ||
                        (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) ||
                        (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) ||

                        (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) ||
                        (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) ||
                        (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) ||

                        (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                        (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void computerTurn(char[][] board, char computerSymbol) {
        Random random = new Random();
        String position;

        while (true) {
            position = Integer.toString(random.nextInt(1, 10));
            if (isValidMove(board, position)) {
                System.out.println("Computer move:");
                placeMove(board, position, computerSymbol);
                break;
            }
        }
    }

    private void playerTurn(char[][] board, String playerName, char playerSymbol) {
        String position;

        while (true) {
            System.out.println(playerName + ", place your move [1-9]:");
            position = scanner.nextLine();
            if (isValidMove(board, position)) {
                placeMove(board, position, playerSymbol);
                break;
            } else {
                System.out.println("Wrong move");
                printBoard(board);
            }
        }
    }

    private boolean isValidMove(char[][] board, String position) {
        switch (position) {
            case "1":
                return board[0][0] == ' ';
            case "2":
                return board[0][1] == ' ';
            case "3":
                return board[0][2] == ' ';
            case "4":
                return board[1][0] == ' ';
            case "5":
                return board[1][1] == ' ';
            case "6":
                return board[1][2] == ' ';
            case "7":
                return board[2][0] == ' ';
            case "8":
                return board[2][1] == ' ';
            case "9":
                return board[2][2] == ' ';
            default:
                return false;
        }
    }

    private void placeMove(char[][] board, String position, char symbol) {
        switch (position) {
            case "1":
                board[0][0] = symbol;
                break;
            case "2":
                board[0][1] = symbol;
                break;
            case "3":
                board[0][2] = symbol;
                break;
            case "4":
                board[1][0] = symbol;
                break;
            case "5":
                board[1][1] = symbol;
                break;
            case "6":
                board[1][2] = symbol;
                break;
            case "7":
                board[2][0] = symbol;
                break;
            case "8":
                board[2][1] = symbol;
                break;
            case "9":
                board[2][2] = symbol;
                break;
            default:
                break;
        }
    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
                if (j < board.length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < board.length - 1) {
                System.out.println("-".repeat(5));
            }
        }
    }
}