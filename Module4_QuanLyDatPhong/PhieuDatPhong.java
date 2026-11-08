import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lớp PhieuDatPhong – quản lý thông tin đặt phòng của khách hàng.
 * Khách lấy từ KHACHHANG (module 1), phòng lấy từ Phong (module 2).
 */
public class PhieuDatPhong implements INhapXuat {

    /** dd/MM/yyyy hoặc d/M/yyyy — nhóm 1 ngày, nhóm 2 tháng, nhóm 3 năm. */
    public static final Pattern MAU_NGAY = Pattern.compile("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$");
    public static final Scanner sc = new Scanner(System.in);

    private String maPhieu;
    private String ngayDat;
    private String ngayNhan;
    private String ngayTra;

    private KHACHHANG khach;
    private Phong phong;

    /** Constructor mặc định – khởi tạo đối tượng rỗng */
    public PhieuDatPhong() {
        this.maPhieu = "PDP" + (System.currentTimeMillis() % 100000);
        this.ngayDat = null;
        this.ngayNhan = null;
        this.ngayTra = null;
        this.khach = new KHACHHANG();
        this.phong = null;
    }

    /**
     * @param maPhieu  Mã phiếu đặt phòng
     * @param ngayDat  Ngày đặt phòng (d/M/yyyy)
     * @param ngayNhan Ngày nhận phòng (d/M/yyyy)
     * @param ngayTra  Ngày trả phòng dự kiến (d/M/yyyy)
     * @param khach    Khách hàng (module 1)
     * @param phong    Phòng được đặt; mã phòng lấy từ phong.getMaPhong()
     */
    public PhieuDatPhong(String maPhieu, String ngayDat, String ngayNhan, String ngayTra, KHACHHANG khach,
            Phong phong) {
        this.maPhieu = (maPhieu != null && !maPhieu.trim().isEmpty()) ? maPhieu.trim()
                : ("PDP" + (System.currentTimeMillis() % 100000));
        this.ngayDat = chuanHoaNgay(ngayDat);
        this.ngayNhan = chuanHoaNgay(ngayNhan);
        this.ngayTra = chuanHoaNgay(ngayTra);
        this.khach = (khach != null) ? khach : new KHACHHANG();
        this.phong = phong;
        if (this.phong != null) {
            this.phong.setTrangThai("Da dat");
        }
    }

    /**
     * @param ngayDat  Ngày đặt phòng (d/M/yyyy)
     * @param ngayNhan Ngày nhận phòng (d/M/yyyy)
     * @param ngayTra  Ngày trả phòng dự kiến (d/M/yyyy)
     * @param khach    Khách hàng (module 1)
     * @param phong    Phòng được đặt; mã phòng lấy từ phong.getMaPhong()
     */
    public PhieuDatPhong(String ngayDat, String ngayNhan, String ngayTra, KHACHHANG khach, Phong phong) {
        this("PDP" + (phong != null && phong.getMaPhong() != null ? phong.getMaPhong()
                : (System.currentTimeMillis() % 100000)),
                ngayDat, ngayNhan, ngayTra, khach, phong);
    }

    /** Nhập đầy đủ thông tin phiếu đặt phòng từ bàn phím */
    public void nhapPhieuDatPhong() {
        nhap(sc);
    }

