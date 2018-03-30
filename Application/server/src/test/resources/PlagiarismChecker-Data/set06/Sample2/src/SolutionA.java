
import java.util.Set;
import java.util.TreeSet;


public class SolutionA {   
	
	public static int[] rearrange(int[] elements)
{
    Set<IntToBinCopy> set = new TreeSet<IntToBinCopy>();
    for (int element: elements)
    {
        set.add(new IntToBinCopy(element));
    }

    int[] actual= new int[set.size()];
    int i=0;
    for (IntToBinCopy in: set)
    {
        actual[i++]=in.getValue();
    }


    return actual;
}
	}


