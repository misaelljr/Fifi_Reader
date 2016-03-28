package Input;

import java.io.IOException;
import java.io.PrintWriter;

import Nifi_Reader_Matrix.NiftiHeader;
import Nifi_Reader_Matrix.NiftiVolume;

public class File_Reader {

	String [] arquivo;

	//método para tratar leitura de arquivos
	public void lerArquivo_Matriz(String[] arquivo) throws IOException{
		this.arquivo = arquivo;

		if (arquivo.length == 0 || arquivo.length > 2)
		{
			System.out.println("Usage: niftijio input.nii.gz [output]");
			System.out.println("Description: read a volume and optionally write it out again");
			return;
		}


		NiftiVolume volume = NiftiVolume.read(arquivo[0]);

		int nx = volume.header.dim[1];
		int ny = volume.header.dim[2];
		int nz = volume.header.dim[3];
		int dim = volume.header.dim[4];

		if (dim == 0)
			dim = 1;

		if (arquivo.length == 1)
		{
			System.out.println("dims: " + nx + " " + ny + " " + nz + " " + dim);
			System.out.println("datatype: " + NiftiHeader.decodeDatatype(volume.header.datatype));
			volume.printMatrix();

		}

		else if (arquivo[1].endsWith("txt"))
		{
			PrintWriter out = new PrintWriter(arquivo[1]);

			out.println("volume ");
			out.println("dimensions:");
			out.println(nx + " " + ny + " " + nz + " " + dim);
			out.println("data:");
			
			for (int d = 0; d < dim; d++)
				for (int k = 0; k < nz; k++)
					for (int j = 0; j < ny; j++)
						for (int i = 0; i < nx; i++)
							out.println(volume.data[i][j][k][d]);

			out.println();
			out.close();
		}

		else
		{
			volume.write(arquivo[1]);
		}


	}

	//método para leitura e tratar pontos da imagem em um array unidimensional
	public void lerArquivo_Array(String[] arquivo) throws IOException{

		if (arquivo.length == 1 && "--help".equals(arquivo[0]))
		{
			System.out.println("Usage: niftijio input.nii.gz [output]");
			System.out.println("Description: read a volume and optionally write it out again");
			return;
		}
		if (arquivo.length == 0)
		{
			int nx = 100;
			int ny = 110;
			int nz = 120;
			int dim = 1;

			Nifi_Reader_Array.NiftiVolume volume = new Nifi_Reader_Array.NiftiVolume(nx, ny, nz, dim);
			int count = 0;
			for (int d = 0; d < dim; d++)
				for (int k = 0; k < nz; k++)
					for (int j = 0; j < ny; j++)
						for (int i = 0; i < nx; i++)
							volume.data.set(i,j,k,d,count++);
			volume.write("example.nii.gz");
		}
		else
		{

			Nifi_Reader_Array.NiftiVolume volume = Nifi_Reader_Array.NiftiVolume.read(arquivo[0]);

			int nx = volume.header.dim[1];
			int ny = volume.header.dim[2];
			int nz = volume.header.dim[3];
			int dim = volume.header.dim[4];   

			if (dim == 0)
				dim = 1;

			if (arquivo.length == 1)
			{
				System.out.println("dims: " + nx + " " + ny + " " + nz + " " + dim);
				System.out.println("datatype: " + NiftiHeader.decodeDatatype(volume.header.datatype));
				volume.data.getData();


			}
			else if (arquivo[1].endsWith("txt"))
			{
				PrintWriter out = new PrintWriter(arquivo[1]);

				out.println("volume ");
				out.println("dimensions:");
				out.println(nx + " " + ny + " " + nz + " " + dim);
				out.println("data:");

				for (int d = 0; d < dim; d++)
					for (int k = 0; k < nz; k++)
						for (int j = 0; j < ny; j++)
							for (int i = 0; i < nx; i++)
								out.println(volume.data.get(i,j,k,d));

				out.println();
				out.close();
			}
			else
			{
				volume.write(arquivo[1]);
			}
		}

	}

}
