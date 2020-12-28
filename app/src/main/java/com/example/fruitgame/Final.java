package com.example.fruitgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Final extends AppCompatActivity {
    private MediaPlayer mp;
    private Button botoncito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        mp = MediaPlayer.create(this,R.raw.acdc);
        mp.start();
        botoncito=(Button)findViewById(R.id.botonFinal);
    }

    public void volver(View view)
    {
        Intent i= new Intent(this,MainActivity.class);
        mp.stop();
        mp.release();
        startActivity(i);
    }
}
