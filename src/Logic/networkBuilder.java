package Logic;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class networkBuilder {
	

	
	public static List<Airport> read(String nodesPath, String edgesPath) throws IOException{
		List<Airport> airports;
		try {
			File nodesFile = new File (nodesPath);
			InputStream input = new FileInputStream(nodesFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			//using skip(1) to avoid the first line which has the attributes names in the file
			airports = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
			br.close();


			} catch (FileNotFoundException e) {
		        throw new FileNotFoundException("nodes file not founded");
			}
			
			// ------Edges
			
			File edgesFile = new File (edgesPath);
			FileReader fr = new FileReader (edgesFile);
			BufferedReader br = new BufferedReader(fr);
			String line;
			//adding neighbors info from the edgesFile
			while((line=br.readLine())!=null){
				String []  lines = line. split(",");
				for(Airport a: airports){
					if(a.getIata().equals(lines[0])){
						a.addNeighbor(lines[1]);
					}
				}
			}
			br.close();
			
		return airports;
	}
	
	private static Function<String, Airport> mapToItem = (line) -> {
		String[] p = line.split(",");// a CSV has comma separated lines
		Airport item = new Airport();
		item.setLabel(p[0]);//this is the first column of the csv file
		item.setCountry(p[2]);
		item.setIcao(p[4]);
		item.setIata(p[3]);
		return item;
	};
	
	
}
