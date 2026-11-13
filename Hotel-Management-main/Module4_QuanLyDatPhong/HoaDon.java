import java.util.Scanner;

/**
 * Lớp HoaDon – tính toán và hiển thị hóa đơn thanh toán toàn diện của khách
 * hàng.
 * Kết hợp đồng bộ:
 * - Tiền phòng (từ PhieuDatPhong + Module 2 Phong/PhongStandard/PhongVIP):
 * Đơn giá/đêm × Số đêm lưu trú.
 * - Tiền dịch vụ (từ ChiTietHoaDon + Module 3 DichVu):
 * Tổng phụ thu các dịch vụ đã sử dụng.
 * - Chiết khấu / Giảm giá và Thuế GTGT (VAT 10%).
 */
public class HoaDon implements INhapXuat {

    // ---------------------------------------------------------------
    // Hằng số – Đơn giá mặc định và thuế suất
    // ---------------------------------------------------------------
    public static final double DON_GIA_PHONG_THUONG = 500_000.0; // 500.000 VNĐ/đêm (dùng khi phòng chưa set giá)
    public static final double THUE_SUAT = 0.10; // 10% thuế VAT

    // ---------------------------------------------------------------
    // Thuộc tính liên kết & định danh
    // ---------------------------------------------------------------
    private String maHoaDon; // Mã hóa đơn (VD: HD-001)
    private String ngayLapHoaDon; // Ngày lập hóa đơn (d/M/yyyy)
    private boolean daThanhToan; // Trạng thái: true (Đã thanh toán) / false (Chưa thanh toán)
    private PhieuDatPhong phieu; // Phiếu đặt phòng liên kết (chứa khách, phòng, ngày lưu trú)
    private ChiTietHoaDon chiTiet; // Chi tiết các dịch vụ phụ trợ đã dùng (Module 3 + 4)

    // ---------------------------------------------------------------
    // Thuộc tính tài chính
    // ---------------------------------------------------------------
    private double donGiaPhong; // Đơn giá phòng / đêm
    private long soNgayLuuTru; // Số đêm lưu trú thực tế
    private double tienPhong; // Tiền phòng trước thuế = donGiaPhong × soNgayLuuTru
    private double tienDichVu; // Tổng tiền dịch vụ phát sinh từ ChiTietHoaDon
    private double giamGia; // Số tiền chiết khấu / giảm giá
    private double thueVAT; // Tiền thuế VAT (10%)
    private double tongTienPhong; // TỔNG TIỀN PHẢI THANH TOÁN (sau thuế, sau giảm giá)

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Constructor đầy đủ với mã hóa đơn và ngày lập.
     */
    public HoaDon(String maHoaDon, String ngayLapHoaDon, PhieuDatPhong phieu, ChiTietHoaDon chiTiet, double giamGia) {
        this.maHoaDon = (maHoaDon != null && !maHoaDon.trim().isEmpty()) ? maHoaDon.trim() : null;
        this.ngayLapHoaDon = (ngayLapHoaDon != null && !ngayLapHoaDon.trim().isEmpty()) ? ngayLapHoaDon.trim() : null;
        this.phieu = phieu;
        this.chiTiet = chiTiet;
        this.giamGia = (giamGia >= 0) ? giamGia : 0.0;
        this.daThanhToan = false;
        capNhatHoaDon();
    }

    /**
     * Constructor kết hợp trực tiếp PhieuDatPhong (tiền phòng) và ChiTietHoaDon
     * (tiền dịch vụ).
     *
     * @param phieu   Phiếu đặt phòng chứa thông tin phòng và thời gian lưu trú
     * @param chiTiet Chi tiết hóa đơn chứa danh sách dịch vụ đã dùng
     * @param giamGia Số tiền giảm giá (nhập 0 nếu không có)
     */
    public HoaDon(PhieuDatPhong phieu, ChiTietHoaDon chiTiet, double giamGia) {
        this(null, null, phieu, chiTiet, giamGia);
    }

    /**
     * Constructor kết hợp PhieuDatPhong và ChiTietHoaDon không có giảm giá.
     */
    public HoaDon(PhieuDatPhong phieu, ChiTietHoaDon chiTiet) {
        this(phieu, chiTiet, 0.0);
    }

