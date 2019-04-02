package com.game.uitilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataRead {

	public static List<String> readFile() {
		List<String> testDataList = new ArrayList<String>();
		FileReader fr;
		try {
			fr = new FileReader(System.getProperty("user.dir").replace("\\","/")+ "/src/test/resources/excel/TestData.txt");
			BufferedReader filereader = new BufferedReader(fr);
			// System.out.println(filereader.readLine());
			String str;
			while ((str = filereader.readLine()) != null) {
				testDataList.add(str);
			}
			fr.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return testDataList;
	}

}
