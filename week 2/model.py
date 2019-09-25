import random
import itertools
import math

MAX_DEPTH = 3

def merge_left(b):
    # merge the board left
    # this is the funcyoin that is reused in the other merges
    # b = [[0, 2, 4, 4], [0, 2, 4, 8], [0, 0, 0, 4], [2, 2, 2, 2]]    
    def merge(row, acc):
        # recursive helper for merge_left

        # if len row == 0, return accumulator
        if not row:
            return acc

        # x = first element
        x = row[0]
        # if len(row) == 1, add element to accumulator
        if len(row) == 1:
            return acc + [x]

        # if len(row) >= 2
        if x == row[1]:
            # add row[0] + row[1] to accumulator, continue with row[2:]
            return merge(row[2:], acc + [2 * x])
        else:
            # add row[0] to accumulator, continue with row[1:]
            return merge(row[1:], acc + [x])

    new_b = []
    for row in b:
        # merge row, skip the [0]'s
        merged = merge([x for x in row if x != 0], [])
        # add [0]'s to the right if necessary
        merged = merged + [0] * (len(row) - len(merged))
        new_b.append(merged)
    # return [[2, 8, 0, 0], [2, 4, 8, 0], [4, 0, 0, 0], [4, 4, 0, 0]]
    return new_b

def merge_right(b):
    # merge the board right
    # b = [[0, 2, 4, 4], [0, 2, 4, 8], [0, 0, 0, 4], [2, 2, 2, 2]]
    def reverse(x):
        return list(reversed(x))

    # rev = [[4, 4, 2, 0], [8, 4, 2, 0], [4, 0, 0, 0], [2, 2, 2, 2]]
    rev = [reverse(x) for x in b]
    # ml = [[8, 2, 0, 0], [8, 4, 2, 0], [4, 0, 0, 0], [4, 4, 0, 0]]
    ml = merge_left(rev)
    # return [[0, 0, 2, 8], [0, 2, 4, 8], [0, 0, 0, 4], [0, 0, 4, 4]]
    return [reverse(x) for x in ml]


def merge_up(b):
    # merge the board upward
    # note that zip(*b) is the transpose of b
    # b = [[0, 2, 4, 4], [0, 2, 4, 8], [0, 0, 0, 4], [2, 2, 2, 2]]
    # trans = [[2, 0, 0, 0], [4, 2, 0, 0], [8, 2, 0, 0], [4, 8, 4, 2]]
    trans = merge_left(zip(*b))
    # return [[2, 4, 8, 4], [0, 2, 2, 8], [0, 0, 0, 4], [0, 0, 0, 2]]
    return [list(x) for x in zip(*trans)]


def merge_down(b):
    # merge the board downward
    # b = [[0, 2, 4, 4], [0, 2, 4, 8], [0, 0, 0, 4], [2, 2, 2, 2]]
    # trans = [[0, 0, 0, 2], [0, 0, 2, 4], [0, 0, 8, 2], [4, 8, 4, 2]]
    trans = merge_right(zip(*b))
    # return [[0, 0, 0, 4], [0, 0, 0, 8], [0, 2, 8, 4], [2, 4, 2, 2]]
    return [list(x) for x in zip(*trans)]


# location: after functions
MERGE_FUNCTIONS = {
    'left': merge_left,
    'right': merge_right,
    'up': merge_up,
    'down': merge_down
}

def move_exists(b):
    # check whether or not a move exists on the board
    # b = [[1, 2, 3, 4], [5, 6, 7, 8]]
    # move_exists(b) return False
    def inner(b):
        for row in b:
            for x, y in zip(row[:-1], row[1:]):
                # tuples (1, 2),(2, 3),(3, 4),(5, 6),(6, 7),(7, 8)
                if x == y or x == 0 or y == 0:
                    return True
        return False

    if inner(b) or inner(zip(*b)):
        return True
    else:
        return False

def start():
    # make initial board
    b = [[0] * 4 for _ in range(4)]
    add_two_four(b)
    add_two_four(b)
    return b


def play_move(b, direction):
    # get merge functin an apply it to board
    b = MERGE_FUNCTIONS[direction](b)
    add_two_four(b)
    return b


