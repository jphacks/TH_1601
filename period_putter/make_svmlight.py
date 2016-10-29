f = open('SC_vec3.dat', 'r')
fo = open('SC_svmlight3.txt', 'w')
line = f.readline()[:-1]
while line:
	fo.write(str(line[200]) + " ")
	for i in range(199):
			fo.write(str(i+1) + ":" + line[i] + " ")
	fo.write(str(200) + ":" + line[199] + "\n")

	line = f.readline()[:-1]
