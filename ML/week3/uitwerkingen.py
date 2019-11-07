import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf
from tensorflow import keras
from keras.models import Sequential

# OPGAVE 1a
def plotImage(img, label):
    # Deze methode krijgt een matrix mee (in img) en een label dat correspondeert met het 
    # plaatje dat in de matrix is weergegeven. Zorg ervoor dat dit grafisch wordt weergegeven.
    # Maak gebruik van plt.cm.binary voor de cmap-parameter van plt.imgshow.

    # YOUR CODE HERE
    plt.imshow(img, cmap=plt.cm.binary, interpolation='nearest')
    plt.title(label)
    plt.axis('off')
    plt.show()
    pass


# OPGAVE 1b
def scaleData(X):
    # Deze methode krijgt een matrix mee waarin getallen zijn opgeslagen van 0..m, en hij 
    # moet dezelfde matrix retourneren met waarden van 0..1. Deze methode moet werken voor 
    # alle maximal waarde die in de matrix voorkomt.
    # Deel alle elementen in de matrix 'element wise' door de grootste waarde in deze matrix.

    # YOUR CODE HERE
    max_value = np.amax(X)
    offset = np.full(X.shape, 1 / max_value)
    return X * offset


# OPGAVE 1c
def buildModel():
    # Deze methode maakt het keras-model dat we gebruiken voor de classificatie van de mnist
    # dataset. Je hoeft deze niet abstract te maken, dus je kunt er van uitgaan dat de input
    # layer van dit netwert alleen geschikt is voor de plaatjes in de opgave (wat is de 
    # dimensionaliteit hiervan?).
    # Maak een model met een input-laag, een volledig verbonden verborgen laag en een softmax
    # output-laag. Compileer het netwerk vervolgens met de gegevens die in opgave gegeven zijn
    # en retourneer het resultaat.

    from keras.models import Sequential
    from keras.layers import Dense, Activation, Flatten

    model = keras.Sequential([
        keras.layers.Flatten(input_shape=(28, 28)),
        keras.layers.Dense(128, activation='relu'),
        keras.layers.Dense(10, activation='softmax')
    ])
    model.compile(optimizer='adam',
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])
    return model


# OPGAVE 2a
def confMatrix(labels, predictions):
    # Retourneer de econfusion matrix op basis van de gegeven voorspelling (pred) en de actuele
    # waarden (labels). Check de documentatie van tf.math.confusion_matrix
    tmp = tf.math.confusion_matrix(
        labels,
        predictions,
        num_classes=None,
        weights=None,
        dtype=tf.dtypes.int32,
        name=None
    )
    return tmp


# OPGAVE 2b
def confEls(conf, labels):
    # Deze methode krijgt een confusion matrix mee (conf) en een set van labels. Als het goed is, is 
    # de dimensionaliteit van de matrix gelijk aan len(labels) × len(labels) (waarom?). Bereken de 
    # waarden van de TP, FP, FN en TN conform de berekening in de opgave. Maak vervolgens gebruik van
    # de methodes zip() en list() om een list van len(labels) te retourneren, waarbij elke tupel 
    # als volgt is gedefinieerd:

    #     (categorie:string, tp:int, fp:int, fn:int, tn:int)

    # Check de documentatie van numpy diagonal om de eerste waarde te bepalen.

    # YOUR CODE HERE
    tp = (conf * np.identity(10))
    fp = (conf - tp).T
    fn = (conf - tp)
    tn = conf - tp - fp - fn

    tp = tp.dot(np.ones((10, 1)))
    fp = fp.dot(np.ones((10, 1)))
    fn = fn.dot(np.ones((10, 1)))
    tn = tn.dot(np.ones((10, 1)))

    ret = list()
    for i in range(10):
        ret.append((labels[0], tp[i][0], fp[i][0], fn[i][0], tn[i][0]))
    return ret


# OPGAVE 2c
def confData(metrics):
    # Deze methode krijgt de lijst mee die je in de vorige opgave hebt gemaakt (dus met lengte len(labels))
    # Maak gebruik van een list-comprehension om de totale tp, fp, fn, en tn te berekenen en 
    # bepaal vervolgens de metrieken die in de opgave genoemd zijn. Retourneer deze waarden in de
    # vorm van een dictionary (de scaffold hiervan is gegeven).

    # VERVANG ONDERSTAANDE REGELS MET JE EIGEN CODE

    tp = 0
    fp = 0
    fn = 0
    tn = 0
    for i in range(len(metrics)):
        tp += metrics[i][1]
        fp += metrics[i][2]
        fn += metrics[i][3]
        tn += metrics[i][4]

    # BEREKEN HIERONDER DE JUISTE METRIEKEN EN RETOURNEER DIE 
    # ALS EEN DICTIONARY

    tpr = tp / (tp + fn)
    ppv = tp / (tp + fp)
    tnr = tn / (tn + fp)
    fpr = fp / (fp + tn)
    rv = {'tpr': tpr, 'ppv': ppv, 'tnr': tnr, 'fpr': fpr}
    return rv
