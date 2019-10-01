from enum import Enum
import itertools


class Card(Enum):
    Ace = 'A'
    King = 'K'
    Queen = 'Q'
    Jack = 'J'


def get_neighbours(current_position, board):
    fields = []
    if current_position == 0:
        fields = [3]
    elif current_position == 1:
        fields = [2]
    elif current_position == 2:
        fields = [1, 3, 4]
    elif current_position == 3:
        fields = [0, 2, 5]
    elif current_position == 4:
        fields = [2, 5]
    elif current_position == 5:
        fields = [3, 4, 6, 7]
    elif current_position == 6:
        fields = [5]
    elif current_position == 7:
        fields = [5]
    cards = []
    for f in fields:
        cards.append(board[f])
    return cards


def find_cards(board, card):
    positions = []
    for i in range(len(board)):
        if board[i] == card:
            positions.append(i)
    return positions


def rule_1(board):
    return could_be_neighbours(board, Card.Ace, Card.King)


def rule_2(board):
    return could_be_neighbours(board, Card.King, Card.Queen)


def rule_3(board):
    return could_be_neighbours(board, Card.Queen, Card.Jack)


def rule_4(board):
    return are_not_neighbours(board, Card.Ace, Card.Queen)


def rule_5(board):
    for c in Card:
        if not are_not_neighbours(board, c, c):
            return False
    return True


rules = (rule_1, rule_2, rule_3, rule_4, rule_5)


def check_rules(board):
    for rule in rules:
        if not rule(board):
            return False
    return True


def could_be_neighbours(board, card1, card2):
    card_positions = find_cards(board, card1)
    for p in card_positions:
        neighbours = get_neighbours(p, board)
        if card2 not in neighbours:
            if None not in neighbours:
                return False
    return True


def are_not_neighbours(board, card1, card2):
    card_positions = find_cards(board, card1)
    for p in card_positions:
        neighbours = get_neighbours(p, board)
        if card2 in neighbours:
            return False
    return True


def dfs_entry():
    board = [None, None, None, None, None, None, None, None]
    choices = {
        Card.Ace: 2,
        Card.King: 2,
        Card.Queen: 2,
        Card.Jack: 2
    }
    for b in dfs(board, 0, choices):
        print_board(b)


def dfs(board, next, choices):
    solutions = []
    if next == 8:
        if check_rules(board):
            return [tuple(board)]
        else:
            return []
    for c in choices:
        if choices[c] == 0:
            continue
        board[next] = c
        if check_rules(board):
            choices[c] -= 1
            result = dfs(board, next + 1, choices)
            choices[c] += 1
            if not len(result) == 0:
                solutions = solutions + result
        board[next] = None
    return solutions


def print_board_list(boards):
    for b in boards:
        print_board(b)


def print_board(board):
    print(" . . " + board[0].value + " . ")
    print(" " + board[1].value + " " + board[2].value + " " + board[3].value + " . ")
    print(" . " + board[4].value + " " + board[5].value + " " + board[6].value)
    print(" . . " + board[7].value + " . ")
    print("\n")


base = (Card.Ace, Card.Ace, Card.King, Card.King, Card.Queen, Card.Queen, Card.Jack, Card.Jack)


def brute_force():
    perm = itertools.permutations(base)
    no_duplicates = set(perm)
    solutions = list(filter(check_rules, no_duplicates))
    print_board_list(solutions)


# brute_force()
dfs_entry()
