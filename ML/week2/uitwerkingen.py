import numpy as np
import matplotlib.pyplot as plt
from scipy.sparse import csr_matrix


# ==== OPGAVE 1 ====
def plotNumber(nrVector):
    # Let op: de manier waarop de data is opgesteld vereist dat je gebruik maakt
    # van de Fortran index-volgorde – de eerste index verandert het snelst, de 
    # laatste index het langzaamst; als je dat niet doet, wordt het plaatje 
    # gespiegeld en geroteerd. Zie de documentatie op 
    # https://docs.scipy.org/doc/numpy/reference/generated/numpy.reshape.html

    matrix = nrVector.reshape(20, 20).T
    plt.matshow(matrix)
    plt.show()

    pass


# ==== OPGAVE 2a ====
def sigmoid(z):
    # Maak de code die de sigmoid van de input z teruggeeft. Zorg er hierbij
    # voor dat de code zowel werkt wanneer z een getal is als wanneer z een
    # vector is.
    # Maak gebruik van de methode exp() in NumPy.

    return 1 / (1 + np.exp(-z))


# ==== OPGAVE 2b ====
def get_y_matrix(y, m):
    # Gegeven een vector met waarden y_i van 1...x, retourneer een (ijle) matrix
    # van m×x met een 1 op positie y_i en een 0 op de overige posities.
    # Let op: de gegeven vector y is 1-based en de gevraagde matrix is 0-based,
    # dus als y_i=1, dan moet regel i in de matrix [1,0,0, ... 0] zijn, als
    # y_i=10, dan is regel i in de matrix [0,0,...1] (in dit geval is de breedte
    # van de matrix 10 (0-9), maar de methode moet werken voor elke waarde van 
    # y en m

    x = np.amax(y)
    m = y.shape[0]
    # rows = [i for i in range(m)]
    # cols = [i[0] - 1 for i in y]
    # data = [1 for _ in cols]
    # matrix = csr_matrix((data, (rows, cols)), shape=(m, x))
    # return matrix

    ret = np.zeros((m, x))
    for i in range(m):
        col = y[i] -1
        ret[i][col] = 1
    return ret


# ==== OPGAVE 2c ==== 
# ===== deel 1: =====
def predictNumber(Theta1, Theta2, X):
    # Deze methode moet een matrix teruggeven met de output van het netwerk
    # gegeven de waarden van Theta1 en Theta2. Elke regel in deze matrix 
    # is de waarschijnlijkheid dat het sample op die positie (i) het getal
    # is dat met de kolom correspondeert.

    # De matrices Theta1 en Theta2 corresponderen met het gewicht tussen de
    # input-laag en de verborgen laag, en tussen de verborgen laag en de
    # output-laag, respectievelijk. 

    # Een mogelijk stappenplan kan zijn:

    #    1. voeg enen toe aan de gegeven matrix X; dit is de input-matrix a1
    #    2. roep de sigmoid-functie van hierboven aan met a1 als actuele
    #       parameter: dit is de variabele a2
    #    3. voeg enen toe aan de matrix a2, dit is de input voor de laatste
    #       laag in het netwerk
    #    4. roep de sigmoid-functie aan op deze a2; dit is het uiteindelijke
    #       resultaat: de output van het netwerk aan de buitenste laag.

    # Voeg enen toe aan het begin van elke stap en reshape de uiteindelijke
    # vector zodat deze dezelfde dimensionaliteit heeft als y in de exercise.
    m, n = X.shape
    a1 = np.c_[np.ones(m), X]
    z2 = a1.dot(Theta1.T)
    a2 = sigmoid(z2)
    a2 = np.c_[np.ones(a2.shape[0]), a2]
    z3 = a2.dot(Theta2.T)
    a3 = sigmoid(z3)
    return a3


# ===== deel 2: =====
def computeCost(Theta1, Theta2, X, y):
    # Deze methode maakt gebruik van de methode predictNumber() die je hierboven hebt
    # geïmplementeerd. Hier wordt het voorspelde getal vergeleken met de werkelijk 
    # waarde (die in de parameter y is meegegeven) en wordt de totale kost van deze
    # voorspelling (dus met de huidige waarden van Theta1 en Theta2) berekend en
    # geretourneerd.
    # Let op: de y die hier binnenkomt is de m×1-vector met waarden van 1...10. 
    # Maak gebruik van de methode get_y_matrix() die je in opgave 2a hebt gemaakt
    # om deze om te zetten naar een matrix.
    m = X.shape[0]
    y_matrix = get_y_matrix(y, 10)
    prediction = predictNumber(Theta1, Theta2, X)
    part1 = y_matrix * np.log(prediction)
    part2 = (1 - y_matrix) * np.log(1 - prediction)
    sum = part1 + part2
    col = np.ones((10, 1))
    sum_k = sum.dot(col)
    row = np.ones((5000, 1))
    sum_j = sum_k.T.dot(row)
    total = -1 / m * sum_j
    return total


# ==== OPGAVE 3a ====
def sigmoidGradient(z):
    # Retourneer hier de waarde van de afgeleide van de sigmoïdefunctie.
    # Zie de opgave voor de exacte formule. Zorg ervoor dat deze werkt met
    # scalaire waarden en met vectoren.
    g = sigmoid(z)
    return g * (1 - g)


# ==== OPGAVE 3b ====
def nnCheckGradients(Theta1, Theta2, X, y):
    # Retourneer de gradiënten van Theta1 en Theta2, gegeven de waarden van X en van y
    # Zie het stappenplan in de opgaven voor een mogelijke uitwerking.

    #copy paste 2c deel 1
    m, n = X.shape
    a1 = np.c_[np.ones(m), X]
    z2 = a1.dot(Theta1.T)
    a2 = sigmoid(z2)
    a2 = np.c_[np.ones(a2.shape[0]), a2]
    z3 = a2.dot(Theta2.T)
    a3 = sigmoid(z3)
    #end copy paste

    y = get_y_matrix(y, m)
    delta3 = a3 - y
    z2p2 = sigmoidGradient(z2)
    delta2 = Theta2.T.dot(delta3.T).T * np.c_[np.ones(z2p2.shape[0]), z2p2]
    Delta3 = delta3.T.dot(a2)
    Delta2 = delta2.T.dot(a2)

    # Delta2 = np.zeros(Theta1.shape)
    # Delta3 = np.zeros(Theta2.shape)
    m = 1  # voorbeeldwaarde; dit moet je natuurlijk aanpassen naar de echte waarde van m



    for i in range(m):
        # YOUR CODE HERE

        pass
    # ones2 = np.ones
    Delta2_grad = Delta2 / m
    Delta3_grad = Delta3 / m

    return Delta2_grad, Delta3_grad
