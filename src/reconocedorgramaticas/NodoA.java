/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reconocedorgramaticas;
import java.util.*;
/**
 *
 * @author andres.cortesg
 */
public class NodoA {
    private String estado;
    private String simbolo;
    private NodoA ligaDer, ligaIzq;
    
    
    public NodoA(){
        ligaDer = ligaIzq = null;
        
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public NodoA getLigaDer() {
        return ligaDer;
    }

    public void setLigaDer(NodoA ligaDer) {
        this.ligaDer = ligaDer;
    }

    public NodoA getLigaIzq() {
        return ligaIzq;
    }

    public void setLigaIzq(NodoA ligaIzq) {
        this.ligaIzq = ligaIzq;
    }

    
    
    
}
