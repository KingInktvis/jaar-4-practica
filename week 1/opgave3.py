import math

n = 9
board = [
0, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 81,
0, 0 , 46, 45, 0 , 55, 74, 0 , 0,
0, 38, 0 , 0 , 43, 0 , 0 , 78, 0,
0, 35, 0 , 0 , 0 , 0 , 0 , 71, 0,
0, 0 , 33, 0 , 0 , 0 , 59, 0 , 0,
0, 17, 0 , 0 , 0 , 0 , 0 , 67, 0,
0, 18, 0 , 0 , 11, 0 , 0 , 64, 0,
0, 0 , 24, 21, 0 , 1 , 2 , 0 , 0,
0, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0]


def print_board():
    text = ""
    for i in range(n*n):
        if i % n == 0:
            text += '\n'
        tmp = str(board[i])
        if len(tmp) == 1:
            tmp += " "
        text += tmp + " "
    print(text)


def neighbours(index):
    tmp = []
    col = index % n
    row = math.floor(index / n)
    if col > 0:
        tmp.append(index-1)
    if row < n - 1:
        tmp.append(index+1)
    if row > 0:
        tmp.append(index-n)
    if row < n - 1:
        tmp.append(index+n)
    return tmp


hints = [1, 81]

start = board.index(hints[0])

print_board()
