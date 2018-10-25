// See README.md for license details.

package tryblackbox

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class TryBlackBoxUnitTester(c: TryBlackBoxTop) extends PeekPokeTester(c) {
  
  def sumBlackBox(a: Int, b: Int): (Int) = {
    var x = a
    var y = b
    x+y
  }

  private val tryblackbox = c

  for(i <- 1 to 40 by 3) {
    for (j <- 1 to 40 by 7) {
      poke(tryblackbox.io.in1top, i)
      poke(tryblackbox.io.in2top, j)
      step(1)

      val expected_sum = sumBlackBox(i, j)

      expect(tryblackbox.io.outtop, expected_sum)
    }
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly example.test.GCDTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly example.test.GCDTester'
  * }}}
  */
class TryBlackBoxTester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(false && firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "TryBlackBox" should s"sabrati dva broja u verilogu (with $backendName)" in {
      Driver(() => new TryBlackBoxTop, backendName) {
        c => new TryBlackBoxUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new TryBlackBoxTop) {
      c => new TryBlackBoxUnitTester(c)
    } should be (true)
  }

  "using --backend-name verilator" should "be an alternative way to run using verilator" in {
    if(backendNames.contains("verilator")) {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new TryBlackBoxTop) {
        c => new TryBlackBoxUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new TryBlackBoxTop) {
      c => new TryBlackBoxUnitTester(c)
    } should be(true)
  }

  "running with --fint-write-vcd" should "create a vcd file from your test" in {
    iotesters.Driver.execute(Array("--fint-write-vcd"), () => new TryBlackBoxTop) {
      c => new TryBlackBoxUnitTester(c)
    } should be(true)
  }
}
