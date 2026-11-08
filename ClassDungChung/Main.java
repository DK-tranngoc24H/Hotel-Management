import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Lớp kiểm thử toàn diện cho tất cả các thành phần trong thư mục ClassDungChung:
 * 1. Interface INhapXuat
 * 2. Lớp tiện ích ThongBao
 * 3. Lớp tiện ích KiemTraLoi
 *
 * Hỗ trợ 2 chế độ kiểm thử:
 * - Tự động (Automated Testing): Giả lập luồng nhập để kiểm tra nhanh mọi hàm và trường hợp lỗi.
 * - Tương tác (Interactive Testing): Menu trực tiếp cho người dùng tự nhập và kiểm chứng.
 */
public class Main {

    // =========================================================================
    // LỚP MẪU TRIỂN KHAI INhapXuat ĐỂ KIỂM THỬ
    // =========================================================================
    public static class DemoDoiTuong implements INhapXuat {
        private String ma;
        private String ten;
        private int soLuong;
        private double giaTien;
        private boolean hoatDong;
        private String trangThaiPhong;
        private String loaiView;

        public DemoDoiTuong() {
            this.ma = "M001";
            this.ten = "Mau Mac Dinh";
            this.soLuong = 1;
            this.giaTien = 100000.0;
            this.hoatDong = true;
            this.trangThaiPhong = "Trong";
            this.loaiView = "Bien";
        }

        public DemoDoiTuong(String ma, String ten, int soLuong, double giaTien,
                            boolean hoatDong, String trangThaiPhong, String loaiView) {
            this.ma = ma;
            this.ten = ten;
            this.soLuong = soLuong;
            this.giaTien = giaTien;
            this.hoatDong = hoatDong;
            this.trangThaiPhong = trangThaiPhong;
            this.loaiView = loaiView;
        }

        @Override
        public void nhap(Scanner sc) {
            ThongBao.inTieuDe("NHAP THONG TIN DOI TUONG MAU (INhapXuat)");
            this.ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma doi tuong: ");
            this.ten = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten doi tuong: ");
            this.soLuong = KiemTraLoi.nhapSoNguyenDuong(sc, "Nhap so luong (>= 0): ");
            this.giaTien = KiemTraLoi.nhapSoThucDuong(sc, "Nhap gia tien (>= 0): ");
            this.hoatDong = KiemTraLoi.nhapCoKhong(sc, "Co dang hoat dong khong? (co/khong): ");
            this.trangThaiPhong = KiemTraLoi.nhapTrangThaiPhong(sc);
            this.loaiView = KiemTraLoi.nhapLoaiView(sc);
            ThongBao.inThanhCong("Da nhap thong tin doi tuong mau thanh cong!");
        }

        @Override
        public void xuat() {
            ThongBao.inTieuDe("XUAT THONG TIN DOI TUONG MAU (INhapXuat)");
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.printf("| %-8s | %-18s | %-8s | %-12s | %-9s | %-12s | %-12s |%n",
                    "MA", "TEN", "SO LUONG", "GIA TIEN", "HOAT DONG", "TRANG THAI", "VIEW");
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.printf("| %-8s | %-18s | %-8d | %-12.2f | %-9s | %-12s | %-12s |%n",
                    ma, ten, soLuong, giaTien, (hoatDong ? "Co" : "Khong"), trangThaiPhong, loaiView);
            System.out.println("---------------------------------------------------------------------------------------------------");
        }

        public String getMa() { return ma; }
        public String getTen() { return ten; }
        public int getSoLuong() { return soLuong; }
        public double getGiaTien() { return giaTien; }
        public boolean isHoatDong() { return hoatDong; }
        public String getTrangThaiPhong() { return trangThaiPhong; }
        public String getLoaiView() { return loaiView; }
    }

    // =========================================================================
    // BIẾN THỐNG KÊ KIỂM THỬ TỰ ĐỘNG
    // =========================================================================
    private static int soLuongPass = 0;
    private static int soLuongFail = 0;

    private static void khangDinh(boolean dieuKien, String tenTest) {
        if (dieuKien) {
            soLuongPass++;
            System.out.println("  [PASS] " + tenTest);
        } else {
            soLuongFail++;
            System.out.println("  [FAIL] " + tenTest);
        }
    }

    private static Scanner taoScannerGiaLap(String duLieu) {
        return new Scanner(new ByteArrayInputStream(duLieu.getBytes(StandardCharsets.UTF_8)));
    }

