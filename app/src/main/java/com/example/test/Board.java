package com.example.test;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;


public class Board extends LinearLayout {

    int player;
    final Size BOARD_SIZE = new Size(8, 12);
    Tile[][] boardMatrix = new Tile[BOARD_SIZE.getHeight()][BOARD_SIZE.getWidth()];
    Tile selectedTile = null;

    public Board(@NonNull Context context, AttributeSet attributeSet) {
        this(context);
    }

    public Board(Context context) {
        super(context);

        player = 1;

        setOrientation(VERTICAL);
        setLayoutParams(
                new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                )
        );

        for (int i = 0; i < BOARD_SIZE.getHeight(); i++) {
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(HORIZONTAL);
            row.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    )
            );
            for (int j = 0; j < BOARD_SIZE.getWidth(); j++) {
                boardMatrix[i][j] = new Tile(context, new Point(i, j));
                row.addView(boardMatrix[i][j]);

                int finalI = i;
                int finalJ = j;
                boardMatrix[i][j].setOnClickListener((View view) -> {
                    if (selectedTile != null) {
                        if (boardMatrix[finalI][finalJ].hasSoldier() &&
                                boardMatrix[finalI][finalJ].getSoldier().getTeamNumber() == selectedTile.getSoldier().getTeamNumber()) {
                            selectedTile.deselect();
                            selectedTile = boardMatrix[finalI][finalJ];
                            boardMatrix[finalI][finalJ].select();
                        }
                        if (Math.abs(boardMatrix[finalI][finalJ].getPosition().x - selectedTile.getPosition().x)
                                + Math.abs(boardMatrix[finalI][finalJ].getPosition().y - selectedTile.getPosition().y) == 1) {
                            if (boardMatrix[finalI][finalJ].hasSoldier()) {
                                if (boardMatrix[finalI][finalJ].getSoldier().getTeamNumber() != selectedTile.getSoldier().getTeamNumber()) {
                                    selectedTile.deselect();
                                    int result = Soldier.fight(selectedTile.getSoldier(), boardMatrix[finalI][finalJ].getSoldier());
                                    if (result == 0) {
                                        selectedTile.removeSoldier();
                                        boardMatrix[finalI][finalJ].removeSoldier();
                                        player = player == 1 ? 2 : 1;
                                    } else if (result == 1) {
                                        moveSoldier(selectedTile, boardMatrix[finalI][finalJ]);
                                    } else if (result == 2) {
                                        selectedTile.removeSoldier();
                                        player = player == 1 ? 2 : 1;
                                    }
                                    selectedTile = null;
                                }
                            } else {
                                selectedTile.deselect();
                                moveSoldier(selectedTile, boardMatrix[finalI][finalJ]);
                                selectedTile = null;
                                player = player == 1 ? 2 : 1;
                            }
                        }
                    } else {
                        if (boardMatrix[finalI][finalJ].hasSoldier() && boardMatrix[finalI][finalJ].getSoldier().getTeamNumber() == player) {
                            selectedTile = boardMatrix[finalI][finalJ];
                            boardMatrix[finalI][finalJ].select();
                        }
                    }
                });
            }
            addView(row);

        }
        ArrayList<Soldier> soldiers = readSoldiersFromFile(new File(context.getExternalFilesDir(null).toString(), "soldiers_positions2"));
        locateSoldiersOnBoard(soldiers);
    }

    ArrayList<Soldier> readSoldiersFromFile(File file) {
        ArrayList<Soldier> soldiers = new ArrayList<>();

//        soldiers.add(new Soldier(1, 'e', new Point(2, 0)));
//        soldiers.add(new Soldier(1, 'e', new Point(3, 0)));
//        soldiers.add(new Soldier(1, 'f', new Point(4, 0)));
//        soldiers.add(new Soldier(1, 'f', new Point(5, 0)));
//        soldiers.add(new Soldier(1, 'e', new Point(1, 1)));
//        soldiers.add(new Soldier(1, 'a', new Point(2, 1)));
//        soldiers.add(new Soldier(1, 'a', new Point(5, 1)));
//        soldiers.add(new Soldier(1, 'f', new Point(6, 1)));
//        soldiers.add(new Soldier(1, 'e', new Point(3, 2)));
//        soldiers.add(new Soldier(1, 'f', new Point(4, 2)));
//        soldiers.add(new Soldier(1, 'w', new Point(0, 4)));
//        soldiers.add(new Soldier(1, 'w', new Point(4, 4)));
//
//        soldiers.add(new Soldier(0, 'w', new Point(1, 6)));
//        soldiers.add(new Soldier(0, 'w', new Point(4, 7)));
//        soldiers.add(new Soldier(0, 'f', new Point(1, 9)));
//        soldiers.add(new Soldier(0, 'e', new Point(3, 9)));
//        soldiers.add(new Soldier(0, 'f', new Point(5, 9)));
//        soldiers.add(new Soldier(0, 'a', new Point(7, 9)));
//        soldiers.add(new Soldier(0, 'e', new Point(2, 10)));
//        soldiers.add(new Soldier(0, 'f', new Point(4, 10)));
//        soldiers.add(new Soldier(0, 'e', new Point(6, 10)));
//        soldiers.add(new Soldier(0, 'a', new Point(1, 11)));
//        soldiers.add(new Soldier(0, 'f', new Point(3, 11)));
//        soldiers.add(new Soldier(0, 'e', new Point(5, 11)));

        byte[] bytes = Tools.readFromFile(file);
        if (bytes == null) return new ArrayList<>();
        int teamNumber = bytes[0];
        int player1Count = bytes[1];
        for (int i = 0; i < player1Count; i++) {
            int x = bytes[4 + i * 3];
            int y = bytes[3 + i * 3];
            char type = (char) bytes[2 + i * 3];
            soldiers.add(
                    new Soldier(
                            teamNumber,
                            type,
                            new Point(x, y)
                    )
            );
        }

        teamNumber = bytes[2 + player1Count * 3];
        int player2Count = bytes[3 + player1Count * 3];
        for (int i = 0; i < player2Count; i++) {
            int x = bytes[6 + player1Count * 3 + i * 3];
            int y = bytes[5 + player1Count * 3 + i * 3];
            char type = (char) bytes[4 + player1Count * 3 + i * 3];
            soldiers.add(
                    new Soldier(
                            teamNumber,
                            type,
                            new Point(x, y)
                    )
            );
        }
        return soldiers;
    }


    void locateSoldiersOnBoard(ArrayList<Soldier> soldiers) {
        for (Soldier soldier : soldiers) {
            int x = soldier.getPosition().x;
            int y = soldier.getPosition().y;
            boardMatrix[y][x].setSoldier(soldier);
        }
    }

    void moveSoldier(Tile fromSelectedTile, Tile toNewTile) {
        Soldier soldier = fromSelectedTile.getSoldier();
        fromSelectedTile.removeSoldier();
        toNewTile.setSoldier(soldier);
    }


}
