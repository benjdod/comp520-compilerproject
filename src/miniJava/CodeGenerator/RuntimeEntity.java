package miniJava.CodeGenerator;

public abstract class RuntimeEntity {
	public int size;
	public Address address;
	
	public String toString() {
		return address.toString() + " (" + size + ")";
	}
}
