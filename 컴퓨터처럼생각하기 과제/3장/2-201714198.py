import calendar

print('201714198정래원')
year = int(input('태어난 연도를 입력해주세요 : '))
month = int(input('태어난 월을 입력해주세요 : '))
print('%d년 %d월의 달력은 다음과 같습니다.' % (year,month))
calendar.prmonth(year,month)

