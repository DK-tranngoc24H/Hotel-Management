import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String MAT_KHAU_NHAN_VIEN = "nv123";
    private static final String MAT_KHAU_QUAN_LY = "admin123";
    private static final double GIA_CO_BAN_STANDARD = 500000.0;
    private static final double GIA_CO_BAN_VIP = 1200000.0;

    private final Scanner sc = new Scanner(System.in);
    private final DanhSachNguoi qlNguoi = new DanhSachNguoi();
    private final DanhSachPhong qlPhong = new DanhSachPhong(sc);
    private final QuanLyLichSuDatPhong qlLichSu = new QuanLyLichSuDatPhong();
    private String vaiTroHienTai = "";

    public Main() {
        napDuLieuTuFile();
    }

    private void napDuLieuTuFile() {
        qlNguoi.docFile("ds_nguoi.txt");
        qlLichSu.docFile("Module4_QuanLyDatPhong/module4_data.txt", qlNguoi, qlPhong);
    }

    public void chayMenu() {
        while (true) {
            if (!dangNhap(sc)) {
                System.out.println("Da thoat chuong trinh.");
                return;
            }
            boolean dangXuat = false;
            while (!dangXuat) {
                String tenVaiTro = isQuanLy() ? "QUAN LY" : "NHAN VIEN";
                System.out.println("\n========== HOTEL MANAGEMENT - MODULE 5 [" + tenVaiTro + "] ==========");
                System.out.println("1. Quan ly khach hang");
                System.out.println("2. Quan ly phong");
                System.out.println("3. Dat phong - dich vu - thanh toan - lich su");
                System.out.println("4. Doi vai tro / Dang xuat");
                System.out.println("0. Thoat");
                int luaChon = KiemTraLoi.nhapSoNguyenDuong(sc, "Chon chuc nang: ");
                switch (luaChon) {
                    case 1:
                        menuKhachHang(sc);
                        break;
                    case 2:
                        menuPhong(sc);
                        break;
                    case 3:
                        menuDatPhong(sc);
                        break;
                    case 4:
                        dangXuat = true;
                        System.out.println("Da dang xuat khoi vai tro " + tenVaiTro + ".");
                        break;
                    case 0:
                        System.out.println("Da thoat chuong trinh.");
                        return;
                    default:
                        System.out.println("Lua chon khong hop le.");
                }
            }
        }
    }

    private boolean dangNhap(Scanner sc) {
        while (true) {
            System.out.println("\n========== DANG NHAP HE THONG - MODULE 5 ==========");
            System.out.println("1. Nhan vien (mat khau: nv123)");
            System.out.println("2. Quan ly   (mat khau: admin123)");
            System.out.println("0. Thoat");
            int chon = KiemTraLoi.nhapSoNguyenDuong(sc, "Chon vai tro: ");
            if (chon == 0) {
                return false;
            }
            if (chon != 1 && chon != 2) {
                System.out.println("Lua chon khong hop le!");
                continue;
            }
            String matKhau = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap mat khau: ");
            if (chon == 1 && MAT_KHAU_NHAN_VIEN.equals(matKhau)) {
                vaiTroHienTai = "NHAN_VIEN";
                System.out.println("-> Dang nhap thanh cong voi vai tro NHAN VIEN.");
                return true;
            }
            if (chon == 2 && MAT_KHAU_QUAN_LY.equals(matKhau)) {
                vaiTroHienTai = "QUAN_LY";
                System.out.println("-> Dang nhap thanh cong voi vai tro QUAN LY.");
                return true;
            }
            System.out.println("-> Sai mat khau! Vui long thu lai.");
        }
    }

    private boolean isQuanLy() {
        return "QUAN_LY".equals(vaiTroHienTai);
    }

    private void menuKhachHang(Scanner sc) {
        while (true) {
            System.out.println("\n--- QUAN LY KHACH HANG ---");
            System.out.println("1. Them khach hang");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Tim theo CCCD");
            System.out.println("4. Xoa khach hang");
            System.out.println("5. Ghi danh sach ra file (ds_nguoi.txt)");
            System.out.println("0. Quay lai");
            switch (KiemTraLoi.nhapSoNguyenDuong(sc, "Chon: ")) {
                case 1:
                    String cccd = nhapCCCDHopLe(sc);
                    if (qlNguoi.timKhachHangTheoCCCD(cccd) != null) {
                        System.out.println("-> Loi: Khach hang co CCCD " + cccd + " da ton tai!");
                        break;
                    }
                    String hoTen = nhapHoTenHopLe(sc);
                    String sdt = nhapSDTHopLe(sc);
                    String diaChi = nhapChuoiKhongDauChamPhay(sc, "Dia chi: ");
                    String loai = nhapLoaiKhachHopLe(sc);
                    KhachHang khMoi = new KhachHang(hoTen, cccd, sdt, diaChi, loai, null);
                    qlNguoi.themDoiTuong(khMoi);
                    qlNguoi.ghiFile("ds_nguoi.txt");
                    System.out.println("Them khach hang thanh cong.");
                    break;
                case 2:
                    List<KhachHang> dsKhach = qlNguoi.getDanhSachKhachHang();
                    if (dsKhach.isEmpty()) {
                        System.out.println("Chua co khach hang.");
                    } else {
                        int sttKhach = 1;
                        for (KhachHang khach : dsKhach) {
                            System.out.printf("[%d] CCCD: %s | %s | %s | %s | So phieu da dat: %d%n",
                                    sttKhach++, khach.getCCCD(), khach.gethoten(), khach.getSdt(),
                                    khach.getLoaikhach(), khach.getLichsudatphong().size());
                        }
                    }
                    break;
                case 3:
                    KhachHang khach = qlNguoi.timKhachHang(
                            KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap CCCD (hoac STT): "));
                    if (khach == null) {
                        System.out.println("Khong tim thay.");
                    } else {
                        khach.xuat();
                    }
                    break;
                case 4:
                    xoaKhachHang(sc);
                    break;
                case 5:
                    qlNguoi.ghiFile("ds_nguoi.txt");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        }
    }

    private void xoaKhachHang(Scanner sc) {
        if (qlNguoi.getDanhSachKhachHang().isEmpty()) {
            System.out.println("Chua co khach hang nao de xoa.");
            return;
        }
        String tuKhoa = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap CCCD (hoac STT) khach hang can xoa: ");
        KhachHang khach = qlNguoi.timKhachHang(tuKhoa);
        if (khach == null) {
            System.out.println("-> Loi: Khong tim thay khach hang '" + tuKhoa + "'.");
            return;
        }
        for (BanGhiLichSu bg : qlLichSu.timTheoCCCD(khach.getCCCD())) {
            if (bg.getTrangThai() == TrangThai.DAT_MOI || bg.getTrangThai() == TrangThai.DANG_O) {
                System.out.println("-> Loi: Khach hang '" + khach.gethoten()
                        + "' dang co phieu " + bg.getPhieu().getMaPhieu()
                        + " (" + bg.getTrangThai().getTenHienThi() + "), khong the xoa!");
                return;
            }
        }
        if (!KiemTraLoi.nhapCoKhong(sc, "Ban co chac chan muon xoa khach hang ["
                + khach.gethoten() + " - " + khach.getCCCD() + "]? (co/khong): ")) {
            System.out.println("Da huy thao tac xoa.");
            return;
        }
        qlNguoi.xoaDoiTuongTheoMa(khach.getCCCD());
        qlNguoi.ghiFile("ds_nguoi.txt");
        System.out.println("-> Da xoa khach hang thanh cong.");
    }

    private void menuPhong(Scanner sc) {
        while (true) {
            System.out.println("\n--- QUAN LY PHONG & TIEN NGHI ---");
            System.out.println("1. Xem danh sach phong");
            System.out.println("2. Xem chi tiet tien nghi cua phong");
            System.out.println("3. Cap nhat tien nghi phong [Quan ly]");
            System.out.println("4. Them phong Standard [Quan ly]");
            System.out.println("5. Them phong VIP [Quan ly]");
            System.out.println("6. Xoa phong [Quan ly]");
            System.out.println("7. Ghi danh sach phong ra file (phong.text) [Quan ly]");
            System.out.println("0. Quay lai");
            switch (KiemTraLoi.nhapSoNguyenDuong(sc, "Chon: ")) {
                case 1:
                    hienThiDanhSachPhong();
                    break;
                case 2:
                    xemTienNghiPhong(sc);
                    break;
                case 3:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc cap nhat tien nghi phong!");
                        break;
                    }
                    capNhatTienNghiPhong(sc);
                    break;
                case 4:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc them phong!");
                        break;
                    }
                    hienThiDanhSachPhongTheoLoai(false);
                    System.out.printf("-> Gia co ban co dinh phong Standard: %,.0f VND/dem%n", GIA_CO_BAN_STANDARD);
                    String maStd = nhapMaPhongMoi(sc);
                    boolean coGiuongPhu = KiemTraLoi.nhapCoKhong(sc, "Co giuong phu khong (+100,000 VND)? (co/khong): ");
                    PhongStandard phongStd = new PhongStandard(maStd, GIA_CO_BAN_STANDARD, "Trong", coGiuongPhu);
                    if (KiemTraLoi.nhapCoKhong(sc, "Ban co muon tuy chinh tien nghi rieng khong? (co/khong): ")) {
                        phongStd.getTienNghi().nhap(sc);
                    }
                            
                    qlPhong.them(phongStd);
                    qlPhong.ghiFileMacDinh();
                    System.out.println("Them phong Standard thanh cong.");
                    break;
                case 5:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc them phong!");
                        break;
                    }
                    hienThiDanhSachPhongTheoLoai(true);
                    System.out.printf("-> Gia co ban co dinh phong VIP: %,.0f VND/dem%n", GIA_CO_BAN_VIP);
                    String maVip = nhapMaPhongMoi(sc);
                    String view = KiemTraLoi.nhapLoaiView(sc);
                    double phiDv = KiemTraLoi.nhapSoThucDuong(sc, "Phi dich vu dac biet rieng cua phong VIP (VND): ");
                    PhongVIP phongVip = new PhongVIP(maVip, GIA_CO_BAN_VIP, "Trong", view, phiDv);
                    if (KiemTraLoi.nhapCoKhong(sc, "Ban co muon tuy chinh tien nghi rieng khong? (co/khong): ")) {
                        phongVip.getTienNghi().nhap(sc);
                    }
                    qlPhong.them(phongVip);
                    qlPhong.ghiFileMacDinh();
                    System.out.println("Them phong VIP thanh cong.");
                    break;
                case 6:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc xoa phong!");
                        break;
                    }
                    qlPhong.xoaPhong();
                    qlPhong.ghiFileMacDinh();
                    break;
                case 7:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc ghi file phong!");
                        break;
                    }
                    qlPhong.ghiFileMacDinh();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        }
    }

    private void hienThiDanhSachPhongTheoLoai(boolean laVIP) {
        String tieuDe = laVIP ? "DANH SACH PHONG VIP HIEN CO" : "DANH SACH PHONG STANDARD HIEN CO";
        System.out.println("\n--- " + tieuDe + " ---");
        StringBuilder sb = new StringBuilder();
        for (Phong p : qlPhong.getDanhSach()) {
            boolean khop = laVIP ? (p instanceof PhongVIP) : (p instanceof PhongStandard);
            if (khop) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(p.getMaPhong()).append(" (").append(p.getTrangThai()).append(")");
            }
        }
        System.out.println(sb.length() > 0 ? "Cac ma phong da co: " + sb : "(Chua co phong nao thuoc loai nay)");
    }

    private void xemTienNghiPhong(Scanner sc) {
        String maPhong = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong can xem tien nghi: ");
        Phong p = qlPhong.timKiemTheoMaPhong(maPhong);
        if (p == null) {
            System.out.println("-> Loi: Khong tim thay phong '" + maPhong + "'.");
            return;
        }
        p.xuat();
    }

    private void capNhatTienNghiPhong(Scanner sc) {
        String maPhong = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong can cap nhat tien nghi: ");
        Phong p = qlPhong.timKiemTheoMaPhong(maPhong);
        if (p == null) {
            System.out.println("-> Loi: Khong tim thay phong '" + maPhong + "'.");
            return;
        }
        if (p.getTienNghi() == null) {
            p.setTienNghi(new TienNghiPhong());
        }
        System.out.println("Thong tin tien nghi hien tai cua phong " + p.getMaPhong() + ":");
        p.getTienNghi().xuat();
        System.out.println("\nNhap thong tin tien nghi moi:");
        p.getTienNghi().nhap(sc);
        System.out.println("-> Da cap nhat tien nghi cho phong " + p.getMaPhong() + " thanh cong.");
    }

    private void hienThiDanhSachPhong() {
        System.out.println("\n==================================================================== DANH SACH PHONG ====================================================================");
        Phong[] dsPhong = qlPhong.getDanhSach();
        if (dsPhong.length == 0) {
            System.out.println("Chua co phong.");
            return;
        }
        String duong = "+----------+----------+--------------+-----------------------+-------------+---------------+------------+--------------------------------------------------------+";
        System.out.println(duong);
        System.out.printf("| %-8s | %-8s | %-12s | %-21s | %-11s | %-13s | %-10s | %-54s |%n",
                "MA PHONG", "LOAI", "GIA CO BAN", "PHU THU VIEW / GIUONG", "PHI DV VIP", "TONG GIA/DEM", "TRANG THAI", "TIEN NGHI");
        System.out.println(duong);
        for (Phong p : dsPhong) {
            String loai;
            String phuThu;
            double phiDvVip = 0.0;
            if (p instanceof PhongVIP) {
                PhongVIP vip = (PhongVIP) p;
                loai = "VIP";
                phuThu = String.format("%s (+%,.0f)", vip.getLoaiView(), vip.tinhPhuThuView());
                phiDvVip = vip.getPhiDichVuDacBiet();
            } else if (p instanceof PhongStandard) {
                PhongStandard std = (PhongStandard) p;
                loai = "Standard";
                phuThu = std.isCoGiuongPhu() ? "Giuong phu (+100,000)" : "Khong (0)";
            } else {
                loai = "Phong";
                phuThu = "-";
            }
            String tienNghi = (p.getTienNghi() != null)
                    ? p.getTienNghi().getTenTienNghi() + " [" + p.getTienNghi().getTinhTrang() + "]"
                    : "Chua co";
            System.out.printf("| %-8s | %-8s | %,12.0f | %-21s | %,11.0f | %,13.0f | %-10s | %-54s |%n",
                    p.getMaPhong(), loai, p.getGiaCoBan(), phuThu, phiDvVip, p.tinhGiaPhong(), p.getTrangThai(), tienNghi);
        }
        System.out.println(duong);
    }

    private void menuDatPhong(Scanner sc) {
        while (true) {
                
            System.out.println("\n--- DAT PHONG VA LICH SU ---");
            System.out.println("1. Tao phieu dat phong");
            System.out.println("2. Them dich vu cho phieu dat");
            System.out.println("3. Nhan phong");
            System.out.println("4. Thanh toan - tra phong");
            System.out.println("5. Huy dat phong");
            System.out.println("6. Xem lich su va doanh thu [Quan ly]");
            System.out.println("7. Xem danh sach dich vu");
            System.out.println("8. Quan ly dich vu khach san (dichvu.txt) [Quan ly]");
                
            System.out.println("9. Ghi du lieu dat phong ra file (module4_data.txt)");
            System.out.println("0. Quay lai");
            switch (KiemTraLoi.nhapSoNguyenDuong(sc, "Chon: ")) {
                case 1:
                    taoPhieu(sc);
                    break;
                case 2:
                    themDichVuChoKhach(sc);
                    break;
                case 3:
                    String maNhan = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phieu: ");
                    System.out.println(qlLichSu.batDauLuuTru(maNhan)
                            ? "Da nhan phong."
                            : "Khong the nhan phong (ma phieu khong ton tai hoac trang thai khong hop le).");
                    break;
                case 4:
                    thanhToanChoKhach(sc);
                    break;
                case 5:
                    String maHuy = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phieu: ");
                    BanGhiLichSu bgHuy = qlLichSu.timTheoMaPhieu(maHuy);
                    if (bgHuy == null) {
                        System.out.println("Khong tim thay ma phieu '" + maHuy + "'.");
                    
                    } else if (bgHuy.getTrangThai() == TrangThai.DANG_O) {
                        System.out.println("-> Loi: Khong the huy phieu dang luu tru (DANG_O)! Vui long thanh toan - tra phong.");
                    } else if (qlLichSu.huyDatPhong(maHuy)) {
                        System.out.println("Da huy dat phong.");
                    } else {
                        System.out.println("Khong the huy phieu o trang thai " + bgHuy.getTrangThai().getTenHienThi() + ".");
                    }
                    break;
                case 6:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc xem bao cao lich su va doanh thu!");
                        break;
                    }
                    qlLichSu.hienThiLichSu();
                    System.out.printf("Doanh thu da thanh toan: %,.0f VND%n", qlLichSu.tinhDoanhThuDaThanhToan());
                    break;
                case 7:
                    new DanhSachDichVu().hienThi();
                    break;
                case 8:
                    if (!isQuanLy()) {
                        System.out.println("-> Tu choi quyen: Chi QUAN LY moi duoc quan ly dich vu khach san!");
                        break;
                    }
                    menuQuanLyDichVuKhachSan(sc);
                    break;
                case 9:
                    qlLichSu.ghiFile("Module4_QuanLyDatPhong/module4_data.txt");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        }
    }

    private void taoPhieu(Scanner sc) {
        System.out.println("\n--- TAO PHIEU DAT PHONG ---");
        boolean coPhongTrong = false;
        for (Phong p : qlPhong.getDanhSach()) {
            if (p.conTrong()) {
                coPhongTrong = true;
                                
                break;
            }
        }
        if (!coPhongTrong) {
            System.out.println("Hien tai da het phong trong de dat!");
            return;
        }

        hienThiDanhSachPhong();

        // 1. Nhap truc tiep khach hang (neu trung CCCD co san thi dung lai)
        System.out.println("\n[Buoc 1] Thong tin khach hang:");
        String cccd = nhapCCCDHopLe(sc);
        KhachHang khach = qlNguoi.timKhachHangTheoCCCD(cccd);
        if (khach != null) {
            System.out.println("-> Tim thay khach hang co san: " + khach.gethoten()
                    + " (CCCD: " + khach.getCCCD() + ", SDT: " + khach.getSdt() + "). Dung lai thong tin nay.");
        } else {
            System.out.println("-> Khach hang moi. Vui long nhap thong tin:");
            String hoTen = nhapHoTenHopLe(sc);
            String sdt = nhapSDTHopLe(sc);
            String diaChi = nhapChuoiKhongDauChamPhay(sc, "Dia chi: ");
            String loai = nhapLoaiKhachHopLe(sc);
            khach = new KhachHang(hoTen, cccd, sdt, diaChi, loai, null);
            qlNguoi.themDoiTuong(khach);
            qlNguoi.ghiFile("ds_nguoi.txt");
            System.out.println("-> Da them khach hang moi vao he thong.");
        }

        // 2. Chon phong trong (sai bat nhap lai)
        System.out.println("\n[Buoc 2] Chon phong:");
        Phong phong;
        while (true) {
            String maPhong = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phong trong: ");
            phong = qlPhong.timKiemTheoMaPhong(maPhong);
            if (phong == null) {
                System.out.println("-> Loi: Phong '" + maPhong + "' khong ton tai! Vui long nhap lai.");
            } else if (!phong.conTrong()) {
                System.out.println("-> Loi: Phong '" + maPhong + "' khong con trong (Trang thai: " + phong.getTrangThai() + "). Vui long chon phong khac.");
            } else {
                System.out.println("-> Da chon phong: " + phong.getMaPhong() + " (Gia: " + String.format("%,.0f", phong.tinhGiaPhong()) + " VND/dem)");
                break;
            }
        }

        // 3. Nhap ma phieu (kiem tra trung truoc khi tao PhieuDatPhong de khong doi trang thai phong)
        System.out.println("\n[Buoc 3] Thong tin phieu dat:");
        String ma;
        while (true) {
            ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phieu: ");
            if (qlLichSu.timTheoMaPhieu(ma) != null) {
                System.out.println("-> Loi: Ma phieu '" + ma + "' da ton tai! Vui long nhap ma khac.");
            } else {
                break;
            }
        }

        // 4. Nhap ngay dat / nhan / tra (bat Module4_Exception va nhap lai ngay)
        LocalDate ngayDat = nhapNgayHopLe(sc, "Ngay dat (d/M/yyyy): ", null, null, false);
        LocalDate ngayNhan = nhapNgayHopLe(sc, "Ngay nhan (d/M/yyyy): ", ngayDat, null, true);
        LocalDate ngayTra = nhapNgayHopLe(sc, "Ngay tra (d/M/yyyy): ", ngayNhan, null, true);

        try {
            PhieuDatPhong phieu = new PhieuDatPhong(ma, ngayDat, ngayNhan, ngayTra, khach, phong);
            if (qlLichSu.themPhieuDatPhong(phieu)) {
                System.out.println("-> Tao phieu dat phong thanh cong! (Da ghi vao lich su cua khach hang).");
            } else {
                System.out.println("-> Khong the them phieu dat phong.");
            }
        } catch (Module4_Exception ex) {
            System.out.println("-> Loi nghiep vu dat phong: " + ex.getMessage());
        }
    }

    private void themDichVuChoKhach(Scanner sc) {
        String maPhieu = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phieu: ");
        BanGhiLichSu banGhi = qlLichSu.timTheoMaPhieu(maPhieu);
                        
        if (banGhi == null) {
            System.out.println("-> Loi: Khong tim thay phieu dat phong '" + maPhieu + "'.");
            return;
        }
        if (banGhi.getTrangThai() == TrangThai.DA_HUY || banGhi.getTrangThai() == TrangThai.DA_TRA_PHONG) {
            System.out.println("-> Loi: Khong the them dich vu cho phieu o trang thai "
                    + banGhi.getTrangThai().getTenHienThi() + ".");
        // 
            return;
        }

        DanhSachDichVu dsDV = new DanhSachDichVu();
        List<DichVu> dsDichVu = dsDV.getDs();
        if (dsDichVu.isEmpty()) {
            System.out.println("-> Loi: Danh sach dich vu dang trong.");
            return;
        }
        dsDV.hienThi();
        QuanLyDichVu qlDV = new QuanLyDichVu(dsDV, new NhapLieu(sc));

        while (true) {
            DichVu dvChon = null;
            while (dvChon == null) {
                String chon = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap Ma DV hoac STT dich vu: ");
                try {
                    int stt = Integer.parseInt(chon);
                    if (stt >= 1 && stt <= dsDichVu.size()) {
                        dvChon = dsDichVu.get(stt - 1);
                    } else {
                        System.out.println("-> Loi: STT phai nam trong khoang 1.." + dsDichVu.size() + ".");
                    }
                } catch (NumberFormatException ignored) {
                    dvChon = qlDV.timTheoMa(chon);
                    if (dvChon == null) {
                        System.out.println("-> Loi: Khong tim thay dich vu '" + chon + "'. Vui long nhap lai.");
                    }
                }
            }

            int soLuong = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "So luong cho [" + dvChon.getTenDV() + "]: ", 1, Integer.MAX_VALUE);

            if (qlLichSu.themDichVu(maPhieu, dvChon.getMaDV(), soLuong)) {
                System.out.println("-> Da them " + soLuong + " x [" + dvChon.getTenDV() + "] vao phieu " + maPhieu + ".");
            } else {
                System.out.println("-> Khong them duoc dich vu.");
            }

            if (!KiemTraLoi.nhapCoKhong(sc, "Ban co muon them dich vu khac cho phieu nay khong? (co/khong): ")) {
                break;
            }
        }
    }

    private void menuQuanLyDichVuKhachSan(Scanner sc) {
        DanhSachDichVu dsDV = new DanhSachDichVu();
        QuanLyDichVu qlDV = new QuanLyDichVu(dsDV, new NhapLieu(sc));
        while (true) {
            System.out.println("\n--- QUAN LY DANH MUC DICH VU KHACH SAN ---");
            System.out.println("1. Xem danh sach dich vu");
            System.out.println("2. Them dich vu moi");
            System.out.println("3. Sua dich vu theo ma");
            System.out.println("4. Xoa dich vu theo ma");
            System.out.println("5. Ghi danh sach dich vu ra file (dichvu.txt)");
            System.out.println("0. Quay lai");
            switch (KiemTraLoi.nhapSoNguyenDuong(sc, "Chon: ")) {
                case 1:
                    dsDV.hienThi();
                    break;
                case 2:
                    qlDV.them();
                    break;
                case 3:
                    dsDV.hienThi();
                    qlDV.sua();
                    break;
                case 4:
                    dsDV.hienThi();
                    qlDV.xoa();
                    
                    break;
                case 5:
                    dsDV.ghiFile();
                    System.out.println("-> Da luu danh sach dich vu ra file dichvu.txt.");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
                    break;
            }
        }
    }

    private void thanhToanChoKhach(Scanner sc) {
        String maPhieu = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phieu: ");
        BanGhiLichSu banGhi = qlLichSu.timTheoMaPhieu(maPhieu);
        if (banGhi == null) {
            System.out.println("-> Loi: Khong tim thay ma phieu '" + maPhieu + "'.");
            return;
        }
        if (banGhi.getTrangThai() == TrangThai.DAT_MOI) {
            System.out.println("-> Loi: Phieu chua nhan phong (DAT_MOI), khong the thanh toan! Vui long nhan phong truoc.");
            return;
        }
        if (banGhi.getTrangThai() != TrangThai.DANG_O) {
            System.out.println("-> Loi: Khong the thanh toan phieu o trang thai " + banGhi.getTrangThai().getTenHienThi() + ".");
            return;
        }

        String pttt = KiemTraLoi.nhapChuoiKhongRong(sc, "Phuong thuc thanh toan (Tien mat / Chuyen khoan / The tin dung): ");
        if (qlLichSu.thanhToanVaTraPhong(maPhieu, pttt)) {
            System.out.println("-> Da thanh toan va tra phong thanh cong.");
            if (banGhi.getHoaDon() != null) {
                banGhi.getHoaDon().xuat();
            }
        } else {
            System.out.println("-> Khong the thanh toan.");
        }
    }

    private LocalDate nhapNgayHopLe(Scanner sc, String loiNhac, LocalDate ngayTruoc, LocalDate ngaySau, boolean kiemTraMoc) {
        while (true) {
            String raw = KiemTraLoi.nhapChuoiKhongRong(sc, loiNhac);
            try {
                LocalDate date = PhieuDatPhong.phanTichNgay(raw);
                if (kiemTraMoc) {
                    if (ngayTruoc != null) {
                        PhieuDatPhong.kiemTraLogicNgay(ngayTruoc, date, ngaySau);
                    }
                }
                return date;
            } catch (Module4_Exception ex) {
                System.out.println("-> Loi ngay thang: " + ex.getMessage() + " Vui long nhap lai.");
            }
        }
    }

    private String nhapHoTenHopLe(Scanner sc) {
        while (true) {
            String hoTen = KiemTraLoi.nhapChuoiKhongRong(sc, "Ho ten: ");
            if (Nguoi.kiemTraHoTen(hoTen)) {
                return hoTen;
            }
            System.out.println(
                    "-> Loi: Ho ten chi duoc chua chu cai va khoang trang (khong chua so hoac ky tu dac biet)!");
        }
    }

    private String nhapCCCDHopLe(Scanner sc) {
                
        while (true) {
            String cccd = KiemTraLoi.nhapChuoiKhongRong(sc, "CCCD (12 chu so): ");
            if (cccd.matches("\\d{12}")) {
                return cccd;
            }
            System.out.println("-> Loi: CCCD khong hop le! Phai gom dung 12 chu so.");
        }
    }

    private String nhapSDTHopLe(Scanner sc) {
        while (true) {
            
            String sdt = KiemTraLoi.nhapChuoiKhongRong(sc, "So dien thoai (10 chu so, bat dau bang 0): ");
            if (Nguoi.kiemTraSDT(sdt)) {
                return sdt;
            }
            System.out.println("-> Loi: So dien thoai khong hop le! Phai gom 10 chu so va bat dau bang 0.");
        }
    }

    private String nhapLoaiKhachHopLe(Scanner sc) {
        while (true) {
            String loai = KiemTraLoi.nhapChuoiKhongRong(sc, "Loai khach (Thuong / VIP): ");
            if (loai.equalsIgnoreCase("Thuong")) return "Thuong";
            if (loai.equalsIgnoreCase("VIP")) return "VIP";
            System.out.println("-> Loi: Chi chap nhan 'Thuong' hoac 'VIP'.");
        }
    }

    private String nhapChuoiKhongDauChamPhay(Scanner sc, String loiNhac) {
        while (true) {
            String s = KiemTraLoi.nhapChuoiKhongRong(sc, loiNhac);
            if (!s.contains(";")) {
                return s;
            }
                    
            System.out.println("-> Loi: Khong duoc chua ky tu ';'.");
        }
    }

    private String nhapMaPhongMoi(Scanner sc) {
        while (true) {
            String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Ma phong: ");
            if (qlPhong.timKiemTheoMaPhong(ma) == null) {
                return ma;
            }
            System.out.println("-> Loi: Ma phong '" + ma + "' da ton tai!");
        }
    }

    public static void main(String[] args) {
        new Main().chayMenu();
    }
}

            
                
                

    