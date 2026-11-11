

import java.util.Scanner;

public abstract class DichVu {
    protected static final Scanner sc = new Scanner(System.in);

    protected String maDV;
    protected String tenDV;
    protected double donGia;

    public DichVu() {}

    public DichVu(String maDV, String tenDV, double donGia) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    // Nhập chuỗi không được rỗng
    protected static String nhapChuoi(String thongBao) {
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
    protected static double nhapSoThucDuong(String thongBao) {
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
    protected static int nhapSoNguyen(String thongBao, int min, int max) {
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

    public void nhap() {
        maDV = nhapChuoi("Nhập mã dịch vụ: ");
        tenDV = nhapChuoi("Nhập tên dịch vụ: ");
        donGia = nhapSoThucDuong("Nhập đơn giá: ");
    }

    public void xuat() {
        System.out.println("Mã DV: " + maDV + " | Tên: " + tenDV + " | Đơn giá: " + donGia);
    }
}
