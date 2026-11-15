import java.util.ArrayList;
import java.util.Scanner;
import BT_quanLyKhachSan.DanhSachDichVu;
import BT_quanLyKhachSan.DichVu;
import BT_quanLyKhachSan.DichVuAnUong;
import BT_quanLyKhachSan.DichVuSpaGiaiTri;
import BT_quanLyKhachSan.DichVuThueXe;

/**
 * Lớp ChiTietHoaDon – quản lý danh sách các dịch vụ mà khách đã sử dụng.
 * Đã đồng bộ tên class và tên biến với Module 1 (KhachHang) và Module 3 (DichVu, DanhSachDichVu.getDs(), hienThi()),
 * đồng thời kiểm soát ngoại lệ bằng Module4_Exception.
 */
public class ChiTietHoaDon implements INhapXuat {

    public static class ChiTietDichVu {
        private DichVu dichVu;
        private int soLuong;

        public ChiTietDichVu(DichVu dichVu, int soLuong) {
            if (dichVu == null) {
                throw new Module4_Exception.DichVuKhongHopLeException("Đối tượng dịch vụ không được để trống (null).");
            }
            if (soLuong <= 0) {
                throw new Module4_Exception.DichVuKhongHopLeException("Số lượng sử dụng phải lớn hơn 0 (nhập vào: " + soLuong + ").");
            }
            this.dichVu = dichVu;
            this.soLuong = soLuong;
        }

        public DichVu getDichVu() {
            return dichVu;
        }

        public void setDichVu(DichVu dichVu) {
            if (dichVu == null) {
                throw new Module4_Exception.DichVuKhongHopLeException("Đối tượng dịch vụ không được null.");
            }
            this.dichVu = dichVu;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            if (soLuong <= 0) {
                throw new Module4_Exception.DichVuKhongHopLeException("Số lượng dịch vụ cập nhật phải lớn hơn 0.");
            }
            this.soLuong = soLuong;
        }

        public double tinhThanhTien() {
            return (dichVu != null) ? dichVu.getDonGia() * soLuong : 0.0;
        }

        public double tinhTien() {
            return tinhThanhTien();
        }

        public String getTenDV() {
            return (dichVu != null && dichVu.getTenDV() != null) ? dichVu.getTenDV() : "";
        }

        public String getTenDichVu() {
            return getTenDV();
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
            if (dichVu == null) return "";
            return String.format("%-6s | %-24s | %-14s | %,10.0f VND x %-3d = %,12.0f VND",
                    dichVu.getMaDV(), dichVu.getTenDV(), getPhanLoai(),
                    dichVu.getDonGia(), soLuong, tinhThanhTien());
        }
    }

    private PhieuDatPhong phieu;
    private final KhachHang khachHang;
    private final ArrayList<ChiTietDichVu> danhSachChiTiet;
    private double tongTienPhuThu;

    public ChiTietHoaDon(PhieuDatPhong phieu) {
        this.phieu = phieu;
        this.khachHang = (phieu != null) ? phieu.getKhachHang() : null;
        this.danhSachChiTiet = new ArrayList<>();
        this.tongTienPhuThu = 0.0;
    }

    public ChiTietHoaDon(KhachHang khachHang) {
        this.phieu = null;
        this.khachHang = khachHang;
        this.danhSachChiTiet = new ArrayList<>();
        this.tongTienPhuThu = 0.0;
    }

    public PhieuDatPhong getPhieuDatPhong() {
        return phieu;
    }

    public void setPhieuDatPhong(PhieuDatPhong phieu) {
        this.phieu = phieu;
    }

    /**
     * Lấy danh sách dịch vụ từ Module 3 (đọc từ file Module3_DichVuTienIch/dichvu.txt).
     */
    private ArrayList<DichVu> layDanhSachDichVuTuFile() {
        DanhSachDichVu dsDV = new DanhSachDichVu();
        return dsDV.getDs();
    }

