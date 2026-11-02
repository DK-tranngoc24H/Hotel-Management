/**
 * Lớp DichVu – đại diện cho một dịch vụ mà khách hàng sử dụng.
 * Lưu trữ tên dịch vụ, đơn giá và số lượng; cung cấp phương thức tính thành tiền.
 */
public class DichVu {

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    private String tenDichVu;  // Tên dịch vụ
    private double donGia;     // Đơn giá (VNĐ)
    private int    soLuong;    // Số lượng sử dụng

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------

    /**
     * Tạo đối tượng dịch vụ.
     *
     * @param tenDichVu Tên dịch vụ
     * @param donGia    Đơn giá (VNĐ)
     * @param soLuong   Số lượng sử dụng
     */
    public DichVu(String tenDichVu, double donGia, int soLuong) {
        this.tenDichVu = tenDichVu;
        this.donGia    = donGia;
        this.soLuong   = soLuong;
    }

    // ---------------------------------------------------------------
    // Tính toán
    // ---------------------------------------------------------------

    /** Trả về thành tiền = đơn giá × số lượng */
    public double tinhTien() {
        return this.donGia * this.soLuong;
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------
    public String getTenDichVu() { return tenDichVu; }
    public double getDonGia()    { return donGia; }
    public int    getSoLuong()   { return soLuong; }

    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }
    public void setDonGia(double donGia)       { this.donGia    = donGia; }
    public void setSoLuong(int soLuong)        { this.soLuong   = soLuong; }

    // ---------------------------------------------------------------
    // toString – dùng khi in danh sách dịch vụ
    // ---------------------------------------------------------------
    @Override
    public String toString() {
        String gia      = String.format("%,.0f", donGia);
        String thanhTien = String.format("%,.0f", tinhTien());
        return String.format("%-20s | %s VND x %d = %s VND", tenDichVu, gia, soLuong, thanhTien);
    }
}