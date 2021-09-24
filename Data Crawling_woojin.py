import re
from selenium import webdriver
from selenium.webdriver.support.select import Select


driver_path = 'C:/python38/chromedriver/chromedriver.exe'
URL = 'http://ccgc.chuncheon.go.kr/site/franchise/search_list.do'
save_path = 'C:/Users/wooji/PycharmProjects/crawling/data'
driver = webdriver.Chrome(driver_path) #드라이버 설정
driver.get(url=URL)
driver.set_window_position(0, 0) #크기 및 위치 조정
driver.set_window_size(1920, 1080)

#동 선택

for dong in range(40):
    dong_str = str(dong+1)
    select=Select(driver.find_element_by_id("fc-sch-area"))
    select.select_by_index(dong_str) #동 선택
    driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/div[1]/div[1]/fieldset/div[1]/div[2]/button').click() # 검색버튼 클릭
    driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/div[1]/div[2]/ul/li[2]/a').click() # 목록형으로 전환 버튼 클릭

    info_path = '/html/body/div[2]/div/div[2]/div/div[1]/div[3]/div/ul/'
    data_cnt = driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/div[1]/div[2]/h3/span').text #데이터 개수
    data_cnt = int(re.sub("\(|\)|\총|\개|\,|\ |","",data_cnt)) # 스플릿 및 정수화
    max_page_num = int(data_cnt / 12) +1 # 한페이지 12개 표시 12로 나누면 목록 개수
    # 페이지 돌면서 크롤링
    cur_cnt = 0 # 현재 데이터 저장 개수
    flag=0
    for page in range(max_page_num):
        page_num = str(page+1)
        driver.execute_script('fn_egov_select_franchiseList(' + page_num + ');return false; ')
        if flag==1: # 개수 초과시 탈출
            flag=0
            break
        for x in range(12):
            print(cur_cnt, data_cnt)
            if cur_cnt>=data_cnt:
                print("탈출")
                flag = 1
                break
            target_num = str(x+1)
            target_li = 'li[' + target_num + ']'
            hi = driver.find_element_by_xpath(info_path + target_li).text
            name = driver.find_element_by_xpath(info_path + target_li + '/span[1]').text # 상호명
            sector = driver.find_element_by_xpath(info_path + target_li + '/span[2]').text #업종
            telnum = driver.find_element_by_xpath(info_path + target_li + '/span[3]').text #전화번호
            address =  driver.find_element_by_xpath(info_path + target_li + '/span[4]').text #주소
            result = 'name:'+ name +' sector:'+sector+' telnum:'+telnum+' address:'+address + '\n'
            print(result)
            #파일 저장

            f = open(save_path + '/'+ dong_str +'.txt','a',encoding='UTF-8')
            f.writelines(result)
            f.close()
            cur_cnt+=1