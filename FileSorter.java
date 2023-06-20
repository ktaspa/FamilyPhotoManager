import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

public class FileSorter {
    private static boolean isRunning = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the source folder path: ");
        String sourceFolderPath = scanner.nextLine();

        System.out.print("Enter the destination folder path: ");
        String destinationFolderPath = scanner.nextLine();

        System.out.println("Press 's' to stop the program at any time.");

        File sourceFolder = new File(sourceFolderPath);
        File[] files = sourceFolder.listFiles();

        if (files != null) {
            Set<String> yearsSet = new HashSet<>();

            for (File file : files) {
                if (file.isFile()) {
                    if (!isRunning) {
                        System.out.println("Program stopped by user.");
                        break;
                    }

                    String destinationSubfolderPath = getDestinationFolderPath(file, destinationFolderPath, yearsSet);
                    File destinationSubfolder = new File(destinationSubfolderPath);

                    if (!destinationSubfolder.exists()) {
                        destinationSubfolder.mkdirs();
                    }

                    Path sourceFilePath = file.toPath();
                    Path destinationFilePath = new File(destinationSubfolderPath, file.getName()).toPath();

                    try {
                        Files.move(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved file: " + file.getName());
                    } catch (Exception e) {
                        System.out.println("Failed to move file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }

            // Print the unique years found
            System.out.println("Unique years in the files: " + yearsSet);
        } else {
            System.out.println("No files found in the source folder.");
        }

        scanner.close();
    }

    public static String getDestinationFolderPath(File file, String destinationFolderPath, Set<String> yearsSet) {
        String year = "";

        try {
            Path filePath = file.toPath();
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            Date creationDate = new Date(fileAttributes.lastModifiedTime().toMillis());
            year = new SimpleDateFormat("yyyy").format(creationDate);

            // Add the year to the set
            yearsSet.add(year);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the destination folder path based on the year
        return destinationFolderPath + File.separator + year;
    }

    public static void stopProgram() {
        isRunning = false;
    }
}
