/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reconocedorgramaticas;


import java.util.*;
/** 
 *Lista para una gramatica
 * @author andres.cortesg elkin.garces
 */
public class Gramatica {
    private Produccion cabeza;
    private int nroProducciones;
    private boolean hayNulo;
    
/** 
*Constructor 
*Inicializamos el Numero de producciones de la gramatica en 0
*Se crea una nueva produccion
*/
    
    public Gramatica(){
       nroProducciones = 0;
       cabeza = new Produccion();
       cabeza.setLigaDer(cabeza);
       cabeza.setLigaIzq(cabeza);
       hayNulo = false; // bandera nolo para simplificacion
    }

/** 
*Encuentra el nodo cabeza de una lista que contiene las producciones
*
*@return nodo cabeza de una gramática
*/
    public Produccion getCabeza() {
        return cabeza;
    }
    
    public int getNroProducciones(){
        return nroProducciones;
    }
/** 
*Retorna la primera producción que se encuentra en la lista 
*@return nodo cabeza de la primera producción de una gramática
*/
    
    public Produccion primerProduccion(){
        return cabeza.getLigaDer();
    }
/** 
*Retorna la ultima producción que se encuentra en la lista 
*@return nodo cabeza de la ultima producción de la gramática
*/
    
    public Produccion ultimaProduccion(){
        return cabeza.getLigaIzq();
    }
/** 
*Verifica si el nodo que se pasa por parámetro es el primero de la gramática 
*@param x envia como parametro un nodo
*@return nodo cabeza de la ultima producción
*/
    public boolean esPrimero(Produccion x){
        return x == primerProduccion();
    }
    
/** 
*Verifica si el nodo que se pasa por parámetro es el ultimo de la gramática 
*@param x envia como parametro un nodo
*@return nodo cabeza de la ultima producción
*/
    public boolean esUltimo(Produccion x){
        return x == ultimaProduccion();
    }
    /** 
    *Verifica si la lista que representa una produccion esta vacia 
    *@return true cuando esta vacia,  false cuando la lista contiene elementos
    *
    */
    
    public boolean esVacia(){
        return primerProduccion() == cabeza;
    }
    /** 
    *Retorna la primera produción de una gramática
    *@return nodo que apunta hacia la lista donde esta almacenada una producción
    */
    public Produccion getProduccionInicial(){
        return cabeza.getLigaDer();
        
    }
/** 
*Metodo que recorre la lista que representa la Gramática y las sublistas que representan las producciones
*Se usa el metodo imprimir en consola para recorrer las sublistas que representan las producciones
*/
    
    public void imprimirGramatica(){
        Produccion x;
        x = cabeza.getLigaDer();
        while(x != cabeza){
            x.imprimirEnConsola();
            System.out.println("---------------------------------------------------------------------------");
            x = x.getLigaDer();
        }
    }
    
/** 
*Metodo para insertar una produccion
*@param produccion es una lista ligada 
*Se aumenta el numero de producciones en 1
*/
    
    public void insertarProduccion(Produccion produccion){
        if(esVacia()){
            produccion.setLigaDer(cabeza);
            produccion.setLigaIzq(cabeza);
            cabeza.setLigaDer(produccion);
            cabeza.setLigaIzq(produccion);
            nroProducciones = nroProducciones+1;
            return;
        }
        
        produccion.setLigaIzq(cabeza.getLigaIzq());
        produccion.setLigaDer(cabeza);
        cabeza.getLigaIzq().setLigaDer(produccion);
        cabeza.setLigaIzq(produccion);
        nroProducciones = nroProducciones+1;
    }
/** 
*Método para crear una producción a partir de un string
*Se verifica cada caracter del string y se valida que sea correcta la escritura de la producción
*@param linea es un String 
*@return 0 si la verificacion se lleva a cabo por completo si no hat errores o 1,2,3,4 si hay errores 
* 
*/
       
