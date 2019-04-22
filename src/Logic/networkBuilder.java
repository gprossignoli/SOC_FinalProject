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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class networkBuilder {
	

	
	public static Map<String,Airport> read(String nodesPath, String edgesPath) throws IOException{
		Map<String,Airport> airports = new HashMap<>();
		try {
			File nodesFile = new File (nodesPath);
			InputStream input = new FileInputStream(nodesFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			br.readLine(); //skips the first line to avoid the first
                            // currentLine which has the names of the attributes in the file
            String currentLine;
			while((currentLine = br.readLine()) != null){
                String[] p = currentLine.split(",");// a CSV has comma separated lines
                Airport item = new Airport();
                item.setLabel(p[0]);//this is the first column of the csv file
                item.setCountry(p[2]);
                item.setIcao(p[4]);
                item.setIata(p[3]);
			    airports.put(p[3],item);
            }
			br.close();


			} catch (FileNotFoundException e) {
		        throw new FileNotFoundException("nodes file not founded");
			}
			catch (IOException e){
		        throw new IOException("problems with nodes file");
            }
			
			// ------Edges
			try {
                File edgesFile = new File(edgesPath);
                FileReader fr = new FileReader(edgesFile);
                BufferedReader br = new BufferedReader(fr);
                String currentLine;

                br.readLine(); //skips the first line to avoid the first
                // currentLine which has the names of the attributes in the file

                //adding neighbors info from the edgesFile
                while ((currentLine = br.readLine()) != null) {
                    String[] line = currentLine.split(",");
                    Airport a;
                    if((a = airports.get(line[0])) != null)
                        a.addNeighbor(line[1]);
                }

               //eliminates the isolated nodes because they just add noise to the results
               airports = airports.entrySet()
                       .stream()
                       .filter(x -> x.getValue().getNeighbors().size() > 0)
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            catch (FileNotFoundException e) {
                throw new FileNotFoundException("edge file not founded");
			}
            catch (IOException e){
                throw new IOException("problems with edge file");
            }
		return airports;
	}
	

	
	
}