    @Override
    public void nhap(Scanner sc) {
        ThongBao.inTieuDe("NHAP THONG TIN PHIEU DAT PHONG");
        this.maPhieu = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu dat phong: ");

        ThongBao.inTieuDe("Nhap thong tin khach hang");
        String hoten = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Ho va Ten: ");
        String cccd = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap CCCD: ");
        String sdt = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap sdt: ");
        String diachi = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Dia Chi: ");
        String loaikhach = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap loai khach (Thuong / VIP / Doanh nghiep): ");
        this.khach = new KHACHHANG(hoten, cccd, sdt, diachi, loaikhach, null);

        ThongBao.inTieuDe("Chon phong");
        System.out.println("1. Phong Standard");
        System.out.println("2. Phong VIP");
        int loaiPhong = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Lua chon loai phong (1-2): ", 1, 2);
        String maPhong = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong: ");
        double giaCoBan = KiemTraLoi.nhapSoThucDuong(sc, "Nhap gia phong co ban moi dem (0 de dung mac dinh): ");
        if (giaCoBan == 0) {
            giaCoBan = (loaiPhong == 2) ? 1_000_000.0 : 500_000.0;
        }

        if (loaiPhong == 2) {
            String loaiView = KiemTraLoi.nhapLoaiView(sc);
            double phiDacBiet = KiemTraLoi.nhapSoThucDuong(sc, "Nhap phi dich vu dac biet (0 neu khong co): ");
            this.phong = new PhongVIP(maPhong, giaCoBan, "Da dat", loaiView, phiDacBiet);
        } else {
            boolean coGiuongPhu = KiemTraLoi.nhapCoKhong(sc, "Co them giuong phu khong? (co/khong): ");
            this.phong = new PhongStandard(maPhong, giaCoBan, "Da dat", coGiuongPhu);
        }

        this.ngayDat = nhapNgay(sc, "Xin hay nhap ngay dat phong (d/M/yyyy): ");
        this.ngayNhan = nhapNgay(sc, "Xin hay nhap ngay nhan phong (d/M/yyyy): ");
        this.ngayTra = nhapNgay(sc, "Xin hay nhap ngay tra phong du kien (d/M/yyyy): ");
        while (soNgayGiua(this.ngayNhan, this.ngayTra) < 0) {
            ThongBao.inLoi("Ngay tra phong khong the truoc ngay nhan phong!");
            this.ngayTra = nhapNgay(sc, "Xin hay nhap lai ngay tra phong (d/M/yyyy): ");
        }
        ThongBao.inThanhCong("Da nhap phieu dat phong.");
    }

    /** Xuất thông tin phiếu đặt phòng ra màn hình */
    public void xuatPhieuDatPhong() {
        xuat();
    }

    @Override
    public void xuat() {
        ThongBao.inTieuDe("Phieu dat phong");
        System.out.println("Ma phieu dat    : " + (maPhieu != null ? maPhieu : "N/A"));
        if (khach != null) {
            khach.xuat();
        } else {
            ThongBao.inCanhBao("Chua co thong tin khach hang.");
        }
        System.out.println("Ma phong        : " + getMaPhong());
        System.out.println("Loai phong      : " + (phong instanceof PhongVIP ? "Phong VIP"
                : (phong instanceof PhongStandard ? "Phong Standard" : "Phong")));
        System.out.printf("Don gia phong   : %,.0f VND/dem%n", getGiaPhongMotDem());
        System.out.println("Ngay dat phong  : " + (ngayDat != null ? ngayDat : ""));
        System.out.println("Ngay nhan phong : " + (ngayNhan != null ? ngayNhan : ""));
        System.out.println("Ngay tra phong  : " + (ngayTra != null ? ngayTra : ""));
        System.out.println("So dem luu tru  : " + tinhSoNgayLuuTru() + " dem");
        System.out.printf("Tien phong      : %,.0f VND%n", tinhTienPhong());
    }

    private String nhapNgay(Scanner sc, String thongBao) {
        while (true) {
            String raw = KiemTraLoi.nhapChuoiKhongRong(sc, thongBao);
            if (hopLeNgay(raw)) {
                return raw;
            }
            ThongBao.inLoi("Ngay khong hop le. Vi du: 7/11/2026");
        }
    }

    /** Giữ chuỗi ngày nếu regex và lịch khớp; chuỗi rỗng trả về null. */
    public static String chuanHoaNgay(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        String ngay = raw.trim();
        if (!hopLeNgay(ngay)) {
            throw new IllegalArgumentException("Ngay khong hop le: " + raw);
        }
        return ngay;
    }

