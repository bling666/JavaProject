import requests
import csv
import sys
from  lxml import etree
from bs4 import BeautifulSoup
headers=['word','pronounce','voice','simple_mean','collins']
def parse(html,word):
    # class='ol'的 div的xpath
    #print(html)
    soup = BeautifulSoup(html)
    #print(soup.prettify())
    details_xpath='//*[@id="collinsResult"]/div/div/div/div/ul' 
    d_path2='//*[@id="phrsListTab"]/div[2]/ul/li'
    d_path1='//*[@id="phrsListTab"]/div[1]/'
    #print(d_path2)
    tree=etree.HTML(html)
    #print(len(tree.xpath(d_path2)))
    #print(dir(tree.xpath(details_xpath)[0]))
    # print(tree.xpath(details_xpath)[0].text)
    # //*[@id="collinsResult"]/div/div/div/div/ul/li[1]
    # 遍历下方的li节点
    k1=len(tree.xpath(d_path2))#基本释义
    k2=len(tree.xpath(details_xpath))#科林斯释义
    #print(k2)
    k3=0#音标
    k4=0#读音
    res={}
    res['word']=word
    pronstr=''
    for tag in soup.find_all('div',class_='baav'):
        k3=1
        m_p=tag.find_all('span',class_='pronounce')
        for pronoun in m_p:
            pr=pronoun.get_text()
            #print(type(pr))
            splitpr=pr.split('\n')
            #print(splitpr)
            tmplist=''
            for i in splitpr:
                if i!='':
                    if tmplist!="":
                        tmplist+='@'+i.strip()
                    else:
                        tmplist+=i.strip()
            if pronstr!='':
                pronstr+='#'+str(tmplist)
            else:
                pronstr+=tmplist
            #print(splitpr)
        p_voice=tag.find_all('a',class_='sp dictvoice voice-js log-js')
        if len(p_voice)!=0:
            k4=1
    #print(pronstr)
    if k3==1 and pronstr!='':
        res['pronounce']=pronstr
    else:
        res['pronounce']='$'
    if k4==1:
        res['voice']='+'
    else:
        res['voice']='-'
        #print(m_p)
    if k1!=0:
        simple_str=''
        for li in range(1,len(tree.xpath(d_path2))+1):
            li_xpath='//*[@id="phrsListTab"]/div[2]/ul/li['+str(li)+']'
            All=tree.xpath(li_xpath+'//text()')
            if simple_str=='':
                simple_str=simple_str+All[0]
            else:
                simple_str=simple_str+'#'+All[0]
        res['simple_mean']=simple_str
    else:
        res['simple_mean']='$'
    add_xpath='//*[@id="phrsListTab"]/div[2]/p//text()'
    add=tree.xpath(add_xpath)
    #pronun=tree.xpath(d_path1)
    #print(tree.xpath(d_path1))
    coll_str=''
    if k2!=0:
        for li in range(1,len(tree.xpath(details_xpath)[0])+1):
            #print(str(li)+'.')
            # 构造li节点的xpath
            li_xpath='//*[@id="collinsResult"]/div/div/div/div/ul/li['+str(li)+']'
            # 获取所有的test，直接用 strip() 方法
            _all=tree.xpath(li_xpath+'/div[1]/p//text()')
            string=''            
            for i in range(len(_all)):
                tmpstr=_all[i].replace("\n"," ")
                tmpstr=tmpstr.replace('\t','')
                tmpstr=tmpstr.strip()
                if tmpstr=='':
                    continue
                if string=='':
                    string+=tmpstr
                else:
                    string+=' '+tmpstr
            #print(string)
            #print(string)
            # 与上同理
            example_xpath=li_xpath+'/div[2]/div//text()'
            example_all=tree.xpath(example_xpath)
            example_str=''
            #example_all=[i.strip('\t') for i in example_all]
            for i in range(len(example_all)):
                tmp=example_all[i].replace("\n"," ")
                tmp=tmp.replace("\t","")
                tmp=tmp.strip()
                if example_str=='':
                    example_str+=tmp
                else:
                    example_str+=' '+tmp
            #print(example_str)
            if example_str!='':
                string+='@'+example_str
            if coll_str=='':
                coll_str+=string
            else:
                coll_str+='%'+string
        res['collins']=coll_str
    else:
        res['collins']='$'
    return res

def crawl(url):
    header = {
        'accept': "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
        'accept-encoding': "gzip, deflate",
        'accept-language': "zh-CN,zh;q=0.9,en;q=0.8",
        'cache-control': "no-cache",
        'connection': "keep-alive",
        'cookie':"DICT_UGC=be3af0da19b5c5e6aa4e17bd8d90b28a|; webDict_HdAD=%7B%22req%22%3A%22http%3A//dict.youdao.com%22%2C%22width%22%3A960%2C%22height%22%3A240%2C%22showtime%22%3A5000%2C%22fadetime%22%3A500%2C%22notShowInterval%22%3A3%2C%22notShowInDays%22%3Afalse%2C%22lastShowDate%22%3A%22Mon%20Nov%2008%202010%22%7D; ___rl__test__cookies=1588393306367; DICT_UGC=be3af0da19b5c5e6aa4e17bd8d90b28a|; OUTFOX_SEARCH_USER_ID=-1092315434@120.10.10.202; JSESSIONID=abchXp2gJiPY4-kcBvthx; OUTFOX_SEARCH_USER_ID_NCOO=276002431.0678465; _ntes_nnid=e207c9338bfa3488c9513ba80642ce9b,1588388551862; UM_distinctid=171d35f4f0a18e-0133761f794f84-c373667-1704a0-171d35f4f0b59e; user-from=http://youdao.com/w/threated/; from-page=http://youdao.com/w/threated/; tabRecord.examples=%23bilingual; tabRecord.webTrans=%23tEETrans; tabRecord.authTrans=%23collinsResult; search-popup-show=-1",
        'host': "youdao.com",
        'upgrade-insecure-requests': "1",
        'user-agent': "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36"
    }
    response = requests.request('GET', headers=header,url=url)
    response.encoding = response.apparent_encoding
    return response.text
def get_info(word):
    url = 'http://youdao.com/w/eng/' + word + '/'
    html=crawl(url)
    return parse(html,word)

if __name__ == '__main__':
    word = sys.argv[1]
    info=get_info(word)
    rows=[]
    rows.append(info)
    with open('res.csv','w',newline='',encoding='utf-8')as f:
        f_csv = csv.DictWriter(f,headers)
        f_csv.writeheader()
        f_csv.writerows(rows)
