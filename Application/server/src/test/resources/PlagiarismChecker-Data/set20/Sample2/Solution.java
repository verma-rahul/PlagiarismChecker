package File2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        List<String> result = new Solution().removeInvalidParentheses("()())()");
        result.forEach(System.out::println);
    }

    public List<String> removeInvalidParentheses(String s) {
        Set<String> set = new HashSet<>();
        List<String> result = new ArrayList<>();
        result.add("");
        //generate all combinations of unique parentheses
        for(int i = s.length() - 1; i >= 0; i --){
            for(int j = 0, l = result.size(); j < l; j++){
                String curr = s.charAt(i) + result.get(j);
                if(!set.contains(curr)){
                    result.add(curr);
                    set.add(curr);
                }
            }
        }
        //check for max length
        int maxLen = 0;
        for(String r : result){
            if(isValid(r)){
                maxLen = Math.max(maxLen, r.length());
            }
        }
        //prepare the final list
        List<String> finalR = new ArrayList<>();
        for(String r : result){
            if(isValid(r)) {
                if(r.length() == maxLen){
                    finalR.add(r);
                }
            }
        }
        return finalR;
    }

    /**
     * Check if the given string of parentheses is valid or not
     * @param s String of parentheses
     * @return true if valid
     */
    private boolean isValid(String s){
        int count = 0;
        for(int i = 0, l = s.length(); i < l; i ++){
            if(s.charAt(i) == '('){
                count ++;
            } else if(s.charAt(i) == ')'){
                count --;
                if(count < 0) return false;
            }
        }
        return count == 0;
    }
}
