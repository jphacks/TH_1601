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
		self.all_pos_list = ['名詞', '名詞接尾辞', '冠名詞', '英語接尾辞', '動詞語幹', '動詞活用語尾', '動詞接尾辞', '冠動詞', '補助名詞', '形容詞語幹', '形容詞接尾辞', '冠形容詞', '連体詞', '連用詞', '接続詞', '独立詞', '接続接尾辞', '判定詞', '格助詞', '引用助詞', '連用助詞', '終助詞', '間投詞', '括弧', '句点', '読点', 'Symbol', 'Year', 'Month', 'Day', 'YearMonth', 'MonthDay', 'Hour', 'Minute', 'Second', 'HourMinute', 'MinuteSecond', 'PreHour', 'PostHour', 'Number', '助数詞', '助助数詞', '冠数詞', 'Alphabet', 'Kana', 'Katakana', 'Kanji', 'Roman', 'Undef']
		#解析した文のベクトルを入れる配列
		self.vec = [0] * 200

	#ベクトルを2値ベクトルに変換
	def to_binary_vec(self, vec_dec):
		for (i,item) in enumerate(vec_dec):
			for j in range(50):
				if j == 49:
					self.vec[i] = (int(item == -1)
				else:
					print(int(item == i), end="")


	#形態素解析の結果から分類器用のベクトルをつくり，分類する
	def make_vec(self, result_json):
		#print(result_json)
		sentences = json.loads(result_json)["word_list"]
		pos_list = []

		#句点のない文章だからここでは必ず1文になるはずだけど，一応文分割処理入れておく
		for sentence in sentences:
			for pos in sentence:
				pos_list.append(pos[0])

		n = len(pos_list)
		#句点の入る位置と思われる場所を基準として[2つ前，1つ前，1つ後，2つ後]
		vec_dec = [-1, -1, -1, -1]
		for i in range(0, n-1):
			vec_dec[0] = vec_dec[1]
			vec_dec[1] = pos_list[i] if vec_dec[2] == -1 else vec_dec[2]
			vec_dec[2] = pos_list[i+1] if vec_dec[3] == -1 else vec_dec[3]
			vec_dec[3] = pos_list[i+2] if i < n-2 else -1
			print(vec_dec)
			self.to_binary_vec(vec_dec)

			#self.clf.predict(#予測したいベクトルを入れる)


		return pos_list


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
		result_json = self.post(plane_string)
		vec_binary = self.make_vec(result_json)
		return

pp = Period_putter()
pp.put_period("入れるよお大事にね")