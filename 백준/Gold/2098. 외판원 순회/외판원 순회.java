import java.io.*;
import java.util.*;

public class Main {

    static int[][] dp, map;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


        N = Integer.parseInt(br.readLine());
        dp = new int[N][(1<<N)-1];
        map = new int[N][N];

        for(int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<N; i++) {
            Arrays.fill(dp[i], -1);
        }

        bw.write(dfs(0, 1) + "\n");


        bw.flush();
        bw.close();
        br.close();
    }

    /**
     *
     * @param now 현재 위치
     * @param check 방문 경로 비트 -> 1, 2, 4, 8, ... ==> 방문 체크
     */
    public static int dfs(int now, int check) {
        if(check == (1 << N) - 1) {
            if(map[now][0] == 0) {
                return 16_000_000;
            }
            return map[now][0];
        }

        if(dp[now][check] != -1) {
            return dp[now][check];
        }

        dp[now][check] = 16_000_000;

        for(int i=0; i<N; i++) {
            // 다음 도시를 방문하지 않았고,now에서 다음 도시인 i로 가는 경로가 있으면!!, 현재 저장된 최소 경로와 비교해서 최솟값 저장
            if((check & (1<<i)) == 0 && map[now][i] != 0) {
                dp[now][check] = Math.min(dfs(i, check | (1 << i)) + map[now][i], dp[now][check]);
            }
        }
        return dp[now][check];
    }
}