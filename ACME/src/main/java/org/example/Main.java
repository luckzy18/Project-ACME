package org.example;

import org.example.Database.CreateDB;
import org.example.UI.MainUI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        IO.println("Checking Database connection and integrity...");
        CreateDB.initialise();
        IO.println("Database connected.");
        MainUI cli=new MainUI();
        cli.start();

    }
}
