# Exercise

## Longest common subsequence
Given two strings the implementation should produce the longest common (non-contiguous) sequence of characters, i.e. `'AABACDA'` and `'DACBBCAD'` should produce `'ABCA'`.

The implementation is defined in `co.goodlord.exercise.lcs.Lcs`.
The result can be obtained by running:

    $ sbt 'runMain co.goodlord.exercise.lcs.Main --left AABACDA --right DACBBCAD'
    
Which should produce the following output:

    ...
    result: 'ABAD'
    
## Expression generator
Given a list of integers and a target larger integer the implementation should produce a sequence of arithmetic operations composed of the specified smaller integers and operations `+`, `-` and `*`.
For example, given values `2, 3, 5 and 6`, and target `42` the result should be `(2 + 5) * 6`.

The implementation is defined in `co.goodlord.exercise.expression.Expression`.
The result can be obtained by running:

    $ sbt 'runMain co.goodlord.exercise.expression.Main --operands 2,3,5,6 --target 42'
    
Which should produce the following output:

    ...
    result: ((2 + 5) * 6) = 42
    
## Running the tests
The entire test suite is implemented using [ScalaTest](http://www.scalatest.org/) and can be executed with:

    $ sbt clean test