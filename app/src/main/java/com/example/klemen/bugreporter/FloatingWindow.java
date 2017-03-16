package com.example.klemen.bugreporter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

public class FloatingWindow extends Service{

    WindowManager windowManager;
    boolean clicked = true;
    CountDownTimer countTimer;
    View layoutButton;
    com.github.clans.fab.FloatingActionButton video, screen, description;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //declaration
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        setTheme(R.style.AppTheme);
        countTimer = null;
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;
        description = new com.github.clans.fab.FloatingActionButton(FloatingWindow.this);
        screen = new com.github.clans.fab.FloatingActionButton(FloatingWindow.this);
        video = new com.github.clans.fab.FloatingActionButton(FloatingWindow.this);

        //Main Button
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layoutButton = inflater.inflate(R.layout.activity_main_button,null ,true);
        final WindowManager.LayoutParams parametersButton = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        parametersButton.gravity = Gravity.LEFT;
        parametersButton.x = 0;
        parametersButton.y = 200;
        windowManager.addView(layoutButton, parametersButton);
        startTimer();


        /*final FloatingActionMenu floatingActionMenu = (FloatingActionMenu) layoutButton.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(floatingActionMenu.isOpened()) {
                    floatingActionMenu.close(true);
                    floatingActionMenu.removeAllMenuButtons();
                    parametersButton.gravity = Gravity.LEFT;
                    parametersButton.x = 0;
                    parametersButton.y = 200;
                    windowManager.updateViewLayout(layoutButton,parametersButton);
                    startTimer();
                }
                else {
                    cancelTimer();
                    layoutButton.setAlpha(1.0f);
                    WindowManager.LayoutParams updatedParams = parametersButton;
                    description.setButtonSize(1);
                    description.setImageResource(R.mipmap.ic_pisi);
                    screen.setButtonSize(1);
                    screen.setImageResource(R.mipmap.ic_screen);
                    video.setButtonSize(1);
                    video.setImageResource(R.mipmap.ic_video);
                    floatingActionMenu.addMenuButton(screen);
                    floatingActionMenu.addMenuButton(video);
                    floatingActionMenu.addMenuButton(description);
                    floatingActionMenu.open(true);
                    updatedParams.gravity = Gravity.RIGHT;
                    updatedParams.x = 0;
                    updatedParams.y = 200;
                    windowManager.updateViewLayout(layoutButton,updatedParams);
                }
            }
        });*/

        //Button moved
        layoutButton.setOnTouchListener(new View.OnTouchListener() {
            WindowManager.LayoutParams updatedParameters = parametersButton;
            int x;
            int y;
            double pressedX;
            double pressedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParameters.x;
                        y = updatedParameters.y;
                        pressedX = event.getRawX();
                        pressedY = event.getRawY();
                        clicked = true;
                        layoutButton.setAlpha(1.0f);
                        cancelTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(updatedParameters.y > (height/2 - 300)) {
                            updatedParameters.y = height/2 - 150;
                        }
                        else if(updatedParameters.y < -(height/2 - 300)) {
                            updatedParameters.y = -height/2 + 150;
                        }
                        else if(updatedParameters.x <= (width/2 - 150)) {
                            updatedParameters.x = 0;
                        }
                        else {
                            updatedParameters.x = width - 5;
                        }
                        windowManager.updateViewLayout(layoutButton,updatedParameters);
                        startTimer();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = x - (int) (event.getRawX() - pressedX);
                        updatedParameters.y = y + (int) (event.getRawY() - pressedY);
                        windowManager.updateViewLayout(layoutButton, updatedParameters);
                        clicked = false;
                        break;
                    default:
                        return false;
                }
                return false;
            }

        });
    }
    //start timer function
    void startTimer() {
        countTimer = new CountDownTimer(5000, 20000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                layoutButton.setAlpha(0.7f);
            }
        };
        countTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(countTimer!=null)
            countTimer.cancel();
    }
}