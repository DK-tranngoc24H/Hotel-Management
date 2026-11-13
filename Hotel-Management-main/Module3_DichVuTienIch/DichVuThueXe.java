

public class DichVuThueXe extends DichVu {
    private String loaiPhuongTien;   
    private int soNgayThue;

    public DichVuThueXe() {
        super();
    }

    public DichVuThueXe(String maDV, String tenDV, double donGia, String loaiPhuongTien, int soNgayThue) {
        super(maDV, tenDV, donGia);
        this.loaiPhuongTien = loaiPhuongTien;
        this.soNgayThue = soNgayThue;
    }

    public String getLoaiPhuongTien() {
        return loaiPhuongTien;
    }

    public int getSoNgayThue() {
        return soNgayThue;
    }

    @Override
    public void nhap(NhapLieu nl) {
        super.nhap(nl);
        loaiPhuongTien = nl.nhapChuoi("Nhập phương tiện (VD: Xe máy / Ô tô 4 chỗ / Ô tô 7 chỗ / Xe đạp đôi): ");
        soNgayThue = nl.nhapSoNguyen("Nhập số ngày thuê (từ 1 đến 365): ", 1, 365);
    }

    @Override
    public String moTaChiTiet() {
        return loaiPhuongTien + " - " + soNgayThue + " ngày";
    }
}
