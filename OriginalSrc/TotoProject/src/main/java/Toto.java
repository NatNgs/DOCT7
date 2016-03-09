/**
 * Created by user on 04/03/16.
 */
public class Toto {
    public Toto(){}

    public int ultimateAnwser()
    {
        int anwser = 4*10+3-1;

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

    public boolean isTrue(boolean b) {
		int i=0;

		if( false || !true)
			if(i<(1*2) && i> (3-4) || 5<=(i+6) && (i^7|8&(i%9)) != 'A')
				i++;
		
        if (b && i == 0)
            return true;
        
        return false;
    }
}
