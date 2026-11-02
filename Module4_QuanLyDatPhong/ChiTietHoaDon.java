import java.util.ArrayList;

/**
 * Lớp ChiTietHoaDon – quản lý danh sách các dịch vụ mà khách đã sử dụng.
 * Cung cấp chức năng thêm dịch vụ, tính tổng tiền phụ thu, và xuất danh sách.
 */
public class ChiTietHoaDon {

    // ---------------------------------------------------------------
    // Thuộc tính
    // ---------------------------------------------------------------
    private final ThongTinKhachHang  khach;            // Khách hàng liên kết
    private final ArrayList<DichVu>  danhSachDichVu;   // Danh sách dịch vụ đã dùng
    private double                   tongTienPhuThu;   // Tổng tiền dịch vụ

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------

    /**
     * Khởi tạo chi tiết hóa đơn từ phiếu đặt phòng.
     *
     * @param phieu Phiếu đặt phòng của khách
     */
    public ChiTietHoaDon(PhieuDatPhong phieu) {
        this.khach           = phieu.getThongTinKhachHang();
        this.danhSachDichVu  = new ArrayList<>();
        this.tongTienPhuThu  = 0;
    }

    // ---------------------------------------------------------------
    // Thêm / Xoá dịch vụ
    // ---------------------------------------------------------------

    /**
     * Thêm một dịch vụ vào danh sách và cập nhật tổng tiền.
     *
     * @param tenDichVu Tên dịch vụ
     * @param donGia    Đơn giá (VNĐ)
     * @param soLuong   Số lượng sử dụng
     */
    public void themDichVu(String tenDichVu, double donGia, int soLuong) {
        DichVu dv = new DichVu(tenDichVu, donGia, soLuong);
        danhSachDichVu.add(dv);
        tongTienPhuThu += dv.tinhTien(); // Cập nhật tổng ngay khi thêm
    }

    /**
     * Xoá dịch vụ theo tên (xoá lần xuất hiện đầu tiên).
     *
     * @param tenDichVu Tên dịch vụ cần xoá
     * @return true nếu xoá thành công, false nếu không tìm thấy
     */
    public boolean xoaDichVu(String tenDichVu) {
        for (DichVu dv : danhSachDichVu) {
            if (dv.getTenDichVu().equalsIgnoreCase(tenDichVu)) {
                tongTienPhuThu -= dv.tinhTien();
                danhSachDichVu.remove(dv);
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------
    // Tính tổng tiền
    // ---------------------------------------------------------------

    /**
     * Tính lại tổng tiền phụ thu từ toàn bộ danh sách dịch vụ.
     * Gọi phương thức này nếu bạn chỉnh sửa dịch vụ bên ngoài class.
     */
    public void tinhTongTienPhuThu() {
        tongTienPhuThu = 0;
        for (DichVu dv : danhSachDichVu) {
            tongTienPhuThu += dv.tinhTien();
        }
    }

    // ---------------------------------------------------------------
    // Xuất danh sách dịch vụ
    // ---------------------------------------------------------------

    /** In ra danh sách các dịch vụ đã sử dụng và tổng tiền */
    public void xuatDichVu() {
        System.out.println("========== CHI TIET DICH VU ==========");
        System.out.println("Khach hang: " + khach.getTen());

        if (danhSachDichVu.isEmpty()) {
            System.out.println("(Khong su dung dich vu nao)");
        } else {
            System.out.println("So luong dich vu: " + danhSachDichVu.size());
            for (DichVu dv : danhSachDichVu) {
                System.out.println("  " + dv);
            }
            System.out.printf("Tong tien dich vu: %,.0f VND%n", tongTienPhuThu);
        }
        System.out.println("=======================================");
    }

    // ---------------------------------------------------------------
    // Getter
    // ---------------------------------------------------------------
    public double              getTongTienPhuThu() { return tongTienPhuThu; }
    public ArrayList<DichVu>   getDanhSachDichVu() { return danhSachDichVu; }
    public int                 getSoLuongDichVu()  { return danhSachDichVu.size(); }
}