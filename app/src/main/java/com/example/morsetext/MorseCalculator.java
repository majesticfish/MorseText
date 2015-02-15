package com.example.morsetext;

/**
 * Created by Phillip on 2/14/2015.
 */
public class MorseCalculator {
    static char[] alphabet = {' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
                             's','t','u','v','w','x','y','z',',','.','!','?','#'};
    static char[] capitals = {' ','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
                               'S','T','U','V','W','X','Y','Z'};
    public boolean[] charToInt(char a){
        //used when reading texts, changing them into vibrations
        //shows true if has a bit, false otherwise
        //indexes 0,1,2,3,4 are the bits of the number in order
        int temp = 0;
        for(int i=0;i<alphabet.length;i++){
            if(a==alphabet[i]) temp=i;
        }
        for(int i=0;i<capitals.length;i++){
            if(a==capitals[i]) temp=i;
        }
        boolean[] bits = new boolean[5];
        for(int i=0;i<5;i++){
            bits[4-i] = temp%2==1?true:false;
        }
        return bits;
    }
    public char intToChar(int a){
        //used when taking in input, changing into text to send
        return alphabet[a];
    }
}
