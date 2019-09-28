from enum import Enum
import itertools


class Inhabitants(Enum):
    Loes = 1
    Marja = 2
    Niels = 3
    Erik = 4
    Joep = 5


def loes_rule(order):
    return not order[4] == Inhabitants.Loes


def marja_rule(order):
    return not order[0] == Inhabitants.Marja


def niels_rule(order):
    if order[4] == Inhabitants.Niels or order[0] == Inhabitants.Niels:
        return False
    else:
        return True


def erik_rule(order):
    erik = order.index(Inhabitants.Erik)
    marja = order.index(Inhabitants.Marja)
    return erik > marja


def joep_rule(order):
    joep = order.index(Inhabitants.Joep)
    niels = order.index(Inhabitants.Niels)
    diff = joep - niels
    if diff == 1 or diff == -1:
        return False
    else:
        return True


def niels_marja_rule(order):
    niels = order.index(Inhabitants.Niels)
    marja = order.index(Inhabitants.Marja)
    diff = niels - marja
    if diff == 1 or diff == -1:
        return False
    else:
        return True


base = (Inhabitants.Loes, Inhabitants.Marja, Inhabitants.Niels, Inhabitants.Erik, Inhabitants.Joep)
rule_list = (loes_rule, marja_rule, niels_rule, erik_rule, joep_rule, niels_marja_rule)


def do_rules(order):
    for rule in rule_list:
        if not rule(order):
            return False
    return True


perm = itertools.permutations(base)
result = list(filter(do_rules, perm))
print(result)
