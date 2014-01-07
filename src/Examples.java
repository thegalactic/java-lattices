package lattice.lattice;


/*
 * Examples.java
 *
 * last update on December 2013
 *
 **/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.TreeSet;
import lattice.lattice.DAGraph;
import lattice.lattice.DGraph;
import lattice.lattice.Node;
import lattice.lattice.*;
/**
 * This classe provides some use examples of main classes of this lattice package.
 * <p>
 * This class is composed of static method giving some use examples of
 * class <code>DGraph</code>, class <code>DAGraph</code>, class <code>Context</code>,
 * class <code>IS</code> and the specific class <code>BijectiveComponents</code>.
 * <p>
 * Copyright: 2013 University of La Rochelle, France
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license



* <img src="..\..\..\images\lgpl.png" height="20" alt="lgpl"/>
 * Copyright 2010 Karell Bertet<p>
 * This file is part of lattice.
 * lattice is free package: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with lattice.  If not, see <a href="http://www.gnu.org/licenses/" target="_blank">license</a>
 *
 * @author Karell Bertet
 * @version 2010
 */

class Examples {

    /** Static fields specifying the input file directory **/
    static String inputDir = "examples\\input\\";
    /** Static fields specifying the output file directory **/
    static String outputDir = "examples\\output\\";

    /** Replaces the input directory with the specified one **/
    public static void setInputDir (String input) {
        inputDir = input;
    }
    /** Replaces the output directory with the specified one **/
    public static void setOutputDir (String output) {
        outputDir = output;
    }

    /** The main static method. **/
    public static void main (String arg[])
    {   
        double time = System.currentTimeMillis();
        //Examples.ExampleDGraph();
        //Examples.ExampleDAGraph ();
        //Examples.ExampleIS("animauxIS");
        Examples.ExampleContext("Context-3");
        //Examples.ExampleBijectiveComponentsForContext("animaux");
        //Examples.ExampleBijectiveComponentsForIS("animauxIS");
        //Examples.ExampleBijectiveComponentsForIS("ISEquivalents-2");        
        System.out.println("temps: " + (System.currentTimeMillis() - time));
    }

    /** Use example for BijectiveComponent class
     * with an implicational system as initial closure system **/
    public static void ExampleBijectiveComponentsForIS (String name) {
        IS Init = new IS (inputDir+name+".txt");                
        BijectiveComponents BC = new BijectiveComponents (Init);
        double time = BC.initialize();
        BC.save(outputDir,name);
        System.out.println("time: "+time);        
    }

    /** Use example for BijectiveComponent class
     * with a context as initial closure system **/
    public static void ExampleBijectiveComponentsForContext (String name) {
        Context Init = new Context (inputDir+name+".txt");
        BijectiveComponents BC = new BijectiveComponents (Init);
        double time = BC.initialize();
        BC.save(outputDir, name);
        Init.reverse();
        ConceptLattice CL = Init.closedSetLattice(true);
        IS BCD = CL.getCanonicalDirectBasis();
        System.out.println(BCD);
        System.out.println("time: "+time);
    }

