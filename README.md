# Exercise

## Longest common subsequence
Given two strings the implementation should produce the longest common (non-contiguous) sequence of characters, i.e. `'AABACDA'` and `'DACBBCAD'` should produce `'ABAD'`.

The implementation is defined in `co.goodlord.exercise.lcs.Lcs`.
The result can be obtained by running:

    $ sbt 'runMain co.goodlord.exercise.lcs.Main --left AABACDA --right DACBBCAD'
    
Which should produce the following output:

    ...
    result: 'ABAD'

### Implementation notes
The implementation is based on a recursive traversal of possible selections of individual characters, i.e. it passes over both input strings, one character at a time.
If selected characters, one from each string match, it selects the character for the result. If not it splits the execution into two branches,
one ignoring the current character from the left string, and other ignoring the character from the right string. Afterwards, the longer sub-result is selected and the execution ascends from the current recursion level.

For performance reasons, the implementation makes use of dynamic programming, i.e. partial results are cached during execution and are reused in subsequent calls.
    
## Expression generator
Given a list of integers and a target larger integer the implementation should produce a sequence of arithmetic operations composed of the specified smaller integers and operations `+`, `-` and `*`.
For example, given values `2, 3, 5, 6`, and target `42` the result should be `(2 + 5) * 6`.

The implementation is defined in `co.goodlord.exercise.expression.Expression`.
The result can be obtained by running:

    $ sbt 'runMain co.goodlord.exercise.expression.Main --operands 2,3,5,6 --target 42'
    
Which should produce the following output:

    ...
    result: ((2 + 5) * 6) = 42
    
## Implementation notes
The solution is a form of a DSL which consists of type hierarchy:
    
    Expression -> Operator -> Addtion
                           -> Subtraction
                           -> Multiplication
               -> Operand

The implementing classes of `Expression` also define their `.value()` which returns the result of the entire arithmetic operation.

The current implementation can be improved in the following ways:

1. the `Expression` and its subtypes are not type-parametrised and only bound to `Int`. As such, this could be generalised into more abstract entities (possibly as free monads) and accept any type for which a relevant implementation is available (for example, `implicits`).
1. the implementation of `Expression.apply`, which attempts to build an `Expression` instance from given operands and target value, is relatively inefficient as it doesn't consider the commutative property of the operators. This causes re-evaluation of expressions although they are equal. The performance could be improved if this property was further exploited.

## Running the tests
The entire test suite is implemented using [ScalaTest](http://www.scalatest.org/) and can be executed with:

    $ sbt clean test