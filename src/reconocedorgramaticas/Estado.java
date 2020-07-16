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
public class Estado {
    
    private Estado ligaDer, ligaIzq;
    private NodoA cabeza;
    private boolean noDeterministico;
    private boolean aceptacion;
    private ArrayList<NodoA> listaND;
    
    
    public Estado(){
        noDeterministico = aceptacion = false;
        ligaDer = ligaIzq = null;
        cabeza = new NodoA();
        listaND = new ArrayList<>();
        cabeza.setLigaDer(cabeza);
        cabeza.setLigaIzq(cabeza);
        cabeza.setSimbolo(null);
        cabeza.setEstado(null);
    }
    public boolean esVacio(){
        return cabeza == cabeza.getLigaIzq();
    }
    
    public void setAceptacion(boolean a){
        aceptacion = a;
    }
    
    public boolean getAceptacion(){
        return aceptacion;
    }
    
    public Estado getLigaDer() {
        return ligaDer;
    }

    public void setLigaDer(Estado ligaDer) {
        this.ligaDer = ligaDer;
    }

    public Estado getLigaIzq() {
        return ligaIzq;
    }

    public void setLigaIzq(Estado ligaIzq) {
        this.ligaIzq = ligaIzq;
    }
    
    public NodoA getCabeza(){
        return cabeza;
    }
    
    public String getEstado(){ //estado actual
        return cabeza.getEstado();
    }
    
    public void setEstado(String estado){ // define el estado actual de la trasicion
        cabeza.setEstado(estado);
        
    }
    
    public NodoA primerElemento(){
        return cabeza.getLigaDer();
    }
    
    public NodoA ultimoElemento(){
        return cabeza.getLigaIzq();
    }
    
    public boolean esVacia(){
        return cabeza.getLigaDer() == cabeza;
    }

    public ArrayList<NodoA> getListaND() {
        return listaND;
    }

    
    
    
    
    public StringBuilder imprimir(){
        StringBuilder transicion = new StringBuilder();
        NodoA a = cabeza;
        transicion.append(a.getEstado()).append("\4");
        a = a.getLigaDer();
        while(a != cabeza){
            transicion.append("|").append("\4").append(a.getSimbolo()).append("-").append(a.getEstado());
            a = a.getLigaDer();
        }
        transicion.append("|");
        return transicion;
    }
    
    public NodoA desconectar(NodoA t){ 
        //si el nodo a desconectar no es cabeza = retorna el nodo
        //caso contrario = null
        if(t != cabeza){
            t.getLigaDer().setLigaIzq(t.getLigaIzq());
            System.out.println("Direccion:"+t+" izquierdo:"+t.getLigaIzq()+" derecho:"+t.getLigaDer());
            t.getLigaIzq().setLigaDer(t.getLigaDer());
            return t;
        }
        return null;
    }
    
    public void insertarTransicion(NodoA t){ // inserción al final
        if(!esVacia()){ // si No es vacia
            t.setLigaDer(cabeza.getLigaIzq());
            t.setLigaDer(cabeza.getLigaIzq().getLigaDer());
            cabeza.getLigaIzq().setLigaDer(t);
            cabeza.setLigaIzq(t);
        }else{ // caso contrario
            cabeza.setLigaDer(t);
            cabeza.setLigaIzq(t);
            t.setLigaDer(cabeza);
            t.setLigaIzq(cabeza);
        }
        
    }
    
    public boolean crearTransicion(String estado, String simbolo){ // crea una transicion
        NodoA x = new NodoA();
        x.setEstado(estado);
        x.setSimbolo(simbolo);
        insertarTransicion(x);
        return !esVacia();
    }
    
    
    
    public boolean esDeterministico(){ //determina si es deterministico con una busqueda simple
        NodoA x, y;
        x = primerElemento();
        y = x.getLigaDer();
        boolean bandera = true;
        while(x != cabeza){
            while(y != cabeza){
                if(y.getSimbolo().equals( x.getSimbolo())){
                    
                    if(!listaND.contains(x)){
                        listaND.add(x);
                    }
                    
                    bandera= false;
                }
                y = y.getLigaDer();
            }
            
            x = x.getLigaDer();
            y = x.getLigaDer();
            
            
        }
        
        return bandera;
      
    }
    
    public void imprimirEstado(){
        NodoA x = primerElemento();
        
        while(x != cabeza){
            
            x = x.getLigaDer();
        }
    }
    
    
    //en desarrollo
    
    
    

    
    public void ordenarTransiciones(ArrayList<String> listaSimbolos){
        Estado e = new Estado();
        e.setEstado(this.getEstado());
        for(int i = 0; i < listaSimbolos.size(); i++){
            NodoA x = primerElemento();
         
            while(x != cabeza){
                if(x.getSimbolo().equals(listaSimbolos.get(i))){
                    e.crearTransicion(x.getEstado(), x.getSimbolo());
                    x = x.getLigaDer();
                }else{
                   x = x.getLigaDer(); 
                }
                
            }
            
        }
        
        //Reescritura
        
        NodoA a, b;
        a = this.primerElemento();
        b = e.primerElemento();
        while(a != cabeza){
            
            a.setEstado(b.getEstado());
            a.setSimbolo(b.getSimbolo());
            
            a = a.getLigaDer();
            b= b.getLigaDer();
        }
    }
    
    //une dos o más transiciones
    public Estado unirEstados(ArrayList<NodoA> transiciones){
        
        Estado e = new Estado();
        StringBuilder a = new StringBuilder();
        transiciones.forEach((transicion) -> {
            a.append(transicion.getEstado());
        });
        
        e.setEstado(a.toString());
        
        return e;
    }
    
    public String proximoEstado(char a){
        NodoA x = primerElemento();
        while(x != cabeza){
            
            if(x.getSimbolo().equals(String.valueOf(a))){
                return x.getEstado();
            }
            x = x.getLigaDer();
        }
        return null;
    }
    
    public StringBuilder unirTransicionesND(){
        String actual;
        StringBuilder transicion = new StringBuilder();
        
        NodoA x = primerElemento();
        
        actual = x.getSimbolo();
        while (x!= cabeza){
            
            if(x.getSimbolo().equals(actual) ){
                actual = x.getSimbolo();
                transicion.append(x.getEstado());
                System.out.println("dato a desconectar"+x.getEstado()+" Simbolo:"+x.getSimbolo()+" izquierda:"+x.getLigaIzq());
                x =desconectar(x);
            }else{
                actual = x.getSimbolo();
                crearTransicion(transicion.toString(), actual);
                
            }
            x = x.getLigaDer();
        }
        return transicion;
    }
}
