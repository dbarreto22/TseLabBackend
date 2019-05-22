package com.fakenews.data;

public enum TipoCalificacion {
	VERDADERO("Verdadero"),
    VERD_A_MEDIAS("Verdadero a medias"),
    INFLADO("Inflado"),
    ENGANOSO("Engañoso"),
    FALSO("Falso"),
    RIDICULO("Rídiculo");
   
    private String tipoCalificacionStr;
    
    TipoCalificacion(String tipoCalificacionStr){
        this.tipoCalificacionStr = tipoCalificacionStr;
    }
    
    public String tipoCalificacionStr() {
        return tipoCalificacionStr;
    }
}
