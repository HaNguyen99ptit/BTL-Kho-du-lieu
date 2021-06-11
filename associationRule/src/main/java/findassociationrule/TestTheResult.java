/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findassociationrule;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class TestTheResult {
    public static void main(String[] args) {
        //PATH OF THE FILE TO APPLY ALGRITHM AND TEST
        String path = "C:\\Users\\Lenovo\\Documents\\Nam4_KI_2\\khodlvakhaiphadl\\btl\\roHang\\test.xlsx";
        // GET TEST RESULT FROM EXCEL TEST FILE 
      //  String testPath = getTestPath(path); // test file name
        ExcelRead excelread = new ExcelRead();
        ArrayList<Float> testInfo = excelread.readTestInformation(path);
        ArrayList<Rule<ItemSet,ItemSet,Float>> listRuleOfTest = excelread.readTest(path);
        
        // RUN ALGRORITHM
        // get minsup and min-conf form testInfo
        
        /*
		 * Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
		 */
          path = "GroceryStoreDataSet.xlsx";
        float minsup = testInfo.get(0);
        float minconf = testInfo.get(1);
        ArrayList<ArrayList<String>> listItemSet = excelread.readData(path);
        //tree
        FPTree tree = new FPTree(listItemSet);
        tree.constructFPTree();
        // fp growth
        FPGrowth fpgrowth = new FPGrowth(tree,minsup);
        fpgrowth.findFrequentItemSet();
        //apriori
        Hashtable<String,Integer> listSupOfItemSet = fpgrowth.getListSupOfItemSet();
        ArrayList<ArrayList<String>> listFrequentItemset = fpgrowth.getListFrequentItemset();
        ArrayList<Rule<ItemSet,ItemSet,Float>> listRule = new ArrayList<>();
        int l = listFrequentItemset.size();
        for(int i=0; i<l; i++){
            ArrayList<String> itemset = listFrequentItemset.get(i);
            Apriori apriori = new Apriori(minconf,listSupOfItemSet,itemset);
            apriori.findRules();
        //    apriori.printResult();
            listRule.addAll(apriori.getListRule());
        }
        
        // COMPARE TEST AND RESULT
        ArrayList<Integer> listRuleError = new ArrayList<>();
        ArrayList<Integer> listFreqError = new ArrayList<>();
        ArrayList<Integer> listConfError = new ArrayList<>();
        
        l = listRuleOfTest.size();
        System.out.println("SIZE OF LIST RULE OF TEST: " + l);
        System.out.println("SIZE OF LIST RULE OF RESULT : " + listRule.size() );
        for(int i=0; i<l; i++){
            Rule<ItemSet,ItemSet,Float> rule = listRule.get(i);
            Rule<ItemSet,ItemSet,Float> ruleOfTest = listRuleOfTest.get(i);
            String baseItemset = rule.getConditionItemSet().getItemset().toString();
            String addItemset = rule.getResultItemSet().getItemset().toString();
            float freq = (float)rule.getResultItemSet().getFreq();
            float conf = (float)rule.getConf();
            String baseItemsetTest = ruleOfTest.getConditionItemSet().getItemset().toString();
            String addItemsetTest = ruleOfTest.getResultItemSet().getItemset().toString();
            float freqTest = (float) ruleOfTest.getResultItemSet().getFreq();
            float confTest = (float) ruleOfTest.getConf();
            
            if(!baseItemset.equals(baseItemsetTest) || !addItemset.equals(addItemsetTest)){
                listRuleError.add(i);
            }
            
            if(freq != freqTest){
                listFreqError.add(i);
            }
            
            if(conf != confTest){
                listConfError.add(i);
            }
        }
        
        //PRINT TEST INFORMATION
        System.out.println("data: "+path);
        //System.out.println("test result: "+testPath);
        System.out.println("min_sup = " +minsup+"\nmin_confident = "+minconf);
        
        
        // PRINT ERROR COMPARE WITH TEST OF RESULT 
        System.out.println("RULE ERROR: "+ listRuleError.size());
        for(int i=0; i< listRuleError.size(); i++){
            int index = listRuleError.get(i);
            System.out.println("Test: " + listRuleOfTest.get(index));
            System.out.println("Result: "+ listRule.get(index));
        }
        System.out.println("");
        System.out.println("FREQUENT ERROR: "+ listFreqError.size());
        for(int i=0; i< listFreqError.size(); i++){
            int index = listFreqError.get(i);
            System.out.println("Test: " + listRuleOfTest.get(index));
            System.out.println("Result: "+ listRule.get(index));
        }
        System.out.println("");
        System.out.println("CONFIDENT ERROR: "+listConfError.size());
        for(int i=0; i< listConfError.size(); i++){
            int index = listConfError.get(i);
            System.out.println("Test: " + listRuleOfTest.get(index));
            System.out.println("Result: "+ listRule.get(index));
        }
        
        //PRINT RESULT
        System.out.println("LIST RULE OF RESULT:");
       // printResult(listRule);
        //PRINT TEST
        System.out.println("LIST RULE OF TEST:");
        for(Rule rule : listRuleOfTest){
            System.out.println(rule);
        }
    }
    
    public static String getTestPath(String path){
        int i=0;
        for (i=0; i<path.length(); i++){
            if(path.charAt(i) == '.'){
                break;
            }
        }
        String testPath = path.substring(0, i)+"_test.xlsx";
        return testPath;
    }
    
    
    public static void printResult(ArrayList<Rule<ItemSet,ItemSet,Float>> listRule){
        int l = listRule.size();
        for(int i=0; i<l; i++){
            System.out.println(listRule.get(i));
        }
    }
    
}
