import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final QuanLyLichSuDatPhong qlLichSu = new QuanLyLichSuDatPhong();
    private final List<KHACHHANG> danhSachKhachHang = new ArrayList<>();
    private final List<Phong> danhSachPhong = new ArrayList<>();

    public Main() {
        khoiTaoDuLieuMau();
    }

    private void khoiTaoDuLieuMau() {
        // 1. Khach hang mau
        KHACHHANG kh1 = new KHACHHANG("Nguyen Van An", "012345678901", "0901234567", "Ha Noi", "Thuong", null);
        KHACHHANG kh2 = new KHACHHANG("Tran Thi Bich", "079987654321", "0987654321", "TP Ho Chi Minh", "VIP", null);
        KHACHHANG kh3 = new KHACHHANG("Le Hoang Nam", "048123456789", "0912345678", "Da Nang", "Doanh nghiep", null);
        KHACHHANG kh4 = new KHACHHANG("Pham Minh Tuan", "036123456789", "0934567890", "Hai Phong", "Thuong", null);
        KHACHHANG kh5 = new KHACHHANG("Hoang Thuy Linh", "001123456789", "0945678901", "Quang Ninh", "VIP", null);
        KHACHHANG kh6 = new KHACHHANG("Dang Quoc Huy", "092123456789", "0956789012", "Can Tho", "Doanh nghiep", null);
        danhSachKhachHang.add(kh1);
        danhSachKhachHang.add(kh2);
        danhSachKhachHang.add(kh3);
        danhSachKhachHang.add(kh4);
        danhSachKhachHang.add(kh5);
        danhSachKhachHang.add(kh6);

        // 2. Phong mau (Standard va VIP)
        PhongStandard p101 = new PhongStandard("P101", 500000.0, "Trong", false);
        PhongStandard p102 = new PhongStandard("P102", 550000.0, "Trong", true);
        PhongStandard p103 = new PhongStandard("P103", 500000.0, "Trong", false);
        PhongStandard p104 = new PhongStandard("P104", 600000.0, "Trong", true);
        PhongStandard p105 = new PhongStandard("P105", 500000.0, "Trong", false);
        PhongVIP p201 = new PhongVIP("P201", 1200000.0, "Trong", "Bien", 200000.0);
        PhongVIP p202 = new PhongVIP("P202", 1000000.0, "Trong", "Thanh pho", 150000.0);
        PhongVIP p203 = new PhongVIP("P203", 900000.0, "Trong", "San vuon", 100000.0);
        PhongVIP p301 = new PhongVIP("P301", 2000000.0, "Trong", "Bien", 500000.0);
        danhSachPhong.add(p101);
        danhSachPhong.add(p102);
        danhSachPhong.add(p103);
        danhSachPhong.add(p104);
        danhSachPhong.add(p105);
        danhSachPhong.add(p201);
        danhSachPhong.add(p202);
        danhSachPhong.add(p203);
        danhSachPhong.add(p301);

        // 3. Phieu dat phong & Lich su mau (Day du 4 trang thai nghiep vu)
        try {
            // PDP01: Khach Nguyen Van An - Phong P101 - Trang thai: DANG_O (da goi Pho bo + Ca phe)
            PhieuDatPhong pdp1 = new PhieuDatPhong("PDP01", "10/11/2026", "10/11/2026", "13/11/2026", kh1, p101);
            if (qlLichSu.themPhieuDatPhong(pdp1)) {
                qlLichSu.themDichVu("PDP01", "DA01", 2); // 2 Pho bo dac biet
                qlLichSu.themDichVu("PDP01", "NU01", 2); // 2 Ca phe sua da
                qlLichSu.batDauLuuTru("PDP01");          // Chuyen trang thai sang Dang o, phong P101: Dang o
            }

            // PDP02: Khach Tran Thi Bich - Phong VIP P201 - Trang thai: DA_TRA_PHONG (dung Spa + thue xe)
            PhieuDatPhong pdp2 = new PhieuDatPhong("PDP02", "01/11/2026", "02/11/2026", "05/11/2026", kh2, p201);
            if (qlLichSu.themPhieuDatPhong(pdp2)) {
                qlLichSu.themDichVu("PDP02", "SG01", 2); // 2 Massage body Thuy Dien
                qlLichSu.themDichVu("PDP02", "TX02", 1); // 1 Ngay thue xe may
                qlLichSu.thanhToanVaTraPhong("PDP02", "Chuyen khoan"); // Thanh toan xong, phong P201 ve Trong
            }

            // PDP03: Khach Le Hoang Nam - Phong P102 - Trang thai: DAT_MOI (chuan bi nhan phong)
            PhieuDatPhong pdp3 = new PhieuDatPhong("PDP03", "12/11/2026", "13/11/2026", "16/11/2026", kh3, p102);
            qlLichSu.themPhieuDatPhong(pdp3); // P102 o trang thai "Da dat"

            // PDP04: Khach Pham Minh Tuan - Phong VIP P202 - Trang thai: DANG_O (da goi Com tam + Bia + Gym)
            PhieuDatPhong pdp4 = new PhieuDatPhong("PDP04", "09/11/2026", "09/11/2026", "12/11/2026", kh4, p202);
            if (qlLichSu.themPhieuDatPhong(pdp4)) {
                qlLichSu.themDichVu("PDP04", "DA03", 2); // 2 Com tam suon bi cha
                qlLichSu.themDichVu("PDP04", "NU04", 4); // 4 Bia Heineken
                qlLichSu.themDichVu("PDP04", "SG04", 2); // 2 Ve tap Gym
                qlLichSu.batDauLuuTru("PDP04");          // Phong P202: Dang o
            }

            // PDP05: Khach Hoang Thuy Linh - Phong P103 - Trang thai: DA_HUY
            PhieuDatPhong pdp5 = new PhieuDatPhong("PDP05", "05/11/2026", "06/11/2026", "08/11/2026", kh5, p103);
            if (qlLichSu.themPhieuDatPhong(pdp5)) {
                qlLichSu.huyDatPhong("PDP05"); // Phong P103 tra ve Trong
            }

            // PDP06: Khach Dang Quoc Huy - Phong VIP P301 - Trang thai: DA_TRA_PHONG (thanh toan The tin dung)
            PhieuDatPhong pdp6 = new PhieuDatPhong("PDP06", "02/11/2026", "03/11/2026", "07/11/2026", kh6, p301);
            if (qlLichSu.themPhieuDatPhong(pdp6)) {
                qlLichSu.themDichVu("PDP06", "SG01", 2); // 2 Massage body
                qlLichSu.themDichVu("PDP06", "DA04", 2); // 2 Com ga xoi mo
                qlLichSu.themDichVu("PDP06", "NU02", 2); // 2 Nuoc ep cam
                qlLichSu.thanhToanVaTraPhong("PDP06", "The tin dung"); // Phong P301 ve Trong
            }
        } catch (Exception ignored) {
        }
    }

    public void chayMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n========== HOTEL MANAGEMENT - MODULE 5 ==========");
            System.out.println("1. Quan ly khach hang");
            System.out.println("2. Quan ly phong");
            System.out.println("3. Dat phong - dich vu - thanh toan - lich su");
            System.out.println("0. Thoat");
            int luaChon = nhapSo(sc, "Chon chuc nang: ");
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
                case 0:
                    System.out.println("Da thoat chuong trinh.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private void menuKhachHang(Scanner sc) {
        System.out.println("\n--- QUAN LY KHACH HANG ---");
        System.out.println("1. Them khach hang");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Tim theo CCCD");
        System.out.println("0. Quay lai");
        switch (nhapSo(sc, "Chon: ")) {
            case 1:
                String hoTen = nhapChuoi(sc, "Ho ten: ");
                String cccd = nhapChuoi(sc, "CCCD: ");
                String sdt = nhapChuoi(sc, "So dien thoai: ");
                String diaChi = nhapChuoi(sc, "Dia chi: ");
                String loai = nhapChuoi(sc, "Loai khach (Thuong/VIP/Doanh nghiep): ");
                danhSachKhachHang.add(new KHACHHANG(hoTen, cccd, sdt, diaChi, loai, null));
                System.out.println("Them khach hang thanh cong.");
                break;
            case 2:
                if (danhSachKhachHang.isEmpty()) {
                    System.out.println("Chua co khach hang.");
                }
                int sttKhach = 1;
                for (KHACHHANG khach : danhSachKhachHang) {
                    System.out.printf("[%d] CCCD: %s | %s | %s | %s%n", sttKhach++, khach.getCCCD(), khach.gethoten(), khach.getSdt(), khach.getLoaikhach());
                }
                break;
            case 3:
                KHACHHANG khach = timKhachHang(nhapChuoi(sc, "Nhap CCCD: "));
                System.out.println(khach == null ? "Khong tim thay." : khach.gethoten() + " | " + khach.getSdt());
                break;
            default:
                break;
        }
    }

    private void menuPhong(Scanner sc) {
        System.out.println("\n--- QUAN LY PHONG ---");
        System.out.println("1. Them phong Standard");
        System.out.println("2. Them phong VIP");
        System.out.println("3. Xem trang thai phong");
        System.out.println("0. Quay lai");
        switch (nhapSo(sc, "Chon: ")) {
            case 1:
                danhSachPhong.add(new PhongStandard(nhapChuoi(sc, "Ma phong: "), nhapGia(sc), "Trong", false));
                System.out.println("Them phong Standard thanh cong.");
                break;
            case 2:
                danhSachPhong.add(new PhongVIP(nhapChuoi(sc, "Ma phong: "), nhapGia(sc), "Trong", "Khong xac dinh", 0));
                System.out.println("Them phong VIP thanh cong.");
                break;
            case 3:
                if (danhSachPhong.isEmpty()) {
                    System.out.println("Chua co phong.");
                }
                for (Phong phong : danhSachPhong) {
                    System.out.printf("%s | %s | %,.0f VND/dem%n", phong.getMaPhong(), phong.getTrangThai(), phong.tinhGiaPhong());
                }
                break;
            default:
                break;
        }
    }

    private void menuDatPhong(Scanner sc) {
        System.out.println("\n--- DAT PHONG VA LICH SU ---");
        System.out.println("1. Tao phieu dat phong");
        System.out.println("2. Them dich vu");
        System.out.println("3. Nhan phong");
        System.out.println("4. Thanh toan - tra phong");
        System.out.println("5. Huy dat phong");
        System.out.println("6. Xem lich su va doanh thu");
        System.out.println("7. Xem danh sach dich vu");
        System.out.println("0. Quay lai");
        switch (nhapSo(sc, "Chon: ")) {
            case 1:
                taoPhieu(sc);
                break;
            case 2:
                System.out.println(qlLichSu.themDichVu(nhapChuoi(sc, "Ma phieu: "), nhapChuoi(sc, "Ma dich vu: "), nhapSo(sc, "So luong: "))
                        ? "Da them dich vu." : "Khong them duoc dich vu.");
                break;
            case 3:
                System.out.println(qlLichSu.batDauLuuTru(nhapChuoi(sc, "Ma phieu: ")) ? "Da nhan phong." : "Khong the nhan phong.");
                break;
            case 4:
                String maPhieu = nhapChuoi(sc, "Ma phieu: ");
                System.out.println(qlLichSu.thanhToanVaTraPhong(maPhieu, nhapChuoi(sc, "Phuong thuc thanh toan: "))
                        ? "Da thanh toan va tra phong." : "Khong the thanh toan.");
                break;
            case 5:
                System.out.println(qlLichSu.huyDatPhong(nhapChuoi(sc, "Ma phieu: ")) ? "Da huy dat phong." : "Khong the huy.");
                break;
            case 6:
                qlLichSu.hienThiLichSu();
                System.out.printf("Doanh thu da thanh toan: %,.0f VND%n", qlLichSu.tinhDoanhThuDaThanhToan());
                break;
            case 7:
                new DanhSachDichVu().hienThiDanhSach();
                break;
            default:
                break;
        }
    }

    private void taoPhieu(Scanner sc) {
        System.out.println("\n--- TAO PHIEU DAT PHONG ---");
        // Hien thi phong con trong de tien theo doi
        List<Phong> phongTrong = new ArrayList<>();
        for (Phong p : danhSachPhong) {
            if (p.conTrong()) phongTrong.add(p);
        }
        if (phongTrong.isEmpty()) {
            System.out.println("Hien tai da het phong trong de dat!");
            return;
        }
        System.out.print("Cac phong dang trong: ");
        for (int i = 0; i < phongTrong.size(); i++) {
            System.out.print(phongTrong.get(i).getMaPhong() + (i < phongTrong.size() - 1 ? ", " : ""));
        }
        System.out.println();

        String cccd = nhapChuoi(sc, "CCCD khach hang (hoac so thu tu 1, 2...): ");
        KHACHHANG khach = timKhachHang(cccd);
        if (khach == null) {
            System.out.println("-> Loi: Khong tim thay khach hang voi thong tin '" + cccd + "'!");
            System.out.println("   (Vui long vao menu [1. Quan ly khach hang] de xem danh sach hoac them khach moi).");
            return;
        }
        System.out.println("-> Da chon khach: " + khach.gethoten() + " (CCCD: " + khach.getCCCD() + ")");

        String maPhong = nhapChuoi(sc, "Ma phong trong: ");
        Phong phong = timPhong(maPhong);
        if (phong == null) {
            System.out.println("-> Loi: Phong '" + maPhong + "' khong ton tai trong he thong!");
            return;
        }
        if (!phong.conTrong()) {
            System.out.println("-> Loi: Phong '" + maPhong + "' khong con trong (Trang thai hien tai: " + phong.getTrangThai() + ").");
            return;
        }
        System.out.println("-> Da chon phong: " + phong.getMaPhong() + " (" + phong.getTrangThai() + ")");

        String ma = nhapChuoi(sc, "Ma phieu: ");
        String ngayDat = nhapChuoi(sc, "Ngay dat (d/M/yyyy): ");
        String ngayNhan = nhapChuoi(sc, "Ngay nhan (d/M/yyyy): ");
        String ngayTra = nhapChuoi(sc, "Ngay tra (d/M/yyyy): ");
        try {
            PhieuDatPhong phieu = new PhieuDatPhong(ma, ngayDat, ngayNhan, ngayTra, khach, phong);
            System.out.println(qlLichSu.themPhieuDatPhong(phieu) ? "Tao phieu dat phong thanh cong." : "Ma phieu da ton tai.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Ngay khong hop le: " + ex.getMessage());
        }
    }

    private KHACHHANG timKhachHang(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) return null;
        String trimmed = cccd.trim();
        // 1. Tim theo dung so CCCD
        for (KHACHHANG khach : danhSachKhachHang) {
            if (khach.getCCCD().equalsIgnoreCase(trimmed)) return khach;
        }
        // 2. Ho tro nguoi dung nhap so thu tu (1, 2, 3...)
        try {
            int stt = Integer.parseInt(trimmed);
            if (stt >= 1 && stt <= danhSachKhachHang.size()) {
                return danhSachKhachHang.get(stt - 1);
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    private Phong timPhong(String maPhong) {
        for (Phong phong : danhSachPhong) {
            if (phong.getMaPhong().equalsIgnoreCase(maPhong)) return phong;
        }
        return null;
    }

    private int nhapSo(Scanner sc, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            if (!sc.hasNextLine()) return 0;
            try {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Vui long nhap so nguyen hop le.");
            }
        }
    }

    private double nhapGia(Scanner sc) {
        while (true) {
            System.out.print("Gia co ban moi dem: ");
            if (!sc.hasNextLine()) return 0.0;
            try {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) return Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                System.out.println("Vui long nhap gia hop le.");
            }
        }
    }

    private String nhapChuoi(Scanner sc, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            if (!sc.hasNextLine()) return "";
            String giaTri = sc.nextLine().trim();
            if (!giaTri.isEmpty()) return giaTri;
            System.out.println("Thong tin khong duoc de trong.");
        }
    }

    public static void main(String[] args) {
        new Main().chayMenu();
    }
}