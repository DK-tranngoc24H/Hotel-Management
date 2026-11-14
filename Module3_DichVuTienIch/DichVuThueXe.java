package BT_quanLyKhachSan;

public class DichVuThueXe extends DichVu {
    private String loaiPhuongTien;   // Xe máy, Ô tô 4 chỗ, ...
    private int soNgayThue;

    public DichVuThueXe() {}

    public DichVuThueXe(String maDV, String tenDV, double donGia, String loaiPhuongTien, int soNgayThue) {
        super(maDV, tenDV, donGia);
        this.loaiPhuongTien = loaiPhuongTien;
        this.soNgayThue = soNgayThue;
    }

    @Override
    public void nhap(NhapLieu nl) {
        super.nhap(nl);
        loaiPhuongTien = nl.nhapChuoi("Phương tiện (VD: Xe máy / Ô tô 4 chỗ): ");
        soNgayThue = nl.nhapSoNguyen("Số ngày thuê (1-365): ", 1, 365);
    }

    @Override
    public String moTaChiTiet() {
        return loaiPhuongTien + " - " + soNgayThue + " ngày";
    }

    @Override
    public String toFileLine() {
        return "ThueXe" + SEP + maDV + SEP + tenDV + SEP + donGia + SEP + loaiPhuongTien + SEP + soNgayThue;
    }
}
