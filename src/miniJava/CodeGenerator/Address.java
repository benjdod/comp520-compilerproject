package miniJava.CodeGenerator;

import mJAM.Machine.Reg;

public class Address {
	public int offset;
	public Reg reg;
	
	public Address (Reg reg, int offset) {
		this.reg = reg;
		this.offset = offset;
	}
	
	public String toString() {
		return this.reg.toString() + " [" + this.offset + "]";
	}
}
