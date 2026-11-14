import java.util.Scanner;

/**
 * Lớp PhuongThucThanhToan – quản lý hình thức thanh toán của khách hàng.
 * Tích hợp kiểm tra ngoại lệ bằng Module4_Exception.
 */
public class PhuongThucThanhToan implements INhapXuat {

    public enum LoaiThanhToan {
        TIEN_MAT("Tien mat"),
        CHUYEN_KHOAN("Chuyen khoan ngan hang"),
        THE_TIN_DUNG("The tin dung / Ghi no (POS)"),
        VI_DIEN_TU("Vi dien tu (MoMo / ZaloPay / VNPAY)");

        private final String tenHienThi;

        LoaiThanhToan(String tenHienThi) {
            this.tenHienThi = tenHienThi;
        }

        public String getTenHienThi() {
            return tenHienThi;
        }
    }

    private LoaiThanhToan loaiThanhToan;
    private HoaDon hoaDon;
    private double tienKhachDua;
    private double tienThoiLai;
    private String maGiaoDich;
    private String thongTinNganHangHoacThe;
    private String trangThaiThanhToan;

    public PhuongThucThanhToan(LoaiThanhToan loaiThanhToan, HoaDon hoaDon) {
        if (hoaDon == null) {
            throw new Module4_Exception.ThanhToanKhongHopLeException("Không thể khởi tạo thanh toán khi hóa đơn bị trống (null).");
        }
        this.loaiThanhToan = (loaiThanhToan != null) ? loaiThanhToan : LoaiThanhToan.TIEN_MAT;
        this.hoaDon = hoaDon;
        this.tienKhachDua = hoaDon.getTongTienPhong();
        this.tienThoiLai = 0.0;
        this.maGiaoDich = "GD" + (System.currentTimeMillis() % 1000000);
        this.thongTinNganHangHoacThe = "Mac dinh";
        this.trangThaiThanhToan = "Da thanh toan";
        this.hoaDon.setDaThanhToan(true);
    }

    public PhuongThucThanhToan(HoaDon hoaDon) {
        this.loaiThanhToan = null;
        this.hoaDon = hoaDon;
        this.tienKhachDua = 0.0;
        this.tienThoiLai = 0.0;
        this.maGiaoDich = null;
        this.thongTinNganHangHoacThe = "";
        this.trangThaiThanhToan = "Chua thanh toan";
    }

    public PhuongThucThanhToan() {
        this(null);
    }

    public void kiemTraDuSoTien(double soTienDua, double tongCanTra) {
        if (soTienDua < tongCanTra) {
            throw new Module4_Exception.ThanhToanKhongHopLeException(
                    "Số tiền khách đưa chưa đủ (còn thiếu " + String.format("%,.0f VND", (tongCanTra - soTienDua)) + ")! Vui lòng nhập lại.");
        }
    }

    @Override
    public void nhap(Scanner sc) {
        if (hoaDon == null) {
            throw new Module4_Exception.ThanhToanKhongHopLeException("Chưa có hóa đơn để thực hiện thanh toán.");
        }
        if (hoaDon.isDaThanhToan()) {
            throw new Module4_Exception.ThanhToanKhongHopLeException("Hóa đơn [" + hoaDon.getMaHoaDon() + "] đã được thanh toán trước đó.");
        }

        ThongBao.inTieuDe("CHON HINH THUC VA THUC HIEN THANH TOAN");
        double tongTien = hoaDon.getTongTienPhong();
        System.out.printf("TONG SO TIEN CAN THANH TOAN: %,.0f VND%n%n", tongTien);

        System.out.println("Cac hinh thuc thanh toan duoc chap nhan:");
        System.out.println("  1. Tien mat");
        System.out.println("  2. Chuyen khoan ngan hang (Vietcombank, MB, BIDV...)");
        System.out.println("  3. The tin dung / Ghi no (Visa, MasterCard)");
        System.out.println("  4. Vi dien tu (MoMo, ZaloPay, VNPAY)");
        int chon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Moi ban chon hinh thuc thanh toan (1 - 4): ", 1, 4);

        switch (chon) {
            case 2:
                loaiThanhToan = LoaiThanhToan.CHUYEN_KHOAN;
                thongTinNganHangHoacThe = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten ngan hang chuyen khoan: ");
                maGiaoDich = "CK" + String.format("%06d", (System.currentTimeMillis() % 1000000));
                tienKhachDua = tongTien;
                tienThoiLai = 0.0;
                ThongBao.inThanhCong("Xac nhan da nhan du tien chuyen khoan. Ma GD: " + maGiaoDich);
                break;

            case 3:
                loaiThanhToan = LoaiThanhToan.THE_TIN_DUNG;
                thongTinNganHangHoacThe = KiemTraLoi.nhapChuoiKhongRong(sc,
                        "Nhap loai the va ten chu the (VD: Visa - Nguyen Van A): ");
                maGiaoDich = "POS" + String.format("%06d", (System.currentTimeMillis() % 1000000));
                tienKhachDua = tongTien;
                tienThoiLai = 0.0;
                ThongBao.inThanhCong("Quet the thanh cong qua POS. Ma GD: " + maGiaoDich);
                break;

            case 4:
                loaiThanhToan = LoaiThanhToan.VI_DIEN_TU;
                thongTinNganHangHoacThe = KiemTraLoi.nhapChuoiKhongRong(sc,
                        "Nhap ten vi dien tu (MoMo / ZaloPay / VNPAY): ");
                maGiaoDich = "EW" + String.format("%06d", (System.currentTimeMillis() % 1000000));
                tienKhachDua = tongTien;
                tienThoiLai = 0.0;
                ThongBao.inThanhCong("Quet ma QR thanh toan vi dien tu thanh cong. Ma GD: " + maGiaoDich);
                break;

            case 1:
            default:
                loaiThanhToan = LoaiThanhToan.TIEN_MAT;
                thongTinNganHangHoacThe = "Thu tien mat tai quay";
                maGiaoDich = "TM" + String.format("%06d", (System.currentTimeMillis() % 1000000));

                if (tongTien > 0) {
                    while (true) {
                        try {
                            double dua = KiemTraLoi.nhapSoThucDuong(sc, "Nhap so tien khach dua (VND): ");
                            kiemTraDuSoTien(dua, tongTien);
                            tienKhachDua = dua;
                            tienThoiLai = dua - tongTien;
                            break;
                        } catch (Module4_Exception.ThanhToanKhongHopLeException e) {
                            ThongBao.inLoi(e.getMessage());
                        }
                    }
                    if (tienThoiLai > 0) {
                        ThongBao.inThanhCong(String.format("Tien thoi lai cho khach: %,.0f VND", tienThoiLai));
                    }
                } else {
                    tienKhachDua = 0.0;
                    tienThoiLai = 0.0;
                }
                break;
        }

        this.trangThaiThanhToan = "Da thanh toan";
        this.hoaDon.setDaThanhToan(true);
        if (this.hoaDon.getPhieu() != null && this.hoaDon.getPhieu().getPhong() != null) {
            this.hoaDon.getPhieu().getPhong().setTrangThai("Trong");
        }
        ThongBao.inThanhCong("GIAO DICH THANH TOAN HOAN TAT!");
    }