    /** Use example for DGraph and DAGraph classes **/
    public static void ExampleDGraph () {
        try 	{
            String name = "DGraph";
            // create the directory to save files
            File f = new File(outputDir+name);
            f.mkdir();
            // create the Readme file
            name = name+"\\"+name;

            BufferedWriter file = new BufferedWriter(new FileWriter(outputDir+name+"Readme.txt"));
            String log = "EXAMPLE FOR DGRAPH AND DAGRAPH CLASSES\n";
            log += "--------------------------------------\n";
            System.out.println(log); file.write(log);
            // randomly generates a directed graph of 5 nodes
            DGraph G = DGraph.randomDGraph (10);
            String nameGraph = name+".dot";
            G.toDot(outputDir+nameGraph);
            log = "-> Randomly generated DGraph saved in "+nameGraph+"\n";
            System.out.println(log+G.toString()); file.write(log);
            // compute the complementary graph
            G.complementary();
            String nameComp = name+"Complementary.dot";
            G.toDot(outputDir+nameComp);
            log = "-> Complementary graph saved in "+nameComp+"\n ";
            System.out.println(log+G.toString());
            // check if the dgraph is acyclic
            log = "-> DGraph acyclic? "+G.isAcyclic()+"\n";
            System.out.println(log); file.write(log);
            // computes and print the transitive closure of the dgraph
            G.transitiveClosure();
            String nameTransClosure = name+"TransitiveClosure.dot";
            G.toDot(outputDir+nameTransClosure);
            log = "-> Transitive closure saved in "+nameTransClosure+"\n";
            System.out.println(log+G.toString()); file.write(log);
            // computes and print a depth first search in the directed graph
            ArrayList[] S = G.depthFirstSearch();
            log = "-> Depth first search (first visited nodes): "+ S[0]+"\n";
            log += "Depth first search (last visited nodes): "+ S[1]+"\n";
            System.out.println(log); file.write(log);
            // computes and print the directed acyclic graph whose nodes
            // are strongly connected components of the directed graph
            DAGraph CC = G.stronglyConnectedComponent();
            String nameCC = name+"ConnectedComponents.dot";
            CC.toDot(outputDir+nameCC);
            log = "-> Strongly connected components saved in "+nameCC+"\n";
            System.out.println(log+CC.toString()); file.write(log);
            // verify that the dagraph is acyclic
            log = nameCC+" acyclic? "+CC.isAcyclic()+"\n";
            System.out.println(log); file.write(log);
            // computes and print the sugbraph of the dgraph induces by 5 first nodes
            TreeSet<Node> X = new TreeSet();
            for (Node n : G.getNodes())
                if (X.size()!=5) X.add(n);
            DGraph SG = G.subgraphByNodes(X);
            String nameSG = name+"Subgraph.dot";
            SG.toDot(outputDir+nameSG);
            log = "-> Subgraph induced by 5 first nodes saved in "+nameSG+"\n";
            System.out.println(log+SG.toString()); file.write(log);
            file.close();
        }
    	catch (Exception e) {}
    }


