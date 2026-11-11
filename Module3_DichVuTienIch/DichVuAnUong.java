package BT_quanLyKhachSan;

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

    @Override
    public void nhap() {
        super.nhap();
        phanLoai = nhapChuoi("Phân loại (VD: Đồ ăn / Nước uống): ");
        chiTiet = nhapChuoi("Chi tiết (VD: Bữa sáng / Nước ngọt 330ml): ");
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" Loại: " + phanLoai + " | Chi tiết: " + chiTiet);
    }
}
