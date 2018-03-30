## Program to calculate TF-IDF

import math

documents=["How do we process information", 
"Information Retrieval is an interesting topic",
"An interesting discussion",
"Discussion on a psychological topic",
"Perhaps it is some sort of psychological trauma"]

def computeTermFrequency(documents):
    
    tf={}
    
    for d in documents:
        temp_d = d.split()
        
        for i in range(0,len(temp_d)):
            temp_d[i]=temp_d[i].lower()
        
        for t in temp_d:
            if t in tf:
                count=tf[t]
                count+=1
                tf[t]=count
            else:
                tf[t]=1

    return tf

def computeDocumentFrequency(documents, tf):
    
    df={}
    
    for key,value in tf.items():
        for d in documents:
            if key in d:
                if key in df.keys():
                    count = df[key]
                    count+=1
                    df[key] = count
            else:
                df[key] = 1
            
    return df

def computeInverseDocumentFrequency(documents, df):
    
    N = len(documents)
    idf={}
    
    for d in df.keys():
        
        idf[d] = math.log(float(N)/df[d])
    
    return idf

computeInverseDocumentFrequency(documents, computeDocumentFrequency(documents, computeTermFrequency(documents)))
