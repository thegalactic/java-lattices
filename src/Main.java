

/**
 *
 * @author kbertet
 */

import java.util.TreeMap;
import java.util.TreeSet;
import lattice.lattice.ConceptLattice;
import lattice.lattice.Context;
import lattice.lattice.IS;

class Main {
static String inputDir;
static String outputDir;
public static void main (String arg[])
{
    TestReductionContext("Context-1");
    TestReductionIS("IS-1");
    //TestReductionIS(1000);
}

public static void TestReductionIS (int nb)
{
    for (int i=1; i<=nb; i++) {
        IS Sigma = IS.randomIS(500,0);
        int[] Eq = new int[3];
        Eq[0]=0; Eq[1]=0; Eq[2]=0;
        TreeMap<Object,TreeSet> Red = Sigma.getReducibleElements();
        for (Object x : Red.keySet()) {
            if (Red.get(x).size()==2) Eq[0]++;
        }
        System.out.println("Résultats test "+i+":");
        //System.out.println("Sigma "+Sigma);
    //System.out.println("Réd "+Red);
        System.out.println("Nb deleted: "+Red.size());
        System.out.println("Nb equivalents: "+Eq[0]+"-"+Eq[1]+"-"+Eq[2]);
    }
}

public static void TestReductionContext (String name)
{
    inputDir = "examples\\reduction\\";
    outputDir = "examples\\reduction\\";    
    // avant reduction
    Context Init1 = new Context (inputDir+name+".txt");    
    ConceptLattice CL1 = Init1.conceptLattice(true);    
    TreeSet objets = CL1.meetIrreducibles();
    CL1.toDot(outputDir+name+"CL_AvantReduction.dot");    
    System.out.println("****Avant reduction: ");
    System.out.println("Context: "+Init1);
    System.out.println("Nb concepts: "+CL1.nbNodes());
    System.out.println(objets.size()+" meet: "+objets);
    System.out.println("Precedence graph: "+Init1.precedenceGraph());
    
    // apres reduction
    TreeMap red = Init1.attributesReduction();
    System.out.println("Reduits: "+red);    
    ConceptLattice CL2 = Init1.conceptLattice(true);    
    CL2.toDot(outputDir+name+"CL_ApresReduction.dot");
    System.out.println("\n\n****Apres reduction: ");
    System.out.println("Context: "+Init1);
    System.out.println("Nb concepts: "+CL2.nbNodes());
    objets = CL2.meetIrreducibles();
    System.out.println(objets.size()+" meet: "+objets);
    System.out.println("Precedence graph: "+Init1.precedenceGraph());   
}
public static void TestReductionIS (String name)
{
    inputDir = "examples\\reduction\\";
    outputDir = "examples\\reduction\\";    
    // avant reduction
    IS Init1 = new IS (inputDir+name+".txt");   
    Init1.makeCanonicalDirectBasis();
    ConceptLattice CL1 = Init1.closedSetLattice(true);    
    TreeSet objets = CL1.joinIrreducibles();
    CL1.toDot(outputDir+name+"CL_AvantReductionIS.dot");    
    System.out.println("****Avant reduction: ");
    System.out.println("IS: "+Init1);
    System.out.println("Nb concepts: "+CL1.nbNodes());
    System.out.println(objets.size()+" join: "+objets);
    System.out.println("Precedence graph: "+Init1.precedenceGraph());
    
    // apres reduction
    TreeMap red = Init1.reduction();    
    Init1.makeCanonicalDirectBasis();
    System.out.println("Reduits: "+red);    
    ConceptLattice CL2 = Init1.closedSetLattice(true);    
    CL2.toDot(outputDir+name+"CL_ApresReductionIS.dot");
    System.out.println("\n\n****Apres reduction: ");
    System.out.println("IS: "+Init1);
    System.out.println("Nb concepts: "+CL2.nbNodes());
    objets = CL2.joinIrreducibles();
    System.out.println(objets.size()+" join: "+objets);
    System.out.println("Precedence graph: "+Init1.precedenceGraph());   
}
}// end of Main class
