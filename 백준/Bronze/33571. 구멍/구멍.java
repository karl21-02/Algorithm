import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static Map<Character, Integer> list = new HashMap();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


        list.put('A', 1);
        list.put('a', 1);
        list.put('B', 2);
        list.put('b', 1);
        list.put('D', 1);
        list.put('d', 1);
        list.put('e', 1);
        list.put('g', 1);
        list.put('O', 1);
        list.put('o', 1);
        list.put('P', 1);
        list.put('p', 1);
        list.put('Q', 1);
        list.put('q', 1);
        list.put('R', 1);
        list.put('@', 1);

        String s = br.readLine();


        int sum = 0;
        for(int i=0; i<s.length(); i++) {
            if(list.containsKey(s.charAt(i))) {
                sum += list.get(s.charAt(i));
            }
        }

        System.out.println(sum);


        bw.flush();
    }
}