    /**
     * Constructor tương thích ngược với code cũ truyền tiền dịch vụ dạng số thực.
     *
     * @param tienDichVu Tổng tiền dịch vụ đã sử dụng
     * @param giamGia    Số tiền giảm giá (0 nếu không có)
     * @param phieu      Phiếu đặt phòng của khách
     */
    public HoaDon(double tienDichVu, double giamGia, PhieuDatPhong phieu) {
        this.phieu = phieu;
        this.chiTiet = null;
        this.tienDichVu = (tienDichVu >= 0) ? tienDichVu : 0.0;
        this.giamGia = (giamGia >= 0) ? giamGia : 0.0;
        this.daThanhToan = false;
        capNhatHoaDon();
    }

    /**
     * Constructor mặc định.
     */
    public HoaDon() {
        this.phieu = null;
        this.chiTiet = null;
        this.maHoaDon = "HD-" + (System.currentTimeMillis() % 100000);
        this.ngayLapHoaDon = null;
        this.daThanhToan = false;
        this.donGiaPhong = 0.0;
        this.soNgayLuuTru = 0;
        this.tienPhong = 0.0;
        this.tienDichVu = 0.0;
        this.giamGia = 0.0;
        this.thueVAT = 0.0;
        this.tongTienPhong = 0.0;
    }

    // ---------------------------------------------------------------
    // Phương thức tính toán và cập nhật chi phí
    // ---------------------------------------------------------------

    /**
     * Đồng bộ tính toán toàn bộ chi phí:
     * 1. Số đêm lưu trú từ PhieuDatPhong
     * 2. Đơn giá phòng từ đối tượng Phong (Module 2)
     * 3. Tiền phòng = Đơn giá phòng × Số đêm
     * 4. Tiền dịch vụ từ ChiTietHoaDon (Module 3)
     * 5. Thuế VAT 10% trên (Tiền phòng + Tiền dịch vụ - Giảm giá)
     * 6. Tổng cộng tiền thanh toán
     */
    public void capNhatHoaDon() {
        // 1. Tính số đêm lưu trú
        this.soNgayLuuTru = tinhNgayLuuTru();

        // 2. Tính đơn giá phòng 1 đêm
        this.donGiaPhong = tinhDonGiaPhong();

        // 3. Tính tiền phòng
        this.tienPhong = this.donGiaPhong * this.soNgayLuuTru;

        // 4. Lấy tiền dịch vụ nếu có đối tượng ChiTietHoaDon
        if (this.chiTiet != null) {
            this.tienDichVu = this.chiTiet.getTongTienPhuThu();
        }

        // 5. Tính tiền trước thuế sau giảm giá
        double tienTruocThue = this.tienPhong + this.tienDichVu - this.giamGia;
        if (tienTruocThue < 0) {
            tienTruocThue = 0.0;
        }

        // 6. Tính thuế VAT và tổng thanh toán
        this.thueVAT = tienTruocThue * THUE_SUAT;
        this.tongTienPhong = tienTruocThue + this.thueVAT;

        // 7. Định danh mã hóa đơn và ngày lập tự động nếu chưa có
        if (this.maHoaDon == null || this.maHoaDon.trim().isEmpty()) {
            this.maHoaDon = "HD" + (phieu != null && phieu.getMaPhieu() != null ? ("-" + phieu.getMaPhieu())
                    : ("-" + (System.currentTimeMillis() % 100000)));
        }
        if (this.ngayLapHoaDon == null || this.ngayLapHoaDon.trim().isEmpty()) {
            this.ngayLapHoaDon = (phieu != null && phieu.getNgayTraStr() != null && !phieu.getNgayTraStr().isEmpty())
                    ? phieu.getNgayTraStr()
                    : "07/11/2026";
        }
    }

    /**
     * Tính số đêm lưu trú từ ngày nhận và ngày trả phòng.
     */
    public long tinhNgayLuuTru() {
        if (phieu == null)
            return 1;
        return phieu.tinhSoNgayLuuTru();
    }

