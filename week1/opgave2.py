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

    # def choices(self, point, exclusions):
    #     (row, col) = point
    #     points = []
    #     for x in range(row - 1, row + 1):
    #         if x > self.n:
    #             x = 0
    #         for y in range(col - 1, col + 1):
    #             if y > self.n:
    #                 y = 0
    #             if x == 2 and y == 2:
    #                 continue
    #             new = (x, y)
    #             if new not in exclusions:
    #                 points.append(new)
    #     return points


def random_char():
    return random.choice(string.ascii_lowercase)


class Node:
    def __init__(self, char):
        self.end = False
        self.children = {}
        self.char = char

    # Voeg een nieuw woord toe aan de tree
    def add_word(self, word):
        if len(word) == 0:
            self.end = True
            return
        first = word[0]
        if first not in self.children:
            new = Node(first)
            self.children[first] = new
        self.children[first].add_word(word[1:])


# controleer of de node een woord markeerd
def isFinalNode(node):
    return node.end


def getWordFromHistory(history, boardSize):
    str = ""
    for x in history:
        str += board.grid[int(x / boardSize)][x % boardSize]
    return str


def searchWords(currentIndex, indexHistory, lastNode, boardSize, foundWords):
    # controleer of de letter niet al gebruikt is
    if currentIndex in indexHistory:
        return False
    # controleer of dit een woord maakt
    if isFinalNode(lastNode):
        word = getWordFromHistory(indexHistory, boardSize)
        # voeg woord toe aan gevonden woorden als deze niet al eerder is gevonden
        if word not in foundWords:
            foundWords += [word, ]
            print("Found word!", word)
        return
    nextNode = getNextNode(lastNode, currentIndex, boardSize)
    if not nextNode:
        return False
    # print(nextNode)
    newHis = indexHistory + [currentIndex, ]
    searchWords(getNextPosition(currentIndex, 0, boardSize), newHis, nextNode, boardSize, foundWords)
    searchWords(getNextPosition(currentIndex, 1, boardSize), newHis, nextNode, boardSize, foundWords)
    searchWords(getNextPosition(currentIndex, 2, boardSize), newHis, nextNode, boardSize, foundWords)
    searchWords(getNextPosition(currentIndex, 3, boardSize), newHis, nextNode, boardSize, foundWords)


# return the vertex met het karakter voor het huidige veld. return None als er geen vertex is met dit karakter
def getNextNode(currentNode, currentIndex, boardSize):
    char = board.grid[int(currentIndex / boardSize)][currentIndex % boardSize]
    if char in currentNode.children:
        return currentNode.children[char]
    return None


# return de index van de buur van de huidige positie in de aangegeven richting
# direction 0=up,1=down,2=left,3=right
def getNextPosition(currentPosition, direction, boardSize):
    if direction == 0:
        if currentPosition < boardSize:
            return currentPosition + (boardSize * (boardSize - 1))
        else:
            return currentPosition - boardSize
    if direction == 1:
        if currentPosition >= (boardSize * (boardSize - 1)):
            return currentPosition - (boardSize * (boardSize - 1))
        else:
            return currentPosition + boardSize
    if direction == 2:
        if currentPosition % boardSize == 0:
            return currentPosition + (boardSize - 1)
        else:
            return currentPosition - 1
    if direction == 3:
        if (currentPosition + 1) % boardSize == 0:
            return currentPosition - (boardSize - 1)
        else:
            return currentPosition + 1


boardSize = 100
board = Board(boardSize)
print(board)
entry = Node("")
foundWords = []

# open woordenboek en voeg de woorden toe aan de tree
f = open("words.txt", "r")
for i in f:
    entry.add_word(i.rstrip())

for x in range(0, boardSize * boardSize):
    searchWords(x, [], entry, boardSize, foundWords)