    /**
     * Regex tách ngày/tháng/năm, rồi kiểm tra số ngày trong tháng (kể cả năm
     * nhuận).
     */
    public static boolean hopLeNgay(String raw) {
        return tachNgay(raw) != null;
    }

    /**
     * Số ngày từ ngày nhận đến ngày trả.
     * Regex lấy d/m/y, rồi đổi mỗi ngày thành số ngày Julius để trừ.
     */
    public static long soNgayGiua(String tuNgay, String denNgay) {
        int[] tu = tachNgay(tuNgay);
        int[] den = tachNgay(denNgay);
        if (tu == null || den == null) {
            throw new IllegalArgumentException("Ngay khong hop le, khong tinh duoc so ngay luu tru.");
        }
        return soNgayJulius(den[0], den[1], den[2]) - soNgayJulius(tu[0], tu[1], tu[2]);
    }

    private static int[] tachNgay(String raw) {
        if (raw == null) {
            return null;
        }
        Matcher khop = MAU_NGAY.matcher(raw.trim());
        if (!khop.matches()) {
            return null;
        }
        int ngay = Integer.parseInt(khop.group(1));
        int thang = Integer.parseInt(khop.group(2));
        int nam = Integer.parseInt(khop.group(3));
        if (thang < 1 || thang > 12 || ngay < 1 || ngay > soNgayTrongThang(thang, nam)) {
            return null;
        }
        return new int[] { ngay, thang, nam };
    }

    private static int soNgayTrongThang(int thang, int nam) {
        switch (thang) {
            case 2:
                return namNhuan(nam) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private static boolean namNhuan(int nam) {
        return (nam % 4 == 0 && nam % 100 != 0) || (nam % 400 == 0);
    }

    /** Công thức Julius: biến một ngày lịch thành số ngày để trừ hai mốc. */
    private static long soNgayJulius(int ngay, int thang, int nam) {
        int a = (14 - thang) / 12;
        int y = nam + 4800 - a;
        int m = thang + 12 * a - 3;
        return ngay + (153L * m + 2) / 5 + 365L * y + y / 4 - y / 100 + y / 400 - 32045;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public String getNgayDatStr() {
        return ngayDat != null ? ngayDat : "";
    }

    public String getNgayNhanStr() {
        return ngayNhan != null ? ngayNhan : "";
    }

    public String getNgayTraStr() {
        return ngayTra != null ? ngayTra : "";
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = chuanHoaNgay(ngayDat);
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = chuanHoaNgay(ngayNhan);
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = chuanHoaNgay(ngayTra);
    }

    public KHACHHANG getKhachHang() {
        return this.khach;
    }

    public void setKhachHang(KHACHHANG khach) {
        this.khach = khach;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public Phong getPhong() {
        return this.phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
        if (this.phong != null) {
            this.phong.setTrangThai("Da dat");
        }
    }

    /** Mã phòng lấy từ đối tượng Phong (module 2). */
    public String getMaPhong() {
        return (phong != null && phong.getMaPhong() != null) ? phong.getMaPhong() : "";
    }

    /**
     * Tính số đêm lưu trú giữa ngày nhận và ngày trả.
     */
    public long tinhSoNgayLuuTru() {
        if (ngayNhan == null || ngayTra == null)
            return 1;
        try {
            long soNgay = soNgayGiua(this.ngayNhan, this.ngayTra);
            return (soNgay <= 0) ? 1 : soNgay;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Lấy đơn giá phòng 1 đêm từ đối tượng Phong (Module 2).
     */
    public double getGiaPhongMotDem() {
        if (phong != null && phong.tinhGiaPhong() > 0) {
            return phong.tinhGiaPhong();
        }
        return 500_000.0; // Mặc định 500.000 VNĐ/đêm nếu chưa cấu hình
    }

    /**
     * Tính tổng chi phí tiền phòng = Đơn giá phòng/đêm × Số đêm lưu trú.
     */
    public double tinhTienPhong() {
        return getGiaPhongMotDem() * tinhSoNgayLuuTru();
    }

}
