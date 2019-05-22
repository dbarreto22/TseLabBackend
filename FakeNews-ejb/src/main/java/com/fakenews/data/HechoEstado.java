package com.fakenews.data;

public enum HechoEstado {
	
	A_COMPROBAR("A comprobar"),
    NUEVO("Nuevo"),
    EN_PROCESO("En proceso"),
    VERIFICADO("Verificado"),
    PUBLICADO("Publicado"),
    CANCELADO("Cancelado");
   
    private String estadoStr;
    
    HechoEstado(String estadoStr){
        this.estadoStr = estadoStr;
    }
    
    public String estadoStr() {
        return estadoStr;
    }
}
