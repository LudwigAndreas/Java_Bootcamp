

import java.io.File;
import java.net.URL;
import java.util.List;

public class FileDownloader {
    private final File downloadFolder;
    private final File urlsFile;
    private int threadsCount;
    private List<URL> urls;

    public FileDownloader(String downloadFolder, String urlsFile, int threadsCount) {
        this.downloadFolder = new File(downloadFolder);
        this.urlsFile = new File(urlsFile);
        this.threadsCount = threadsCount;

        if (!this.downloadFolder.exists() && !this.downloadFolder.mkdirs()) {
            System.err.println("Can not find or create download folder: " + downloadFolder);
            System.exit(1);
        }

        if (!this.urlsFile.exists()) {
            System.err.println("Can not find urls file: " + urlsFile);
            System.exit(1);
        }

        if (threadsCount < 1) {
            System.err.println("Threads count must be greater than 0");
            System.exit(1);
        }
        readUrls();
    }

    public void readUrls() {
        urls = Utils.readUrls(this.urlsFile);
    }

    public void start() {

        if (threadsCount > urls.size()) {
            threadsCount = urls.size();
        }
        ExecutorService executor = new ExecutorService(threadsCount);
        for (int i = 0; i < urls.size(); i++) {
            executor.submit(new DownloadRunnable(urls.get(i), downloadFolder, i));
        }

        try {
            executor.waitForCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
