package net.bilaljh.fubar;

import java.io.*;
import java.util.Objects;

public class ScoreControl {


    public char[][] names = new char[10][3];
    public char[] characters;
    public long[] scores = new long[10];
    private int[] indexes = {-1, -1, -1};
    private int rank = 5;
    BufferedReader scoreReader, nameReader;
    RandomAccessFile scoresFile, namesFile;

    {
        try {
            scoreReader = new BufferedReader(new FileReader("src/resource/scores.txt"));
            nameReader = new BufferedReader(new FileReader("src/resource/names.txt"));
            scoresFile = new RandomAccessFile("src/resource/scores.txt", "rw");
            namesFile = new RandomAccessFile("src/resource/names.txt", "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ScoreControl() throws IOException {

        this.characters = new char[]{
                // Großbuchstaben
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                // Zahlen 0-9
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                // Umlaute
                'Ä', 'Ö', 'Ü', 'ß',
                // Sonderzeichen
                '!', '?', '-', '_', '.', ',', '*',
                // Leerzeichen
                ' '
        };

        readNames();
        readScores();

        /*
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 3; j++) {
                names[i][j] = (char) nameReader.read();
                System.out.print(names[i][j]);
            }
            scores[i] = Integer.parseInt(scoreReader.readLine());
            System.out.println( ": " + scores[i]);
        } */
    }

    public void setName(int slot, String action) {
        if(Objects.equals(action, "UP")) {
            indexes[slot]++;
            if(indexes[slot] > 47) {
                indexes[slot] = 0;
            }
            names[rank][slot] = characters[indexes[slot]];
        } else if(Objects.equals(action, "DOWN")) {
            indexes[slot]--;
            if(indexes[slot] < 0) {
                indexes[slot] = 47;
            }
            names[rank][slot] = characters[indexes[slot]];
        }
    }

    public void save() throws IOException {
        //int score = Main.player.getScore();
        int score = 999999;
        long position = rank * 5L;
        for(int i = 0; i < 3; i++) {
            namesFile.seek(position + i);
            namesFile.write(names[rank][i]);
        }
        printName();
        position = rank * 6L;
        scoresFile.seek(position);
        scoresFile.write(String.valueOf(score).getBytes());
        //readScores();
    }

    public void printName() {
        for(int i = 0; i < 10; i++) {
            System.out.print("Name: ");
            for (int j = 0; j < 3; j++) {
                System.out.print(names[i][j]);
            }
            System.out.println(", " + scores[i]);
        }
    }

    public void readNames() throws IOException {
        for(int i = 0; i < 10; i++) {
            char[] charBuffer;
            charBuffer = nameReader.readLine().toCharArray();
            for(int j = 0; j < 3; j++) {
                names[i][j] = charBuffer[j];
            }
        }
    }

    public void readScores() throws IOException {
        for(int i = 0; i < 10; i++) {
            scores[i] = Long.parseLong(scoreReader.readLine());
        }
    }
}