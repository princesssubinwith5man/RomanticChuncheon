print('201714198정래원')
a = float(input('첫번째 과목의 학점을 입력하세요. : '))
if a >4.5:
    print('0과 4.5 사이의 값을 입력하세요.')
else:
    b = float(input('두번째 과목의 학점을 입력하세요. : '))
    if b >4.5:
        print('0과 4.5 사이의 값을 입력하세요.')
    else:
        c = float(input('세번째 과목의 학점을 입력하세요. : '))
        if c >4.5:
            print('0과 4.5 사이의 값을 입력하세요.')
        else:
            grade = (a+b+c)/3
            if grade == 4.5:
                print('학점은 %.1f이며, A+입니다.'%(grade))
            elif grade >= 4.0:
                print('학점은 %.1f이며, A입니다.'%(grade))
            elif grade >= 3.5:
                print('학점은 %.1f이며, B+입니다.'%(grade))
            elif grade >= 3.0:
                print('학점은 %.1f이며, B입니다.'%(grade))
            elif grade >= 2.5:
                print('학점은 %.1f이며, C+입니다.'%(grade))
            elif grade >= 2.0:
                print('학점은 %.1f이며, C입니다.'%(grade))
            else:
                print('학점은 %.1f이며, F입니다.'%(grade))