    public int crearProduccion(String linea){
        Produccion produccion = new Produccion(); //nueva produccion
        StringBuilder aux = new StringBuilder(); // buffer para el string a insertar
        boolean ladoDerecho = false; // verificador de lado derecho
        boolean inBounds; // dentro de los limites
        for(int i = 0; i< linea.length(); i++){
            if(linea.charAt(i) == '='){
                
            }
            
            inBounds = (i >= 0) && (i+1 < linea.length());
            switch(linea.charAt(i)){
                case 60: //no terminal '<'
                    
                    if(!inBounds || linea.charAt(i+1) == '>'){
                        return 1; // error 1: secuencia mal escrita
                    }
                    int x = i+1;
                    boolean inX = (x >= 0) && (x+1 < linea.length());
                    while(linea.charAt(x) != '>' ){
                        if(!inX){
                            return 1; // error 1
                        }
                        aux.append(linea.charAt(x));
                        x = x+1;
                    }
                    
                    i = x;
                    if(ladoDerecho == false){
                        
                        produccion.crearCabeza(aux.toString()); // crea no terminal lado izquierdo
                    }else{
                        produccion.crearElemento(aux.toString() ,0); //crea no terminal lado derecho
                    }
                    
                    break;
                case 61: // separador de lados '='
                    
                    if(produccion.cabezaVacia()){
                        return 2; // error 2: no tiene lado izquierdo
                    }else{
                        ladoDerecho = true;
                    }
                    
                    break;
                case 32: //ignora espacios 'space'
                    if(i == (linea.length()-1) ){
                        return 4; // error 4: no hay nada después del =
                    }
                    break;
                    
                case 'λ'://secuencia nula
                case '&'://secuencia nula, carácter reservado &
                    
                    if(produccion.getCantidadNT() == 0 && produccion.getCantidadT() == 0 && !inBounds){
                        aux.append(linea.charAt(i));
                        produccion.crearElemento(aux.toString(), 2); //crea terminal
                        insertarProduccion(produccion);
                        return 0;
                    }else{
                        return 3; // error 3: secuencia nula cuando tiene elementos en el lado derecho
                    }
                   
                    
                default:
                    if(inBounds){ // verifica que no tenga simbolos '>' antes de la secuencia nula
                        if(linea.charAt(i+1) == '>'){
                        return 1;
                        }
                    }
                    
                    aux.append(linea.charAt(i));
                    produccion.crearElemento(aux.toString(), 1); //crea terminal
                    
                    break;
            }
            aux = new StringBuilder();
        }
        if(produccion.esVacia()){
            return 4; //error 4: nada después del '='
        }
        insertarProduccion(produccion);
        return 0; // terminación normal
    }
    
/** 
*Método que detecta las producciones vivas y terminales vivos por definicion
*Se recorre la gramatica y las producciones para determinar 
*@return lista con las producciones vivas solo se guardan en la lista los nodo cabeza de las producciones
* 
*/
    
    
    public <String> List<String> detectarVivosPorDefinicion(){
        int NTvivos = 0;
        Produccion x= cabeza.getLigaDer();
        NodoP vivo = new NodoP();
        List lista = new ArrayList();   
        
        while(x != cabeza ){// NT vivos por definición
            
            if( (x.getCantidadNT() == 0 && x.getCantidadT() > 0) || x.esNulo()){  // Condicion para las producciones
                    
                  lista.add(x.getCabeza());
                  //System.out.println("N "+ x.getCantidadNT()+ "  TTT  "+x.getCantidadT()+ "aqui");   
            }
                   
            x = x.getLigaDer();
        }
    
            
      
        return lista;
    }
    
    public <String> List<String> detectarAlcanzablesPorDefinicion(){
        List lista = new ArrayList(); 
        lista.add(this.getProduccionInicial().getCabeza());
        NodoP a= this.getProduccionInicial().cabeza().getLigaDer();
        do{
            if(a.getModo() == 0 ){
                if(!lista.contains(a.getDato())){
                   System.out.println("dato a insertar:" + a.getDato() +" Modo:"+ a.getModo());
                    lista.add(a.getDato()); 
                }
            }
            a = a.getLigaDer();
        }while(a != this.getProduccionInicial().cabeza());
        return lista;
    }
    
