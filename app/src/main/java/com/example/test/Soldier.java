package com.example.test;

import android.graphics.Point;

public class Soldier {

    int teamNumber;
    char type;
    Point position;

    public Soldier(int teamNumber, char type, Point position) {
        this.teamNumber = teamNumber;
        this.type = type;
        this.position = position;
    }

    public static int fight(Soldier soldier1, Soldier soldier2) {
        if (soldier1.getType() == soldier2.getType()) return 0;
        switch (soldier1.getType()) {
            case 'W':
                if (soldier2.getType() == 'A' || soldier2.getType() == 'F') return 1;
                return 2;
            case 'A':
                if (soldier2.getType() == 'F' || soldier2.getType() == 'E') return 1;
                return 2;
            case 'F':
                if (soldier2.getType() == 'E') return 1;
                return 2;
            case 'E':
                if (soldier2.getType() == 'W') return 1;
                return 2;
        }
        return 0;
    }

    public void moveTo(Point newPosition) {
        position = newPosition;
    }

    public char getType() {
        return type;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public Point getPosition() {
        return position;
    }
}
