import java.io.*;
import java.util.*;

public class Main {

    static class Node {
        int rx;
        int ry;
        int bx;
        int by;
        int count;
        StringBuilder sb;

        public Node(int rx, int ry, int bx, int by, int count, StringBuilder sb) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.count = count;
            this.sb = sb;
        }
    }

    static int n;
    static int m;
    static char[][] map;
    static int[] mx = {-1, 1, 0, 0};
    static int[] my = {0, 0, -1, 1};
    static String[] ways = {"U", "D", "L", "R"};
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

        Node redMarvel = new Node(rx, ry, 0, 0, 0, new StringBuilder());
        Node blueMarvel = new Node(0, 0, bx, by, 0, new StringBuilder());
        visited[rx][ry][bx][by] = true;

        String[] answers = bfs(redMarvel, blueMarvel);

        if(answers[0].equals("-1")) {
            bw.write("-1");
        }
        else {
            bw.write(answers[0] + "\n" + answers[1]);
        }

        bw.flush();
        bw.close();
        br.close();
    }

    public static String[] bfs(Node redMarvel, Node blueMarvel) {
        Queue<Node> que = new LinkedList<>();
        Node node = new Node(
                redMarvel.rx,
                redMarvel.ry,
                blueMarvel.bx,
                blueMarvel.by,
                1,
                new StringBuilder()
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
                return new String[]{"-1", marvel.sb.toString()};
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
                    return new String[]{String.valueOf(count), marvel.sb.append(ways[i]).toString()};
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
                    que.add(new Node(newRx, newRy, newBx, newBy, count + 1, new StringBuilder(marvel.sb).append(ways[i])));
                }
            }
        }
        return new String[]{"-1", ""};
    }
}