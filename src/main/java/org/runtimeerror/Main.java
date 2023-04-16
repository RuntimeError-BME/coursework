package org.runtimeerror;
import java.util.Scanner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.skeleton.SkeletonController;


public class Main {

    public static SkeletonController skeleton;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        skeleton = new SkeletonController(in);

        System.out.println("Udvozollek a Skeletonunk tesztelofeluleten!");
        System.out.println("A kovetkezo tesztesetek/muveletek allnak rendelkezesre:");
        System.out.println("(a szamot beirva valaszthatod ki oket)");
        skeleton.PrintAllTestNames();

        int testNr = -1;
        do {
            try {
                testNr = in.nextInt();
                if (testNr >= 0 && testNr < 30) {
                    System.out.println(skeleton.GetTestName(testNr));
                    skeleton.RunTest(testNr);
                } else
                    System.out.println("Hibas bemenet. Probald ujra.");
            } catch (NotImplementedException n_ex) {
                n_ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Hibas bemenet. Probald ujra.");
                in.next();
            }
        } while (testNr != 0);

        in.close();
    }
}