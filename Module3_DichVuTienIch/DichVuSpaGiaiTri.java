package BT_quanLyKhachSan;

public class DichVuSpaGiaiTri extends DichVu {
    private String loaiHoatDong;   // VD: Massage, Hồ bơi, Karaoke, Xông hơi
    private int thoiGianSuDung;    // Tính bằng phút

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
        loaiHoatDong = nhapChuoi("Nhập hoạt động (VD: Massage / Hồ bơi / Karaoke / Xông hơi): ");
        thoiGianSuDung = nhapSoNguyen("Nhập thời gian sử dụng (phút, từ 1 đến 600): ", 1, 600);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" Hoạt động: " + loaiHoatDong + " | Thời gian: " + thoiGianSuDung + " phút");
    }
}
