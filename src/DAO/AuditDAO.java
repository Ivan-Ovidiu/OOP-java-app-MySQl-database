package DAO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditDAO {
    private static final String filename = "audit.csv";

    public static void writeAudit(String action)  {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("%s,%s%n", action, timestamp);
        try(PrintWriter pw = new PrintWriter(filename))
        {
            pw.write(logEntry);

        }catch(FileNotFoundException e) {
            System.out.println("Error creating / writing file:");
            e.printStackTrace();}
    }
}