    /** Use example for DAGraph and Lattice classes **/
    public static void ExampleDAGraph () {
        try {
            String name = "DAGraph";
            // create the directory to save files
            File f = new File(outputDir+name);
            f.mkdir();
            // create the Readme file
            name = name+"\\"+name;
            BufferedWriter file = new BufferedWriter(new FileWriter(outputDir+name+"Readme.txt"));
            String log = "EXAMPLE FOR DAGRAPH AND LATTICE CLASSES\n";
            log += "-----------------------------------------\n";
            System.out.println(log); file.write(log);
            // randomly generates a directed graph of 10 nodes
            DAGraph G = DAGraph.randomDAGraph (10);
            String nameGraph = name+".dot";
            G.toDot(outputDir+nameGraph);
            log = "-> Randomly generated DAGraph saved in "+nameGraph+"\n";
            System.out.println(log+G.toString()); file.write(log);
            // verify if the dagraph is acyclic
            log = nameGraph+" acyclic? "+G.isAcyclic()+"\n";
            System.out.println(log); file.write(log);

            // computes and print the transitive reduction of the dagraph
            G.transitiveReduction();
            String nameTR = name+"TransitiveReduction.dot";
            G.toDot(outputDir+nameTR);
            log = "-> Transitive reduction saved in "+nameTR+"\n";
            System.out.println(log+G.toString()); file.write(log);
            // computes and print the ideal and the filter of the first node
            Node n = G.getNodes().first();
            DAGraph ideal = G.ideal(n);
            String nameIdeal = name+"Ideal.dot";
            ideal.toDot(outputDir+nameIdeal);
            log = "-> Minorants of "+n+" : "+G.minorants(n)+"\n saved as a dagraph in "+nameIdeal+"\n";
            System.out.println(log); file.write(log);
            DAGraph filter = G.filter(n);
            String nameFilter = name+"Filter.dot";
            filter.toDot(outputDir+nameFilter);
            log = "-> Majorants of "+n+" : "+G.majorants(n)+"\n saved as a dagraph in "+nameFilter+"\n";
            System.out.println(log); file.write(log);

            // computes and print the ideals lattice of the dagraph
            ConceptLattice CSL = G.idealsLattice();
            String nameIdealsLattice = name+"IdealsLattice.dot";
            CSL.toDot(outputDir+nameIdealsLattice);
            log = "-> Ideal lattice saved in "+nameIdealsLattice+"\n";
            System.out.println(log+CSL.toString()); file.write(log);
            // check if the ideals lattice is a lattice
            log = "-> Check if the ideal lattice is a lattice ? "+CSL.isLattice()+"\n";
            System.out.println(log); file.write(log);
            // print the irreducibles elements of the ideal lattice
            log = "-> Join irreducibles of ideal lattice: "+CSL.joinIrreducibles()+"\n";
            log += "Meet irreducibles of ideal lattice: "+CSL.meetIrreducibles()+"\n";
            System.out.println(log); file.write(log);
            
                    

            // reduces the ideal lattice by replacing each join irreducible node by one element
            Lattice L = CSL.getJoinReduction();
            String nameReducedLattice = name+"ReducedLattice.dot";
            L.toDot(outputDir+nameReducedLattice);
            log = "-> Reduced ideal lattice saved in "+nameReducedLattice+"\n";
            System.out.println(log+L.toString()); file.write(log);
            // print the irreducibles elements of the reduces ideal lattice
            log = "-> Join irreducibles of reduced ideal lattice: "+L.joinIrreducibles()+"\n";
            log += "Meet irreducibles of reduced ideal lattice: "+L.meetIrreducibles()+"\n";
            System.out.println(log); file.write(log);
            // computes the table of the reduced lattice
            Context T = L.getTable();            
            String nameTable = name+"IrrTable.txt";
            T.toFile(outputDir+nameTable);
            log = "-> Irreducibles table of the reduced ideal lattice saved in "+nameTable+":\n "+T.toString();
            System.out.println(log); file.write(log);
            // compute the subgraph of join irreducible nodes
            DAGraph JIrr = L.joinIrreduciblesSubgraph();
            String nameIrrSG = name+"IrrSubgraph.dot";
            JIrr.toDot(outputDir+nameIrrSG);
            log = "-> Join irreducibles subgraph saved in "+nameIrrSG+"\n";
            System.out.println(log+JIrr.toString()); file.write(log);
            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Initial random DAGraph ("+nameGraph+") isomorphic to\n";
            log += "Join irreducible subgraph of its ideal lattice ("+nameIrrSG+")\n";
            System.out.println(log); file.write(log);
            file.close();
        }
    	catch (Exception e) {}
    }

