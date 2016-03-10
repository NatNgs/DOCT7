package fr.unice.polytech.devops.groupe7.mutation;

/**
 * Created by user on 10/03/16.
 */
public class Main {

    public static void main(String args[])
    {

        if(args.length != 2)
        {
            System.err.println("Incorrect arguments : <project_path> <mutator>");
            return;
        }
        MutationApply ma = new MutationApply(args[0], args[1]);
        ma.apply();
    }
}