    public boolean esNTAlcanzable(List listaAlcanzables, Produccion a){
        Produccion x = this.primerProduccion();
        while(x != cabeza){
            
            if(listaAlcanzables.contains(x.getCabeza())){
                if(x.buscarElemento(1, a.getCabeza())!=null ){
                    System.out.println("-- El dato "+a.getCabeza()+" existe --");
                    return true;
                }
              
            }
            x = x.getLigaDer();
        }
        System.out.println("-- El dato "+a.getCabeza()+" NO existe --");
        return false;
    }
    
    
/** 
*Método que detecta los terminales vivos por definicion
*Se recorre la producciones para determinar 
*@return true si el terminal examinado es vivo, falso de lo contrario
* 
*/
    
    public boolean esNTVivo(List listaVivos, Produccion x){ //detectar NT vivo
        NodoP a = x.primerElemento();
        while(a != x.cabeza()){
            if((!listaVivos.contains(a.getDato()) && a.getModo() == 0) ){
                return false;
            }
            
            a = a.getLigaDer();
        }
        
        return true;
    }
    
    
public NTListas detectarNTAlcanzables(){
        NTListas NTS = new NTListas();
        List NTAlcanzables = this.detectarAlcanzablesPorDefinicion();
        NTS.setNT1(NTAlcanzables);
        boolean hayNTAlcanzables = true; // hay NT vivos
        
        Produccion x = this.getProduccionInicial();
        
        while(hayNTAlcanzables){
            
            if(this.esNTVivo(NTAlcanzables, x) && !NTAlcanzables.contains(x.getCabeza())){ 
                
                NTAlcanzables.add(x.getCabeza());
                x = this.getProduccionInicial();
            }
            
            if(x == cabeza){
                hayNTAlcanzables = false;
            }
            
            x = x.getLigaDer();
        }
        NTAlcanzables.remove(NTAlcanzables.size()-1);
        
        
        x = this.getProduccionInicial();
        List NTInalcanzables = new ArrayList();
        
        while(x != cabeza){
            if(!NTAlcanzables.contains(x.getCabeza())){
                if(!NTInalcanzables.contains(x.getCabeza())){
                   NTInalcanzables.add(x.getCabeza()); 
                }
                
            }
            
            x = x.getLigaDer();
        }
        NTS.setNT0(NTInalcanzables);
        
        return NTS;
    }
        
/** 
*Método que detecta las producciones vivas y muertas
*Se recorre la producciones para determinar si es viva o no y agregar a la lista correspondiente
*@return NTS que representa un objeto de la clase  NTVivosMuertos donde se encuentran 2 listas una para cada tipo de terminal
* 
*/
    
    public NTListas detectarNTVivos(){
        
        NTListas NTS = new NTListas();
        List NTVivos = this.detectarVivosPorDefinicion();
        NTS.setNT1(NTVivos);
        boolean hayNTVivos = true; // hay NT vivos
        
        Produccion x = this.getProduccionInicial();
        
        while(hayNTVivos){
            
            if(this.esNTVivo(NTVivos, x) && !NTVivos.contains(x.getCabeza())){ 
                
                NTVivos.add(x.getCabeza());
                x = this.getProduccionInicial();
            }
            
            if(x == cabeza){
                hayNTVivos = false;
            }
            
            x = x.getLigaDer();
        }
        NTVivos.remove(NTVivos.size()-1);
        NTS.setNT1(NTVivos);
        
        x = this.getProduccionInicial();
        List NTMuertos = new ArrayList();
        
        while(x != cabeza){
            if(!NTVivos.contains(x.getCabeza())){
                if(!NTMuertos.contains(x.getCabeza())){
                   NTMuertos.add(x.getCabeza()); 
                }
                
            }
            
            x = x.getLigaDer();
        }
        NTS.setNT0(NTMuertos);
        
        return NTS;
    }
    
/** 
*Método para determinar si la gramatica es regular o no 
*Se recorre la producciones para determinar
*@return true si cumple para ser regular, falso de lo contrario.
* 
*/
    
