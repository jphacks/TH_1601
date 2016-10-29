f = open('SC_vec.dat', 'r')
fo = open('SC_svmlight.txt', 'w')
line = f.readline()[:-1]
while line:
	fo.write(str(line[250]) + " ")
	for i in range(100):
			fo.write(str(i+1) + ":" + line[i] + " ")
	for i in range(150, 249):
			fo.write(str(i-50+1) + ":" + line[i] + " ")
	fo.write(str(200) + ":" + line[249] + "\n")

	line = f.readline()[:-1]