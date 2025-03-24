import java.io.*;
import java.util.*;

public class Main {
    static int[][] map;
    static int[][] groupMap; // To store which group each cell belongs to
    static int[] groupSize;  // To store size of each group
    static int n, m;
    static int groupCount = 0;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        groupMap = new int[n][m];

        // Read the map
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j) - '0';
                groupMap[i][j] = -1; // Initialize all cells as not belonging to any group
            }
        }

        // Number groups starting from 0
        groupCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0 && groupMap[i][j] == -1) {
                    bfs(i, j, groupCount++);
                }
            }
        }

        // Initialize groupSize array
        groupSize = new int[groupCount];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (groupMap[i][j] != -1) {
                    groupSize[groupMap[i][j]]++;
                }
            }
        }

        // Calculate result for each cell
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0) {
                    sb.append("0");
                } else {
                    // Calculate reachable cells if this wall is broken
                    int sum = 1; // Start with 1 for the cell itself
                    
                    // HashSet to avoid counting the same group multiple times
                    HashSet<Integer> countedGroups = new HashSet<>();
                    
                    for (int k = 0; k < 4; k++) {
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        
                        if (nx >= 0 && nx < n && ny >= 0 && ny < m && map[nx][ny] == 0) {
                            int groupId = groupMap[nx][ny];
                            if (!countedGroups.contains(groupId)) {
                                countedGroups.add(groupId);
                                sum += groupSize[groupId];
                            }
                        }
                    }
                    
                    // Print result modulo 10
                    sb.append(sum % 10);
                }
            }
            sb.append("\n");
        }
        
        System.out.print(sb);
        br.close();
    }

    // BFS to mark connected cells with the same group number
    private static void bfs(int startX, int startY, int groupNumber) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startX, startY});
        groupMap[startX][startY] = groupNumber;
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && map[nx][ny] == 0 && groupMap[nx][ny] == -1) {
                    groupMap[nx][ny] = groupNumber;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
    }
}