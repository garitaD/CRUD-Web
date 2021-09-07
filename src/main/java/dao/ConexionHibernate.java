/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author DGB
 */
public class ConexionHibernate {
    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory factory;
    
    public static EntityManagerFactory getEntityManagerFactory(){
        try{
            if(factory == null)
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            
            return factory;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }
    
    public static void shutdown(){
        if(factory == null)
            factory.close();
    }

}
