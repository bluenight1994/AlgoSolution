"""
Input:
	In kargerMinCut.txt is the adjacency list representation of a simple undirect graph
	There are 200 vertices labeled from 1 to 200.
	First column of each row is the vertex label,
	after the labels, all the vertexs that is adjacent with this vertex is given.

Task:
	code up and run the randomized contraction algorithm for the min cut problem on the given graph

Random Contraction Algorithm (David Karger)
	while there are more that 2 vertices:
		- pick a remaining edge (u, v) uniformly at random
		- merge u & v into a single vertex
		- remove self-loops

"""

from collections import defaultdict
from random import choice

"""
	read and construct a graph from input txt
	use dict in python to represent the graph
"""
def readFile(file):
	f = open(file, 'r')
	lines = f.readlines()
	graph = defaultdict(list)
	for line in lines:
		line = [int(num) for num in line.strip().split()]
		graph[line[0]] = line[1:]
	return graph

def randomContraction(graph):
	vertices = len(graph.keys())
	for i in range(vertices-2):
		# pick a remaining edge (u, v)
		u = choice(graph.keys())
		v = choice(graph[u])
		# merge u & v, keep u and delete v
		ad_v = graph[v]
		ad_u = graph[u]
		new_vertex = ad_v + ad_u
		while v in new_vertex:
			new_vertex.remove(v)
		while u in new_vertex:
			new_vertex.remove(u)
		graph[u] = new_vertex
		del(graph[v])
		for tmp in graph.keys():
			if tmp != u:
				while v in graph[tmp]:
					graph[tmp].remove(v)
					graph[tmp].append(u)
		if i == vertices-3:
			return len(graph[u])
	return graph

# graph = defaultdict(list)
# graph[1] = [2,3]
# graph[2] = [1,3,4]
# graph[3] = [1,2,4]
# graph[4] = [2,3]

graph = readFile('kargerMinCut.txt')
cut = 2**31

for _ in range(1000):
	cut = min(cut, randomContraction(graph))

print cut


# the min cut is 17

