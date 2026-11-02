import java.util.Scanner;

/**
 * Lớp đại diện cho tiện nghi có trong phòng (ví dụ: Tivi, Tủ lạnh, Điều hòa...)
 * Lớp này sẽ được dùng làm thuộc tính bên trong lớp Phong (Quan hệ Has-A).
 */
public class TienNghiPhong implements INhapXuat {
    private String maTienNghi;
    private String tenTienNghi;
    private int soLuong;
    private String tinhTrang; // "Tot" hoặc "Hong" / "Can sua"

    // Constructor mặc định
    public TienNghiPhong() {
        this.maTienNghi = "";
        this.tenTienNghi = "";
        this.soLuong = 0;
        this.tinhTrang = "Tot";
    }

    // Constructor đầy đủ tham số
    public TienNghiPhong(String maTienNghi, String tenTienNghi, int soLuong, String tinhTrang) {
        this.maTienNghi = maTienNghi;
        this.tenTienNghi = tenTienNghi;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
    }

    // Getters và Setters
    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getTenTienNghi() {
        return tenTienNghi;
    }

    public void setTenTienNghi(String tenTienNghi) {
        this.tenTienNghi = tenTienNghi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = (soLuong >= 0) ? soLuong : 0;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    // Kiểm tra tiện nghi còn dùng tốt hay không (hỗ trợ cả có dấu và không dấu)
    public boolean isConTot() {
        return "tot".equalsIgnoreCase(this.tinhTrang) || "tốt".equalsIgnoreCase(this.tinhTrang);
    }

    @Override
    public void nhap(Scanner sc) {
        System.out.print("  + Nhap ma tien nghi: ");
        this.maTienNghi = sc.nextLine().trim();
        System.out.print("  + Nhap ten tien nghi: ");
        this.tenTienNghi = sc.nextLine().trim();
        System.out.print("  + Nhap so luong: ");
        this.soLuong = sc.nextInt();
        sc.nextLine(); // Chống trôi lệnh
        System.out.print("  + Nhap tinh trang (Tot / Can sua / Hong): ");
        this.tinhTrang = sc.nextLine().trim();
    }

    @Override
    public void xuat() {
        System.out.printf("    [Tien nghi] Ma: %-6s | Ten: %-12s | SL: %-2d | Tinh trang: %-8s\n",
                maTienNghi, tenTienNghi, soLuong, tinhTrang);
    }

    @Override
    public String toString() {
        return String.format("[Tien nghi: %s - %s, SL: %d, TT: %s]",
                maTienNghi, tenTienNghi, soLuong, tinhTrang);
    }
}
