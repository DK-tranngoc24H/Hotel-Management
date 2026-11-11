import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Lớp QuanLyDatPhong – Điều phối trung tâm cho Module 4 (Quản lý đặt phòng).
 * Quản lý toàn bộ vòng đời đặt phòng khách sạn:
 * 1. Đặt phòng mới (tạo PhieuDatPhong)
 * 2. Gọi thêm dịch vụ trong quá trình lưu trú (tích hợp Module 3 qua
 * ChiTietHoaDon)
 * 3. Lập và xem trước hóa đơn tổng hợp (tiền phòng + tiền dịch vụ + thuế VAT)
 * 4. Thanh toán & Trả phòng (PhuongThucThanhToan, giải phóng phòng thành
 * 'Trong')
 * 5. Báo cáo & Thống kê doanh thu
 */
public class QuanLyDatPhong {

    private final ArrayList<PhieuDatPhong> dsPhieu;
    private final ArrayList<HoaDon> dsHoaDon;
    private final Map<String, ChiTietHoaDon> mapChiTietDichVu; // Map maPhieu -> ChiTietHoaDon

    public QuanLyDatPhong() {
        this.dsPhieu = new ArrayList<>();
        this.dsHoaDon = new ArrayList<>();
        this.mapChiTietDichVu = new HashMap<>();
        napDuLieuSanCo();
    }

    /**
     * Nạp dữ liệu mẫu ban đầu để sẵn sàng kiểm thử và vận hành ngay.
     */
    public void napDuLieuSanCo() {
        // Khách hàng mẫu
        KHACHHANG kh1 = new KHACHHANG("Nguyen Van An", "012345678901", "0901234567", "Ha Noi", "Thuong", null);
        KHACHHANG kh2 = new KHACHHANG("Tran Thi Bich", "079987654321", "0987654321", "TP Ho Chi Minh", "VIP", null);
        KHACHHANG kh3 = new KHACHHANG("Le Hoang Nam", "048123456789", "0912345678", "Da Nang", "Doanh nghiep", null);
        KHACHHANG kh4 = new KHACHHANG("Pham Minh Tuan", "036123456789", "0934567890", "Hai Phong", "Thuong", null);

        // Phòng mẫu
        PhongStandard p101 = new PhongStandard("P101", 500_000.0, "Da dat", true);
        PhongVIP p201 = new PhongVIP("P201", 1_200_000.0, "Trong", "Bien", 200_000.0);
        PhongStandard p102 = new PhongStandard("P102", 550_000.0, "Da dat", false);
        PhongVIP p202 = new PhongVIP("P202", 1_000_000.0, "Trong", "Thanh pho", 150_000.0);

        // Phiếu đặt phòng mẫu
        PhieuDatPhong pdp1 = new PhieuDatPhong("PDP01", "01/12/2026", "02/12/2026", "05/12/2026", kh1, p101);
        PhieuDatPhong pdp2 = new PhieuDatPhong("PDP02", "03/12/2026", "04/12/2026", "08/12/2026", kh2, p201);
        PhieuDatPhong pdp3 = new PhieuDatPhong("PDP03", "10/12/2026", "11/12/2026", "14/12/2026", kh3, p102);
        PhieuDatPhong pdp4 = new PhieuDatPhong("PDP04", "15/12/2026", "16/12/2026", "19/12/2026", kh4, p202);

        dsPhieu.add(pdp1);
        dsPhieu.add(pdp2);
        dsPhieu.add(pdp3);
        dsPhieu.add(pdp4);

        // Gắn chi tiết dịch vụ cho phiếu PDP01 (Phở bò + Cà phê)
        ChiTietHoaDon cthd1 = new ChiTietHoaDon(pdp1);
        cthd1.themDichVu("DA01", 2); // 2 Phở bò đặc biệt
        cthd1.themDichVu("NU01", 2); // 2 Cà phê sữa đá
        mapChiTietDichVu.put(pdp1.getMaPhieu(), cthd1);

        // Gắn chi tiết dịch vụ cho phiếu PDP02 (Spa + Thuê xe máy)
        ChiTietHoaDon cthd2 = new ChiTietHoaDon(pdp2);
        cthd2.themDichVu("SG01", 2); // 2 Massage body Thụy Điển
        cthd2.themDichVu("TX02", 1); // 1 Ngày thuê xe máy
        mapChiTietDichVu.put(pdp2.getMaPhieu(), cthd2);

        // Gắn chi tiết dịch vụ cho phiếu PDP03 (Cơm tấm + Nước ép cam)
        ChiTietHoaDon cthd3 = new ChiTietHoaDon(pdp3);
        cthd3.themDichVu("DA03", 2); // 2 Cơm tấm sườn bì chả
        cthd3.themDichVu("NU02", 2); // 2 Nước ép cam tươi
        mapChiTietDichVu.put(pdp3.getMaPhieu(), cthd3);

        // Gắn chi tiết dịch vụ cho phiếu PDP04 (Karaoke + Thuê ô tô tự lái)
        ChiTietHoaDon cthd4 = new ChiTietHoaDon(pdp4);
        cthd4.themDichVu("SG03", 2); // 2 Giờ Karaoke VIP
        cthd4.themDichVu("TX03", 1); // 1 Ngày thuê ô tô 4 chỗ tự lái
        mapChiTietDichVu.put(pdp4.getMaPhieu(), cthd4);

        // Tạo hóa đơn cho PDP01 (chưa thanh toán)
        HoaDon hd1 = new HoaDon("HD-PDP01", "05/12/2026", pdp1, cthd1, 50_000.0);
        dsHoaDon.add(hd1);

        // Tạo hóa đơn cho PDP02 đã thanh toán
        HoaDon hd2 = new HoaDon("HD-PDP02", "08/12/2026", pdp2, cthd2, 100_000.0);
        hd2.setDaThanhToan(true);
        if (pdp2.getPhong() != null) {
            pdp2.getPhong().setTrangThai("Trong");
        }
        dsHoaDon.add(hd2);

        // Tạo hóa đơn cho PDP03 (chưa thanh toán)
        HoaDon hd3 = new HoaDon("HD-PDP03", "14/12/2026", pdp3, cthd3, 0.0);
        dsHoaDon.add(hd3);

        // Tạo hóa đơn cho PDP04 đã thanh toán
        HoaDon hd4 = new HoaDon("HD-PDP04", "19/12/2026", pdp4, cthd4, 150_000.0);
        hd4.setDaThanhToan(true);
        if (pdp4.getPhong() != null) {
            pdp4.getPhong().setTrangThai("Trong");
        }
        dsHoaDon.add(hd4);
    }

