# -*- coding: utf-8 -*-

#機械学習関連
from sklearn.datasets import load_svmlight_file
from sklearn.cross_validation import train_test_split
from sklearn.grid_search import GridSearchCV
from sklearn.metrics import classification_report
from sklearn.svm import SVC
from sklearn.externals import joblib
import numpy

#
import urllib.request
import json

class Period_putter():

	def __init__(self):
		self.clf = joblib.load('clf.pkl')

	#形態素解析APIにPOSTを投げる
	def post(self, contents):
		url = "https://labs.goo.ne.jp/api/morph"
		data = {"app_id":"93a5bf3d466870df366b5e860277fcf930695424168936452c31dfff0b8218a0", "sentence":contents, "info_filter":"pos"}
		json_data = json.dumps(data).encode("utf-8")
		method = "POST"
		headers = {"Content-Type" : "application/json"}

		# httpリクエストを準備してPOST
		request = urllib.request.Request(url, data=json_data, method=method, headers=headers)
		with urllib.request.urlopen(request) as response:
			response_body = response.read().decode("utf-8")
			return response_body
		return None 

	#ピリオド挿入プロセス全体
	def put_period(self, plane_string):
		print(plane_string)
		print(self.post(plane_string))




		return

pp = Period_putter()
pp.put_period("入れるよお大事にね")
