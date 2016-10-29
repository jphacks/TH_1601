from sklearn.datasets import load_svmlight_file
from sklearn.grid_search import GridSearchCV
from sklearn.cross_validation import train_test_split
from sklearn.metrics import classification_report
from sklearn.svm import SVC
from sklearn.externals import joblib
import numpy as np

#SVC(C=10, cache_size=200, class_weight=None, coef0=0.0,
#  decision_function_shape=None, degree=3, gamma='auto', kernel='linear',
#  max_iter=-1, probability=False, random_state=None, shrinking=True,
#  tol=0.001, verbose=False)


def grid_search(train_features, train_labels):
    param_grid = [
        {'C': [1, 10, 100, 1000], 'kernel': ['linear']},
        {'C': [1, 10, 100, 1000], 'gamma': [0.001, 0.0001], 'kernel': ['rbf']},
    ]

    clf = GridSearchCV(SVC(C=1), param_grid, n_jobs=-1)
    clf.fit(train_features, train_labels)
    print(clf.best_estimator_)

x,y = load_svmlight_file('SC_svmlight3.txt')
x = x.toarray()

clf = SVC(C=1, cache_size=200, class_weight=None, coef0=0.0,
  decision_function_shape=None, degree=3, gamma='auto', kernel='linear',
  max_iter=-1, probability=False, random_state=None, shrinking=True,
  tol=0.001, verbose=False)

#clf = joblib.load('clf3.pkl')

x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=0)
#grid_search(x_train, y_train)

clf.fit(x_train, y_train)
print(clf.score(x_test, y_test))

joblib.dump(clf, 'clf3.pkl')
