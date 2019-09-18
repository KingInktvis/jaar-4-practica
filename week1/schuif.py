class schuif:
    def __init__(self, size, numbs):
        self.board = []
        self.numbs = numbs
        self.size = size
        self.makeBoard()
        self.empty = self.findEmpty(self.board)
        self.foundMoves = 999999999
        self.winningHistory = []

    def findEmpty(self, board):
        i = 0
        for x in board:
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

    def isDone(self, board):
        for x in range(1, self.size*self.size):
            if board[x-1] != x:
                return False
        return True

    def getNextPositions(self, empty):
        ret = []
        cur = empty
        if cur >= self.size:
            ret += [cur-self.size,]
        if cur < self.size*(self.size-1):
            ret += [cur+self.size,]
        if (cur%self.size) != 0:
            ret += [cur-1,]
        if (cur % self.size) < self.size-1:
            ret += [cur+1, ]
        return ret

    def startLoop(self, heuristic):
        self.loop(self.board,[],  heuristic)

    class boardState():
        def __init__(self, board, moves, history):
            self.board = board
            self.moves = moves
            self.history = history

        def updateMovesAndHistory(self, moves, history):
            self.moves = moves;
            self.history = history

    def loop(self, currentBoard, globalHistory, heuristic):
        # if currentBoard in globalHistory:
        #     return False
        boardsToExplore = [self.boardState(currentBoard, 0, [])]
        while boardsToExplore:
            bs = boardsToExplore[0]
            board = bs.board
            for x in globalHistory:
                if x.board == board:
                    if x.moves > bs.moves:
                        x.moves = bs.moves
                        x.history = bs.history
                    del boardsToExplore[0]
                    board = None
                    break
                continue
            if not board:
                continue
            else:
                globalHistory += [bs, ]
            print(board, heuristic(board))
            if self.isDone(board):
                print("FOUND WINNING BOARD IN ", bs.moves , " MOVES! : ", bs.history)
                print("History Boards: ")
                self.printHistoryBoards(currentBoard,bs.history)
                return True
            empty = self.findEmpty(board)
            del boardsToExplore[0]
            for x in self.getNextPositions(empty):
                newCurBoard = board.copy()
                newCurBoard[empty] = newCurBoard[x]
                newCurBoard[x] = 0
                newHis = bs.history + [x, ]
                boardsToExplore = self.insertIntoArray(boardsToExplore, self.boardState(newCurBoard, bs.moves+1, newHis), heuristic)

    def misplaced_heuristic(self, board):
        score = 0
        for x in range(1, len(board) + 1):
            y = board[x - 1]
            if y != 0 and y != x:
                score += 1
        return score

    def no_heuristic(self, board):
        return 0

    def printHistoryBoards(self, start, history):
        self.printBoard(start)
        for x in history:
            empty = self.findEmpty(start)
            start[empty] = start[x]
            start[x] = 0
            self.printBoard(start)

    def insertIntoArray(self, array, boardState, heuristic):
        i = 0
        boardHeur = heuristic(boardState.board)
        for x in array:
            if heuristic(x.board) > boardHeur:
                array = array[:i] + [boardState, ] + array[i:]
                return array
            i += 1
        array += [boardState, ]
        return array








s = schuif(3, "867254301")
s.startLoop(s.misplaced_heuristic)
