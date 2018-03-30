# Sample program to calculate TF

str_list = ["Una paloma blanca Im just a bird in the sky", 
"Una paloma blanca over the mountain I fly",
"Blue ridge mountains Shenandoah river",
"Im like a bird Ill only fly away",
"West Virginia Mountain mamma"]

tf={}

#calculate tf
for l in str_list:
    temp = l.split(" ")
    
    for i in range(len(temp)):
        temp[i]=temp[i].lower()
    
    for t in temp:
        if t in tf:
            count=tf[t]
            count+=1
            tf[t]=count
        else:
            tf[t]=1

#calculate df
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

N = len(documents)

#calculate idf
idf={}
    
    for d in df.keys():
        
        idf[d] = math.log(float(N)/df[d])

idf