    public boolean esRegular(){
        Produccion x = this.primerProduccion();
        while(x != cabeza){
            if( x.getCantidadNT() >1 ){
                return false;
            }
            NodoP a = x.primerElemento();
            boolean NT = false;
            while(a != x.cabeza()){
                if(a.getModo() == 0){ //hay más de un no terminal
                    NT = true;
                }
                if(NT && a.getModo() == 1){ //producción de la forma <A>a
                    return false;
                }
                
                a = a.getLigaDer();
            }
            x = x.getLigaDer();
        }
        
        return true;
    }

/** 
*Método para determinar si la gramatica es especial o no 
*Se recorre la producciones para determinar
*@return true si cumple para ser especial, falso de lo contrario.
* 
*/
    public boolean esFormaEspecial(){
        if(!esRegular()){
            return false;
        }
        Produccion x = this.primerProduccion();
        while(x != cabeza){
            if( (x.getCantidadNT() >1) || (x.getCantidadT() > 1) ){
                return false;
            }
            if(!x.esNulo() && x.getCantidadT() > 0 && x.getCantidadNT() == 0){
                return false;
            }
            x = x.getLigaDer();
        }
        
        return true;
    }
    
/** 
*Método para desconectar una produccion. 
*Se recorre la producciones hasta que se encuentre la que se desea desconectar
*
*/
    //desconecta producciones
    public void desconectar( Produccion nodo){
        
        if(nodo != cabeza){
            nroProducciones = nroProducciones -1;
            nodo.getLigaIzq().setLigaDer(nodo.getLigaDer());
            nodo.getLigaDer().setLigaIzq(nodo.getLigaIzq());
            return;
        }
        System.out.println("No se puede borrar el nodo cabeza");
    }
/** 
*Método para eliminar una produccion. 
*Se recorre la producciones hasta que se encuentre la que se desea eliminar
*
*/
    
    public boolean eliminarProduccion(String nombre){
        Produccion x = this.getProduccionInicial();
        boolean bandera = false;
        while(x != cabeza){
            if(x.getCabeza().equals(nombre)){
                desconectar(x);
                bandera = true;
            }
            x = x.getLigaDer();
        }
        return bandera;
    }

    
    public boolean limpiarGramatica(String dato){
        Produccion x = this.getProduccionInicial();
        boolean bandera = false;
        while(x != cabeza){
            NodoP a = x.primerElemento();
            while(a != x.cabeza()){
                if(dato.equals(a.getDato())){
                    x.desconectar(a);
                    bandera = true;
                }
                a = a.getLigaDer();
            }
            x = x.getLigaDer();
        }
        return bandera;
    }
    
    
    public void caso1(){ // caso 1 de simplificación de gramaticas : solo terminales
       Produccion x = this.primerProduccion();
       List listaR = new ArrayList();
       while(x != cabeza){
           if(x.getCantidadNT() == 0 && x.getCantidadT() > 0){
               //bucle
               NodoP a = x.primerElemento();
               if(x.getCantidadT() == 1 && x.getCantidadNT() == 0 && !hayNulo){ // solo 1 terminal - agregue nulo
                   if(!hayNulo){
                       
                       this.crearProduccion("<"+x.getCabeza()+">="+x.primerElemento().getDato()+"<nulo>");
                       
                        hayNulo = true;
                        x.crearElemento("nulo", 0);
                        this.crearProduccion("<nulo>=&");  
                   }else{
                        x.crearElemento("nulo", 0);
                   }
                   
               }
               
               if(x.getCantidadT() > 1 && x.getCantidadNT() == 0){ // más de 1 terminal - se sigque un proceso recursivo
                   while(a != x.cabeza()){ // agregar elementos sobrante en lista
                       
                       listaR.add(a.getDato());
                       
                       a = a.getLigaDer();
                   }
                   
                   List ultimo = casoXRec(listaR, x.getCabeza()); //recursión acá, retorna el último elemento
                   String u = ultimo.get(0).toString();
                   
                   this.crearProduccion("<"+u+">="+u+"<nulo>"); // agrega última producción
                   if(!hayNulo){
                        hayNulo = true;
                        x.crearElemento("nulo", 0);
                        this.crearProduccion("<nulo>=&");  
                   }else{
                        x.crearElemento("nulo", 0);
                   }
                   
                   //System.out.println("se esta imprimiendo la lista");
                   //System.out.println(listaR);
                   
                   
               }
               this.desconectar(x); //elimina la producción al final
           }
           x = x.getLigaDer();
       }
    }
    
