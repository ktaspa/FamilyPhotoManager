import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main {
    private static boolean isRunning = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the source folder path: ");
        String sourceFolderPath = scanner.nextLine();

        System.out.print("Enter the years to sort the files into (comma-separated): ");
        String yearsInput = scanner.nextLine();
        String[] yearsArray = yearsInput.split(",");

        System.out.println("Press 's' to stop the program at any time.");

        File sourceFolder = new File(sourceFolderPath);
        File[] files = sourceFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!isRunning) {
                        System.out.println("Program stopped by user.");
                        break;
                    }

                    String destinationFolderPath = getDestinationFolderPath(file, yearsArray);
                    File destinationFolder = new File(destinationFolderPath);

                    if (!destinationFolder.exists()) {
                        destinationFolder.mkdirs();
                    }

                    Path sourceFilePath = file.toPath();
                    Path destinationFilePath = new File(destinationFolderPath, file.getName()).toPath();

                    try {
                        Files.move(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved file: " + file.getName());
                    } catch (Exception e) {
                        System.out.println("Failed to move file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("No files found in the source folder.");
        }

        scanner.close();
    }

    public static String getDestinationFolderPath(File file, String[] yearsArray) {
        String destinationDirectoryPath = "path/to/destination/directory";
        String year = "";

        try {
            Path filePath = file.toPath();
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            Date creationDate = new Date(fileAttributes.creationTime().toMillis());
            year = new SimpleDateFormat("yyyy").format(creationDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check if the year matches any of the specified years
        for (String specifiedYear : yearsArray) {
            if (year.equals(specifiedYear.trim())) {
                return destinationDirectoryPath + File.separator + year;
            }
        }

        // If the year doesn't match any specified years, use a default folder
        return destinationDirectoryPath + File.separator + "Other";
    }

    public static void stopProgram() {
        isRunning = false;
    }
}
