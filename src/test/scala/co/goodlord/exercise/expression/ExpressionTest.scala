package co.goodlord.exercise.expression

import org.scalatest.{FlatSpec, Matchers, OptionValues}

class ExpressionTest extends FlatSpec with Matchers with OptionValues {

  import Expression._

  behavior of classOf[Expression].getSimpleName

  it should "produce an expression evaluating to the target value" in {
    val input1: Seq[Operand] = Seq(2, 3, 5, 6)
    val target1 = 42

    val input2: Seq[Operand] = Seq(1, 2, 3, 4, 5, 6, 7, 8)
    val target2 = 128

    val input3: Seq[Operand] = Seq(1, 2, 3)
    val target3 = 1024

    val result1 = Expression(target1, input1)
    val result2 = Expression(target2, input2)
    val result3 = Expression(target3, input3)

    result1.value.value shouldEqual target1
    result2.value.value shouldEqual target2
    result3 shouldBe None
  }

  it should "ensure that no intermediate computation yields a negative value" in {
    val input: Seq[Operand] = Seq(1, 2, 3, 4, 5, 6, 7, 8)
    val target = 128

    val result = Expression(target, input)
    result.map(testNotNegative).value shouldEqual true
  }

  def testNotNegative(expression: Expression): Boolean = expression match {
    case Operand(v) if v >= 0 => true
    case Addition(l, r) if l.value + r.value >= 0 => testNotNegative(l) && testNotNegative(r)
    case Subtraction(l, r) if l.value - r.value >= 0 => testNotNegative(l) && testNotNegative(r)
    case Multiplication(l, r) if l.value * r.value >= 0 => testNotNegative(l) && testNotNegative(r)
    case _ => false
  }

}
