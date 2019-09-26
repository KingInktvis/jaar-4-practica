import matplotlib.pyplot as plt
import random
import time
import itertools
import math
import unittest
from collections import namedtuple

# based on Peter Norvig's IPython Notebook on the TSP

City = namedtuple('City', 'x y')


def distance(A, B):
    return math.hypot(A.x - B.x, A.y - B.y)


def try_all_tours(cities):
    # generate and test all possible tours of the cities and choose the shortest tour
    tours = alltours(cities)
    return min(tours, key=tour_length)


def alltours(cities):
    # return a list of tours (a list of lists), each tour a permutation of cities,
    # and each one starting with the same city
    # cities is a set, sets don't support indexing
    start = next(iter(cities))
    return [[start] + list(rest)
            for rest in itertools.permutations(cities - {start})]


def tour_length(tour):
    # the total of distances between each pair of consecutive cities in the tour
    return sum(distance(tour[i], tour[i - 1])
               for i in range(len(tour)))


def make_cities(n, seed=0, width=1000, height=1000):
    # make a set of n cities, each with random coordinates within a rectangle (width x height).

    random.seed(seed)  # the current system time is used as a seed
    # note: if we use the same seed, we get the same set of cities

    return frozenset(City(random.randrange(width), random.randrange(height))
                     for c in range(n))


def plot_tour(tour):
    # plot the cities as circles and the tour as lines between them
    points = list(tour) + [tour[0]]
    plt.plot([p.x for p in points], [p.y for p in points], 'bo-')
    plt.axis('scaled')  # equal increments of x and y have the same length
    plt.axis('off')
    plt.show()


def nn_algorithm(cities):
    to_go = set(cities)
    start = to_go.pop()
    path = [start]
    while to_go:
        nn = find_nn(path[len(path) - 1], to_go)
        to_go.remove(nn)
        path.append(nn)
    return path


def find_nn(city, options):
    best = None
    euclidean_distance = 9999999
    for c in options:
        c_distance = (city.x - c.x) ** 2 + (city.y - c.y) ** 2
        if c_distance < euclidean_distance:
            euclidean_distance = c_distance
            best = c
    return best


def switch_edges(path, vertex_index_1, vertex_index_2):
    start = path[:vertex_index_1] if vertex_index_1 > 0 else []
    end = path[vertex_index_2 + 1:]
    mid = path[vertex_index_1:vertex_index_2 + 1]
    mid.reverse()
    new = start + mid + end
    return new


def remove_intersections(path):
    tour = path[:]
    for i in range(len(tour) - 2):
        c1 = tour[i]
        c2 = tour[i + 1]
        for j in range(i + 1, len(tour)):
            c3 = tour[j]
            if j < len(tour) - 1:
                c4 = tour[j + 1]
            else:
                c4 = tour[0]

            if is_intersecting(c1, c2, c3, c4):
                tour = switch_edges(tour, i + 1, j)
    return tour


def nn_no_intersection_algorithm(tour):
    nn = nn_algorithm(tour)
    improve = remove_intersections(nn)
    # improve2 = remove_intersections(improve)
    return improve


def is_intersecting(city1, city2, city3, city4):
    domain = shared_domain(city1, city2, city3, city4)
    if domain is None:
        return False
    if city1 == city3 or city1 == city4 or city2 == city3 or city2 == city4:
        return False

    f1 = get_function(city1, city2)
    f2 = get_function(city3, city4)
    start1 = f1(domain[0])
    start2 = f2(domain[0])
    end1 = f1(domain[1])
    end2 = f2(domain[1])
    if city1.x == city2.x:
        if city3.x == city4.x:
            return in_range(city1, city2, city3, city4)
        if city2.y < f2(city1.x) < city1.y or city2.y > f2(city1.x) > city1.y:
            return True
        else:
            return False
    if city3.x == city4.x:
        if city4.y < f1(city3.x) < city3.y or city4.y > f1(city3.x) > city3.y:
            return True
        else:
            return False

    if start1 == start2 or end1 == end2:
        return False
    elif start1 > start2 and end1 > end2:
        return False
    elif start1 < start2 and end1 < end2:
        return False
    else:
        return True


def get_function(city1, city2):
    if city2.x == city1.x:
        return lambda x: None
    a = (city2.y - city1.y) / (city2.x - city1.x)
    b = city1.y - a * city1.x
    function = lambda x: a * x + b
    return function


def shared_domain(city1, city2, city3, city4):
    if not in_domain(city1, city2, city3, city4):
        return None
    # Get domains of to short edges
    if city1.x < city2.x:
        domain1 = (city1.x, city2.x)
    else:
        domain1 = (city2.x, city1.x)

    if city3.x < city4.x:
        domain2 = (city3.x, city4.x)
    else:
        domain2 = (city4.x, city3.x)
    # Find start of shared domain
    if domain1[0] < domain2[0]:
        start = domain2[0]
    else:
        start = domain1[0]

    # Find end of shared domain
    if domain1[1] > domain2[1]:
        end = domain2[1]
    else:
        end = domain1[1]

    return start, end


