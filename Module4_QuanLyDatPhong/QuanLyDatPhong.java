import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Lớp QuanLyDatPhong – Điều phối trung tâm cho Module 4 (Quản lý đặt phòng & Hóa đơn).
 * Đã đồng bộ hoàn toàn tên class và tên biến với:
 * - Module 1: KhachHang (hoten, CCCD, sdt, diachi, loaikhach)
 * - Module 2: Phong, PhongStandard (maPhong, giaCoBan, trangThai, coGiuongPhu),
 *             PhongVIP (maPhong, giaCoBan, trangThai, loaiView, phiDichVuDacBiet)
 * - Module 3: BT_quanLyKhachSan.DichVu (maDV, tenDV, donGia)
 * - ClassDungChung: INhapXuat, KiemTraLoi, ThongBao, Module4_Exception
 */
public class QuanLyDatPhong {

    public static final String FILE_DU_LIEU_MAC_DINH = "Module4_QuanLyDatPhong/module4_data.txt";

    private final ArrayList<PhieuDatPhong> dsPhieu;
    private final ArrayList<HoaDon> dsHoaDon;
    private final Map<String, ChiTietHoaDon> mapChiTietDichVu;

    public QuanLyDatPhong() {
        this.dsPhieu = new ArrayList<>();
        this.dsHoaDon = new ArrayList<>();
        this.mapChiTietDichVu = new HashMap<>();
        napDuLieuSanCo();
    }

    /**
     * Ưu tiên đọc dữ liệu từ file module4_data.txt; nếu không tìm thấy file thì nạp dữ liệu dự phòng.
     */
    public void napDuLieuSanCo() {
        File fileData = timFileDuLieu(FILE_DU_LIEU_MAC_DINH);
        if (fileData != null && fileData.exists()) {
            try {
                docDuLieuTuFile(fileData.getPath());
                return;
            } catch (Module4_Exception e) {
                ThongBao.inCanhBao(e.getMessage() + " -> Chuyển sang nạp dữ liệu mặc định.");
            }
        }
        napDuLieuMauMacDinh();
    }

    private File timFileDuLieu(String tenFile) {
        String[] cacDuongDan = {
            tenFile,
            "module4_data.txt",
            "Module4_QuanLyDatPhong/module4_data.txt",
            "../Module4_QuanLyDatPhong/module4_data.txt"
        };
        for (String path : cacDuongDan) {
            File f = new File(path);
            if (f.exists() && f.isFile()) {
                return f;
            }
        }
        return null;
    }

