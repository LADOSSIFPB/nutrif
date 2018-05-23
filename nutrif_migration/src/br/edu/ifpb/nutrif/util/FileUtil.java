package br.edu.ifpb.nutrif.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.IOExceptionNutrIF;
import br.edu.ladoss.entity.Arquivo;
import br.edu.ladoss.enumeration.TipoArquivo;

/**
 * Utils para manipulação de arquivos.
 * 
 * @author Rhavy
 */
public class FileUtil {

	private static Logger logger = LogManager.getLogger(FileUtil.class);

	public static String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static String USER_HOME = System.getProperties().getProperty("user.home");

	public static String BASE_PATH = USER_HOME + FILE_SEPARATOR + "nutrif_upload" + FILE_SEPARATOR;

	public static String PERFIL_PATH = BASE_PATH + "perfil";

	public static String PDF_FILE = "pdf";
	
	public static String PDF_PNG = "png";
	
	public static String PDF_JPEG = "jpeg";
	
	public static String PDF_JPG = "jpg";

	private static Map<TipoArquivo, String> diretorios = new HashMap<TipoArquivo, String>()  {{
	    put(TipoArquivo.ARQUIVO_FOTO_PERFIL, PERFIL_PATH);
	    put(TipoArquivo.ARQUIVO_REFEICAO_ALMOCO, PERFIL_PATH);
	}};
	
	/**
	 * Salvar os arquivo no diretório do servidor.
	 * 
	 * @param content
	 * @param filename
	 * @throws IOException
	 */
	public static void writeFile(Arquivo arquivo) throws IOExceptionNutrIF {

		byte[] content = arquivo.getFile();
		
		String nomeArquivo = arquivo.getNomeSistemaArquivo();
		
		TipoArquivo tipoArquivo = arquivo.getTipoArquivo();

		String diretorioArquivo = diretorios.get(tipoArquivo);

		File file = new File(diretorioArquivo + FILE_SEPARATOR + nomeArquivo);

		try {
			
			logger.info("Salvando o arquivo em disco: " + file.getName());
			
			if (!file.exists()) {
				
				file.createNewFile();
			}

			FileOutputStream fop = new FileOutputStream(file);
			fop.write(content);
			fop.flush();
			fop.close();
			logger.info("Arquivo " + file.getName() + " salvo com sucesso.");
			
		} catch (IOException e) {
			
			logger.error("Problema ao salvar o arquivo no sistema");
			throw new IOExceptionNutrIF(e.getMessage());
		}
	}
	
	public static InputStream readFile(TipoArquivo tipoArquivo, 
			String fileName) {
		
		InputStream in = null;
		
		String diretorioArquivo = diretorios.get(tipoArquivo);
		
		Path file = Paths.get(diretorioArquivo + FILE_SEPARATOR + fileName);
		
		try {
			
			in = Files.newInputStream(file);
				    
		} catch (IOException exception) {
		    
			logger.error(exception.getMessage());
		}		
		
		return in;
	}
	
	public static StreamingOutput readStreamingOutput(TipoArquivo tipoArquivo, 
			String fileName) {
		
		final InputStream is = FileUtil.readFile(tipoArquivo, fileName);

		StreamingOutput stream = new StreamingOutput() {

			public void write(OutputStream output) 
					throws IOException, WebApplicationException {

				try {
					
					output.write(IOUtils.toByteArray(is));
					
				} catch (Exception e) {
					
					throw new WebApplicationException(e);
				}
			}
		};
		
		return stream;		
	}

	public static String readBase64(TipoArquivo tipoArquivo, 
			String fileName) {
		
		String content = null;
		
		try {
			final InputStream is = FileUtil.readFile(tipoArquivo, fileName);

			byte[] bytes64bytes = Base64.encodeBase64(IOUtils.toByteArray(is));
			
			content = new String(bytes64bytes);
		
		} catch (IOException exception) {
			
		}
		
		
		return content;		
	}
	
	public static String getNomeSistemaArquivo(String prefix, String extension) {
		
		Date agora = new Date();
		
		String nomeSistemaArquivo = prefix + "-"
				+ Long.toString(agora.getTime()) + "." + extension;
		
		return nomeSistemaArquivo;
	}
}
