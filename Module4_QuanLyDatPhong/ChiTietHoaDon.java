import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lớp ChiTietHoaDon – quản lý danh sách các dịch vụ mà khách đã sử dụng.
 * Áp dụng trực tiếp cấu trúc và dữ liệu DichVu từ Module 3 (DichVu,
 * DichVuAnUong,
 * DichVuSpaGiaiTri, DichVuThueXe, DanhSachDichVu) và các chuẩn chung từ
 * ClassDungChung.
 */
public class ChiTietHoaDon implements INhapXuat {

    // =========================================================================
    // LỚP NỘI BỘ LƯU THÔNG TIN MỖI DỊCH VỤ ĐƯỢC KHÁCH SỬ DỤNG
    // =========================================================================
    public static class ChiTietDichVu {
        private DichVu dichVu; // Đối tượng DichVu từ Module 3
        private int soLuong; // Số lượng / số lần sử dụng

        public ChiTietDichVu(DichVu dichVu, int soLuong) {
            this.dichVu = dichVu;
            this.soLuong = soLuong;
        }

        public DichVu getDichVu() {
            return dichVu;
        }

        public void setDichVu(DichVu dichVu) {
            this.dichVu = dichVu;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        /** Thành tiền = đơn giá (từ Module 3) × số lượng */
        public double tinhThanhTien() {
            if (dichVu == null)
                return 0.0;
            return dichVu.getDonGia() * soLuong;
        }

        /** Alias để tương thích tinhTien() */
        public double tinhTien() {
            return tinhThanhTien();
        }

        public String getTenDichVu() {
            return (dichVu != null && dichVu.getTenDV() != null) ? dichVu.getTenDV() : "";
        }

        public double getDonGia() {
            return (dichVu != null) ? dichVu.getDonGia() : 0.0;
        }

        public String getPhanLoai() {
            if (dichVu instanceof DichVuAnUong) {
                return "Am thuc";
            } else if (dichVu instanceof DichVuSpaGiaiTri) {
                return "Spa & Giai tri";
            } else if (dichVu instanceof DichVuThueXe) {
                return "Thue xe";
            }
            return "Dich vu khac";
        }

        @Override
        public String toString() {
            if (dichVu == null)
                return "";
            return String.format("%-6s | %-24s | %-14s | %,10.0f VND x %-3d = %,12.0f VND",
                    dichVu.getMaDV(), dichVu.getTenDV(), getPhanLoai(),
                    dichVu.getDonGia(), soLuong, tinhThanhTien());
        }
    }

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    private PhieuDatPhong                  phieu;             // Phiếu đặt phòng liên kết
    private final KHACHHANG                khach;             // Khách hàng liên kết (Module 1)
    private final ArrayList<ChiTietDichVu> danhSachChiTiet;   // Danh sách chi tiết dịch vụ đã dùng
    private double                         tongTienPhuThu;    // Tổng tiền dịch vụ

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Khởi tạo chi tiết hóa đơn từ phiếu đặt phòng.
     *
     * @param phieu Phiếu đặt phòng của khách
     */
    public ChiTietHoaDon(PhieuDatPhong phieu) {
        this.phieu           = phieu;
        this.khach           = (phieu != null) ? phieu.getKhachHang() : null;
        this.danhSachChiTiet = new ArrayList<>();
        this.tongTienPhuThu  = 0.0;
    }

    /**
     * Khởi tạo chi tiết hóa đơn trực tiếp cho một khách hàng.
     *
     * @param khach Đối tượng KHACHHANG (Module 1)
     */
    public ChiTietHoaDon(KHACHHANG khach) {
        this.phieu           = null;
        this.khach           = khach;
        this.danhSachChiTiet = new ArrayList<>();
        this.tongTienPhuThu  = 0.0;
    }

    public PhieuDatPhong getPhieuDatPhong() {
        return phieu;
    }

    public void setPhieuDatPhong(PhieuDatPhong phieu) {
        this.phieu = phieu;
    }

    // ---------------------------------------------------------------
    // Thêm dịch vụ (Hỗ trợ nạp dữ liệu từ Module 3)
    // ---------------------------------------------------------------

