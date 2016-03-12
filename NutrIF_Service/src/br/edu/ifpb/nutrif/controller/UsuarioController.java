package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.UsuarioDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Usuario;

@Path("usuario")
public class UsuarioController {

	/**
	 * Entrada: 
	 * {
	 * 	"nome":"[a-z]",
	 * 	"senha":"[a-z]"
	 * }
	 * 
	 * @param usuario
	 * @return
	 */
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Usuario usuario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.usuario(usuario);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				// Criptografar senha.
				String senhaCriptografada = StringUtil.criptografarBase64(
						usuario.getSenha());				
				usuario.setSenha(senhaCriptografada);
				
				// Gerar AuthKey.
				Date hoje = new Date();
				String key = StringUtil.criptografarSha256(hoje.toString());
				usuario.setKey(key);
				
				//Inserir o Aluno.
				Integer idUsuario = UsuarioDAO.getInstance().insert(usuario);
				
				if (idUsuario != BancoUtil.IDVAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(usuario);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getErro());
				
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException 
					exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						"");			
			}
		}				
		
		return builder.build();		
	}
	
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Usuario> getAll() {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		usuarios = UsuarioDAO.getInstance().getAll();
		
		return usuarios;
	}
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getUsuarioById(@PathParam("id") int idUsuario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Usuario usuario = UsuarioDAO.getInstance().getById(idUsuario); 
			
			builder.status(Response.Status.OK);
			builder.entity(usuario);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getErro());			
		}

		return builder.build();
	}
}
