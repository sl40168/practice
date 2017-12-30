package sl40168.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimizeTree {
	static int[][] weight = new int[][] {
			{ 0, 10, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 11, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE },
			{ 10, 0, 18, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 16, Integer.MAX_VALUE, 12 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 22, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE, 8 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, 22, 0, 20, Integer.MAX_VALUE, Integer.MAX_VALUE, 16, 21 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 20, 0, 26, Integer.MAX_VALUE, 7,
					Integer.MAX_VALUE },
			{ 11, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 26, 0, 17, Integer.MAX_VALUE,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, 16, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 17, 0, 19,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 16, 7, Integer.MAX_VALUE, 19, 0,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, 12, 8, 21, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
					0 } };

	static void printPrim() {
		int[] vertex = new int[weight.length];
		for (int i = 0; i < weight.length; i++) {
			vertex[i] = i;
		}

		int[] lowcost = new int[10];
		int[] adjvex = new int[10];

		lowcost[0] = 0;// 当前所有已遍历节点与未遍历节点间最小距离
		adjvex[0] = 0;// 所有已遍历节点中与未遍历节点距离最小的节点

		int i, min, j, k;
		for (i = 1; i < vertex.length; i++) {
			lowcost[i] = weight[0][i];
			adjvex[i] = 0;
		}

		int total = 0;
		for (i = 1; i < vertex.length; i++) {
			min = Integer.MAX_VALUE;
			k = 0;
			j = 1;
			while (j < vertex.length) {
				if (lowcost[j] > 0 && lowcost[j] < min) {
					min = lowcost[j];
					k = j;
				}
				j++;
			}
			total += min;
			System.out.println("(" + adjvex[k] + "," + k + ") : total = " + total);
			lowcost[k] = 0;

			for (j = 1; j < vertex.length; j++) {
				if (lowcost[j] > 0 && weight[k][j] < lowcost[j]) {
					lowcost[j] = weight[k][j];
					adjvex[j] = k;
				}
			}
		}

	}

	static void printKruskal() {
		List<Edge> edges = buildEdges();
		int[] parent = new int[weight.length];
		for (int i = 0; i < parent.length;i++) {
			parent[i] = 0;
		}
		int n,m;
		int total = 0;
		for (int i = 0; i < edges.size();i++) {
			n = find(parent, edges.get(i).from);
			m = find(parent, edges.get(i).to);
			if (m != n) {
				parent[n] = m;
				total += edges.get(i).weight;
				System.out.println("(" + edges.get(i).from + "," + edges.get(i).to + ") : total = " + total);
			}
		}
	}
	
	static int find(int[] parent, int f) {
		while(parent[f] > 0)
			f = parent[f];
		return f;
	}

	static List<Edge> buildEdges() {
		List<Edge> edges = new ArrayList<MinimizeTree.Edge>();
		for (int i = 0; i < weight.length; i++) {
			for (int j = i + 1; j < weight.length; j++) {
				if (weight[i][j] > 0 && weight[i][j] < Integer.MAX_VALUE) {
					edges.add(new Edge(i, j, weight[i][j]));
				}
			}
		}
		Collections.sort(edges);
		return edges;
	}

	static class Edge implements Comparable<Edge> {

		private int from;
		private int to;
		private int weight;

		Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;

		}

		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}

	}

	public static void main(String[] args) {
		System.out.println("Prim:");
		printPrim();
		System.out.println("Krushkal:");
		printKruskal();
	}

}
