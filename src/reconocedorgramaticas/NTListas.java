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
 * 
 * Clase para No terminales vivos y muertos / Alcanzables e inalzanzables
 */
public class NTListas {
    private List NT1, NT0;

   
    
    public NTListas(){

        NT1 = new ArrayList(); //vivos - Alcanzables
        NT0 = new ArrayList(); //muertos - Inalcanzables
        
    }


    public List getNT1() {
        return NT1;
    }

    public void setNT1(List NTVivos) {
        this.NT1 = NTVivos;
    }

    public List getNT0() {
        return NT0;
    }

    public void setNT0(List NTMuertos) {
        this.NT0 = NTMuertos;
    }
    
    
}
