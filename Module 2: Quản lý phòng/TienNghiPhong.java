import java.util.Scanner;

public class TienNghiPhong {
    private String maTienNghi;
    private String tenTienNghi;
    private int soLuong;
    private String tinhTrang;

    public TienNghiPhong() {
        this.maTienNghi = "";
        this.tenTienNghi = "";
        this.soLuong = 0;
        this.tinhTrang = "";
    }

    public TienNghiPhong(String maTienNghi, String tenTienNghi, int soLuong, String tinhTrang) {
        this.maTienNghi = maTienNghi;
        this.tenTienNghi = tenTienNghi;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
    }

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
        this.soLuong = soLuong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public boolean isConTot() {
        return "tot".equalsIgnoreCase(this.tinhTrang) || "tốt".equalsIgnoreCase(this.tinhTrang);
    }

    public void nhap(Scanner sc) {
        System.out.print("Nhap ma tien nghi: ");
        this.maTienNghi = sc.nextLine();
        System.out.print("Nhap ten tien nghi: ");
        this.tenTienNghi = sc.nextLine();
        System.out.print("Nhap so luong: ");
        this.soLuong = sc.nextInt();
        sc.nextLine();
        System.out.print("Nhap tinh trang (Tot / Can sua / Hong): ");
        this.tinhTrang = sc.nextLine();
    }

    public void xuat() {
        System.out.printf("Ma TN: %-8s | Ten TN: %-15s | So luong: %-3d | Tinh trang: %-10s\n",
                maTienNghi, tenTienNghi, soLuong, tinhTrang);
    }
}