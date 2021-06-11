/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findassociationrule;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class Node {
    private String name;
    private ArrayList<Node> listSubNode;
    private Node superNode;
    private int value; //biến count

    public Node(String name) {
        this.name = name;
        this.value = 1;
    }
    
    public void increValue() {
        this.value++;
    }
    
    public Node findSubNode(Node node){
        if(this.listSubNode != null){
            for(Node subnode : this.listSubNode){
                if(subnode.name.equals(node.name)){
                    return subnode;
                }
            }
        }
        return null;
    }
    
    public void addSubNode(Node node){
        if(this.listSubNode == null){
            this.listSubNode = new ArrayList<>();
        }
        Node subnode = this.findSubNode(node);
        if(subnode == null){
            subnode = node;
            subnode.setSuperNode(this);
            this.listSubNode.add(subnode);
        }
        else{
            subnode.increValue();
        }
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<Node> getListSubNode() {
        return listSubNode;
    }

    public void setListSubNode(ArrayList<Node> listSubNode) {
        this.listSubNode = listSubNode;
    }

    public Node getSuperNode() {
        return superNode;
    }

    public void setSuperNode(Node superNode) {
        this.superNode = superNode;
    }
    
}
