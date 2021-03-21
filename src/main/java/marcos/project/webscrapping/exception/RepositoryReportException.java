package marcos.project.webscrapping.exception;

public class RepositoryReportException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepositoryReportException(String message) {
		super(message);
	}

	public RepositoryReportException(String message, Exception e) {
		super(message, e);
	}

	public RepositoryReportException(Exception e) {
		super(e);
	}

}
