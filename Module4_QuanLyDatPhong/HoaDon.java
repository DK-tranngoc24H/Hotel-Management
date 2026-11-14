import java.time.LocalDate;
import java.util.Scanner;
import BT_quanLyKhachSan.DichVu;

/**
 * Lớp HoaDon – tính toán và hiển thị hóa đơn thanh toán toàn diện của khách hàng.
 * Đã đồng bộ tên class và tên biến với Module 1 (KhachHang), Module 2 (Phong, PhongStandard, PhongVIP),
 * Module 3 (DichVu) và sử dụng LocalDate cùng Module4_Exception.
 */
public class HoaDon implements INhapXuat {

    public static final double DON_GIA_PHONG_THUONG = 500_000.0;
    public static final double THUE_SUAT = 0.10; // 10% thuế VAT

    private String maHoaDon;
    private LocalDate ngayLapHoaDon;
    private boolean daThanhToan;
    private PhieuDatPhong phieu;
    private ChiTietHoaDon chiTiet;

    private double donGiaPhong;
    private long soNgayLuuTru;
    private double tienPhong;
    private double tienDichVu;
    private double giamGia;
    private double thueVAT;
    private double tongTienPhong;

    public HoaDon(String maHoaDon, String ngayLapHoaDon, PhieuDatPhong phieu, ChiTietHoaDon chiTiet, double giamGia) {
        if (giamGia < 0) {
            throw new Module4_Exception.HoaDonKhongHopLeException("Số tiền giảm giá không được âm (" + giamGia + ").");
        }
        this.maHoaDon = (maHoaDon != null && !maHoaDon.trim().isEmpty()) ? maHoaDon.trim() : null;
        this.ngayLapHoaDon = (ngayLapHoaDon != null && !ngayLapHoaDon.trim().isEmpty())
                ? PhieuDatPhong.phanTichNgay(ngayLapHoaDon)
                : null;
        this.phieu = phieu;
        this.chiTiet = chiTiet;
        this.giamGia = giamGia;
        this.daThanhToan = false;
        capNhatHoaDon();
    }

    public HoaDon(PhieuDatPhong phieu, ChiTietHoaDon chiTiet, double giamGia) {
        this(null, null, phieu, chiTiet, giamGia);
    }

    public HoaDon(PhieuDatPhong phieu, ChiTietHoaDon chiTiet) {
        this(phieu, chiTiet, 0.0);
    }

    public HoaDon(double tienDichVu, double giamGia, PhieuDatPhong phieu) {
        if (tienDichVu < 0 || giamGia < 0) {
            throw new Module4_Exception.HoaDonKhongHopLeException("Tiền dịch vụ và giảm giá không được âm.");
        }
        this.phieu = phieu;
        this.chiTiet = null;
        this.tienDichVu = tienDichVu;
        this.giamGia = giamGia;
        this.daThanhToan = false;
        capNhatHoaDon();
    }

    public HoaDon() {
        this.phieu = null;
        this.chiTiet = null;
        this.maHoaDon = "HD-" + (System.currentTimeMillis() % 100000);
        this.ngayLapHoaDon = LocalDate.now();
        this.daThanhToan = false;
    }

    public void capNhatHoaDon() {
        this.soNgayLuuTru = tinhNgayLuuTru();
        this.donGiaPhong = tinhDonGiaPhong();
        this.tienPhong = this.donGiaPhong * this.soNgayLuuTru;

        if (this.chiTiet != null) {
            this.tienDichVu = this.chiTiet.getTongTienPhuThu();
        }

        double tienTruocThue = Math.max(0.0, this.tienPhong + this.tienDichVu - this.giamGia);
        this.thueVAT = tienTruocThue * THUE_SUAT;
        this.tongTienPhong = tienTruocThue + this.thueVAT;

        if (this.maHoaDon == null || this.maHoaDon.trim().isEmpty()) {
            this.maHoaDon = "HD-" + (phieu != null && phieu.getMaPhieu() != null ? phieu.getMaPhieu()
                    : (System.currentTimeMillis() % 100000));
        }
        if (this.ngayLapHoaDon == null) {
            this.ngayLapHoaDon = (phieu != null && phieu.getNgayTraLocalDate() != null)
                    ? phieu.getNgayTraLocalDate()
                    : LocalDate.now();
        }
    }

    public long tinhNgayLuuTru() {
        return (phieu != null) ? phieu.tinhSoNgayLuuTru() : 1;
    }

    public double tinhDonGiaPhong() {
        return (phieu != null) ? phieu.getGiaPhongMotDem() : DON_GIA_PHONG_THUONG;
    }

    public double tinhTienPhong() {
        return this.tienPhong;
    }

