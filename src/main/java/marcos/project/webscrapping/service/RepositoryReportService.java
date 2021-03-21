package marcos.project.webscrapping.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import marcos.project.webscrapping.model.RepositoryReport;

@Component
public class RepositoryReportService {

	public RepositoryReport searchByFilters(String repositoryAddress) {

		return downloadRepository(repositoryAddress);
	}

	private RepositoryReport downloadRepository(String repositoryAddress) {

		RepositoryReport repositoryReport = new RepositoryReport();

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(repositoryAddress);
			try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
				final HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					try (InputStream inputStream = entity.getContent()) {
						readInputStream(inputStream);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return repositoryReport;

	}

	private void readInputStream(InputStream inputStream) {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				extractElement(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void extractElement(String line) {
	
		
	}

	@SuppressWarnings("unused")
	private void handlesTextFiles(File file, RepositoryReport repositoryReport) {
		long noOfLines = 1;
		try (FileChannel channel = new FileInputStream(file).getChannel()) {
			ByteBuffer byteBuffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
			while (byteBuffer.hasRemaining()) {
				byte currentByte = byteBuffer.get();
				if (currentByte == '\n') {
					noOfLines++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		repositoryReport.incrementNumberOfLines(noOfLines);
	}

	@SuppressWarnings("unused")
	private void handlesBinaryFiles(File file, RepositoryReport repositoryReport) {

		Optional<String> optionalExtension = getExtension(file.getName());
		String extension = "";
		if (optionalExtension.isPresent()) {
			extension = optionalExtension.get();
		}

		long length = file.length();
		repositoryReport.addExtensionAndSize(extension, BigInteger.valueOf(length));

	}

	@SuppressWarnings("unused")
	private boolean isBinaryFile(File f) throws IOException {
		String type = Files.probeContentType(f.toPath());
		if (type == null) {
			// type couldn't be determined, assume binary
			return true;
		} else if (type.startsWith("text")) {
			return false;
		} else {
			// type isn't text
			return true;
		}
	}

	private Optional<String> getExtension(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
