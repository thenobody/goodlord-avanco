package co.goodlord.exercise

sealed trait Expression {
  def value: Int
}
case class Operand(value: Int) extends Expression
sealed trait Operator extends Expression

case class Addition(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value + right.value
}
case class Subtraction(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value - right.value
}
case class Multiplication(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value * right.value
}
case class Division(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value / right.value
}

object Expression {

  def apply(target: Int, operands: Seq[Operand]): Option[Expression] = {
    None
  }

  implicit def intToOperand(value: Int): Operand = Operand(value)

}
