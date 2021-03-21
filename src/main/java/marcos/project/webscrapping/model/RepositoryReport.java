package marcos.project.webscrapping.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryReport {

	
	private Long lines;
	private Map<String, BigInteger> files;
	
	
	public RepositoryReport() {
	}


	public Long getLines() {
		if(lines == null) {
			return 0l;
		}
		return lines;
	}


	public void setLines(Long lines) {
		this.lines = lines;
	}


	public Map<String, BigInteger> getFiles() {
		if(this.files == null) {
			return new HashMap<String, BigInteger>();
		}
		return files;
	}


	public void setFiles(Map<String, BigInteger> files) {
		this.files = files;
	}


	public void incrementNumberOfLines(Long noOfLines) {
		setLines(getLines() + noOfLines);
	}
	
	
	public void addExtensionAndSize(final String extension, final BigInteger size) {
		getFiles().computeIfPresent(extension, (k, v) ->  v.add(size));
	}
	


	
	
	
}
