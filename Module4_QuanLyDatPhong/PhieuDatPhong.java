import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * Lớp PhieuDatPhong – quản lý thông tin đặt phòng của khách hàng.
 * Đã loại bỏ hoàn toàn giá phòng ví dụ 500k, lấy trực tiếp đối tượng Phong và giá phòng
 * từ Module 2 (DanhSachPhong, Phong, PhongStandard, PhongVIP).
 */
public class PhieuDatPhong implements INhapXuat {

    /** Định dạng chuẩn đầu vào (chấp nhận cả d/M/yyyy và dd/MM/yyyy với lịch nghiêm ngặt STRICT) */
    public static final DateTimeFormatter PARSER_NGAY = DateTimeFormatter.ofPattern("d/M/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    /** Định dạng chuẩn hiển thị (dd/MM/yyyy) */
    public static final DateTimeFormatter FORMATTER_NGAY = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    public static final Scanner sc = new Scanner(System.in);

    private String maPhieu;
    private LocalDate ngayDat;
    private LocalDate ngayNhan;
    private LocalDate ngayTra;

    private KhachHang khachHang;
    private Phong phong;

    /** Constructor mặc định – khởi tạo đối tượng rỗng */
    public PhieuDatPhong() {
        this.maPhieu = "PDP" + (System.currentTimeMillis() % 100000);
        this.ngayDat = LocalDate.now();
        this.ngayNhan = null;
        this.ngayTra = null;
        this.khachHang = new KhachHang();
        this.phong = null;
    }

    /**
     * Constructor đầy đủ sử dụng chuỗi ngày (d/M/yyyy hoặc dd/MM/yyyy).
     */
    public PhieuDatPhong(String maPhieu, String ngayDat, String ngayNhan, String ngayTra, KhachHang khachHang,
            Phong phong) {
        this.maPhieu = (maPhieu != null && !maPhieu.trim().isEmpty()) ? maPhieu.trim()
                : ("PDP" + (System.currentTimeMillis() % 100000));
        this.ngayDat = phanTichNgay(ngayDat);
        this.ngayNhan = phanTichNgay(ngayNhan);
        this.ngayTra = phanTichNgay(ngayTra);
        kiemTraLogicNgay(this.ngayDat, this.ngayNhan, this.ngayTra);

        this.khachHang = (khachHang != null) ? khachHang : new KhachHang();
        this.phong = phong;
        if (this.phong != null) {
            this.phong.setTrangThai("Da dat");
        }
    }

    /**
     * Constructor sử dụng trực tiếp đối tượng LocalDate.
     */
    public PhieuDatPhong(String maPhieu, LocalDate ngayDat, LocalDate ngayNhan, LocalDate ngayTra, KhachHang khachHang,
            Phong phong) {
        this.maPhieu = (maPhieu != null && !maPhieu.trim().isEmpty()) ? maPhieu.trim()
                : ("PDP" + (System.currentTimeMillis() % 100000));
        kiemTraLogicNgay(ngayDat, ngayNhan, ngayTra);
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.khachHang = (khachHang != null) ? khachHang : new KhachHang();
        this.phong = phong;
        if (this.phong != null) {
            this.phong.setTrangThai("Da dat");
        }
    }

    public PhieuDatPhong(String ngayDat, String ngayNhan, String ngayTra, KhachHang khachHang, Phong phong) {
        this("PDP" + (phong != null && phong.getMaPhong() != null ? phong.getMaPhong()
                : (System.currentTimeMillis() % 100000)),
                ngayDat, ngayNhan, ngayTra, khachHang, phong);
    }

    // ---------------------------------------------------------------
    // Các phương thức xử lý LocalDate & Ngoại lệ (Module4_Exception)
    // ---------------------------------------------------------------

    public static LocalDate phanTichNgay(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(raw.trim(), PARSER_NGAY);
        } catch (DateTimeParseException e) {
            throw new Module4_Exception.NgayThangKhongHopLeException(
                    "Định dạng ngày '" + raw + "' không hợp lệ (yêu cầu dd/MM/yyyy, ví dụ: 07/11/2026).");
        }
    }

    public static void kiemTraLogicNgay(LocalDate ngayDat, LocalDate ngayNhan, LocalDate ngayTra) {
        if (ngayDat != null && ngayNhan != null && ngayNhan.isBefore(ngayDat)) {
            throw new Module4_Exception.NgayThangKhongHopLeException(
                    "Ngày nhận phòng (" + dinhDangNgay(ngayNhan) + ") không được trước ngày đặt phòng (" + dinhDangNgay(ngayDat) + ").");
        }
        if (ngayNhan != null && ngayTra != null && ngayTra.isBefore(ngayNhan)) {
            throw new Module4_Exception.NgayThangKhongHopLeException(
                    "Ngày trả phòng (" + dinhDangNgay(ngayTra) + ") không được trước ngày nhận phòng (" + dinhDangNgay(ngayNhan) + ").");
        }
    }

    public static String dinhDangNgay(LocalDate date) {
        return (date != null) ? date.format(FORMATTER_NGAY) : "";
    }

