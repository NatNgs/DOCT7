package fr.unice.polytech.doct;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Dossier dans lequel sont les dossiers contenant les sortie xml de JUnit
        File mainDirectory = new File(args[0]);
        Rapport rapport = new Rapport(mainDirectory);
        rapport.compile();

    }
}
