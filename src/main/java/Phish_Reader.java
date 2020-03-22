import com.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Phish_Reader {
    private final String file;
    private static final String CSV_FILE_PATH = "./result.csv";
    private JSONParser parser = new JSONParser();

    public Phish_Reader(String fileName) {
        this.file = fileName;
    }

    private void writeToFile(){
        File outFile = new File(CSV_FILE_PATH);
        try {
            //creating file
            FileWriter output = new FileWriter(outFile);
            //creating csv with , as separators
            CSVWriter writer = new CSVWriter(output, ',',
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
            List<String[]> data = new ArrayList<>();

            //getting JSON object elements
            Object obj = parser.parse(new FileReader(file));
            JSONArray array = (JSONArray) obj;
            for(int i = 0; i < array.size(); i++){
                JSONObject jsonObj = (JSONObject) array.get(i);
                String name = jsonObj.get("name").toString();
                String lastRun = jsonObj.get("last_run").toString();
                String last_PPP = jsonObj.get("last_phish_prone_percentage").toString();

                String[] elementData = new String[]{name,lastRun,last_PPP};
                data.add(elementData);
            }

            writer.writeAll(data);
            writer.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
