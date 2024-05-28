
package com.example.mobile_pixelart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView mv = new MyView(this);
        setContentView(mv);
    }
}

class MyView extends View {

    ArrayList<Integer> saveX = new ArrayList<>();
    ArrayList<Integer> saveY = new ArrayList<>();

    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(3);


        paint.setColor(Color.CYAN);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                canvas.drawRect(50 + 60 * i, 50 + 60 * j, 110 + 60 * i, 110 + 60 * j, paint);
            }
        }

        paint.setColor(Color.BLACK);
        for (int i = 0; i <= 10; i++) {
            canvas.drawLine(50 + 60 * i, 50, 50 + 60 * i, 650, paint);
            canvas.drawLine(50, 50 + 60 * i, 650, 50 + 60 * i, paint);
        }

        paint.setColor(Color.MAGENTA);
        for (int i = 0; i < saveX.size(); i++) {
            int left = 50 + 60 * saveX.get(i);
            int top = 50 + 60 * saveY.get(i);
            int right = left + 60;
            int bottom = top + 60;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int left = 50 + 60 * i;
                    int top = 50 + 60 * j;
                    int right = left + 60;
                    int bottom = top + 60;

                    if (x >= left && x < right && y >= top && y < bottom) {
                        saveX.add(i);
                        saveY.add(j);
                        invalidate();
                        return true;
                    }

                }
            }

        }
        return false;
    }
}




