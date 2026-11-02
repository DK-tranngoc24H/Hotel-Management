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
    }

    // Constructor đầy đủ tham số không có tiện nghi
    public PhongStandard(String maPhong, double giaCoBan, String trangThai, boolean coGiuongPhu) {
        super(maPhong, giaCoBan, trangThai, null);
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

        // Vòng lặp kiểm tra nhập lựa chọn giường phụ hợp lệ
        while (true) {
            System.out.print("Co them giuong phu khong? (co/khong): ");
            String traLoi = sc.nextLine().trim();

            if (traLoi.equalsIgnoreCase("co") || traLoi.equalsIgnoreCase("có")
                    || traLoi.equalsIgnoreCase("true") || traLoi.equals("1")) {
                this.coGiuongPhu = true;
                break;
            }
            if (traLoi.equalsIgnoreCase("khong") || traLoi.equalsIgnoreCase("không")
                    || traLoi.equalsIgnoreCase("false") || traLoi.equals("0")) {
                this.coGiuongPhu = false;
                break;
            }

            System.out.println("Gia tri khong hop le! Vui long nhap co/khong.");
        }
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