    private DichVu timDichVuTrongDanhMuc(ArrayList<DichVu> dsDichVu, String tuKhoa) {
        if (dsDichVu == null || tuKhoa == null) {
            return null;
        }
        String key = tuKhoa.trim();
        for (DichVu dv : dsDichVu) {
            if ((dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(key)) ||
                    (dv.getTenDV() != null && dv.getTenDV().equalsIgnoreCase(key))) {
                return dv;
            }
        }
        return null;
    }

    private String taoMaDichVuTuDong() {
        int stt = 1;
        while (true) {
            String ma = String.format("DV%02d", stt);
            boolean daTrung = false;
            for (ChiTietDichVu item : danhSachChiTiet) {
                DichVu dv = item.getDichVu();
                if (dv != null && dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(ma)) {
                    daTrung = true;
                    break;
                }
            }
            if (!daTrung) {
                return ma;
            }
            stt++;
        }
    }

    public void themDichVu(DichVu dv, int soLuong) {
        if (dv == null) {
            throw new Module4_Exception.DichVuKhongHopLeException("Dịch vụ thêm vào không được để trống (null).");
        }
        if (soLuong <= 0) {
            throw new Module4_Exception.DichVuKhongHopLeException("Số lượng dịch vụ '" + dv.getTenDV() + "' phải lớn hơn 0.");
        }

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

    public boolean themDichVu(String maDV, int soLuong) {
        if (maDV == null || maDV.trim().isEmpty() || soLuong <= 0) {
            return false;
        }
        for (DichVu dv : layDanhSachDichVuTuFile()) {
            if (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(maDV.trim())) {
                themDichVu(dv, soLuong);
                return true;
            }
        }
        return false;
    }

    public void themDichVu(String tenDV, double donGia, int soLuong) {
        if (tenDV == null || tenDV.trim().isEmpty()) {
            throw new Module4_Exception.DichVuKhongHopLeException("Tên dịch vụ không được để trống.");
        }
        if (donGia < 0 || soLuong <= 0) {
            throw new Module4_Exception.DichVuKhongHopLeException("Đơn giá phải >= 0 và số lượng phải > 0.");
        }

        DichVu dvCoSan = timDichVuTrongDanhMuc(layDanhSachDichVuTuFile(), tenDV);
        if (dvCoSan != null) {
            themDichVu(dvCoSan, soLuong);
            return;
        }

        String maTuTao = taoMaDichVuTuDong();
        DichVu dvMoi = new DichVuAnUong(maTuTao, tenDV.trim(), donGia, "Dich vu them", tenDV.trim());
        themDichVu(dvMoi, soLuong);
    }

    public boolean xoaDichVu(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return false;
        }
        String key = tuKhoa.trim();
        for (int i = 0; i < danhSachChiTiet.size(); i++) {
            DichVu dv = danhSachChiTiet.get(i).getDichVu();
            if (dv != null && ((dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(key)) ||
                    (dv.getTenDV() != null && dv.getTenDV().equalsIgnoreCase(key)))) {
                danhSachChiTiet.remove(i);
                tinhTongTienPhuThu();
                return true;
            }
        }
        return false;
    }

    public void tinhTongTienPhuThu() {
        tongTienPhuThu = 0.0;
        for (ChiTietDichVu item : danhSachChiTiet) {
            tongTienPhuThu += item.tinhThanhTien();
        }
    }

    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("DANG KY DICH VU KHACH HANG (MODULE 3)");
        DanhSachDichVu dsDV = new DanhSachDichVu();
        ArrayList<DichVu> danhMucDV = dsDV.getDs();
        if (!danhMucDV.isEmpty()) {
            dsDV.hienThi();
        } else {
            ThongBao.inCanhBao("Danh sach dich vu trong dichvu.txt dang trong.");
        }

