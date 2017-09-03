package co.goodlord.exercise.expression

import scopt.Read

sealed trait Expression {
  def value: Int
}
case class Operand(value: Int) extends Expression {
  override def toString: String = value.toString
}
sealed trait Operator extends Expression

case class Addition(left: Expression, right: Expression) extends Operator {
  val value: Int = left.value + right.value
  override def toString: String = s"($left + $right)"
}
case class Subtraction(left: Expression, right: Expression) extends Operator {
  val value: Int = left.value - right.value
  override def toString: String = s"($left - $right)"
}
case class Multiplication(left: Expression, right: Expression) extends Operator {
  val value: Int = left.value * right.value
  override def toString: String = s"($left * $right)"
}

object Expression {

  def apply(target: Int, operands: Seq[Operand]): Option[Expression] = {
    def go(op: Seq[Operand], result: Option[Expression]): Option[Expression] = result match {
      case Some(r) if r.value == target => result
      case Some(r) if r.value < 0 => None
      case Some(exp) =>
        op.map { o =>
          val remainder = op.filter(_ != o)
          val add = Addition(exp, o)
          val sub = Subtraction(exp, o)
          val mult = Multiplication(exp, o)
          go(remainder, Some(add)).orElse(go(remainder, Some(sub))).orElse(go(remainder, Some(mult)))
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

  def main(args: Array[String]): Unit = Config(args).foreach { config =>
    Expression(config.target, config.operands) match {
      case Some(result) => println(s"result: $result = ${result.value}")
      case None => println(s"target value ${config.target} cannot be obtained from operands ${config.operands.mkString(", ")}")
    }
  }
}

case class Config(operands: Seq[Operand] = Seq(), target: Int = -1)

object Config {
  implicit val operandRead = Read.reads[Operand] { implicitly[Read[Int]].reads(_) }

  def apply(args: Array[String]): Option[Config] = {
    val parser = new scopt.OptionParser[Config]("Longest Common Subsequence") {
      opt[Seq[Operand]]("operands").required.action { (ops, c) => c.copy(operands = ops) }
        .text(s"comma-separated list of operands (e.g '1,2,3,4,5')")
      opt[Int]("target").required.action { (t, c) => c.copy(target = t) }
        .text(s"the target value of the expression")
    }
    parser.parse(args, Config())
  }
}
