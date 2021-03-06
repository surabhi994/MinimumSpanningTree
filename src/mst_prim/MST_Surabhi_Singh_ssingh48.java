package mst_prim;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Mst_prim {

 static int s=0;
 static int arr[];
 static int key_array[];
 static int position[];
 
 
 static int adjMat[][];// to store the weight
static  String input , output="";
    public static void main(String[] args) throws FileNotFoundException, IOException {
        input= "input.txt";
        output= "output.txt";
       FileReader fis = new FileReader(input);// reading input file
          Scanner sc = new Scanner(fis);
    PrintWriter out = new PrintWriter(new FileWriter(output));
     
     String l= sc.nextLine();
     String[] tempArr = l.split(" ");// splits the two element of first line and stores it in array
     
     int n=  Integer.parseInt(tempArr[0]);// stores the number of vertices in n from input file
     int m= Integer.parseInt(tempArr[1]);// stores the number of edges in m from input file
    adjMat= new int[n+1][n+1];// creates adjacency list to store the weights
     while(sc.hasNextLine())
     { //loop until the last line of given input file
    String l1 = sc.nextLine();
			String[] tempArr1 = l1.split(" ");// splitting the 3 elements of input
			adjMat[Integer.parseInt(tempArr1[0])][Integer
					.parseInt(tempArr1[1])] = adjMat[Integer.parseInt(tempArr1[1])][Integer.parseInt(tempArr1[0])] = Integer
							.parseInt(tempArr1[2]);								// file line and storing in array

			
			
		}
		
		arr = new int[n + 1];
		position = new int[n + 1];
		key_array = new int[n + 1];
		min_st(n, out);// calling minimum spanning tree function
                out.close();
	}

	static void min_st(int n, PrintWriter out) {
		int d[] = new int[n + 1];
		int edge[] = new int[n + 1];
		HashMap<Integer, Integer> edgeMap = new HashMap<Integer, Integer>();
		int u;
		int c = 0;
		int weight = 0;
		d[1] = 0;// dist of starting node is 0 from starting node

		for (int v = 2; v <= n; v++) {
			d[v] = Integer.MAX_VALUE;// storing largest number for rest of the
										// verticies
		}

		for (int v = 1; v <= n; v++) {
			insert(v, d[v]);
		}
		List<Integer> S = new ArrayList<Integer>();
		S.add(0);

		while (c < n) {
			u = extract_min();// extracting minimum value from heap

			
			S.add(u);// storing this u in a seperate set S which is set of
						// visited verticies
			
			c++;

			for (int v = 1; v <= n; v++) {
				if (adjMat[u][v] != 0 && adjMat[v][u] != 0) 
															
					if (!S.contains(v)) {
						if (adjMat[u][v] < d[v]) {
							d[v] = adjMat[u][v];
							decrease_key(v, d[v]);
							edge[v] = u;// stores the other end of edge
							edgeMap.put(v, u);
						}
					}

			}
		}
		for (int i = 2; i <= n; i++) {
			
			weight = weight + adjMat[S.get(i)][edgeMap.get(S.get(i))];
		}
		
                out.println(weight);
                  for (int i = 2; i <= n; i++) {
			
			out.println(edgeMap.get(S.get(i)) + " " + S.get(i));
		}
	}

	static void insert(int node, int key_value) {
		s = s + 1;// increase size of heap
		arr[s] = node;
		position[node] = s;
		key_array[node] = key_value;//storing key value in array
		heapify_up(s);

	}

	static void heapify_up(int size) {
		int temp, j = 0;
		while (size > 1) {
			j = (int) Math.floor(size / 2);
			if (key_array[arr[size]] < key_array[arr[j]]) {
				temp = arr[size];
				arr[size] = arr[j];
				arr[j] = temp;
				position[arr[size]] = size;
				position[arr[j]] = j;
				size = j;
			} else {
				break;
			}
		}
	}

	static int extract_min() {
		int minimum = arr[1];// storing root in minimum
		arr[1] = arr[s];//storing last element in root
		position[arr[1]] = 1;
		s--;//reducing size of heap
                 
                if(s>=0)
		heapify_down(1);//calling heapify down
                {
		return minimum;
                }

	}

	static void decrease_key(int node, int key_value) {
		key_array[node] = key_value;
		heapify_up(position[node]);

	}

	static void heapify_down(int i) {
		int j = 0;
		int temp = 0;
		while ((2 * i) <= s) {
			if ((2 * i) == s || key_array[arr[2 * i]] < key_array[arr[(2 * i) + 1]]) { //checking if left child is smaller than right child
				j = (2 * i);//store left child in j
			} else {
				j = (2 * i) + 1;//store right child in j
			}
			if (key_array[arr[j]] < key_array[arr[i]]) {// checking if child is less than parent
				temp = arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
				position[arr[i]] = i;
				position[arr[j]] = j;
				i = j;
			} else {
				break;
			}
		}

	}

}