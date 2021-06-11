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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author Administrator
 */
//Mỗi cây sẽ có 1 bảng header
public class FPTree {
    public Node root;
    private Hashtable<String,ArrayList<Node>> headerTable; //bang header
    private ArrayList<String> listItemName; //tên các mặt hàng trong câ
    ArrayList<ArrayList<String>> listItemSet; //tập ban ghi giao dich = record
    public FPTree(ArrayList<ArrayList<String>> listItemSet) {
        this.listItemSet = listItemSet;
        this.root = null;
        this.headerTable = new Hashtable<>();
        this.listItemName = new ArrayList<>();
    }
    
    public void constructFPTree(){
        //Create HashTable
        Set<String> itemNameSet = new HashSet<String>();
        for(ArrayList<String> itemSet : listItemSet){
            if(root == null){
                root = new Node("root_null");
            }else{
                root.increValue();
            }
            Node node = root;
            
            for(String itemName : itemSet){
                Node subNode = new Node(itemName);
                node.addSubNode(subNode); //thêm node con vào sau cây
                node = node.findSubNode(subNode);//mất công tìm này===> đáng ra nên tạo một biến tạm thì hơn
                
          
                addNodeHeaderTable(node);
                itemNameSet.add(itemName);
            }
        }
        //chuyển giao dịch sắp theo tên của nó, code mình thì sắp theo sup theo thứ tự như header
        listItemName = new ArrayList(Arrays.asList(itemNameSet.toArray()));
        Collections.sort(listItemName, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }
    //thêm node vào header
    public void addNodeHeaderTable(Node node){
        String itemname = node.getName();  
        ArrayList<Node> listNode = headerTable.get(itemname);
        //lấy danh sách các node trong cây có tên như string trên
        if(listNode==null){
            listNode = new ArrayList<>();
            headerTable.put(itemname, listNode);
        }
        //xét các node trong sanh sách, nếu hai node bằng nhau, bằng nhau khi nào thì ít khi có
        for(Node n : listNode){
            if(node==n){
                return;
            }
        }
        listNode.add(node); //thêm vào danh sách bảng chỗ có tên là itemname
    }
    
    public void printFPTree(Node root){
        ArrayList<Node> listSubNode = root.getListSubNode();
        if(listSubNode == null || listSubNode.size()==0){
            System.out.print(root.getName()+":"+root.getValue()+"\n");
        }
        else {
            for(Node subNode : listSubNode){
                System.out.print(root.getName()+":"+root.getValue()+" -> ");
                printFPTree(subNode);
            }
        }
    }

    public Node getRoot() {
        return root;
    }

    public Hashtable<String, ArrayList<Node>> getHeaderTable() {
        return headerTable;
    }

    public ArrayList<String> getListItemName() {
        return listItemName;
    }

    public ArrayList<ArrayList<String>> getListItemSet() {
        return listItemSet;
    }
    
}
