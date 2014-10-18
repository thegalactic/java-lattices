ConceptLattice
==============

Creating a concept lattice
--------------------------

Using the `ConceptLattice` class, it is easy to create a concept lattice from any closure system, e.g. an implicationnal system or a context.

### From a context

For instance, it is possible to use one of the `cxt` file from examples, in the following manner :

~~~Code(tealady.tmp,
Context ctx = new Context(\\\"tealady.cxt\\\");
ConceptLattice cl = ConceptLattice.completeLattice(ctx);
)~~~

### From an implicationnal system

For instance, it is possible to use the `random` method, in the following manner :

~~~Code(randomrule.tmp,
ImplicationalSystem is = ImplicationalSystem.random(5, 10);
ConceptLattice cl = ConceptLattice.completeLattice(is);
)~~~

Computations from `Lattice` class
---------------------------------

The `ConceptLattice` is inherited from the lattice class, thus it is possible to use the following methods with a concept lattice.
