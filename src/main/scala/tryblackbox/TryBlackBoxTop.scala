package tryblackbox

import chisel3._

class TryBlackBoxTop extends Module {
	val io = IO(new Bundle {
		val in1top = Input(UInt(64.W))
		val in2top = Input(UInt(64.W))
		val outtop = Output(UInt(64.W))
	})

	val tryBlackBoxTopInst = Module(new TryBlackBox())
	tryBlackBoxTopInst.io.in1 := io.in1top
	tryBlackBoxTopInst.io.in2 := io.in2top
	io.outtop := tryBlackBoxTopInst.io.out
}
	
