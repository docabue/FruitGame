package com.example.fruitgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



/**
 *
 *
 *this activity represents the first level that are sums of two random numbers
 *
 * @author David Ocampos Buendia
 * @version 1
 */

public class MainActivity extends AppCompatActivity {

    /**
     * declaration of variables
     */
       private EditText ednombre;
       private ImageView iv_personaje;
       private TextView tv_record;
       private MediaPlayer mp;

       int num_aleatorio=(int)(Math.random() * 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednombre=(EditText)findViewById(R.id.enombre);
        iv_personaje=(ImageView)findViewById(R.id.imageView_personaje);
        tv_record=(TextView)findViewById(R.id.MejorJugador);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

         /**
           place the image from a random number
          */
        int id;
        if(num_aleatorio == 0 || num_aleatorio==10)
        {
            id=getResources().getIdentifier("mango", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else if(num_aleatorio == 1 || num_aleatorio==9){

            id=getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(num_aleatorio == 2 || num_aleatorio==8){

            id=getResources().getIdentifier("manzana", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(num_aleatorio == 3 || num_aleatorio==7){

            id=getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(num_aleatorio == 4 || num_aleatorio==5 || num_aleatorio==6){

            id=getResources().getIdentifier("uva", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }


        /**
         *   create a database reaction
         */
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();//creamos la apertura modo lectura y escritura de la bbdd

        /**
         sql query creation
         */
        Cursor consulta = BD.rawQuery(
                "select* from puntaje where score=(select max(score) from puntaje)", null
        );
          if(consulta.moveToFirst() ){ //esto lo que dice es que si hay datos se mete en el if
            String tem_nombre=consulta.getString(0);//coloco el 0 pq es la columna 0
            String temp_score=consulta.getString(1);//cojo los datos de la columan 1 q es score
              tv_record.setText("El record "+ temp_score + " es de " + tem_nombre);
              BD.close();
          }else{
              BD.close();
          }



        mp =MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);


         }


    /**
     * We check if there is a name, if it exists we go to the next activity
     * If it does not exist we launch a toast
     * @param view
     */

    public void Jugar(View view)
    {
       String nombre= ednombre.getText().toString();

        if(!nombre.equals(""))
        {
            mp.stop();
            mp.release();
            Intent i = new Intent(this, Main2Activity_nivel1.class);
            i.putExtra("jugador",nombre);
            startActivity(i);
            finish();

        }else{
            Toast.makeText(this, "Debes escribir tu nombre", Toast.LENGTH_SHORT).show();
            ednombre.requestFocus();
            InputMethodManager imm= (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(ednombre, InputMethodManager.SHOW_IMPLICIT);


        }


    }

    /**
     * Music stopped and closed the app
     */
        @Override
    public void onBackPressed()
        {
         mp.stop();
         mp.release();
            finish();
        }


}
