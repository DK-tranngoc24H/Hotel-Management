import java.util.Scanner;

/**
 * Lớp đại diện cho phòng VIP (phòng cao cấp).
 * Kế thừa từ Phong, bổ sung view nhìn và phí dịch vụ đặc biệt.
 */
public class PhongVIP extends Phong {
    private static final double PHU_THU_VIEW_BIEN = 100000.0;
    private static final double PHU_THU_VIEW_THANH_PHO = 50000.0;

    private String loaiView; // "Bien" (hoặc "Biển"), "ThanhPho", "SanVuon"...
    private double phiDichVuDacBiet;

    // Constructor mặc định
    public PhongVIP() {
        super();
        this.loaiView = "ThanhPho";
        this.phiDichVuDacBiet = 0.0;
    }

    // Constructor tham số cơ bản
    public PhongVIP(String maPhong, double giaCoBan, String trangThai, String loaiView, double phiDichVuDacBiet) {
        super(maPhong, giaCoBan, trangThai, null);
        this.loaiView = loaiView;
        setPhiDichVuDacBiet(phiDichVuDacBiet);
    }

    // Constructor đầy đủ tham số kèm tiện nghi
    public PhongVIP(String maPhong, double giaCoBan, String trangThai, String loaiView, double phiDichVuDacBiet, TienNghiPhong tienNghi) {
        super(maPhong, giaCoBan, trangThai, tienNghi);
        this.loaiView = loaiView;
        setPhiDichVuDacBiet(phiDichVuDacBiet);
    }

    public String getLoaiView() {
        return loaiView;
    }

    public void setLoaiView(String loaiView) {
        this.loaiView = loaiView;
    }

    public double getPhiDichVuDacBiet() {
        return phiDichVuDacBiet;
    }

    public void setPhiDichVuDacBiet(double phiDichVuDacBiet) {
        this.phiDichVuDacBiet = (phiDichVuDacBiet >= 0) ? phiDichVuDacBiet : 0.0;
    }

    // Tính phụ thu dựa trên loại view
    public double tinhPhuThuView() {
        if (loaiView == null) return 0.0;
        if (loaiView.equalsIgnoreCase("bien") || loaiView.equalsIgnoreCase("biển")) {
            return PHU_THU_VIEW_BIEN;
        } else if (loaiView.equalsIgnoreCase("thanhpho") || loaiView.equalsIgnoreCase("thành phố")
                || loaiView.equalsIgnoreCase("thanh pho")) {
            return PHU_THU_VIEW_THANH_PHO;
        }
        return 0.0;
    }

    @Override
    public double tinhGiaPhong() {
        return getGiaCoBan() + tinhPhuThuView() + phiDichVuDacBiet;
    }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);

        System.out.print("Nhap loai view (Bien / Thanh pho / San vuon): ");
        this.loaiView = sc.nextLine().trim();

        System.out.print("Nhap phi dich vu dac biet: ");
        double phi = sc.nextDouble();
        sc.nextLine(); // Chống trôi dòng
        setPhiDichVuDacBiet(phi);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | Loai: VIP      | View: %-10s | Phi VIP: %-10.2f | Tong gia: %-10.2f\n",
                loaiView, phiDichVuDacBiet, tinhGiaPhong());
        // Xuất tiện nghi đi kèm
        if (getTienNghi() != null) {
            getTienNghi().xuat();
        }
    }
}
