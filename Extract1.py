import numpy as np

#visualization libraries
#import matplotlib.pyplot as plt
#import seaborn as sns
#ignore warnings
import pandas as pd
import warnings

from sklearn import model_selection
from sklearn.metrics import accuracy_score
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier

warnings.filterwarnings('ignore')

class Project1:

    def getlistbymarks(self, x):
        df = pd.read_csv("E:/cap2.csv")
        df1 = df[(df['Marks'] >= x - 2) & (df['Marks'] <= x + 2) & (df['Caste'] == 'Open') ]
        return df1

    def getlistbymarksandcaste(self, x, y):
        df = pd.read_csv("E:/cap2.csv")
        x = int(x)
        y = str(y)
        df1 = df[(df['Marks'] >= x ) & (df['Marks'] <= x ) & (df['Caste'] == y)]
        #df2 = df1['Marks'], df1['Caste'], df1['Bname'], df1['Clname']
        #print(df2)
        ls = list(df1)
        return ls

    def getlistbymarkscasteandbranch(self, x, y, z):
        df = pd.read_csv("E:/cap2.csv")
        x = int(x)
        y = str(y)
        z = str(z)
        df1 = df[(df['Marks'] >= x - 2) & (df['Marks'] <= x + 2) & (df['Caste'] == y) & (df['Bname'] == z)]
        #df2 = df1['Marks'], df1['Caste'], df1['Bname'], df1['Clname']
        df2 = df1
        ls = str(df2)
        print("Inside Extract1.py result set is -\n" + ls)

        return df2

#getlistbyrank()
# print(df1['Clname'],df1['Bname'])