package BT_quanLyKhachSan;

public abstract class DichVu {
    // Ký tự ngăn cách các cột trong file dữ liệu
    public static final String SEP = "|";

    protected String maDV;
    protected String tenDV;
    protected double donGia;

    public DichVu() {}

    public DichVu(String maDV, String tenDV, double donGia) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
    }

    public String getMaDV() { return maDV; }
    public void setMaDV(String maDV) { this.maDV = maDV; }

    public String getTenDV() { return tenDV; }
    public void setTenDV(String tenDV) { this.tenDV = tenDV; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    // Mỗi loại dịch vụ tự mô tả phần riêng của mình
    public abstract String moTaChiTiet();

    // Mỗi loại dịch vụ tự biến mình thành một dòng để ghi file
    public abstract String toFileLine();

    // Nhập thông tin chung, lớp con gọi super.nhap() rồi nhập phần riêng
    public void nhap(NhapLieu nl) {
        maDV = nl.nhapChuoi("Nhập mã dịch vụ: ");
        tenDV = nl.nhapChuoi("Nhập tên dịch vụ: ");
        donGia = nl.nhapSoThucDuong("Nhập đơn giá: ");
    }
}
