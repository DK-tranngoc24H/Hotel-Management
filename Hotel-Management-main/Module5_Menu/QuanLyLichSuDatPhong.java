import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Xu ly toan bo nghiep vu Module 5: luu tru, tra cuu, cap nhat trang thai va doanh thu.
 */
public class QuanLyLichSuDatPhong {
    private static final DateTimeFormatter DINH_DANG_NGAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final List<BanGhiLichSu> lichSu = new ArrayList<>();

    public List<BanGhiLichSu> getLichSu() {
        return Collections.unmodifiableList(lichSu);
    }

    public boolean themPhieuDatPhong(PhieuDatPhong phieu) {
        if (phieu == null || phieu.getMaPhieu() == null || timTheoMaPhieu(phieu.getMaPhieu()) != null) {
            return false;
        }
        lichSu.add(new BanGhiLichSu(phieu));
        return true;
    }

    public BanGhiLichSu timTheoMaPhieu(String maPhieu) {
        if (maPhieu == null) {
            return null;
        }
        for (BanGhiLichSu banGhi : lichSu) {
            if (maPhieu.trim().equalsIgnoreCase(banGhi.getPhieu().getMaPhieu())) {
                return banGhi;
            }
        }
        return null;
    }

    public BanGhiLichSu timTheoMaPhong(String maPhong) {
        if (maPhong == null) {
            return null;
        }
        for (BanGhiLichSu banGhi : lichSu) {
            if (maPhong.trim().equalsIgnoreCase(banGhi.getPhieu().getMaPhong())) {
                return banGhi;
            }
        }
        return null;
    }

    public List<BanGhiLichSu> timTheoCCCD(String cccd) {
        List<BanGhiLichSu> ketQua = new ArrayList<>();
        if (cccd == null) {
            return ketQua;
        }
        for (BanGhiLichSu banGhi : lichSu) {
            KHACHHANG khach = banGhi.getPhieu().getKhachHang();
            if (khach != null && cccd.trim().equalsIgnoreCase(khach.getCCCD())) {
                ketQua.add(banGhi);
            }
        }
        return ketQua;
    }

    public boolean themDichVu(String maPhieu, String maDichVu, int soLuong) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        return banGhi != null && banGhi.getChiTietDichVu().themDichVu(maDichVu, soLuong);
    }

    public HoaDon taoHoaDon(String maPhieu, double giamGia) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null) {
            return null;
        }
        if (banGhi.getHoaDon() == null) {
            String maHoaDon = "HD-" + banGhi.getPhieu().getMaPhieu();
            banGhi.setHoaDon(new HoaDon(maHoaDon, ngayHienTai(), banGhi.getPhieu(), banGhi.getChiTietDichVu(), giamGia));
        } else {
            banGhi.getHoaDon().setChiTiet(banGhi.getChiTietDichVu());
            banGhi.getHoaDon().capNhatHoaDon();
        }
        return banGhi.getHoaDon();
    }

    public boolean batDauLuuTru(String maPhieu) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() == TrangThai.DA_HUY
                || banGhi.getTrangThai() == TrangThai.DA_TRA_PHONG) {
            return false;
        }
        banGhi.setTrangThai(TrangThai.DANG_O);
        if (banGhi.getPhieu().getPhong() != null) {
            banGhi.getPhieu().getPhong().setTrangThai("Dang o");
        }
        return true;
    }

    public boolean thanhToanVaTraPhong(String maPhieu, String phuongThucThanhToan) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() == TrangThai.DA_HUY
                || banGhi.getTrangThai() == TrangThai.DA_TRA_PHONG) {
            return false;
        }
        HoaDon hoaDon = taoHoaDon(maPhieu, 0.0);
        if (hoaDon != null) {
            hoaDon.setDaThanhToan(true);
        }
        String pttt = (phuongThucThanhToan == null || phuongThucThanhToan.trim().isEmpty())
                ? "Khong xac dinh" : phuongThucThanhToan.trim();
        banGhi.setPhuongThucThanhToan(pttt);
        banGhi.setTrangThai(TrangThai.DA_TRA_PHONG);
        if (banGhi.getPhieu().getPhong() != null) {
            banGhi.getPhieu().getPhong().setTrangThai("Trong");
        }
        return true;
    }

    public boolean huyDatPhong(String maPhieu) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() == TrangThai.DA_TRA_PHONG) {
            return false;
        }
        banGhi.setTrangThai(TrangThai.DA_HUY);
        if (banGhi.getPhieu().getPhong() != null) {
            banGhi.getPhieu().getPhong().setTrangThai("Trong");
        }
        return true;
    }

    public double tinhDoanhThuDaThanhToan() {
        double tong = 0.0;
        for (BanGhiLichSu banGhi : lichSu) {
            if (banGhi.getHoaDon() != null && banGhi.getHoaDon().isDaThanhToan()) {
                tong += banGhi.getHoaDon().getTongTienPhong();
            }
        }
        return tong;
    }

    public void hienThiLichSu() {
        System.out.println("\n================ LICH SU DAT PHONG - MODULE 5 ================");
        if (lichSu.isEmpty()) {
            System.out.println("Chua co du lieu lich su.");
            return;
        }
        System.out.printf("%-10s | %-20s | %-10s | %-14s | %-16s | %-16s%n",
                "MA PHIEU", "KHACH HANG", "MA PHONG", "TRANG THAI", "TONG TIEN", "THANH TOAN");
        for (BanGhiLichSu banGhi : lichSu) {
            String tenKhach = banGhi.getPhieu().getKhachHang() == null ? "N/A"
                    : banGhi.getPhieu().getKhachHang().gethoten();
            String tongTien = banGhi.getHoaDon() == null ? "Chua lap"
                    : String.format("%,.0f VND", banGhi.getHoaDon().getTongTienPhong());
            String thanhToan = banGhi.getHoaDon() != null && banGhi.getHoaDon().isDaThanhToan()
                    ? "Da thanh toan" : "Chua thanh toan";
            System.out.printf("%-10s | %-20s | %-10s | %-14s | %-16s | %-16s%n",
                    banGhi.getPhieu().getMaPhieu(), tenKhach, banGhi.getPhieu().getMaPhong(),
                    banGhi.getTrangThai().getTenHienThi(), tongTien, thanhToan);
        }
    }

    private String ngayHienTai() {
        return LocalDate.now().format(DINH_DANG_NGAY);
    }
}