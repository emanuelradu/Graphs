import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class P1 {
	static class Task {
		public static final String INPUT_FILE = "p1.in";
		public static final String OUTPUT_FILE = "p1.out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		// clasa pentru retinerea perechilor de noduri intre care exista muchie
		public class Edge {
			public Integer node1;
			public Integer node2;

			Edge(Integer node1, Integer node2) {
				this.node1 = node1;
				this.node2 = node2;
			}
		}

		@SuppressWarnings("unchecked")
		// lista perechilor de muchii din graf
		ArrayList<Edge> edges = new ArrayList<Edge>();
		// vectorul de distante
		Integer[] distances;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(INPUT_FILE)));
				n = sc.nextInt();
				distances = new Integer[n + 1];
				distances[0] = 0;
				for (int i = 1; i <= n; i++) {
					distances[i] = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Edge> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE)));
				if (result.size() == 0) {
					pw.printf("-1");
				} else {
					pw.printf("%d\n", result.size());
					for (Edge edge : result) {
						pw.printf("%d %d\n", edge.node1, edge.node2);
					}
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<Edge> getResult() {
			Integer index;
			int i;
			// folosesc distArray pt a obtine numarul nodului care are o anumita
			// distanta fata de nodul sursa
			ArrayList<Integer> distArray = new ArrayList<Integer>(Arrays.asList(distances));

			Arrays.sort(distances);

			for (i = 1; i <= n; i++) {
				// nod sursa
				if (i == 1 && distances[i] == 0) {
					continue;
				}
				// caz nevalid
				if (i != 1 && distances[i] == 0) {
					return new ArrayList<Edge>();
				}
				// caz nevalid
				if (distances[i] - distances[i - 1] > 1) {
					return new ArrayList<Edge>();
				}

				// adaug muchie intre nod si sursa
				if (distances[i] == 1) {
					index = distArray.indexOf(distances[i]);
					edges.add(new Edge(1, index));
					if (index != -1) {
						distArray.set(index, -1);
					}
					continue;
				}

				else {
					// nodul curent se va lega la parintele nodului precedent
					if (distances[i] - distances[i - 1] == 0) {
						index = distArray.indexOf(distances[i]);
						edges.add(new Edge(edges.get(edges.size() - 1).node1, index));
						if (index != -1) {
							distArray.set(index, -1);
						}
					}
					// nodul curent se va lega la nodul precedent
					if (distances[i] - distances[i - 1] == 1) {
						index = distArray.indexOf(distances[i]);
						edges.add(new Edge(edges.get(edges.size() - 1).node2, index));
						if (index != -1) {
							distArray.set(index, -1);
						}
					}
				}
			}

			return edges;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