    private void napDuLieuMauMacDinh() {
        KhachHang kh1 = new KhachHang("Nguyen Van An", "012345678901", "0901234567", "Ha Noi", "Thuong", null);
        KhachHang kh2 = new KhachHang("Tran Thi Bich", "079987654321", "0987654321", "TP Ho Chi Minh", "VIP", null);
        KhachHang kh3 = new KhachHang("Le Hoang Nam", "048123456789", "0912345678", "Da Nang", "Doanh nghiep", null);
        KhachHang kh4 = new KhachHang("Pham Minh Tuan", "036123456789", "0934567890", "Hai Phong", "Thuong", null);

        PhongStandard p101 = new PhongStandard("P101", 500_000.0, "Da dat", true);
        PhongVIP p201 = new PhongVIP("P201", 1_200_000.0, "Trong", "Bien", 200_000.0);
        PhongStandard p102 = new PhongStandard("P102", 550_000.0, "Da dat", false);
        PhongVIP p202 = new PhongVIP("P202", 1_000_000.0, "Trong", "Thanh pho", 150_000.0);

        PhieuDatPhong pdp1 = new PhieuDatPhong("PDP01", "01/12/2026", "02/12/2026", "05/12/2026", kh1, p101);
        PhieuDatPhong pdp2 = new PhieuDatPhong("PDP02", "03/12/2026", "04/12/2026", "08/12/2026", kh2, p201);
        PhieuDatPhong pdp3 = new PhieuDatPhong("PDP03", "10/12/2026", "11/12/2026", "14/12/2026", kh3, p102);
        PhieuDatPhong pdp4 = new PhieuDatPhong("PDP04", "15/12/2026", "16/12/2026", "19/12/2026", kh4, p202);

        dsPhieu.add(pdp1);
        dsPhieu.add(pdp2);
        dsPhieu.add(pdp3);
        dsPhieu.add(pdp4);

        ChiTietHoaDon cthd1 = new ChiTietHoaDon(pdp1);
        cthd1.themDichVu("DA01", 2);
        cthd1.themDichVu("NU01", 2);
        mapChiTietDichVu.put(pdp1.getMaPhieu(), cthd1);

        ChiTietHoaDon cthd2 = new ChiTietHoaDon(pdp2);
        cthd2.themDichVu("SG01", 2);
        cthd2.themDichVu("TX02", 1);
        mapChiTietDichVu.put(pdp2.getMaPhieu(), cthd2);

        ChiTietHoaDon cthd3 = new ChiTietHoaDon(pdp3);
        cthd3.themDichVu("DA03", 2);
        cthd3.themDichVu("NU02", 2);
        mapChiTietDichVu.put(pdp3.getMaPhieu(), cthd3);

        ChiTietHoaDon cthd4 = new ChiTietHoaDon(pdp4);
        cthd4.themDichVu("SG03", 2);
        cthd4.themDichVu("TX03", 1);
        mapChiTietDichVu.put(pdp4.getMaPhieu(), cthd4);

        HoaDon hd1 = new HoaDon("HD-PDP01", "05/12/2026", pdp1, cthd1, 50_000.0);
        dsHoaDon.add(hd1);

        HoaDon hd2 = new HoaDon("HD-PDP02", "08/12/2026", pdp2, cthd2, 100_000.0);
        hd2.setDaThanhToan(true);
        p201.setTrangThai("Trong");
        dsHoaDon.add(hd2);

        HoaDon hd3 = new HoaDon("HD-PDP03", "14/12/2026", pdp3, cthd3, 0.0);
        dsHoaDon.add(hd3);

        HoaDon hd4 = new HoaDon("HD-PDP04", "19/12/2026", pdp4, cthd4, 150_000.0);
        hd4.setDaThanhToan(true);
        p202.setTrangThai("Trong");
        dsHoaDon.add(hd4);
    }

    // ---------------------------------------------------------------
    // Đọc / Ghi bộ dữ liệu Module 4 (module4_data.txt)
    // ---------------------------------------------------------------

    /**
     * Đọc dữ liệu từ file module4_data.txt vào hệ thống.
     */
    public void docDuLieuTuFile(String duongDan) {
        File f = timFileDuLieu(duongDan);
        if (f == null || !f.exists()) {
            throw new Module4_Exception.DuLieuFileException("Không tìm thấy file dữ liệu tại đường dẫn: " + duongDan);
        }

        dsPhieu.clear();
        dsHoaDon.clear();
        mapChiTietDichVu.clear();

        int soDongThanhCong = 0;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            int soDong = 0;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] p = line.split(";");
                if (p.length < 17) {
                    throw new Module4_Exception.DuLieuFileException(
                            "Dòng " + soDong + " không đủ số trường dữ liệu (cần tối thiểu 17 trường).");
                }

                String maPhieu = p[0].trim();
                String ngayDat = p[1].trim();
                String ngayNhan = p[2].trim();
                String ngayTra = p[3].trim();

                String hoten = p[4].trim();
                String CCCD = p[5].trim();
                String sdt = p[6].trim();
                String diachi = p[7].trim();
                String loaikhach = p[8].trim();
                KhachHang khachHang = new KhachHang(hoten, CCCD, sdt, diachi, loaikhach, null);

