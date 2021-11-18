package com.example.test;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

public class Tile extends AppCompatImageView {

    Soldier soldier = null;
    Point position;

    public Tile(@NonNull Context context) {
        this(context, new Point(0, 0));
    }

    public Tile(@NonNull Context context, AttributeSet attributeSet) {
        this(context, new Point(0, 0));
    }

    public Tile(Context context, Point position) {
        super(context);

        this.position = position;

        setBackgroundResource(R.drawable.tile);
        setLayoutParams(
                new ViewGroup.LayoutParams(
                        Tools.dpToPx(context, 40),
                        Tools.dpToPx(context, 40)
                )
        );
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
        switch (this.soldier.getType()) {
            case 'A':
                setImageResource(R.drawable.air_circle);
                break;
            case 'F':
                setImageResource(R.drawable.fire_circle);
                break;
            case 'W':
                setImageResource(R.drawable.water_cirlce);
                break;
            case 'E':
                setImageResource(R.drawable.earth_circle);
                break;
        }
    }

    public void removeSoldier() {
        soldier = null;
        setImageResource(0);
    }

    public boolean hasSoldier() {
        return soldier != null;
    }

    public void select() {
        setBackgroundResource(R.drawable.tile_selected);
    }

    public void deselect() {
        setBackgroundResource(R.drawable.tile);
    }

    public Point getPosition() {
        return position;
    }

    public Soldier getSoldier() {
        return soldier;
    }
}
