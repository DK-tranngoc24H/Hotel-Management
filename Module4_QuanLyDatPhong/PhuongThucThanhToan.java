import java.util.Scanner;

/**
 * Lớp PhuongThucThanhToan – quản lý hình thức thanh toán của khách hàng.
 * Tích hợp toàn diện với:
 * - HoaDon (Module 4): Tính toán tổng tiền phòng và tiền dịch vụ
 * - PhieuDatPhong & Phong: Tự động cập nhật trạng thái phòng khi thanh toán /
 * trả phòng
 * - ClassDungChung: INhapXuat, KiemTraLoi, ThongBao
 * Hỗ trợ 4 hình thức thanh toán: Tiền mặt, Chuyển khoản, Thẻ tín dụng, Ví điện
 * tử.
 */
public class PhuongThucThanhToan implements INhapXuat {

    // ---------------------------------------------------------------
    // Enum – Các loại hình thức thanh toán
    // ---------------------------------------------------------------

    /**
     * Các hình thức thanh toán được hỗ trợ.
     * Dùng enum thay vì chuỗi rời rạc để đảm bảo an toàn kiểu dữ liệu.
     */
    public enum LoaiThanhToan {
        TIEN_MAT("Tien mat"),
        CHUYEN_KHOAN("Chuyen khoan ngan hang"),
        THE_TIN_DUNG("The tin dung / Ghi no (POS)"),
        VI_DIEN_TU("Vi dien tu (MoMo / ZaloPay / VNPAY)");

        private final String tenHienThi;

        LoaiThanhToan(String tenHienThi) {
            this.tenHienThi = tenHienThi;
        }

        public String getTenHienThi() {
            return tenHienThi;
        }
    }

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    private LoaiThanhToan loaiThanhToan; // Hình thức thanh toán đã chọn
    private HoaDon hoaDon; // Hóa đơn cần thanh toán
    private double tienKhachDua; // Tiền khách đưa (nếu dùng tiền mặt)
    private double tienThoiLai; // Tiền thối lại cho khách
    private String maGiaoDich; // Mã giao dịch thanh toán
    private String thongTinNganHangHoacThe; // Tên ngân hàng / Loại thẻ / Ví điện tử
    private String trangThaiThanhToan; // "Chua thanh toan", "Da thanh toan"

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Tạo đối tượng thanh toán với hình thức đã biết và hóa đơn.
     *
     * @param loaiThanhToan Hình thức thanh toán (enum LoaiThanhToan)
     * @param hoaDon        Hóa đơn cần thanh toán
     */
    public PhuongThucThanhToan(LoaiThanhToan loaiThanhToan, HoaDon hoaDon) {
        this.loaiThanhToan = loaiThanhToan;
        this.hoaDon = hoaDon;
        this.tienKhachDua = (hoaDon != null) ? hoaDon.getTongTienPhong() : 0.0;
        this.tienThoiLai = 0.0;
        this.maGiaoDich = "GD" + (System.currentTimeMillis() % 1000000);
        this.thongTinNganHangHoacThe = "Mac dinh";
        this.trangThaiThanhToan = "Da thanh toan";
        if (this.hoaDon != null) {
            this.hoaDon.setDaThanhToan(true);
        }
    }

    /**
     * Tạo đối tượng thanh toán từ hóa đơn (người dùng sẽ chọn hình thức sau qua
     * nhap()).
     *
     * @param hoaDon Hóa đơn cần thanh toán
     */
    public PhuongThucThanhToan(HoaDon hoaDon) {
        this.loaiThanhToan = null;
        this.hoaDon = hoaDon;
        this.tienKhachDua = 0.0;
        this.tienThoiLai = 0.0;
        this.maGiaoDich = null;
        this.thongTinNganHangHoacThe = "";
        this.trangThaiThanhToan = "Chua thanh toan";
    }

    /**
     * Constructor mặc định.
     */
    public PhuongThucThanhToan() {
        this(null);
    }

    // ---------------------------------------------------------------
    // Nhập hình thức thanh toán & chi tiết giao dịch (INhapXuat)
    // ---------------------------------------------------------------

