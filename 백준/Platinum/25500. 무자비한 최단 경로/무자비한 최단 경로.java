import java.io.*;
import java.util.*;

public class Main {

    static class Node implements Comparable<Node> {
        int e;
        int c;

        public Node(int e, int c) {
            this.e = e;
            this.c = c;
        }

        @Override
        public int compareTo(Node o) {
            return this.c - o.c;
        }
    }

    static int N;
    static int K;
    static List<List<Node>> list = new ArrayList<>();
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer stk = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(stk.nextToken());
        K = Integer.parseInt(stk.nextToken());


        for(int i=0; i<N + K;i ++) {
            list.add(new ArrayList<>());
        }

        int[][] position = new int[N][3]; // (x, y, z)
        dp = new int[N + K];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for(int i=0; i<N; i++) {
            stk = new StringTokenizer(br.readLine(), " ");
            position[i][0] = Integer.parseInt(stk.nextToken());
            position[i][1] = Integer.parseInt(stk.nextToken());
            position[i][2] = Integer.parseInt(stk.nextToken());
        }

        // make ways
        Integer[] idx = new Integer[N];
        for (int i = 0; i < N; i++) idx[i] = i;
        Arrays.sort(idx, Comparator.comparingInt(i -> position[i][0]));
        for (int i = 0; i < N - 1; i++) {
            int u = idx[i], v = idx[i + 1];
            int cost = Math.abs(position[u][0] - position[v][0]);
            list.get(u).add(new Node(v, cost));
            list.get(v).add(new Node(u, cost));
        }

        Arrays.sort(idx, Comparator.comparingInt(i -> position[i][1]));
        for (int i = 0; i < N - 1; i++) {
            int u = idx[i], v = idx[i + 1];
            int cost = Math.abs(position[u][1] - position[v][1]);
            list.get(u).add(new Node(v, cost));
            list.get(v).add(new Node(u, cost));
        }

        // Z 좌표 처리
        for (int i = 0; i < N; i++) {
            list.get(N + position[i][2] % K).add(new Node(i, position[i][2]));
            list.get(i).add(new Node(N + (K - position[i][2] % K) % K, position[i][2]));
        }
        dijstra(0);

        for(int i=0; i<N; i++) {
            bw.write(dp[i] + "\n");
        }



        bw.flush();
        bw.close();
        br.close();
    }

    public static void dijstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        dp[start] = 0;

        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            if(dp[cur.e] < cur.c) {continue;}

            for(Node next : list.get(cur.e)) {
                if(dp[next.e] > cur.c + next.c
                ) {
                    dp[next.e] = cur.c + next.c;
                    pq.add(new Node(next.e, dp[next.e]));
                }
            }
        }
    }
}