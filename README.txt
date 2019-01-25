README.txt for WCBC Java materials version 1.0 (January 2019)
-------------------------------------------------------------

1. Overview

WCBC is an abbreviation for the book "What Can Be Computed?: A Practical Guide to the Theory of Computation," written by John MacCormick and published by Princeton University Press (2018). Please see http://whatcanbecomputed.com for more details about the book. This directory contains supporting materials for WCBC. More specifically, this directory contains *Java* materials. The book uses Python for examples and explanations, but all functionality is also available in Java. This directory provides those Java materials.

2. File types in this directory

Essentially all files of interest are in the "src" subdirectory. 

The majority of the materials are Java classes (.java files), but there are several other file types, as summarized in the following table:

.cfg  description of a context free grammar
.dfa  description of a deterministic finite automaton
.nfa  description of a nondeterministic finite automaton
.pda  description of a push down automaton
.java Java class (SISO or non-SISO -- see below)
.tm   description of a Turing machine
.txt  ASCII text 

All the above file types are described in the WCBC book.

3. SISO and non-SISO Java classes

WCBC defines a special kind of Python program referred to as "string in string out", or SISO for short. Here we are instead providing Java materials, and the analogous concept is a SISO Java class, which implements the "siso" interface. See Siso.java for details.

In these materials, most but not all of the provided Java files are SISO classes; SISO classes are identified by a comment in the first line. There are also several non-SISO classes. These include  Graph.java, TuringMachine.java, and the library of utility functions, utils.java.

4. Tests

Standard JUnit tests are provided in the test directory.

