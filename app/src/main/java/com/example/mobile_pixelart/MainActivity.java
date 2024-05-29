
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
    int canvasSize = 16;
    int cellsize = 560 / canvasSize;
    int startWich = 510;



    ArrayList<Integer> saveX = new ArrayList<>();
    ArrayList<Integer> saveY = new ArrayList<>();

    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#F5F5DC"));
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(3);

        paint.setColor(Color.CYAN);
        for (int i = 0; i < canvasSize; i++) {
            for (int j = 0; j < canvasSize; j++) {
                if((i + j) % 2 != 0){
                    paint.setColor(Color.parseColor("#A9A9A9"));
                }
                else{
                    paint.setColor(Color.parseColor("#D3D3D3"));
                }
                canvas.drawRect(80 + cellsize * i, startWich + cellsize * j, 80 + cellsize * (i + 1), startWich + cellsize * (j + 1), paint);
            }
        }

//        paint.setColor(Color.BLACK);
//        for (int i = 0; i <= 10; i++) {
//            canvas.drawLine(50 + cellsize * i, startWich, 50 + cellsize * i, 650 + startWich, paint);
//            canvas.drawLine(50, startWich + cellsize * i, 650 + startWich, 50 + cellsize * i, paint);
//        }

        paint.setColor(Color.parseColor("#FFA07A"));
        for (int i = 0; i < saveX.size(); i++) {
            int left = 80 + cellsize * saveX.get(i);
            int top = startWich + cellsize * saveY.get(i);
            int right = left + cellsize;
            int bottom = top + cellsize;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        for (int i = 0; i < canvasSize; i++) {
            for (int j = 0; j < canvasSize; j++) {
                int left = 80 + cellsize * i;
                int top = startWich + cellsize * j;
                int right = left + cellsize;
                int bottom = top + cellsize;

                if (x >= left && x < right && y >= top && y < bottom) {
                    saveX.add(i);
                    saveY.add(j);
                    invalidate();
                    return true;
                }

            }
        }
        return false;
    }
}




