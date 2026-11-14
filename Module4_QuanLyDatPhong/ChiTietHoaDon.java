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
     * Lấy danh sách dịch vụ mẫu từ Module 3 (nếu file dichvu.txt ở thư mục con chưa nạp được thì khởi tạo bộ mặc định).
     */
    private ArrayList<DichVu> layDanhSachDichVuMau() {
        DanhSachDichVu dsDV = new DanhSachDichVu();
        ArrayList<DichVu> list = dsDV.getDs();
        if (list != null && !list.isEmpty()) {
            return list;
        }
        ArrayList<DichVu> macDinh = new ArrayList<>();
        macDinh.add(new DichVuAnUong("DA01", "Phở bò đặc biệt", 50000.0, "Đồ ăn", "Bữa sáng"));
        macDinh.add(new DichVuAnUong("DA02", "Bún Bò", 50000.0, "Đồ ăn", "Bữa sáng"));
        macDinh.add(new DichVuAnUong("DA03", "Cơm tấm sườn bì chả", 65000.0, "Đồ ăn", "Bữa trưa/tối"));
        macDinh.add(new DichVuAnUong("DA04", "Cơm gà xối mỡ", 65000.0, "Đồ ăn", "Bữa trưa/tối"));
        macDinh.add(new DichVuAnUong("DA05", "Lẩu Gà Lá é", 200000.0, "Đồ ăn", "Bữa trưa/tối"));
        macDinh.add(new DichVuAnUong("NU01", "Cà phê sữa đá", 35000.0, "Đồ uống", "Không cồn - 280ml"));
        macDinh.add(new DichVuAnUong("NU02", "Nước ép cam tươi", 35000.0, "Đồ uống", "Không cồn - 330ml"));
        macDinh.add(new DichVuAnUong("NU03", "Matcha latte", 35000.0, "Đồ uống", "Không cồn - 330ml"));
        macDinh.add(new DichVuAnUong("NU04", "Bia Heineken lon", 30000.0, "Đồ uống", "Có cồn - 330ml"));
        macDinh.add(new DichVuAnUong("NU05", "Rượu gạo", 70000.0, "Đồ uống", "Có cồn - 200ml"));
        macDinh.add(new DichVuSpaGiaiTri("SG01", "Massage body Thụy Điển", 300000.0, "Massage", 60));
        macDinh.add(new DichVuSpaGiaiTri("SG02", "Vé vào cổng Hồ bơi vô cực", 100000.0, "Hồ bơi", 120));
        macDinh.add(new DichVuSpaGiaiTri("SG03", "Phòng hát Karaoke VIP", 250000.0, "Karaoke", 60));
        macDinh.add(new DichVuSpaGiaiTri("SG04", "Vé tập Gym", 50000.0, "Phòng Gym", 180));
        macDinh.add(new DichVuThueXe("TX01", "Thuê xe đạp", 50000.0, "Xe đạp", 1));
        macDinh.add(new DichVuThueXe("TX02", "Thuê xe máy tay ga", 150000.0, "Xe máy", 1));
        macDinh.add(new DichVuThueXe("TX03", "Thuê ô tô tự lái 4 chỗ", 850000.0, "Ô tô 4 chỗ", 1));
        macDinh.add(new DichVuThueXe("TX04", "Thuê ô tô 7 chỗ có tài xế", 1500000.0, "Ô tô 7 chỗ", 1));
        return macDinh;
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
        for (DichVu dv : layDanhSachDichVuMau()) {
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

        for (DichVu dv : layDanhSachDichVuMau()) {
            if ((dv.getTenDV() != null && dv.getTenDV().equalsIgnoreCase(tenDV.trim())) ||
                    (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(tenDV.trim()))) {
                themDichVu(dv, soLuong);
                return;
            }
        }

        String maTuTao = "DV" + String.format("%02d", danhSachChiTiet.size() + 1);
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
        if (!dsDV.getDs().isEmpty()) {
            dsDV.hienThi();
        } else {
            System.out.printf("  %-6s | %-28s | %-12s | %-25s%n", "MA DV", "TEN DICH VU", "DON GIA", "CHI TIET");
            System.out.println("  --------------------------------------------------------------------------------");
            for (DichVu dv : layDanhSachDichVuMau()) {
                System.out.printf("  %-6s | %-28s | %,12.0f | %-25s%n",
                        dv.getMaDV(), dv.getTenDV(), dv.getDonGia(), dv.moTaChiTiet());
            }
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
                    String maDV = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma dich vu (VD: DA01, NU01, SG01, TX01): ");
                    DichVu timThay = null;
                    for (DichVu dv : layDanhSachDichVuMau()) {
                        if (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(maDV.trim())) {
                            timThay = dv;
                            break;
                        }
                    }
                    if (timThay == null) {
                        throw new Module4_Exception.DichVuKhongHopLeException(
                                "Không tìm thấy mã dịch vụ '" + maDV + "' trong danh mục khách sạn!");
                    }
                    int soLuong = KiemTraLoi.nhapSoNguyenDuong(sc, "Nhap so luong su dung cho [" + timThay.getTenDV() + "]: ");
                    themDichVu(timThay, soLuong);
                    ThongBao.inThanhCong("Da them " + soLuong + " x [" + timThay.getTenDV() + "] vao chi tiet hoa don.");
                } else {
                    String tenDV = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten dich vu: ");
                    double donGia = KiemTraLoi.nhapSoThucDuong(sc, "Nhap don gia (VND): ");
                    int soLuong = KiemTraLoi.nhapSoNguyenDuong(sc, "Nhap so luong: ");
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
