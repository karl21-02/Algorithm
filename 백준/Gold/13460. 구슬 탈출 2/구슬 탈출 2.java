import java.io.*;
import java.util.*;

public class Main {

    static class Node {
        int rx;
        int ry;
        int bx;
        int by;
        int count;

        public Node(int rx, int ry, int bx, int by, int count) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.count = count;
        }
    }

    static int n;
    static int m;
    static char[][] map;
    static int[] mx = {-1, 1, 0, 0};
    static int[] my = {0, 0, -1, 1};
    static boolean[][][][] visited;
    static int holeX;
    static int holeY;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        map = new char[n][m];
        visited = new boolean[n][m][n][m];

        int rx = 0, ry = 0, bx =0, by=0;
        for(int i=0; i<n; i++) {
            String line = br.readLine();
            for(int j=0; j<m; j++) {
                map[i][j] = line.charAt(j);

                if(map[i][j] == 'O') {
                    holeX = i;
                    holeY = j;
                }
                else if(map[i][j] == 'R') {
                    rx = i;
                    ry = j;
                }
                else if(map[i][j] == 'B') {
                    bx = i;
                    by = j;
                }
            }
        }

        Node redMarvel = new Node(rx, ry, 0, 0, 0);
        Node blueMarvel = new Node(0, 0, bx, by, 0);
        visited[rx][ry][bx][by] = true;

        bw.write(bfs(redMarvel, blueMarvel) + "");


        bw.flush();
        bw.close();
        br.close();
    }

    public static int bfs(Node redMarvel, Node blueMarvel) {
        Queue<Node> que = new LinkedList<>();
        Node node = new Node(
                redMarvel.rx,
                redMarvel.ry,
                blueMarvel.bx,
                blueMarvel.by,
                1
        );
        que.add(node);

        while(!que.isEmpty()) {
            Node marvel = que.poll();

            int rx = marvel.rx;
            int ry = marvel.ry;
            int bx = marvel.bx;
            int by = marvel.by;
            int count = marvel.count;

            if(count > 10) {
                return -1;
            }

            for(int i=0; i<4; i++) {

                int newRx = rx;
                int newRy = ry;
                int newBx = bx;
                int newBy = by;

                boolean isBlueMarbel = false;
                boolean isRedMarbel = false;

                // 빨간 구슬 벽 만날때 까지 이동
                while(map[newRx + mx[i]][newRy + my[i]] != '#') {
                    newRx = newRx + mx[i];
                    newRy = newRy + my[i];

                    if (holeX == newRx && holeY == newRy) {
                        isRedMarbel = true;
                        break;
                    }
                }

                // 파란 구슬 벽 만날때 까지 이동
                while(map[newBx + mx[i]][newBy + my[i]] != '#') {
                    newBx = newBx + mx[i];
                    newBy = newBy + my[i];

                    if(holeX == newBx && holeY == newBy) {
                        isBlueMarbel = true;
                        break;
                    }
                }

                if(isBlueMarbel) {
                    continue;
                }

                if(isRedMarbel && !isBlueMarbel) {
                    return count;
                }

                // 두 구슬 위치가 겹치는 경우, 방향에 따라 다르게 배치한다.
                if(newRx == newBx && newRy == newBy) {
                    if(i == 0) { // 위로 기운 경우
                        if(rx > bx) {
                            newRx -= mx[i];
                        }
                        else {
                            newBx -= mx[i];
                        }
                    }
                    else if(i == 1) {
                        if(rx < bx) {
                            newRx -= mx[i];
                        }
                        else {
                            newBx -= mx[i];
                        }
                    }
                    else if(i == 2) {
                        if(ry > by) {
                            newRy -= my[i];
                        }
                        else {
                            newBy -= my[i];
                        }
                    }
                    else {
                        if(ry < by) {
                            newRy -= my[i];
                        }
                        else {
                            newBy -= my[i];
                        }
                    }
                }

                if(!visited[newRx][newRy][newBx][newBy]) {
                    visited[newRx][newRy][newBx][newBy] = true;
                    que.add(new Node(newRx, newRy, newBx, newBy, count + 1));
                }
            }
        }
        return -1;
    }
}