    /**
     * Chọn hình thức thanh toán từ bàn phím và xử lý tương ứng.
     */
    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("CHON HINH THUC VA THUC HIEN THANH TOAN");

        double tongTien = (hoaDon != null) ? hoaDon.getTongTienPhong() : 0.0;
        System.out.printf("TONG SO TIEN CAN THANH TOAN: %,.0f VND%n%n", tongTien);

        System.out.println("Cac hinh thuc thanh toan duoc chap nhan:");
        System.out.println("  1. Tien mat");
        System.out.println("  2. Chuyen khoan ngan hang (Vietcombank, MB, BIDV...)");
        System.out.println("  3. The tin dung / Ghi no (Visa, MasterCard)");
        System.out.println("  4. Vi dien tu (MoMo, ZaloPay, VNPAY)");
        int chon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Moi ban chon hinh thuc thanh toan (1 - 4): ", 1, 4);

        switch (chon) {
            case 2:
                loaiThanhToan = LoaiThanhToan.CHUYEN_KHOAN;
                thongTinNganHangHoacThe = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten ngan hang chuyen khoan: ");
                maGiaoDich = "CK" + String.format("%06d", (System.currentTimeMillis() % 1000000));
                tienKhachDua = tongTien;
                tienThoiLai = 0.0;
                ThongBao.inThanhCong("Xac nhan da nhan du tien chuyen khoan. Ma GD: " + maGiaoDich);
                break;

            case 3:
                loaiThanhToan = LoaiThanhToan.THE_TIN_DUNG;
                thongTinNganHangHoacThe = KiemTraLoi.nhapChuoiKhongRong(sc,
                        "Nhap loai the va ten chu the (VD: Visa - Nguyen Van A): ");
                maGiaoDich = "POS" + String.format("%06d", (System.currentTimeMillis() % 1000000));
                tienKhachDua = tongTien;
                tienThoiLai = 0.0;
                ThongBao.inThanhCong("Quet the thanh cong qua POS. Ma GD: " + maGiaoDich);
                break;

            case 4:
                loaiThanhToan = LoaiThanhToan.VI_DIEN_TU;
                thongTinNganHangHoacThe = KiemTraLoi.nhapChuoiKhongRong(sc,
                        "Nhap ten vi dien tu (MoMo / ZaloPay / VNPAY): ");
                maGiaoDich = "EW" + String.format("%06d", (System.currentTimeMillis() % 1000000));
                tienKhachDua = tongTien;
                tienThoiLai = 0.0;
                ThongBao.inThanhCong("Quet ma QR thanh toan vi dien tu thanh cong. Ma GD: " + maGiaoDich);
                break;

            case 1:
            default:
                loaiThanhToan = LoaiThanhToan.TIEN_MAT;
                thongTinNganHangHoacThe = "Thu tien mat tai quay";
                maGiaoDich = "TM" + String.format("%06d", (System.currentTimeMillis() % 1000000));

                if (tongTien > 0) {
                    while (true) {
                        double dua = KiemTraLoi.nhapSoThucDuong(sc, "Nhap so tien khach dua (VND): ");
                        if (dua >= tongTien) {
                            tienKhachDua = dua;
                            tienThoiLai = dua - tongTien;
                            break;
                        } else {
                            ThongBao.inLoi("So tien khach dua chua du (thieu " +
                                    String.format("%,.0f VND", (tongTien - dua)) + ")! Vui long nhap lai.");
                        }
                    }
                    if (tienThoiLai > 0) {
                        ThongBao.inThanhCong(String.format("Tien thoi lai cho khach: %,.0f VND", tienThoiLai));
                    }
                } else {
                    tienKhachDua = 0.0;
                    tienThoiLai = 0.0;
                }
                break;
        }

