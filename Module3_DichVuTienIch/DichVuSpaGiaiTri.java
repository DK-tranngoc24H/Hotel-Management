package BT_quanLyKhachSan;

public class DichVuSpaGiaiTri extends DichVu {
    private String loaiHoatDong;   // Massage, Hồ bơi, Karaoke, ...
    private int thoiGianSuDung;    // phút

    public DichVuSpaGiaiTri() {}

    public DichVuSpaGiaiTri(String maDV, String tenDV, double donGia, String loaiHoatDong, int thoiGianSuDung) {
        super(maDV, tenDV, donGia);
        this.loaiHoatDong = loaiHoatDong;
        this.thoiGianSuDung = thoiGianSuDung;
    }

    @Override
    public void nhap(NhapLieu nl) {
        super.nhap(nl);
        loaiHoatDong = nl.nhapChuoi("Hoạt động (VD: Massage / Hồ bơi / Karaoke): ");
        thoiGianSuDung = nl.nhapSoNguyen("Thời gian sử dụng (phút, 1-600): ", 1, 600);
    }

    @Override
    public String moTaChiTiet() {
        return loaiHoatDong + " - " + thoiGianSuDung + " phút";
    }

    @Override
    public String toFileLine() {
        return "SpaGiaiTri" + SEP + maDV + SEP + tenDV + SEP + donGia + SEP + loaiHoatDong + SEP + thoiGianSuDung;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 2c424b4259f2ca0ec5a2e8a0ce5700237fce3469
