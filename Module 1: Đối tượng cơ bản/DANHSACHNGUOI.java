import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DANHSACHNGUOI implements IQUANLY {
    private NGUOI[] ds;
    private int soluong;
    Scanner sc = new Scanner(System.in);

    // Thanh phan static kiem tra sdt hop le (Buoc 5)
    public static boolean kiemTraSDT(String sdt) {
        return sdt != null && sdt.matches("^0\\d{9}$");
    }

    public DANHSACHNGUOI() {
        this.ds = null;
        this.soluong = 0;
    }

    public DANHSACHNGUOI(int n) {
        this.soluong = n;
        this.ds = new NGUOI[n];
    }

    // Helper: Tao doi tuong theo lua chon (Cai dat da hinh tai day)
    private NGUOI taoDoiTuongMoi() {
        System.out.println("Chon loai doi tuong can tao:");
        System.out.println("1. Khach hang");
        System.out.println("2. Nhan vien thuong");
        System.out.println("3. Quan ly");
        System.out.print("Lua chon cua ban (1-3): ");
        int chon = 1;
        try {
            chon = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            chon = 1;
        }

        NGUOI x;
        switch (chon) {
            case 2:
                x = new NHANVIEN();
                break;
            case 3:
                x = new QUANLY();
                break;
            case 1:
            default:
                x = new KHACHHANG();
                break;
        }
        x.nhap(); // Da hinh: goi dung phuong thuc nhap() cua lop con
        return x;
    }

    // a. Nhap n phan tu dau tien (Buoc 2.1.a)
    @Override
    public void nhapDanhSach() {
        System.out.print("Nhap so luong phan tu ban dau n = ");
        try {
            soluong = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            soluong = 0;
        }

        ds = new NGUOI[soluong];
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
            ds[i].xuat(); // Da hinh: tu dong in chi tiet theo kieu doi tuong thuc te
            System.out.println("----------------------------------------------");
        }
    }

    // c. Them moi 1 phan tu, k phan tu (Buoc 2.1.c)
    @Override
    public void them1PhanTu() {
        if (ds == null) {
            ds = new NGUOI[1];
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
        System.out.print("Nhap so luong can them k = ");
        int k = 0;
        try {
            k = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            k = 0;
        }
        if (k <= 0) {
            System.out.println("So luong them phai > 0!");
            return;
        }

        int viTriCu = (ds == null) ? 0 : soluong;
        ds = (ds == null) ? new NGUOI[k] : Arrays.copyOf(ds, soluong + k);

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

        int viTri = timViTriTheoMa(ma);
        if (viTri == -1) {
            System.out.println(">> Khong tim thay doi tuong co ma: " + ma);
            return;
        }

        System.out.println("\n--- Thong tin hien tai cua doi tuong: ---");
        ds[viTri].xuat();

        System.out.println("\n--- Nhap lai thong tin moi: ---");
        ds[viTri] = taoDoiTuongMoi();
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
        if (ds == null) return -1;
        for (int i = 0; i < soluong; i++) {
            if (ds[i] != null && ds[i].getMaDinhDanh().equalsIgnoreCase(ma)) {
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
        System.out.print("Lua chon: ");
        int chon = 1;
        try {
            chon = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            chon = 1;
        }

        if (chon == 1) {
            System.out.print("Nhap ma can tim: ");
            String ma = sc.nextLine().trim();
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
            boolean timThay = false;
            System.out.println(">> Cac ket qua phu hop:");
            for (int i = 0; i < soluong; i++) {
                if (ds[i] != null && ds[i].gethoten().toLowerCase().contains(ten)) {
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
            if (ds[i] instanceof QUANLY) {
                demQL++;
                demNV++;
                QUANLY ql = (QUANLY) ds[i];
                tongLuong += ql.getLuong() + (long) ql.getPhuCapChucVu();
            } else if (ds[i] instanceof NHANVIEN) {
                demNV++;
                NHANVIEN nv = (NHANVIEN) ds[i];
                tongLuong += nv.getLuong();
            } else if (ds[i] instanceof KHACHHANG) {
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

        NHANVIEN maxNV = null;
        for (int i = 0; i < soluong; i++) {
            if (ds[i] instanceof NHANVIEN) {
                NHANVIEN nv = (NHANVIEN) ds[i];
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
        if (soluong == 0 || ds == null) {
            System.out.println("Danh sach trong, khong co du lieu de ghi file!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (int i = 0; i < soluong; i++) {
                NGUOI x = ds[i];
                if (x instanceof QUANLY) {
                    QUANLY ql = (QUANLY) x;
                    bw.write(String.format("QUANLY;%s;%s;%s;%s;%s;%s;%d;%s;%.2f\n",
                            ql.gethoten(), ql.getCCCD(), ql.getSdt(), ql.getDiachi(),
                            ql.getManv(), ql.getChucvu(), ql.getLuong(),
                            ql.getPhongBQL(), ql.getPhuCapChucVu()));
                } else if (x instanceof NHANVIEN) {
                    NHANVIEN nv = (NHANVIEN) x;
                    bw.write(String.format("NHANVIEN;%s;%s;%s;%s;%s;%s;%d\n",
                            nv.gethoten(), nv.getCCCD(), nv.getSdt(), nv.getDiachi(),
                            nv.getManv(), nv.getChucvu(), nv.getLuong()));
                } else if (x instanceof KHACHHANG) {
                    KHACHHANG kh = (KHACHHANG) x;
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

    @Override
    public void docFile(String tenFile) {
        File f = new File(tenFile);
        if (!f.exists()) {
            File alt = new File("D:\\Documents\\java(lthdt)\\java(lthdt)\\TV1", tenFile);
            if (alt.exists()) {
                f = alt;
            } else {
                System.out.println("File khong ton tai: " + tenFile);
                return;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                String loai = parts[0];

                NGUOI x = null;
                if (loai.equalsIgnoreCase("KHACHHANG") && parts.length >= 6) {
                    x = new KHACHHANG(parts[1], parts[2], parts[3], parts[4], parts[5], null);
                } else if (loai.equalsIgnoreCase("NHANVIEN") && parts.length >= 8) {
                    int luong = Integer.parseInt(parts[7]);
                    x = new NHANVIEN(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], luong);
                } else if (loai.equalsIgnoreCase("QUANLY") && parts.length >= 10) {
                    int luong = Integer.parseInt(parts[7]);
                    double phuCap = Double.parseDouble(parts[9]);
                    x = new QUANLY(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], luong, parts[8], phuCap);
                }

                if (x != null) {
                    if (ds == null) {
                        ds = new NGUOI[1];
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
        } catch (Exception e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        }
    }

    public int getSoluong() {
        return soluong;
    }
}