    public void thucHienThanhToan() {
        if (hoaDon == null) {
            throw new Module4_Exception.ThanhToanKhongHopLeException("Chưa có thông tin hóa đơn để thực hiện thanh toán.");
        }
        if (loaiThanhToan == null) {
            loaiThanhToan = LoaiThanhToan.TIEN_MAT;
        }
        if (maGiaoDich == null || maGiaoDich.isEmpty()) {
            maGiaoDich = "GD" + String.format("%06d", (System.currentTimeMillis() % 1000000));
        }
        if (tienKhachDua == 0.0) {
            tienKhachDua = hoaDon.getTongTienPhong();
            tienThoiLai = 0.0;
        } else {
            kiemTraDuSoTien(tienKhachDua, hoaDon.getTongTienPhong());
            tienThoiLai = tienKhachDua - hoaDon.getTongTienPhong();
        }
        this.trangThaiThanhToan = "Da thanh toan";
        this.hoaDon.setDaThanhToan(true);
        if (this.hoaDon.getPhieu() != null && this.hoaDon.getPhieu().getPhong() != null) {
            this.hoaDon.getPhieu().getPhong().setTrangThai("Trong");
        }
        xuat();
    }

    @Override
    public void xuat() {
        if (hoaDon != null) {
            hoaDon.xuat();
        } else {
            ThongBao.inCanhBao("Chua co thong tin hoa don de thanh toan.");
        }

        if (loaiThanhToan == null) {
            ThongBao.inCanhBao("Chua chon hinh thuc thanh toan.");
            return;
        }

        ThongBao.inTieuDe("BIEN LAI XAC NHAN THANH TOAN");
        double tongTien = (hoaDon != null) ? hoaDon.getTongTienPhong() : 0.0;

        System.out.printf("  - Hinh thuc thanh toan    : %s%n", loaiThanhToan.getTenHienThi());
        System.out.printf("  - Ma giao dich (Ref)      : %s%n", (maGiaoDich != null ? maGiaoDich : "N/A"));
        if (thongTinNganHangHoacThe != null && !thongTinNganHangHoacThe.isEmpty()) {
            System.out.printf("  - Thong tin thanh toan    : %s%n", thongTinNganHangHoacThe);
        }
        System.out.printf("  - Tong tien can thanh toan: %,15.0f VND%n", tongTien);
        System.out.printf("  - So tien khach da tra    : %,15.0f VND%n", tienKhachDua);
        if (tienThoiLai > 0) {
            System.out.printf("  - Tien thoi lai cho khach : %,15.0f VND%n", tienThoiLai);
        }
        System.out.printf("  - Trang thai giao dich    : %s%n",
                ("Da thanh toan".equalsIgnoreCase(trangThaiThanhToan) ? "THANH CONG (PAID)" : "CHUA HOAN TAT"));
        System.out.println("==================================================================================================");
        ThongBao.inThanhCong("Cam on quy khach da su dung dich vu cua khach san!");
    }

    public LoaiThanhToan getLoaiThanhToan() {
        return loaiThanhToan;
    }

    public void setLoaiThanhToan(LoaiThanhToan loai) {
        this.loaiThanhToan = loai;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public double getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(double tienKhachDua) {
        if (tienKhachDua < 0) {
            throw new Module4_Exception.ThanhToanKhongHopLeException("Số tiền khách đưa không được âm.");
        }
        this.tienKhachDua = tienKhachDua;
    }

    public double getTienThoiLai() {
        return tienThoiLai;
    }

    public void setTienThoiLai(double tienThoiLai) {
        this.tienThoiLai = tienThoiLai;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getThongTinNganHangHoacThe() {
        return thongTinNganHangHoacThe;
    }

    public void setThongTinNganHangHoacThe(String thongTin) {
        this.thongTinNganHangHoacThe = thongTin;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThai) {
        this.trangThaiThanhToan = trangThai;
    }

    public boolean isDaThanhToan() {
        return "Da thanh toan".equalsIgnoreCase(this.trangThaiThanhToan);
    }
}