    public static String chuanHoaNgay(String raw) {
        LocalDate d = phanTichNgay(raw);
        return (d != null) ? d.format(FORMATTER_NGAY) : null;
    }

    public static boolean hopLeNgay(String raw) {
        try {
            return phanTichNgay(raw) != null;
        } catch (Module4_Exception.NgayThangKhongHopLeException e) {
            return false;
        }
    }

    public static long soNgayGiua(String tuNgay, String denNgay) {
        LocalDate tu = phanTichNgay(tuNgay);
        LocalDate den = phanTichNgay(denNgay);
        if (tu == null || den == null) {
            throw new Module4_Exception.NgayThangKhongHopLeException("Ngày nhận hoặc ngày trả bị trống.");
        }
        return ChronoUnit.DAYS.between(tu, den);
    }

    private LocalDate nhapLocalDate(Scanner sc, String thongBao) {
        while (true) {
            String raw = KiemTraLoi.nhapChuoiKhongRong(sc, thongBao);
            try {
                return phanTichNgay(raw);
            } catch (Module4_Exception.NgayThangKhongHopLeException e) {
                ThongBao.inLoi(e.getMessage());
            }
        }
    }

    // ---------------------------------------------------------------
    // Nhập / Xuất (INhapXuat) – Lấy phòng & giá trực tiếp từ Module 2
    // ---------------------------------------------------------------

    public void nhapPhieuDatPhong() {
        nhap(sc);
    }

    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("NHAP THONG TIN PHIEU DAT PHONG");
        this.maPhieu = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu dat phong: ");

        ThongBao.inTieuDe("Nhap thong tin khach hang");
        String hoten = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Ho va Ten: ");
        String CCCD = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap CCCD: ");
        String sdt = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap sdt: ");
        String diachi = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Dia Chi: ");
        String loaikhach = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap loai khach (Thuong / VIP): ");
        this.khachHang = new KhachHang(hoten, CCCD, sdt, diachi, loaikhach, null);

        ThongBao.inTieuDe("Chon phong tu Module 2 (DanhSachPhong)");
        DanhSachPhong dsPhongM2 = new DanhSachPhong(sc);
        if (dsPhongM2.getSoLuong() > 0) {
            System.out.println("Danh sach phong hien co trong Module 2 (phong.text):");
            for (Phong p : dsPhongM2.getDanhSach()) {
                if (p != null) {
                    System.out.printf("  - Ma: %-6s | Loai: %-8s | Gia co ban: %,10.0f | Tong gia/dem: %,10.0f VND | Trang thai: %s%n",
                            p.getMaPhong(),
                            (p instanceof PhongVIP ? "VIP" : "Standard"),
                            p.getGiaCoBan(),
                            p.tinhGiaPhong(),
                            p.getTrangThai());
                }
            }
        }

        while (true) {
            String maPhong = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong tu danh sach Module 2 (VD: P101, P102, P201...): ");
            Phong phongM2 = dsPhongM2.timKiemTheoMaPhong(maPhong);

            if (phongM2 != null) {
                this.phong = phongM2;
                this.phong.setTrangThai("Da dat");
                ThongBao.inThanhCong(String.format(
                        "Da chon phong [%s] tu Module 2 -> Gia co ban: %,.0f VND | Don gia/dem (tinhGiaPhong): %,.0f VND",
                        this.phong.getMaPhong(), this.phong.getGiaCoBan(), this.phong.tinhGiaPhong()));
                break;
            }

            if (dsPhongM2.getSoLuong() == 0) {
                System.out.println("Chua doc duoc file phong.text, khoi tao phong moi theo lop cua Module 2:");
                int loaiPhong = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Chon loai phong (1. Standard | 2. VIP): ", 1, 2);
                Phong phongMoi = (loaiPhong == 1) ? new PhongStandard() : new PhongVIP();
                phongMoi.nhap(sc);
                phongMoi.setTrangThai("Da dat");
                this.phong = phongMoi;
                break;
            } else {
                ThongBao.inLoi("Khong tim thay ma phong '" + maPhong + "' trong danh sach phong cua Module 2! Vui long nhap lai.");
            }
        }

        this.ngayDat = nhapLocalDate(sc, "Xin hay nhap ngay dat phong (dd/MM/yyyy): ");
        while (true) {
            this.ngayNhan = nhapLocalDate(sc, "Xin hay nhap ngay nhan phong (dd/MM/yyyy): ");
            if (!this.ngayNhan.isBefore(this.ngayDat)) {
                break;
            }
            ThongBao.inLoi("Ngày nhận phòng không được trước ngày đặt phòng (" + getNgayDatStr() + ")!");
        }

