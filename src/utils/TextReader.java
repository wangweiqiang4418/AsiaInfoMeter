package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TextReader {
	
private ArrayList<String> context;
	
	public void fileRead(String filePath){
		try {
			BufferedReader br = new BufferedReader(new 
					InputStreamReader(new FileInputStream(filePath)));
			String rowkey = "";
			context = new ArrayList<String>();
			while((rowkey=br.readLine())!=null){
				context.add(rowkey);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public ArrayList<String> getContext() {
		return context;
	}

	
}
