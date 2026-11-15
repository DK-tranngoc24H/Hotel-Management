import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Xu ly toan bo nghiep vu Module 5: luu tru, tra cuu, cap nhat trang thai va doanh thu.
 */
public class QuanLyLichSuDatPhong {
    private static final DateTimeFormatter DINH_DANG_NGAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final List<BanGhiLichSu> lichSu = new ArrayList<>();

    public List<BanGhiLichSu> getLichSu() {
        return Collections.unmodifiableList(lichSu);
    }

    public boolean themPhieuDatPhong(PhieuDatPhong phieu) {
        if (phieu == null || phieu.getMaPhieu() == null || timTheoMaPhieu(phieu.getMaPhieu()) != null) {
            return false;
        }
        lichSu.add(new BanGhiLichSu(phieu));
        if (phieu.getPhong() != null) {
            phieu.getPhong().setTrangThai("Da dat");
        }
        if (phieu.getKhachHang() != null) {
            phieu.getKhachHang().themLichSuDatPhong(phieu);
        }
        return true;
    }

    public BanGhiLichSu timTheoMaPhieu(String maPhieu) {
        if (maPhieu == null) {
            return null;
        }
        for (BanGhiLichSu banGhi : lichSu) {
            if (maPhieu.trim().equalsIgnoreCase(banGhi.getPhieu().getMaPhieu())) {
                return banGhi;
            }
        }
        return null;
    }

    public BanGhiLichSu timTheoMaPhong(String maPhong) {
        if (maPhong == null) {
            return null;
        }
        for (BanGhiLichSu banGhi : lichSu) {
            if (maPhong.trim().equalsIgnoreCase(banGhi.getPhieu().getMaPhong())) {
                return banGhi;
            }
        }
        return null;
    }

    public List<BanGhiLichSu> timTheoCCCD(String cccd) {
        List<BanGhiLichSu> ketQua = new ArrayList<>();
        if (cccd == null) {
            return ketQua;
        }
        for (BanGhiLichSu banGhi : lichSu) {
            KhachHang khach = banGhi.getPhieu().getKhachHang();
            if (khach != null && cccd.trim().equalsIgnoreCase(khach.getCCCD())) {
                ketQua.add(banGhi);
            }
        }
        return ketQua;
    }

