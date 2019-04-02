import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class networkBuilder {
	

	
	public static List<Airport> read(String pathNodos, String pathEnlaces) throws IOException{
		List<Airport> airports = new ArrayList<Airport>();
		try {
			
			
			
			File archivo = new File (pathNodos);
			InputStream input = new FileInputStream(archivo);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
				
			airports = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
			br.close();
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			// ------aristas
			
			File archivo = new File (pathEnlaces);
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while((linea=br.readLine())!=null){
				String []  lineas = linea. split(",");
				for(Airport a: airports){
					if(a.getIata().equals(lineas[0])){
						a.addNeighbor(lineas[1]);
					}
				}
			}
			br.close();
			
		return airports;
	}
	
	private static Function<String, Airport> mapToItem = (line) -> {
		String[] p = line.split(",");// a CSV has comma separated lines
		Airport item = new Airport();
		item.setLabel(p[0]);//<-- this is the first column in the csv file
		item.setCountry(p[2]);
		item.setIcao(p[4]);
		item.setIata(p[3]);
		return item;
	};
	
	
}