    public void caso2(){ // caso 2 de simplificación : más de 1 terminal
        Produccion x = this.primerProduccion();
        List listaR = new ArrayList();
        while(x != cabeza){
            
            if(x.getCantidadT() >1 && x.getCantidadNT() == 1){ // condición para caso 2
                //llenado de lista para casoXRec()
                NodoP a = x.primerElemento();
                while(a != x.cabeza()){
                    listaR.add(a.getDato());
                    a = a.getLigaDer();
                }
                listaR.remove(0);

                String NT = String.join("", listaR); // concatena todos los elementos de la lista
                this.crearProduccion("<"+x.getCabeza()+">="+x.primerElemento().getDato()+"<"+NT+">");
                
                casoXRec(listaR, NT); //recursión 
                this.desconectar(x);  //elimina la producción al final
                
            }
            x = x.getLigaDer();
        }
    }
    
    public void caso3(){ // caso 3 de simplificación : solo un No terminal
        Produccion x = this.getProduccionInicial();
        
        while(x != cabeza){
            
            if(x.getCantidadNT() == 1 && x.getCantidadT() == 0){ // condición del caso 3
                Produccion y = this.getProduccionInicial();
                while(y != cabeza){
                    if( y.getCabeza().equals(x.primerElemento().getDato()) ){
                        this.crearProduccion("<"+x.getCabeza()+">="+y.getLadoDerecho());
                    }
                    y = y.getLigaDer();
                }
                this.desconectar(x);
            }
            x = x.getLigaDer();
        }
    }
    
    public List casoXRec(List lista, String NTAnterior){ // método para simplificacion de casos 1 y 2
        //recursivo
        //System.out.println(lista);
        if(lista.size() == 1){ // caso base
            System.out.println("Lista restante"+lista);
            return lista;
        }
        
        StringBuilder NT = new StringBuilder();
        for(int i = 1; i < lista.size(); i++){ // concatena lo demás
            NT.append(lista.get(i));
        }
        this.crearProduccion("<"+NTAnterior+"> ="+lista.get(0)+"<"+NT+">"); //agrega produccion
        System.out.println(NT);
        lista.remove(0);
        
        return casoXRec(lista, NT.toString());
    }
/** 
*Método para simplificar una gramatica. 
*Se recorre la producciones para eliminar las producciones muertas
*
*/
    public boolean limpiarMuertos(){
        NTListas NTVM = this.detectarNTVivos();
        
        //bucle para eliminar producciones muertas
        Produccion x = this.getProduccionInicial();
        if(!NTVM.getNT0().isEmpty()){
        for(int i = 0; i < NTVM.getNT0().size(); i++){
            System.out.println("Terminales inalcanzables:"+NTVM.getNT0().get(i));
            eliminarProduccion((String) NTVM.getNT0().get(i));
            limpiarGramatica((String) NTVM.getNT0().get(i));
            
            }
        return true;
        }
        return false;
        
    }
    
    public void limpiarInalcanzables(){
        NTListas NTVM = this.detectarNTAlcanzables();
        
        //bucle para eliminar producciones Inalcanzables
        Produccion x = this.getProduccionInicial();
        for(int i = 0; i < NTVM.getNT0().size(); i++){
            System.out.println("Terminales inalcanzables:"+NTVM.getNT0().get(i));
            eliminarProduccion((String) NTVM.getNT0().get(i));
            limpiarGramatica((String) NTVM.getNT0().get(i));
        }
    }
    
    //falta NT inalcanzables
    public void simplificarGramatica(){
        caso1();
        caso2();
        caso3();

    }
    
/** 
*Método para imprimir gramatica de forma natural. 
*Se recorre la gramatica y se determina que tipo de elemneto es para llevarlo a su escritura formal como se ve en los libros
* El metodo imprimirEnString recorre cada produccion y la convierte en un string
*@return un string que contiene los elemento de una produccion
*/
    public StringBuilder imprimirGramaticaFormal(){
        Produccion x;
        x = cabeza.getLigaDer();
        StringBuilder linea =new StringBuilder();
        
        while(x != cabeza){
            linea.append(x.imprimirEnString()).append("\n");
            x = x.getLigaDer();
        }
    return linea;
            }
    
    
    
    
}
