package classhunter;

public interface HuntableImportStatement {

	public abstract boolean hasAsterisk();

	public abstract boolean endsWith(String className);

	public abstract String toPath();

	public abstract String toString();

}