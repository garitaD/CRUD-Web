/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * @author DGB
 */
public class UsuarioRepositorio {
    
    //READ 
    public List<Usuario> leerUsuarios(){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            Query query = conexion.createNamedQuery("Usuario.findAll");
            List<Usuario> usuarios = query.getResultList();
            return usuarios;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    public Usuario leerUsuario(String identificacion){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            Query query = conexion.createNamedQuery("Usuario.findByIdentificacion");
            query.setParameter("identificacion", identificacion);
            List<Usuario> usuarios = query.getResultList();
            
            if(usuarios.isEmpty())
                return null;
            else
                return usuarios.get(0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    //CREATE
    public Boolean crearUsuario(Usuario usuario){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            usuario.setIdUsuario(null);
            conexion.getTransaction().begin();
            conexion.persist(usuario);
            conexion.getTransaction().commit();
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.err.println(e);
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    //UPDATE
    public Boolean actualizarUsuario(Usuario usuario){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            conexion.getTransaction().begin();
            conexion.merge(usuario);
            conexion.getTransaction().commit();
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    //DELETE
    public Boolean eliminarUsuario(int idUsuario){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            conexion.getTransaction().begin();
            Usuario usuario = conexion.find(Usuario.class, idUsuario);
            //if(persona == null)
            //    return false;
            conexion.remove(usuario);
            conexion.getTransaction().commit();
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    //Validacion
    public static Usuario validarUsuario(String email, String identificacion){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            Query query = conexion.createNamedQuery("Usuario.findByIdentificacionAndEmail");
            query.setParameter("email", email);
            query.setParameter("identificacion", identificacion);
           // ;
            List<Usuario> usuarios = query.getResultList();
            
            if(usuarios.isEmpty())
                return null;
            else
                return usuarios.get(0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    
    ///COMPROBAR EMIAL
    public static boolean ajaxFormatoEmail(String correo){
        //Patrón para validar el correo
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher matcher = patron.matcher(correo);
        
        return matcher.find();
    }
     public static boolean validarNumeros(String identificacion){
        return identificacion.matches("[0-9]*");
    }
    public static boolean sonCaracteres(String dato){
        return dato.matches("[a-zA-Z]*");
    
    }
    
    public static Usuario existeIdentificacion(String identificacion){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            Query query = conexion.createNamedQuery("Usuario.findByIdentificacion");
           
            query.setParameter("identificacion", identificacion);
           // ;
            List<Usuario> usuarios = query.getResultList();
            
            if(usuarios.isEmpty())
                return null;//NO EXISTE
            else
                return usuarios.get(0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }
    
    public static Usuario existeEmail(String email){
        EntityManager conexion = ConexionHibernate.getEntityManagerFactory().createEntityManager();
        try{
            Query query = conexion.createNamedQuery("Usuario.findByEmail");
           
            query.setParameter("email", email);
           // ;
            List<Usuario> usuarios = query.getResultList();
            
            if(usuarios.isEmpty())
                return null;//NO EXISTE
            else
                return usuarios.get(0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally{
            conexion.close();
        }
    }

}
