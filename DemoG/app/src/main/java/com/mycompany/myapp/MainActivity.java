package com.mycompany.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import game.graphic.GameView;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnTouchListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game);
        Button btn_down = (Button) findViewById(R.id.gm_down);
        Button btn_up = (Button) findViewById(R.id.gm_up);
        Button btn_left = (Button) findViewById(R.id.gm_left);
        Button btn_right = (Button) findViewById(R.id.gm_right);
        Button btn_ok = (Button) findViewById(R.id.gm_ok);
        Button btn_cancle = (Button) findViewById(R.id.gm_cancle);
        Button btn_menu = (Button) findViewById(R.id.gm_menu); 
        btn_up.setOnTouchListener(this);
        btn_down.setOnTouchListener(this);
        btn_left.setOnTouchListener(this);
        btn_right.setOnTouchListener(this);
        btn_ok.setOnTouchListener(this);
        btn_cancle.setOnTouchListener(this);
        btn_menu.setOnTouchListener(this);
        btn_up.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View p1, MotionEvent p2)
    {
        // TODO: Implement this method
        switch (p1.getId())
        {
            case R.id.gm_ok:
                return GameView.mTouch(game.graphic.Button.OK, p2);            
            case R.id.gm_up:
                return  GameView.mTouch(game.graphic.Button.UP, p2);           
            case R.id.gm_down:
                return  GameView.mTouch(game.graphic.Button.DOWN, p2);           
            case R.id.gm_left:
                return  GameView.mTouch(game.graphic.Button.LEFT, p2);           
            case R.id.gm_right:
                return  GameView.mTouch(game.graphic.Button.RIGHT, p2);           
            case R.id.gm_menu:
                return  GameView.mTouch(game.graphic.Button.MENU, p2);           
            case R.id.gm_cancle:
                return  GameView.mTouch(game.graphic.Button.CANCLE, p2);           
        }
        return true;
    }
    void outp(Object o)
    {
        Toast.makeText(MainActivity.this, o.toString(), 100).show();
    }

}
