package com.example.fruitgame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class Main2Activity_nivel4 extends AppCompatActivity {
    private TextView tv_nombre,tv_score;
    private ImageView iv_uno,iv_dos,iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp,mp_great,mp_bad;
///////////////////////////////////////////////////////////////creo este imageView

    private ImageView iv_signo;

    int score =0;
    int num_aleatorio_uno,num_aleatorio_dos,resultado,vidas=3;
    String nombre_jugador,string_score, string_vidas;
    String numero[]={"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_nivel4);


        Toast.makeText(this,"Nivel 4 - Sumas y Restas ", Toast.LENGTH_LONG).show();

        tv_nombre=(TextView) findViewById(R.id.textView_nombre);
        tv_score=(TextView) findViewById(R.id.textView_score);
        iv_vidas=(ImageView) findViewById(R.id.imageView_vidas);
        iv_uno=(ImageView)findViewById(R.id.imageView_numUno);
        iv_dos=(ImageView)findViewById(R.id.imageView_numDos);
        et_respuesta=(EditText)findViewById(R.id.editText_resultado);
//////////////////////////////////////////agrego el signo
        iv_signo=(ImageView) findViewById(R.id.imageView_signo);
        /////////////////////////////////////////////////// /////////////////////////////////////////////////// //obtenemos nombre del jugador ///////////////////////////////////////////////////
        nombre_jugador=getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: " + nombre_jugador);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////recojo los datos y los inserto///////////////////////////////////
        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: "+ score);

        string_vidas=getIntent().getStringExtra("vidas");
        vidas=Integer.parseInt(string_vidas);
        ///////////////coloco las vidas//////////
        if(vidas==3)
        {
            iv_vidas.setImageResource(R.drawable.tresvidas);
        }else if(vidas==2)
        {
            iv_vidas.setImageResource(R.drawable.dosvidas);
        }else if(vidas==1)
        {
            iv_vidas.setImageResource(R.drawable.unavida);
        }

        ////////////////////////////////////////// //////////////////////////////////////////////////////////////agregamos icono al actionbar ///////////////////////////////////////////////////
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



        /////////////////////////////////////////////////// ///////////////////////////////////////////////////empezamos la musica /////////////////////////////////////////////////// ///////////////////////////////////////////////////

        mp = MediaPlayer.create(this,R.raw.goats);
        mp.start();
        mp.setLooping(true);

        //ponemos audio de si la respuesta es correcta o incorrecta
        mp_great=MediaPlayer.create(this,R.raw.wonderful);
        mp_bad=MediaPlayer.create(this,R.raw.bad);



        //////////////////////////////////////////////cargamos las imagenes de los numeros aleatorios/////////////////////////////////////
        NumAleatorio();


    }
    ///////////////////////////////////////////////////comprobamos las respuestas//////////////////////////////////////////////////
    public void Comparar(View view)
    {
        String respuesta=et_respuesta.getText().toString();
        if(!respuesta.equals(""))
        {
            int respuesta_jugador=Integer.parseInt(respuesta); //transformamos en string el numero de la respuesta
            if(resultado==respuesta_jugador)
            {
                mp_great.start();
                score++;
                tv_score.setText("Score: " + score);
                et_respuesta.setText("");
                BaseDeDatos();    //actualizamos la base de datos para saber si el score supera al mejor_score

            }
            else{
                mp_bad.start();
                vidas--;
                BaseDeDatos();   //acutializa la base de datos para saber si el score supera al mejor_score
                switch(vidas)
                {
                    case 3:
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2:
                        Toast.makeText(this,"Quedan dos vidas",Toast.LENGTH_SHORT).show();
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1:
                        Toast.makeText(this,"Quedan una vida",Toast.LENGTH_SHORT).show();
                        iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0:
                        Toast.makeText(this,"has perdido todas las vidas",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(this,MainActivity.class);
                        startActivity(i);
                        finish();
                        mp.stop();
                        mp.release();
                        break;

                }
                //limpiamos los campos
                et_respuesta.setText("");

            }
            NumAleatorio();

        }
        else{
            Toast.makeText(this,"Escribe tu respuesta",Toast.LENGTH_LONG).show();


        }


    }


/////////////////////////////////////////////////////////////////////////creamos la asignacion de los numeros aleatorios y los insertamos en los imageView////////////////////////////

    public void NumAleatorio(){
////////////////////////////cambiamos el score a 39/////////
        if(score <= 39)
        {
            num_aleatorio_uno=(int)(Math.random() * 10);
            num_aleatorio_dos=(int)(Math.random() * 10);


            ////////////////////////////////////////////////////////////////para saber si sumar o restas utilizamos esta estructura
            if(num_aleatorio_uno>=0 && num_aleatorio_uno<=4)//establecemos q si esta entre 0 y 4   (Esto es pq queremos nosotros dependiendo de estos valores sumaremos o restaremos)
            {
                       resultado= num_aleatorio_uno+num_aleatorio_dos;
                       iv_signo.setImageResource(R.drawable.adicion); //le metemos el signo a la imagen
            }else{
                        resultado= num_aleatorio_uno-num_aleatorio_dos;
                        iv_signo.setImageResource(R.drawable.resta);
            }


            ///////////como no puede haber numeros negativos lo arreglamos asi////
            if(resultado >= 0 )
            {
                for( int i =0 ; i< numero.length ; i ++ )
                {

                    int id= getResources().getIdentifier(numero[i],"drawable",getPackageName() ); //metemos la imagen del numero en el ImageView
                    if(num_aleatorio_uno==i)
                    {
                        iv_uno.setImageResource(id);
                    }
                    if(num_aleatorio_dos==i)
                    {
                        iv_dos.setImageResource(id);
                    }
                }
            }else //utilizamos recursividad
            {
                NumAleatorio();
            }

        }else{
            //////////////////////////////aqui cambiamos el activity al 3 /////////////
            Intent intent= new Intent(this, Main2Activity_nivel5.class);
            //enviamos  score , vidas y el nombre del jugador
            string_score=String.valueOf(score);
            string_vidas=String.valueOf(vidas);

            intent.putExtra("jugador",nombre_jugador);
            intent.putExtra("score",string_score);
            intent.putExtra("vidas",string_vidas);
            startActivity(intent);
            mp.stop();
            mp.release();
            finish();

        }


    }



    ///////////////////////////////////////////////////////////////////////////////////nos permite crud del score de cada jugador////////////////////////////////////

    public void BaseDeDatos()
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();   //apertura, lectura y escritura de BBDD

        //verificamos si existen los registros y comprobamos si son superiores sus scores o no
        // hacemos la consulta
        Cursor consulta= BD.rawQuery("Select * from puntaje where score = (select max(score) from puntaje)",null);

        if(consulta.moveToFirst()) ///comprobamos si se ha encontrado algun registro
        {
            String temp_nombre= consulta.getString(0); //coge el valor de la columna 0
            String temp_score= consulta.getString(1); //coge el valor de la columna 1

            int mejor_score= Integer.parseInt(temp_score); //convertimos de string a entero

            if(score > mejor_score) //verificamos si el score actual es el mejor
            {
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", nombre_jugador);
                modificacion.put("score", score);

                BD.update("puntaje", modificacion , ("score=" + mejor_score),null);

            }
            BD.close();
        }else{

            ContentValues insertar= new ContentValues();
            insertar.put("nombre", nombre_jugador);
            insertar.put("score",score);
            BD.insert("puntaje",null,insertar);
            BD.close();
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////controlamos el boton back//////////////////////////////////////////////
    @Override
    public void onBackPressed()
    {

    }

}
