class Farmer:

    def __init__(self):
        self.exploredLeftStates = []

    # 0x1111 > FWCG | Farmer, Wolf, Cabbage, Goat
    # stateLeft (0x0000) / stateRight (0x0000) / isFarmerLeft (T|F) / history (Tuple of boat companies, starting left)
    def findSolution(self, stateLeft, stateRight, history, leftHistory):
        if stateLeft in leftHistory:
            return False
        else:
            leftHis = leftHistory + [stateLeft, ];
        if stateRight == 0x0000 and history:
            return False  # no need to explore this as its the start situation
        his = history + [stateLeft, stateRight, ]
        if stateLeft == 0x0000:
            self.printValidStateHistory(his)
            return False
        if not (self.isValidState(stateLeft) and self.isValidState(stateRight)):
            return False
        if stateLeft & 0x1000 == 0x1000:
            if stateLeft & 0x0100 == 0x0100:
                self.findSolution(stateLeft & 0x0011, stateRight | 0x1100, his, leftHis)
            if stateLeft & 0x0010 == 0x0010:
                self.findSolution(stateLeft & 0x0101, stateRight | 0x1010, his, leftHis)
            if stateLeft & 0x0001 == 0x0001:
                self.findSolution(stateLeft & 0x0110, stateRight | 0x1001, his, leftHis)
        else:  # farmer is right side
            if stateRight & 0x0100 == 0x0100:
                self.findSolution(stateLeft | 0x1100, stateRight & 0x0011, his, leftHis)
            if stateRight & 0x0010 == 0x0010:
                self.findSolution(stateLeft | 0x1010, stateRight & 0x0101, his, leftHis)
            if stateRight & 0x0001 == 0x0001:
                self.findSolution(stateLeft | 0x1001, stateRight & 0x0110, his, leftHis)
            self.findSolution(stateLeft | 0x1000, stateRight & 0x0111, his, leftHis)

    def isValidState(self, state):
        return state & 0x1000 == 0x1000 or (state & 0x0101) != 0x0101 and (state & 0x0011) != 0x0011


    def printValidStateHistory(self, history):
        print("VALID STATE:")
        for x in range(0, len(history)):
            if x % 2 == 1:
                continue
            print(" >> ")
            left = history[x]
            x = x + 1
            right = history[x]
            self.printState(left, right)


    def printState(self, left, right):
        print(self.getSingularSideState(left) + "|" + self.getSingularSideState(right))

    def getSingularSideState(self, state):
        str = "";
        if state & 0x1000 == 0x1000: str = str + "F";
        if state & 0x0100 == 0x0100: str = str + "W";
        if state & 0x0010 == 0x0010: str = str + "C";
        if state & 0x0001 == 0x0001: str = str + "G";
        return str

Farmer().findSolution(0x1111, 0x0000, [], [])