    /**
     * 1. Thêm dịch vụ bằng đối tượng DichVu trực tiếp từ Module 3.
     * Nếu dịch vụ đã tồn tại trong danh sách, tự động cộng dồn số lượng.
     *
     * @param dv      Đối tượng DichVu (DichVuAnUong, DichVuSpaGiaiTri,
     *                DichVuThueXe...)
     * @param soLuong Số lượng sử dụng
     */
    public void themDichVu(DichVu dv, int soLuong) {
        if (dv == null || soLuong <= 0)
            return;

        for (ChiTietDichVu item : danhSachChiTiet) {
            DichVu tonTai = item.getDichVu();
            if (tonTai != null && tonTai.getMaDV() != null &&
                    tonTai.getMaDV().equalsIgnoreCase(dv.getMaDV())) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                tinhTongTienPhuThu();
                return;
            }
        }

        danhSachChiTiet.add(new ChiTietDichVu(dv, soLuong));
        tinhTongTienPhuThu();
    }

    /**
     * 2. Thêm dịch vụ theo Mã dịch vụ (maDV) từ kho dữ liệu mẫu Module 3
     * (DanhSachDichVu).
     *
     * @param maDV    Mã dịch vụ (ví dụ: DA01, NU01, SG01, TX01...)
     * @param soLuong Số lượng sử dụng
     * @return true nếu tìm thấy và thêm thành công, false nếu không tìm thấy mã
     */
    public boolean themDichVu(String maDV, int soLuong) {
        if (maDV == null || soLuong <= 0)
            return false;

        DanhSachDichVu ds = new DanhSachDichVu();
        for (DichVu dv : ds.getDsDichVu()) {
            if (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(maDV.trim())) {
                themDichVu(dv, soLuong);
                return true;
            }
        }
        return false;
    }

    /**
     * 3. Thêm dịch vụ theo Tên, Đơn giá và Số lượng (duy trì tương thích với code
     * cũ).
     * Ưu tiên tra cứu trong dữ liệu mẫu Module 3; nếu chưa có thì khởi tạo DichVu
     * phù hợp.
     *
     * @param tenDichVu Tên dịch vụ
     * @param donGia    Đơn giá (VNĐ)
     * @param soLuong   Số lượng sử dụng
     */
    public void themDichVu(String tenDichVu, double donGia, int soLuong) {
        if (soLuong <= 0 || tenDichVu == null)
            return;

        // Tra cứu trong DanhSachDichVu của Module 3
        DanhSachDichVu ds = new DanhSachDichVu();
        for (DichVu dv : ds.getDsDichVu()) {
            if (dv.getTenDV() != null && dv.getTenDV().equalsIgnoreCase(tenDichVu.trim())) {
                themDichVu(dv, soLuong);
                return;
            }
            if (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(tenDichVu.trim())) {
                themDichVu(dv, soLuong);
                return;
            }
        }

        // Nếu không có sẵn trong danh sách mẫu, khởi tạo đối tượng DichVuAnUong cụ thể
        // kế thừa DichVu
        String maTuTao = "DV" + String.format("%02d", danhSachChiTiet.size() + 1);
        DichVu dvMoi = new DichVuAnUong(maTuTao, tenDichVu, donGia, "Dich vu them", tenDichVu);
        themDichVu(dvMoi, soLuong);
    }

    // ---------------------------------------------------------------
    // Xoá dịch vụ
    // ---------------------------------------------------------------

    /**
     * Xoá dịch vụ theo mã dịch vụ hoặc tên dịch vụ.
     *
     * @param tuKhoa Mã DV hoặc Tên DV cần xoá
     * @return true nếu xoá thành công, false nếu không tìm thấy
     */
    public boolean xoaDichVu(String tuKhoa) {
        if (tuKhoa == null)
            return false;
        String key = tuKhoa.trim();

        for (int i = 0; i < danhSachChiTiet.size(); i++) {
            ChiTietDichVu item = danhSachChiTiet.get(i);
            DichVu dv = item.getDichVu();
            if (dv != null) {
                if ((dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(key)) ||
                        (dv.getTenDV() != null && dv.getTenDV().equalsIgnoreCase(key))) {
                    danhSachChiTiet.remove(i);
                    tinhTongTienPhuThu();
                    return true;
                }
            }
        }
        return false;
    }

    // ---------------------------------------------------------------
    // Tính tổng tiền
    // ---------------------------------------------------------------

    /**
     * Tính lại tổng tiền phụ thu từ toàn bộ danh sách dịch vụ.
     */
    public void tinhTongTienPhuThu() {
        tongTienPhuThu = 0.0;
        for (ChiTietDichVu item : danhSachChiTiet) {
            tongTienPhuThu += item.tinhThanhTien();
        }
    }

    // ---------------------------------------------------------------
    // Nhập / Xuất (INhapXuat)
    // ---------------------------------------------------------------

