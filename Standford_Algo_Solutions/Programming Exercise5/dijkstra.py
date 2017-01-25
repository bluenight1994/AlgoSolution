"""
	TASK:

	run Dijkstra's shortest-path algorithm on this graph, 
	use 1(the first vertex) as the source vertex
	compute the shortest-path distances between 1 and every other vertex
	if there is no path between a vertex v & vertex 1
	the distance is 1000000

	INPUT:

	dijkstraData.txt contains an adjacency list representation of an undirected weighted graph
	this graph with 200 vertices labeled 1 to 200.
	each row consists of the node tuples. (node, length of that edge)

	OUTPUT:

	report the shortest-path distances to the following 10 vertices
	7, 37, 59, 82, 99, 115, 133, 165, 188, 197
	seperate by comma
"""
from IndexMinPQ import IndexMinPQ

class DirectedEdge:

	def __init__(self, v, w, weight):
		self.v = v
		self.w = w
		self.weight = weight

	def tail(self):
		return self.v

	def head(self):
		return self.w

	def weight(self):
		return self.weight



class EdgeWeightedDigraph:

	def __init__(self, V):
		self.v = V
		self.adj = []
		for _ in range(V):
			self.adj.append([])

	def addEdge(self, e):
		v = e.tail()
		self.adj[v].append(e)

	def adjs(self, v):
		return self.adj[v]

	def V(self):
		return self.v

	def E(self):
		pass

	def edges(self):
		pass

	def toString(self):
		pass



class DijkstraSP:

	def __init__(self, G, s):
		self.distTo = [2**31] * G.V()
		self.edgeTo = [None] * G.V()
		self.pq = IndexMinPQ(G.V())
		self.distTo[s] = 0.0
		self.pq.insert(s, 0.0)
		while not self.pq.isEmpty():
			v = self.pq.delMin()
			for e in G.adjs(v):
				self.relax(e)

	def DistTo(self, v):
		return self.distTo[v]

	def PathTo(self, v):
		path = []
		e = self.edgeTo[v]
		while e != None:
			path.append(e.tail())
			e = self.edgeTo[e.tail()]
		return path

	def relax(self, e):
		v = e.tail()
		w = e.head()
		if self.distTo[w] > self.distTo[v] + e.weight:
			self.distTo[w] = self.distTo[v] + e.weight
			self.edgeTo[w] = e
			if self.pq.contains(w): 
				self.pq.decreaseKey(w, self.DistTo(w))
			else:
				self.pq.insert(w, self.DistTo(w))

def test():
	e1 = DirectedEdge(0, 1, 5)
	e2 = DirectedEdge(0, 7, 8)
	e3 = DirectedEdge(0, 4, 9)
	e4 = DirectedEdge(1, 3, 15)
	e5 = DirectedEdge(1, 2, 12)
	e6 = DirectedEdge(1, 7, 4)
	e7 = DirectedEdge(2, 3, 3)
	e8 = DirectedEdge(2, 6, 11)
	e9 = DirectedEdge(3, 6, 9)
	e10 = DirectedEdge(4, 5, 4)
	e11 = DirectedEdge(4, 6, 20)
	e12 = DirectedEdge(4, 7, 5)
	e13 = DirectedEdge(5, 2, 1)
	e14 = DirectedEdge(5, 6, 13)
	e15 = DirectedEdge(7, 5, 6.0)
	e16 = DirectedEdge(7, 2, 7.0)

	graph = EdgeWeightedDigraph(16)
	graph.addEdge(e1)
	graph.addEdge(e2)
	graph.addEdge(e3)
	graph.addEdge(e4)
	graph.addEdge(e5)
	graph.addEdge(e6)
	graph.addEdge(e7)
	graph.addEdge(e8)
	graph.addEdge(e9)
	graph.addEdge(e10)
	graph.addEdge(e11)
	graph.addEdge(e12)
	graph.addEdge(e13)
	graph.addEdge(e14)
	graph.addEdge(e15)
	graph.addEdge(e16)

	graph.addEdge(e1)

	sp = DijkstraSP(graph, 0)
	for i in range(1, 7):
		for t in sp.PathTo(i)[::-1]:
			print str(t) + " ",
		print 

def readFile(file):
	f = open(file, 'r')
	graph = EdgeWeightedDigraph(201)
	lines = f.readlines()
	for line in lines:
		line = line.strip().split()
		src = int(line[0])
		for tuples in line[1:]:
			dst = int(tuples.split(',')[0])
			wei = float(tuples.split(',')[1])
			e = DirectedEdge(src, dst, wei)
			graph.addEdge(e)
	return graph

graph = readFile("dijkstraData.txt")
sp = DijkstraSP(graph, 1)
tests = [7, 37, 59, 82, 99, 115, 133, 165, 188, 197]
ret = []
for test in tests:
	ret.append(int(sp.DistTo(test)))
	for t in sp.PathTo(test)[::-1]:
		print str(t) + "->",
	print str(test)
print ret






