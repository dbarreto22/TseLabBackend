package com.fakenews.datatypes;

public enum EnumParam {
	
	CLIENT_ID("CLIENT_ID"),
    CANT_DIAS("CANT_DIAS");
  
    private String paramStr;
    
    EnumParam(String paramStr){
        this.paramStr = paramStr;
    }
    
    public String paramStr() {
        return paramStr;
    }
}
