import numpy as np
import pandas as pd
#visualization libraries
import matplotlib.pyplot as plt
import seaborn as sns
#ignore warnings
import warnings
from sklearn import model_selection
from sklearn.metrics import accuracy_score
from sklearn.neighbors import KNeighborsClassifier
warnings.filterwarnings('ignore')

class predict:
    print('Hello')

    def prediction(self, marks, caste):

        marks = int(marks)
        caste = str(caste)
        caste = caste.upper()
        df = pd.read_csv("E:/cap2.csv")
        print(df)
        if caste == 'OPEN':
            z = 1
        elif caste == 'SC':
            z = 2
        elif caste == 'ST':
            z = 3
        elif caste == 'VJ':
            z = 4
        elif caste == 'OBC':
            z = 0
        else:
            z = 1
        X = df.iloc[:, [0, 1]]
        print('X is - \n')
        print(X)
        Y = df.iloc[:, 2]
        print('Y is - \n')
        print(Y)
        from sklearn.preprocessing import LabelEncoder
        labelencoder = LabelEncoder()
        X['Caste'] = labelencoder.fit_transform(X['Caste'])
        print('X is - ')
        print(X)
        validation_size = 0.20
        seed = 7
        X_train, X_validation, Y_train, Y_validation = model_selection.train_test_split(X, Y, test_size=validation_size, random_state=seed)
        knn = KNeighborsClassifier()
        # train
        knn.fit(X_train, Y_train)
        X1 = [
            [z, marks]
        ]
        predictions = knn.predict(X1)
        print('Prediction is ')
        print(predictions)

        #df1 = df[(df['Marks'] >= marks ) & (df['Marks'] <= marks ) & (df['Caste'] == caste)]
        #ls = list(df1)
        #return ls

ob = predict()
#df = ob.prediction(130,'open')
ob.prediction(110,'open')
print('Out now!')
#print(df)

