from enum import Enum


class Cargo(Enum):
    Wolf = "W"
    Goat = "G"
    Cabbage = "C"


class Vertex:
    left = set()
    right = set()
    farmer_right = False
    end = False

    def __str__(self):
        ret = ""
        for c in self.left:
            ret += c.value
        if self.farmer_right:
            ret += "|F"
        else:
            ret += "F|"
        for c in self.right:
            ret += c.value
        return ret

    def equals(self, other):
        if self.farmer_right == other.farmer_right and self.right <= other.right and self.left <= other.left:
            return True
        else:
            return False


def end_state(vertex):
    shore = vertex.right if vertex.farmer_right else vertex.left
    if check_shore(shore):
        vertex.end = True
    if vertex.farmer_right and len(vertex.right) == 3:
        vertex.end = True


def check_shore(shore):
    if Cargo.Wolf in shore and Cargo.Goat in shore:
        return False
    if Cargo.Cabbage in shore and Cargo.Goat in shore:
        return False
    return True


def options(vertex):
    if vertex.end:
        return set()
    if vertex.farmer_right:
        return vertex.right
    else:
        return vertex.left


def add_move(vertex, cargo):
    # stop adding if it is an ending
    if vertex.end:
        return None
    # search if the edge already exists
    for e in edges:
        print(e)
        if e[0].equals(vertex) and e[2] == cargo:
            return
    # make the new vertex
    new = Vertex()
    new.left = vertex.left.copy()
    new.right = vertex.right.copy()
    if vertex.farmer_right:
        new.right.remove(cargo)
        new.left.add(cargo)
        new.farmer_right = False
    else:
        new.left.remove(cargo)
        new.right.add(cargo)
        new.farmer_right = True
    # Check if vertex already exists
    for v in vertices:
        if v.equals(new):
            edges.add(v)
            return
    # add new edge
    print(new)
    vertices.add(new)
    edge = tuple([vertex, new, cargo])
    edges.add(edge)
    fill_edges(new)


def fill_edges(vertex):
    choices = options(vertex)
    for c in choices:
        add_move(vertex, c)


vertices = set()
# (start, end, cargo)
edges = set()

start = Vertex()
start.left = {Cargo.Wolf, Cargo.Goat, Cargo.Cabbage}
start.farmer_right = False
vertices.add(start)
fill_edges(start)
