package com.example.morsetext;

/**
 * Created by Phillip on 2/14/2015.
 */
public class MorseCalculator {
    static char[] alphabet = {' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
                             's','t','u','v','w','x','y','z',',','.','!','?','#'};
    static char[] capitals = {' ','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
                               'S','T','U','V','W','X','Y','Z'};
    static public boolean[] charToInt(char a){
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
            temp /= 2;
        }
        return bits;
    }
    static public long[] stringToBuzz(String string){
        long[] answer = new long[string.length()*10+2];
        answer[0] = 0;
        answer[1] = 2500;
        for(int i = 0; i < string.length(); i ++){
            boolean[] temp = charToInt(string.charAt(i));
            for(int j = 0; j < 5; j ++){
                if(j == 0){
                    answer[(i * 5 + j) * 2 + 2] = 700;
                }else {
                    answer[(i * 5 + j) * 2 + 2] = 350;
                }
                answer[(i*5+j)*2+3] = temp[j]? 700:300;
            }
        }
        return answer;
    }
    static public char intToChar(int a){
        //used when taking in input, changing into text to send
        return alphabet[a];
    }
}
