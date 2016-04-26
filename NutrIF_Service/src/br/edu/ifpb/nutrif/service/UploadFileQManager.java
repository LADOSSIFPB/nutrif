package br.edu.ifpb.nutrif.service;

import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.edu.ladoss.enumeration.TipoArquivo;
import br.edu.ladoss.form.FileUploadForm;

/**
 * Serviço de upload de arquivo.
 * 
 * @author Rhavy
 *
 */
@Path("/arquivo")
public class UploadFileQManager {

	/**
	 * Upload dos arquivos do Projeto.
	 * 
	 * @param idProjeto
	 * @param form
	 * @return response
	 * @author Rhavy Maia Guedes.
	 */
	@PermitAll
	@POST
	@Path("/upload/perfil/{idprojeto}/{tipoarquivoprojeto}")
	@Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=UTF-8")
	@Produces("application/json")
	public Response uploadArquivoProjeto(
			@PathParam("idprojeto") String idProjeto,
			@PathParam("tipoarquivoprojeto") TipoArquivo tipoArquivoProjeto,
			@MultipartForm FileUploadForm form) {

		// Arquivo do projeto com extensão "pdf".
		ResponseBuilder builder = Response.status(Response.Status.NOT_MODIFIED);
		builder.expires(new Date());
		/*
		try {
			String nomeRealArquivo = form.getFileName();
			String extension = FilenameUtils.getExtension(nomeRealArquivo);

			if (extension.equalsIgnoreCase(FileUtil.PDF_FILE)) {

				// Nome do arquivo será o código do Projeto + CurrentTimeStamp
				// (milis).
				String nomeSistemaArquivo = FileUtil.getNomeSistemaArquivo(idProjeto,
						FileUtil.PDF_FILE);

				Projeto projeto = new Projeto();
				projeto.setIdProjeto(Integer.valueOf(idProjeto));

				Pessoa pessoa = new Pessoa();
				pessoa.setPessoaId(form.getIdPessoa());
							
				// Arquivo genérico.
				Arquivo arquivo = new Arquivo();				
				arquivo.setNomeRealArquivo(nomeRealArquivo);
				arquivo.setNomeSistemaArquivo(nomeSistemaArquivo);
				arquivo.setExtensaoArquivo(extension);
				arquivo.setCadastradorArquivo(pessoa);
				arquivo.setTipoArquivo(TipoArquivo.ARQUIVO_PROJETO);
				arquivo.setFile(form.getData());
				
				// Identificação do Arquivo de Projeto
				ArquivoProjeto arquivoProjeto = new ArquivoProjeto();
				arquivoProjeto.setArquivo(arquivo);
				arquivoProjeto.setProjeto(projeto);
				arquivoProjeto.setTipoArquivoProjeto(tipoArquivoProjeto);
				
				// Salvar no diretório
				FileUtil.writeFile(arquivo);				
				
				// Persistência do metadado do arquivo no banco de dados.	
				ArquivoProjetoDAO arquivoProjetoDAO = ArquivoProjetoDAO
						.getInstance();
				int idArquivoProjeto = arquivoProjetoDAO.insert(arquivoProjeto);

				if (idArquivoProjeto != BancoUtil.IDVAZIO) {
					
					arquivoProjeto.setIdArquivoProjeto(idArquivoProjeto);
					builder.status(Response.Status.OK).entity(arquivoProjeto);					
				}

			} else {

				MapErroQManager me = new MapErroQManager(
						CodeErroQManager.FORMATO_ARQUIVO_INVALIDO);
				Erro erro = me.getErro();
				builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
			}

		} catch (IOExceptionNutrIF | SQLExceptionQManager e) {

			Erro erro = e.getErro();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}
		*/

		return builder.build();
	}
}