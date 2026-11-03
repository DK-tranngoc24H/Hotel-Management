import java.util.Scanner;

/**
 * Lớp đại diện cho phòng Standard (phòng tiêu chuẩn).
 * Kế thừa từ Phong và bổ sung thuộc tính giường phụ.
 */
public class PhongStandard extends Phong {
    private static final double PHU_THU_GIUONG_PHU = 100000.0;
    private boolean coGiuongPhu;

    // Constructor mặc định
    public PhongStandard() {
        super();
        this.coGiuongPhu = false;
        // Phong Standard mac dinh co: Tivi, Dieu hoa, Binh nong lanh, Wifi (Khong co Tu lanh)
        setTienNghi(new TienNghiPhong("TN-STD", true, false, true, true, true, "Tot"));
    }

    // Constructor đầy đủ tham số không truyền tiện nghi
    public PhongStandard(String maPhong, double giaCoBan, String trangThai, boolean coGiuongPhu) {
        super(maPhong, giaCoBan, trangThai, new TienNghiPhong("TN-STD", true, false, true, true, true, "Tot"));
        this.coGiuongPhu = coGiuongPhu;
    }

    // Constructor đầy đủ tham số kèm tiện nghi
    public PhongStandard(String maPhong, double giaCoBan, String trangThai, boolean coGiuongPhu, TienNghiPhong tienNghi) {
        super(maPhong, giaCoBan, trangThai, tienNghi);
        this.coGiuongPhu = coGiuongPhu;
    }

    public boolean isCoGiuongPhu() {
        return coGiuongPhu;
    }

    public void setCoGiuongPhu(boolean coGiuongPhu) {
        this.coGiuongPhu = coGiuongPhu;
    }

    @Override
    public double tinhGiaPhong() {
        return getGiaCoBan() + (coGiuongPhu ? PHU_THU_GIUONG_PHU : 0.0);
    }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        // Tận dụng lớp KiemTraLoi để kiểm tra nhập co/khong gọn gàng
        this.coGiuongPhu = KiemTraLoi.nhapCoKhong(sc, "Co them giuong phu khong? (co/khong): ");
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | Loai: Standard | Giuong phu: %-5s | Tong gia: %-10.2f\n",
                (coGiuongPhu ? "Co" : "Khong"), tinhGiaPhong());
        // Xuất chi tiết tiện nghi của phòng
        if (getTienNghi() != null) {
            getTienNghi().xuat();
        }
    }
}
