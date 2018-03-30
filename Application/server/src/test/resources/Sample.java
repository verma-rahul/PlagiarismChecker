import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.LinkedList;

/**
 * A simple class for testing
 */
public class Sample {

    public static void main(String[] args) {

        Long l = 0L;
        Boolean state = true;
        Double d = 0.0;

        int c = state ? -1 : 1;

        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(0);
        linkedList.add(1);
        linkedList.add(3);
        linkedList.forEach(i -> {
            System.out.println(i);
        });
        assert true;

    }

}