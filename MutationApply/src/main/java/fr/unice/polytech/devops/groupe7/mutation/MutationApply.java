package fr.unice.polytech.devops.groupe7.mutation;

/**
 * Created by user on 10/03/16.
 */
public class MutationApply {
    private String project;
    private String mutator;

    public MutationApply(String project, String mutator)
    {
        this.project = project;
        this.mutator = mutator;
    }

    public void apply()
    {
        if(!loadPom())
        {
            System.err.println("Could not load pom.xml for project " + project);
            return;
        }

        applyMutator();
        saveMutator();
    }

    private boolean loadPom()
    {
        return false;
    }

    private void applyMutator()
    {

    }

    private void saveMutator()
    {

    }
}
