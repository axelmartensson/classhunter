package classhunter;


public class SimpleImportStatement implements HuntableImportStatement {
	private String qualifier;
	
	public SimpleImportStatement(String qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public boolean hasAsterisk() {
		return qualifier.endsWith("*");
	}

	@Override
	public boolean endsWith(String className) {
		return qualifier.endsWith(className);
	}

	@Override
	public String toPath() {
		String path = qualifier.replace('.','/');
		if(hasAsterisk()){
			return path.replace('*', '/');
		} else {
			return path;
		}
	}
	
	@Override
	public String toString(){
		return qualifier;
	}
}
