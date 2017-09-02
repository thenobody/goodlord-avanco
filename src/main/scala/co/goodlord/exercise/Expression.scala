package co.goodlord.exercise

sealed trait Expression {
  def value: Int
}
case class Operand(value: Int) extends Expression {
  override def toString: String = value.toString
}
sealed trait Operator extends Expression

case class Addition(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value + right.value
  override def toString: String = s"($left + $right)"
}
case class Subtraction(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value - right.value
  override def toString: String = s"($left - $right)"
}
case class Multiplication(left: Expression, right: Expression) extends Operator {
  def value: Int = left.value * right.value
  override def toString: String = s"($left * $right)"
}

object Expression {

  def apply(target: Int, operands: Seq[Operand]): Option[Expression] = {
    def go(op: Seq[Operand], result: Option[Expression]): Option[Expression] = result match {
      case Some(r) if r.value == target =>
        result
      case Some(r) if r.value < 0 =>
        None
      case Some(exp) =>
        op.flatMap { o =>
          val remainder = op.filter(_ != o)
          val add = Addition(exp, o)
          val sub = Subtraction(exp, o)
          val mult = Multiplication(exp, o)
          Seq(
            go(remainder, Some(add)),
            go(remainder, Some(sub)),
            go(remainder, Some(mult))
          )
        }.collectFirst { case Some(res) => res }
      case None =>
        op.map { o =>
          val remainder = op.filter(_ != o)
          go(remainder, Some(o))
        }.collectFirst { case Some(res) => res }
    }
    go(operands, None)
  }

  implicit def intToOperand(value: Int): Operand = Operand(value)
}

object Main {
  import Expression._
  def main(args: Array[String]): Unit = {
    println(Expression(42, Seq(2, 3, 5, 6)))
    println(Expression(128, Seq(1, 2, 3, 4, 5, 6, 7)))
  }
}
