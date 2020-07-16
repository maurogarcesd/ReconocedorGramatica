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
 * @author elkin.garces
 */
public class Automata implements Cloneable{
    
    private boolean esDeterministico;
    private ArrayList<NodoA> estadosAceptacion = new ArrayList<>();
    private Estado cabeza;
    private ArrayList<String> listaEstados;
    private ArrayList<String> listaSimbolos;
 /**
 *Constructor
 * 
 */
    
    public Automata(){
        listaEstados = new ArrayList<>();
        listaSimbolos = new ArrayList<>();
        esDeterministico = true;
        estadosAceptacion = new ArrayList<>();
        cabeza = new Estado();
        cabeza.setLigaDer(cabeza);
        cabeza.setLigaIzq(cabeza);
    }

    
 /**
 *Metodo para determinar si el automata es deterministico
 * @return true si cumple para ser deterministico false de lo contrario
 * 
 */
   
     
    public int nroEstados(){
        return listaEstados.size();
    }
            
    public int nroSimbolos(){
        return listaSimbolos.size();
    }
            
    public ArrayList<NodoA> getEstadosAceptacion(){
        return estadosAceptacion;
    }
    
    public boolean esVacio(){
        return cabeza == cabeza.getLigaIzq();
    }
    
    public Estado getCabeza(){
        return cabeza;
    }
    public Estado primerElemento(){
        return cabeza.getLigaDer();
    }
    public Estado ultimoElemento(){
        return cabeza.getLigaIzq();
    }
     public boolean esDeterministico() {
        Estado x = primerElemento();
        while(x != cabeza){
            if(!x.esDeterministico()){
                esDeterministico = false;
            }
            x = x.getLigaDer();
        }
        return esDeterministico;
    }
    //para pruebas
    public void imprimirNodos(){
        Estado x = primerElemento();
         while(x != cabeza){
            System.out.println("--------------------nodo:"+x+" dato asociado:"+x.getEstado()+"----------------------");
            NodoA y = x.primerElemento();
            while(y != x.getCabeza()){
                System.out.println("Estado:"+y.getEstado()+" Simbolo:"+y.getSimbolo());
                y = y.getLigaDer();
            } 
            x = x.getLigaDer();
        }
    }
    
    public StringBuffer imprimir(){
        Estado x = primerElemento();
         StringBuffer a =  new StringBuffer();
        while(x != cabeza){
            a.append(x.imprimir());
            if(x.getAceptacion()){
                a.append("\1 (1) \n");
            }else{
                a.append("\1 (0) \n");
            }
           x = x.getLigaDer();
        }
        System.out.println(a.toString());
        return a;
    }
    
    //probado
    public Estado buscarEstado(String e){
        Estado x = primerElemento();
        while(x != cabeza){
            
            if(x.getEstado().equals(e)){
                return x;
            }
            
            x = x.getLigaDer();
        }
        
        return null;
    }
    
    
    //probado
    public void insertarEstado(Estado e){ // inserción al final
        if(!e.esDeterministico()){
                esDeterministico = false;
        }
        if(!esVacio()){ // si No es vacia
            e.setLigaDer(cabeza.getLigaIzq());
            e.setLigaDer(cabeza.getLigaIzq().getLigaDer());
            cabeza.getLigaIzq().setLigaDer(e);
            cabeza.setLigaIzq(e);
        }else{ // caso contrario
            cabeza.setLigaDer(e);
            cabeza.setLigaIzq(e);
            e.setLigaDer(cabeza);
            e.setLigaIzq(cabeza);
        }
        
    }
    
    public int crearEstado(String estado){
        if(!listaEstados.contains(estado)){
            listaEstados.add(estado);
            Estado e = new Estado();
            e.setEstado(estado);
            insertarEstado(e);
            
            return 0;
        }
        return 1;
        

    }
    
    public int crearTransiciones(Produccion p){
        Estado x = buscarEstado(p.getCabeza());
        if(x != null){
            if(!listaSimbolos.contains(p.primerElemento().getDato())){
                listaSimbolos.add(p.primerElemento().getDato());
            }
            x.crearTransicion(p.ultimoElemento().getDato(), p.primerElemento().getDato());
            x.imprimirEstado();
            return 0;
        }
        return 1;
        
    }
    
    public void generarAutomata(Gramatica g){
        Produccion p = g.primerProduccion();
        
        while(p != g.getCabeza()){
            if(p.esNulo()){
                crearEstado(p.getCabeza());
                buscarEstado(p.getCabeza()).setAceptacion(true);
            }else{
                crearEstado(p.getCabeza());
                crearTransiciones(p);
            }
            p = p.getLigaDer();
        }
        ordenarTransiciones(); 
    }
    
    
    //sin uso final
    public int conectar(Estado x, Estado estado){
        Estado t = primerElemento();
        while(t != cabeza){
            if( t == x){
                estado.setLigaIzq(t);
                estado.setLigaDer(t.getLigaDer());
                t.getLigaDer().setLigaIzq(estado);
                t.setLigaDer(estado);
                return 0;
            }
            t = t.getLigaDer();
        }
        return 1;
    }
    
    
    public Estado desconectar(Estado e){
        if(e != cabeza){
            e.getLigaDer().setLigaIzq(e.getLigaIzq());
            e.getLigaIzq().setLigaDer(e.getLigaDer());
            return e;
        }
        return null;
    }
    
    public void ordenarTransiciones(){
        Estado x = primerElemento();
        while(x != cabeza){
            
            x.ordenarTransiciones(listaSimbolos);
            x = x.getLigaDer();
        }
        
    }
    
    //sin uso final
    public void ordenarEstados(){
        Automata a = new Automata();
               
        for(int i = 0; i < listaEstados.size(); i++){
            Estado x = primerElemento();
            while(x != cabeza){
                if(x.getEstado().equals(listaEstados.get(i))){
                    a.insertarEstado(x);
                    x = x.getLigaDer();
                }else{
                    x = x.getLigaDer();
                }
            }
        }
        a.imprimir();
    }
    
    
    public boolean evaluarHilera(String hilera){
        Estado actual = primerElemento();
        for(int i = 0; i < hilera.length(); i++){
            String siguiente = actual.proximoEstado(hilera.charAt(i));
            if(siguiente  !=  null){
                actual = buscarEstado(siguiente);
            }else{
                return false;
            }
            
        }
        return actual.getAceptacion();
    }
    
    public void aDeterministico(){
        ArrayList<NodoA> listaND, nuevoND;
       
        nuevoND = new ArrayList<>();
        Estado x = primerElemento();
        while(!esDeterministico()){
            
            if(!x.esDeterministico()){ //
                Estado nuevo = new Estado();
                listaND = x.getListaND();
                nuevo.unirEstados(listaND);
                
                //parte 1.1: desconectar nodos no deterministicos y reemplazarlos con nodos deterministicos
                x.unirTransicionesND();
                //parte 2:llenado de nuevos estado no deterministicos
                
                for(int i = 0; i< listaND.size(); i++){
                    nuevo.crearTransicion(listaND.get(i).getEstado(), listaND.get(i).getSimbolo());
                }
                insertarEstado(nuevo);
               
            }
            x = x.getLigaDer();
        }
    }
    
   
}
