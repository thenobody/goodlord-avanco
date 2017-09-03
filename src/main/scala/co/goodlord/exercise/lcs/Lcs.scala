package co.goodlord.exercise.lcs

import scala.collection.mutable

trait Cached[K1, K2, V] {
  val cache: mutable.Map[(K1, K2), V] = mutable.Map()
  def cached(left: K1, right: K2)(callback: (K1, K2) => V): V = cache.getOrElseUpdate((left, right), callback(left, right))
}

object Lcs extends Cached[Seq[Char], Seq[Char], Seq[Char]] {

  def apply(left: Seq[Char], right: Seq[Char]): Seq[Char] = cached(left, right) {
    case (Nil, _) => Seq()
    case (_, Nil) => Seq()
    case (lx +: lxs, rx +: rxs) if lx == rx => lx +: Lcs(lxs, rxs)
    case (lx +: lxs, rx +: rxs) =>
      (Lcs(lxs, rx +: rxs), Lcs(lx +: lxs, rxs)) match {
        case (l, r) if l.size > r.size => l
        case (_, r) => r
      }
  }
}

object Main {
  def main(args: Array[String]): Unit = Config(args).foreach { config =>
    println(s"result: '${Lcs(config.left, config.right).mkString}'")
  }
}

case class Config(left: String = "", right: String = "")

object Config {
  def apply(args: Array[String]): Option[Config] = {
    val parser = new scopt.OptionParser[Config]("Longest Common Subsequence") {
      opt[String]('l', "left").required.action { (l, c) => c.copy(left = l) }
        .text(s"left string")
      opt[String]('r', "right").required.action { (r, c) => c.copy(right = r) }
        .text(s"right string")
    }
    parser.parse(args, Config())
  }
}