    /** Use example for Context and ConceptLattice classes **/
    public static void ExampleIS (String name) {
        try {
            // load an IS from the "ISrules.txt" file
            String nameIS = name+".txt";
            IS base = new IS (inputDir+nameIS);
            // create the directory to save files
            File f = new File(outputDir+name);
            f.mkdir();
            // create the Readme file
            name = name+"\\"+name;
            BufferedWriter file = new BufferedWriter(new FileWriter (outputDir+name+"Readme.txt"));
            String log = "EXAMPLE FOR IS AND CONCEPTLATTICE CLASSES\n";
            log += "-----------------------------------------\n";                                    
            log += "-> Initial set of rules ("+base.nbRules()+" rules):\n"+base+"\n";
            System.out.println(log); file.write(log);
            
            // computes the precedence graph of the IS
            DGraph prec = base.precedenceGraph();
            String namePrecGraph = name+"PrecedenceGraph.dot";
            prec.toDot(outputDir+namePrecGraph);
            log = "Precedence graph of IS saved in "+namePrecGraph+"\n";
            System.out.println(log+prec.toString()); file.write(log);

            // some IS transformation
            log = "-> Some IS transformations: \n";
            base.makeUnary();
            log += "-> Unary equivalent rules ("+base.nbRules()+" rules):\n"+base+"\n";
            base.makeLeftMinimal();
            log += "Left minimal equivalent rules ("+base.nbRules()+" rules):\n"+base+"\n";
            base.makeRightMaximal();
            log += "Right maximal equivalent rules ("+base.nbRules()+" rules):\n"+base+"\n";
            base.makeCompact();
            log += "Compact equivalent rules ("+base.nbRules()+" rules):\n"+base+"\n";
            System.out.println(log); file.write(log);

            // computes and prints the closed set lattice of the initial rules with NextClosure
            ConceptLattice CLNC = base.closedSetLattice(false);
            String nameCLNC = name+"ClosedSetLatticeNextClosure.dot";
            CLNC.toDot(outputDir+nameCLNC);
            log = "-> Closed set lattice of IS (generated by Next Closure algorithm) saved in "+nameCLNC+"\n";
            System.out.println(log+CLNC.toString()); file.write(log);

            // computes and prints the closed set lattice of the initial rules with Bordat
            ConceptLattice CLBordat = base.closedSetLattice(true);
            String nameCLBordat = name+"ClosedSetLatticeBordat.dot";
            CLBordat.toDot(outputDir+nameCLBordat);
            log = "-> Closed set lattice of IS (generated by Bordat's algorithm) saved in "+nameCLBordat+"\n";
            System.out.println(log+CLBordat.toString()); file.write(log);

            // computes dependance graph, minimal generators and canonical direct basis
            log = "-> Components generated while Bordat's algorithm computes the lattice:\n";
            DGraph ODG = CLBordat.getDependanceGraph();
            String nameODG = name+"DependanceGraphOfClosedSetLattice.dot";
            ODG.toDot(outputDir+nameODG);
            log += "Dependance graph of closed set lattice saved in "+nameODG+"\n";
            System.out.println(log+ODG.toString()); file.write(log);
            TreeSet MinGen = CLBordat.getMinimalGenerators();
            log = "Minimal generators of closed set lattice : "+MinGen+"\n";
            IS CLBCD = CLBordat.getCanonicalDirectBasis();
            String nameCLBCD = name+"CanonicalDirectBasisOfClosedSetLattice.txt";
            CLBCD.toFile(outputDir+nameCLBCD);
            log += "Canonical direct basis of closed set lattice saved in "+nameCLBCD+": \n"+CLBCD.toString();
            System.out.println(log); file.write(log);

            // computes the canonical basis and the closed set lattice of the basis
            base.makeCanonicalBasis();
            String nameBC = name+"CanonicalBasis.txt";
            base.toFile(outputDir+nameBC);
            log = "Canonical basis ("+base.nbRules()+" rules) saved in "+nameBC+": \n"+base;
            ConceptLattice CLBC = base.closedSetLattice(true);
            String nameCLBC = name+"ClosedSetLatticeOfCanonicalBasis.dot";
            CLBC.toDot(outputDir+nameCLBC);
            log += "Closed set lattice of the canonical basis saved in "+nameCLBC+"\n";
            System.out.println(log+CLBC.toString()); file.write(log);
            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Concept lattice of initial IS ("+nameCLBordat+") isomorphic to\n";
            log += "Concept lattice of the canonical basis of initial IC ("+nameCLBC+")\n";
            log += "-----------------\n";

            // computes the canonical directe basis
            base.makeCanonicalDirectBasis();
            String nameBCD = name+"CanonicalDirectBasis.txt";
            base.toFile(outputDir+nameBC);
            log = "-> Canonical direct basis ("+base.nbRules()+" rules) saved in "+nameBCD+": \n"+base;            
            System.out.println(log); file.write(log);
            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Canonical direct basis of initial IS ("+nameBCD+") isomorphic to\n";
            log += "Canonical direct basis of the concept lattice of initial IC ("+nameCLBCD+")\n";            
            log += "-----------------\n";            
            
            // computes the closed set lattice of the canonical direct basis 
            ConceptLattice BCDCL = base.closedSetLattice(true);
            String nameBCDCL = name+"ClosedSetLatticeOfCanonicalDirectBasis.dot";
            BCDCL.toDot(outputDir+nameCLBCD);
            log += "-> Closed set lattice of the canonical direct basis saved in "+nameBCDCL+"\n";
            System.out.println(log+BCDCL.toString()); file.write(log);
            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Closed set lattice of initial IS ("+nameCLBordat+") isomorphic to\n";
            log += "Closed set lattice of the canonical direct basis of initial IC ("+nameBCDCL+")\n";
            log += "-----------------\n";
            System.out.println(log); file.write(log);

            // computes and prints the join reduction of the closed set lattice
            Lattice L = CLBordat.getJoinReduction();
            String nameCLJoinReduced = name+"LatticeJoinReduction.dot";
            L.toDot(outputDir+nameCLJoinReduced);
            log = "-> Join reduction of the concept lattice saved in "+nameCLJoinReduced+"\n";
            System.out.println(log+L.toString()); file.write(log);

            // computes the table of irreducible nodes of the reduced lattice
            Context T = L.getTable();
            String nameTable = name+"TableOfReducedLattice.txt";
            T.toFile(outputDir+nameTable);
            log = "-> Irreducibles table saved in "+nameTable+":\n "+T;
            System.out.println(log); file.write(log);

            // computes the concept lattice of the table            
            ConceptLattice CLTable = T.conceptLattice(false);
            String nameCLTable = name+"ConceptLatticeOfTable.dot";
            CLTable.toDot(outputDir+nameCLTable);
            log = "Concept lattice of the table saved in "+nameCLTable+"\n";
            System.out.println(log+CLTable.toString());
            file.write(log);


            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Concept lattice of the canonical direct basis of initial IC ("+nameCLBCD+") is isomorphic to \n";
            log += "is isomorphic to concept lattice of its irreducibles table ("+nameCLTable+")\n";
            log += "-----------------\n";
            System.out.println(log); file.write(log);
            file.close();
        }
    	catch (Exception e) {}
    }