    // ---------------------------------------------------------------
    // 1. Quản lý Đặt phòng (PhieuDatPhong)
    // ---------------------------------------------------------------

    public void themPhieuDatPhong(PhieuDatPhong phieu) {
        if (phieu != null) {
            dsPhieu.add(phieu);
            if (!mapChiTietDichVu.containsKey(phieu.getMaPhieu())) {
                mapChiTietDichVu.put(phieu.getMaPhieu(), new ChiTietHoaDon(phieu));
            }
        }
    }

    public void taoPhieuDatPhongMoi(Scanner sc) {
        PhieuDatPhong phieuMoi = new PhieuDatPhong();
        phieuMoi.nhap(sc);
        themPhieuDatPhong(phieuMoi);
        ThongBao.inThanhCong("Tao phieu dat phong thanh cong voi ma: " + phieuMoi.getMaPhieu());
    }

    public PhieuDatPhong timPhieuDatPhong(String maPhieu) {
        if (maPhieu == null)
            return null;
        for (PhieuDatPhong p : dsPhieu) {
            if (p.getMaPhieu() != null && p.getMaPhieu().equalsIgnoreCase(maPhieu.trim())) {
                return p;
            }
        }
        return null;
    }

    public PhieuDatPhong timPhieuTheoMaPhong(String maPhong) {
        if (maPhong == null)
            return null;
        for (PhieuDatPhong p : dsPhieu) {
            if (p.getMaPhong() != null && p.getMaPhong().equalsIgnoreCase(maPhong.trim())) {
                return p;
            }
        }
        return null;
    }

