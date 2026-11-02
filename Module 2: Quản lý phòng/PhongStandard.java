import java.util.Scanner;

public class PhongStandard extends Phong {
    private static final double Phuthugiuonphu = 100000;
    private boolean cogiuongphu;

    public PhongStandard() {
        super();
    }

    public PhongStandard(String maphong, double giacoban, String trangthai, TienNghiPhong tienNghi,
            boolean cogiuongphu) {
        super(maphong, giacoban, trangthai, tienNghi);
        this.cogiuongphu = cogiuongphu;
    }

    public boolean getcogiuongphu() {
        return cogiuongphu;
    }

    public void setcogiuongphu(boolean cogiuongphu) {
        this.cogiuongphu = cogiuongphu;
    }

    @Override
    public double tinhgiaphong() {
        return getgiacoban() + (cogiuongphu ? Phuthugiuonphu : 0);
    }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);

        while (true) {
            System.out.print("Co them giuong phu khong? (co/khong): ");
            String traLoi = sc.nextLine().trim();

            if (traLoi.equalsIgnoreCase("co") || traLoi.equalsIgnoreCase("có")
                    || traLoi.equalsIgnoreCase("true") || traLoi.equals("1")) {
                cogiuongphu = true;
                break;
            }
            if (traLoi.equalsIgnoreCase("khong") || traLoi.equalsIgnoreCase("không")
                    || traLoi.equalsIgnoreCase("false") || traLoi.equals("0")) {
                cogiuongphu = false;
                break;
            }

            System.out.println("Gia tri khong hop le. Hay nhap co/khong.");
        }
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | Loai: Standard | Giuong phu: %-5s | Tong gia: %.2f\n",
                (cogiuongphu ? "Co" : "Khong"), tinhgiaphong());
    }
}
