import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class DanhSachNguoi implements IQuanLy {
    private Nguoi[] ds;
    private int soluong;
    Scanner sc = new Scanner(System.in);

    // Thanh phan static kiem tra sdt hop le (Buoc 5)
    public static boolean kiemTraSDT(String sdt) {
        return sdt != null && sdt.trim().matches("^0\\d{9}$");
    }

    public DanhSachNguoi() {
        this.ds = null;
        this.soluong = 0;
    }

    public DanhSachNguoi(int n) {
        if (n < 0) {
            System.out.println("Loi: So luong khoi tao khong duoc am, dat ve 0.");
            n = 0;
        }
        this.soluong = n;
        this.ds = new Nguoi[n];
    }

    private int nhapSoNguyenTrongKhoang(String prompt, int min, int max) {
        return KiemTraLoi.nhapSoNguyenTrongKhoang(sc, prompt, min, max);
    }

    // Kiem tra trung ma dinh danh (boQuaViTri = -1 khi them moi)
    private boolean kiemTraTrungMa(String ma, int boQuaViTri) {
        if (ds == null || ma == null || ma.trim().isEmpty()) {
            return false;
        }
        String maCanTim = ma.trim();
        for (int i = 0; i < soluong; i++) {
            if (i == boQuaViTri) {
                continue;
            }
            if (ds[i] != null && ds[i].getMaDinhDanh() != null
                    && ds[i].getMaDinhDanh().equalsIgnoreCase(maCanTim)) {
                return true;
            }
        }
        return false;
    }

    // Helper: Tao doi tuong theo lua chon (Cai dat da hinh tai day)
    private Nguoi taoDoiTuongMoi() {
        return taoDoiTuongMoi(-1);
    }

    private Nguoi taoDoiTuongMoi(int boQuaViTri) {
        while (true) {
            System.out.println("Chon loai doi tuong can tao:");
            System.out.println("1. Khach hang");
            System.out.println("2. Nhan vien thuong");
            System.out.println("3. Quan ly");
            int chon = nhapSoNguyenTrongKhoang("Lua chon cua ban (1-3): ", 1, 3);

            Nguoi x;
            switch (chon) {
                case 2:
                    x = new NhanVien();
                    break;
                case 3:
                    x = new QuanLy();
                    break;
                case 1:
                default:
                    x = new KhachHang();
                    break;
            }
            x.nhap(); // Da hinh: goi dung phuong thuc nhap() cua lop con
            if (!kiemTraTrungMa(x.getMaDinhDanh(), boQuaViTri)) {
                return x;
            }
            ThongBao.inLoi("Ma dinh danh '" + x.getMaDinhDanh() + "' da ton tai trong danh sach! Vui long nhap lai.");
        }
    }

    // a. Nhap n phan tu dau tien (Buoc 2.1.a)
    @Override
    public void nhapDanhSach() {
        soluong = nhapSoNguyenTrongKhoang("Nhap so luong phan tu ban dau n = ", 0, Integer.MAX_VALUE);
        ds = new Nguoi[soluong];
        for (int i = 0; i < soluong; i++) {
            System.out.println("\n--- Nhap phan tu thu " + (i + 1) + " ---");
            ds[i] = taoDoiTuongMoi();
        }
        System.out.println(">> Da nhap xong danh sach!");
    }

    // b. Xuat danh sach (Buoc 2.1.b - Da hinh)
    @Override
    public void xuatDanhSach() {
        if (soluong == 0 || ds == null) {
            System.out.println(">> Danh sach hien dang trong!");
            return;
        }

        System.out.println("\n========== DANH SACH CON NGUOI (SO LUONG: " + soluong + ") ==========");
        for (int i = 0; i < soluong; i++) {
            System.out.println("\n[STT: " + (i + 1) + "]");
            if (ds[i] != null) {
                ds[i].xuat(); // Da hinh: tu dong in chi tiet theo kieu doi tuong thuc te
            } else {
                System.out.println("(Phan tu rong)");
            }
            System.out.println("----------------------------------------------");
        }
    }

    // c. Them moi 1 phan tu, k phan tu (Buoc 2.1.c)
    @Override
    public void them1PhanTu() {
        if (ds == null) {
            ds = new Nguoi[1];
            soluong = 0;
        } else {
            ds = Arrays.copyOf(ds, soluong + 1);
        }
        System.out.println("\n--- Them 1 doi tuong moi ---");
        ds[soluong] = taoDoiTuongMoi();
        soluong++;
        System.out.println(">> Da them thanh cong 1 doi tuong!");
    }

    @Override
    public void themKPhanTu() {
        int k = nhapSoNguyenTrongKhoang("Nhap so luong can them k = ", 1, Integer.MAX_VALUE);

        int viTriCu = (ds == null) ? 0 : soluong;
        ds = (ds == null) ? new Nguoi[k] : Arrays.copyOf(ds, soluong + k);

        for (int i = 0; i < k; i++) {
            System.out.println("\n--- Them phan tu thu " + (i + 1) + "/" + k + " ---");
            ds[viTriCu + i] = taoDoiTuongMoi();
        }
        soluong += k;
        System.out.println(">> Da them thanh cong " + k + " phan tu!");
    }

    // d. Sua phan tu theo ma (Buoc 2.1.d)
    @Override
    public void suaTheoMa() {
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong!");
            return;
        }
        System.out.print("Nhap ma (CCCD hoac MaNV) can sua: ");
        String ma = sc.nextLine().trim();
        if (ma.isEmpty()) {
            System.out.println("Loi: Ma can sua khong duoc de trong!");
            return;
        }

        int viTri = timViTriTheoMa(ma);
        if (viTri == -1) {
            System.out.println(">> Khong tim thay doi tuong co ma: " + ma);
            return;
        }

        System.out.println("\n--- Thong tin hien tai cua doi tuong: ---");
        ds[viTri].xuat();

        System.out.println("\n--- Nhap lai thong tin moi: ---");
        ds[viTri] = taoDoiTuongMoi(viTri);
        System.out.println(">> Cap nhat thong tin thanh cong!");
    }

    // e. Xoa phan tu theo ma (Buoc 2.1.e)
    @Override
    public void xoaTheoMa() {
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong!");
            return;
        }
        System.out.print("Nhap ma (CCCD hoac MaNV) can xoa: ");
        String ma = sc.nextLine().trim();
        if (ma.isEmpty()) {
            System.out.println("Loi: Ma can xoa khong duoc de trong!");
            return;
        }

        int viTri = timViTriTheoMa(ma);
        if (viTri == -1) {
            System.out.println(">> Khong tim thay doi tuong co ma: " + ma);
            return;
        }

        for (int i = viTri; i < soluong - 1; i++) {
            ds[i] = ds[i + 1];
        }
        ds[soluong - 1] = null;
        soluong--;
        ds = Arrays.copyOf(ds, soluong);
        System.out.println(">> Xoa thanh cong doi tuong co ma: " + ma);
    }

    // Helper: Tim vi tri theo ma (chinh xac)
    public int timViTriTheoMa(String ma) {
        if (ds == null || ma == null || ma.trim().isEmpty()) {
            return -1;
        }
        String maCanTim = ma.trim();
        for (int i = 0; i < soluong; i++) {
            if (ds[i] != null && ds[i].getMaDinhDanh() != null
                    && ds[i].getMaDinhDanh().equalsIgnoreCase(maCanTim)) {
                return i;
            }
        }
        return -1;
    }

    // f. Tim kiem: Nhieu khoa, tu chinh xac den gan dung (Buoc 2.1.f)
    @Override
    public void timKiem() {
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong!");
            return;
        }
        System.out.println("\n--- CHON CHE DO TIM KIEM ---");
        System.out.println("1. Tim kiem chinh xac theo ma (CCCD / MaNV)");
        System.out.println("2. Tim kiem gan dung theo ho ten");
        int chon = nhapSoNguyenTrongKhoang("Lua chon (1-2): ", 1, 2);

        if (chon == 1) {
            System.out.print("Nhap ma can tim: ");
            String ma = sc.nextLine().trim();
            if (ma.isEmpty()) {
                System.out.println("Loi: Ma tim kiem khong duoc de trong!");
                return;
            }
            int idx = timViTriTheoMa(ma);
            if (idx != -1) {
                System.out.println(">> Ket qua tim thay:");
                ds[idx].xuat();
            } else {
                System.out.println(">> Khong tim thay ket qua!");
            }
        } else {
            System.out.print("Nhap ten/tu khoa ho ten can tim: ");
            String ten = sc.nextLine().trim().toLowerCase();
            if (ten.isEmpty()) {
                System.out.println("Loi: Tu khoa tim kiem khong duoc de trong!");
                return;
            }
            boolean timThay = false;
            System.out.println(">> Cac ket qua phu hop:");
            for (int i = 0; i < soluong; i++) {
                if (ds[i] != null && ds[i].gethoten() != null
                        && ds[i].gethoten().toLowerCase().contains(ten)) {
                    ds[i].xuat();
                    System.out.println("-------------------------");
                    timThay = true;
                }
            }
            if (!timThay) {
                System.out.println("Khong tim thay ai co ten chua tu khoa '" + ten + "'");
            }
        }
    }

    // g. Thong ke so lieu theo nhieu khoa (Buoc 2.1.g)
    @Override
    public void thongKe() {
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong!");
            return;
        }

        int demKH = 0, demNV = 0, demQL = 0;
        long tongLuong = 0;

        for (int i = 0; i < soluong; i++) {
            if (ds[i] instanceof QuanLy) {
                demQL++;
                demNV++;
                QuanLy ql = (QuanLy) ds[i];
                tongLuong += ql.getLuong() + (long) ql.getPhuCapChucVu();
            } else if (ds[i] instanceof NhanVien) {
                demNV++;
                NhanVien nv = (NhanVien) ds[i];
                tongLuong += nv.getLuong();
            } else if (ds[i] instanceof KhachHang) {
                demKH++;
            }
        }

        System.out.println("\n========== THONG KE SO LIEU ==========");
        System.out.println("- Tong so doi tuong dang quan ly: " + soluong);
        System.out.println("- So luong Khach Hang: " + demKH);
        System.out.println("- So luong Nhan Vien: " + demNV + " (Trong do co " + demQL + " quan ly)");
        System.out.println("- Tong quy luong nhan vien/quan ly: " + tongLuong + " VND");
        if (demNV > 0) {
            System.out.println("- Luong trung binh/nhan vien: " + (tongLuong / demNV) + " VND");
        }
    }

    // h. Chuc nang nghiep vu khac (Buoc 2.1.h)
    public void timNhanVienLuongCaoNhat() {
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong!");
            return;
        }

        NhanVien maxNV = null;
        for (int i = 0; i < soluong; i++) {
            if (ds[i] instanceof NhanVien) {
                NhanVien nv = (NhanVien) ds[i];
                if (maxNV == null || nv.getLuong() > maxNV.getLuong()) {
                    maxNV = nv;
                }
            }
        }

        if (maxNV != null) {
            System.out.println("\n>> Nhan vien co muc luong co ban cao nhat la:");
            maxNV.xuat();
        } else {
            System.out.println("Chua co nhan vien nao trong danh sach!");
        }
    }

    // Buoc 4: Doc va ghi du lieu len file
    @Override
    public void ghiFile(String tenFile) {
        if (tenFile == null || tenFile.trim().isEmpty()) {
            System.out.println("Loi: Ten file khong duoc de trong!");
            return;
        }
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong, khong co du lieu de ghi file!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (int i = 0; i < soluong; i++) {
                Nguoi x = ds[i];
                if (x == null) continue;
                if (x instanceof QuanLy) {
                    QuanLy ql = (QuanLy) x;
                    bw.write(String.format(Locale.US, "QUANLY;%s;%s;%s;%s;%s;%s;%d;%s;%.2f\n",
                            ql.gethoten(), ql.getCCCD(), ql.getSdt(), ql.getDiachi(),
                            ql.getManv(), ql.getChucvu(), ql.getLuong(),
                            ql.getPhongBQL(), ql.getPhuCapChucVu()));
                } else if (x instanceof NhanVien) {
                    NhanVien nv = (NhanVien) x;
                    bw.write(String.format("NHANVIEN;%s;%s;%s;%s;%s;%s;%d\n",
                            nv.gethoten(), nv.getCCCD(), nv.getSdt(), nv.getDiachi(),
                            nv.getManv(), nv.getChucvu(), nv.getLuong()));
                } else if (x instanceof KhachHang) {
                    KhachHang kh = (KhachHang) x;
                    bw.write(String.format("KHACHHANG;%s;%s;%s;%s;%s\n",
                            kh.gethoten(), kh.getCCCD(), kh.getSdt(), kh.getDiachi(),
                            kh.getLoaikhach()));
                }
            }
            System.out.println(">> Da ghi thanh cong " + soluong + " phan tu vao file: " + tenFile);
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    private boolean kiemTraDuLieuChungFile(String[] parts, int soDong) {
        if (!Nguoi.kiemTraHoTen(parts[1])) {
            ThongBao.inCanhBao("Dong " + soDong + ": Ho ten khong hop le, bo qua.");
            return false;
        }
        if (!Nguoi.kiemTraCCCD(parts[2])) {
            ThongBao.inCanhBao("Dong " + soDong + ": CCCD khong hop le, bo qua.");
            return false;
        }
        if (!Nguoi.kiemTraSDT(parts[3])) {
            ThongBao.inCanhBao("Dong " + soDong + ": SDT khong hop le, bo qua.");
            return false;
        }
        if (parts[4].trim().isEmpty()) {
            ThongBao.inCanhBao("Dong " + soDong + ": Dia chi trong, bo qua.");
            return false;
        }
        return true;
    }

    @Override
    public void docFile(String tenFile) {
        if (tenFile == null || tenFile.trim().isEmpty()) {
            ThongBao.inLoi("Ten file khong duoc de trong!");
            return;
        }
        File f = new File(tenFile);
        if (!f.exists()) {
            File alt = new File("D:\\Documents\\java(lthdt)\\java(lthdt)\\TV1", tenFile);
            if (alt.exists()) {
                f = alt;
            } else {
                ThongBao.inLoi("File khong ton tai: " + tenFile);
                return;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            int soDong = 0;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                String loai = parts[0].trim();

                Nguoi x = null;
                try {
                    if (loai.equalsIgnoreCase("KHACHHANG") && parts.length >= 6) {
                        if (!kiemTraDuLieuChungFile(parts, soDong)) continue;
                        if (parts[5].trim().isEmpty()) {
                            ThongBao.inCanhBao("Dong " + soDong + ": Loai khach trong, bo qua.");
                            continue;
                        }
                        x = new KhachHang(parts[1], parts[2], parts[3], parts[4], parts[5], null);
                    } else if (loai.equalsIgnoreCase("NHANVIEN") && parts.length >= 8) {
                        if (!kiemTraDuLieuChungFile(parts, soDong)) continue;
                        if (!NhanVien.kiemTraMaNV(parts[5]) || parts[6].trim().isEmpty()) {
                            ThongBao.inCanhBao("Dong " + soDong + ": Ma NV hoac chuc vu khong hop le, bo qua.");
                            continue;
                        }
                        int luong = Integer.parseInt(parts[7].trim());
                        if (luong < 0) {
                            ThongBao.inCanhBao("Dong " + soDong + ": Luong am, bo qua.");
                            continue;
                        }
                        x = new NhanVien(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], luong);
                    } else if (loai.equalsIgnoreCase("QUANLY") && parts.length >= 10) {
                        if (!kiemTraDuLieuChungFile(parts, soDong)) continue;
                        if (!NhanVien.kiemTraMaNV(parts[5]) || parts[6].trim().isEmpty() || parts[8].trim().isEmpty()) {
                            ThongBao.inCanhBao("Dong " + soDong + ": Ma NV, chuc vu hoac phong BQL khong hop le, bo qua.");
                            continue;
                        }
                        int luong = Integer.parseInt(parts[7].trim());
                        double phuCap = Double.parseDouble(parts[9].trim());
                        if (luong < 0 || phuCap < 0) {
                            ThongBao.inCanhBao("Dong " + soDong + ": Luong hoac phu cap am, bo qua.");
                            continue;
                        }
                        x = new QuanLy(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], luong, parts[8], phuCap);
                    } else {
                        ThongBao.inCanhBao("Dong " + soDong + ": Dinh dang hoac loai doi tuong khong hop le.");
                    }
                } catch (NumberFormatException e) {
                    ThongBao.inCanhBao("Dong " + soDong + ": Loi dinh dang so (" + e.getMessage() + "), bo qua.");
                    continue;
                }

                if (x != null) {
                    if (kiemTraTrungMa(x.getMaDinhDanh(), -1)) {
                        ThongBao.inCanhBao("Dong " + soDong + ": Trung ma dinh danh '" + x.getMaDinhDanh() + "', bo qua.");
                        continue;
                    }
                    if (ds == null) {
                        ds = new Nguoi[1];
                        ds[0] = x;
                        soluong = 1;
                    } else {
                        ds = Arrays.copyOf(ds, soluong + 1);
                        ds[soluong] = x;
                        soluong++;
                    }
                }
            }
            System.out.println(">> Da doc file thanh cong! Tong so phan tu hien tai: " + soluong);
        } catch (IOException e) {
            ThongBao.inLoi("Loi khi doc file: " + e.getMessage());
        }
    }

    public int getSoluong() {
        return soluong;
    }
}
