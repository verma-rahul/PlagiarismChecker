public class IntToBinCopy implements Comparable<IntToBinCopy>
{
Integer value;

public IntToBinCopy(int value) {
this.value = value;
}

public int compareTo(IntToBinCopy o) {
int thisValue=countOfOnes(Integer.toBinaryString(value));
int thatValue=countOfOnes(Integer.toBinaryString(o.getValue()));

if (thisValue>thatValue)
{
    return 1;
}
else if (thisValue==thatValue)
{
    if (value>o.getValue())
    {
        return 1;
    }
}

    return -1;
}

private int countOfOnes(String binSTring)
{
int count=0;
for ( char c : binSTring.toCharArray())
{
    if ((c+"").equals("1"))
    {
        count++;
    }
}
return count;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (o == null || getClass() != o.getClass()) return false;

IntToBinCopy intToBin = (IntToBinCopy) o;

return value.equals(intToBin.value);
}

@Override
public int hashCode() {
return value.hashCode();
}

public int getValue() {
return value;
}
}


