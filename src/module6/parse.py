import xmltodict
import pprint
import json


def get_mag(val):
    a = val.split(' - ')[0]
    b = float(a.split(' ')[1])
    return b


def sort_function(a, b):
    return get_mag(a) - get_mag(b)


with open('data/quiz2.atom') as fd:
    doc = xmltodict.parse(fd.read())
    titles = [v['title'] for v in doc['feed']['entry']]
    titles.sort(key=get_mag)
    for title in titles[-15:]:
        print(title)
