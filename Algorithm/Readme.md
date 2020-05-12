# 文件说明
## 1.stopwords.txt

停用词表
## 2.Processor.java

用于读取文件的文本信息，切分出其中的单词，去重并统计词频，最后按照词频从大到小排序，并返回String[]
同时，提供cal_Importance()函数用于计算单词的在此时背诵的估值，估值越高，越需要背诵

# 思路说明
重要度：p = f * alpha + deltstate * beta  
f 为频率等级， deltstate为记忆阶段差  
alpha, beta 先取1  
每次对所有单词进行重要度计算 O(n)  
然后按照重要度排序 O(nlogn)  
按照用户需求，取出排名前k个单词，一遍背会的单词，state += 1, 没有背会的单词，state -= 1, state >= 9时视为背会了  
用户背完这k个单词以后，将单词及背诵情况存回列表  