    /**
     * Lấy đơn giá phòng 1 đêm từ đối tượng Phong (Module 2).
     */
    public double tinhDonGiaPhong() {
        if (phieu == null)
            return DON_GIA_PHONG_THUONG;
        return phieu.getGiaPhongMotDem();
    }

    /**
     * Tính tiền phòng trước thuế.
     */
    public double tinhTienPhong() {
        return this.tienPhong;
    }

    /**
     * Tính tổng tiền phải trả (bao gồm tiền phòng, dịch vụ, giảm giá và VAT).
     */
    public double tinhTongTienPhong() {
        return this.tongTienPhong;
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public void setNgayLapHoaDon(String ngayLapHoaDon) {
        this.ngayLapHoaDon = ngayLapHoaDon;
    }

    public boolean isDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public PhieuDatPhong getPhieu() {
        return phieu;
    }

    public void setPhieu(PhieuDatPhong phieu) {
        this.phieu = phieu;
        capNhatHoaDon();
    }

    public ChiTietHoaDon getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(ChiTietHoaDon chiTiet) {
        this.chiTiet = chiTiet;
        capNhatHoaDon();
    }

    public double getDonGiaPhong() {
        return donGiaPhong;
    }

    public long getSoNgayLuuTru() {
        return soNgayLuuTru;
    }

    public double getTienPhong() {
        return tienPhong;
    }

    public double getTienDichVu() {
        return tienDichVu;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = (giamGia >= 0) ? giamGia : 0.0;
        capNhatHoaDon();
    }

    public double getThueVAT() {
        return thueVAT;
    }

    public double getTongTienPhong() {
        return tongTienPhong;
    }

    public double getTongThanhToan() {
        return tongTienPhong;
    }

    // ---------------------------------------------------------------
    // Phương thức Nhập / Xuất (INhapXuat)
    // ---------------------------------------------------------------

    /**
     * Nhập cập nhật thông tin giảm giá hoặc chi phí hóa đơn từ bàn phím.
     */
    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("CAP NHAT THONG TIN HOA DON");
        System.out.printf("Tien phong tam tinh (tu PhieuDatPhong) : %,.0f VND%n", tienPhong);
        System.out.printf("Tien dich vu (tu ChiTietHoaDon)         : %,.0f VND%n", tienDichVu);

        this.giamGia = KiemTraLoi.nhapSoThucDuong(sc, "Nhap so tien giam gia (0 neu khong co): ");
        capNhatHoaDon();
        ThongBao.inThanhCong("Da cap nhat hoa don thanh cong!");
    }

    public void xuatHoaDon() {
        xuat();
    }