        while (true) {
            if (!KiemTraLoi.nhapCoKhong(sc, "\nBan co muon them dich vu khong? (co/khong): ")) {
                break;
            }

            System.out.println("Chon cach them dich vu:");
            System.out.println("  1. Chon tu danh sach dich vu khach san (theo Ma DV)");
            System.out.println("  2. Nhap dich vu tuy bien ben ngoai");
            int luaChon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Lua chon cua ban (1 hoac 2): ", 1, 2);

            try {
                if (luaChon == 1) {
                    if (danhMucDV.isEmpty()) {
                        ThongBao.inCanhBao("Chua co dich vu nao trong file dichvu.txt.");
                        continue;
                    }
                    String maDV = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma dich vu (VD: DA01, NU01, SG01, TX01): ");
                    DichVu timThay = timDichVuTrongDanhMuc(danhMucDV, maDV);
                    if (timThay == null) {
                        throw new Module4_Exception.DichVuKhongHopLeException(
                                "Không tìm thấy mã dịch vụ '" + maDV + "' trong danh mục khách sạn!");
                    }
                    int soLuong = KiemTraLoi.nhapSoNguyenTrongKhoang(sc,
                            "Nhap so luong su dung cho [" + timThay.getTenDV() + "] (> 0): ", 1, Integer.MAX_VALUE);
                    themDichVu(timThay, soLuong);
                    ThongBao.inThanhCong("Da them " + soLuong + " x [" + timThay.getTenDV() + "] vao chi tiet hoa don.");
                } else {
                    String tenDV = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten dich vu: ");
                    double donGia = KiemTraLoi.nhapSoThucDuong(sc, "Nhap don gia (VND): ");
                    int soLuong = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Nhap so luong (> 0): ", 1, Integer.MAX_VALUE);
                    themDichVu(tenDV, donGia, soLuong);
                    ThongBao.inThanhCong("Da them dich vu '" + tenDV + "' vao chi tiet hoa don.");
                }
            } catch (Module4_Exception e) {
                ThongBao.inLoi(e.getMessage());
            }
        }
    }

    public void xuatDichVu() {
        xuat();
    }

    @Override
    public void xuat() {
        ThongBao.inTieuDe("CHI TIET DICH VU SU DUNG");
        if (khachHang != null && khachHang.gethoten() != null && !khachHang.gethoten().isEmpty()) {
            System.out.println("Khach hang: " + khachHang.gethoten());
        }

        if (danhSachChiTiet.isEmpty()) {
            ThongBao.inCanhBao("Khach hang khong su dung dich vu phu tro nao.");
        } else {
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-6s | %-26s | %-16s | %-12s | %-8s | %-15s |%n",
                    "MA DV", "TEN DICH VU", "PHAN LOAI", "DON GIA", "SO LUONG", "THANH TIEN");
            System.out.println("------------------------------------------------------------------------------------------------------");
            for (ChiTietDichVu item : danhSachChiTiet) {
                DichVu dv = item.getDichVu();
                System.out.printf("| %-6s | %-26s | %-16s | %,12.0f | %-8d | %,15.0f |%n",
                        dv.getMaDV(), dv.getTenDV(), item.getPhanLoai(),
                        dv.getDonGia(), item.getSoLuong(), item.tinhThanhTien());
            }
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.printf("Tong cong tien dich vu phu thu: %,.0f VND%n", tongTienPhuThu);
        }
    }

    public double getTongTienPhuThu() {
        return tongTienPhuThu;
    }

    public ArrayList<ChiTietDichVu> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    public ArrayList<DichVu> getDanhSachDichVu() {
        ArrayList<DichVu> ds = new ArrayList<>();
        for (ChiTietDichVu item : danhSachChiTiet) {
            ds.add(item.getDichVu());
        }
        return ds;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public int getSoLuongDichVu() {
        return danhSachChiTiet.size();
    }

    public void xoaTatCaDichVu() {
        danhSachChiTiet.clear();
        tinhTongTienPhuThu();
    }
}
