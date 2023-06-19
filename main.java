import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the source folder path: ");
        String sourceFolderPath = scanner.nextLine();

        System.out.print("Enter the years to sort the files into (comma-separated): ");
        String yearsInput = scanner.nextLine();
        String[] yearsArray = yearsInput.split(",");

        File sourceFolder = new File(sourceFolderPath);
        File[] files = sourceFolder.listFiles();

        