/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serviciosWeb;

import dao.Usuario;
import dao.UsuarioRepositorio;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author DGB
 */
@Path("usuario")
@RequestScoped
public class UsuarioResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
    }
    
    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verUsuarios(){
        try{
            UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
            List<Usuario> listaUsuarios = usuarioRepositorio.leerUsuarios();
            JsonArrayBuilder arregloUsuarios = Json.createArrayBuilder();
            for(Usuario usuario : listaUsuarios){
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                JsonObject jsonObject = jsonObjectBuilder
                        .add("id", usuario.getIdUsuario())
                        .add("nombre", usuario.getNombre())
                        .add("apellido1", usuario.getApellido1())
                        .add("apellido2", usuario.getApellido2())
                        .add("identificacion", usuario.getIdentificacion())
                        .add("email", usuario.getEmail())
                        .build();
                arregloUsuarios.add(jsonObject);
            }
            JsonObjectBuilder jsonObjectBuilder2 = Json.createObjectBuilder();
            JsonObject jsonFinal = jsonObjectBuilder2.add("usuarios", arregloUsuarios).build();
            StringWriter jsonString = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(jsonString);
            jsonWriter.writeObject(jsonFinal);
            return Response.ok(jsonString.toString()).build();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            String resultado = "{\"error\": -1}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resultado).build();
        }
    }

    @POST
    @Path("/agregarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ingresarUsuario(String content){
        try{
            UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
            JsonObject jsonObject;
            JsonReader jsonReader = Json.createReader(new StringReader(content));
            jsonObject = jsonReader.readObject();
            Usuario usuario = new Usuario();
            usuario.setNombre(jsonObject.getString("nombre"));
            usuario.setApellido1(jsonObject.getString("apellido1"));
            usuario.setApellido2(jsonObject.getString("apellido2"));
            usuario.setIdentificacion(jsonObject.getString("identificacion"));
            usuario.setEmail(jsonObject.getString("email"));
            usuarioRepositorio.crearUsuario(usuario);
            return Response.ok("{\"operacionExitosa\": 1}").build();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            String resultado = "{\"error\": -1}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resultado).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public void eliminarUsuario(@PathParam("id") int id){
        try{
            UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
            usuarioRepositorio.eliminarUsuario(id);
           // return Response.ok("{\"operacionExitosa\": 1}").build();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            String resultado = "{\"error\": -1}";
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resultado).build();
        }
    }
    
}