    public double tinhTongTienPhong() {
        return this.tongTienPhong;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            throw new Module4_Exception.HoaDonKhongHopLeException("Mã hóa đơn không được để trống.");
        }
        this.maHoaDon = maHoaDon.trim();
    }

    public LocalDate getNgayLapLocalDate() {
        return ngayLapHoaDon;
    }

    public String getNgayLapHoaDon() {
        return PhieuDatPhong.dinhDangNgay(ngayLapHoaDon);
    }

    public void setNgayLapHoaDon(String ngayLapHoaDon) {
        this.ngayLapHoaDon = PhieuDatPhong.phanTichNgay(ngayLapHoaDon);
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
        if (phieu == null) {
            throw new Module4_Exception.HoaDonKhongHopLeException("Phiếu đặt phòng gắn với hóa đơn không được null.");
        }
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
        if (giamGia < 0) {
            throw new Module4_Exception.HoaDonKhongHopLeException("Số tiền giảm giá không được âm.");
        }
        double tongGoc = this.tienPhong + this.tienDichVu;
        if (tongGoc > 0 && giamGia > tongGoc) {
            throw new Module4_Exception.HoaDonKhongHopLeException(
                    "Số tiền giảm giá (" + String.format("%,.0f", giamGia) + ") vượt quá tổng tiền trước thuế (" + String.format("%,.0f", tongGoc) + ").");
        }
        this.giamGia = giamGia;
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

    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("CAP NHAT THONG TIN HOA DON");
        System.out.printf("Tien phong tam tinh (tu PhieuDatPhong) : %,.0f VND%n", tienPhong);
        System.out.printf("Tien dich vu (tu ChiTietHoaDon)         : %,.0f VND%n", tienDichVu);

        while (true) {
            try {
                double nhapGiam = KiemTraLoi.nhapSoThucDuong(sc, "Nhap so tien giam gia (0 neu khong co): ");
                setGiamGia(nhapGiam);
                break;
            } catch (Module4_Exception e) {
                ThongBao.inLoi(e.getMessage());
            }
        }
        ThongBao.inThanhCong("Da cap nhat hoa don thanh cong!");
    }

    public void xuatHoaDon() {
        xuat();
    }

    @Override
    public void xuat() {
        capNhatHoaDon();

        ThongBao.inTieuDe("HOA DON THANH TOAN KHACH SAN");
        System.out.printf("  Ma hoa don : %-20s  Ngay lap   : %s%n",
                (maHoaDon != null ? maHoaDon : "N/A"),
                (ngayLapHoaDon != null ? getNgayLapHoaDon() : "N/A"));
        System.out.printf("  Trang thai : [%s]%n",
                (daThanhToan ? "DA THANH TOAN" : "CHUA THANH TOAN"));
        System.out.println("--------------------------------------------------------------------------------------------------");

        System.out.println("[1] THONG TIN KHACH HANG:");
        if (phieu != null && phieu.getKhachHang() != null) {
            KhachHang khachHang = phieu.getKhachHang();
            System.out.printf("  - Ho va ten   : %s%n", (khachHang.gethoten() != null ? khachHang.gethoten() : "N/A"));
            System.out.printf("  - So CCCD     : %s%n", (khachHang.getCCCD() != null ? khachHang.getCCCD() : "N/A"));
            System.out.printf("  - Loai khach  : %s%n", (khachHang.getLoaikhach() != null ? khachHang.getLoaikhach() : "Thuong"));
        } else {
            System.out.println("  (Chua co thong tin khach hang)");
        }

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

        System.out.println("\n[3] CHI TIET DICH VU PHU TRO (ChiTietHoaDon):");
        if (chiTiet != null && chiTiet.getSoLuongDichVu() > 0) {
            System.out.println("  ------------------------------------------------------------------------------------------------");
            System.out.printf("  | %-6s | %-26s | %-16s | %-12s | %-8s | %-15s |%n",
                    "MA DV", "TEN DICH VU", "PHAN LOAI", "DON GIA", "SO LUONG", "THANH TIEN");
            System.out.println("  ------------------------------------------------------------------------------------------------");
            for (ChiTietHoaDon.ChiTietDichVu item : chiTiet.getDanhSachChiTiet()) {
                DichVu dv = item.getDichVu();
                System.out.printf("  | %-6s | %-26s | %-16s | %,12.0f | %-8d | %,15.0f |%n",
                        dv.getMaDV(), dv.getTenDV(), item.getPhanLoai(),
                        dv.getDonGia(), item.getSoLuong(), item.tinhThanhTien());
            }
            System.out.println("  ------------------------------------------------------------------------------------------------");
            System.out.printf("  ==> TONG TIEN DICH VU : %,.0f VND%n", tienDichVu);
        } else if (tienDichVu > 0) {
            System.out.printf("  ==> TONG TIEN DICH VU : %,.0f VND%n", tienDichVu);
        } else {
            System.out.println("  (Khach hang khong su dung dich vu phu tro: 0 VND)");
        }

        System.out.println("\n[4] TONG HOP THANH TOAN:");
        System.out.println("==================================================================================================");
        System.out.printf("  1. Tien phong                    : %,15.0f VND%n", tienPhong);
        System.out.printf("  2. Tien dich vu phu thu          : %,15.0f VND%n", tienDichVu);
        System.out.printf("  3. Giam gia / Chiet khau         : -%,14.0f VND%n", giamGia);
        System.out.printf("  4. Thue VAT (10%%)                : %,15.0f VND%n", thueVAT);
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("  TONG CONG PHAI THANH TOAN        : %,15.0f VND%n", tongTienPhong);
        System.out.println("==================================================================================================");
    }
}
