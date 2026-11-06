
import java.util.Scanner;

public class DichVuSpaGiaiTri extends DichVu {
    private String loaiHoatDong; // Massage, Hồ bơi, Karaoke, Xông hơi
    private int thoiGianSuDung;  // Tính bằng phút

    public DichVuSpaGiaiTri() {
        super();
    }

    public DichVuSpaGiaiTri(String maDV, String tenDV, double donGia, String loaiHoatDong, int thoiGianSuDung) {
        super(maDV, tenDV, donGia);
        this.loaiHoatDong = loaiHoatDong;
        this.thoiGianSuDung = thoiGianSuDung;
    }

    @Override
    public void nhap() {
        super.nhap();
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập hoạt động (Massage/Hồ bơi/Karaoke/Xông hơi): ");
        loaiHoatDong = sc.nextLine();
        System.out.println("Nhập thời gian sử dụng (phút): ");
        thoiGianSuDung = Integer.parseInt(sc.nextLine());
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" Hoạt động: " + loaiHoatDong + " | Thời gian: " + thoiGianSuDung + " phút");
    }
}
