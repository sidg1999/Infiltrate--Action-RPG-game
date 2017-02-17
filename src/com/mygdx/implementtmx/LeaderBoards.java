/*
 * Leaderboards screen that reads from a file and sorts the score of players and shows then as 
 * ascending order.
 * A: Muhammad Hassaan
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 *
 * @author hassa
 */
public class LeaderBoards implements Screen {
    // declare required variables
    ImplementTmx g;
    Texture backGround;
    SpriteBatch batch = new SpriteBatch();
    int[] scores;
    String[] names;
    BitmapFont title;
    String titleText;
    BitmapFont scur;
    String scurs = "";
    BitmapFont YourScore;
    String yourScore;
    MenuSwitch menuS;

    public LeaderBoards(ImplementTmx g,MenuSwitch menuS) { // main constructor
        backGround = new Texture(Gdx.files.internal("Space.jpg")); // set the same background as the menu
        this.g = g; 
        // instantiate neccassry stuff
        titleText = "Top 5 Scores";      
        title = new BitmapFont();
        title.getData().setScale(3);
        scur = new BitmapFont();
        scur.getData().setScale(2);  
        YourScore = new BitmapFont();
        this.menuS = menuS;
        
        addScore(menuS.getScore());
        
    }

    @Override
    public void show() {
        Gdx.app.log("LeaderBoards", "show called"); 
    }

    @Override
    public void render(float delta) {
        batch.begin(); // begin the batch
        batch.draw(backGround, 0, 0); // draw the backgrouind
        title.setColor(1.0f, 1.0f, 1.0f, 1.0f); // set basic color of the title text
        scur.setColor(1.0f, 1.0f, 1.0f, 1.0f); 
        scur.draw(batch, scurs, 480, 400); // draw the text
        title.draw(batch, titleText, 1024/2-100, 500);
        batch.end(); // end the batch
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("LeaderBoards", "resizing");
    }

    @Override
    public void pause() {
        Gdx.app.log("LeaderBoards", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("LeaderBoards", "resume called");
    }

    @Override
    public void hide() {
        Gdx.app.log("LeaderBoards", "hide called");
    }

    @Override
    public void dispose() {

    }

    public void addScore(int scoreToAdd) { // add score and names to File
            int size=0;
        try {
            FileWriter writeScore = new FileWriter("scores.txt", true);
            //FileWriter writeName = new FileWriter("names.txt,true");
            PrintWriter printScore = new PrintWriter(writeScore);
            //PrintWriter printName = new PrintWriter(writeName);
            printScore.printf("%s" + "%n", scoreToAdd);
            //printName.printf("%s" + "%n", name);
            //printName.close();
            printScore.close();
            size = findFileSize(); // find the size of file to declare the array to sort
            scores = new int[size]; // declare the array with the correct size
            names = new String[size];
            fillArray(size);
            if (size == 1) {
            } else {
                
                mergeSort(scores); // sort the array
                
            }
        } catch (IOException e) { // catch any errors
            System.out.println("Error" + e);
        }
        
        
        if(size<=5){// if size of array is less then 5 then we dont have top 5 scores at the moment until more games are played
            scurs = "Top 5 to be announced";
        } else { 
        // store top 5 scores in string
        for(int i = size-1;i>size-6;i--){
            scurs = scurs + scores[i] + "\n";
        }
        }
    }

    public int findFileSize() { // find the size of the file so we can declare the array
        int size = 0;
        try { // read the file and add to the size variable everytime a element is found
            boolean eof = false;
            FileReader fr = new FileReader("scores.txt");
            BufferedReader br = new BufferedReader(fr);
            String text;
            while (!eof) {
                text = br.readLine();
                if (text==null) {
                    eof = true;
                } else {
                    size++;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return size; // retunr the size 
    }

    public void fillArray(int size) { // fill the array with information from the text file
        try {
            
            FileReader score = new FileReader("scores.txt");
            BufferedReader scor = new BufferedReader(score);
            String scoress;
            int num;
            String noms;
            // fill scores array
            for (int i = 0; i < size; i++) {
               scoress = scor.readLine();
               num = Integer.parseInt(scoress);
                scores[i] = num;
            }
            scor.close();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
    }

    // merge sort
    static void mergeSort(int[] A) {
        if (A.length > 1) {
            int q = A.length / 2;

            int[] leftArray = Arrays.copyOfRange(A, 0, q);
            int[] rightArray = Arrays.copyOfRange(A, q, A.length);

            mergeSort(leftArray);
            mergeSort(rightArray);
            merge(A, leftArray, rightArray);
        }
    }

    static void merge(int[] a, int[] l, int[] r) {
        int totElem = l.length + r.length;
        //int[] a = new int[totElem];
        int i, li, ri;
        i = li = ri = 0;
        while (i < totElem) {
            if ((li < l.length) && (ri < r.length)) {
                if (l[li] < r[ri]) {
                    a[i] = l[li];
                    i++;
                    li++;
                } else {
                    a[i] = r[ri];
                    i++;
                    ri++;
                }
            } else {
                if (li >= l.length) {
                    while (ri < r.length) {
                        a[i] = r[ri];
                        i++;
                        ri++;
                    }
                }
                if (ri >= r.length) {
                    while (li < l.length) {
                        a[i] = l[li];
                        li++;
                        i++;
                    }
                }
            }
        }
        //return a;
    }

}
