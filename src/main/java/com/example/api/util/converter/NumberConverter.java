package com.example.api.util.converter;

public  class NumberConverter {
    
    public static boolean isNumber(String s) {
        
        if(s == null)
            return false;
        
        String number = s.replaceAll(",", ".");

        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double stringToDouble(String s) {

        if(s == null)
            return 0D;
        
        String number = s.replaceAll(",", ".");

        if(isNumber(number))
            return Double.parseDouble(number);
        
        return 0D;
    }
}
