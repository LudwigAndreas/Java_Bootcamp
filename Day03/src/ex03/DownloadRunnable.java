
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DownloadRunnable implements Runnable {
    private final File downloadedFile;
    private URL url;
    private final int fileNumber;

    public DownloadRunnable(URL url, File downloadFolder, int fileNumber) {
        this.url = url;
        this.downloadedFile = new File(createDownloadFileName(url.toString(), downloadFolder.toString()));
        this.fileNumber = fileNumber;

    }

    public static String createDownloadFileName(String url, String downloadDirectory) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);

        return downloadDirectory + File.separator + fileName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start download file number " + fileNumber);
        try {
            while (true) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    String newUrl = httpURLConnection.getHeaderField("Location");
                    url = new URL(newUrl);
                } else {
                    break;
                }
            }

            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(downloadedFile);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            readableByteChannel.close();
            fileOutputStream.close();
            fileChannel.close();
            System.out.println(Thread.currentThread().getName() + " finish download file number " + fileNumber);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " error downloading file " + fileNumber);
        }
    }
}
