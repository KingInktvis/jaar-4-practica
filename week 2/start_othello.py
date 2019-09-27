"""

Othello is a turn-based two-player strategy board game.

-----------------------------------------------------------------------------
Board representation

We represent the board as a 100-element list, which includes each square on
the board as well as the outside edge. Each consecutive sublist of ten
elements represents a single row, and each list element stores a piece. 
An initial board contains four pieces in the center:

    ? ? ? ? ? ? ? ? ? ?
    ? . . . . . . . . ?
    ? . . . . . . . . ?
    ? . . . . . . . . ?
    ? . . . o @ . . . ?
    ? . . . @ o . . . ?
    ? . . . . . . . . ?
    ? . . . . . . . . ?
    ? . . . . . . . . ?
    ? ? ? ? ? ? ? ? ? ?

This representation has two useful properties:

1. Square (m,n) can be accessed as `board[mn]`. This is because size of square is 10x10,
   and mn means m*10 + n. This avoids conversion between square locations and list indexes.
2. Operations involving bounds checking are slightly simpler.
"""

# The outside edge is marked ?, empty squares are ., black is @, and white is o.
# The black and white pieces represent the two players.
import random

EMPTY, BLACK, WHITE, OUTER = '.', '@', 'o', '?'
PIECES = (EMPTY, BLACK, WHITE, OUTER)
PLAYERS = {BLACK: 'Black', WHITE: 'White'}

# To refer to neighbor squares we can add a direction to a square.
UP, DOWN, LEFT, RIGHT = -10, 10, -1, 1
UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT = -9, 11, 9, -11

# 8 directions; note UP_LEFT = -11, we can repeat this from row to row
DIRECTIONS = (UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)

# Heuristic board to give values to locations.
# board supplied by http://hairdesiresalon.com/Zen-Master-Go/design.html
HEURISTIC_BOARD = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                   0, 1, 5, 3, 3, 3, 3, 5, 1, 0,
                   0, 5, 5, 4, 4, 4, 4, 5, 5, 0,
                   0, 3, 4, 2, 2, 2, 2, 4, 3, 0,
                   0, 3, 4, 2, 0, 0, 2, 4, 3, 0,
                   0, 3, 4, 2, 0, 0, 2, 4, 3, 0,
                   0, 3, 4, 2, 2, 2, 2, 4, 3, 0,
                   0, 5, 5, 4, 4, 4, 4, 5, 5, 0,
                   0, 1, 5, 3, 3, 3, 3, 5, 1, 0,
                   0, 0, 0, 0, 0, 0, 0, 0, 0, 0]


def squares():
    # list all the valid squares on the board.
    # returns a list [11, 12, 13, 14, 15, 16, 17, 18, 21, ...]; e.g. 19,20,21 are invalid
    # 11 means first row, first col, because the board size is 10x10
    return [i for i in range(11, 89) if 1 <= (i % 10) <= 8]


def initial_board():
    # create a new board with the initial black and white positions filled
    # returns a list ['?', '?', '?', ..., '?', '?', '?', '.', '.', '.', ...]
    board = [OUTER] * 100
    for i in squares():
        board[i] = EMPTY
    # the middle four squares should hold the initial piece positions.
    board[44], board[45] = WHITE, BLACK
    board[54], board[55] = BLACK, WHITE
    return board


def print_board(board):
    # get a string representation of the board
    # heading '  1 2 3 4 5 6 7 8\n'
    rep = ''
    rep += '  %s\n' % ' '.join(map(str, range(1, 9)))
    # begin,end = 11,19 21,29 31,39 ..
    for row in range(1, 9):
        begin, end = 10 * row + 1, 10 * row + 9
        rep += '%d %s\n' % (row, ' '.join(board[begin:end]))
    return rep


# gets a count of the white tiles and black tiles on a board
def get_white_black_count(board):
    white_count = 0
    black_count = 0
    for pawn in board:
        if pawn == WHITE:
            white_count += 1
        elif pawn is not EMPTY and pawn is not OUTER:
            black_count += 1
    return white_count, black_count


# -----------------------------------------------------------------------------
# Playing the game

# We need functions to get moves from players, check to make sure that the moves
# are legal, apply the moves to the board, and detect when the game is over.

# Checking moves. # A move must be both valid and legal: it must refer to a real square,
# and it must form a bracket with another piece of the same color with pieces of the
# opposite color in between.

def is_valid(move):
    # is move a square on the board?
    # move must be an int, and must refer to a real square
    return isinstance(move, int) and move in squares()


def opponent(player):
    # get player's opponent piece
    return BLACK if player is WHITE else WHITE


def find_bracket(square, player, board, direction):
    # find and return the square that forms a bracket with `square` for `player` in the given
    # `direction`
    # returns None if no such square exists
    bracket = square + direction
    if board[bracket] == player:
        return None
    opp = opponent(player)
    while board[bracket] == opp:
        bracket += direction
    # if last square board[bracket] not in (EMPTY, OUTER, opp) then it is player
    return None if board[bracket] in (OUTER, EMPTY) else bracket


def is_legal(move, player, board):
    # is this a legal move for the player?
    # move must be an empty square and there has to be is an occupied line in some direction
    # any(iterable) : Return True if any element of the iterable is true
    hasbracket = lambda direction: find_bracket(move, player, board, direction)
    return board[move] == EMPTY and any(hasbracket(x) for x in DIRECTIONS)


# Making moves
# When the player makes a move, we need to update the board and flip all the
# bracketed pieces.

def make_move(move, player, board):
    # update the board to reflect the move by the specified player
    # assuming now that the move is valid
    board[move] = player
    # look for a bracket in any direction
    for d in DIRECTIONS:
        make_flips(move, player, board, d)
    return board


