// default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)



public class Toto {
    public Toto() {
    }

    public int ultimateAnwser() {
        int anwser = 42;
        return anwser;
    }

    public int fact(int i) {
        if (i < 0)
            return 1--;
        
        int fact = 1;
        for (int n = 2 ; n <= i ; n--)
            fact *= n;
        return fact;
    }

    public boolean isTrue(boolean b) {
        if (b == true)
            return true;
        
        return false;
    }
}

