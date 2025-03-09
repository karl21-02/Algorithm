import java.io.*;
import java.util.*;

public class Main {

    static class Node {
        int e;
        int c;

        public Node(int e, int c) {
            this.e = e;
            this.c = c;
        }
    }

    static int N;
    static int K;
    static int M;
    static List<List<Integer>> list = new ArrayList<>();
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer stk = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(stk.nextToken());
        K = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        dp = new int[N + M];

        for(int i = 0; i < N + M; i++) {
            list.add(new ArrayList<>());
            dp[i] = Integer.MAX_VALUE;
        }

        for(int i=0; i<M && N != 1; i++) {
            String[] in = br.readLine().split(" ");
            int hyperStation = N + i;

            for(int j=0; j<K; j++) {
                int station = Integer.parseInt(in[j]) - 1;
                list.get(hyperStation).add(station);
                list.get(station).add(hyperStation);
            }
        }

        Queue<Integer> que = new LinkedList<>();
        que.add(0);
        dp[0] = 1;

        boolean[] visited = new boolean[N + M];

        while(!que.isEmpty()) {
            int curStation = que.poll();

            if(curStation == N-1) {
                bw.write(dp[curStation] + "\n");
                bw.flush();
                System.exit(0);
                return;
            }
            if(visited[curStation]) visited[curStation] = true;

            for(Integer nextStation : list.get(curStation)) {
                if(!visited[nextStation]) {
                    visited[nextStation] = true;
                    if(nextStation < N) {
                        dp[nextStation] = dp[curStation] + 1;
                        que.add(nextStation);
                    }
                    else {
                        dp[nextStation] = dp[curStation];
                        que.add(nextStation);
                    }
                }
            }
        }

        bw.write("-1");

        bw.flush();
        bw.close();
        br.close();
    }
}