import string
import random


class Board:
    def __init__(self, len):
        rows = []
        self.n = len
        for _ in range(len):
            row = []
            for _ in range(len):
                row.append(random_char())
            rows.append(tuple(row))
        self.grid = tuple(rows)

    def __str__(self):
        start = ""
        for row in self.grid:
            for cell in row:
                start += " | " + cell
            start += " |\n"
        return start

    def choices(self, point, exclusions):
        (row, col) = point
        points = []
        for x in range(row - 1, row + 1):
            if x > self.n:
                x = 0
            for y in range(col - 1, col + 1):
                if y > self.n:
                    y = 0
                if x == 2 and y == 2:
                    continue
                new = (x, y)
                if new not in exclusions:
                    points.append(new)
        return points


def random_char():
    return random.choice(string.ascii_lowercase)


class Node:
    def __init__(self):
        # self.char = char
        self.end = False
        self.children = {}

    def add_word(self, word):
        if len(word) == 0:
            self.end = True
            return

        first = word[0]
        if first not in self.children:
            new = Node()
            self.children[first] = new
        self.children[first].add_word(word[1:])


board = Board(4)
print(board)
entry = Node()

f = open("words.txt", "r")
for i in f:
    entry.add_word(i.rstrip())
