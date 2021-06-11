package findassociationrule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class thu {
	public static void main(String[] args) throws IOException {
		 
		/*
		 * Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
		 */
		String path = "GroceryStoreDataSet.xlsx";
		List<List<String>> res = readData(path);
		for(List<String> item : res) {
			for(String i: item) {
				System.out.println(i);
			}
		}
		
		 
	}

	public static List<List<String>> readData(String path) {

		/*
		 * Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
		 */
		List<List<String>> res = new LinkedList<List<String>>();
		File file = new File(path);
		try {
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workBook;
			try {
				workBook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workBook.getSheetAt(0);

				Iterator<Row> rowIt = sheet.iterator();

				while (rowIt.hasNext()) {
					List<String> lst = new LinkedList();
					Row row = rowIt.next();
					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						lst.add(cell.toString());
					}
					res.add(lst);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public static ArrayList<ArrayList<String>> readDataA(String path) {

		/*
		 * Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
		 */
		ArrayList<ArrayList<String>> res = new ArrayList ();
		File file = new File(path);
		try {
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workBook;
			try {
				workBook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workBook.getSheetAt(0);

				Iterator<Row> rowIt = sheet.iterator();

				while (rowIt.hasNext()) {
					ArrayList<String> lst = new ArrayList();
					Row row = rowIt.next();
					Iterator<Cell> cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						lst.add(cell.toString());
					}
					res.add(lst);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	 
}
