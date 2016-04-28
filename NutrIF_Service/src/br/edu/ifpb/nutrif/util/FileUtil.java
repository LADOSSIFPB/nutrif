package br.edu.ifpb.nutrif.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	}};
	
	/**
	 * Salvar os arquivo no diretório do servidor.
	 * 
	 * @param content
	 * @param filename
	 * @throws IOException
	 */
	public static void writeFile(Arquivo arquivo)
			throws IOExceptionNutrIF {

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
	
	public static String getNomeSistemaArquivo(String prefix, String extension) {
		
		Date agora = new Date();
		
		String nomeSistemaArquivo = prefix + "-"
				+ Long.toString(agora.getTime()) + "." + extension;
		
		return nomeSistemaArquivo;
	}
}