def in_domain(city1, city2, city3, city4):
    if city1.x < city2.x:
        domain1 = (city1.x, city2.x)
    else:
        domain1 = (city2.x, city1.x)

    if city3.x < city4.x:
        domain2 = (city3.x, city4.x)
    else:
        domain2 = (city4.x, city3.x)
    if domain2[0] <= domain1[0] <= domain2[1]:
        return True
    elif domain2[0] <= domain1[1] <= domain2[1]:
        return True
    else:
        return False


def in_range(city1, city2, city3, city4):
    if city1.y < city2.y:
        domain1 = (city1.y, city2.y)
    else:
        domain1 = (city2.y, city1.y)

    if city3.y < city4.y:
        domain2 = (city3.y, city4.y)
    else:
        domain2 = (city4.y, city3.y)

    if domain2[0] <= domain1[0] <= domain2[1]:
        return True
    elif domain2[0] <= domain1[1] <= domain2[1]:
        return True
    else:
        return False


def count_intersections(tour):
    count = 0
    for i in range(len(tour) - 2):
        c1 = tour[i]
        c2 = tour[i + 1]
        for j in range(i + 1, len(tour)):
            c3 = tour[j]
            if j < len(tour) - 1:
                c4 = tour[j + 1]
            else:
                c4 = tour[0]

            if is_intersecting(c1, c2, c3, c4):
                count += 1
    return count


def plot_tsp(algorithm, cities):
    # apply a TSP algorithm to cities, print the time it took, and plot the resulting tour.

    start_time = time.time()
    tour = algorithm(cities)
    exec_time = time.time() - start_time

    print("{} city tour with length {:.1f} in {:.3f} secs for {}"
          .format(len(tour), tour_length(tour), exec_time, algorithm.__name__))
    print("Start plotting ...")
    plot_tour(tour)


class TestStringMethods(unittest.TestCase):
    c1 = City(x=1, y=1)
    c2 = City(x=3, y=4)
    c3 = City(x=1, y=2)
    c4 = City(x=4, y=4)
    c5 = City(x=3, y=3)

    def test_domain(self):
        self.assertTrue(in_domain(self.c1, self.c2, self.c3, self.c4))
        self.assertEqual(shared_domain(self.c1, self.c2, self.c3, self.c4), (1, 3))
        self.assertEqual(shared_domain(self.c4, self.c2, self.c3, self.c1), None)

    def test_intersection(self):
        self.assertTrue(is_intersecting(self.c1, self.c2, self.c3, self.c4))
        self.assertFalse(is_intersecting(self.c4, self.c2, self.c3, self.c1))
        c1 = City(x=1, y=1)
        c2 = City(x=5, y=5)
        c3 = City(x=1, y=2)
        c4 = City(x=5, y=6)
        self.assertFalse(is_intersecting(c1, c2, c3, c4))

    def test_intersection_count(self):
        c1 = City(x=1, y=1)
        c2 = City(x=5, y=4)
        c3 = City(x=2, y=3)
        c4 = City(x=6, y=6)
        c5 = City(x=3, y=3)
        tour = (c1, c2, c3, c4, c5)
        self.assertEqual(count_intersections(tour), 1)

        cities = make_cities(10, 0)
        tour = nn_algorithm(cities)
        self.assertEqual(count_intersections(tour), 2)

        cities = make_cities(10, 2)
        tour = nn_algorithm(cities)
        self.assertEqual(count_intersections(tour), 3)

    def test_edge_swap(self):
        tour = [self.c1, self.c2, self.c3, self.c4, self.c5]
        check = [self.c1, self.c4, self.c3, self.c2, self.c5]
        self.assertEqual(switch_edges(tour, 1, 3), check)


# if __name__ == '__main__':
#     unittest.main()


def city_intersection_correlation():
    for i in range(1, 100):
        tour = nn_algorithm(make_cities(i, 0))
        print(count_intersections(tour))


# city_intersection_correlation()

def edge_length(edge):
    distance = (edge[0].x - edge[1].x) ** 2 + (edge[0].y - edge[1].y) ** 2
    return distance


def two_opt(cities):
    tour = []
    # for c in cities:
    #     tour.append(c)
    tour = nn_algorithm(cities)
    for _ in range(1000):
        new_tour = cycle(tour)
        if new_tour == tour:
            return new_tour
        else:
            tour = new_tour
    return tour


def test_proposal(tour, index_1, index_2):
    start1 = tour[index_1]
    end1 = tour[index_1 + 1]
    start2 = tour[index_2]
    end2 = tour[index_2 + 1] if index_2 + 1 < len(tour) else tour[0]
    first_case = distance(start1, end1) + distance(start2, end2)
    second_case = distance(start1, start2) + distance(end1, end2)
    if second_case < first_case:
        return True
    else:
        return False


def cycle(tour):
    for i in range(len(tour)):
        for j in range(i + 1, len(tour)):
            if test_proposal(tour, i, j):
                new = switch_edges(tour, i+1, j)
                return new
    return tour


# plot_tsp(try_all_tours, make_cities(10))
plot_tsp(nn_algorithm, make_cities(500, 0))
# plot_tsp(nn_no_intersection_algorithm, make_cities(100, 0))
plot_tsp(two_opt, make_cities(500, 0))
