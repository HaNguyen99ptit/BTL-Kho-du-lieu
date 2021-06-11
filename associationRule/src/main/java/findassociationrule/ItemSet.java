/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findassociationrule;

/**
 *
 * @author Administrator
 */
public class ItemSet<K,V> {
    private K itemset;
    private V freq;

    public ItemSet() {
    }

    public K getItemset() {
        return itemset;
    }

    public void setItemset(K itemset) {
        this.itemset = itemset;
    }

    public V getFreq() {
        return freq;
    }

    public void setFreq(V sup) {
        this.freq = sup;
    }
    
    public String toString(){
        return (itemset+" freq="+freq);
    }
}