                String maPhong = p[9].trim();
                String loaiPhong = p[10].trim();
                double giaCoBan = Double.parseDouble(p[11].trim());
                String thuocTinhPhong = p[12].trim();
                double phiDichVuDacBiet = Double.parseDouble(p[13].trim());

                Phong phong;
                if ("VIP".equalsIgnoreCase(loaiPhong)) {
                    phong = new PhongVIP(maPhong, giaCoBan, "Da dat", thuocTinhPhong, phiDichVuDacBiet);
                } else {
                    boolean coGiuongPhu = Boolean.parseBoolean(thuocTinhPhong);
                    phong = new PhongStandard(maPhong, giaCoBan, "Da dat", coGiuongPhu);
                }

                PhieuDatPhong phieu = new PhieuDatPhong(maPhieu, ngayDat, ngayNhan, ngayTra, khachHang, phong);
                dsPhieu.add(phieu);

                ChiTietHoaDon cthd = new ChiTietHoaDon(phieu);
                String chuoiDichVu = p[14].trim();
                if (!chuoiDichVu.isEmpty() && !"NONE".equalsIgnoreCase(chuoiDichVu)) {
                    String[] dsCapDV = chuoiDichVu.split(",");
                    for (String cap : dsCapDV) {
                        String[] kv = cap.trim().split(":");
                        if (kv.length == 2) {
                            String maDV = kv[0].trim();
                            int soLuong = Integer.parseInt(kv[1].trim());
                            cthd.themDichVu(maDV, soLuong);
                        }
                    }
                }
                mapChiTietDichVu.put(maPhieu, cthd);

                double giamGia = Double.parseDouble(p[15].trim());
                boolean daThanhToan = Boolean.parseBoolean(p[16].trim());

