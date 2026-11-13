

public class DichVuAnUong extends DichVu {
    private String phanLoai;
    private String chiTiet;

    public DichVuAnUong() {
        super();
    }

    public DichVuAnUong(String maDV, String tenDV, double donGia, String phanLoai, String chiTiet) {
        super(maDV, tenDV, donGia);
        this.phanLoai = phanLoai;
        this.chiTiet = chiTiet;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    @Override
    public void nhap(NhapLieu nl) {
        super.nhap(nl);
        phanLoai = nl.nhapChuoi("Phân loại (VD: Đồ ăn / Nước uống): ");
        chiTiet = nl.nhapChuoi("Chi tiết (VD: Bữa sáng / Nước ngọt 330ml): ");
    }

    @Override
    public String moTaChiTiet() {
        return phanLoai + " - " + chiTiet;
    }
}
