package com.example.morsetext;

/**
 * Created by Phillip on 2/14/2015.
 */
public class MorseCalculator {
    static char[] alphabet = {' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
                             's','t','u','v','w','x','y','z',',','.','!','?','#'};
    static char[] capitals = {' ','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
                               'S','T','U','V','W','X','Y','Z'};
    public int charToInt(char a){
        //used when reading texts, changing them into vibrations
        for(int i=0;i<alphabet.length;i++){
            if(a==alphabet[i]) return i;
        }
        for(int i=0;i<capitals.length;i++){
            if(a==capitals[i]) return i;
        }
        return 0;
    }
    public char intToChar(int a){
        //used when taking in input, changing into text to send
        return alphabet[a];
    }
}