        while (true) {
            this.ngayTra = nhapLocalDate(sc, "Xin hay nhap ngay tra phong du kien (dd/MM/yyyy): ");
            if (!this.ngayTra.isBefore(this.ngayNhan)) {
                break;
            }
            ThongBao.inLoi("Ngày trả phòng không được trước ngày nhận phòng (" + getNgayNhanStr() + ")!");
        }
        ThongBao.inThanhCong("Da nhap phieu dat phong.");
    }

    public void xuatPhieuDatPhong() {
        xuat();
    }

    @Override
    public void xuat() {
        ThongBao.inTieuDe("Phieu dat phong");
        System.out.println("Ma phieu dat    : " + (maPhieu != null ? maPhieu : "N/A"));
        if (khachHang != null) {
            khachHang.xuat();
        } else {
            ThongBao.inCanhBao("Chua co thong tin khach hang.");
        }
        System.out.println("Ma phong        : " + getMaPhong());
        if (phong instanceof PhongVIP) {
            PhongVIP vip = (PhongVIP) phong;
            System.out.printf("Loai phong      : Phong VIP (View: %s)%n", vip.getLoaiView());
            System.out.printf("Gia co ban      : %,.0f VND/dem%n", vip.getGiaCoBan());
            System.out.printf("Phu thu view    : %,.0f VND/dem%n", vip.tinhPhuThuView());
            System.out.printf("Phi DV dac biet : %,.0f VND/dem%n", vip.getPhiDichVuDacBiet());
        } else if (phong instanceof PhongStandard) {
            PhongStandard std = (PhongStandard) phong;
            System.out.printf("Loai phong      : Phong Standard (Giuong phu: %s)%n", (std.isCoGiuongPhu() ? "Co (+100,000 VND)" : "Khong"));
            System.out.printf("Gia co ban      : %,.0f VND/dem%n", std.getGiaCoBan());
        } else {
            System.out.println("Loai phong      : Phong");
        }
        System.out.printf("Don gia/dem(M2) : %,.0f VND/dem%n", getGiaPhongMotDem());
        System.out.println("Ngay dat phong  : " + getNgayDatStr());
        System.out.println("Ngay nhan phong : " + getNgayNhanStr());
        System.out.println("Ngay tra phong  : " + getNgayTraStr());
        System.out.println("So dem luu tru  : " + tinhSoNgayLuuTru() + " dem");
        System.out.printf("Tien phong      : %,.0f VND%n", tinhTienPhong());
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------

    public LocalDate getNgayDatLocalDate() {
        return ngayDat;
    }

    public LocalDate getNgayNhanLocalDate() {
        return ngayNhan;
    }

    public LocalDate getNgayTraLocalDate() {
        return ngayTra;
    }

    public String getNgayDat() {
        return getNgayDatStr();
    }

    public String getNgayNhan() {
        return getNgayNhanStr();
    }

    public String getNgayTra() {
        return getNgayTraStr();
    }

    public String getNgayDatStr() {
        return dinhDangNgay(ngayDat);
    }

    public String getNgayNhanStr() {
        return dinhDangNgay(ngayNhan);
    }

    public String getNgayTraStr() {
        return dinhDangNgay(ngayTra);
    }

    public void setNgayDat(String ngayDat) {
        LocalDate d = phanTichNgay(ngayDat);
        kiemTraLogicNgay(d, this.ngayNhan, this.ngayTra);
        this.ngayDat = d;
    }

    public void setNgayNhan(String ngayNhan) {
        LocalDate d = phanTichNgay(ngayNhan);
        kiemTraLogicNgay(this.ngayDat, d, this.ngayTra);
        this.ngayNhan = d;
    }

    public void setNgayTra(String ngayTra) {
        LocalDate d = phanTichNgay(ngayTra);
        kiemTraLogicNgay(this.ngayDat, this.ngayNhan, d);
        this.ngayTra = d;
    }

    public KhachHang getKhachHang() {
        return this.khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            throw new Module4_Exception.PhieuDatPhongException("Thông tin khách hàng không được để trống (null).");
        }
        this.khachHang = khachHang;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            throw new Module4_Exception.PhieuDatPhongException("Mã phiếu đặt phòng không được để trống.");
        }
        this.maPhieu = maPhieu.trim();
    }

    public Phong getPhong() {
        return this.phong;
    }

    public void setPhong(Phong phong) {
        if (phong == null) {
            throw new Module4_Exception.PhongKhongKhaDungException("Phòng gán cho phiếu đặt không được null.");
        }
        this.phong = phong;
        this.phong.setTrangThai("Da dat");
    }

    public String getMaPhong() {
        return (phong != null && phong.getMaPhong() != null) ? phong.getMaPhong() : "";
    }

    public long tinhSoNgayLuuTru() {
        if (ngayNhan == null || ngayTra == null) {
            return 1;
        }
        long soNgay = ChronoUnit.DAYS.between(ngayNhan, ngayTra);
        return (soNgay <= 0) ? 1 : soNgay;
    }

    /**
     * Lấy đơn giá phòng 1 đêm trực tiếp từ phương thức đa hình phong.tinhGiaPhong() của Module 2.
     */
    public double getGiaPhongMotDem() {
        return (phong != null) ? phong.tinhGiaPhong() : 0.0;
    }

    public double tinhTienPhong() {
        return getGiaPhongMotDem() * tinhSoNgayLuuTru();
    }
}
