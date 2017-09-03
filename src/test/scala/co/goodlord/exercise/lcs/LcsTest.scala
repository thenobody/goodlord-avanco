package co.goodlord.exercise.lcs

import org.scalatest.{FlatSpec, Matchers}

class LcsTest extends FlatSpec with Matchers {

  behavior of Lcs.getClass.getSimpleName

  it should "produce the longest common subsequence of two string" in {
    val input1: (Seq[Char], Seq[Char]) = ("AABACDA", "DACBBCA")
    val expResult1: Seq[Char] = "ABCA"

    val input2: (Seq[Char], Seq[Char]) = ("QWEQWAEQWEAQWEBQWEQWECQWEQWEDQWE", "XYZXYZXYZAXYZXYZBXYZXYZCDXYAZ")
    val expResult2: Seq[Char] = "ABCD"

    val input3: (Seq[Char], Seq[Char]) = ("ABCD", "QWERTY")

    val result1 = Lcs.apply _ tupled input1
    val result2 = Lcs.apply _ tupled input2
    val result3 = Lcs.apply _ tupled input3

    result1 shouldEqual expResult1
    result2 shouldEqual expResult2
    result3 shouldBe 'empty
  }

}
