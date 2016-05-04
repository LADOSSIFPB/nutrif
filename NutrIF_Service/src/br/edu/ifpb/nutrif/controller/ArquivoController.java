package br.edu.ifpb.nutrif.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.edu.ifpb.nutrif.dao.ArquivoDAO;
import br.edu.ifpb.nutrif.dao.PessoaDAO;
import br.edu.ifpb.nutrif.exception.IOExceptionNutrIF;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.FileUtil;
import br.edu.ifpb.nutrif.validation.ImageValidator;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Arquivo;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Pessoa;
import br.edu.ladoss.enumeration.TipoArquivo;
import br.edu.ladoss.form.FileUploadForm;

/**
 * Serviço de upload de arquivo.
 * 
 * @author Rhavy
 *
 */
@Path("/arquivo")
public class ArquivoController {

	private static ImageValidator imageValidator = new ImageValidator();
	
	/**
	 * Upload de arquivos.
	 * 
	 * @param idProjeto
	 * @param form
	 * @return response
	 * @author Rhavy Maia Guedes.
	 */
	@PermitAll
	@POST
	@Path("/upload/{tipoarquivo}")
	@Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=UTF-8")
	@Produces("application/json")
	public Response uploadArquivoProjeto(
			@PathParam("tipoarquivo") TipoArquivo tipoArquivo,
			@MultipartForm FileUploadForm form) {

		// Arquivo.
		ResponseBuilder builder = Response.status(Response.Status.NOT_MODIFIED);
		builder.expires(new Date());
		
		// Validação dos dados do arquivo.
		int validacao = Validate.uploadArquivo(tipoArquivo, form);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				String nomeRealArquivo = form.getFileName();
				String extension = FilenameUtils.getExtension(nomeRealArquivo);
				Integer idPessoa = form.getIdPessoa();

				if (imageValidator.validate(nomeRealArquivo)) {

					// Nome do arquivo
					String nomeSistemaArquivo = FileUtil.getNomeSistemaArquivo(
							idPessoa.toString(),
							extension);

					Pessoa pessoa = PessoaDAO.getInstance().getById(idPessoa);
					Date agora = new Date();
					
					// Arquivo genérico.
					Arquivo arquivo = new Arquivo();
					arquivo.setFile(form.getData());
					arquivo.setNomeRealArquivo(nomeRealArquivo);
					arquivo.setNomeSistemaArquivo(nomeSistemaArquivo);
					arquivo.setExtensaoArquivo(extension);
					arquivo.setTipoArquivo(tipoArquivo);
					arquivo.setRegistro(agora);
					arquivo.setSubmetedor(pessoa);
					
					// Salvar no diretório
					FileUtil.writeFile(arquivo);				
					
					// Persistência do metadado do arquivo no banco de dados.	
					int idArquivo = ArquivoDAO.getInstance().insert(arquivo);

					if (idArquivo != BancoUtil.IDVAZIO) {
						
						arquivo.setId(idArquivo);
						builder.status(Response.Status.OK);					
					}

				} else {
					
					builder.status(Response.Status.NOT_ACCEPTABLE);
				}

			} catch (IOExceptionNutrIF | SQLExceptionNutrIF e) {

				Error error = e.getError();
				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error);
			}
		}		

		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/download/{tipoarquivo}/nome/{nome}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("tipoarquivo") TipoArquivo tipoArquivo, 
			@PathParam("nome") String nomeSistemaArquivo) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		StreamingOutput stream = null;
		
		// Validação dos dados de entrada.
		int validacao = Validate.downloadArquivo(tipoArquivo, nomeSistemaArquivo);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			// Recuperar nome real.
			Arquivo arquivo = ArquivoDAO.getInstance().getByNomeSistema(
					nomeSistemaArquivo);
			
			if (arquivo != null) {
				
				final InputStream is = FileUtil.readFile(tipoArquivo,
						arquivo.getNomeSistemaArquivo());

				stream = new StreamingOutput() {

					public void write(OutputStream output) 
							throws IOException, WebApplicationException {

						try {
							
							output.write(IOUtils.toByteArray(is));
							
						} catch (Exception e) {
							
							throw new WebApplicationException(e);
						}
					}
				};
				
				// Arquivo para envio.
				builder.entity(stream)
					.status(Response.Status.OK)
					.type(MediaType.APPLICATION_OCTET_STREAM)
					.header("content-disposition", 
							"attachment; filename=\"" + nomeSistemaArquivo + "\"");
				
			} else {
				
				// Arquivo inexistente.
				builder.status(Response.Status.NOT_FOUND);
			}
		}
		
		return builder.build();
	}
}