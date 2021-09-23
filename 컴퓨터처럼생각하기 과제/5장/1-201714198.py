print('201714198정래원')
year = int(input('년도 입력 : '))

if((year%400==0) or (year%4==0 and year%100!=0)):
    print('%d은 윤년입니다.'%(year))
else:
    print('%d은 윤년이 아닙니다.'%(year))
if((2020-year)%4==0):
    print('%d은 올림픽이 열리는 해 입니다.'%(year))
if((2018-year)%4==0):
    print('%d은 월드컵이 열리는 해 입니다.'%(year))
