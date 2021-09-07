/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dao.Usuario;
import dao.UsuarioRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author DGB
 */
@Named(value = "usuariosControl")
@ViewScoped
public class UsuariosControl implements Serializable {

    /**
     * Creates a new instance of UsuariosControl
     */
    private List<Usuario> listaUsuarios;
    private Usuario usuario;
    UsuarioRepositorio repo = new UsuarioRepositorio();
    Usuario u = new Usuario();

    public UsuariosControl() {
        usuario = new Usuario();
    }

    public List<Usuario> getListaUsuario() {
        UsuarioRepositorio ad = new UsuarioRepositorio();
        listaUsuarios = ad.leerUsuarios();
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void limpiarUsuario() {
        usuario = new Usuario();
    }

    public void agregarUsuario() {
        UsuarioRepositorio ad = new UsuarioRepositorio();
        ad.crearUsuario(usuario);
    }

    public void crearusuario() {
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        UsuariosControl uc = new UsuariosControl();
        Usuario usuario = new Usuario();
        
        System.out.println(this.usuario.getNombre());
        usuario.setNombre(this.usuario.getNombre());
        usuario.setApellido1(this.usuario.getApellido1());
        usuario.setApellido2(this.usuario.getApellido2());
        usuario.setIdentificacion(this.usuario.getIdentificacion());
        usuario.setEmail(this.usuario.getEmail());
        
        usuarioRepositorio.crearUsuario(usuario);
    }

    public void modificarUsuario() {
        UsuarioRepositorio ad = new UsuarioRepositorio();
        ad.actualizarUsuario(usuario);
        limpiarUsuario();
    }

    public void eliminarUsuario() {
        UsuarioRepositorio ad = new UsuarioRepositorio();
        ad.eliminarUsuario(this.usuario.getIdUsuario());
        limpiarUsuario();
    }

    //Metodo que valida que exista el usuario y concuerde con la identificacion
    public String valida() {
        
        Usuario usuario = UsuarioRepositorio.validarUsuario(this.getUsuario().getEmail(), this.getUsuario().getIdentificacion());

        if (usuario != null) {

            return "UsuariosControl.xhtml";
        } else {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o "
                    + "contraseña inválidos.");
            FacesContext.getCurrentInstance().addMessage("loginForm:clave", msg);
            return "Login.xhtml";
        }
    }

    ///////////////////////PRUEBAS AJAX
    private String mensajeIdentificacion, mensajeCorreo, mensajeNombre, mensajeApellido1, mensajeApellido2;
    private Boolean bloquearBoton;

    public String getMensajeIdentificacion() {
        return mensajeIdentificacion;
    }

    public void setMensajeIdentificacion(String mensajeIdentificacion) {
        this.mensajeIdentificacion = mensajeIdentificacion;
    }

    public String getMensajeCorreo() {
        return mensajeCorreo;
    }

    public void setMensajeCorreo(String mensajeCorreo) {
        this.mensajeCorreo = mensajeCorreo;
    }

    public Boolean getBloquearBoton() {
        return bloquearBoton;
    }

    public void setBloquearBoton(Boolean bloquearBoton) {
        this.bloquearBoton = bloquearBoton;
    }

    public String getMensajeNombre() {
        return mensajeNombre;
    }

    public void setMensajeNombre(String mensajeNombre) {
        this.mensajeNombre = mensajeNombre;
    }

    public String getMensajeApellido1() {
        return mensajeApellido1;
    }

    public void setMensajeApellido1(String mensajeApellido1) {
        this.mensajeApellido1 = mensajeApellido1;
    }

    public String getMensajeApellido2() {
        return mensajeApellido2;
    }

    public void setMensajeApellido2(String mensajeApellido2) {
        this.mensajeApellido2 = mensajeApellido2;
    }
    
    public void ajaxValidarIdentificacion() {
      
        String identificacion=this.getUsuario().getIdentificacion();
        
        Usuario usuario = UsuarioRepositorio.existeIdentificacion(identificacion);

        if (UsuarioRepositorio.validarNumeros(identificacion)) {
            
            if (usuario!=null) {
                this.mensajeIdentificacion="Identificación ya existe";
                this.bloquearBoton = true;
                
            }else{
                this.mensajeIdentificacion ="";
                this.bloquearBoton=false;
            }
        }else{
            this.mensajeIdentificacion = "Solo se permite el ingreso de numeros";
            this.bloquearBoton = true;
        }

    }
    
    public void ajaxValidarEmail() {
        Usuario usuario = UsuarioRepositorio.existeEmail(this.getUsuario().getEmail());

        if (UsuarioRepositorio.ajaxFormatoEmail(this.getUsuario().getEmail())) {

            if (usuario != null) {
                this.mensajeCorreo = "Correo ya registrado";
                this.bloquearBoton = true;

            } else {
                this.mensajeCorreo = "";
                this.bloquearBoton = false;
            }

        } else {
            this.mensajeCorreo = "Correo no válido";
            this.bloquearBoton = true;
        }

    }
    
    public void ajaxValidarNombre(){
        String nombre = this.getUsuario().getNombre();
        if (!UsuarioRepositorio.sonCaracteres(nombre)) {
            this.mensajeNombre = "Nombre no válido";
            this.bloquearBoton = true;
        } else {
                this.mensajeNombre = "";
                this.bloquearBoton = false;
            }
        
    }
    
    public void ajaxValidarApellido1(){
        String apellido1 = this.getUsuario().getApellido1();
        if (!UsuarioRepositorio.sonCaracteres(apellido1)) {
            this.mensajeApellido1 = "Apellido no valido";
            this.bloquearBoton = true;
        } else {
                this.mensajeApellido1 = "";
                this.bloquearBoton = false;
            }
        
    }
    public void ajaxValidarApellido2(){
        String apellido2 = this.getUsuario().getApellido2();
        if (!UsuarioRepositorio.sonCaracteres(apellido2)) {
            this.mensajeApellido2 = "Apellido no válido";
            this.bloquearBoton = true;
        } else {
                this.mensajeApellido2 = "";
                this.bloquearBoton = false;
            }
        
    }
    
}