def make_flips(move, player, board, direction):
    # flip pieces in the given direction as a result of the move by player
    bracket = find_bracket(move, player, board, direction)
    if not bracket:
        return
    # found a bracket in this direction
    square = move + direction
    while square != bracket:
        board[square] = player
        square += direction


# Monitoring players

# define an exception
class IllegalMoveError(Exception):
    def __init__(self, player, move, board):
        self.player = player
        self.move = move
        self.board = board

    def __str__(self):
        return '%s cannot move to square %d' % (PLAYERS[self.player], self.move)


def legal_moves(player, board):
    # get a list of all legal moves for player
    # legals means : move must be an empty square and there has to be is an occupied line in some direction
    return [sq for sq in squares() if is_legal(sq, player, board)]


def any_legal_move(player, board):
    # can player make any moves?
    return any(is_legal(sq, player, board) for sq in squares())


# Putting it all together

# Each round consists of:
# - Get a move from the current player.
# - Apply it to the board.
# - Switch players. If the game is over, get the final score.

def play(black_strategy, white_strategy):
    # get a starting player and initialise the board
    player = WHITE if random.randint(0, 1) is 0 else BLACK
    board = initial_board()
    # as long as we have a player who currently can move, play the game
    while player:
        # get the players next move using their strategy. Player is never None in this stage, so only check white/black
        if player == WHITE:
            next_move = white_strategy(player, board)
        else:
            next_move = black_strategy(player, board)
        # if no moves can be made by the current player, get the next player, and start the loop again
        if not next_move:
            player = next_player(board, player)
            continue
        # update the board by making the move for the player
        board = make_move(next_move, player, board)
        # swap players
        player = opponent(player)
        # print the board to show the updated board
        print(print_board(board))
    # The game ended, lets print out the scores and final board
    print("Game Concluded!")
    white_black_count = get_white_black_count(board)
    print("WHITE (" + WHITE + "): ", white_black_count[0], " vs. ", white_black_count[1], " BLACK (" + BLACK + ")")
    print(print_board(board))
    # play a game of Othello and return the final board and score


# find the next player
def next_player(board, prev_player):
    # by default, the opponent is at turn now
    other_player = opponent(prev_player)
    # if the opponent can do any legal move, return him
    if any_legal_move(other_player, board):
        return other_player
    # if he cant, but the previous player can, return him instead
    elif any_legal_move(prev_player, board):
        return prev_player
    # nobody can do any move at this point
    return None


def get_move(strategy, player, board):
    return strategy(player, board)


# Heuristic to count tiles on the board
def score_count_tiles(player, board):
    player_score = 0
    opponent_score = 0
    # for each board space, check if its ours or our opponents, and add 1 to their score
    for pawn in board:
        if pawn == opponent(player):
            opponent_score += 1
        elif pawn is not EMPTY and pawn is not OUTER:
            player_score += 1
    # subtract the two scores
    return player_score - opponent_score
    # compute player's score (number of player's pieces minus opponent's)


# Heuristic position count scores
def score_heuristic_count(player, board):
    playerScore = 0
    opponentScore = 0
    index = 0
    # similar to the previous get score function, but this one takes a weighted value from our heuristic board
    for pawn in board:
        if pawn == opponent(player):
            opponentScore += HEURISTIC_BOARD[index]
        elif pawn is not EMPTY and pawn is not OUTER:
            playerScore += HEURISTIC_BOARD[index]
        index += 1
    return playerScore - opponentScore


# Play strategies
def strategy_random(player, board):
    if any_legal_move(player, board):
        possible_moves = legal_moves(player, board)
        return possible_moves[random.randint(0, len(possible_moves) - 1)]


def strategy_negamax(player, board):
    return negamax(player, board, 3, 1)


def strategy_negamax_pruning(player, board):
    return negamax_pruning(player, board, 3, 1)


# Negamax implementation
def negamax(player, board, depth, color):
    # if we reached the end, or no legal moves exist anymore, return the current heuristic count
    if depth == 0 or not any_legal_move(player, board):
        return color * score_heuristic_count(player, board)
    # keep track of best score and move
    best_score = -99999999
    best_move = None
    board_copy = board.copy()
    # for any legal move, check its score, and if its better than the current score, keep track of it
    for next_move in legal_moves(player, board_copy):
        new_board = make_move(next_move, player, board_copy)
        new_score = max(best_score, -negamax(opponent(player), new_board, depth - 1, -color))
        if new_score > best_score or best_move is None:
            best_score = new_score
            best_move = next_move
    return best_move


# similar to negamax, but this implements pruning
def negamax_pruning(player, board, depth, color, best_branch=float("-inf")):
    # if we reached the end, or no legal moves exist anymore, return the current heuristic count
    if depth == 0 or not any_legal_move(player, board):
        return color * score_heuristic_count(player, board)
    # keep track of best score and move
    best_score = -99999999
    best_move = None
    board_copy = board.copy()
    # for any legal move, check its score, and if its better than the current score, keep track of it
    for next_move in legal_moves(player, board_copy):
        new_board = make_move(next_move, player, board_copy)
        new_score = negamax_pruning(opponent(player), new_board, depth - 1, -color, best_score)
        # if the tree got terminated due to a better branch count, simply return the current best move
        if new_score is None:
            return best_move
        # find the maximum of the -new_score and the best_score
        new_score = max(-new_score, best_score)
        # if our new score is worse than our best branch, simply return None to let know the branch is terminated
        if best_branch > new_score:
            return None
        # if we found a better move than our previous best, track it
        if new_score > best_score or best_move is None:
            best_score = new_score
            best_move = next_move
    return best_move


play(strategy_negamax_pruning, strategy_random)