    public void hienThiDanhSachPhieu() {
        ThongBao.inTieuDe("DANH SACH PHIEU DAT PHONG");
        if (dsPhieu.isEmpty()) {
            ThongBao.inCanhBao("Hien chua co phieu dat phong nao trong he thong.");
            return;
        }

        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-20s | %-8s | %-12s | %-12s | %-8s | %-16s |%n",
                "MA PHIEU", "KHACH HANG", "PHONG", "NGAY NHAN", "NGAY TRA", "SO DEM", "TIEN PHONG");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        for (PhieuDatPhong p : dsPhieu) {
            String tenKh = (p.getKhachHang() != null && p.getKhachHang().gethoten() != null)
                    ? p.getKhachHang().gethoten()
                    : "N/A";
            System.out.printf("| %-8s | %-20s | %-8s | %-12s | %-12s | %-8d | %,16.0f |%n",
                    p.getMaPhieu(), tenKh, p.getMaPhong(),
                    p.getNgayNhanStr(), p.getNgayTraStr(),
                    p.tinhSoNgayLuuTru(), p.tinhTienPhong());
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // 2. Quản lý Dịch vụ phát sinh (ChiTietHoaDon từ Module 3)
    // ---------------------------------------------------------------

    public ChiTietHoaDon layChiTietDichVu(String maPhieu) {
        if (maPhieu == null)
            return null;
        ChiTietHoaDon cthd = mapChiTietDichVu.get(maPhieu);
        if (cthd == null) {
            PhieuDatPhong phieu = timPhieuDatPhong(maPhieu);
            if (phieu != null) {
                cthd = new ChiTietHoaDon(phieu);
                mapChiTietDichVu.put(maPhieu, cthd);
            }
        }
        return cthd;
    }

    public void themDichVuChoPhieu(Scanner sc) {
        ThongBao.inTieuDe("GOI THEM DICH VU CHO PHONG / PHIEU DAT");
        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu dat phong (hoac ma phong): ");
        PhieuDatPhong phieu = timPhieuDatPhong(ma);
        if (phieu == null) {
            phieu = timPhieuTheoMaPhong(ma);
        }

        if (phieu == null) {
            ThongBao.inLoi("Khong tim thay phieu dat phong hop le voi thong tin da nhap!");
            return;
        }

        ChiTietHoaDon cthd = layChiTietDichVu(phieu.getMaPhieu());
        cthd.nhap(sc);

        // Đồng bộ cập nhật lại hóa đơn nếu đã có trong danh sách
        HoaDon hd = timHoaDonTheoMaPhieu(phieu.getMaPhieu());
        if (hd != null) {
            hd.setChiTiet(cthd);
        }
        ThongBao.inThanhCong("Da cap nhat thanh cong dich vu cho phieu: " + phieu.getMaPhieu());
    }

    // ---------------------------------------------------------------
    // 3. Quản lý Hóa đơn & Thanh toán (HoaDon + PhuongThucThanhToan)
    // ---------------------------------------------------------------

    public HoaDon timHoaDonTheoMaPhieu(String maPhieu) {
        if (maPhieu == null)
            return null;
        for (HoaDon hd : dsHoaDon) {
            if (hd.getPhieu() != null && hd.getPhieu().getMaPhieu() != null &&
                    hd.getPhieu().getMaPhieu().equalsIgnoreCase(maPhieu.trim())) {
                return hd;
            }
        }
        return null;
    }

    public HoaDon timHoaDonTheoMaHD(String maHD) {
        if (maHD == null)
            return null;
        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaHoaDon() != null && hd.getMaHoaDon().equalsIgnoreCase(maHD.trim())) {
                return hd;
            }
        }
        return null;
    }

    public void thucHienThanhToanVaTraPhong(Scanner sc) {
        ThongBao.inTieuDe("THANH TOAN VA TRA PHONG (CHECK-OUT)");
        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu dat phong hoac ma phong can check-out: ");
        PhieuDatPhong phieu = timPhieuDatPhong(ma);
        if (phieu == null) {
            phieu = timPhieuTheoMaPhong(ma);
        }

        if (phieu == null) {
            ThongBao.inLoi("Khong tim thay phieu dat phong tuong ung!");
            return;
        }

        ChiTietHoaDon cthd = layChiTietDichVu(phieu.getMaPhieu());
        HoaDon hd = timHoaDonTheoMaPhieu(phieu.getMaPhieu());
        if (hd == null) {
            hd = new HoaDon(phieu, cthd, 0.0);
            dsHoaDon.add(hd);
        } else {
            hd.setChiTiet(cthd);
            hd.capNhatHoaDon();
        }

        if (hd.isDaThanhToan()) {
            ThongBao.inCanhBao("Hoa don nay da duoc thanh toan truoc do roi!");
            hd.xuat();
            return;
        }

        // Tiến hành thanh toán qua PhuongThucThanhToan
        PhuongThucThanhToan pttt = new PhuongThucThanhToan(hd);
        pttt.nhap(sc);

        // In biên lai hoàn chỉnh
        pttt.xuat();
        ThongBao.inThanhCong("Da tra phong thanh cong! Phong [" + phieu.getMaPhong() + "] hien o trang thai 'Trong'.");
    }

    public void hienThiDanhSachHoaDon() {
        ThongBao.inTieuDe("DANH SACH HOA DON");
        if (dsHoaDon.isEmpty()) {
            ThongBao.inCanhBao("Hien chua co hoa don nao.");
            return;
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-10s | %-12s | %-14s | %-14s | %-16s | %-16s |%n",
                "MA HOA DON", "MA PHIEU", "NGAY LAP", "TIEN PHONG", "TIEN DICH VU", "TONG CONG", "TRANG THAI");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------");
        for (HoaDon hd : dsHoaDon) {
            String maPhieu = (hd.getPhieu() != null && hd.getPhieu().getMaPhieu() != null) ? hd.getPhieu().getMaPhieu()
                    : "N/A";
            String trangThai = hd.isDaThanhToan() ? "DA THANH TOAN" : "CHUA THANH TOAN";
            System.out.printf("| %-12s | %-10s | %-12s | %,14.0f | %,14.0f | %,16.0f | %-16s |%n",
                    hd.getMaHoaDon(), maPhieu, hd.getNgayLapHoaDon(),
                    hd.getTienPhong(), hd.getTienDichVu(), hd.getTongTienPhong(), trangThai);
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------");
    }

    public double tinhTongDoanhThu() {
        double tong = 0.0;
        for (HoaDon hd : dsHoaDon) {
            if (hd.isDaThanhToan()) {
                tong += hd.getTongTienPhong();
            }
        }
        return tong;
    }

    public void thongKeDoanhThu() {
        ThongBao.inTieuDe("THONG KE DOANH THU KHACH SAN (MODULE 4)");
        int daTra = 0;
        int chuaTra = 0;
        double doanhThuPhong = 0.0;
        double doanhThuDichVu = 0.0;
        double tongDoanhThu = 0.0;

        for (HoaDon hd : dsHoaDon) {
            if (hd.isDaThanhToan()) {
                daTra++;
                doanhThuPhong += hd.getTienPhong();
                doanhThuDichVu += hd.getTienDichVu();
                tongDoanhThu += hd.getTongTienPhong();
            } else {
                chuaTra++;
            }
        }

        System.out.printf("  - Tong so phieu dat phong         : %d phieu%n", dsPhieu.size());
        System.out.printf("  - So hoa don da thanh toan        : %d hoa don%n", daTra);
        System.out.printf("  - So hoa don chua thanh toan      : %d hoa don%n", chuaTra);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("  + Doanh thu tien phong            : %,16.0f VND%n", doanhThuPhong);
        System.out.printf("  + Doanh thu dich vu phu thu       : %,16.0f VND%n", doanhThuDichVu);
        System.out.printf("  ==> TONG DOANH THU THUC TE THU VE : %,16.0f VND%n", tongDoanhThu);
        System.out.println("==================================================================");
    }

    // ---------------------------------------------------------------
    // Menu điều khiển tương tác console
    // ---------------------------------------------------------------
    public void menu(Scanner sc) {
        while (true) {
            ThongBao.inTieuDe("HE THONG QUAN LY DAT PHONG & HOA DON (MODULE 4)");
            System.out.println("  1. Tao phieu dat phong moi");
            System.out.println("  2. Xem danh sach tat ca phieu dat phong");
            System.out.println("  3. Goi them dich vu cho phong luu tru (Module 3)");
            System.out.println("  4. Xem chi tiet hoa don tam tinh");
            System.out.println("  5. Thanh toan & Tra phong (Check-out)");
            System.out.println("  6. Xem danh sach tat ca hoa don");
            System.out.println("  7. Thong ke & Tong hop doanh thu");
            System.out.println("  0. Quay lai / Thoat");

            int chon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Chon chuc nang (0 - 7): ", 0, 7);
            switch (chon) {
                case 1:
                    taoPhieuDatPhongMoi(sc);
                    break;
                case 2:
                    hienThiDanhSachPhieu();
                    break;
                case 3:
                    themDichVuChoPhieu(sc);
                    break;
                case 4: {
                    String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu hoac ma hoa don: ");
                    HoaDon hd = timHoaDonTheoMaHD(ma);
                    if (hd == null)
                        hd = timHoaDonTheoMaPhieu(ma);
                    if (hd != null) {
                        hd.xuat();
                    } else {
                        PhieuDatPhong p = timPhieuDatPhong(ma);
                        if (p != null) {
                            ChiTietHoaDon cthd = layChiTietDichVu(p.getMaPhieu());
                            HoaDon hdTam = new HoaDon(p, cthd, 0.0);
                            hdTam.xuat();
                        } else {
                            ThongBao.inLoi("Khong tim thay thong tin hoa don tuong ung!");
                        }
                    }
                    break;
                }
                case 5:
                    thucHienThanhToanVaTraPhong(sc);
                    break;
                case 6:
                    hienThiDanhSachHoaDon();
                    break;
                case 7:
                    thongKeDoanhThu();
                    break;
                case 0:
                    ThongBao.inThanhCong("Da thoat Quan Ly Dat Phong.");
                    return;
            }
        }
    }

}
