package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.dao.RoleDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.Pessoa;
import br.edu.ladoss.entity.PessoaAcesso;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.enumeration.TipoRole;

@Path("funcionario")
public class FuncionarioController {

	/**
	 * Entrada: 
	 * {
	 * 	"nome":"[a-z]",
	 * 	"senha":"[a-z]"
	 *  "email":"[a-z]"
	 * }
	 * 
	 * @param pessoaAcesso
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(PessoaAcesso pessoaAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		// Validação dos dados de entrada.
		int validacao = Validate.inserirFuncionario(pessoaAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				// Criptografar senha.
				String senhaCriptografada = StringUtil.criptografarBase64(
						pessoaAcesso.getSenha());				
				pessoaAcesso.setSenha(senhaCriptografada);
				
				// Gerar AuthKey.
				Date hoje = new Date();
				String keyAuth = StringUtil.criptografarSha256(hoje.toString());
				pessoaAcesso.setKeyAuth(keyAuth);
				
				// Roles do Funcionário.
				List<Role> roles = RoleDAO.getInstance().getRolesByRolesId(
						pessoaAcesso.getRoles());
				pessoaAcesso.setRoles(roles);
				
				// Campus
				int idCampus = pessoaAcesso.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				pessoaAcesso.setCampus(campus);
				
				// Tipo Funcionário 
				pessoaAcesso.setTipo(Funcionario.TIPO_FUNCIONARIO);
				
				// Ativar Funacionário.
				pessoaAcesso.setAtivo(BancoUtil.ATIVO);
				
				//Inserir o Pessoa - Funcionário.
				Pessoa pessoa = pessoaAcesso.getPessoa();
				Funcionario funcionario = Funcionario.getFuncionario(pessoa);
				
				// Data de inserção do registro do funcionário.
				funcionario.setDataInsercao(hoje);
				
				Integer idFuncionario = FuncionarioDAO.getInstance()
						.insert(funcionario);
				
				if (idFuncionario != BancoUtil.ID_VAZIO) {					
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(pessoa);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
				
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException 
					exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));			
			}
			
		} else {
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
	
	
	/**
	 * Atualizar dados do funcionario.
	 * 
	 * 
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Funcionario funcionario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());		
		
		// Validação dos dados de entrada.
		int validacao = Validate.atualizaFuncionario(funcionario);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {				
				
				// Recuperar Senha e keyAuth.
				int idPessoa = funcionario.getId();
				Funcionario funcionarioAntigo = FuncionarioDAO.getInstance()
						.getById(idPessoa);
				
				if (funcionario != null) {
					
					String senha = funcionarioAntigo.getSenha();
					String keyAuth = funcionarioAntigo.getKeyAuth();				
					
					// Reestabelece a senha e chave de autenticação.
					funcionario.setSenha(senha);
					funcionario.setKeyAuth(keyAuth);
					
					// Roles do Funcionário.
					List<Role> roles = RoleDAO.getInstance().getRolesByRolesId(
							funcionario.getRoles());
					funcionario.setRoles(roles);
					
					// Data de atualização do registro do funcionário.
					Date hoje = new Date();
					funcionario.setDataInsercao(hoje);
					
					// Atualiza funcionário				
					Funcionario funcionarioAtualizado = FuncionarioDAO.getInstance()
							.update(funcionario);
					
					if (funcionarioAtualizado != null) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);					
						builder.entity(funcionarioAtualizado);
					}
				}
				
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			} 
		}				
		
		return builder.build();		
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Produces("application/json")
	public List<Funcionario> getAll() {
		
		List<Funcionario> usuarios = new ArrayList<Funcionario>();
		
		usuarios = FuncionarioDAO.getInstance().getAll();
		
		return usuarios;
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getUsuarioById(@PathParam("id") int idUsuario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Funcionario usuario = FuncionarioDAO.getInstance().getById(idUsuario); 
			
			builder.status(Response.Status.OK);
			builder.entity(usuario);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());			
		}

		return builder.build();
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/nome/{nome}")
	@Produces("application/json")
	public Response getByNome(@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		try {

			funcionarios = FuncionarioDAO.getInstance().listByNome(nome);
			
			builder.status(Response.Status.OK);
			builder.entity(funcionarios);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();		
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/migrar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response migrarFuncionarios(List<PessoaAcesso> pessoasAcesso){
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		try {

			for(PessoaAcesso pessoaAcesso: pessoasAcesso) {
				
				// Campus
				int idCampus = pessoaAcesso.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				pessoaAcesso.setCampus(campus);
				
				// Tipo Funcionário 
				pessoaAcesso.setTipo(Funcionario.TIPO_FUNCIONARIO);
				
				// Ativar Funacionário.
				pessoaAcesso.setAtivo(true);
				
				//Inserir o Pessoa - Funcionário.
				Pessoa pessoa = pessoaAcesso.getPessoa();
				Funcionario funcionario = Funcionario.getFuncionario(pessoa);
				Integer idFuncionario = FuncionarioDAO.getInstance().insert(
						funcionario);
				
				funcionarios.add(funcionario);
			}
			
			builder.status(Response.Status.OK);
			builder.entity(funcionarios);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();			
	}
}