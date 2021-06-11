/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findassociationrule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class Apriori {
    private float minconf;
    private Hashtable<String,Integer> listSupOfItemSet;
    private ArrayList<String> frequentItemSet;
    private int supFrequentItemSet; //tần xuất của cái taaoj frequentItemSet này xuất hiện trong data
    private ArrayList<ArrayList<String>> listNotRule;
    private ArrayList<Rule<ItemSet,ItemSet,Float>> listRule; //viết luật => in ra
    
    public Apriori(float minconf,Hashtable<String,Integer> listSupOfItemSet, ArrayList<String> frequentItemSet){
        this.minconf = minconf;
        this.listSupOfItemSet = listSupOfItemSet;
        this.frequentItemSet = frequentItemSet;
        this.supFrequentItemSet = this.listSupOfItemSet.get(this.frequentItemSet.toString());
        this.listNotRule = new ArrayList<>();
        this.listRule = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        ExcelRead excelRead = new ExcelRead();
        ArrayList<ArrayList<String>> listItemSet = excelRead.readData("GroceryStoreDataSet.xlsx");
        
        FPTree tree = new FPTree(listItemSet);
        tree.constructFPTree();
    //    tree.printFPTree(tree.getRoot());
        
        float minsup = (float)0.008;
        
        FPGrowth fpgrowth = new FPGrowth(tree,minsup);
        fpgrowth.findFrequentItemSet();
        
        System.out.println("minsup = "+fpgrowth.getMinsup()+" ("+fpgrowth.getMinfreq()+")");
        System.out.println("number of transaction: "+fpgrowth.getNumbOfTransaction());
        
        Hashtable<String,Integer> listSupOfItemSet = fpgrowth.getListSupOfItemSet();
        ArrayList<ArrayList<String>> listFrequentItemset = fpgrowth.getListFrequentItemset();
        ArrayList<Rule<ItemSet,ItemSet,Float>> listRule = new ArrayList<>();
        
        float minconf = (float)0.4;
        int l = listFrequentItemset.size();
        for(int i=0; i<l; i++){
            ArrayList<String> itemset = listFrequentItemset.get(i);
            Apriori apriori = new Apriori(minconf,listSupOfItemSet,itemset);
            apriori.findRules();
           apriori.printResult();
            listRule.addAll(apriori.getListRule());
        }
        
        //Sap xep lai listRule theo conf
        Collections.sort(listRule, new Comparator<Rule<ItemSet,ItemSet,Float>>() {
            @Override
            public int compare(Rule o1, Rule o2) {
                float substract = (float)o1.getConf()-(float)o2.getConf();
                if(substract<0) return (1);
                else if(substract == 0) return 0;
                else return (-1);
            }
        });
        
        Apriori.printResult(listRule);
    }

    public ArrayList<Rule<ItemSet, ItemSet, Float>> getListRule() {
        return listRule;
    }
    
    public int[] init(int k){
        int[] combi = new int[k];
        for(int i=0; i<k; i++){
            combi[i] = i;
        }
        return combi;
    }
    public int[] genCombination(int k,int n,int[] combi){
        int i = k-1;
        while(i>=0 && combi[i] == n-k+i){
            i--;
        }
        if(i>=0){
            combi[i]++;
            for(int j=i+1; j<k; j++){
                combi[j] = combi[i]+ j - i;
            }
        }
        else return new int[0];
        return combi;
    }
    
    public void printarray(int[] arr){
        int l = arr.length;
        for(int i=0;i<l;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println("");
    }
    public ArrayList<String> getItemSet(int[] combi){
        ArrayList<String> itemset = new ArrayList<>();
        int l = combi.length;
        for(int i=0; i<l; i++){
            itemset.add(frequentItemSet.get(combi[i]));
        }
        return itemset;
    }
    
    public boolean isSubSet(ArrayList<String> itemset, ArrayList<String> superset){
        int k = 0;
        int l = itemset.size();
        int supersetlen = superset.size();
        if(l>=supersetlen){
            return false;
        }
        for(int i=0; i<l ; i++){
            while(k <supersetlen && itemset.get(i).compareTo(superset.get(k)) <0){
                k++;
            }
            if(k >=supersetlen || itemset.get(i).compareTo(superset.get(k)) > 0){
                return false;
            }
        }
        return true;
    }
    
    public int calculateSup(ArrayList<String> itemSet){
        String keyItemSet = itemSet.toString();
        int sup = listSupOfItemSet.get(keyItemSet);
        return sup;
    }
    
    public void addRule(ArrayList<String> itemSet,float sup,float conf){
        int l = frequentItemSet.size();
        int itemsetlen = itemSet.size();
        ArrayList<String> resultItemSet = new ArrayList<>();
        int  j = 0;
        for(int i=0; i<l; i++){
            if(j>=itemsetlen || frequentItemSet.get(i).compareTo(itemSet.get(j)) != 0 ){
                resultItemSet.add(frequentItemSet.get(i));
            }
            else {
                j++;
            }
        }
        ItemSet<String,Float> conditionItemSetObject = new ItemSet<>();
        ItemSet<String,Float> resultItemSetObject = new ItemSet<>();
        conditionItemSetObject.setItemset(itemSet.toString());
        conditionItemSetObject.setFreq(sup);
        resultItemSetObject.setItemset(resultItemSet.toString());
        resultItemSetObject.setFreq((float)supFrequentItemSet);
        Rule<ItemSet,ItemSet,Float> rule = new Rule();
        rule.setConditionItemSet(conditionItemSetObject);
        rule.setResultItemSet(resultItemSetObject);
        rule.setConf(conf);
        listRule.add(rule);
    }
    
    public void findRules(){
        int n = frequentItemSet.size();
        for(int k=n-1;k>=1;k--){ //xét từ cuối trong tập mhtx
            int[] combi = init(k);
            while(combi.length>0){ 
                ArrayList<String> itemSet = getItemSet(combi);
                boolean isRemoved = false;
                int l = listNotRule.size();
                for(int i=0; i<l ; i++){
                    if(isSubSet(itemSet,listNotRule.get(i))){
                        isRemoved = true;
                    }
                }
                if(!isRemoved){
                    int sup = calculateSup(itemSet);
                    float conf = (float)supFrequentItemSet/(float)sup;
                    if(conf >= minconf){
                        addRule(itemSet,sup,conf);
                    }
                    else{
                        listNotRule.add(itemSet);
                    }
                }
                combi = genCombination(k, n, combi);
            }
        }
    }
    
    public void printResult(){
        int l = listRule.size();
        for(int i=0; i<l; i++){
            System.out.println(listRule.get(i));
        }
    }
    
    public static void printResult(ArrayList<Rule<ItemSet,ItemSet,Float>> listRule){
        int l = listRule.size();
        for(int i=0; i<l; i++){
            System.out.println(listRule.get(i));
        }
    }
    
}