        this.trangThaiThanhToan = "Da thanh toan";
        if (this.hoaDon != null) {
            this.hoaDon.setDaThanhToan(true);
            if (this.hoaDon.getPhieu() != null && this.hoaDon.getPhieu().getPhong() != null) {
                this.hoaDon.getPhieu().getPhong().setTrangThai("Trong");
            }
        }
        ThongBao.inThanhCong("GIAO DICH THANH TOAN HOAN TAT!");
    }

    // ---------------------------------------------------------------
    // Thực hiện & Xuất biên lai thanh toán
    // ---------------------------------------------------------------

    /**
     * Thực hiện thanh toán và in hóa đơn cùng biên lai.
     */
    public void thucHienThanhToan() {
        if (loaiThanhToan == null) {
            loaiThanhToan = LoaiThanhToan.TIEN_MAT;
        }
        if (maGiaoDich == null || maGiaoDich.isEmpty()) {
            maGiaoDich = "GD" + String.format("%06d", (System.currentTimeMillis() % 1000000));
        }
        if (hoaDon != null && tienKhachDua == 0.0) {
            tienKhachDua = hoaDon.getTongTienPhong();
            tienThoiLai = 0.0;
        }
        this.trangThaiThanhToan = "Da thanh toan";
        if (this.hoaDon != null) {
            this.hoaDon.setDaThanhToan(true);
            if (this.hoaDon.getPhieu() != null && this.hoaDon.getPhieu().getPhong() != null) {
                this.hoaDon.getPhieu().getPhong().setTrangThai("Trong");
            }
        }
        xuat();
    }

    @Override
    public void xuat() {
        // 1. In toàn bộ hóa đơn chi tiết kết hợp tiền phòng & tiền dịch vụ
        if (hoaDon != null) {
            hoaDon.xuat();
        } else {
            ThongBao.inCanhBao("Chua co thong tin hoa don de thanh toan.");
        }

        if (loaiThanhToan == null) {
            ThongBao.inCanhBao("Chua chon hinh thuc thanh toan.");
            return;
        }

        // 2. In biên lai giao dịch thanh toán
        ThongBao.inTieuDe("BIEN LAI XAC NHAN THANH TOAN");
        double tongTien = (hoaDon != null) ? hoaDon.getTongTienPhong() : 0.0;

        System.out.printf("  - Hinh thuc thanh toan    : %s%n", loaiThanhToan.getTenHienThi());
        System.out.printf("  - Ma giao dich (Ref)      : %s%n", (maGiaoDich != null ? maGiaoDich : "N/A"));
        if (thongTinNganHangHoacThe != null && !thongTinNganHangHoacThe.isEmpty()) {
            System.out.printf("  - Thong tin thanh toan    : %s%n", thongTinNganHangHoacThe);
        }
        System.out.printf("  - Tong tien can thanh toan: %,15.0f VND%n", tongTien);
        System.out.printf("  - So tien khach da tra    : %,15.0f VND%n", tienKhachDua);
        if (tienThoiLai > 0) {
            System.out.printf("  - Tien thoi lai cho khach : %,15.0f VND%n", tienThoiLai);
        }
        System.out.printf("  - Trang thai giao dich    : %s%n",
                ("Da thanh toan".equalsIgnoreCase(trangThaiThanhToan) ? "THANH CONG (PAID)" : "CHUA HOAN TAT"));
        System.out.println(
                "==================================================================================================");
        ThongBao.inThanhCong("Cam on quy khach da su dung dich vu cua khach san!");
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------
    public LoaiThanhToan getLoaiThanhToan() {
        return loaiThanhToan;
    }

    public void setLoaiThanhToan(LoaiThanhToan loai) {
        this.loaiThanhToan = loai;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public double getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(double tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public double getTienThoiLai() {
        return tienThoiLai;
    }

    public void setTienThoiLai(double tienThoiLai) {
        this.tienThoiLai = tienThoiLai;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getThongTinNganHangHoacThe() {
        return thongTinNganHangHoacThe;
    }

    public void setThongTinNganHangHoacThe(String thongTin) {
        this.thongTinNganHangHoacThe = thongTin;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThai) {
        this.trangThaiThanhToan = trangThai;
    }

    public boolean isDaThanhToan() {
        return "Da thanh toan".equalsIgnoreCase(this.trangThaiThanhToan);
    }

}
