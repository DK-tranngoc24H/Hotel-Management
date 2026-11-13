import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class DanhSachPhong {
    private static final int KICH_THUOC_MAC_DINH = 100;

    private Phong[] ds;
    private int soLuong;
    private final Scanner sc;

    public DanhSachPhong() {
        this(new Scanner(System.in));
    }

    public DanhSachPhong(Scanner sc) {
        this.sc = (sc != null) ? sc : new Scanner(System.in);
        this.ds = new Phong[KICH_THUOC_MAC_DINH];
        this.soLuong = 0;
        docFileMacDinh();
    }

    private void docFileMacDinh() {
        String[] duongDan = { "Module2_QuanLyPhong/phong.text", "phong.text" };
        for (String path : duongDan) {
            if (new File(path).exists()) {
                docFile(path);
                return;
            }
        }
    }

    private void moRongMangNeuDay() {
        if (soLuong >= ds.length) {
            int kichThuocMoi = (ds.length == 0) ? KICH_THUOC_MAC_DINH : ds.length * 2;
            ds = Arrays.copyOf(ds, kichThuocMoi);
        }
    }

    private boolean them(Phong p) {
        if (p == null || timViTriTheoMa(p.getMaPhong()) != -1) {
            return false;
        }
        moRongMangNeuDay();
        ds[soLuong++] = p;
        return true;
    }

    private int timViTriTheoMa(String ma) {
        if (ma == null || ma.trim().isEmpty()) {
            return -1;
        }
        String maCanTim = ma.trim();
        for (int i = 0; i < soLuong; i++) {
            if (ds[i] != null && ds[i].getMaPhong().equalsIgnoreCase(maCanTim)) {
                return i;
            }
        }
        return -1;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public Phong[] getDanhSach() {
        return Arrays.copyOf(ds, soLuong);
    }

    public Phong timKiemTheoMaPhong(String ma) {
        int viTri = timViTriTheoMa(ma);
        return (viTri != -1) ? ds[viTri] : null;
    }

    public void themPhong() {
        int loai = KiemTraLoi.nhapSoNguyenTrongKhoang(sc, "Chon loai phong can them (1. Standard | 2. VIP): ", 1, 2);
        Phong phongMoi = (loai == 1) ? new PhongStandard() : new PhongVIP();
        phongMoi.nhap(sc);

        if (!them(phongMoi)) {
            ThongBao.inLoi("Ma phong '" + phongMoi.getMaPhong() + "' da ton tai!");
            return;
        }

        ThongBao.inThanhCong("Them phong moi thanh cong!");
    }

    public void xoaPhong() {
        if (soLuong == 0) {
            ThongBao.inCanhBao("Danh sach phong dang trong!");
            return;
        }

        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong can xoa: ");
        int viTri = timViTriTheoMa(ma);
        if (viTri == -1) {
            ThongBao.inLoi("Khong tim thay phong voi ma: " + ma);
            return;
        }
        if (!ds[viTri].conTrong()) {
            ThongBao.inCanhBao("Phong dang co nguoi thue hoac da duoc dat, khong the xoa!");
            return;
        }

        for (int i = viTri; i < soLuong - 1; i++) {
            ds[i] = ds[i + 1];
        }
        ds[--soLuong] = null;
        ThongBao.inThanhCong("Da xoa phong '" + ma.trim() + "' thanh cong!");
    }

    public void suaPhong() {
        if (soLuong == 0) {
            ThongBao.inCanhBao("Danh sach phong dang trong!");
            return;
        }

        String ma = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma phong can sua: ");
        int viTri = timViTriTheoMa(ma);
        if (viTri == -1) {
            ThongBao.inLoi("Khong tim thay phong voi ma: " + ma);
            return;
        }

        Phong p = ds[viTri];
        String maCu = p.getMaPhong();
        System.out.println("Tim thay phong! Nhap lai thong tin moi:");
        p.nhap(sc);

        // Tranh trung ma phong voi phong khac trong danh sach
        if (!p.getMaPhong().equalsIgnoreCase(maCu)) {
            for (int i = 0; i < soLuong; i++) {
                if (i != viTri && ds[i].getMaPhong().equalsIgnoreCase(p.getMaPhong().trim())) {
                    ThongBao.inCanhBao("Ma phong moi bi trung voi phong khac! Giu nguyen ma cu: " + maCu);
                    p.setMaPhong(maCu);
                    break;
                }
            }
        }
        ThongBao.inThanhCong("Cap nhat thong tin phong thanh cong!");
    }

    public int locPhongTrong() {
        int dem = 0;
        ThongBao.inTieuDe("DANH SACH PHONG CON TRONG");
        for (int i = 0; i < soLuong; i++) {
            if (ds[i].conTrong()) {
                ds[i].xuat();
                System.out.println("----------------------------------------");
                dem++;
            }
        }
        if (dem == 0) {
            ThongBao.inCanhBao("Hien tai da het phong trong!");
        }
        return dem;
    }

    public void xuatDanhSach() {
        if (soLuong == 0) {
            ThongBao.inCanhBao("Danh sach phong dang trong!");
            return;
        }
        ThongBao.inTieuDe("DANH SACH PHONG (" + soLuong + " phong)");
        for (int i = 0; i < soLuong; i++) {
            ds[i].xuat();
            System.out.println("----------------------------------------");
        }
    }

    public void ghiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (int i = 0; i < soLuong; i++) {
                bw.write(ds[i].toFileString());
                bw.newLine();
            }
            ThongBao.inThanhCong("Da ghi danh sach phong vao file: " + tenFile);
        } catch (IOException e) {
            ThongBao.inLoi("Loi ghi file: " + e.getMessage());
        }
    }

    public void docFile(String tenFile) {
        File f = new File(tenFile);
        if (!f.exists()) {
            ThongBao.inLoi("Khong tim thay file: " + tenFile);
            return;
        }

        Arrays.fill(ds, null);
        soLuong = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");
                String loai = parts[0].trim();
                if (loai.equalsIgnoreCase("STANDARD") && parts.length >= 5) {
                    them(new PhongStandard(
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()),
                            parts[3].trim(),
                            Boolean.parseBoolean(parts[4].trim())));
                } else if (loai.equalsIgnoreCase("VIP") && parts.length >= 6) {
                    them(new PhongVIP(
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim()),
                            parts[3].trim(),
                            parts[4].trim(),
                            Double.parseDouble(parts[5].trim())));
                }
            }
            ThongBao.inThanhCong("Da doc " + soLuong + " phong tu file: " + tenFile);
        } catch (Exception e) {
            ThongBao.inLoi("Loi doc file: " + e.getMessage());
        }
    }
}
