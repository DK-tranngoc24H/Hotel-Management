
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

    public String getLoaiHoatDong() {
        return loaiHoatDong;
    }

    public int getThoiGianSuDung() {
        return thoiGianSuDung;
    }

    @Override
    public void nhap(NhapLieu nl) {
        super.nhap(nl);
        loaiHoatDong = nl.nhapChuoi("Nhập hoạt động (VD: Massage / Hồ bơi / Karaoke / Xông hơi): ");
        thoiGianSuDung = nl.nhapSoNguyen("Nhập thời gian sử dụng (phút, từ 1 đến 600): ", 1, 600);
    }

    @Override
    public String moTaChiTiet() {
        return loaiHoatDong + " - " + thoiGianSuDung + " phút";
    }
}
