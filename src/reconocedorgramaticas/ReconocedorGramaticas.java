/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the  template in the editor.
 */
package reconocedorgramaticas;

/**
 *
 * @author andres.cortesg
 */
public class ReconocedorGramaticas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ventana1 v = new Ventana1();
        v.setVisible(true);
        // TODO code application logic here
        
        // --------------- AREA DE PRUEBAS E INICALIZACIÓN ------------------
        
        /**
         * Notas de pruebas
         * -Terminado funcionalidad de gramaticas
         * -falta terminar metodos base de Automata y Estado (completado)
         * -métodos para simplificación
         * -métodos para conversion ND-D
         */
        
        
        /*
        Estado a = new Estado();
        a.setEstado("A");
        a.crearTransicion("A", "1");
        a.crearTransicion("B", "2");
        a.crearTransicion("C", "3");
        
        System.out.println(a.imprimir());
        System.out.println(a.esDeterministico());
        a.crearTransicion("D", "1");
        System.out.println(a.imprimir());
        System.out.println(a.esDeterministico());
        */
        

        Automata x = new Automata();

        Gramatica g = new Gramatica();
        
        g.crearProduccion("<S> = a<A>");
        g.crearProduccion("<S> = a<B>");
        g.crearProduccion("<S> = b<S>");
        g.crearProduccion("<A> = a<S>");
        g.crearProduccion("<A> = b<A>");
        g.crearProduccion("<A> = &");
        g.crearProduccion("<B> = &");
        
        g.caso1();
        g.caso2();
        g.caso3();

//        Automata x = new Automata();
//
//        Gramatica g = new Gramatica();
//        
//        g.crearProduccion("<S> = a<A>");
//        g.crearProduccion("<S> = b<S>");
//        g.crearProduccion("<A> = a<S>");
//        g.crearProduccion("<A> = b<A>");
//        g.crearProduccion("<A> = &");
//        
//        g.caso1();
//        g.caso2();
//        g.caso3();

        
       
        g.imprimirGramaticaFormal();
        
        
        //este metodo genera el automata
        x.generarAutomata(g);
        
        x.ordenarTransiciones();
        
        //si quiere obtener los datos para imprimir el la tabla, recorra el automata manualmente
        //y llame el metodo imprimir() de cada estado para obtener la fila
        x.imprimir();
        
        //este metodo devuelve true si aprueba la hilera
        System.out.println(x.esDeterministico());

//        g.imprimirGramaticaFormal();
//        
//        
//        //este metodo genera el automata
//        x.generarAutomata(g);
//        
//        x.ordenarTransiciones();
//        
//        //si quiere obtener los datos para imprimir el la tabla, recorra el automata manualmente
//        //y llame el metodo imprimir() de cada estado para obtener la fila
//        x.imprimir();
//        
//        //este metodo devuelve true si aprueba la hilera
//        System.out.println(x.evaluarHilera("abba"));

        
        x.aDeterministico();
        x.imprimir();
    }
    
}
