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
public class Rule<K,V,CONF> {
    private K conditionItemSet;
    private V resultItemSet;
    private CONF conf;
    public Rule() {
    }

    public K getConditionItemSet() {
        return conditionItemSet;
    }

    public void setConditionItemSet(K conditionItemSet) {
        this.conditionItemSet = conditionItemSet;
    }

    public V getResultItemSet() {
        return resultItemSet;
    }

    public void setResultItemSet(V resultItemSet) {
        this.resultItemSet = resultItemSet;
    }

    public CONF getConf() {
        return conf;
    }

    public void setConf(CONF conf) {
        this.conf = conf;
    }
    
    public String toString(){
        return (conditionItemSet+" ---> "+resultItemSet+" confident="+conf);
    }
}
