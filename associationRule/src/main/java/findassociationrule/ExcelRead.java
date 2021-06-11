package findassociationrule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {
	public static void main(String[] args) throws IOException {
            
		/*
		 * Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
		 */
		String path = "Grocery.xlsx";
		ArrayList<ArrayList<String>> res = new ExcelRead().readData(path);
		for(ArrayList<String> itemset : res) {
                    System.out.println(itemset.toString());
		}
		
	}
        
        public ArrayList<Float> readTestInformation(String testPath){
            ArrayList<Float> testInfo = new ArrayList<>();
            File file = new File(testPath);
            try {
                    FileInputStream fis = new FileInputStream(file);
                    XSSFWorkbook workBook;
                    try {
                            workBook = new XSSFWorkbook(fis);
                            XSSFSheet sheet = workBook.getSheetAt(0);

                            Iterator<Row> rowIt = sheet.iterator();
                            if(rowIt.hasNext()){
                                Row row = rowIt.next();
                                Iterator<Cell> cellIterator = row.cellIterator();
                                Cell cell = cellIterator.next();
                                testInfo.add(Float.parseFloat(cell.toString()));
                                cell = cellIterator.next();
                                testInfo.add(Float.parseFloat(cell.toString()));
                            }
                            fis.close();
                    } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }

            } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return testInfo;
        }
        
        public ArrayList<Rule<ItemSet,ItemSet,Float>> readTest(String testPath){
            ArrayList<Rule<ItemSet,ItemSet,Float>> listRuleOfTest = new ArrayList<>();
            File file = new File(testPath);
            try {
                    FileInputStream fis = new FileInputStream(file);
                    XSSFWorkbook workBook;
                    try {
                            workBook = new XSSFWorkbook(fis);
                            XSSFSheet sheet = workBook.getSheetAt(0);

                            Iterator<Row> rowIt = sheet.iterator();
                            if(rowIt.hasNext()) rowIt.next();
                            while (rowIt.hasNext()) {
                                    Rule<ItemSet,ItemSet,Float> rule = new Rule<>();
                                    Row row = rowIt.next();
                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    while (cellIterator.hasNext()) {
                                            Cell cell = cellIterator.next();
                                            ItemSet baseItemset = new ItemSet();
                                            baseItemset.setItemset(cell.toString());
                                            cell = cellIterator.next();
                                            ItemSet addItemset = new ItemSet();
                                            addItemset.setItemset(cell.toString());
                                            cell = cellIterator.next();
                                            addItemset.setFreq(Float.parseFloat(cell.toString()));
                                            cell = cellIterator.next();
                                            rule.setConf(Float.parseFloat((cell.toString())));
                                            rule.setConditionItemSet(baseItemset);
                                            rule.setResultItemSet(addItemset);
                                    }
                                    listRuleOfTest.add(rule);
                            }
                            fis.close();
                    } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }

            } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return listRuleOfTest;
        }

	public ArrayList<ArrayList<String>> readData(String path) {

		/*
		 * Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
		 */
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
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
                                        sortItemSet(lst);
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
        
        public void sortItemSet(ArrayList<String> itemSet){
            Collections.sort(itemSet, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }

}
