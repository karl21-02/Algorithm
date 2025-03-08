import java.io.*;
import java.util.*;

public class Main {

    static long[] arr, tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer stk = new StringTokenizer(br.readLine(), " ");

        int n, m, k;
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());


        arr = new long[n+1];
        for(int i=1; i<=n; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        tree = new long[n*4];

        init(1, n, 1);

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<m + k; i++) {
            stk = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(stk.nextToken());
            int b = Integer.parseInt(stk.nextToken());
            long c = Long.parseLong(stk.nextToken());

            if(a == 1) {
                long diff = c - arr[b];
                arr[b] = c;
                update(1, n, 1, b, diff);
            }
            else if(a == 2){
                sb.append(sum(1, n, 1, b, (int)c) + "\n");
            }
        }

        bw.write(sb.toString());

        bw.flush();
        bw.close();
        br.close();
    }

    /**
     *
     * @param start 시작 인덱스
     * @param end 끝 인덱스
     * @param node 저장할 노드 위치 번호
     * @return
     */
    public static long init(int start, int end, int node) {
        if(start == end) {
            return tree[node] = arr[start];
        }
        int mid = (start + end)/2;

        return tree[node] = init(start, mid, node * 2) + init(mid + 1, end, node * 2 + 1);
    }

    public static long sum(int start, int end, int node, int left, int right) {
        if(left > end || right < start) {
            return 0;
        }

        if(left <= start && end <= right) {
            return tree[node];
        }
        int mid = (start + end)/2;
        return sum(start, mid, node*2, left, right) + sum(mid + 1, end, node*2+1, left, right);
    }

    public static void update(int start, int end, int node, int idx, long dif) {
        if(idx < start || idx > end) {
            return;
        }

        tree[node] += dif;
        if (start == end) {
            return;
        }

        int mid = (start + end) / 2;
        update(start, mid, node*2, idx, dif);
        update(mid+1, end, node*2+1, idx, dif);
    }
}