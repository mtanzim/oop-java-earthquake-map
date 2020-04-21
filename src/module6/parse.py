import xmltodict
import pprint
import json
import matplotlib.pyplot as plt
import numpy as np


def get_mag(val):
    a = val.split(' - ')[0]
    b = float(a.split(' ')[1])
    return b


def sort_function(a, b):
    return get_mag(a) - get_mag(b)


if __name__ == "__main__":
    plt.rcdefaults()
    fig, ax = plt.subplots()
    NUM_ELEMS = 20

    with open('data/quiz2.atom') as fd:
        doc = xmltodict.parse(fd.read())
        titles = [v['title'] for v in doc['feed']['entry']]
        titles.sort(key=get_mag)
        sorted_titles = titles[-NUM_ELEMS:]
        for title in sorted_titles:
            print(title)
        y_pos = np.arange(len(sorted_titles))
        mags = [get_mag(eq) for eq in sorted_titles]
        ax.barh(y_pos, mags, align='center')
        ax.set_yticks(y_pos)
        ax.set_yticklabels(titles)
        plt.show()
