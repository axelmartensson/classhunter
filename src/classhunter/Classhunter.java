package classhunter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;

public class Classhunter {
	private String rootDir;
	private ImportStatement packageContext;
	private List<ImportStatement> importStatements;

	public Classhunter(String rootDir, String packageContext,
			List<ImportStatement> importStatements) {
		if(!rootDir.endsWith("/")){
			rootDir += "/";
		}
		this.rootDir = rootDir;
		this.packageContext = new ImportStatement(packageContext);
		this.importStatements = importStatements;
	}

	public File find (String className) throws FileNotFoundException{
		File f = findInDirectReferences(className);
		if (f == null){
			f = findInAsteriskedImports(className);
		}
		if (f == null){
			throw new FileNotFoundException();
		}
		return f;
	}
	private File findInAsteriskedImports(String className) {
		for (ImportStatement stmt : importStatements) {
			if(stmt.hasAsterisk()){
				ImportStatement maybeStmt = new ImportStatement(stmt.toString().replace('*', '/')+
						new ImportStatement(className).toPath());
				File f = convertToFile(maybeStmt);
				if(f != null){
					return f;
				}
			}
		}
		return null;
	}

	private File findInDirectReferences(String className){
		for (ImportStatement stmt : importStatements) {
			if(stmt.endsWith(className)){
				return convertToFile(stmt);
			}
		}
		return null;
	}

	private File convertToFile(ImportStatement stmt ) {
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
