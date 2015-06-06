package eva.main

import eva.front.LambdaParser

object LambdaEva {
  val parser = new LambdaParser()

  def main(args: Array[String]): Unit = {
    import parser.{NoSuccess, Success}
    parser.parse(args(0)) match {
      case Success(expr, _) => println("Parsed: " + expr)
      case err: NoSuccess   => println(err)
    }
  }
}
