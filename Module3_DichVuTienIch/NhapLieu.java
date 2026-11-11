package BT_quanLyKhachSan;

import java.util.Scanner;

// Lớp chịu trách nhiệm toàn bộ việc đọc dữ liệu từ bàn phím, có kiểm tra lỗi
public class NhapLieu {
    private final Scanner sc = new Scanner(System.in);

    // Nhập chuỗi không được rỗng
    public String nhapChuoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Lỗi: không được để trống. Vui lòng nhập lại.");
        }
    }

    // Nhập số thực dương (đơn giá)
    public double nhapSoThucDuong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            try {
                double so = Double.parseDouble(s);
                if (so > 0) {
                    return so;
                }
                System.out.println("Lỗi: giá trị phải lớn hơn 0. Vui lòng nhập lại.");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: vui lòng nhập một số hợp lệ. Vui lòng nhập lại.");
            }
        }
    }

    // Nhập số nguyên trong khoảng [min, max]
    public int nhapSoNguyen(String thongBao, int min, int max) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            try {
                int so = Integer.parseInt(s);
                if (so >= min && so <= max) {
                    return so;
                }
                System.out.println("Lỗi: giá trị phải nằm trong khoảng " + min + " - " + max + ". Vui lòng nhập lại.");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: vui lòng nhập một số nguyên hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
}