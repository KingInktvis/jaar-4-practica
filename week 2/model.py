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


# Random Move player
def get_random_move():
    # get any random move possible and return it
    return random.choice(list(MERGE_FUNCTIONS.keys()))


def compare_boards(existing_board, board_to_compare):
    for x in range(0, 16):
        if existing_board[int(x / 4)][x % 4] == board_to_compare[int(x / 4)][x % 4]:
            continue
        else:
            return False
    return True


# Expectimax Move player
# idea from https://gjdanis.github.io/2015/09/27/2048-game-and-ai/
def get_expectimax_move(board, depth, make_move=False, initial_move=False):
    # if we reached the lowest depth, or no more moves are available to us, and its our turn to make a move,
    # return the heuristic
    if depth <= 0 or (make_move and not move_exists(board)):
        return -1, heuristic(board)
    # keep track of the best move and its heuristic value
    best_move = None
    best_score = float("-inf")
    # determine if we should make a move right now, or if the board gets merged
    if make_move:
        # go by each possible move
        for move_direction in MERGE_FUNCTIONS.keys():
            # copy the board, and play the move out
            new_board = board.copy()
            new_board = MERGE_FUNCTIONS[move_direction](new_board)
            if compare_boards(new_board, board):
                continue
            # look for the next tiles and moves recursively
            new_score = get_expectimax_move(new_board, depth - 1)  # returns best_move, best_score
            # if we found a new best, track it
            if new_score[1] > best_score or best_move is None:
                best_score = new_score[1]
                best_move = move_direction
        # the game expects a move, not a move and a score back. Therefore, if its the intitial call, only return a move
        if initial_move:
            return best_move
        # return the best move, and the best score
        return (best_move, best_score)
    else:  # we should add a two (or four) tile for each possible scenario
        # keep track of the scores
        score_calculation = 0
        empty_locations = [(i, j) for i, j in itertools.product(range(4), range(4)) if
                           board[i][j] == 0]  # get all locations of empty spots
        for i, j in empty_locations:
            # make a new board (twice) where a 2 (or 4) is added in this empty spot
            copy_add_two = board.copy()
            copy_add_four = board.copy()
            copy_add_two[i][j] = 2
            copy_add_four[i][j] = 4
            # get the new scores if a 2 (or 4) is set on that spot, and the game plays on.
            # Because its a 90% chance to spawn a two, we give that a weight of 0.9, and a 0.1 weight to the 4
            score_board_two = 0.9 * get_expectimax_move(copy_add_two, depth - 1, True)[1]
            score_board_four = 0.1 * get_expectimax_move(copy_add_four, depth - 1, True)[1]
            # combine the two scores
            score_calculation += score_board_two + score_board_four
        # return the score
        return (1, score_calculation)


heuristic_board = [6, 5, 4, 3,
                   5, 4, 3, 2,
                   4, 3, 2, 1,
                   3, 2, 1, 0]


# gives the heuristic value of a board
# this function might sometimes be magic.
def heuristic(board):
    # if its game over, return the lowest possible value
    if not move_exists(board):
        return -float("inf")

    # keep track of the score and highest found tile
    score = 0
    highest_tile = 0
    #go by each tile on the board
    for x in range(0, 16):
        board_tile = board[int(x / 4)][x % 4]
        # keep track of highest found tile
        if board_tile > highest_tile:
            highest_tile = board_tile
        # if the boards tile is empty, give it a penalty
        if board_tile == 0:
            score -= (heuristic_board[x] * heuristic_board[x]) ** 3
        else:  # its not empty, so give it a score of the tile in that spot, and our heuristic
            score += heuristic_board[x] ** math.log(board_tile, 2)
    # if the highest tile is not in the most prominent spot, give it a penalty depending on how far off it is
    if highest_tile != board[0][0]:
        score -= abs(board[0][0] - highest_tile) ** 2
    return score