    // =========================================================================
    // SUITE KIỂM THỬ TỰ ĐỘNG (AUTOMATED TEST SUITE)
    // =========================================================================
    public static void chayKiemThuTuDong() {
        soLuongPass = 0;
        soLuongFail = 0;

        ThongBao.inTieuDe("BAT DAU KIEM THU TU DONG - CLASSDUNGCHUNG");

        // 1. Kiểm thử ThongBao
        System.out.println("\n--- [1] Kiem thu lop ThongBao ---");
        ThongBao.inTieuDe("DEMO TIEU DE");
        ThongBao.inThanhCong("Kiem tra inThanhCong hoat dong tot.");
        ThongBao.inCanhBao("Kiem tra inCanhBao hoat dong tot.");
        ThongBao.inLoi("Kiem tra inLoi hoat dong tot.");

        khangDinh(ThongBao.LOI_DE_TRONG != null && !ThongBao.LOI_DE_TRONG.isEmpty(), "Hang so LOI_DE_TRONG ton tai va hop le");
        khangDinh(ThongBao.LOI_SO_AM != null && !ThongBao.LOI_SO_AM.isEmpty(), "Hang so LOI_SO_AM ton tai va hop le");
        khangDinh(ThongBao.LOI_SO_KHONG_HOP_LE != null && !ThongBao.LOI_SO_KHONG_HOP_LE.isEmpty(), "Hang so LOI_SO_KHONG_HOP_LE ton tai va hop le");
        khangDinh(ThongBao.LOI_CHON_CO_KHONG != null && !ThongBao.LOI_CHON_CO_KHONG.isEmpty(), "Hang so LOI_CHON_CO_KHONG ton tai va hop le");
        khangDinh(ThongBao.LOI_TRANG_THAI != null && !ThongBao.LOI_TRANG_THAI.isEmpty(), "Hang so LOI_TRANG_THAI ton tai va hop le");
        khangDinh(ThongBao.LOI_VIEW != null && !ThongBao.LOI_VIEW.isEmpty(), "Hang so LOI_VIEW ton tai va hop le");

        // 2. Kiểm thử KiemTraLoi.nhapChuoiKhongRong
        System.out.println("\n--- [2] Kiem thu KiemTraLoi.nhapChuoiKhongRong ---");
        Scanner scStr = taoScannerGiaLap("\n   \nKhach San Hoang Gia\n");
        String strKetQua = KiemTraLoi.nhapChuoiKhongRong(scStr, "Test nhap chuoi: ");
        khangDinh("Khach San Hoang Gia".equals(strKetQua), "Bo qua chuoi rong va lay dung 'Khach San Hoang Gia'");

        // 3. Kiểm thử KiemTraLoi.nhapSoNguyenDuong
        System.out.println("\n--- [3] Kiem thu KiemTraLoi.nhapSoNguyenDuong ---");
        Scanner scInt1 = taoScannerGiaLap("-10\nchuoi_khong_hop_le\n0\n");
        int intKetQua1 = KiemTraLoi.nhapSoNguyenDuong(scInt1, "Test nhap so nguyen >= 0: ");
        khangDinh(intKetQua1 == 0, "Tu choi so am (-10) & chuoi chu, chap nhan so 0");

        Scanner scInt2 = taoScannerGiaLap("150\n");
        int intKetQua2 = KiemTraLoi.nhapSoNguyenDuong(scInt2, "Test nhap so nguyen duong: ");
        khangDinh(intKetQua2 == 150, "Chap nhan so nguyen duong 150");

        // 4. Kiểm thử KiemTraLoi.nhapSoThucDuong
        System.out.println("\n--- [4] Kiem thu KiemTraLoi.nhapSoThucDuong ---");
        Scanner scDouble1 = taoScannerGiaLap("-99.9\nabc\n350000.5\n");
        double dblKetQua1 = KiemTraLoi.nhapSoThucDuong(scDouble1, "Test nhap so thuc >= 0: ");
        khangDinh(dblKetQua1 == 350000.5, "Tu choi so thuc am & ky tu, chap nhan 350000.5");

        Scanner scDouble2 = taoScannerGiaLap("0\n");
        double dblKetQua2 = KiemTraLoi.nhapSoThucDuong(scDouble2, "Test nhap so thuc 0: ");
        khangDinh(dblKetQua2 == 0.0, "Chap nhan so thuc 0.0");

        // 5. Kiểm thử KiemTraLoi.nhapSoNguyenTrongKhoang
        System.out.println("\n--- [5] Kiem thu KiemTraLoi.nhapSoNguyenTrongKhoang ---");
        Scanner scRange = taoScannerGiaLap("0\n10\n-5\nsai\n3\n");
        int rangeKetQua = KiemTraLoi.nhapSoNguyenTrongKhoang(scRange, "Test khoang [1, 5]: ", 1, 5);
        khangDinh(rangeKetQua == 3, "Tu choi ngoai khoang [1, 5], chap nhan gia tri 3");

        Scanner scRangeBienDuoi = taoScannerGiaLap("1\n");
        khangDinh(KiemTraLoi.nhapSoNguyenTrongKhoang(scRangeBienDuoi, "Test bien duoi: ", 1, 5) == 1, "Chap nhan bien duoi 1");

        Scanner scRangeBienTren = taoScannerGiaLap("5\n");
        khangDinh(KiemTraLoi.nhapSoNguyenTrongKhoang(scRangeBienTren, "Test bien tren: ", 1, 5) == 5, "Chap nhan bien tren 5");

        // 6. Kiểm thử KiemTraLoi.nhapCoKhong
        System.out.println("\n--- [6] Kiem thu KiemTraLoi.nhapCoKhong ---");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("sai_dinh_dang\nco\n"), "Test: ") == true, "Chuoi 'co' tra ve true");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("có\n"), "Test: ") == true, "Chuoi 'có' tra ve true");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("true\n"), "Test: ") == true, "Chuoi 'true' tra ve true");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("1\n"), "Test: ") == true, "Chuoi '1' tra ve true");

        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("khong\n"), "Test: ") == false, "Chuoi 'khong' tra ve false");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("không\n"), "Test: ") == false, "Chuoi 'không' tra ve false");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("false\n"), "Test: ") == false, "Chuoi 'false' tra ve false");
        khangDinh(KiemTraLoi.nhapCoKhong(taoScannerGiaLap("0\n"), "Test: ") == false, "Chuoi '0' tra ve false");

        // 7. Kiểm thử KiemTraLoi.nhapTrangThaiPhong
        System.out.println("\n--- [7] Kiem thu KiemTraLoi.nhapTrangThaiPhong ---");
        khangDinh("Trong".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("sai\ntrong\n"))), "Tu choi sai va nhan 'trong' -> 'Trong'");
        khangDinh("Trong".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("trống\n"))), "Nhan 'trống' -> 'Trong'");
        khangDinh("Dang o".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("dang o\n"))), "Nhan 'dang o' -> 'Dang o'");
        khangDinh("Dang o".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("đang ở\n"))), "Nhan 'đang ở' -> 'Dang o'");
        khangDinh("Dang o".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("dango\n"))), "Nhan 'dango' -> 'Dang o'");
        khangDinh("Da dat".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("da dat\n"))), "Nhan 'da dat' -> 'Da dat'");
        khangDinh("Da dat".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("đã đặt\n"))), "Nhan 'đã đặt' -> 'Da dat'");
        khangDinh("Da dat".equals(KiemTraLoi.nhapTrangThaiPhong(taoScannerGiaLap("dadat\n"))), "Nhan 'dadat' -> 'Da dat'");

        // 8. Kiểm thử KiemTraLoi.nhapLoaiView
        System.out.println("\n--- [8] Kiem thu KiemTraLoi.nhapLoaiView ---");
        khangDinh("Bien".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("nui\nbien\n"))), "Tu choi sai va nhan 'bien' -> 'Bien'");
        khangDinh("Bien".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("biển\n"))), "Nhan 'biển' -> 'Bien'");
        khangDinh("Thanh pho".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("thanh pho\n"))), "Nhan 'thanh pho' -> 'Thanh pho'");
        khangDinh("Thanh pho".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("thành phố\n"))), "Nhan 'thành phố' -> 'Thanh pho'");
        khangDinh("Thanh pho".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("thanhpho\n"))), "Nhan 'thanhpho' -> 'Thanh pho'");
        khangDinh("San vuon".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("san vuon\n"))), "Nhan 'san vuon' -> 'San vuon'");
        khangDinh("San vuon".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("sân vườn\n"))), "Nhan 'sân vườn' -> 'San vuon'");
        khangDinh("San vuon".equals(KiemTraLoi.nhapLoaiView(taoScannerGiaLap("sanvuon\n"))), "Nhan 'sanvuon' -> 'San vuon'");

        // 9. Kiểm thử Interface INhapXuat & DemoDoiTuong
        System.out.println("\n--- [9] Kiem thu Interface INhapXuat & DemoDoiTuong ---");
        INhapXuat doiTuongTest = new DemoDoiTuong();
        String inputDemo = "P999\nPhong VIP Tong Thong\n3\n2500000\nco\ntrong\nbien\n";
        Scanner scDemo = taoScannerGiaLap(inputDemo);
        doiTuongTest.nhap(scDemo);

        DemoDoiTuong casted = (DemoDoiTuong) doiTuongTest;
        khangDinh("P999".equals(casted.getMa()), "DemoDoiTuong nhap dung ma: P999");
        khangDinh("Phong VIP Tong Thong".equals(casted.getTen()), "DemoDoiTuong nhap dung ten");
        khangDinh(casted.getSoLuong() == 3, "DemoDoiTuong nhap dung so luong: 3");
        khangDinh(casted.getGiaTien() == 2500000.0, "DemoDoiTuong nhap dung gia tien: 2500000.0");
        khangDinh(casted.isHoatDong() == true, "DemoDoiTuong nhap dung hoat dong: true");
        khangDinh("Trong".equals(casted.getTrangThaiPhong()), "DemoDoiTuong nhap dung trang thai: Trong");
        khangDinh("Bien".equals(casted.getLoaiView()), "DemoDoiTuong nhap dung view: Bien");

        doiTuongTest.xuat();

        // TỔNG KẾT
        System.out.println("\n========================================");
        System.out.printf("   KET QUA: PASS = %d, FAIL = %d%n", soLuongPass, soLuongFail);
        System.out.println("========================================");
        if (soLuongFail == 0) {
            ThongBao.inThanhCong("TAT CA CAC TEST CASE CUA CLASSDUNGCHUNG DA HOAN TOAN VUOT QUA!");
        } else {
            ThongBao.inLoi("Co " + soLuongFail + " test case bi that bai! Vui long kiem tra lai.");
        }
    }

    // =========================================================================
    // CÁC HÀM KIỂM THỬ TƯƠNG TÁC (INTERACTIVE MANUAL TESTING)
    // =========================================================================
    public static void kiemThuThongBaoTrucTiep() {
        ThongBao.inTieuDe("KIEM THU TRUC TIEP LOP THONG BAO");
        ThongBao.inThanhCong("Day la thong bao thanh cong (inThanhCong)!");
        ThongBao.inCanhBao("Day la thong bao canh bao (inCanhBao)!");
        ThongBao.inLoi("Day la thong bao loi (inLoi)!");

        System.out.println("\nDanh sach cac hang so thong bao loi dung chung:");
        System.out.println("  1. LOI_DE_TRONG        : " + ThongBao.LOI_DE_TRONG);
        System.out.println("  2. LOI_SO_AM           : " + ThongBao.LOI_SO_AM);
        System.out.println("  3. LOI_SO_KHONG_HOP_LE : " + ThongBao.LOI_SO_KHONG_HOP_LE);
        System.out.println("  4. LOI_CHON_CO_KHONG   : " + ThongBao.LOI_CHON_CO_KHONG);
        System.out.println("  5. LOI_TRANG_THAI      : " + ThongBao.LOI_TRANG_THAI);
        System.out.println("  6. LOI_VIEW            : " + ThongBao.LOI_VIEW);
    }

    public static void kiemThuNhapChuoi(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP CHUOI KHONG RONG");
        String s = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap mot chuoi bat ky (khong de trong): ");
        ThongBao.inThanhCong("Chuoi hop le vua nhap: \"" + s + "\"");
    }

    public static void kiemThuNhapSoNguyenDuong(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP SO NGUYEN DUONG (>= 0)");
        int n = KiemTraLoi.nhapSoNguyenDuong(sc, "Nhap so nguyen (>= 0): ");
        ThongBao.inThanhCong("So nguyen hop le vua nhap: " + n);
    }

    public static void kiemThuNhapSoThucDuong(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP SO THUC DUONG (>= 0)");
        double d = KiemTraLoi.nhapSoThucDuong(sc, "Nhap so thuc (>= 0): ");
        ThongBao.inThanhCong("So thuc hop le vua nhap: " + d);
    }

    public static void kiemThuNhapSoNguyenTrongKhoang(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP SO NGUYEN TRONG KHOANG [10, 50]");
        int k = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Nhap so nguyen trong khoang 10 den 50: ", 10, 50);
        ThongBao.inThanhCong("So hop le trong khoang [10, 50] vua nhap: " + k);
    }

    public static void kiemThuNhapCoKhong(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP CO / KHONG");
        boolean b = KiemTraLoi.nhapCoKhong(sc, "Ban co dong y tiep tuc khong? (co/khong): ");
        ThongBao.inThanhCong("Ket qua tra ve boolean: " + b + " (" + (b ? "Co / True" : "Khong / False") + ")");
    }

    public static void kiemThuNhapTrangThaiPhong(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP TRANG THAI PHONG");
        String tt = KiemTraLoi.nhapTrangThaiPhong(sc);
        ThongBao.inThanhCong("Trang thai phong hop le da chuan hoa: \"" + tt + "\"");
    }

    public static void kiemThuNhapLoaiView(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU NHAP LOAI VIEW PHONG VIP");
        String v = KiemTraLoi.nhapLoaiView(sc);
        ThongBao.inThanhCong("Loai view hop le da chuan hoa: \"" + v + "\"");
    }

    public static void kiemThuINhapXuat(Scanner sc) {
        ThongBao.inTieuDe("KIEM THU INTERFACE INhapXuat VOI DOI TUONG MAU");
        INhapXuat dt = new DemoDoiTuong();
        dt.nhap(sc);
        dt.xuat();
    }

    // =========================================================================
    // MAIN METHOD & MENU DIEU KHIEN
    // =========================================================================
    public static void main(String[] args) {
        // Neu truyen tham so 'auto' hoac '--auto', chay che do kiem thu tu dong
        if (args.length > 0 && (args[0].equalsIgnoreCase("auto") || args[0].equalsIgnoreCase("--auto"))) {
            chayKiemThuTuDong();
            return;
        }

        Scanner sc = new Scanner(System.in);
        boolean tiepTuc = true;

        while (tiepTuc) {
            ThongBao.inTieuDe("MENU KIEM THU CLASS DUNG CHUNG");
            System.out.println("  1. Chay toan bo test tu dong (Automated Test Suite)");
            System.out.println("  2. Kiem thu ThongBao (in cac loai thong bao mau)");
            System.out.println("  3. Kiem thu KiemTraLoi: nhapChuoiKhongRong");
            System.out.println("  4. Kiem thu KiemTraLoi: nhapSoNguyenDuong");
            System.out.println("  5. Kiem thu KiemTraLoi: nhapSoThucDuong");
            System.out.println("  6. Kiem thu KiemTraLoi: nhapSoNguyenTrongKhoang");
            System.out.println("  7. Kiem thu KiemTraLoi: nhapCoKhong");
            System.out.println("  8. Kiem thu KiemTraLoi: nhapTrangThaiPhong");
            System.out.println("  9. Kiem thu KiemTraLoi: nhapLoaiView");
            System.out.println(" 10. Kiem thu Interface INhapXuat (Nhap & Xuat doi tuong mau)");
            System.out.println("  0. Thoat chuong trinh");
            System.out.println("----------------------------------------");

            int luaChon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Moi ban chon chuc nang (0 - 10): ", 0, 10);

            switch (luaChon) {
                case 1:
                    chayKiemThuTuDong();
                    break;
                case 2:
                    kiemThuThongBaoTrucTiep();
                    break;
                case 3:
                    kiemThuNhapChuoi(sc);
                    break;
                case 4:
                    kiemThuNhapSoNguyenDuong(sc);
                    break;
                case 5:
                    kiemThuNhapSoThucDuong(sc);
                    break;
                case 6:
                    kiemThuNhapSoNguyenTrongKhoang(sc);
                    break;
                case 7:
                    kiemThuNhapCoKhong(sc);
                    break;
                case 8:
                    kiemThuNhapTrangThaiPhong(sc);
                    break;
                case 9:
                    kiemThuNhapLoaiView(sc);
                    break;
                case 10:
                    kiemThuINhapXuat(sc);
                    break;
                case 0:
                    ThongBao.inCanhBao("Tam biet! Da ket thuc chuong trinh kiem thu.");
                    tiepTuc = false;
                    break;
            }

            if (tiepTuc) {
                System.out.println("\n[Nhan Enter de quay ve Menu...]");
                sc.nextLine();
            }
        }
    }
}
