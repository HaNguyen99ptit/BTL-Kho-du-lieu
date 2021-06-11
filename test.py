#!/usr/bin/env python
# coding: utf-8

# In[157]:





# In[327]:


# 0.008-0.4; 0.09-0.4
import pandas as pd
from mlxtend.frequent_patterns import association_rules
import math
from mlxtend.preprocessing import TransactionEncoder
import re
#Market_Basket_Optimisation.xlsx GroceryStoreDataSet.xlsx Grocery.xlsx
path = "C:\\Users\\Lenovo\\Documents\\Nam4_KI_2\\khodlvakhaiphadl\\btl\\roHang\\Grocery.xlsx"
df= pd.read_excel(path , header = None, engine='openpyxl')

row = len(df)
print(row)

lst = []
for i in df.values:
    li = []
    for  j in i:
        if j.__class__.__name__ == 'str':
            li.append(j)
    lst.append(li)
            
te = TransactionEncoder()
te_array = te.fit(lst).transform(lst)
df = pd.DataFrame(te_array, columns=te.columns_)


sup = 0.009
conf = 0.9
frequent_itemsets_fp.to_excel("C:\\Users\\Lenovo\\Documents\\Nam4_KI_2\\khodlvakhaiphadl\\btl\\roHang\\xuat.xlsx") 



from mlxtend.frequent_patterns import fpgrowth
frequent_itemsets_fp=fpgrowth(df, min_support=sup, use_colnames=True)
rules_fp = association_rules(frequent_itemsets_fp, metric ="confidence", min_threshold=conf)


# In[328]:




cols = ['antecedents','consequents','support','confidence' ]
rules = rules_fp[cols]
#print(rules)

resultRules = []
resultRules_inString=[]
for index in rules.index:
    freq_itemset = []
    lstAntecedents = list(rules.loc[index,'antecedents'])
    lstAntecedents.sort(reverse=True)
    lstConsequents  = list(rules.loc[index, 'consequents']) 
    lstConsequents.sort(reverse=True)
    # bo itemsets
    freq_itemset = lstAntecedents + lstConsequents 
    freq_itemset.sort(reverse=True)
    freq_itemset.append("zzzzzz")
    
    support =(rules.loc[index,'support']*row)
    confidence = rules.loc[index,'confidence']
    
    tpl = (freq_itemset,lstAntecedents,lstConsequents,support,confidence)
    resultRules.append(tpl)
    

resultRules.sort(key=lambda x: (x[0], len(x[1]), x[1]), reverse=True)    
    
#print(resultRules)

for rule in resultRules:
    #print(rule)
    base_itemset = str(rule[1])
    add_itemset = str(rule[2])
    supp = rule[3]
    conff = rule[4]
    base_itemset = re.sub("[']","",base_itemset)
    add_itemset = re.sub("[']","",add_itemset)
    tpl = (base_itemset,add_itemset,supp,conff)
    resultRules_inString.append(tpl)
    #print(tpl)
    #print("\n")
for rule_instring in resultRules_inString:
    print(str(rule_instring[0]) +" --> "+ str(rule_instring[1]) +" sup="+ str(rule_instring[2]) +" conf="+str(rule_instring[3]))
    


# In[314]:


#xuat file
import xlsxwriter
test_path =  "C:\\Users\\Lenovo\\Documents\\Nam4_KI_2\\khodlvakhaiphadl\\btl\\roHang\\test.xlsx"
workbook = xlsxwriter.Workbook(test_path)
worksheet = workbook.add_worksheet("new_test")
r = 0
worksheet.write(r,0,sup)
worksheet.write(r,1,conf)
r+=1
for tpl in resultRules_inString:
    worksheet.write(r,0,tpl[0])
    worksheet.write(r,1,tpl[1])
    worksheet.write(r,2,tpl[2])
    worksheet.write(r,3,tpl[3])
    r+=1
workbook.close()

 



