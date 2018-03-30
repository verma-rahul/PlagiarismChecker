import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        
        List<Player> player = new ArrayList<Player>();
        Checker checker = new Checker();
        
        for(int i = 0; i < n; i++){
            player.add(new Player(scan.next(), scan.nextInt()));
        }
        scan.close();
     
        Collections.sort(player, checker);
        for(int i = 0; i < player.size(); i++){
            System.out.printf("%s %s\n", player.get(i).name,  player.get(i).score);
        }
    }
}