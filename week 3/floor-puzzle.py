from enum import Enum
import itertools


# Enum with all persons who live in the building
class Inhabitants(Enum):
    Loes = 1
    Marja = 2
    Niels = 3
    Erik = 4
    Joep = 5


# Loes does not live on the top floor
def loes_rule(order):
    return not order[4] == Inhabitants.Loes


# Marja does not live on the ground floor
def marja_rule(order):
    return not order[0] == Inhabitants.Marja


# Niels does not live on the highest or lowest floor
def niels_rule(order):
    if order[4] == Inhabitants.Niels or order[0] == Inhabitants.Niels:
        return False
    else:
        return True


# Erik lives on a higher floor than Marja
def erik_marja_rule(order):
    erik = order.index(Inhabitants.Erik)
    marja = order.index(Inhabitants.Marja)
    return erik > marja


# Joep does not live directly above or below Niels
def joep_rule(order):
    joep = order.index(Inhabitants.Joep)
    niels = order.index(Inhabitants.Niels)
    diff = joep - niels
    if diff == 1 or diff == -1:
        return False
    else:
        return True


# Niels does not live directly above or below Marja
def niels_marja_rule(order):
    niels = order.index(Inhabitants.Niels)
    marja = order.index(Inhabitants.Marja)
    diff = niels - marja
    if diff == 1 or diff == -1:
        return False
    else:
        return True


people = (Inhabitants.Loes, Inhabitants.Marja, Inhabitants.Niels, Inhabitants.Erik, Inhabitants.Joep)
rule_list = (loes_rule, marja_rule, niels_rule, erik_marja_rule, joep_rule, niels_marja_rule)


# Test the given order if it fulfills all rule constraints
def do_rules(order):
    for rule in rule_list:
        if not rule(order):
            return False
    return True


perm = itertools.permutations(people)
result = list(filter(do_rules, perm))
print(result)
