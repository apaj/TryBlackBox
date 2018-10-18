package tryblackbox

import chisel3._
import chisel3.util.HasBlackBoxResource

class TryBlackBox extends BlackBox with HasBlackBoxResource {
  val io = IO(new Bundle() {
    val in1 = Input(UInt(64.W))
    val in2 = Input(UInt(64.W))
    val out = Output(UInt(64.W))
  })
  setResource("/tryblackbox/tryMe.v")
}
