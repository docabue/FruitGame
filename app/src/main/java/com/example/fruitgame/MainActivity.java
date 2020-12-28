package com.example.fruitgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


       private EditText ednombre;
       private ImageView iv_personaje;
       private TextView tv_record;
       private MediaPlayer mp;

       int num_aleatorio=(int)(Math.random() * 10);  ///hacemos casting para convertirlo en int
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednombre=(EditText)findViewById(R.id.enombre);
        iv_personaje=(ImageView)findViewById(R.id.imageView_personaje);
        tv_record=(TextView)findViewById(R.id.MejorJugador);


             /////////////////////////////////////   //como colocar el icono de la app dentro de la actionbar/////////////////////////////////////////////////////////////////////////////////////////////////
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

       ////////////////////////////////////////////////////////////// //metemos una imagen aleatoria///////////////////////////////////////////////////////////////////////////////////////////////////////////
        int id;
        if(num_aleatorio == 0 || num_aleatorio==10) //si el numero es 0 o 10 mete mango
        {
            id=getResources().getIdentifier("mango", "drawable", getPackageName()); //busca la  imagen mango dentro de drawable
            iv_personaje.setImageResource(id); //mete dentro del imageView la ruta del la imagen
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
        ////////////////////////////////////////////////////////////////XREAMOS RELACION CON BBDD/////////////////////////////////////////////////////////////////
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();//creamos la apertura modo lectura y escritura de la bbdd

        //creamos la consulta a la base de datos////
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


        ////////////////////////////////////////////////////////////////APERTURA DE AUDIO//////////////////////////////////////////////////////////////////
        mp =MediaPlayer.create(this, R.raw.alphabet_song);  //aqui indicamos el contexto la ruta de la song
        mp.start();  //empieza la cancion
        mp.setLooping(true); //para que se repruduzca en buble
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

         }



    //////////////////////////////////////////////////////////////////////////METODO JUGAR//////////////////////////////////////////////////////////////////////////////////////////////

    public void Jugar(View view)
    {
       String nombre= ednombre.getText().toString();
       //si encuentra el nombre o no
        if(!nombre.equals(""))
        {
            //iniciaremos el sigueinte activity, pero antes paramos el audio
            mp.stop();
            mp.release();//nos permite destruir el objeto MediaPlayer

            //pasamos al siguiente activity
            Intent i = new Intent(this, Main2Activity_nivel1.class);
            //enviamos el nombre del jugador
            i.putExtra("jugador",nombre); //pasamos parametro del nombre
            startActivity(i);
            finish();

        }else{
            Toast.makeText(this, "Debes escribir tu nombre", Toast.LENGTH_SHORT).show();
          ///////////////////////////////  //para q se abra el teclado automaticamente //////////////////////////////////////////////
            ednombre.requestFocus();
            InputMethodManager imm= (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(ednombre, InputMethodManager.SHOW_IMPLICIT);

            /////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////////////////////////////////////////////
        }


    }
    ///////////////////////////////////////////////////////////////////////////CREAMOS EL METODO PARA VOLVER A LA ACTIVITY ANTERIOR DANDOLE AL BOTON DEL BACK////////////////////////////////////////////////////////////////////////////////////////
        @Override
    public void onBackPressed()
        {
         finish();  //cierro la activity
        }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
