/**
 * Lớp PhuongThucThanhToan – quản lý hình thức thanh toán của khách hàng.
 * Hỗ trợ 3 hình thức: tiền mặt, chuyển khoản, thẻ tín dụng.
 */
public class PhuongThucThanhToan {

    // ---------------------------------------------------------------
    // Enum – các loại hình thức thanh toán
    // ---------------------------------------------------------------

    /**
     * Các hình thức thanh toán được hỗ trợ.
     * Dùng enum thay vì chuỗi rời rạc để tránh lỗi gõ sai tên.
     */
    public enum LoaiThanhToan {
        TIEN_MAT       ("Tien mat"),
        CHUYEN_KHOAN   ("Chuyen khoan"),
        THE_TIN_DUNG   ("The tin dung");

        private final String tenHienThi;

        LoaiThanhToan(String tenHienThi) {
            this.tenHienThi = tenHienThi;
        }

        public String getTenHienThi() { return tenHienThi; }
    }

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    private final LoaiThanhToan loaiThanhToan;  // Hình thức thanh toán đã chọn
    private final HoaDon        hoaDon;         // Hóa đơn cần thanh toán

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------

    /**
     * Tạo đối tượng thanh toán.
     *
     * @param loaiThanhToan Hình thức thanh toán (dùng enum LoaiThanhToan)
     * @param hoaDon        Hóa đơn cần thanh toán
     */
    public PhuongThucThanhToan(LoaiThanhToan loaiThanhToan, HoaDon hoaDon) {
        this.loaiThanhToan = loaiThanhToan;
        this.hoaDon        = hoaDon;
    }

    // ---------------------------------------------------------------
    // Thực hiện thanh toán
    // ---------------------------------------------------------------

    /** In hóa đơn và xác nhận thanh toán thành công */
    public void thucHienThanhToan() {
        hoaDon.xuatHoaDon();
        System.out.println("Hinh thuc thanh toan : " + loaiThanhToan.getTenHienThi());
        System.out.println(">>> THANH TOAN THANH CONG! <<<");
    }

    // ---------------------------------------------------------------
    // Getter
    // ---------------------------------------------------------------
    public LoaiThanhToan getLoaiThanhToan() { return loaiThanhToan; }
}
