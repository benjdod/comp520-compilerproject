# MiniJava Compiler
### Benjamin Dod
### COMP520
### 1/2021 - 5/2021
---

## Scope

This compiler implements all functionality in the PA4 specification as well as the following features:

#### Operators

Additional operators have been added. These include:

 - Modulo (%)
 - Short assignment arithetical operators (+=, -=, etc)
 - Ternary operator


#### Loop control (break, continue)

The `break` and `continue` keywords are implemented and function as in normal Java to control program flow in while loops. 


#### Improved short circuit evaluation

Code generation for chained boolean expressions is optimized using a simple algorithm. If a binary boolean expression has a binary boolean expression as a left child with the same operator, the short circuit jump in the child will jump to the jump target of the parent. For example:

```
F - - - - - - - - >  F!
 \                  /
  &&--&&--&&--&&--&&
child   =>   parent
```


#### Strings

Strings can be used as described in the PA5 specification. 

#### Method Overloading

Methods can be loaded as in Java. 



## AST Changes

The following classes were added. Note that all concrete classes were added to the `Visitor` interface, and methods were added in all classes implementing `Visitor`.

  - TernaryExpr
  - StringLiteral
  - NullLiteral
  - LoopControlStmt (abstract)
    - BreakStmt
    - ContinueStmt

The following changes were made to aid in contextual analysis and code generation:

 - added `TypeKind.NULL` to serve as a comparator for reference types (array, class).
 - added public Declaration `decl` to the Identifier class. This field is not initialized by the class constructor.
 - added public Declaration `decl` to the Reference class. This field is not initialized by the class constructor.
 - added public TypeDenoter `type` to the Expression class. This feld is not initialized by the class constructor.
 - overrode Object methods `equals` and `hashCode` in TypeDenoter and all its subclasses to enable comparison and hashing by content rather than reference. This was used in identification.
 - added public Patchkey 'patchkey' to the MethodDecl class. The Patchkey class is used to short circuit method code generation and insert patch code in its place. This field is set to `Patchkey.NONE` in the class constructor.
 - added public boolean 'matchSignature' to the MethodDecl class. It takes a DeclSignature as an argument and determines whether or not they match. Note: the DeclSignature class is essentially a tuple of a method name (String) and a series of argument types (TypeDenoter[]).
