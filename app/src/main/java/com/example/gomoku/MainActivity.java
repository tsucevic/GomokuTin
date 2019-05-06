package com.example.gomoku;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    final static int maxN=15; // Velicina ploce
    private ImageView[][] ivCell = new ImageView[maxN][maxN]; // deklaracija varijable za celije u polju
    private Context context;

    private Drawable[] drawCell= new Drawable[4]; //0 prazno, 1 igrac jedan, 2 igrac dva, 3 pozadina

    private Button btnPlay;
    private TextView tvTurn;
    private int[][] valueCell= new int[maxN][maxN]; // 0 nitko, 1 , 2
    private int winner_play; // 0 nitko, 1 igrac 1, 2 igrac 2
    private boolean firstMove;
    private int xMove,yMove;
    private int turnPlay;

    public MainActivity(/*Button btnPlay, TextView tvTurn*/) {
        //this.btnPlay = btnPlay;
        //this.tvTurn = tvTurn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        setListen();
        loadResources();
        designBoardGame();
    }

    private void setListen() {
        btnPlay = (Button) findViewById(R.id.btnPlay);
        tvTurn = (TextView) findViewById(R.id.tvTurn);

        btnPlay.setText("NOVA IGRA");
        tvTurn.setText("Dotaknite NOVA IGRA za igru");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_game();
                play_game();
            }
        });
    }

    private void init_game() {
        firstMove=true;
        winner_play=0;
        for (int i=0;i<maxN;i++){
            for (int j=0;j<maxN;j++){
                ivCell[i][j].setImageDrawable(drawCell[0]);
                valueCell[i][j] =0;
            }
        }

    }

    private void play_game() {
       turnPlay = 1;
       if (turnPlay==1){
           Toast.makeText(context,"Igrač 1 igra prvi.",Toast.LENGTH_SHORT).show();
           playerTurn();
       } else{
           Toast.makeText(context,"Igrač 2 igra prvi",Toast.LENGTH_SHORT).show();
           botTurn();
       }
    }

    private void botTurn() {
        tvTurn.setText("Igrač 2");
        Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();
        isClicked=false;
    }

    private void playerTurn() {
        tvTurn.setText("Igrač 1");
        Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
        isClicked=false;
    }

    private void make_a_move() {
        ivCell[xMove][yMove].setImageDrawable(drawCell[turnPlay]);
        valueCell[xMove][yMove] = turnPlay;
        if (!isWinPlay()) {
            if (turnPlay == 1) {
                turnPlay = 3 - turnPlay;
                botTurn();
            } else {
                turnPlay = 3 - turnPlay;
                playerTurn();
            }
        }else {
            winner_play=turnPlay;
            tvTurn.setText("IGRAČ "+winner_play+" JE POBJEDNIK!");
        }
    }

    private boolean isWinPlay() {

       if(prebrojavanje(turnPlay,xMove,yMove,0,1)==5){
            return true;
        }else if (prebrojavanje(turnPlay,xMove,yMove,1,0)==5){
            return true;
        }else if (prebrojavanje(turnPlay,xMove,yMove,1,-1)==5){
            return true;
        }else if (prebrojavanje(turnPlay,xMove,yMove,1,1)==5){
            return true;
        }else {
            return false;
        }
    }

    private int prebrojavanje(int igrac, int red, int stupac, int smjerX, int smjerY) {
        //private int prebrojavanje(int igrac, int red, int stupac, int smjerX, int smjerY){
           int br = 1;  // Broj kamencica istog igraca

           int r, s;    // Koordinata kamencica od kojeg pocinjemo na ploci

            r = red + smjerX;  // Provjera u specificiranom smjeru
            s = stupac + smjerY;
            //return 2;
            while ( r >= 0 && r < 15 && s >= 0 && s < 15  && valueCell[r][s] == igrac ) {
                // Polje koje provjeravamo je na ploci
                // i sadrzi kamencic igraca koji je bio na potezu.
                br++;
                r += smjerX;  // Provjerava se slijedeci kamencic.
                s += smjerY;
            }

            r = red - smjerX;  // Sada se provjera krece u drugom smjeru.
            s = stupac - smjerY;
            while ( r >= 0 && r < 15 && s >= 0 && s < 15 && valueCell[r][s] == igrac ) {
                br++;
                r -= smjerX;   // Provjerava se slijedeci kamencic u tom smjeru.
                s -= smjerY;
            }

            return br;
        //}

    }


    private void loadResources() {
        drawCell[3] = context.getResources().getDrawable(R.drawable.bg); // pozadina celije
        drawCell[0] = null; // prazna celija
        drawCell[1] =context.getResources().getDrawable(R.drawable.p1); // igrac 1
        drawCell[2] =context.getResources().getDrawable(R.drawable.p2); // igrac 2

    }

    private boolean isClicked; // prati je li odigrano na neku celiju

    private void designBoardGame() {
        // izrada parametara za optimiziranu velicinu celije
        // ^ je bitno zato sto ce se napraviti horizontalni LinearLayout
        // koji sadrzi maxN celija

        int sizeOfCell = Math.round(ScreenWidth()/maxN);
        LinearLayout.LayoutParams lpRow = new LinearLayout.LayoutParams(sizeOfCell*maxN, sizeOfCell);
        LinearLayout.LayoutParams lpCell = new LinearLayout.LayoutParams(sizeOfCell,sizeOfCell);

        LinearLayout linBoardGame= (LinearLayout) findViewById(R.id.linBoardGame);

        // izrada celija
        for (int i=0;i<maxN;i++){
           LinearLayout linRow = new LinearLayout(context);
           // izrada reda
            for (int j=0;j<maxN;j++){
                ivCell[i][j]=new ImageView(context);
                // izrada celije
                ivCell[i][j].setBackground(drawCell[3]);
                final int x=i;
                final int y=j;
                ivCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(/*turnPlay==1 &&*/ !isClicked && valueCell[x][y]==0 && winner_play==0){
                            isClicked=true;
                            xMove=x;yMove=y;
                            make_a_move();
                        }
                    }
                });
                linRow.addView(ivCell[i][j],lpCell);
            }
            linBoardGame.addView(linRow,lpRow);
        }
    }

    private float ScreenWidth() {
        Resources resources=context.getResources();
        DisplayMetrics dp = resources.getDisplayMetrics();
        return dp.widthPixels;
    }


}
