
package com.example.mobile_pixelart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        myView = findViewById(R.id.myView);
        if(itemid == R.id.item1){
            myView.penSizedown();
        }
        else if(itemid == R.id.item2){
            myView.setCanvasSize(16);
        }
        else if(itemid == R.id.item3){
            myView.setCanvasSize(32);
        }
        else if(itemid == R.id.item4){
            myView.setCanvasSize(64);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean darklightflag = true;
    public MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.myView);
        ImageButton clearButton = findViewById(R.id.clearButton);
        ImageButton sizeButton = findViewById(R.id.sizeButton);
        ImageButton colorButton = findViewById(R.id.colorButton);
        ImageButton darknlightButtonButton = findViewById(R.id.darkNlightButton);

        LinearLayout linearLayout = findViewById(R.id.buttonlayout);

        Button.OnClickListener listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId() == R.id.clearButton)
                    myView.clearCanvas();

                if(v.getId() == R.id.sizeButton)
                    myView.penSize();

                if(v.getId() == R.id.colorButton)
                    myView.randomPenColor();

                if(v.getId() == R.id.darkNlightButton) {
                    if(darklightflag == true){
                        myView.darklightChange1();
                        findViewById(R.id.main).setBackgroundColor(Color.parseColor("#c1b4b0"));
                        darklightflag = false;
                    }
                    else{
                        myView.darklightChange2();
                        findViewById(R.id.main).setBackgroundColor(Color.parseColor("#A1887F"));
                        darklightflag = true;
                    }

                }

            }





        };

        clearButton.setOnClickListener(listener);
        sizeButton.setOnClickListener(listener);
        colorButton.setOnClickListener(listener);
        darknlightButtonButton.setOnClickListener(listener);

/*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });*/

    }
}

class MyView extends View {
    int canvasSize = 16;
    int cellsize = 560 / canvasSize;
    int startWich = 88; // wich는 위치라는 뜻, 여기서 80단위로 크기 맞출것

    boolean flag = true;
    int currentPenSize = 0;


    int R = 0;         // 기본 펜 색상
    int G = 0;
    int B = 0;


  /*  ArrayList<Integer> saveX = new ArrayList<>();
    ArrayList<Integer> saveY = new ArrayList<>();*/

    /*ArrayList<Integer> saveXSmall = new ArrayList<>();
    ArrayList<Integer> saveYSmall = new ArrayList<>();
    ArrayList<Integer> saveXLarge = new ArrayList<>();
    ArrayList<Integer> saveYLarge = new ArrayList<>();*/

    ArrayList<Pixel> pixels = new ArrayList<>();

/*    ArrayList<Integer> currentSaveX = saveXSmall;
    ArrayList<Integer> currentSaveY = saveYSmall;*/


    public void darklightChange1(){
        setBackgroundColor(Color.parseColor("#c1b4b0"));
    }
    public void darklightChange2(){
        setBackgroundColor(Color.parseColor("#A1887F"));
    }
    public void penColor() {

    }

    public void randomPenColor(){
        this.R = (int)(Math.random() * 255);
        this.G = (int)(Math.random() * 255);
        this.B = (int)(Math.random() * 255);
        invalidate();
    }
    public void penSize(){
        currentPenSize += 1;
//        currentPenSize = (currentPenSize == 0) ? 1 : 0;
    }

    public void penSizedown(){
        if(currentPenSize > 0){
            currentPenSize -= 1;
        }
    }

    public void clearCanvas() {
        pixels.clear();
        invalidate();
    }

//    public MyView(Context context) {
//        super(context);
//        setBackgroundColor(Color.parseColor("#F5F5DC"));
//    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.parseColor("#A1887F"));           // 배경색 심근경색 크흡킄ㅋ큭
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

        for (Pixel pixel : pixels) {
            paint.setColor(pixel.color);
            int left = 80 + cellsize * pixel.x;
            int top = startWich + cellsize * pixel.y;
            int sizeOffset = pixel.size * cellsize;
            int right = left + cellsize + sizeOffset;
            int bottom = top + cellsize + sizeOffset;
            canvas.drawRect(left, top, right, bottom, paint);
        }


//        paint.setColor(Color.BLACK);
//        for (int i = 0; i <= 10; i++) {
//            canvas.drawLine(50 + cellsize * i, startWich, 50 + cellsize * i, 650 + startWich, paint);
//            canvas.drawLine(50, startWich + cellsize * i, 650 + startWich, 50 + cellsize * i, paint);
//        }

    }


    public boolean onTouchEvent(MotionEvent event) {       // 드로잉 -> 픽셀 채우기
        int x = (int) event.getX();
        int y = (int) event.getY();


        for (int i = 0; i < canvasSize; i++) {
            for (int j = 0; j < canvasSize; j++) {
                int left = 80 + cellsize * i;
                int top = startWich + cellsize * j;
                int right = left + cellsize ;
                int bottom = top + cellsize ;

                if (x >= left && x < right && y >= top && y < bottom) {
                    pixels.add(new Pixel(i, j, Color.rgb(R, G, B), currentPenSize));
                    invalidate();
                    return true;
                }

            }
        }
        return false;
    }

    private static class Pixel {
        int x, y;
        int color;
        int size;

        Pixel(int x, int y, int color, int size) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.size = size;
        }
    }

    public void setCanvasSize(int newCanvasSize) {
        this.canvasSize = newCanvasSize;
        this.cellsize = 560 / newCanvasSize;
        clearCanvas();
    }

}
