package com.kuba.tictactoe;

import com.kuba.tictactoe.configuration.AppConfiguration;
import com.kuba.tictactoe.model.TicTacToe;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        TicTacToe ticTacToe = context.getBean(TicTacToe.class);

        ticTacToe.start();
    }
}
