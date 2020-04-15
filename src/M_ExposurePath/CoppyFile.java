package M_ExposurePath;

import java.io.*;

public class CoppyFile {
	public static void main(String[] args) throws Exception {
        // Dữ liệu được ghi trên Console sử dụng lớp PrintWriter
        PrintWriter writer = new PrintWriter(System.out);
        writer.write("VietTuts.Vn: ");
        writer.flush();
        writer.close();
        // Dữ liệu được ghi vào File sử dụng PrintWriter
        PrintWriter writer1 = null;
        double c = 1.2;
        writer1 = new PrintWriter(new File("C:\\Users\\20161\\Desktop\\BTL_Lab\\src\\M_ExposurePath\\output.txt"));
        writer1.write(c + "Java, Spring, Hibernate, Android, PHP, ...");
        writer1.flush();
        writer1.close();
    }
}