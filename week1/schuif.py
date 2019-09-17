class schuif:
    def __init__(self, size, numbs):
        self.board = []
        self.numbs = numbs
        self.size = size
        self.makeBoard()
        self.empty = self.findEmpty()
        self.foundMoves = 999999999
        self.winningHistory = []

    def findEmpty(self):
        i = 0
        for x in self.board:
            if x == 0:
                return i
            i += 1


    def makeBoard(self):
        if not self.numbs:
            self.board = [x for x in range(0, self.size*self.size)]
            return
        self.board = [int(x) for x in self.numbs]

    def printBoard(self, board):
        i = 0
        ret = ""
        for x in board:
            if i >= self.size:
                i = 0
                ret += "\n"
            i+=1
            ret += str(x) + " "
        ret += "\n"
        print(ret)

    def isDone(self):
        for x in range(1, self.size*self.size):
            if self.board[x-1] != x:
                return False
        return True

    def getNextPositions(self, empty):
        ret = []
        cur = empty
        if cur >= self.size:
            ret += [cur-self.size,]
        if cur < self.size*(self.size-1):
            ret += [cur+self.size,]
        if (cur-1)%self.size == 0:
            ret += [cur-1, ]
        if (cur % self.size) == 0:
            ret += [cur+1, ]
        return ret

    def startLoop(self):
        self.loop(self.board, [self.empty, ], 0, [])

    def loop(self, currentBoard, history, currentMoveAmount, globalHistory):
        # if currentBoard in globalHistory:
        #     return False
        globalHistory += [currentBoard, ]
        if self.isDone():
            self.foundMoves = currentMoveAmount
            self.winningHistory = history
            print("FOUND WINNING BOARD IN ", currentMoveAmount, " MOVES! : ", self.winningHistory)
            return True
        if currentMoveAmount > self.foundMoves:
            return False
        newMoveAmount = currentMoveAmount + 1
        empty = history[len(history)-1]
        if len(history)>3 and empty == history[len(history) - 3]:
            return
        for x in self.getNextPositions(empty):
            newCurBoard = currentBoard.copy()
            newCurBoard[empty] = newCurBoard[x]
            newCurBoard[x] = 0
            self.printBoard(newCurBoard)
            newHistory = history + [x, ]
            self.loop(newCurBoard, newHistory, newMoveAmount, globalHistory)







s = schuif(3, "867254301")
s.startLoop()
