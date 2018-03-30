
import java.util.Set;
import java.util.TreeSet;


public class SolutionB {   

	public static int[] rearrange(int[] elements)
	{
		Set<MyIntToBin> set = new TreeSet<MyIntToBin>();
		for (int element: elements)
		{
			set.add(new MyIntToBin(element));
		}

		int[] actual= new int[set.size()];
		int i=0;
		for (MyIntToBin in: set)
		{
			actual[i++]=in.getValue();
		}


		return actual;
	}
}