    /**
     * Nhập dịch vụ sử dụng: Hiển thị bảng dịch vụ mẫu từ Module 3
     * và cho phép chọn theo Mã DV hoặc nhập dịch vụ tùy biến.
     */
    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("DANG KY DICH VU KHACH HANG (MODULE 3)");

        DanhSachDichVu catalog = new DanhSachDichVu();
        catalog.hienThiDanhSach();

        while (true) {
            boolean themTiep = KiemTraLoi.nhapCoKhong(sc, "\nBan co muon them dich vu khong? (co/khong): ");
            if (!themTiep) {
                break;
            }

            System.out.println("Chon cach them dich vu:");
            System.out.println("  1. Chon tu danh sach dich vu khach san (theo Ma DV)");
            System.out.println("  2. Nhap dich vu tuy bien ben ngoai");
            int luaChon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Lua chon cua ban (1 hoac 2): ", 1, 2);

            if (luaChon == 1) {
                String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma dich vu (VD: DA01, NU01, SG01, TX01): ");
                DichVu timThay = null;
                for (DichVu dv : catalog.getDsDichVu()) {
                    if (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(ma)) {
                        timThay = dv;
                        break;
                    }
                }

                if (timThay != null) {
                    int sl = KiemTraLoi.nhapSoNguyenDuong(sc,
                            "Nhap so luong su dung cho [" + timThay.getTenDV() + "]: ");
                    if (sl == 0) {
                        ThongBao.inCanhBao("So luong = 0, bo qua.");
                    } else {
                        themDichVu(timThay, sl);
                        ThongBao.inThanhCong("Da them " + sl + " x [" + timThay.getTenDV() + "] vao chi tiet hoa don.");
                    }
                } else {
                    ThongBao.inLoi("Khong tim thay ma dich vu '" + ma + "' trong danh muc khach san!");
                }
            } else {
                String ten = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten dich vu: ");
                double gia = KiemTraLoi.nhapSoThucDuong(sc, "Nhap don gia (VND): ");
                int sl = KiemTraLoi.nhapSoNguyenDuong(sc, "Nhap so luong: ");
                if (sl == 0) {
                    ThongBao.inCanhBao("So luong = 0, bo qua.");
                } else {
                    themDichVu(ten, gia, sl);
                    ThongBao.inThanhCong("Da them dich vu '" + ten + "' vao chi tiet hoa don.");
                }
            }
        }
    }

    public void xuatDichVu() {
        xuat();
    }

    /**
     * Xuất danh sách dịch vụ chi tiết và tổng tiền.
     */
    @Override
    public void xuat() {
        ThongBao.inTieuDe("CHI TIET DICH VU SU DUNG");
        if (khach != null && khach.gethoten() != null && !khach.gethoten().isEmpty()) {
            System.out.println("Khach hang: " + khach.gethoten());
        }

        if (danhSachChiTiet.isEmpty()) {
            ThongBao.inCanhBao("Khach hang khong su dung dich vu phu tro nao.");
        } else {
            System.out.println(
                    "------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-6s | %-26s | %-16s | %-12s | %-8s | %-15s |%n",
                    "MA DV", "TEN DICH VU", "PHAN LOAI", "DON GIA", "SO LUONG", "THANH TIEN");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------");
            for (ChiTietDichVu item : danhSachChiTiet) {
                DichVu dv = item.getDichVu();
                System.out.printf("| %-6s | %-26s | %-16s | %,12.0f | %-8d | %,15.0f |%n",
                        dv.getMaDV(), dv.getTenDV(), item.getPhanLoai(),
                        dv.getDonGia(), item.getSoLuong(), item.tinhThanhTien());
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------");
            System.out.printf("Tong cong tien dich vu phu thu: %,.0f VND%n", tongTienPhuThu);
        }
    }

    // ---------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------
    public double getTongTienPhuThu() {
        return tongTienPhuThu;
    }

    public ArrayList<ChiTietDichVu> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    /**
     * Trả về danh sách đối tượng DichVu từ Module 3 (duy trì tương thích).
     */
    public ArrayList<DichVu> getDanhSachDichVu() {
        ArrayList<DichVu> ds = new ArrayList<>();
        for (ChiTietDichVu item : danhSachChiTiet) {
            ds.add(item.getDichVu());
        }
        return ds;
    }

    public KHACHHANG getKhachHang() {
        return khach;
    }

    public int getSoLuongDichVu() {
        return danhSachChiTiet.size();
    }

    public void xoaTatCaDichVu() {
        danhSachChiTiet.clear();
        tinhTongTienPhuThu();
    }

}