    public boolean themDichVu(String maPhieu, String maDichVu, int soLuong) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() == TrangThai.DA_HUY
                || banGhi.getTrangThai() == TrangThai.DA_TRA_PHONG) {
            return false;
        }
        return banGhi.getChiTietDichVu().themDichVu(maDichVu, soLuong);
    }

    public HoaDon taoHoaDon(String maPhieu, double giamGia) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null) {
            return null;
        }
        if (banGhi.getHoaDon() == null) {
            String maHoaDon = "HD-" + banGhi.getPhieu().getMaPhieu();
            banGhi.setHoaDon(new HoaDon(maHoaDon, ngayHienTai(), banGhi.getPhieu(), banGhi.getChiTietDichVu(), giamGia));
        } else {
            banGhi.getHoaDon().setChiTiet(banGhi.getChiTietDichVu());
            banGhi.getHoaDon().capNhatHoaDon();
        }
        return banGhi.getHoaDon();
    }

    public boolean batDauLuuTru(String maPhieu) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() == TrangThai.DA_HUY
                || banGhi.getTrangThai() == TrangThai.DA_TRA_PHONG) {
            return false;
        }
        banGhi.setTrangThai(TrangThai.DANG_O);
        if (banGhi.getPhieu().getPhong() != null) {
            banGhi.getPhieu().getPhong().setTrangThai("Dang o");
        }
        return true;
    }

    public boolean thanhToanVaTraPhong(String maPhieu, String phuongThucThanhToan) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() != TrangThai.DANG_O) {
            return false;
        }
        HoaDon hoaDon = taoHoaDon(maPhieu, 0.0);
        if (hoaDon != null) {
            hoaDon.setDaThanhToan(true);
        }
        String pttt = (phuongThucThanhToan == null || phuongThucThanhToan.trim().isEmpty())
                ? "Khong xac dinh" : phuongThucThanhToan.trim();
        banGhi.setPhuongThucThanhToan(pttt);
        banGhi.setTrangThai(TrangThai.DA_TRA_PHONG);
        if (banGhi.getPhieu().getPhong() != null) {
            banGhi.getPhieu().getPhong().setTrangThai("Trong");
        }
        return true;
    }

    public boolean huyDatPhong(String maPhieu) {
        BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
        if (banGhi == null || banGhi.getTrangThai() != TrangThai.DAT_MOI) {
            return false;
        }
        banGhi.setTrangThai(TrangThai.DA_HUY);
        if (banGhi.getPhieu().getPhong() != null) {
            banGhi.getPhieu().getPhong().setTrangThai("Trong");
        }
        return true;
    }

    public double tinhDoanhThuDaThanhToan() {
        double tong = 0.0;
        for (BanGhiLichSu banGhi : lichSu) {
            if (banGhi.getHoaDon() != null && banGhi.getHoaDon().isDaThanhToan()) {
                tong += banGhi.getHoaDon().getTongTienPhong();
            }
        }
        return tong;
    }

    public void hienThiLichSu() {
        System.out.println("\n================ LICH SU DAT PHONG - MODULE 5 ================");
        if (lichSu.isEmpty()) {
            System.out.println("Chua co du lieu lich su.");
            return;
        }
        System.out.printf("%-10s | %-20s | %-10s | %-14s | %-16s | %-16s%n",
                "MA PHIEU", "KHACH HANG", "MA PHONG", "TRANG THAI", "TONG TIEN", "THANH TOAN");
        for (BanGhiLichSu banGhi : lichSu) {
            String tenKhach = banGhi.getPhieu().getKhachHang() == null ? "N/A"
                    : banGhi.getPhieu().getKhachHang().gethoten();
            String tongTien = banGhi.getHoaDon() == null ? "Chua lap"
                    : String.format("%,.0f VND", banGhi.getHoaDon().getTongTienPhong());
            String thanhToan = banGhi.getHoaDon() != null && banGhi.getHoaDon().isDaThanhToan()
                    ? "Da thanh toan" : "Chua thanh toan";
            System.out.printf("%-10s | %-20s | %-10s | %-14s | %-16s | %-16s%n",
                    banGhi.getPhieu().getMaPhieu(), tenKhach, banGhi.getPhieu().getMaPhong(),
                    banGhi.getTrangThai().getTenHienThi(), tongTien, thanhToan);
        }
    }

    public void ghiFile(String duongDan) {
        File file = new File(duongDan);
        if (!file.exists() && new File("module4_data.txt").exists()) {
            file = new File("module4_data.txt");
        }
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            bw.write("# maPhieu;ngayDat;ngayNhan;ngayTra;hoten;CCCD;sdt;diachi;loaikhach;maPhong;loaiPhong;giaCoBan;thuocTinhPhong;phiDichVuDacBiet;dsDichVu;giamGia;daThanhToan;trangThai;phuongThucThanhToan\n");
            for (BanGhiLichSu bg : lichSu) {
                PhieuDatPhong p = bg.getPhieu();
                KhachHang kh = p.getKhachHang();
                Phong phong = p.getPhong();
                String loaiPhong = (phong instanceof PhongVIP) ? "VIP" : "Standard";
                double giaCoBan = (phong != null) ? phong.getGiaCoBan() : 0.0;
                String thuocTinhPhong = "false";
                double phiDvVip = 0.0;
                if (phong instanceof PhongVIP) {
                    thuocTinhPhong = ((PhongVIP) phong).getLoaiView();
                    phiDvVip = ((PhongVIP) phong).getPhiDichVuDacBiet();
                } else if (phong instanceof PhongStandard) {
                    thuocTinhPhong = String.valueOf(((PhongStandard) phong).isCoGiuongPhu());
                }

                StringBuilder sbDV = new StringBuilder();
                List<ChiTietHoaDon.ChiTietDichVu> dsDV = bg.getChiTietDichVu().getDanhSachChiTiet();
                if (dsDV.isEmpty()) {
                    sbDV.append("NONE");
                } else {
                    for (int i = 0; i < dsDV.size(); i++) {
                        if (i > 0) sbDV.append(",");
                        sbDV.append(dsDV.get(i).getDichVu().getMaDV())
                                .append(":").append(dsDV.get(i).getSoLuong());
                    }
                }

                HoaDon hd = bg.getHoaDon();
                double giamGia = (hd != null) ? hd.getGiamGia() : 0.0;
                boolean daThanhToan = (hd != null) && hd.isDaThanhToan();
                String pttt = (bg.getPhuongThucThanhToan() != null) ? bg.getPhuongThucThanhToan() : "Chua thanh toan";

                bw.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%.0f;%s;%.0f;%s;%.0f;%b;%s;%s%n",
                        p.getMaPhieu(), p.getNgayDatStr(), p.getNgayNhanStr(), p.getNgayTraStr(),
                        (kh != null ? kh.gethoten() : "N/A"),
                        (kh != null ? kh.getCCCD() : "N/A"),
                        (kh != null ? kh.getSdt() : "N/A"),
                        (kh != null ? kh.getDiachi() : "N/A"),
                        (kh != null ? kh.getLoaikhach() : "Thuong"),
                        p.getMaPhong(), loaiPhong, giaCoBan, thuocTinhPhong, phiDvVip,
                        sbDV.toString(), giamGia, daThanhToan, bg.getTrangThai().name(), pttt));
            }
            System.out.println("-> Da ghi " + lichSu.size() + " ban ghi dat phong ra file: " + file.getPath());
        } catch (IOException e) {
            System.out.println("-> Loi ghi file dat phong: " + e.getMessage());
        }
    }

    public void docFile(String duongDan, DanhSachNguoi qlNguoi, DanhSachPhong qlPhong) {
        File file = new File(duongDan);
        if (!file.exists() && new File("module4_data.txt").exists()) {
            file = new File("module4_data.txt");
        }
        if (!file.exists()) {
            System.out.println("-> Canh bao: Khong tim thay file dat phong: " + duongDan);
            return;
        }

        lichSu.clear();
        int soBanGhi = 0;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\uFEFF", "").trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] p = line.split(";", -1);
                if (p.length < 17) {
                    continue;
                }

                try {
                    String maPhieu = p[0].trim();
                    String ngayDat = p[1].trim();
                    String ngayNhan = p[2].trim();
                    String ngayTra = p[3].trim();

                    String hoten = p[4].trim();
                    String cccd = p[5].trim();
                    String sdt = p[6].trim();
                    String diachi = p[7].trim();
                    String loaikhach = p[8].trim();

                    KhachHang khach = (qlNguoi != null) ? qlNguoi.timKhachHangTheoCCCD(cccd) : null;
                    if (khach == null) {
                        khach = new KhachHang(hoten, cccd, sdt, diachi, loaikhach, null);
                    }

                    String maPhong = p[9].trim();
                    Phong phong = (qlPhong != null) ? qlPhong.timKiemTheoMaPhong(maPhong) : null;
                    if (phong == null) {
                        String loaiPhong = p[10].trim();
                        double giaCoBan = Double.parseDouble(p[11].trim());
                        String thuocTinhPhong = p[12].trim();
                        double phiDvVip = Double.parseDouble(p[13].trim());
                        phong = "VIP".equalsIgnoreCase(loaiPhong)
                                ? new PhongVIP(maPhong, giaCoBan, "Trong", thuocTinhPhong, phiDvVip)
                                : new PhongStandard(maPhong, giaCoBan, "Trong", Boolean.parseBoolean(thuocTinhPhong));
                    }

                    PhieuDatPhong phieu = new PhieuDatPhong(maPhieu, ngayDat, ngayNhan, ngayTra, khach, phong);
                    if (!themPhieuDatPhong(phieu)) {
                        continue;
                    }

                    String chuoiDichVu = p[14].trim();
                    if (!chuoiDichVu.isEmpty() && !"NONE".equalsIgnoreCase(chuoiDichVu)) {
                        for (String cap : chuoiDichVu.split(",")) {
                            String[] kv = cap.trim().split(":");
                            if (kv.length == 2) {
                                themDichVu(maPhieu, kv[0].trim(), Integer.parseInt(kv[1].trim()));
                            }
                        }
                    }

                    double giamGia = Double.parseDouble(p[15].trim());
                    boolean daThanhToan = Boolean.parseBoolean(p[16].trim());

                    TrangThai trangThai = daThanhToan ? TrangThai.DA_TRA_PHONG : TrangThai.DAT_MOI;
                    if (p.length >= 18 && !p[17].trim().isEmpty()) {
                        try {
                            trangThai = TrangThai.valueOf(p[17].trim());
                        } catch (IllegalArgumentException ignored) {
                            // Du lieu goc cua Module 4 luu loai thanh toan o cot nay
                        }
                    }

                    BanGhiLichSu bg = timTheoMaPhieu(maPhieu);
                    if (bg != null) {
                        bg.setTrangThai(trangThai);
                        if (p.length >= 19 && !p[18].trim().isEmpty()) {
                            bg.setPhuongThucThanhToan(p[18].trim());
                        }
                        if (daThanhToan || trangThai == TrangThai.DA_TRA_PHONG) {
                            HoaDon hd = taoHoaDon(maPhieu, giamGia);
                            if (hd != null) {
                                hd.setDaThanhToan(daThanhToan);
                            }
                        }
                    }

                    if (trangThai == TrangThai.DANG_O) {
                        phong.setTrangThai("Dang o");
                    } else if (trangThai == TrangThai.DAT_MOI) {
                        phong.setTrangThai("Da dat");
                    } else {
                        phong.setTrangThai("Trong");
                    }
                    soBanGhi++;
                } catch (Exception ignored) {
                    // Bo qua dong loi dinh dang de khong gian doan khoi tao
                }
            }
            System.out.println("-> Da doc " + soBanGhi + " ban ghi dat phong tu file: " + file.getPath());
        } catch (IOException e) {
            System.out.println("-> Loi doc file dat phong: " + e.getMessage());
        }
    }

    private String ngayHienTai() {
        return LocalDate.now().format(DINH_DANG_NGAY);
    }
}