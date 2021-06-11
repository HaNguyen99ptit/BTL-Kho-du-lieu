/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findassociationrule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 *
 * @author Administrator
 */
public class FPGrowth {

    /**
     * @param args the command line arguments
     */
    private ArrayList<String> listItemName;
    private Hashtable<String,ArrayList<Node>> headerTable; //bảng header 
    private float minsup; 
    private int minfreq;
    private int numbOfTransaction; // tổng số giao dịch
    private ArrayList<ArrayList<String>> listFrequentItemset; //kết quản là danh sách các tập mh thường xuyên
    private Hashtable<String,Integer> listSupOfItemSet; // sup cua cac mh

    //đầu vào của chuẩn bị khai thác cây là 1 cây với các thông tin thuộc tính của nó, và minsup
    public FPGrowth(FPTree tree,float minsup) {
        this.listItemName = tree.getListItemName();
        this.headerTable = tree.getHeaderTable();
        this.minsup = minsup;
        this.numbOfTransaction = tree.getListItemSet().size();
        this.minfreq = (int)Math.ceil(minsup*(float)numbOfTransaction);
        this.listFrequentItemset = new ArrayList<>();
        this.listSupOfItemSet = new Hashtable<>();
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<ArrayList<String>> listItemSet = listItemSet = thu.readDataA("Grocery.xlsx");
        FPTree tree = new FPTree(listItemSet);
        tree.constructFPTree();
    //    tree.printFPTree(tree.getRoot());
        
        float minsup = (float)0.05;
        FPGrowth fpgrowth = new FPGrowth(tree,minsup);
        
        fpgrowth.findFrequentItemSet(); //khai thác cây
        System.out.println("minsup = "+fpgrowth.getMinsup()+" ("+fpgrowth.getMinfreq()+")");
        System.out.println("number of transaction: "+fpgrowth.getNumbOfTransaction());
        fpgrowth.printResult(); //viết hàm in kết quả trong ddaay
    //    obj.printheaderTable();
        
    }
    
    
    public ArrayList<Integer> getListValue(ArrayList<Node> listNode){
        ArrayList<Integer> listValue = new ArrayList<>();
        for(Node node : listNode){
            listValue.add(node.getValue());
        }
        return listValue;
    }
    
    public void findFrequentItemSet(){
        int index = listItemName.size()-1; //
        ArrayList<String> itemset = null; //các item  
        ArrayList<Node> listNodeChecked = null; //node đã checked chưa
        ArrayList<Integer> listValue = null; // các count của các node bên trên , viết ra để cho vào hàm thui, bỏ qua cx ok nhá
        findFrequentItemSetRecursive(index, itemset, listNodeChecked, listValue);
    }
    
    public void findFrequentItemSetRecursive(int i,ArrayList<String> itemset,ArrayList<Node> listNodeChecked,ArrayList<Integer> listValue){
        for (int j = i; j>=0; j--){ //xét từng mặt hàng trong tập mặt hàng . bước này thưa,f vì có thể cây nó bị k có node đó??
            String itemname = listItemName.get(j);
            ArrayList<Node> listNodeNew = new ArrayList<>(); //tạo danh sách node mới 
            ArrayList<Integer> listValueNew =  new ArrayList<>(); //count ứng vs node trên
            if(listNodeChecked == null){ //node chưa kiêm tra bao giờ
                listNodeNew = headerTable.get(itemname); //lấy danh sách các node trong bảng header đều có tên itemname
                listValueNew = getListValue(listNodeNew); //cần gì tạo cái bảng này nhở, phí thế
                
            }else{
                int l = listNodeChecked.size(); 
                for(int k=0; k<l ;k++){ //xét tất các các   node trong bảng header đều có tên itemname
                    Node node = listNodeChecked.get(k); //lấy các node được kiểm tra
                    while(node.getName().compareTo(itemname)>0){
                        Node supernode = node.getSuperNode();
                        if(supernode.getName().equals("root_null")){ //xét lần đến gốc
                            break;
                        }
                        node = supernode; //nút gốc
                    }
                    if(node.getName().equals(itemname)){ //nếu đến nút gốc, tức là vẫn không thầy thằng  nào mà đứng sau mình
                        listNodeNew.add(node); //tạo list mới
                        listValueNew.add(listValue.get(k));
                    }
                }
            }
            
            
            int count = 0;
            int l = listNodeNew.size();
            for(int k =0; k<l; k++){
                count += listValueNew.get(k);
            }
        //    System.out.println(itemname+":"+count);
            if(count >= minfreq){ //lấy tấn xuất xh tối thiểu
                ArrayList<String> itemsetNew = null;
                if(itemset==null){
                    itemsetNew = new ArrayList<>();
                }else{
                    itemsetNew = (ArrayList<String>) itemset.clone();
                }
                itemsetNew.add(itemname);
                listFrequentItemset.add(itemsetNew);
                listSupOfItemSet.put(itemsetNew.toString(), count);
                findFrequentItemSetRecursive(j-1, itemsetNew, listNodeNew, listValueNew);
            }
        }
    }
    
    public void printResult(){
        int l = listFrequentItemset.size();
        for(int i=0 ; i<l; i++){
            String keyItemset = listFrequentItemset.get(i).toString();
            System.out.print(keyItemset);
            System.out.println("count = "+listSupOfItemSet.get(keyItemset));
        }
    }
    
    public Hashtable<String,Integer> getListSupOfItemSet(){
        return listSupOfItemSet;
    }

    public ArrayList<ArrayList<String>> getListFrequentItemset() {
        return listFrequentItemset;
    }

    public float getMinsup() {
        return minsup;
    }

    public int getMinfreq() {
        return minfreq;
    }

    public int getNumbOfTransaction() {
        return numbOfTransaction;
    }
    
    
}
