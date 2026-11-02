// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.*;

/**
 * Lớp PhieuDatPhong – quản lý thông tin đặt phòng của khách hàng.
 * Bao gồm: ngày đặt, ngày nhận, ngày trả dự kiến, và thông tin khách.
 */
public class PhieuDatPhong {

    // Định dạng ngày dùng chung: dd/MM/yyyy
    //public static final DateTimeFormatter DINH_DANG_NGAY = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static final Scanner sc = new Scanner(System.in);

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    /*private LocalDate ngayDat;
    private LocalDate ngayNhan;
    private LocalDate ngayTra;*/
    private String ngayDat;
    private String ngayNhan;
    private String ngayTra;

    private ThongTinKhachHang khach;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /** Constructor mặc định – khởi tạo đối tượng rỗng */
    public PhieuDatPhong() {
        ngayDat = null;
        ngayNhan = null;
        ngayTra = null;
        this.khach = new ThongTinKhachHang();
    }

    /**
     * 
     * @param ngayDat Ngay dat phong (DD/MM/YYYY)
     * @param ngayNhan Ngay nhan phong (DD/MM/YYYY)
     * @param ngayTra Ngay tra phong du kien (DD/MM/YYYY)
     * @param khach Chua thong tin khach hang
     */
    public PhieuDatPhong(String ngayDat, String ngayNhan, String ngayTra, ThongTinKhachHang khach) {
        this.ngayDat  = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTra  = ngayTra;
        this.khach    = khach;
    }

    // ---------------------------------------------------------------
    // Nhập / Xuất dữ liệu
    // ---------------------------------------------------------------

    /** Nhập đầy đủ thông tin phiếu đặt phòng từ bàn phím */
    public void nhapPhieuDatPhong() {
        this.khach = new ThongTinKhachHang();
        this.khach.nhapThongTin(sc); // Truyền Scanner chung, tránh xung đột System.in

        System.out.print("Xin hay nhap ngay dat phong (d/M/yyyy): ");
        this.ngayDat = sc.nextLine();

        System.out.print("Xin hay nhap ngay nhan phong (d/M/yyyy): ");
        this.ngayNhan = sc.nextLine();

        System.out.print("Xin hay nhap ngay tra phong du kien (d/M/yyyy): ");
        this.ngayTra = sc.nextLine();
    }

    /** Xuất thông tin phiếu đặt phòng ra màn hình */
    public void xuatPhieuDatPhong() {
        System.out.println("===== PHIEU DAT PHONG =====");
        this.khach.xuatThongTin();
        System.out.println("Ngay dat phong  : " + ngayDat.format(DINH_DANG_NGAY));
        System.out.println("Ngay nhan phong : " + ngayNhan.format(DINH_DANG_NGAY));
        System.out.println("Ngay tra phong  : " + ngayTra.format(DINH_DANG_NGAY));
        System.out.println("===========================");
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------

    public LocalDate getNgayDat()  { return ngayDat; }
    public LocalDate getNgayNhan() { return ngayNhan; }
    public LocalDate getNgayTra()  { return ngayTra; }

    /** Trả về ngày dưới dạng chuỗi để tương thích với các class khác */
    public String getNgayDatStr()  { return ngayDat  != null ? ngayDat.format(DINH_DANG_NGAY)  : ""; }
    public String getNgayNhanStr() { return ngayNhan != null ? ngayNhan.format(DINH_DANG_NGAY) : ""; }
    public String getNgayTraStr()  { return ngayTra  != null ? ngayTra.format(DINH_DANG_NGAY)  : ""; }

    public void setNgayDat(String ngayDat)   { this.ngayDat  = LocalDate.parse(ngayDat,  DINH_DANG_NGAY); }
    public void setNgayNhan(String ngayNhan) { this.ngayNhan = LocalDate.parse(ngayNhan, DINH_DANG_NGAY); }
    public void setNgayTra(String ngayTra)   { this.ngayTra  = LocalDate.parse(ngayTra,  DINH_DANG_NGAY); }

    public ThongTinKhachHang getThongTinKhachHang() { return this.khach; }

    // ---------------------------------------------------------------
    // Main – dùng để kiểm tra nhanh
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        PhieuDatPhong phieu = new PhieuDatPhong();
        phieu.nhapPhieuDatPhong();
        phieu.xuatPhieuDatPhong();

        ChiTietHoaDon chiTiet = new ChiTietHoaDon(phieu);
        chiTiet.themDichVu("Nuoc suoi", 10_000, 2);
        chiTiet.themDichVu("Giat la", 30_000, 1);
        chiTiet.xuatDichVu();

        HoaDon hoaDon = new HoaDon(chiTiet.getTongTienPhuThu(), 0, phieu);
        hoaDon.xuatHoaDon();

        PhuongThucThanhToan tt = new PhuongThucThanhToan(PhuongThucThanhToan.LoaiThanhToan.TIEN_MAT, hoaDon);
        tt.thucHienThanhToan();
    }
}
