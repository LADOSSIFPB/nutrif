package br.edu.ladoss.nutrif.validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class ImageValidator implements NutrIFValidator {
	
   private Pattern pattern;
   private Matcher matcher;
 
   private static final String IMAGE_PATTERN = 
                "([^\\s]+(\\.(?i)(jpg|jpeg|png))$)";
	        
   public ImageValidator(){
	  pattern = Pattern.compile(IMAGE_PATTERN);
   }
	  
  /**
   * Validate image with regular expression.
   * 
   * @param image image for validation
   * @return true valid image, false invalid image
   */
   @Override
   public boolean validate(final String image){
		  
	  matcher = pattern.matcher(image);
	  return matcher.matches();
	    	    
   }
}