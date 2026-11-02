package BT_quanLyKhachSan;



import java.util.Scanner;

public class DichVuThueXe extends DichVu {
    private String loaiPhuongTien; // Xe máy, Ô tô 4 chỗ, Ô tô 7 chỗ, Xe đạp đôi
    private int soNgayThue;

    public DichVuThueXe() {
        super();
    }

    public DichVuThueXe(String maDV, String tenDV, double donGia, String loaiPhuongTien, int soNgayThue) {
        super(maDV, tenDV, donGia);
        this.loaiPhuongTien = loaiPhuongTien;
        this.soNgayThue = soNgayThue;
    }

    @Override
    public void nhap() {
        super.nhap();
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập phương tiện (Xe máy / Ô tô 4 chỗ / Ô tô 7 chỗ / Xe đạp đôi): ");
        loaiPhuongTien = sc.nextLine();
        System.out.println("Nhập số ngày thuê: ");
        soNgayThue = Integer.parseInt(sc.nextLine());
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" Phương tiện: " + loaiPhuongTien + " | Số ngày thuê: " + soNgayThue);
    }
}