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


def make_cities(n, seed=1, width=1000, height=1000):
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


# Create a tour by taking a city as start point and adding the closed city as next point in the tour
# repeat this until all cities are added
def nearest_neighbour_algorithm(cities):
    to_go = set(cities)
    start = to_go.pop()
    path = [start]
    while to_go:
        nearest_neighbour = find_nearest_neighbour(path[len(path) - 1], to_go)
        to_go.remove(nearest_neighbour)
        path.append(nearest_neighbour)
    return path


# return the city from the give options which is closed to the given current city
def find_nearest_neighbour(current_city, city_options):
    best = None
    euclidean_distance = 9999999
    for new_city in city_options:
        city_distance = (current_city.x - new_city.x) ** 2 + (current_city.y - new_city.y) ** 2
        if city_distance < euclidean_distance:
            euclidean_distance = city_distance
            best = new_city
    return best


# switch the edges in the given path.
# both indexes are the location of first vertex of both edges
def switch_edges(path, vertex_index_1, vertex_index_2):
    start = path[:vertex_index_1] if vertex_index_1 > 0 else []
    end = path[vertex_index_2 + 1:]
    mid = path[vertex_index_1:vertex_index_2 + 1]
    mid.reverse()
    new_path = start + mid + end
    return new_path


def plot_tsp(algorithm, cities):
    # apply a TSP algorithm to cities, print the time it took, and plot the resulting tour.

    start_time = time.time()
    tour = algorithm(cities)
    exec_time = time.time() - start_time

    print("{} city tour with length {:.1f} in {:.3f} secs for {}"
          .format(len(tour), tour_length(tour), exec_time, algorithm.__name__))
    print("Start plotting ...")
    plot_tour(tour)


# find the fastest path using 2-opt for optimization
def two_opt(cities):
    # using nearest neighbour as start point to improve execution time
    tour = nearest_neighbour_algorithm(cities)
    for i in range(300):
        new_tour = improve_tour(tour)
        if new_tour == tour:
            return new_tour
        else:
            tour = new_tour
    return tour


# Take a proposed switch and return true if it makes the path shorter and false if not
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


# Try to switch every edges with one another and keep the change if it reduces the path length
# returns the resulting tour path
def improve_tour(tour):
    for i in range(len(tour)):
        for j in range(i + 1, len(tour)):
            if test_proposal(tour, i, j):
                tour = switch_edges(tour, i + 1, j)
    return tour


# plot_tsp(try_all_tours, make_cities(10))
plot_tsp(nearest_neighbour_algorithm, make_cities(500, 1))
plot_tsp(two_opt, make_cities(500, 1))
