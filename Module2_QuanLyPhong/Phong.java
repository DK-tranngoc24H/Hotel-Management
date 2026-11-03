import java.util.Scanner;

/**
 * Lớp cha trừu tượng đại diện cho một căn phòng trong khách sạn.
 * Kế thừa các phương thức chuẩn từ INhapXuat và chứa thuộc tính TienNghiPhong.
 */
public abstract class Phong implements INhapXuat {
    private String maPhong;
    private double giaCoBan;
    private String trangThai; // "Trong", "Dang o", "Da dat"
    private TienNghiPhong tienNghi; // Quan hệ Has-A (Một phòng CÓ một bộ tiện nghi)

    // Constructor mặc định
    public Phong() {
        this.maPhong = "";
        this.giaCoBan = 0.0;
        this.trangThai = "Trong";
        this.tienNghi = new TienNghiPhong();
    }

    // Constructor đầy đủ tham số
    public Phong(String maPhong, double giaCoBan, String trangThai, TienNghiPhong tienNghi) {
        this.maPhong = maPhong;
        setGiaCoBan(giaCoBan);
        this.trangThai = trangThai;
        this.tienNghi = (tienNghi != null) ? tienNghi : new TienNghiPhong();
    }

    // Getters và Setters chuẩn camelCase
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public double getGiaCoBan() {
        return giaCoBan;
    }

    // Có kiểm tra dữ liệu hợp lệ (giá không được âm)
    public void setGiaCoBan(double giaCoBan) {
        if (giaCoBan >= 0) {
            this.giaCoBan = giaCoBan;
        } else {
            ThongBao.inCanhBao("Gia co ban phai >= 0. Mac dinh gan = 0.");
            this.giaCoBan = 0.0;
        }
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public TienNghiPhong getTienNghi() {
        return tienNghi;
    }

    public void setTienNghi(TienNghiPhong tienNghi) {
        this.tienNghi = tienNghi;
    }

    // Kiểm tra phòng còn trống hay không (hỗ trợ cả tiếng Việt có dấu)
    public boolean conTrong() {
        return "trong".equalsIgnoreCase(this.trangThai) || "trống".equalsIgnoreCase(this.trangThai);
    }

    // Phương thức trừu tượng: mỗi loại phòng con sẽ tự định nghĩa cách tính giá riêng
    public abstract double tinhGiaPhong();

    @Override
    public void nhap(Scanner sc) {
        // Áp dụng lớp KiemTraLoi để chống nhập rỗng và số âm
        this.maPhong = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong: ");
        this.giaCoBan = KiemTraLoi.nhapSoThucDuong(sc, "Nhap gia co ban: ");
        this.trangThai = KiemTraLoi.nhapTrangThaiPhong(sc);

        System.out.println("Nhap thong tin tien nghi cho phong nay:");
        if (this.tienNghi == null) {
            this.tienNghi = new TienNghiPhong();
        }
        this.tienNghi.nhap(sc);
    }

    @Override
    public void xuat() {
        System.out.printf("Ma phong: %-8s | Gia co ban: %-10.2f | Trang thai: %-8s",
                maPhong, giaCoBan, trangThai);
    }

    @Override
    public String toString() {
        return String.format("Ma: %s | Gia co ban: %.2f | Trang thai: %s | %s",
                maPhong, giaCoBan, trangThai, (tienNghi != null ? tienNghi.toString() : "Chua co tien nghi"));
    }
}
