import java.io.IOException;
import Input.File_Reader;

public class Main {

	public static void main(String[] args) throws IOException {
				
		String [] arquivo = {"images/imagem.nii"};
		
		File_Reader input = new File_Reader();
		
		input.lerArquivo_Array(arquivo);
		
		//input.lerArquivo_Matriz(arquivo);

	}
}