    /**
     * In hóa đơn chi tiết, rõ ràng và đầy đủ:
     * - Header: Mã hóa đơn, Ngày lập, Trạng thái thanh toán
     * - [1] Khách hàng & Thông tin liên hệ
     * - [2] Bóc tách chi phí tiền phòng (PhieuDatPhong)
     * - [3] Bóc tách chi phí tiền dịch vụ (ChiTietHoaDon từ Module 3)
     * - [4] Bảng tổng hợp thanh toán cuối cùng
     */
    @Override
    public void xuat() {
        capNhatHoaDon(); // Đảm bảo số liệu luôn mới nhất

        ThongBao.inTieuDe("HOA DON THANH TOAN KHACH SAN");
        System.out.printf("  Ma hoa don : %-20s  Ngay lap   : %s%n",
                (maHoaDon != null ? maHoaDon : "N/A"),
                (ngayLapHoaDon != null ? ngayLapHoaDon : "N/A"));
        System.out.printf("  Trang thai : [%s]%n",
                (daThanhToan ? "DA THANH TOAN" : "CHUA THANH TOAN"));
        System.out.println(
                "--------------------------------------------------------------------------------------------------");

        // 1. THÔNG TIN KHÁCH HÀNG
        System.out.println("[1] THONG TIN KHACH HANG:");
        if (phieu != null && phieu.getKhachHang() != null) {
            KHACHHANG kh = phieu.getKhachHang();
            System.out.printf("  - Ho va ten   : %s%n", (kh.gethoten() != null ? kh.gethoten() : "N/A"));
            System.out.printf("  - So CCCD     : %s%n", (kh.getCCCD() != null ? kh.getCCCD() : "N/A"));
            System.out.printf("  - Loai khach  : %s%n", (kh.getLoaikhach() != null ? kh.getLoaikhach() : "Thuong"));
        } else {
            System.out.println("  (Chua co thong tin khach hang)");
        }

        // 2. THÔNG TIN PHÒNG & THỜI GIAN LƯU TRÚ (PhieuDatPhong)
        System.out.println("\n[2] CHI TIET TIEN PHONG (PhieuDatPhong):");
        if (phieu != null) {
            String tenLoaiPhong = "Phong Standard";
            if (phieu.getPhong() instanceof PhongVIP) {
                tenLoaiPhong = "Phong VIP (" + ((PhongVIP) phieu.getPhong()).getLoaiView() + ")";
            }

            System.out.printf("  - Ma phong          : %s%n", phieu.getMaPhong());
            System.out.printf("  - Loai phong        : %s%n", tenLoaiPhong);
            System.out.printf("  - Ngay nhan phong   : %s%n", phieu.getNgayNhanStr());
            System.out.printf("  - Ngay tra phong    : %s%n", phieu.getNgayTraStr());
            System.out.printf("  - So dem luu tru    : %d dem%n", soNgayLuuTru);
            System.out.printf("  - Don gia phong/dem : %,.0f VND%n", donGiaPhong);
            System.out.printf("  ==> TIEN PHONG      : %,.0f VND x %d dem = %,.0f VND%n",
                    donGiaPhong, soNgayLuuTru, tienPhong);
        } else {
            System.out.printf("  - Tien phong        : %,.0f VND%n", tienPhong);
        }

        // 3. THÔNG TIN DỊCH VỤ PHỤ THU (ChiTietHoaDon)
        System.out.println("\n[3] CHI TIET DICH VU PHU TRO (ChiTietHoaDon):");
        if (chiTiet != null && chiTiet.getSoLuongDichVu() > 0) {
            System.out.println(
                    "  ------------------------------------------------------------------------------------------------");
            System.out.printf("  | %-6s | %-26s | %-16s | %-12s | %-8s | %-15s |%n",
                    "MA DV", "TEN DICH VU", "PHAN LOAI", "DON GIA", "SO LUONG", "THANH TIEN");
            System.out.println(
                    "  ------------------------------------------------------------------------------------------------");
            for (ChiTietHoaDon.ChiTietDichVu item : chiTiet.getDanhSachChiTiet()) {
                DichVu dv = item.getDichVu();
                System.out.printf("  | %-6s | %-26s | %-16s | %,12.0f | %-8d | %,15.0f |%n",
                        dv.getMaDV(), dv.getTenDV(), item.getPhanLoai(),
                        dv.getDonGia(), item.getSoLuong(), item.tinhThanhTien());
            }
            System.out.println(
                    "  ------------------------------------------------------------------------------------------------");
            System.out.printf("  ==> TONG TIEN DICH VU : %,.0f VND%n", tienDichVu);
        } else if (tienDichVu > 0) {
            System.out.printf("  ==> TONG TIEN DICH VU : %,.0f VND%n", tienDichVu);
        } else {
            System.out.println("  (Khach hang khong su dung dich vu phu tro: 0 VND)");
        }

        // 4. BẢNG TỔNG HỢP THANH TOÁN
        System.out.println("\n[4] TONG HOP THANH TOAN:");
        System.out.println(
                "==================================================================================================");
        System.out.printf("  1. Tien phong                    : %,15.0f VND%n", tienPhong);
        System.out.printf("  2. Tien dich vu phu thu          : %,15.0f VND%n", tienDichVu);
        System.out.printf("  3. Giam gia / Chiet khau         : -%,14.0f VND%n", giamGia);
        System.out.printf("  4. Thue VAT (10%%)                : %,15.0f VND%n", thueVAT);
        System.out.println(
                "--------------------------------------------------------------------------------------------------");
        System.out.printf("  TONG CONG PHAI THANH TOAN        : %,15.0f VND%n", tongTienPhong);
        System.out.println(
                "==================================================================================================");
    }

}
