import java.util.Scanner;

public class PhongVIP extends Phong {
    private String loaiView;
    private double phiDichVuDacBiet;

    public PhongVIP() {
        super();
    }

    public PhongVIP(String maphong, double giacoban, String trangthai, TienNghiPhong tienNghi, String loaiView,
            double phiDichVuDacBiet) {
        super(maphong, giacoban, trangthai, tienNghi);
        this.loaiView = loaiView;
        this.phiDichVuDacBiet = phiDichVuDacBiet;
    }

    public String getloaiView() {
        return loaiView;
    }

    public double getphiDichVuDacBiet() {
        return phiDichVuDacBiet;
    }

    public void setloaiView(String loaiView) {
        this.loaiView = loaiView;
    }

    public void setphiDichVuDacBiet(double phiDichVuDacBiet) {
        this.phiDichVuDacBiet = phiDichVuDacBiet;
    }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.println("chon loai view(bien/thanhpho)");
        loaiView = sc.nextLine();
        System.out.println("gia phi dich vu dac biet");
        phiDichVuDacBiet = sc.nextDouble();
        sc.nextLine();
    }

    @Override
    public double tinhgiaphong() {
        double phuthuview = 0.0;
        if ("bien".equalsIgnoreCase(loaiView) || "biển".equalsIgnoreCase(loaiView)) {
            phuthuview = 100000;
        } else if ("thanhpho".equalsIgnoreCase(loaiView) || "thành phố".equalsIgnoreCase(loaiView)) {
            phuthuview = 500000;
        }
        return getgiacoban() + phuthuview + phiDichVuDacBiet;
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | Loai: VIP      | View: %-10s | Phi VIP: %-10.2f | Tong gia: %.2f\n",
                loaiView, phiDichVuDacBiet, tinhgiaphong());
    }
}
