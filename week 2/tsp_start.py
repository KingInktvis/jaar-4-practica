import matplotlib.pyplot as plt
import random
import time
import itertools
import math
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


def make_cities(n, width=1000, height=1000):
    # make a set of n cities, each with random coordinates within a rectangle (width x height).

    random.seed(4)  # the current system time is used as a seed
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
    distance = 9999999
    for c in options:
        c_distance = ((city.x - c.x) ** 2 + (city.y - c.y) ** 2) ** (1/2)
        if c_distance < distance:
            distance = c_distance
            best = c
    return best


def plot_tsp(algorithm, cities):
    # apply a TSP algorithm to cities, print the time it took, and plot the resulting tour.

    start_time = time.time()
    tour = algorithm(cities)
    exec_time = time.time() - start_time

    print("{} city tour with length {:.1f} in {:.3f} secs for {}"
          .format(len(tour), tour_length(tour), exec_time, algorithm.__name__))
    print("Start plotting ...")
    plot_tour(tour)


# plot_tsp(try_all_tours, make_cities(10))
plot_tsp(nn_algorithm, make_cities(500))