    /** Use example for Context and ConceptLattice classes **/
    public static void ExampleContext (String name) {
        try {
            // load an IS from the "ISrules.txt" file
            String nameContext = name+".txt";
            Context base = new Context (inputDir+nameContext);
            // create the directory to save files
            File f = new File(outputDir+name);
            f.mkdir();
            // create the Readme file
            name = name+"\\"+name;
            BufferedWriter file = new BufferedWriter(new FileWriter (outputDir+name+"Readme.txt"));
            String log = "EXAMPLE FOR CONTEXT AND CONCEPTLATTICE CLASSES\n";
            log += "-----------------------------------------\n";
            log += "-> Initial context:\n "+base+"\n";
            System.out.println(log); file.write(log);

            // compute the immediate successors of a concept from the context using Limited Object Access algorihtm            
            TreeSet<Comparable> setA = new TreeSet();
            setA.add(base.getAttributes().first());
            setA.addAll(base.closure(setA));
            TreeSet<Comparable> setB = new TreeSet();
            setB.addAll(base.getExtent(base.closure(setA)));
            Concept concept = new Concept(setA, setB);
            log = "Chosen concept "+concept.toString();
            System.out.println(log); 
            file.write(log);
            
            ArrayList<TreeSet<Comparable>> immsucc = concept.immediateSuccessorsLOA(base);
            log = "First immediate successor concept "+new Concept(immsucc.get(0),base.getExtent(immsucc.get(0)))+"\n";
            System.out.println(log); 
            file.write(log);
            
            // computes the precedence graph of the context
            DGraph prec = base.precedenceGraph();
            String namePrecGraph = name+"PrecedenceGraph.dot";
            prec.toDot(outputDir+namePrecGraph);
            log = "Precedence graph of Context saved in "+namePrecGraph+"\n";
            System.out.println(log+prec.toString()); file.write(log);

            // computes and prints the concept lattice of the context with NextClosure
            ConceptLattice CLNC = base.closedSetLattice(false);
            String nameCLNC = name+"ClosedSetLatticeNextClosure.dot";
            CLNC.toDot(outputDir+nameCLNC);
            log = "-> Closed set lattice of Context (generated by Next Closure algorithm) saved in "+nameCLNC+"\n";
            System.out.println(log+CLNC.toString()); file.write(log);

            // computes and prints the closed set lattice of the context with Bordat
            ConceptLattice CLBordat = base.closedSetLattice(true);
            String nameCLBordat = name+"ClosedSetLatticeBordat.dot";
            CLBordat.toDot(outputDir+nameCLBordat);
            log = "-> Closed set lattice of Context (generated by Bordat's algorithm) saved in "+nameCLBordat+"\n";
            System.out.println(log+CLBordat.toString()); file.write(log);

            // computes and prints the concept lattice of the context with Bordat
            ConceptLattice CBordat = base.conceptLattice(true);
            String nameCBordat = name+"ConceptLatticeBordat.dot";
            CBordat.toDot(outputDir+nameCBordat);
            log = "-> Concept lattice of Context (generated by Bordat's algorithm) saved in "+nameCBordat+"\n";
            System.out.println(log+CBordat.toString()); file.write(log);

            // computes dependance graph, minimal generators and canonical direct basis
            log = "-> Components generated while Bordat's algorithm computes the lattice:\n";
            DGraph ODG = CLBordat.getDependanceGraph();
            String nameODG = name+"DependanceGraphOfClosedSetLattice.dot";
            ODG.toDot(outputDir+nameODG);
            log += "Dependance graph of closed set lattice saved in "+nameODG+"\n";
            System.out.println(log+ODG.toString()); file.write(log);
            TreeSet MinGen = CLBordat.getMinimalGenerators();
            log = "Minimal generators of closed set lattice : "+MinGen+"\n";
            IS BCD = CLBordat.getCanonicalDirectBasis();
            String nameBCD = name+"CanonicalDirectBasisOfClosedSetLattice.txt";
            BCD.toFile(outputDir+nameBCD);
            log += "Canonical direct basis of closed set lattice saved in "+nameBCD+": \n"+BCD.toString();
            System.out.println(log); file.write(log);


            // computes the reduction and the closed set lattice of the context
            Context reduit = new Context(base);
            reduit.reduction();
            String nameReduit = name+"Reduction.txt";
            base.toFile(outputDir+nameReduit);
            log = "Reduced context saved in "+nameReduit+": \n"+reduit;
            ConceptLattice CLReduit = base.closedSetLattice(true);
            String nameCLReduit = name+"ClosedSetLatticeOfReducedContext.dot";
            CLReduit.toDot(outputDir+nameCLReduit);
            log += "Closed set lattice of the reduced context saved in "+nameCLReduit+"\n";
            System.out.println(log+CLReduit.toString()); file.write(log);
            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Concept lattice of initial context ("+nameCLBordat+") isomorphic to\n";
            log += "Concept lattice of the reduced context ("+nameCLReduit+")\n";
            log += "-----------------\n";


            // computes the table of the concept lattice od the context
            Context table = CBordat.getTable();
            String nameTable = name+"TableOfConceptLattice.txt";
            base.toFile(outputDir+nameTable);
            log = "-> Table of the concept lattice saved in "+nameTable+": \n"+table;
            System.out.println(log); file.write(log);
            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Reduction of the initial context "+reduit+") isomorphic to\n";
            log += "Table of the its concept lattice ("+nameTable+")\n";
            log += "-----------------\n";

            // computes the closed set  lattice of the CDB
            ConceptLattice CLBCD = BCD.closedSetLattice(true);
            String nameCLBCD = name+"ConceptLatticeOfBCD.dot";
            CLBCD.toDot(outputDir+nameCLBCD);
            log = "Concept lattice of the CDB saved in "+nameCLBCD+"\n";
            System.out.println(log+CLBCD.toString());
            file.write(log);

            // BIJECTION
            log = "--- BIJECTION --- \n";
            log += "Concept lattice of the initial context ("+nameCLBordat+") is isomorphic to \n";
            log += "is isomorphic to concept lattice of its canonical directe basis ("+nameCLBCD+")\n";
            log += "-----------------\n";
            System.out.println(log); file.write(log);
            file.close();
        }
    	catch (Exception e) {}
    }
}// end of Example class