def add_two_four(b):
    # add a random tile to the board at open position.
    # chance of placing a 2 is 90%; chance of 4 is 10%
    rows, cols = list(range(4)), list(range(4))
    random.shuffle(rows)
    random.shuffle(cols)
    distribution = [2] * 9 + [4]
    for i, j in itertools.product(rows, rows):
        if b[i][j] == 0:
            b[i][j] = random.sample(distribution, 1)[0]
            return (b)
        else:
            continue

def game_state(b):
    for i in range(4):
        for j in range(4):
            if b[i][j] >= 2048:
                return 'win'
    return 'lose'

def test():
    b = [[0, 2, 4, 4], [0, 2, 4, 8], [0, 0, 0, 4], [2, 2, 2, 2]]
    assert merge_left(b) == [[2, 8, 0, 0], [2, 4, 8, 0], [4, 0, 0, 0], [4, 4, 0, 0]]
    assert merge_right(b) == [[0, 0, 2, 8], [0, 2, 4, 8], [0, 0, 0, 4], [0, 0, 4, 4]]
    assert merge_up(b) == [(2, 4, 8, 4), (0, 2, 2, 8), (0, 0, 0, 4), (0, 0, 0, 2)]
    assert merge_down(b) == [(0, 0, 0, 4), (0, 0, 0, 8), (0, 2, 8, 4), (2, 4, 2, 2)]
    assert move_exists(b) == True
    b = [[2, 8, 4, 0], [16, 0, 0, 0], [2, 0, 2, 0], [2, 0, 0, 0]]
    assert (merge_left(b)) == [[2, 8, 4, 0], [16, 0, 0, 0], [4, 0, 0, 0], [2, 0, 0, 0]]
    assert (merge_right(b)) == [[0, 2, 8, 4], [0, 0, 0, 16], [0, 0, 0, 4], [0, 0, 0, 2]]
    assert (merge_up(b)) == [(2, 8, 4, 0), (16, 0, 2, 0), (4, 0, 0, 0), (0, 0, 0, 0)]
    assert (merge_down(b)) == [(0, 0, 0, 0), (2, 0, 0, 0), (16, 0, 4, 0), (4, 8, 2, 0)]
    assert (move_exists(b)) == True
    b = [[0, 7, 0, 0], [0, 0, 7, 7], [0, 0, 0, 7], [0, 7, 0, 0]]
    g = Game()
    for i in range(11):
        g.add_two_four(b)

def get_random_move():
    return random.choice(list(MERGE_FUNCTIONS.keys()))



def get_expectimax_move(board, depth, make_move=False, initial_move=False):
    if depth<=0 or (make_move and not move_exists(board)):
        return -1, heuristic(board)
    best_move = None
    best_score = -99999999
    if make_move:
        for move_direction in MERGE_FUNCTIONS.keys():
            new_board = board.copy()
            new_board = play_move(new_board, move_direction)
            new_score = get_expectimax_move(new_board, depth-1)  #returns best_move, best_score
            if new_score[1] > best_score:
                best_score = new_score[1]
                best_move = move_direction
        if initial_move:
            return best_move
        return (best_move, best_score)
    else:
        score_calculation = 0
        empty_locations = [(i,j) for i,j in itertools.product(range(4),range(4)) if board[i][j]==0] # get all locations of empty spots
        for i,j in empty_locations:
            copy_add_two = board.copy()
            copy_add_four = board.copy()
            copy_add_two[i][j] = 2
            copy_add_four[i][j] = 4
            score_board_two = 0.9*get_expectimax_move(copy_add_two, depth-1, True)[1]
            score_board_four = 0.1*get_expectimax_move(copy_add_four, depth-1, True)[1]
            score_calculation += score_board_two + score_board_four
        return (1, score_calculation)


def heuristic(board):
    if not move_exists(board):
        return -float("inf")

    snake = []
    for i, col in enumerate(zip(*board)):
        snake.extend(reversed(col) if i % 2 == 0 else col)

    m = max(snake)
    return sum(x/10**n for n, x in enumerate(snake)) - \
           math.pow((board[3][0] != m)*abs(board[3][0] - m), 2)
