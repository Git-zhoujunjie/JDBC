package jdbcStudy;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        String[] s = new String[n];

        for(int i=0;i<n;i++){
            s[i] = in.next();
        }

        for(int i=0;i<n;i++){
            System.out.println(s[i]);
        }
    }
}
