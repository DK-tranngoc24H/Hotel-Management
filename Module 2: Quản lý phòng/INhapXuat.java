import java.util.Scanner;

/**
 * Interface định nghĩa quy chuẩn nhập và xuất dữ liệu.
 * Giúp mã nguồn có tính trừu tượng và chuẩn hóa cao hơn trong OOP.
 */
public interface INhapXuat {
    void nhap(Scanner sc);
    void xuat();
}
