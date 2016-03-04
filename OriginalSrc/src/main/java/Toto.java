/**
 * Created by user on 04/03/16.
 */
public class Toto {
    public Toto(){}

    public int ultimateAnwser()
    {
        int anwser = (4*(20+3)-8)/2;

        return anwser;
    }

    public int fact(int i)
    {
        if(i<0)
            return -1;

        int fact = 1;
        for(int n=2; n<=i; n++)
            fact *= n;
        return fact;
    }

    public boolean isTrue(boolean b)
    {
        if(b == true)
            return true;
        return false;
    }
}
