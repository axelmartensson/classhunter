package classhunter;

public class ImportStatement {
	private String qualifier;
	
	public ImportStatement(String qualifier) {
		this.qualifier = qualifier;
	}

	boolean hasAsterisk() {
		return qualifier.endsWith("*");
	}

	public boolean endsWith(String className) {
		return qualifier.endsWith(className);
	}

	public String toPath() {
		String path = qualifier.replace('.','/');
		if(hasAsterisk()){
			return path.replace('*', '/');
		} else {
			return path;
		}
	}
	
	public String toString(){
		return qualifier;
	}
}
