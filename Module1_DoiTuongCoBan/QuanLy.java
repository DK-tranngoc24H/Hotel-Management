/**
 * Lop QuanLy - nhan vien giu chuc vu quan ly, ke thua NhanVien.
 * Bo sung phong ban quan ly va phu cap chuc vu.
 * Dinh dang file: QUANLY;hoten;cccd;sdt;diachi;manv;chucvu;luong;phongBQL;phuCap
 */
public class QuanLy extends NhanVien {
    private String phongBQL;
    private double phuCapChucVu;

    public QuanLy() {
        super();
        this.phongBQL = "";
        this.phuCapChucVu = 0.0;
    }

    public QuanLy(String hoten, String CCCD, String sdt, String diachi, String manv, String chucvu, int luong,
            String phongBQL, double phuCapChucVu) {
        super(hoten, CCCD, sdt, diachi, manv, chucvu, luong);
        this.phongBQL = "";
        this.phuCapChucVu = 0.0;
        setPhongBQL(phongBQL);
        setPhuCapChucVu(phuCapChucVu);
    }

    @Override
    public void nhap() {
        super.nhap();

        while (true) {
            phongBQL = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap phong ban quan ly: ");
            if (!phongBQL.contains(";")) {
                break;
            }
            ThongBao.inLoi("Phong ban quan ly khong duoc chua ky tu ';'!");
        }

        phuCapChucVu = KiemTraLoi.nhapSoThucDuong(sc, "Nhap phu cap chuc vu: ");
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Phong Ban Quan Ly: " + phongBQL);
        System.out.printf("Phu Cap Chuc Vu: %,.0f%n", phuCapChucVu);
    }

    // Tong thu nhap = luong co ban + phu cap chuc vu
    public double tinhTongThuNhap() {
        return getLuong() + phuCapChucVu;
    }

    public String getPhongBQL() {
        return phongBQL;
    }

    public double getPhuCapChucVu() {
        return phuCapChucVu;
    }

    public void setPhongBQL(String phongBQL) {
        if (phongBQL == null || phongBQL.trim().isEmpty()) {
            ThongBao.inLoi("Phong ban quan ly khong duoc de trong!");
            return;
        }
        if (phongBQL.contains(";")) {
            ThongBao.inLoi("Phong ban quan ly khong duoc chua ky tu ';'!");
            return;
        }
        this.phongBQL = phongBQL.trim();
    }

    public void setPhuCapChucVu(double phuCapChucVu) {
        if (phuCapChucVu < 0) {
            ThongBao.inLoi("Phu cap chuc vu khong duoc am!");
            return;
        }
        this.phuCapChucVu = phuCapChucVu;
    }
}