                HoaDon hd = new HoaDon("HD-" + maPhieu, ngayTra, phieu, cthd, giamGia);
                hd.setDaThanhToan(daThanhToan);
                if (daThanhToan) {
                    phong.setTrangThai("Trong");
                }
                dsHoaDon.add(hd);
                soDongThanhCong++;
            }
        } catch (IOException | NumberFormatException e) {
            throw new Module4_Exception.DuLieuFileException("Lỗi khi đọc file '" + f.getPath() + "': " + e.getMessage());
        }
        ThongBao.inThanhCong("Đã nạp thành công " + soDongThanhCong + " bản ghi từ file: " + f.getPath());
    }

    /**
     * Ghi toàn bộ dữ liệu hiện tại của Module 4 ra file module4_data.txt.
     */
    public void ghiDuLieuRaFile(String duongDan) {
        File f = timFileDuLieu(duongDan);
        if (f == null) {
            f = new File(duongDan);
        }

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            bw.write("# ==============================================================================================\n");
            bw.write("# BỘ DỮ LIỆU CHUẨN CHO MODULE 4 (QUẢN LÝ ĐẶT PHÒNG & HÓA ĐƠN) - FILE: module4_data.txt\n");
            bw.write("# Cấu trúc: maPhieu;ngayDat;ngayNhan;ngayTra;hoten;CCCD;sdt;diachi;loaikhach;maPhong;loaiPhong;giaCoBan;thuocTinhPhong;phiDichVuDacBiet;dsDichVu;giamGia;daThanhToan;loaiThanhToan;thongTinThanhToan\n");
            bw.write("# ==============================================================================================\n");

            for (PhieuDatPhong p : dsPhieu) {
                KhachHang khachHang = p.getKhachHang();
                Phong phong = p.getPhong();
                String loaiPhong = (phong instanceof PhongVIP) ? "VIP" : "Standard";
                double giaCoBan = (phong != null) ? phong.getGiaCoBan() : 500000;
                String thuocTinhPhong = "false";
                double phiDichVuDacBiet = 0;

                if (phong instanceof PhongVIP) {
                    thuocTinhPhong = ((PhongVIP) phong).getLoaiView();
                    phiDichVuDacBiet = ((PhongVIP) phong).getPhiDichVuDacBiet();
                } else if (phong instanceof PhongStandard) {
                    thuocTinhPhong = String.valueOf(((PhongStandard) phong).isCoGiuongPhu());
                }

                ChiTietHoaDon cthd = layChiTietDichVu(p.getMaPhieu());
                StringBuilder sbDV = new StringBuilder();
                if (cthd != null && !cthd.getDanhSachChiTiet().isEmpty()) {
                    for (int i = 0; i < cthd.getDanhSachChiTiet().size(); i++) {
                        ChiTietHoaDon.ChiTietDichVu item = cthd.getDanhSachChiTiet().get(i);
                        if (i > 0) sbDV.append(",");
                        sbDV.append(item.getDichVu().getMaDV()).append(":").append(item.getSoLuong());
                    }
                } else {
                    sbDV.append("NONE");
                }

                HoaDon hd = timHoaDonTheoMaPhieu(p.getMaPhieu());
                double giamGia = (hd != null) ? hd.getGiamGia() : 0.0;
                boolean daThanhToan = (hd != null) && hd.isDaThanhToan();

                String line = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%.0f;%s;%.0f;%s;%.0f;%b;%s;%s",
                        p.getMaPhieu(), p.getNgayDatStr(), p.getNgayNhanStr(), p.getNgayTraStr(),
                        (khachHang != null ? khachHang.gethoten() : "N/A"),
                        (khachHang != null ? khachHang.getCCCD() : "N/A"),
                        (khachHang != null ? khachHang.getSdt() : "N/A"),
                        (khachHang != null ? khachHang.getDiachi() : "N/A"),
                        (khachHang != null ? khachHang.getLoaikhach() : "Thuong"),
                        p.getMaPhong(), loaiPhong, giaCoBan, thuocTinhPhong, phiDichVuDacBiet,
                        sbDV.toString(), giamGia, daThanhToan,
                        (daThanhToan ? "DA_TT" : "CHUA_TT"),
                        (daThanhToan ? "Da thanh toan" : "Chua thanh toan"));
                bw.write(line + "\n");
            }
            ThongBao.inThanhCong("Đã lưu thành công " + dsPhieu.size() + " bản ghi vào file: " + f.getPath());
        } catch (IOException e) {
            throw new Module4_Exception.DuLieuFileException("Không thể ghi dữ liệu ra file: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // 1. Quản lý Đặt phòng (PhieuDatPhong)
    // ---------------------------------------------------------------

    public void themPhieuDatPhong(PhieuDatPhong phieu) {
        if (phieu == null) {
            throw new Module4_Exception.PhieuDatPhongException("Phiếu đặt phòng cần thêm không được null.");
        }
        if (timPhieuDatPhong(phieu.getMaPhieu()) != null) {
            throw new Module4_Exception.PhieuDatPhongException("Mã phiếu đặt phòng '" + phieu.getMaPhieu() + "' đã tồn tại trong hệ thống.");
        }
        dsPhieu.add(phieu);
        if (!mapChiTietDichVu.containsKey(phieu.getMaPhieu())) {
            mapChiTietDichVu.put(phieu.getMaPhieu(), new ChiTietHoaDon(phieu));
        }
    }

    public void taoPhieuDatPhongMoi(Scanner sc) {
        try {
            PhieuDatPhong phieuMoi = new PhieuDatPhong();
            phieuMoi.nhap(sc);
            themPhieuDatPhong(phieuMoi);
            HoaDon hdMoi = new HoaDon(phieuMoi, layChiTietDichVu(phieuMoi.getMaPhieu()), 0.0);
            dsHoaDon.add(hdMoi);
            ThongBao.inThanhCong("Tao phieu dat phong thanh cong voi ma: " + phieuMoi.getMaPhieu());
        } catch (Module4_Exception e) {
            ThongBao.inLoi(e.getMessage());
        }
    }

    public PhieuDatPhong timPhieuDatPhong(String maPhieu) {
        if (maPhieu == null) return null;
        for (PhieuDatPhong p : dsPhieu) {
            if (p.getMaPhieu() != null && p.getMaPhieu().equalsIgnoreCase(maPhieu.trim())) {
                return p;
            }
        }
        return null;
    }

    public PhieuDatPhong timPhieuTheoMaPhong(String maPhong) {
        if (maPhong == null) return null;
        for (PhieuDatPhong p : dsPhieu) {
            if (p.getMaPhong() != null && p.getMaPhong().equalsIgnoreCase(maPhong.trim())) {
                return p;
            }
        }
        return null;
    }

    public PhieuDatPhong timPhieuDatPhongBatBuoc(String ma) {
        PhieuDatPhong phieu = timPhieuDatPhong(ma);
        if (phieu == null) {
            phieu = timPhieuTheoMaPhong(ma);
        }
        if (phieu == null) {
            throw new Module4_Exception.PhieuDatPhongException(
                    "Không tìm thấy phiếu đặt phòng hoặc mã phòng tương ứng với từ khóa: '" + ma + "'.");
        }
        return phieu;
    }

    public void hienThiDanhSachPhieu() {
        ThongBao.inTieuDe("DANH SACH PHIEU DAT PHONG");
        if (dsPhieu.isEmpty()) {
            ThongBao.inCanhBao("Hien chua co phieu dat phong nao trong he thong.");
            return;
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-20s | %-8s | %-12s | %-12s | %-8s | %-16s |%n",
                "MA PHIEU", "KHACH HANG", "PHONG", "NGAY NHAN", "NGAY TRA", "SO DEM", "TIEN PHONG");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (PhieuDatPhong p : dsPhieu) {
            String hoten = (p.getKhachHang() != null && p.getKhachHang().gethoten() != null)
                    ? p.getKhachHang().gethoten()
                    : "N/A";
            System.out.printf("| %-8s | %-20s | %-8s | %-12s | %-12s | %-8d | %,16.0f |%n",
                    p.getMaPhieu(), hoten, p.getMaPhong(),
                    p.getNgayNhanStr(), p.getNgayTraStr(),
                    p.tinhSoNgayLuuTru(), p.tinhTienPhong());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // 2. Quản lý Dịch vụ phát sinh (ChiTietHoaDon từ Module 3)
    // ---------------------------------------------------------------

    public ChiTietHoaDon layChiTietDichVu(String maPhieu) {
        if (maPhieu == null) return null;
        ChiTietHoaDon cthd = mapChiTietDichVu.get(maPhieu);
        if (cthd == null) {
            PhieuDatPhong phieu = timPhieuDatPhong(maPhieu);
            if (phieu != null) {
                cthd = new ChiTietHoaDon(phieu);
                mapChiTietDichVu.put(maPhieu, cthd);
            }
        }
        return cthd;
    }

    public void themDichVuChoPhieu(Scanner sc) {
        ThongBao.inTieuDe("GOI THEM DICH VU CHO PHONG / PHIEU DAT");
        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu dat phong (hoac ma phong): ");
        try {
            PhieuDatPhong phieu = timPhieuDatPhongBatBuoc(ma);
            ChiTietHoaDon cthd = layChiTietDichVu(phieu.getMaPhieu());
            cthd.nhap(sc);

            HoaDon hd = timHoaDonTheoMaPhieu(phieu.getMaPhieu());
            if (hd != null) {
                hd.setChiTiet(cthd);
            }
            ThongBao.inThanhCong("Da cap nhat thanh cong dich vu cho phieu: " + phieu.getMaPhieu());
        } catch (Module4_Exception e) {
            ThongBao.inLoi(e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // 3. Quản lý Hóa đơn & Thanh toán (HoaDon + PhuongThucThanhToan)
    // ---------------------------------------------------------------

    public HoaDon timHoaDonTheoMaPhieu(String maPhieu) {
        if (maPhieu == null) return null;
        for (HoaDon hd : dsHoaDon) {
            if (hd.getPhieu() != null && hd.getPhieu().getMaPhieu() != null &&
                    hd.getPhieu().getMaPhieu().equalsIgnoreCase(maPhieu.trim())) {
                return hd;
            }
        }
        return null;
    }

    public HoaDon timHoaDonTheoMaHD(String maHoaDon) {
        if (maHoaDon == null) return null;
        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaHoaDon() != null && hd.getMaHoaDon().equalsIgnoreCase(maHoaDon.trim())) {
                return hd;
            }
        }
        return null;
    }

    public void thucHienThanhToanVaTraPhong(Scanner sc) {
        ThongBao.inTieuDe("THANH TOAN VA TRA PHONG (CHECK-OUT)");
        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu dat phong hoac ma phong can check-out: ");
        try {
            PhieuDatPhong phieu = timPhieuDatPhongBatBuoc(ma);
            ChiTietHoaDon cthd = layChiTietDichVu(phieu.getMaPhieu());
            HoaDon hd = timHoaDonTheoMaPhieu(phieu.getMaPhieu());
            if (hd == null) {
                hd = new HoaDon(phieu, cthd, 0.0);
                dsHoaDon.add(hd);
            } else {
                hd.setChiTiet(cthd);
                hd.capNhatHoaDon();
            }

            if (hd.isDaThanhToan()) {
                throw new Module4_Exception.ThanhToanKhongHopLeException(
                        "Hóa đơn [" + hd.getMaHoaDon() + "] của phiếu [" + phieu.getMaPhieu() + "] đã được thanh toán trước đó!");
            }

            PhuongThucThanhToan pttt = new PhuongThucThanhToan(hd);
            pttt.nhap(sc);
            pttt.xuat();
            ThongBao.inThanhCong("Da tra phong thanh cong! Phong [" + phieu.getMaPhong() + "] hien o trang thai 'Trong'.");
        } catch (Module4_Exception e) {
            ThongBao.inLoi(e.getMessage());
        }
    }

    public void hienThiDanhSachHoaDon() {
        ThongBao.inTieuDe("DANH SACH HOA DON");
        if (dsHoaDon.isEmpty()) {
            ThongBao.inCanhBao("Hien chua co hoa don nao.");
            return;
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-10s | %-12s | %-14s | %-14s | %-16s | %-16s |%n",
                "MA HOA DON", "MA PHIEU", "NGAY LAP", "TIEN PHONG", "TIEN DICH VU", "TONG CONG", "TRANG THAI");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        for (HoaDon hd : dsHoaDon) {
            String maPhieu = (hd.getPhieu() != null && hd.getPhieu().getMaPhieu() != null) ? hd.getPhieu().getMaPhieu() : "N/A";
            String trangThai = hd.isDaThanhToan() ? "DA THANH TOAN" : "CHUA THANH TOAN";
            System.out.printf("| %-12s | %-10s | %-12s | %,14.0f | %,14.0f | %,16.0f | %-16s |%n",
                    hd.getMaHoaDon(), maPhieu, hd.getNgayLapHoaDon(),
                    hd.getTienPhong(), hd.getTienDichVu(), hd.getTongTienPhong(), trangThai);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

    public double tinhTongDoanhThu() {
        double tong = 0.0;
        for (HoaDon hd : dsHoaDon) {
            if (hd.isDaThanhToan()) {
                tong += hd.getTongTienPhong();
            }
        }
        return tong;
    }

    public void thongKeDoanhThu() {
        ThongBao.inTieuDe("THONG KE DOANH THU KHACH SAN (MODULE 4)");
        int daTra = 0;
        int chuaTra = 0;
        double doanhThuPhong = 0.0;
        double doanhThuDichVu = 0.0;
        double tongDoanhThu = 0.0;

        for (HoaDon hd : dsHoaDon) {
            if (hd.isDaThanhToan()) {
                daTra++;
                doanhThuPhong += hd.getTienPhong();
                doanhThuDichVu += hd.getTienDichVu();
                tongDoanhThu += hd.getTongTienPhong();
            } else {
                chuaTra++;
            }
        }

        System.out.printf("  - Tong so phieu dat phong         : %d phieu%n", dsPhieu.size());
        System.out.printf("  - So hoa don da thanh toan        : %d hoa don%n", daTra);
        System.out.printf("  - So hoa don chua thanh toan      : %d hoa don%n", chuaTra);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("  + Doanh thu tien phong            : %,16.0f VND%n", doanhThuPhong);
        System.out.printf("  + Doanh thu dich vu phu thu       : %,16.0f VND%n", doanhThuDichVu);
        System.out.printf("  ==> TONG DOANH THU THUC TE THU VE : %,16.0f VND%n", tongDoanhThu);
        System.out.println("==================================================================");
    }

    // ---------------------------------------------------------------
    // Menu điều khiển tương tác console
    // ---------------------------------------------------------------
    public void menu(Scanner sc) {
        while (true) {
            ThongBao.inTieuDe("HE THONG QUAN LY DAT PHONG & HOA DON (MODULE 4)");
            System.out.println("  1. Tao phieu dat phong moi");
            System.out.println("  2. Xem danh sach tat ca phieu dat phong");
            System.out.println("  3. Goi them dich vu cho phong luu tru (Module 3)");
            System.out.println("  4. Xem chi tiet hoa don tam tinh");
            System.out.println("  5. Thanh toan & Tra phong (Check-out)");
            System.out.println("  6. Xem danh sach tat ca hoa don");
            System.out.println("  7. Thong ke & Tong hop doanh thu");
            System.out.println("  8. Doc du lieu tu file module4_data.txt");
            System.out.println("  9. Ghi / Luu du lieu ra file module4_data.txt");
            System.out.println("  0. Quay lai / Thoat");

            int chon = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Chon chuc nang (0 - 9): ", 0, 9);
            try {
                switch (chon) {
                    case 1:
                        taoPhieuDatPhongMoi(sc);
                        break;
                    case 2:
                        hienThiDanhSachPhieu();
                        break;
                    case 3:
                        themDichVuChoPhieu(sc);
                        break;
                    case 4: {
                        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phieu hoac ma hoa don: ");
                        HoaDon hd = timHoaDonTheoMaHD(ma);
                        if (hd == null) {
                            hd = timHoaDonTheoMaPhieu(ma);
                        }
                        if (hd != null) {
                            hd.xuat();
                        } else {
                            PhieuDatPhong p = timPhieuDatPhongBatBuoc(ma);
                            ChiTietHoaDon cthd = layChiTietDichVu(p.getMaPhieu());
                            HoaDon hdTam = new HoaDon(p, cthd, 0.0);
                            hdTam.xuat();
                        }
                        break;
                    }
                    case 5:
                        thucHienThanhToanVaTraPhong(sc);
                        break;
                    case 6:
                        hienThiDanhSachHoaDon();
                        break;
                    case 7:
                        thongKeDoanhThu();
                        break;
                    case 8:
                        docDuLieuTuFile(FILE_DU_LIEU_MAC_DINH);
                        break;
                    case 9:
                        ghiDuLieuRaFile(FILE_DU_LIEU_MAC_DINH);
                        break;
                    case 0:
                        ThongBao.inThanhCong("Da thoat Quan Ly Dat Phong.");
                        return;
                }
            } catch (Module4_Exception e) {
                ThongBao.inLoi(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuanLyDatPhong ql = new QuanLyDatPhong();
        ql.menu(sc);
    }
}
