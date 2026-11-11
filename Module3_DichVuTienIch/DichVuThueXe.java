package BT_quanLyKhachSan;

public class DichVuThueXe extends DichVu {
    private String loaiPhuongTien;   // VD: Xe máy, Ô tô 4 chỗ, Ô tô 7 chỗ, Xe đạp đôi
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
        loaiPhuongTien = nhapChuoi("Nhập phương tiện (VD: Xe máy / Ô tô 4 chỗ / Ô tô 7 chỗ / Xe đạp đôi): ");
        soNgayThue = nhapSoNguyen("Nhập số ngày thuê (từ 1 đến 365): ", 1, 365);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" Phương tiện: " + loaiPhuongTien + " | Số ngày thuê: " + soNgayThue);
    }
}
