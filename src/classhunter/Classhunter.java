package classhunter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;


public class Classhunter {
	private String rootDir;
	private HuntableImportStatement packageContext;
	private List<HuntableImportStatement> importStatements;

	public Classhunter(String rootDir, String packageContext,
			List<HuntableImportStatement> importStatements) {
		if(!rootDir.endsWith("/")){
			rootDir += "/";
		}
		this.rootDir = rootDir;
		this.packageContext = new SimpleImportStatement(packageContext);
		this.importStatements = importStatements;
	}

	public File find (String className) {
		File f = findInDirectReferences(className);
		if (f == null){
			f = findInAsteriskedImports(className);
		}
		return f;
	}
	private File findInAsteriskedImports(String className) {
		for (HuntableImportStatement stmt : importStatements) {
			if(stmt.hasAsterisk()){
				HuntableImportStatement maybeStmt = new SimpleImportStatement(stmt.toString().replace('*', '/')+
						new SimpleImportStatement(className).toPath());
				File f = convertToFile(maybeStmt);
				if(f != null){
					return f;
				}
			}
		}
		return null;
	}

	private File findInDirectReferences(String className){
		for (HuntableImportStatement stmt : importStatements) {
			if(stmt.endsWith(className)){
				return convertToFile(stmt);
			}
		}
		return null;
	}

	private File convertToFile(HuntableImportStatement stmt ) {
		File f = null;
		String statementPath = stmt.toPath();
		f = new File(rootDir+statementPath + ".java");
		if(f.exists()){
			return f;
		}
		
		f = new File(rootDir+packageContext.toPath()+'/'+statementPath + ".java");
		if(f.exists()){
			return f;
		}
		return null;
	}
}
