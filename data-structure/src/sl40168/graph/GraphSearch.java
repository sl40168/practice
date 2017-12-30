package sl40168.graph;

public class GraphSearch {
	static int[][] weight = new int[][] {
			{ 0, 1, 5, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE },
			{ 1, 0, 3, 7, 5, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE },
			{ 5, 3, 0, Integer.MAX_VALUE, 1, 7, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, 7, Integer.MAX_VALUE, 0, 2, Integer.MAX_VALUE, 3, Integer.MAX_VALUE,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, 5, 1, 2, 0, 3, 6, 9, Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, 7, Integer.MAX_VALUE, 3, 0, Integer.MAX_VALUE, 5,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3, 6, Integer.MAX_VALUE, 0, 2, 7 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 9, 5, 2, 0, 4 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE, 7, 4, 0 } };

	public static void main(String[] args) {
		int[][] test = new int[][] {{0, 4,1, 10}, {4, 0, 2, 5}, {1,2,0,Integer.MAX_VALUE},{10,5,Integer.MAX_VALUE,0}};
		floyd(test);

	}
	
	static int[][] floyd(int[][] weight) {
		int[][] distance = new int[weight.length][];
		for (int i = 0; i < weight.length;i++) {
			distance[i] = new int[weight[i].length];
			for (int j = 0; j < distance[i].length; j++) {
				distance[i][j] = weight[i][j];
			}
		}
		for (int k = 0; k < distance.length;k++) {
			for (int m = 0; m < distance.length;m++) {
				for (int n = 0; n < distance.length;n++) {
					if (distance[m][n] > distance[m][k] + distance[k][n]) {
						System.out.println("(" + m + "," + n + ")->(" + m + "," + k + ") + (" + k + "," + n + ")");
						System.out.println(distance[m][n] + "->" + distance[m][k] + "+" + distance[k][n]);
						distance[m][n] = distance[m][k] + distance[k][n];
					}
				}
			}
		}
		return distance;
	}

	static int[] dijkstra(int[][] weight) {
		boolean[] gotten = new boolean[weight.length];
		gotten[0] = true;
		for (int i = 1; i < gotten.length; i++)
			gotten[i] = false;
		int[] path = new int[weight.length];
		int[] lowcost = new int[weight.length];

		for (int i = 0; i < weight[0].length; i++) {
			lowcost[i] = weight[0][i];
			path[i] = 0;
		}

		int v, w, k = 0, min;
		for (v = 1; v < weight.length; v++) {
			/*
			 * 每次取未标记节点中距起点距离最短者， 保证今后不可能出现通过未标记节点中转距离小于当前距离
			 */
			min = Integer.MAX_VALUE;
			for (w = 0; w < weight.length; w++) {
				if (!gotten[w] && lowcost[w] < min) {
					min = lowcost[w];
					k = w;
				}
			}
			gotten[k] = true;
			for (w = 0; w < weight.length; w++) {
				if (!gotten[w] && weight[k][w] != Integer.MAX_VALUE && (min + weight[k][w]) < lowcost[w]) {
					lowcost[w] = min + weight[k][w];
					path[w] = k;
				}
			}
		}
		return path;
	}
}
