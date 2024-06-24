package com.antoniotassone.utilities;

public class IdentifierManagement{
    private IdentifierManagement(){}

    public static String createIdentifier(int length){
        if(length <= 0){
            return "";
        }
        char[] chars = {'a','b','c','d','e','f','g','h','i','j',
                'k','l','m','n','o','p','q','r','s','t',
                'u','v','w','x','y','z','A','B','C','D',
                'E','F','G','H','I','L','M','N','O','P',
                'Q','R','S','T','U','V','W','X','Y','Z',
                '0','1','2','3','4','5','6','7','8','9'};
        StringBuilder builder = new StringBuilder();
        int indexMax = chars.length - 1;
        int index;
        for(int i = 0;i < length;i++){
            index = (int)(Math.random() * indexMax);
            builder.append(chars[index]);
        }
        return builder.toString();
    }
}