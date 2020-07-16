/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reconocedorgramaticas;

/**
 *Nodo doblemente ligado para un elemento de una producción
 * @author andres.cortesg
 */
public class NodoP {

    private String dato;
    private int modo;
    //0 = no terminal
    //1 = terminal
    //2 = secuencia nula
    private NodoP ligaIzq;
    private NodoP ligaDer;

    public NodoP() {
        modo  = 2;
        ligaDer = null;
        ligaIzq = null;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public NodoP getLigaIzq() {
        return ligaIzq;
    }

    public NodoP getLigaDer() {
        return ligaDer;
    }

    public void setLigaIzq(NodoP ligaIzq) {
        this.ligaIzq = ligaIzq;
    }

    public void setLigaDer(NodoP ligaDer) {
        this.ligaDer = ligaDer;
    }


    public String getDato() {
        return dato;
    }

    public int getModo() {
        return modo;
    }
    
    


}
