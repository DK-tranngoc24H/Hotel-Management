package BT_quanLyKhachSan;

public class DichVuAnUong extends DichVu {
    private String phanLoai;   // Đồ ăn / Đồ uống
    private String chiTiet;    // Bữa sáng, Không cồn - 330ml, ...

    public DichVuAnUong() {}

    public DichVuAnUong(String maDV, String tenDV, double donGia, String phanLoai, String chiTiet) {
        super(maDV, tenDV, donGia);
        this.phanLoai = phanLoai;
        this.chiTiet = chiTiet;
    }

    @Override
    public void nhap(NhapLieu nl) {
        super.nhap(nl);
        phanLoai = nl.nhapChuoi("Phân loại (VD: Đồ ăn / Đồ uống): ");
        chiTiet = nl.nhapChuoi("Chi tiết (VD: Bữa sáng / Không cồn - 330ml): ");
    }

    @Override
    public String moTaChiTiet() {
        return phanLoai + " - " + chiTiet;
    }

    @Override
    public String toFileLine() {
        return "AnUong" + SEP + maDV + SEP + tenDV + SEP + donGia + SEP + phanLoai + SEP + chiTiet;
    }
}
