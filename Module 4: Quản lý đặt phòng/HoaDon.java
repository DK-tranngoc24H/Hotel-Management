import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Lớp HoaDon – tính toán và hiển thị hóa đơn thanh toán của khách hàng.
 * Bao gồm: tiền phòng, tiền dịch vụ, giảm giá, thuế và tổng tiền phải trả.
 */
public class HoaDon {

    // ---------------------------------------------------------------
    // Hằng số – đơn giá phòng và thuế suất
    // ---------------------------------------------------------------
    private static final double DON_GIA_PHONG_THUONG = 500_000; // 500.000 VNĐ/đêm
    private static final double THUE_SUAT            = 0.10;    // 10% thuế VAT

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    private final PhieuDatPhong phieu;       // Phiếu đặt phòng liên kết
    private final double        tienDichVu;  // Tổng tiền dịch vụ phát sinh
    private final double        giamGia;     // Số tiền được giảm giá

    private final long   soNgayLuuTru;   // Số đêm lưu trú (tính tự động)
    private final double tienPhong;      // Tiền phòng trước thuế
    private final double tongTienPhong;  // Tổng tiền phải trả (sau thuế, sau giảm)

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------

    /**
     * Tạo hóa đơn từ phiếu đặt phòng.
     *
     * @param tienDichVu Tổng tiền dịch vụ đã sử dụng (lấy từ ChiTietHoaDon)
     * @param giamGia    Số tiền giảm giá (nhập 0 nếu không có)
     * @param phieu      Phiếu đặt phòng của khách
     */
    public HoaDon(double tienDichVu, double giamGia, PhieuDatPhong phieu) {
        this.phieu       = phieu;
        this.tienDichVu  = tienDichVu;
        this.giamGia     = giamGia;

        this.soNgayLuuTru  = tinhNgayLuuTru();
        this.tienPhong     = tinhTienPhong();
        this.tongTienPhong = tinhTongTienPhong();
    }

    // ---------------------------------------------------------------
    // Tính toán nội bộ (private)
    // ---------------------------------------------------------------

    /** Tính số đêm lưu trú dựa trên ngày nhận và ngày trả */
    private long tinhNgayLuuTru() {
        LocalDate ngayNhan = phieu.getNgayNhan();
        LocalDate ngayTra  = phieu.getNgayTra();

        long soNgay = ChronoUnit.DAYS.between(ngayNhan, ngayTra);

        if (soNgay < 0)
            throw new IllegalArgumentException("Lỗi: Ngày trả phòng không thể trước ngày nhận phòng!");

        // Nếu trả cùng ngày nhận → tính 1 đêm
        return (soNgay == 0) ? 1 : soNgay;
    }

    /** Tính tiền phòng = đơn giá × số đêm */
    private double tinhTienPhong() {
        return DON_GIA_PHONG_THUONG * this.soNgayLuuTru;
    }

    /**
     * Tính tổng tiền phải trả:
     *   (tiền phòng + tiền dịch vụ - giảm giá) × (1 + thuế VAT)
     */
    private double tinhTongTienPhong() {
        double tienTruocThue = this.tienPhong + this.tienDichVu - this.giamGia;
        if (tienTruocThue <= 0) return 0;
        return tienTruocThue * (1 + THUE_SUAT);
    }

    // ---------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------
    public double getTienPhong()     { return tienPhong; }
    public double getTienDichVu()    { return tienDichVu; }
    public double getGiamGia()       { return giamGia; }
    public double getTongTienPhong() { return tongTienPhong; }
    public long   getSoNgayLuuTru()  { return soNgayLuuTru; }

    // ---------------------------------------------------------------
    // Xuất hóa đơn
    // ---------------------------------------------------------------

    /** In hóa đơn đầy đủ ra màn hình */
    public void xuatHoaDon() {
        System.out.println("======================================");
        System.out.println("              HOA DON                ");
        System.out.println("======================================");

        // Thông tin khách
        phieu.getThongTinKhachHang().xuatThongTin();
        System.out.println("--------------------------------------");

        // Thông tin đặt phòng
        System.out.println("Ngay dat phong  : " + phieu.getNgayDatStr());
        System.out.println("Ngay nhan phong : " + phieu.getNgayNhanStr());
        System.out.println("Ngay tra phong  : " + phieu.getNgayTraStr());
        System.out.println("So dem luu tru  : " + soNgayLuuTru + " dem");
        System.out.println("--------------------------------------");

        // Chi tiết tiền
        System.out.printf("Tien phong      : %,.0f VND%n", tienPhong);
        System.out.printf("Tien dich vu    : %,.0f VND%n", tienDichVu);
        System.out.printf("Giam gia        : %,.0f VND%n", giamGia);
        System.out.printf("Thue VAT (10%%) : %,.0f VND%n", (tienPhong + tienDichVu - giamGia) * THUE_SUAT);
        System.out.println("--------------------------------------");
        System.out.printf("TONG TIEN PHAI TRA: %,.0f VND%n", tongTienPhong);
        System.out.println("======================================");
    }
}
