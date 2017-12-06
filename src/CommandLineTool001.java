import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import static java.lang.String.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;


public class CommandLineTool001 {
    //Here the global variables are defined
    private static String OS = null;
    private static Path path = setPathName();

    public enum choices {
        addBook(1),
        deleteBook(2),
        showAllBooks(3),
        exitProgram(4),
        menu(5);

        int choiceNumber;

        choices(int i) {
            choiceNumber = i;
        }
    }
    public static void main(String[] args) {
        //Clear screen of the console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("------Test-------------------------------------------");
        System.out.println("With this tool you can write book names in a file");
        getOsName();
        System.out.println("The OS you are using is: " + OS);
        System.out.println("The path you are using is " + path);
        try {
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void menu() throws IOException {
        choices selectedChoice = choices.menu;

        while (selectedChoice != choices.exitProgram & !notMacOrWindows) {
            System.out.println();
            System.out.println("Menu:");
            System.out.println("1. Add book");
            System.out.println("2. Delete book");
            System.out.println("3. Show all books");
            System.out.println("4. Stop with this program");

            Scanner c = new Scanner(System.in);
            String input = c.nextLine();
            selectedChoice = Stream.of(choices.values())
                    .filter(i -> i.choiceNumber == Integer.parseInt(input))
                    .findFirst()
                    .orElse(choices.exitProgram);

            switch (selectedChoice) {
                case addBook:
                    addBook();
                    break;
                case deleteBook:
                    deleteBook();
                    break;
                case showAllBooks:
                    showAllBooks();
                    break;
                case exitProgram:
                    exitProgram();
                    break;
            }
        }
    }

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static Path setPathName() {
        if (isWindows() == true) {
            path = Paths.get("c:/users/Gerrit Brands/FileWithBooks777.txt");
        }
        if (isMac() == true) {
            path = Paths.get("//Users/gerritbrands/IdeaProjects/FileWithBooks777.txt");
        }
        if (!isWindows() & !isMac()) {
            notMacOrWindows=true;
            System.out.println("Your OS doesn't support this program from functioning correctly !");
        }
        return path;
    }

    public static boolean notMacOrWindows;

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isMac() {
        return getOsName().startsWith("Mac");
    }

    private static void addBook() throws IOException {
        // 1) create file if not exist on disk
        boolean pathExists =
                Files.exists(path,new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        if (pathExists) System.out.println("The file is already existing");

        else {
            System.out.print("The file doesn't exist yet and is therefore created now ");
            File destFile = new File(valueOf(path));

            if (isWindows() == true) {
                destFile = new File(valueOf(path));
            }
            new FileOutputStream(destFile);
            System.out.println(destFile);
        }

        // 2) read book from console
        String nameOfTheBook;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter a book you read and want to add: ");
        nameOfTheBook = s.nextLine();
        System.out.println(nameOfTheBook);

        // 3) append book to end of file
        if (isWindows() == true) {
            List<String> lines = Files.readAllLines(Paths.get(valueOf(path)));
            lines.add(nameOfTheBook);
            Files.write(Paths.get(valueOf(path)), lines, UTF_8, CREATE);
        } else {
            List<String> lines = Files.readAllLines(Paths.get(valueOf(path)));
            lines.add(nameOfTheBook);
            Files.write(Paths.get(valueOf(path)), lines, UTF_8, CREATE);
        }
    }

    private static void deleteBook() throws IOException {
        List<String> lines;
        int x;
        int y;

        //Read all lines from file
        lines = Files.readAllLines(Paths.get(valueOf(path)));
        System.out.println(lines);
        x = lines.size();
        System.out.println("Amount of books in the file: "+x);

        //Read book you want to delete
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the number of the book you want to delete: ");
        y = Integer.parseInt(s.nextLine());
        System.out.println(y);

        //Remove book that has to be deleted
        if (y<=x) {
            lines.remove(y - 1);
            x = lines.size();
            System.out.println("Amount of books after delete action: " + x);

            //Write remaining books delete old file and write new file
            Files.delete(Paths.get(valueOf(path)));
            Files.write(Paths.get(valueOf(path)), lines, UTF_8, CREATE);
        }
        else {System.out.print("The book you want to delete is not existing !");}
    }


    public static void showAllBooks() throws IOException {
        List<String> lines;
        int x;
        int y = 1;
        boolean pathExists = Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        if (!pathExists) {
            System.out.println("There are no books in the file right now!");
        }
        if (pathExists) {

            lines = Files.readAllLines(Paths.get(valueOf(path)));
            //Determine amount of books in file
            x = lines.size();

            lines = Files.readAllLines(Paths.get(valueOf(path)));

            System.out.println("These are all the books that are currently in the file");
            System.out.println(lines);
            while (y != x + 1) {
                System.out.println(y + "e boek = " + lines.subList(y - 1, y));
                y = y + 1;
            }
        }
    }


    private static void exitProgram() {
        System.out.println("Thanks for using this program, See you ! :-) ");
    }
}

